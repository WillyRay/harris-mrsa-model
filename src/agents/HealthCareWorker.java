package agents;

import containers.Hospital;
import agents.Patient;

public class HealthCareWorker extends Agent {

    private boolean contaminated;
    public HcwType TYPE;
    public Hospital hospital;
     
    public HealthCareWorker(HcwType hcwtype, Hospital hospital) {
	super();
    	this.TYPE = hcwtype;
	this.hospital = hospital;
    }
    
    
    public void makeAVisit() {
	if (hospital.getPatientCount() > 0) {
	    Object po = hospital.getRandomObjects(agents.Patient.class, 1).iterator().next();
	    
	    Patient p = (Patient) po;
		  //   Patient p = (Patient) ((Object) hospital.getRandomObjects(agents.Patient.class, 1)).first();
	    //System.out.println(this.toString() +  " Visits Patient " + p.getAgentId());
	}
    }
    
    @Override
    public String toString() {
	return "Agent " + this.getAgentId() + " " +  TYPE.toString();

    }
}
