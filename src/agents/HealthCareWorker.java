package agents;

import containers.Hospital;
import utils.TimeUtils;
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
	    hospital.visitData.append(this.getAgentId() + "," + this.TYPE.toString() + "," + p.getAgentId() + "," + p.getCurrentLocation() + "," + TimeUtils.getSchedule().getTickCount() + "\n");
	}
    }
    
    @Override
    public String toString() {
	return "Agent " + this.getAgentId() + " " +  TYPE.toString();

    }
    
    
}
