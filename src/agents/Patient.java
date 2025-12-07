package agents;

import processes.disease.AgentDisease;
import processes.disease.DiseaseStates;

public class Patient extends Agent {
    
    private double admitTime, dischargeTime, transferTime;
    private String admitLocation, dischargeLocation,currentLocation;
    private boolean needsRt, needsOt, needsPt; 
    private AgentDisease agentDisease;
    
    public Patient() {
	super();
	this.agentDisease = new AgentDisease(this);	
    }
    
    public DiseaseStates getDiseaseState() {
	return this.agentDisease.getDiseaseState();
    }

    public double getAdmitTime() {
        return admitTime;
    }

    public void setAdmitTime(double admitTime) {
        this.admitTime = admitTime;
    }

    public double getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(double dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public double getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(double transferTime) {
        this.transferTime = transferTime;
    }

    public String getAdmitLocation() {
        return admitLocation;
    }

    public void setAdmitLocation(String admitLocation) {
        this.admitLocation = admitLocation;
    }

    public String getDischargeLocation() {
        return dischargeLocation;
    }

    public void setDischargeLocation(String dischargeLocation) {
        this.dischargeLocation = dischargeLocation;
    }

    public boolean isNeedsRt() {
        return needsRt;
    }

    public void setNeedsRt(boolean needsRt) {
        this.needsRt = needsRt;
    }

    public boolean isNeedsOt() {
        return needsOt;
    }

    public void setNeedsOt(boolean needsOt) {
        this.needsOt = needsOt;
    }

    public boolean isNeedsPt() {
        return needsPt;
    }

    public void setNeedsPt(boolean needsPt) {
        this.needsPt = needsPt;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public AgentDisease getAgentDisease() {
	return this.agentDisease;
    }
    
    
    
}
