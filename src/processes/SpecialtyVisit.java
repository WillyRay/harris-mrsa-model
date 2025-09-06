package processes;

import agents.HealthCareWorker;
import containers.Hospital;

public class SpecialtyVisit extends PatientVisit {
    

    public SpecialtyVisit(double meanIet, Hospital hosp, HealthCareWorker hcw) {
	super(meanIet, hosp, hcw);
	
		
    }

}
