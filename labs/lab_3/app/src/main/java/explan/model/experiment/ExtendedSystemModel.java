package explan.model.experiment;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class ExtendedSystemModel {
    private Generator sourceGenerator1 = Generator.exp(1.0f);
    private Generator workerGenerator1 = Generator.exp(1.0f);
    private Generator sourceGenerator2 = Generator.exp(1.0f);
    private Generator workerGenerator2 = Generator.exp(1.0f);

    public void setSourceGenerator1(Generator gen) {
        sourceGenerator1 = gen;
    }

    public void setWorkerGenerator1(Generator gen) {
        workerGenerator1 = gen;
    }

    public void setSourceGenerator2(Generator gen) {
        sourceGenerator2 = gen;
    }

    public void setWorkerGenerator2(Generator gen) {
        workerGenerator2 = gen;
    }

    public SimulationResult simulateRequests(int totalRequests) {
        var requestsQueued = new ArrayDeque<Request>();
        var requestsProcessed = new ArrayList<Request>();

        double time = 0.0;

        double nextRequestArrivalTime1 = 0.0;
        double nextRequestArrivalTime2 = 0.0;
        double workCompleteTime = 0.0;
        boolean workerBusy = false;

        Request processingRequest = null;
        float workerIdle = 0.0f;

        int requestsProduced = 0;
        int requestsLeft = totalRequests;

        while (requestsLeft > 0) {
            // check request arrival
            if (nextRequestArrivalTime1 <= 0.0 && requestsProduced < totalRequests) {
                requestsProduced += 1;
                // if worker is ready to accept it - bypass queuing
                if (!workerBusy) {
                    processingRequest = new Request(1, time);
                    processingRequest.leaveQueueTime = time;
                    workCompleteTime = workerGenerator1.genNext();
                    workerBusy = true;
                } else {
                    var request = new Request(1, time);
                    requestsQueued.add(request);
                }
                nextRequestArrivalTime1 = sourceGenerator1.genNext();
            }

            if (nextRequestArrivalTime2 <= 0.0 && requestsProduced < totalRequests) {
                requestsProduced += 1;
                // if worker is ready to accept it - bypass queuing
                if (!workerBusy) {
                    processingRequest = new Request(2, time);
                    processingRequest.leaveQueueTime = time;
                    workCompleteTime = workerGenerator2.genNext();
                    workerBusy = true;
                } else {
                    var request = new Request(2, time);
                    requestsQueued.add(request);
                }
                nextRequestArrivalTime2 = sourceGenerator2.genNext();
            }

            // check processing request
            if (workerBusy && workCompleteTime <= 0.0f) {
                processingRequest.leaveSystemTime = time;
                requestsProcessed.add(processingRequest);
                requestsLeft -= 1;
                if (requestsQueued.isEmpty()) {
                    processingRequest = null;
                    workerBusy = false;
                } else {
                    processingRequest = requestsQueued.removeLast();
                    processingRequest.leaveQueueTime = time;
                    if (processingRequest.id == 1) {
                        workCompleteTime = workerGenerator1.genNext();
                    } else {
                        workCompleteTime = workerGenerator2.genNext();
                    }
                }
            }

            // advance time
            var deltaTime = Double.POSITIVE_INFINITY;
            if (requestsProduced < totalRequests) {
                deltaTime = Math.min(nextRequestArrivalTime1, nextRequestArrivalTime2);
            }
            if (workerBusy) {
                deltaTime = Math.min(deltaTime, workCompleteTime);
            }

            time += deltaTime;
            nextRequestArrivalTime1 -= deltaTime;
            nextRequestArrivalTime2 -= deltaTime;
            if (workerBusy) {
                workCompleteTime -= deltaTime;
            } else {
                workerIdle += deltaTime;
            }
        }

        return new SimulationResult(
            time,
            requestsQueued,
            requestsProcessed,
            processingRequest,
            workerIdle
        );
    }
}
