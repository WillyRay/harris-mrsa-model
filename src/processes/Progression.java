package processes;

import org.apache.commons.math3.distribution.WeibullDistribution;

import agents.Patient;
import containers.Hospital;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.ScheduleParameters;
import utils.TimeUtils;

public class Progression extends Process {
    private WeibullDistribution dist;
    private double shape,scale, nextEventTime;
    private ISchedulableAction nextAction;
    private ScheduleParameters schedParams;
    private Patient patient;
    private Hospital hospital;
    
    public Progression(double shape, double scale, Patient p) {
	super(0.0);

	this.patient = p;
	this.shape = shape;
	this.scale = scale;
	dist = new WeibullDistribution(shape, scale);
	super.setDistro(dist);
	start();
	
	
    }
    	
    @Override
    void start() {
	nextEventTime = getNextEventTime();
	schedParams = ScheduleParameters.createOneTime(nextEventTime);
	nextAction = TimeUtils.getSchedule().schedule(schedParams, this, "fire");
	
	
    }

    @Override
    public void fire() {
	patient.getAgentDisease().doProgression();
	
    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub
	
    }
  
   
}
