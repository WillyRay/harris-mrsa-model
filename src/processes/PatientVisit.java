package processes

import org.apache.commons.math3.distribution.LogNormalDistribution

import agents.HealthCareWorker
import containers.Hospital
import repast.simphony.engine.schedule.ISchedulableAction
import repast.simphony.engine.schedule.Schedule
import repast.simphony.engine.schedule.ScheduleParameters

class PatientVisit extends Process{
    Hospital target
    ScheduleParameters schedParams
    double nextEventTime
    ISchedulableAction nextAction
    HealthCareWorker hcw
    double meanObservedIntraEventTime



    PatientVisit(double meanIet, Hospital hosp, HealthCareWorker hcw) {
	super(meanIet)
	this.target = hosp
	this.hcw = hcw
    }

    @Override
    public void start() {

	
	nextEventTime = this.getNextEventTime();
	schedParams = ScheduleParameters.createOneTime(nextEventTime)
	nextAction = schedule.schedule(schedParams, this, "fire")
    }

    @Override
    public void fire() {
	hcw.makeAVisit();
	start()
    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub
	return null;
    }

    double getNextEventTime(){
	double currTime = schedule.getTickCount()
	double elapse = distro.sample()
	return Math.max(currTime+elapse,0)
    }
    
    void testDistribution() {
	def totalVal = 0
	for (def i=0; i<1000; i++) {
	    def value = distro.sample()
	    totalVal += value
	    System.out.println(value)  
	}
	System.out.println(totalVal/1000)
    }
    
}
