package agents;

import containers.Hospital;
import repast.simphony.space.graph.Network;

public class Doctor extends HealthCareWorker {

    public Doctor(HcwType hcwtype, Hospital hospital) {
	super(hcwtype, hospital);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void makeAVisit() {
	//get the hospital net from the hospital container
	Network hospitalNet = super.hospital.getHospitalnet();
	//get a random patient from the hospital network that's connected to this doctor
	if (hospitalNet.getDegree(this) > 0) {
	    Object po = hospitalNet.getRandomAdjacent(this);
	    Patient p = (Patient) po;
	    super.hospital.visitData.append(this.getAgentId() + "," + this.TYPE.toString() + "," + p.getAgentId() + "," + p.getCurrentLocation() + "," + utils.TimeUtils.getSchedule().getTickCount() + "\n");
	
    }
    
    
    }
}
