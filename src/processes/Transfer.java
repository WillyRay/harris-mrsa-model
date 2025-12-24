package processes;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import agents.Patient;
import containers.Hospital;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;

public class Transfer extends Process{
    Hospital target;
    Patient patient;
    LogNormalDistribution dist;
    ScheduleParameters schedParams;
    double shape,scale;
    double nextEventTime;
    ISchedulableAction nextAction;;
    double meanObservedIntraEventTime;



    public Transfer(double scale, double shape, Hospital hosp) {
	super();
	this.target = hosp;
	this.meanIntraEventTime = 0;
	this.shape = shape;
	this.scale = scale;
	dist = new LogNormalDistribution(scale, shape);
	
    }
    
    public void scheduleTransfer(Patient p) {
	nextEventTime = getNextEventTime();
	schedParams = ScheduleParameters.createOneTime(nextEventTime);
	nextAction = schedule.schedule(schedParams, target, "transferPatient", p);
	
    }

    @Override
    public void start() {

	
    }

    @Override
    public void fire() {

    }

    @Override
    public void stop() {
	
    }

    public double getNextEventTime(){
	double currTime = schedule.getTickCount();
	double elapse = dist.sample();
	return Math.max(currTime+elapse,0);
    }
    
   
}
