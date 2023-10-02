import math
import random

class Request:
    def __init__(self, id, time):
        self.id = id
        self.arrival_time = time
        self.leave_queue_time = math.inf
        self.leave_system_time = math.inf
    
    def waiting_time(self):
        return self.leave_queue_time - self.arrival_time


def make_exp_generator(param: float):
    def gen():
        r = random.random()
        return - math.log(r) / param
    return gen


def run_simulation(source_generator, worker_generator, total_requests: int):
    requests_queued = []
    requests_processed = []

    time = 0.0
    request_id = 1

    next_request_arrival_time = 0.0
    work_complete_time = 0.0
    worker_busy = False

    processing_request = None
    worker_idle = 0.0

    requests_produced = 0
    requests_left = total_requests

    while requests_left > 0:
        # check request arrival
        if next_request_arrival_time <= 0.0 and requests_produced < total_requests:
            requests_produced += 1
            # if worker is ready to accept it - bypass queuing
            if not worker_busy:
                request_id += 1
                processing_request = Request(request_id, time)
                processing_request.leave_queue_time = time
                work_complete_time = worker_generator()
                worker_busy = True
            else:
                request_id += 1
                request = Request(request_id, time)
                requests_queued.append(request)
            next_request_arrival_time = source_generator()

        # check processing request
        if worker_busy and work_complete_time <= 0.0:
            processing_request.leave_system_time = time
            requests_processed.append(processing_request)
            requests_left -= 1
            if len(requests_queued) == 0:
                processing_request = None
                worker_busy = False
            else:
                processing_request = requests_queued.pop(0)
                processing_request.leave_queue_time = time
                work_complete_time = worker_generator()

        # advance time
        delta_time = math.inf
        if requests_produced < total_requests:
            delta_time = next_request_arrival_time
        if worker_busy:
            delta_time = min(delta_time, work_complete_time)

        time += delta_time
        next_request_arrival_time -= delta_time
        if worker_busy:
            work_complete_time -= delta_time
        else:
            worker_idle += delta_time

    return time, requests_queued, requests_processed, processing_request, worker_idle


def y_func(x1: float, x2: float) -> float:
    total_requests = 100
    iterations = 100
    res = 0.0

    source_gen = make_exp_generator(x1)
    worker_gen = make_exp_generator(x2)

    for i in range(iterations):
        _, _, requests_processed, _, _ = run_simulation(source_gen, worker_gen, total_requests)
        
        if len(requests_processed) > 0:
            avgTime = 0.0
            for request in requests_processed:
                avgTime += request.waiting_time()
            res += avgTime / len(requests_processed)
        else:
            iterations -= 1

    return res / iterations

def y_func_builder(requests: int, iterations: int):
    def func(x1: float, x2: float, *, S=[requests, iterations]) -> float:
        requests = S[0]
        iterations = S[1]
        res = 0.0
        source_gen = make_exp_generator(x1)
        worker_gen = make_exp_generator(x2)

        for i in range(iterations):
            _, _, requests_processed, _, _ = run_simulation(source_gen, worker_gen, requests)
            if len(requests_processed) > 0:
                avgTime = 0.0
                for request in requests_processed:
                    avgTime += request.waiting_time()
                res += avgTime / len(requests_processed)
            else:
                iterations -= 1
        return res / iterations
    return func


if __name__ == "__main__":
    x1 = float(input("x1:"))
    x2 = float(input("x2:"))

    # source_gen = make_exp_generator(x1)
    # worker_gen = make_exp_generator(x2)

    y = y_func(x1, x2)
    print("y:", y)

