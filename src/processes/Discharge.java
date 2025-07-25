package processes;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import agents.Patient;
import containers.Hospital;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import utils.TimeUtils;

public class Discharge extends Process{
    Hospital target;
    Patient patient;
    LogNormalDistribution dist;
    ScheduleParameters schedParams;
    double shape,scale;
    double nextEventTime;
    ISchedulableAction nextAction;
    static int totalAdmissionsAttempted;
    double meanObservedIntraEventTime;




    public Discharge(double scale, double shape, Hospital hosp) {
	super(0.0);
	this.target = hosp;
	this.meanIntraEventTime = 0;
	this.shape = shape;
	this.scale = scale;
	dist = new LogNormalDistribution(scale, shape);
	
    }
    
    public void scheduleDischarge(Patient p) {
	nextEventTime = getNextEventTime();
	schedParams = ScheduleParameters.createOneTime(nextEventTime);
	nextAction = TimeUtils.getSchedule().schedule(schedParams, target, "dischargePatient", p);
	
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
	double currTime = TimeUtils.getSchedule().getTickCount();
	double elapse = dist.sample();
	return Math.max(currTime+elapse,0);
    }
    
    public void testDistribution() {
	int totalVal = 0;
	for (int i=0; i<1000; i++) {
	    double value = dist.sample();
	    totalVal += value;
	    System.out.println(value);
	}
	System.out.println(totalVal/1000);
    }
    
}
