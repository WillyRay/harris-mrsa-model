package processes

import org.apache.commons.math3.distribution.LogNormalDistribution

import containers.Hospital
import repast.simphony.engine.schedule.ISchedulableAction
import repast.simphony.engine.schedule.Schedule
import repast.simphony.engine.schedule.ScheduleParameters

class Admission extends Process{
    Hospital target
    ScheduleParameters schedParams
    double nextEventTime
    ISchedulableAction nextAction
    static int totalAdmissionsAttempted
    double meanObservedIntraEventTime



    Admission(double meanIet, Hospital hosp) {
	super(meanIet)
	this.target = hosp
	
    }

    @Override
    public void start() {

	if (totalAdmissionsAttempted > 99) {
	    return
	}
	nextEventTime = this.getNextEventTime();

	schedParams = ScheduleParameters.createOneTime(nextEventTime)
	nextAction = schedule.schedule(schedParams, this, "fire")
    }

    @Override
    public void fire() {
	totalAdmissionsAttempted++
	System.out.println(schedule.getTickCount() + ": Admit A Patient")
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
