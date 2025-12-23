package processes.disease;

import agents.HealthCareWorker;
import agents.Patient;
import builders.Builder;
import processes.Progression;
import repast.simphony.engine.schedule.ISchedulableAction;
import utils.TimeUtils;

public class AgentDisease {
    private DiseaseStates diseaseState;
    private Patient patient;
    private boolean imported;
    private double dateColonized;
    private HealthCareWorker colonizedBy;
    private double dateInfected;
    private double dateRecovered;
    private double dateDied;
    private Progression progressionAction;
    private Builder builder;

    public AgentDisease(Patient patient) {
	this.patient = patient;
	this.diseaseState = DiseaseStates.SUSCEPTIBLE;
    }
    
    public void start() {
	
    }
    
    public void doProgression() {
	if (this.diseaseState == DiseaseStates.COLONIZED) {
	    this.diseaseState = DiseaseStates.INFECTED;
	    this.dateInfected = 0.0; 
	   // System.out.println(TimeUtils.getSchedule().getTickCount() +
	//	    ","	+ patient.toString() + ", PROGRESSION" );
	    builder.getInstance().totalInfections++;
	    
	}
    }



    public DiseaseStates getDiseaseState() {
        return diseaseState;
    }



    public void setDiseaseState(DiseaseStates diseaseState) {
        this.diseaseState = diseaseState;
    }



    public Patient getPatient() {
        return patient;
    }



    public void setPatient(Patient patient) {
        this.patient = patient;
    }



    public boolean isImported() {
        return imported;
    }



    public void setImported(boolean imported) {
        this.imported = imported;
    }



    public double getDateColonized() {
        return dateColonized;
    }



    public void setDateColonized(double dateColonized) {
        this.dateColonized = dateColonized;
    }



    public HealthCareWorker getColonizedBy() {
        return colonizedBy;
    }



    public void setColonizedBy(HealthCareWorker colonizedBy) {
        this.colonizedBy = colonizedBy;
        double shape = 0;
        double scale = 0;
        if (patient.getCurrentLocation() == "ICU") {
           shape = 1.5;
           scale = 5.6;
        } else {
            shape = 1.5;
	    scale = 8;
        }
        this.progressionAction = new Progression(shape, scale, this.patient);
        
    }



    public double getDateInfected() {
        return dateInfected;
    }



    public void setDateInfected(double dateInfected) {
        this.dateInfected = dateInfected;
    }



    public double getDateRecovered() {
        return dateRecovered;
    }



    public void setDateRecovered(double dateRecovered) {
        this.dateRecovered = dateRecovered;
    }



    public double getDateDied() {
        return dateDied;
    }



    public void setDateDied(double dateDied) {
        this.dateDied = dateDied;
    }
}
