package agents;

import containers.Hospital;
import processes.disease.DiseaseStates;
import repast.simphony.parameter.Parameters;
import utils.Chooser;
import utils.TimeUtils;
import agents.Patient;
import dataclass.VisitData;


public class HealthCareWorker extends Agent {
    

    private Parameters params = repast.simphony.engine.environment.RunEnvironment.getInstance().getParameters();
    private boolean contaminated;
    public HcwType TYPE;
    public Hospital hospital;
    public boolean icu;
    private double handHygieneCompliance;
    private double handHygieneCompliancePost;
    private double gloveCompliance;

    public HealthCareWorker(HcwType hcwtype, Hospital hospital, double hhPre, double hhPost, double ppe) {
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
    
    public void handHygieneCheckPre() {
	if (!this.isContaminated()) {
	    return ;
	}
	if (Chooser.randomTrue(this.handHygieneCompliance)) {
	    if (Chooser.randomTrue(params.getDouble("hand_hygiene_efficacy"))) {
		this.setContaminated(false);
		return ;
	    }
	}
	
    }
    
    public void handHygieneCheckPost() {
	if (!this.isContaminated()) {
	    return ;
	}
	if (Chooser.randomTrue(this.handHygieneCompliancePost)) {
	    if (Chooser.randomTrue(params.getDouble("hand_hygiene_efficacy"))) {
		this.setContaminated(false);
		return ;
	    }
	}
	
    }
    
    
    public boolean checkVisitForTransmission(Patient p) {
	 boolean checkTransmission = false;
	    boolean ptTransmiss = false;
	    if (p.getDiseaseState() == DiseaseStates.COLONIZED 
		    || p.getDiseaseState() == DiseaseStates.INFECTED) {
		
		ptTransmiss = true;	
		
	    }
	    if (!this.isContaminated() && !ptTransmiss) {
		return false;
		
	    } else if (this.isContaminated() && !ptTransmiss) {
		// hcw contaminated, patient uninfected, check visit for transmission HCW->pt
		handHygieneCheckPre();
		// TODO check if isolation is in place, if so, use GG
		if (!this.isContaminated()) {
		    return false;
		}
		boolean transmission = Chooser.randomTrue(params.getDouble("transmission_probability_hcw_to_patient"));
		if (transmission) {
		    p.getAgentDisease().setDiseaseState(DiseaseStates.COLONIZED);
		    p.getAgentDisease().setDateColonized(TimeUtils.getSchedule().getTickCount());
		    p.getAgentDisease().setColonizedBy(this);
		    // Set Patient fields for discharge tracking
		    p.setColonizedTime(TimeUtils.getSchedule().getTickCount());
		}
		//"time,patientId,hcwId,hcwType,location
		hospital.tranmissionData.append(TimeUtils.getSchedule().getTickCount() + "," + p.getAgentId() + "," + this.getAgentId() + "," + this.TYPE.toString() + "," + p.getCurrentLocation() + "\n");
		
		handHygieneCheckPost();
		return true;
	    }
		
	    else if (!this.isContaminated() && ptTransmiss) {
		//hcw uninfected, patient infected, check visit for transmission pt->HCW
		if (Chooser.randomTrue(params.getDouble("transmission_probability_patient_to_hcw"))) {
		    this.setContaminated(true);
		    //handHygieneCheckPost();
		    if (this.isContaminated()) {
			if (Chooser.randomTrue(this.gloveCompliance)) {
			    if (Chooser.randomTrue(params.getDouble("glove_efficacy"))) {
				this.setContaminated(false);
				return false;
			    }
		    }
			//TODO move hhcheckpost below gg
		    this.setContaminated(true);
		    }
		    return true;
		
		
	    } else if (this.isContaminated() && ptTransmiss) {
		return false;
	    }
	    
	
	
	    }
	    return false;
    }
    
    
    
  
    

    public void makeAVisit() {
	if (hospital.getPatientCount() > 0) {
	    VisitData vd = new VisitData();
	    vd.setTime(TimeUtils.getSchedule().getTickCount());
	    
	    Object po = hospital.getRandomObjects(agents.Patient.class, 1).iterator().next();
	    Patient p = (Patient) po;
	    boolean transmission = false;
	    transmission = checkVisitForTransmission(p);
	    hospital.visitData.append(this.getAgentId() + "," + this.TYPE.toString() + "," + this.isContaminated() + "," + p.getAgentId() + "," +p.getDiseaseState() + "," + p.getCurrentLocation() + "," + TimeUtils.getSchedule().getTickCount() + "," + transmission + "\n");
	}
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
