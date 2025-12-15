package dataclass;

import agents.Agent;

public class VisitData {
    private double time;
    private Agent from;
    private Agent to;
    private boolean transmissionOccurred;
    
    
    public double getTime() {
        return time;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public Agent getFrom() {
        return from;
    }
    public void setFrom(Agent from) {
        this.from = from;
    }
    public Agent getTo() {
        return to;
    }
    public void setTo(Agent to) {
        this.to = to;
    }
    public boolean isTransmissionOccurred() {
        return transmissionOccurred;
    }
    public void setTransmissionOccurred(boolean transmissionOccurred) {
        this.transmissionOccurred = transmissionOccurred;
    }
}
