package processes.disease;

import agents.HealthCareWorker;
import agents.Patient;

public class AgentDisease {
    private DiseaseStates diseaseState;
    private Patient patient;
    private boolean imported;
    private double dateColonized;
    private HealthCareWorker colonizedBy;
    private double dateInfected;
    private double dateRecovered;
    private double dateDied;
    
    
    
    public AgentDisease(Patient patient) {
	this.patient = patient;
	this.diseaseState = DiseaseStates.SUSCEPTIBLE;
    }
    
    public void start() {
	
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
