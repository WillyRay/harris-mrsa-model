package agents;

import java.util.ArrayList;

import containers.Hospital;
import utils.Chooser;
import utils.TimeUtils;

public class Therapist extends HealthCareWorker {

    private HcwType therapistType;
    private ArrayList<Patient> needsArray;
    
    
    public Therapist(HcwType hcwtype, Hospital hospital, double hhPre, double hhPost, double ppe) {
	super(hcwtype, hospital, hhPre,  hhPost,  ppe);
	this.therapistType = hcwtype;
    }
    
    public void setNeedsArray(ArrayList<Patient> needs) {
	this.needsArray = needs;
    }
    
    public void makeAVisit() {
	if (needsArray.size() > 0) {
	   Patient p = (Patient)Chooser.chooseOne(needsArray);
	   needsArray.remove(p);
	   boolean checkVisit = super.checkVisitForTransmission(p);
	   String row = this.getAgentId() + "," + this.TYPE.toString() + "," + this.isContaminated() + "," + p.getAgentId() + "," + p.getDiseaseState()  + "," + p.getCurrentLocation() + "," + TimeUtils.getSchedule().getTickCount() + '\n';	   hospital.visitData.append(row);
	}
    }
}
