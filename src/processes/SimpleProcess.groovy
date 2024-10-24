package processes

import processes.Process
import repast.simphony.engine.schedule.ScheduleParameters

class SimpleProcess extends Process {
	Boolean stop = false

	public SimpleProcess(double intra_event_time) {
		super(intra_event_time);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() {
		def eventTime = this.getNextEventTime()
		ScheduleParameters params= ScheduleParameters.createOneTime(eventTime)
		schedule.schedule(params, this, "fire")
		System.out.println(eventTime)
		return null;
	}

	@Override
	public void fire() {
		System.out.print("firing!")
		if (!this.stop) {
			start()
		}
	}

	@Override
	public void stop() {
				this.stop = true
	}
}
