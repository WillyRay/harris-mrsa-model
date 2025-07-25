package agents;

import java.util.ArrayList;

import containers.Hospital;
import utils.Chooser;

public class Therapist extends HealthCareWorker {

    private HcwType therapistType;
    private ArrayList<Patient> needsArray;
    
    
    public Therapist(HcwType hcwtype, Hospital hospital) {
	super(hcwtype, hospital);
	therapistType = hcwtype;
    }
    
    public void setNeedsArray(ArrayList<Patient> needs) {
	this.needsArray = needs;
    }
    
    public void makeAVisit() {
	if (needsArray.size() > 0) {
	   Patient p = (Patient)Chooser.chooseOne(needsArray);
	   needsArray.remove(p);
	   System.out.println(this.toString() +  " Visits Patient " + p.getAgentId());
	}
    }

}
