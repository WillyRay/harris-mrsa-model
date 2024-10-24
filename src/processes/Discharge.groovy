package processes

import org.apache.commons.math3.distribution.LogNormalDistribution
import agents.Patient
import containers.Hospital
import repast.simphony.engine.schedule.ISchedulableAction
import repast.simphony.engine.schedule.Schedule
import repast.simphony.engine.schedule.ScheduleParameters

class Discharge extends Process{
    Hospital target
    Patient patient
    LogNormalDistribution dist
    ScheduleParameters schedParams
    double shape,scale
    double nextEventTime
    ISchedulableAction nextAction
    static int totalAdmissionsAttempted
    double meanObservedIntraEventTime



    Discharge(double scale, double shape, Hospital hosp, Patient p) {
	super()
	this.target = hosp
	this.meanIntraEventTime = 0
	this.shape = shape
	this.scale = scale
	dist = new LogNormalDistribution(scale, shape)
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

    double getNextEventTime(){
	double currTime = schedule.getTickCount()
	double elapse = dist.sample()
	return Math.max(currTime+elapse,0)
    }
    
    void testDistribution() {
	def totalVal = 0
	for (def i=0; i<1000; i++) {
	    def value = dist.sample()
	    totalVal += value
	    System.out.println(value)  
	}
	System.out.println(totalVal/1000)
    }
    
}
