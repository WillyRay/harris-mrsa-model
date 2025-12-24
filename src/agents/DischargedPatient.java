package agents;

public class DischargedPatient extends Agent {
    
    private double admitTime, dischargeTime, transferTime;
    private boolean imported, icu;
    private int agentId;
    private String dischargeLocation, admitLocation;
    private double colonizedTime, infectedTime;
    private boolean colonizedOnAdmission, infectedOnAdmission,death;
    
    
    
    
    
    
    public DischargedPatient(double admitTime, double dischargeTime, double transferTime, boolean imported, boolean icu,
	    int agentId, String dischargeLocation, String admitLocation, double colonizedTime, double infectedTime,
	    boolean colonizedOnAdmission, boolean infectedOnAdmission, boolean death) {
	super();
	this.admitTime = admitTime;
	this.dischargeTime = dischargeTime;
	this.transferTime = transferTime;
	this.imported = imported;
	this.icu = icu;
	this.agentId = agentId;
	this.dischargeLocation = dischargeLocation;
	this.admitLocation = admitLocation;
	this.colonizedTime = colonizedTime;
	this.infectedTime = infectedTime;
	this.colonizedOnAdmission = colonizedOnAdmission;
	this.infectedOnAdmission = infectedOnAdmission;
	this.death = death;
    }

    
    //getter  agentId
    
    public int getAgentId() {
	return agentId;
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
    public boolean isImported() {
        return imported;
    }
    public void setImported(boolean imported) {
        this.imported = imported;
    }
    public boolean isIcu() {
        return icu;
    }
    public void setIcu(boolean icu) {
        this.icu = icu;
    }
    
    public double getLos() {
	return this.dischargeTime - this.admitTime;
    }

    @Override
    public String toString() {
	return "" + agentId + "," + admitTime + "," + dischargeTime + ","
		+ icu + "," + transferTime + "," + admitLocation + "," + dischargeLocation + ","
		+ colonizedOnAdmission + "," + colonizedTime + "," + infectedOnAdmission + "," + infectedTime + "," + death;
    }

    public double getTransferTime() {
        return transferTime;
    }


    public void setDischargeLocation(String dischargeLocation) {
        this.dischargeLocation = dischargeLocation;
    }


    public void setAdmitLocation(String admitLocation) {
        this.admitLocation = admitLocation;
    }

}
