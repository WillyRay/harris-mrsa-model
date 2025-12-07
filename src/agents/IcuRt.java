package agents;

import java.util.List;
import java.util.stream.Collectors;

import containers.Hospital;
import repast.simphony.space.graph.Network;

public class IcuRt extends HealthCareWorker {

    public IcuRt(HcwType hcwtype, Hospital hospital) {
	super(hcwtype, hospital);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void makeAVisit() {
	//get the hospital net from the hospital container
	Network hospitalNet = super.hospital.getHospitalnet();
	//get a random patient from the ICU context
	
	   
	    //get a random patient from the ICU context
	    List<Patient> po = hospital.inIcu;
	    if (po.isEmpty()) return;
	    
	    Patient p = (Patient) utils.Chooser.chooseOne(po);
	    super.hospital.visitData.append(this.getAgentId() + "," + this.TYPE.toString() + "," + p.getAgentId() + "," + p.getCurrentLocation() + "," + utils.TimeUtils.getSchedule().getTickCount() + "\n");
	
    
    
    
    }
}