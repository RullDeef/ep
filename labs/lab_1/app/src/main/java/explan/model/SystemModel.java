package explan.model;

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

        float time = 0.0f;
        int requestId = 1;

        float nextRequestArrivalTime = 0.0f;
        float workCompleteTime = 0.0f;
        boolean workerBusy = false;

        Request processingRequest = null;
        float workerIdle = 0.0f;

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
            float deltaTime = nextRequestArrivalTime;
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
}
