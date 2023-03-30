package explan.model.experiment;

public class Request {
    public final int id;
    public final double arrivalTime;

    public double leaveQueueTime = 0.0f;
    public double leaveSystemTime = 0.0f;

    public Request(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public double waitingTime() {
        return leaveQueueTime - arrivalTime;
    }
}
