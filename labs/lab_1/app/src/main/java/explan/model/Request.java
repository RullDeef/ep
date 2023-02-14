package explan.model;

public class Request {
    public final int id;
    public final float arrivalTime;

    public float leaveQueueTime = 0.0f;
    public float leaveSystemTime = 0.0f;

    public Request(int id, float arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public float waitingTime() {
        return leaveQueueTime - arrivalTime;
    }
}
