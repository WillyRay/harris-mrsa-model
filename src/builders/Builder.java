package builders;

import agents.Agent;
import processes.SimpleProcess;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;


public class Builder implements ContextBuilder<Object> {
	
	ISchedule schedule ;
	

	@Override
	public Context<Object> build(Context<Object> context) {
		schedule = repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule();
		
		Agent a = new Agent();
		SimpleProcess sp = new processes.SimpleProcess(1.0);
		sp.start();
		
		ScheduleParameters params = ScheduleParameters.createOneTime(10);
		schedule.schedule(params, sp, "stop");
		
		
				
		
		
		return context;
	}

}
