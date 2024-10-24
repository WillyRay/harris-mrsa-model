package containers

import agents.Agent
import agents.HealthCareWorker
import agents.Patient

class Hospital {
    ArrayList<Patient> patients;
    ArrayList<HealthCareWorker> hcws;
    int bedCount
   
    
     public Hospital(int bedCount) {
	super();
	this.patients = new ArrayList<Patient>()
	this.hcws = new ArrayList<HealthCareWorker>()
	this.bedCount = bedCount
    }
    
    
    public createAndAdmitPatient() {
	
    }
    
    
    
}
