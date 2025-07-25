package agents;

public class DischargedPatient {
    
    private double admitTime, dischargeTime, transferTime;
    private boolean imported, icu;
    private int agentId;
    private String dischargeLocation, admitLocation;
    
    
    
    
    
    public DischargedPatient(int agentId, double admitTime, double dischargeTime, boolean imported, boolean icu, double d, String admitLocation, String dischargeLocation) {
	super();
	this.agentId = agentId;
	this.admitTime = admitTime;
	this.dischargeTime = dischargeTime;
	this.imported = imported;
	this.icu = icu;
	this.transferTime = d;
	if (transferTime <= 0)
	    this.transferTime = -1;
	this.admitLocation = admitLocation;
	this.dischargeLocation = dischargeLocation;
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
		+ icu + "," + transferTime + "," + admitLocation + "," + dischargeLocation;
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
