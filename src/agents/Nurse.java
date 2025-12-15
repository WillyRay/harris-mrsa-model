package agents;

import containers.Hospital;
import repast.simphony.space.graph.Network;

public class Nurse extends HealthCareWorker {
    

    
    public Nurse(HcwType hcwtype, Hospital hospital, double hhPre, double hhPost, double ppe) {
	super(hcwtype, hospital,  hhPre,  hhPost,  ppe);
	// TODO Auto-generated constructor stub
    }
    
    @Override
    public void makeAVisit() {
	//get the hospital net from the hospital container
	Network hospitalNet = super.hospital.getHospitalnet();
	//get a random patient from the hospital network that's connected to this nurse
	if (hospitalNet.getDegree(this) > 0) {
	    Object po = hospitalNet.getRandomAdjacent(this);
	    Patient p = (Patient) po;
	    boolean checkVisit = super.checkVisitForTransmission(p);
	    super.hospital.visitData.append(this.getAgentId() + "," + this.TYPE.toString() + "," + this.isContaminated() + "," + p.getAgentId() + "," + p.getDiseaseState() + "," + p.getCurrentLocation() + "," + utils.TimeUtils.getSchedule().getTickCount() + "\n");
	
    }
    }

}
