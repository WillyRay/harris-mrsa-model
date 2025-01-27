package agents

import containers.Hospital
import agents.Patient

class HealthCareWorker extends Agent {

    private boolean contaminated;
    public HcwType TYPE;
    public Hospital hospital;
     
    public HealthCareWorker(HcwType hcwtype, Hospital hospital) {
	super();
    	this.TYPE = hcwtype;
	this.hospital = hospital;
    }
    
    
    public void makeAVisit() {
	Patient p = (Patient)hospital.getRandomObjects(agents.Patient, 1).first()
	System.out.println(this.toString() +  " Visits Patient " + p.getAgentId())
    }
    
    @Override
    public String toString() {
	return "Agent " + this.getAgentId() + " " +  TYPE.toString()

    }
}
