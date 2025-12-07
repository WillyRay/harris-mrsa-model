package agents;

import containers.Hospital;
import processes.disease.DiseaseStates;
import utils.Chooser;
import utils.TimeUtils;
import agents.Patient;

public class HealthCareWorker extends Agent {

    private boolean contaminated;
    public HcwType TYPE;
    public Hospital hospital;
    public boolean icu;
    private double handHygieneCompliance;
    private double handHygieneCompliancePost;
    private double gloveCompliance;
     
    public HealthCareWorker(HcwType hcwtype, Hospital hospital) {
	super();
    	this.TYPE = hcwtype;
	this.hospital = hospital;
	this.setContaminated(false);
    }
    
    public boolean visitCheck(Patient p) {
	boolean checkTransmission = checkVisitForTransmission(p);
	if (!checkTransmission) {
	    return false;
	}
	return true;
    }
    
    public boolean checkVisitForTransmission(Patient p) {
	 boolean checkTransmission = false;
	    boolean ptTransmiss = false;
	    if (p.getDiseaseState() == DiseaseStates.COLONIZED || p.getDiseaseState() == DiseaseStates.INFECTED) {
		ptTransmiss = true;		
	    }
	    if (!this.isContaminated() && !ptTransmiss) {
		return false;
	    } else if (this.isContaminated() && !ptTransmiss) {
		
		checkTransmission = true;
		return checkTransmissionToHcw(p);
	    } else if (!this.isContaminated() && ptTransmiss) {
		// check visit for transmission pt->HCW
		checkTransmission = true;
		return checkTransmissionToPatient(p);
	    } else if (this.isContaminated() && ptTransmiss) {
		return false;
	    }
	    
	
	return false;
    }
    
    
    
    private boolean checkTransmissionToPatient(Patient p) {
	if (Chooser.randomTrue(this.handHygieneCompliance)) {
	    return true;
	}
	return false;
    } 
    

    private boolean checkTransmissionToHcw(Patient p) {
	return false;
    }

    public void makeAVisit() {
	if (hospital.getPatientCount() > 0) {
	    Object po = hospital.getRandomObjects(agents.Patient.class, 1).iterator().next();
	    Patient p = (Patient) po;
	    boolean transmission = false;
	    if (checkVisitForTransmission(p)) {
		checkTransmission(p);
	    }
	    hospital.visitData.append(this.getAgentId() + "," + this.TYPE.toString() + "," + this.isContaminated() + "," + p.getAgentId() + "," +p.getDiseaseState() + "," + p.getCurrentLocation() + "," + TimeUtils.getSchedule().getTickCount() + "\n");
	}
    }
    

    private void checkTransmission(Patient p) {
	
	
    }

    @Override
    public String toString() {
	return "Agent " + this.getAgentId() + " " +  TYPE.toString();

    }


    public HcwType getTYPE() {
        return TYPE;
    }


    public void setTYPE(HcwType tYPE) {
        TYPE = tYPE;
    }


    public boolean isContaminated() {
	return contaminated;
    }


    public void setContaminated(boolean contaminated) {
	this.contaminated = contaminated;
    }


    public double getHandHygieneCompliance() {
	return handHygieneCompliance;
    }


    public void setHandHygieneCompliance(double handHygieneCompliance) {
	this.handHygieneCompliance = handHygieneCompliance;
    }


    public double getGloveCompliance() {
	return gloveCompliance;
    }


    public void setGloveCompliance(double gloveCompliance) {
	this.gloveCompliance = gloveCompliance;
    }


    public double getHandHygieneCompliancePost() {
        return handHygieneCompliancePost;
    }


    public void setHandHygieneCompliancePost(double handHygieneCompliancePost) {
        this.handHygieneCompliancePost = handHygieneCompliancePost;
    }
    
    
    
    
}
