package explan.model.experiment;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class SystemModel {
    private Generator sourceGenerator = Generator.exp(1.0f);
    private Generator workerGenerator = Generator.exp(1.0f);

    private float simTime = 100.0f;

    public void setSourceGenerator(Generator gen) {
        sourceGenerator = gen;
    }

    public void setWorkerGenerator(Generator gen) {
        workerGenerator = gen;
    }

    public void setSimulationTime(float time) {
        simTime = time;
    }

    public SimulationResult simulate() {
        var requestsQueued = new ArrayDeque<Request>();
        var requestsProcessed = new ArrayList<Request>();

        double time = 0.0f;
        int requestId = 1;

        double nextRequestArrivalTime = 0.0f;
        double workCompleteTime = 0.0f;
        boolean workerBusy = false;

        Request processingRequest = null;
        double workerIdle = 0.0f;

        while (time < simTime) {
            // check request arrival
            if (nextRequestArrivalTime <= 0.0) {
                // if worker is ready to accept it - bypass queuing
                if (!workerBusy) {
                    processingRequest = new Request(requestId++, time);
                    processingRequest.leaveQueueTime = time;
                    workCompleteTime = workerGenerator.genNext();
                    workerBusy = true;
                } else {
                    var request = new Request(requestId++, time);
                    requestsQueued.add(request);
                }
                nextRequestArrivalTime = sourceGenerator.genNext();
            }

            // check processing request
            if (workerBusy && workCompleteTime <= 0.0f) {
                processingRequest.leaveSystemTime = time;
                requestsProcessed.add(processingRequest);
                if (requestsQueued.isEmpty()) {
                    processingRequest = null;
                    workerBusy = false;
                } else {
                    processingRequest = requestsQueued.removeLast();
                    processingRequest.leaveQueueTime = time;
                    workCompleteTime = workerGenerator.genNext();
                }
            }

            // advance time
            var deltaTime = nextRequestArrivalTime;
            if (workerBusy) {
                deltaTime = Math.min(deltaTime, workCompleteTime);
            }

            time += deltaTime;
            nextRequestArrivalTime -= deltaTime;
            if (workerBusy) {
                workCompleteTime -= deltaTime;
            } else {
                workerIdle += deltaTime;
            }
        }

        return new SimulationResult(
                simTime,
                requestsQueued,
                requestsProcessed,
                processingRequest,
                workerIdle);
    }

    public SimulationResult simulateRequests(int totalRequests) {
        var requestsQueued = new ArrayDeque<Request>();
        var requestsProcessed = new ArrayList<Request>();

        double time = 0.0;
        int requestId = 1;

        double nextRequestArrivalTime = 0.0;
        double workCompleteTime = 0.0;
        boolean workerBusy = false;

        Request processingRequest = null;
        float workerIdle = 0.0f;

        int requestsProduced = 0;
        int requestsLeft = totalRequests;

        while (requestsLeft > 0) {
            // check request arrival
            if (nextRequestArrivalTime <= 0.0 && requestsProduced < totalRequests) {
                requestsProduced += 1;
                // if worker is ready to accept it - bypass queuing
                if (!workerBusy) {
                    processingRequest = new Request(requestId++, time);
                    processingRequest.leaveQueueTime = time;
                    workCompleteTime = workerGenerator.genNext();
                    workerBusy = true;
                } else {
                    var request = new Request(requestId++, time);
                    requestsQueued.add(request);
                }
                nextRequestArrivalTime = sourceGenerator.genNext();
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
                    workCompleteTime = workerGenerator.genNext();
                }
            }

            // check for time corruption
            if (Double.isNaN(time) || Double.isNaN(nextRequestArrivalTime) || Double.isNaN(workCompleteTime)) {
                // time is corrupted!
                assert false;
            }

            // advance time
            var deltaTime = Double.POSITIVE_INFINITY;
            if (requestsProduced < totalRequests) {
                deltaTime = nextRequestArrivalTime;
            }
            if (workerBusy) {
                deltaTime = Math.min(deltaTime, workCompleteTime);
            }

            time += deltaTime;
            nextRequestArrivalTime -= deltaTime;
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
                workerIdle);
    }
}
