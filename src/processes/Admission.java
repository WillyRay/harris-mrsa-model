package processes;

import org.apache.commons.math3.distribution.LogNormalDistribution;

import containers.Hospital;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;

public class Admission extends Process{

    Hospital target;
    ScheduleParameters schedParams;
    double nextEventTime;
    ISchedulableAction nextAction;
    static int totalAdmissionsAttempted;
    double meanObservedIntraEventTime;



    public Admission(double meanIet, Hospital hosp) {
	super(meanIet);
	this.target = hosp;
	
    }

    @Override
    public void start() {

	
	nextEventTime = this.getNextEventTime();
	schedParams = ScheduleParameters.createOneTime(nextEventTime);
	nextAction = schedule.schedule(schedParams, this, "fire");
    }

    @Override
    public void fire() {
	totalAdmissionsAttempted++;
	this.target.createAndAdmitPatient();
	start();
    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub
	return;
    }

    public double getNextEventTime(){
	double currTime = schedule.getTickCount();
	double elapse = distro.sample();
	return Math.max(currTime+elapse,0);
    }
    
    public void testDistribution() {
	int totalVal = 0;
	for  ( int i=0; i<1000; i++) {
	    double value = distro.sample();
	    totalVal += value;
	    System.out.println(value);  
	}
	System.out.println(totalVal/1000);
    }
    
}
