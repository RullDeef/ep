package explan.model;

import java.util.Deque;
import java.util.List;

public class SimulationResult {
    public float totalTime;
    public Deque<Request> requestsQueued;
    public List<Request> requestsProcessed;

    public Request requestProcessing;
    public float workerIdleTime;

    public SimulationResult(
            float totalTime,
            Deque<Request> queue,
            List<Request> processed,
            Request processing,
            float workerIdle) {
        this.totalTime = totalTime;
        this.requestsQueued = queue;
        this.requestsProcessed = processed;
        this.requestProcessing = processing;
        this.workerIdleTime = workerIdle;
    }

    public float statRho() {
        // return avgWaitTime() / totalTime;
        return statLambda() / statMu();
    }

    public float avgWaitTime() {
        float avgTime = 0.0f;

        for (var request : requestsProcessed)
            avgTime += request.waitingTime();

        // if (requestProcessing != null)
        //     avgTime += requestProcessing.waitingTime();

        // for (var request : requestsQueued)
        //     avgTime += totalTime - request.arrivalTime;

        return avgTime / totalRequestsHandled();
    }

    private float statLambda() {
        return (float) totalRequestsCome() / totalTime;
    }

    private float statMu() {
        return (float) totalRequestsHandled() / totalTime;
    }

    private int totalRequestsCome() {
        int res = requestsQueued.size() + requestsProcessed.size();
        if (requestProcessing != null)
            res += 1;
        return res;
    }

    private int totalRequestsHandled() {
        return requestsProcessed.size();
    }
}
