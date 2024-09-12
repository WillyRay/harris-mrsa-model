package processes

import org.apache.commons.math3.distribution.ExponentialDistribution;

import agents.Agent
import repast.simphony.engine.schedule.ISchedulableAction;
import repast.simphony.engine.schedule.ISchedule;

abstract class Process extends Agent{
	ISchedule schedule
	double meanIntraEventTime
	double nextEventTime
	ExponentialDistribution distro
	ISchedulableAction nextAdmissionAction

	Process(double intra_event_time){

	if (intra_event_time > 0) {
		schedule = repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule();
		meanIntraEventTime = intra
		distro = new ExponentialDistribution(intra)
	}
	}

	abstract def start()

	abstract def stop()

	double getNextEventTime(){
	double currTime = schedule.getTickCount()
	double elapse = distro.sample()
	return currTime+elapse
	}
}
