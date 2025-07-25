package containers;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

import agents.Agent;
import agents.DischargedPatient;
import agents.HealthCareWorker;
import agents.Patient;
import builders.Builder;
import processes.Discharge;
import processes.Transfer;
import repast.simphony.context.DefaultContext;
import repast.simphony.engine.schedule.ScheduledMethod;
import utils.Chooser;
import utils.TimeUtils;

public class Hospital extends DefaultContext<Object>{
    Builder builder;

    ArrayList<Patient> patients;
    ArrayList<HealthCareWorker> hcws;
    int bedCount;
    ArrayList<Patient> inIcu;
    int icuBedCount;
    ArrayList<Patient> notInIcu;
    int wardBedCount;
    Discharge icuDischarger;
    Discharge nonIcuDischarger;
    Transfer transferer;
    ArrayList<String> patientOutputs;
    ArrayList<DischargedPatient> dischargedPatients;
    
    
    public ArrayList<Patient> patientsNeedingOt;
    public ArrayList<Patient> patientsNeedingRt;
    public ArrayList<Patient> patientsNeedingPt;

    public Hospital(Builder builder, int bedCount, int icuBedCount) {
	super();
	
	this.dischargedPatients = new ArrayList<DischargedPatient>();
	this.patients = new ArrayList<Patient>();
	this.inIcu = new ArrayList<Patient>();
	this.notInIcu = new ArrayList<Patient>();
	this.hcws = new ArrayList<HealthCareWorker>();
	this.bedCount = bedCount;
	this.icuBedCount = icuBedCount;
	this.wardBedCount = bedCount - icuBedCount;
	this.builder = builder;
	icuDischarger = new Discharge(builder.getIcuDischargeScale(), builder.getIcuDischargeShape(), this);
//	icuDischarger = new Discharge(builder.getIcuDischargeScale(), builder.getIcuDischargeShape(), this);
	nonIcuDischarger = new Discharge(builder.getDischargeScale(), builder.getDischargeShape(), this);
	transferer = new Transfer(builder.getIcuTransferScale(), builder.getIcuTransferShape(), this);
	patientsNeedingOt = new ArrayList<Patient>();
	patientsNeedingRt = new ArrayList<Patient>();
	patientsNeedingPt = new ArrayList<Patient>();
    }

  

    public void createAndAdmitPatient() {
	if (patients.size() < bedCount) {
	    Patient p = new Patient();
	    this.add(p);
	    builder.getChooser();
	    if (Chooser.randomTrue(builder.getIcuAdmitProbability())
		    && inIcu.size() < icuBedCount) {


		patients.add(p);
		p.setAdmitLocation("ICU");
		p.setCurrentLocation("ICU");
		inIcu.add(p);
		icuDischarger.scheduleDischarge(p);
		transferer.scheduleTransfer(p);
		//these should be set on p.setNeedsOt]
		p.setNeedsOt(Chooser.randomTrue(builder.getNeedsOtIcu()));
		p.setNeedsRt(Chooser.randomTrue(builder.getNeedsRtIcu()));
		p.setNeedsPt(Chooser.randomTrue(builder.getNeedsPtIcu()));
		p.setAttribute("icuAdmit", true);
		p.setCurrentLocation("ICU");
		p.setAdmitTime(TimeUtils.getSchedule().getTickCount());
	    } else {

		patients.add(p);
		p.setAdmitLocation("Ward");
		p.setCurrentLocation("Ward");
		p.setAttribute("icuAdmit", false);
		notInIcu.add(p);
		nonIcuDischarger.scheduleDischarge(p);
		p.setNeedsOt(Chooser.randomTrue(builder.getNeedsOt()));
		p.setNeedsRt(Chooser.randomTrue(builder.getNeedsRt()));
		p.setNeedsPt(Chooser.randomTrue(builder.getNeedsPt()));
		p.setAdmitTime(TimeUtils.getSchedule().getTickCount());   
	    }
	   // System.out.print("admission," + p.agentId + ",")
	    //System.out.println(", " + p.printAttributes())
	}
    }

    public void transferPatient(Patient p) {
	//System.out.println("transfer patient " + p.getAgentId());
	inIcu.remove(p);
	notInIcu.add(p);
	p.setCurrentLocation("Ward");
	p.setTransferTime(TimeUtils.getSchedule().getTickCount());
    }

    public void dischargePatient(Patient p) {
	double admitTime = p.getAdmitTime();
	double currTime = TimeUtils.getSchedule().getTickCount();

	

	
	this.remove(p);
	patients.remove(p);
	inIcu.remove(p);
	notInIcu.remove(p);
	
	
    	DischargedPatient dp = new DischargedPatient(p.getAgentId(), p.getAdmitTime(), 
    			currTime, false, (boolean)p.getAttribute("icuAdmit"), p.getTransferTime(), p.getAdmitLocation(), p.getCurrentLocation());
	dischargedPatients.add(dp);
	this.add(dp);
    }

    public int getPatientCount() {
	return patients.size();
    }

    public int getIcuPatientCount() {
	return inIcu.size();
    }

    public int getNonIcuPatientCount() {
	return notInIcu.size();
    }
    
    public void resetTherapyNeeds() {
	if (patientsNeedingOt.size() > 0) {
	    this.patientsNeedingOt.clear();
	}
	
	if (patientsNeedingPt.size() > 0) {
	    this.patientsNeedingPt.clear();
	}
	if (patientsNeedingRt.size() > 0) {
		this.patientsNeedingRt.clear();
	}
	
	for (Patient p: this.patients) {
	    if ((boolean)p.getAttribute("needs_ot")) {
		this.patientsNeedingOt.add(p);
	    }
	    if ((boolean)p.getAttribute("needs_pt")) {
		this.patientsNeedingPt.add(p);
	    }
	    if ((boolean)p.getAttribute("needs_rt")) {
		this.patientsNeedingOt.add(p);
	    }
	}
    }



    public ArrayList<DischargedPatient> getDischargedPatients() {
        return dischargedPatients;
    }



    public void setDischargedPatients(ArrayList<DischargedPatient> dischargedPatients) {
        this.dischargedPatients = dischargedPatients;
    }
}
