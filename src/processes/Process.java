package processes;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

import agents.Agent;
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.ISchedule;
import java.lang.Math;


abstract class Process extends Agent{


	protected ISchedule schedule;
	protected double meanIntraEventTime;
	double nextEventTime;
	protected RealDistribution distro;
	ISchedulableAction nextAction;
	
	

	Process(double intra_event_time){
	    	super();
		if (intra_event_time > 0) {
			schedule = repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule();
			meanIntraEventTime = intra_event_time;
			distro = new ExponentialDistribution(intra_event_time);
		}
	}
	
	Process(){
	    this.schedule = repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule();
	}
	


	abstract void start();
	
	abstract void fire();

	abstract void stop() ;

	double getNextEventTime(){
		double currTime = schedule.getTickCount();
		double elapse = distro.sample();
		return Math.max(currTime+elapse,0);
	}

	public RealDistribution getDistro() {
	    return distro;
	}

	public void setDistro(RealDistribution distro) {
	    this.distro = distro;
	}
}
