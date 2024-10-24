package builders;



import containers.Hospital;
import processes.Admission;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.schedule.ISchedule;



public class Builder implements ContextBuilder<Object> {
	
	ISchedule schedule ;
	Hospital hospital;
	Admission admissionProcess;
	
	// parameters
	int hospitalCapacity = 90;
	double admissionsRate = 0.2; //mean intra_event time
	double dischargeShape = 1.3;
	double dischargeScale = 1.0;

	

	@Override
	public Context<Object> build(Context<Object> context) {
		schedule = repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule();
		
		//WRR: Create a hospital with 90 beds.
		hospital = new Hospital(hospitalCapacity);
		admissionProcess = new Admission(admissionsRate, hospital);
		admissionProcess.start();
			
				
		
		
		return context;
	}

}
