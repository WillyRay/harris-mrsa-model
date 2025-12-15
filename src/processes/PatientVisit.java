package processes;

import org.apache.commons.math3.distribution.LogNormalDistribution;

import agents.HcwType;
import agents.HealthCareWorker;
import cern.jet.random.Gamma;
import containers.Hospital;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import utils.TimeUtils;

public class PatientVisit extends Process{
    private Hospital target;
    ScheduleParameters schedParams;
    double nextEventTime;
    ISchedulableAction nextAction;
    HealthCareWorker hcw;
    double meanObservedIntraEventTime;
    HcwType hcwType;
    
 



    public PatientVisit(double meanIet, Hospital hosp, HealthCareWorker hcw) {
	super(meanIet);
	this.target = hosp;
	this.hcw = hcw;
	this.hcwType = hcw.getTYPE();
    }

    @Override
    public void start() {

	
	nextEventTime = this.getNextEventTime();
	schedParams = ScheduleParameters.createOneTime(nextEventTime);
	nextAction = schedule.schedule(schedParams, this, "fire");
    }

    @Override
    public void fire() {
	hcw.makeAVisit();
	start();
    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub
	return; 
    }

    public double getNextEventTime(){
	double currTime = schedule.getTickCount();
	
	double elapse = distro.sample()*TimeUtils.MINUTE + 6.6*TimeUtils.MINUTE;
	return Math.max(currTime+elapse,0);
    }
    
    public void testDistribution() {
	int totalVal = 0;
	for (int i=0; i<1000; i++) {
	    double value = distro.sample();
	    totalVal += value;
	    System.out.println(value) ;
	}
	System.out.println(totalVal/1000);
    }

    public Hospital getTarget() {
        return target;
    }
    
}
