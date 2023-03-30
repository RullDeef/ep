package explan.model.experiment;

import java.util.Deque;
import java.util.List;

public class SimulationResult {
    public double totalTime;
    public Deque<Request> requestsQueued;
    public List<Request> requestsProcessed;

    public Request requestProcessing;
    public double workerIdleTime;

    public SimulationResult(
            double totalTime,
            Deque<Request> queue,
            List<Request> processed,
            Request processing,
            double workerIdle) {
        this.totalTime = totalTime;
        this.requestsQueued = queue;
        this.requestsProcessed = processed;
        this.requestProcessing = processing;
        this.workerIdleTime = workerIdle;
    }

    public double statRho() {
        // return avgWaitTime() / totalTime;
        return statLambda() / statMu();
    }

    public double avgWaitTime() {
        double avgTime = 0.0;

        for (var request : requestsProcessed)
            avgTime += request.waitingTime();

        // if (requestProcessing != null)
        //     avgTime += requestProcessing.waitingTime();

        // for (var request : requestsQueued)
        //     avgTime += totalTime - request.arrivalTime;

        return avgTime / totalRequestsHandled();
    }

    private double statLambda() {
        return (double) totalRequestsCome() / totalTime;
    }

    private double statMu() {
        return (double) totalRequestsHandled() / totalTime;
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
