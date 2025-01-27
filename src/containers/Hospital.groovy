package containers

import agents.Agent
import agents.HealthCareWorker
import agents.Patient
import builders.Builder
import processes.Discharge
import processes.Transfer
import repast.simphony.context.DefaultContext
import utils.Chooser

class Hospital extends DefaultContext<Object>{
    Builder builder;

    ArrayList<Patient> patients;
    ArrayList<HealthCareWorker> hcws;
    int bedCount
    ArrayList<Patient> inIcu;
    int icuBedCount
    ArrayList<Patient> notInIcu;
    int wardBedCount
    Discharge icuDischarger
    Discharge nonIcuDischarger;
    Transfer transferer;



    public Hospital(Builder builder, int bedCount, int icuBedCount) {
	super();

	this.patients = new ArrayList<Patient>()
	this.inIcu = new ArrayList<Patient>()
	this.notInIcu = new ArrayList<Patient>()
	this.hcws = new ArrayList<HealthCareWorker>()
	this.bedCount = bedCount
	this.icuBedCount = icuBedCount
	this.wardBedCount = bedCount - icuBedCount
	this.builder = builder
	icuDischarger = new Discharge(builder.getIcuDischargeScale(), builder.getIcuDischargeShape(), this)
	nonIcuDischarger = new Discharge(builder.getDischargeScale(), builder.getDischargeShape(), this)
	transferer = new Transfer(builder.getIcuTransferScale(), builder.getIcuTransferShape(), this)
    }



    public createAndAdmitPatient() {
	if (patients.size() < bedCount) {
	    Patient p = new Patient()
	    this.add(p);
	    if (builder.getChooser().randomTrue(builder.getIcuAdmitProbability())
		    && inIcu.size() < icuBedCount) {


		patients.add(p)
		p.setAttribute("icuAdmit", true)
		inIcu.add(p)
		icuDischarger.scheduleDischarge(p)
		transferer.scheduleTransfer(p)
		p.setAttribute("needs_ot", builder.getNeedsOtIcu())
		p.setAttribute("needs_rt", builder.getNeedsRtIcu())
		p.setAttribute("needs_pt", builder.getNeedsPtIcu())
	    } else {

		patients.add(p)
		p.setAttribute("icuAdmit", false)
		notInIcu.add(p)
		nonIcuDischarger.scheduleDischarge(p)
		p.setAttribute("needs_ot", builder.getNeedsOt())
		p.setAttribute("needs_rt", builder.getNeedsRt())
		p.setAttribute("needs_pt", builder.getNeedsPt())
	    }
	}
    }

    public void transferPatient(Patient p) {
	//System.out.println("transfer patient " + p.getAgentId());
	inIcu.remove(p)
	notInIcu.add(p)
    }

    public void dischargePatient(Patient p) {
	this.remove(p)
	patients.remove(p)
	if (p.getAttribute("icuAdmit") == true) {
	    inIcu.remove(p)
	} else {
	    notInIcu.remove(p)
	}
    }

    public int getPatientCount() {
	return patients.size()
    }

    public int getIcuPatientCount() {
	return inIcu.size()
    }

    public int getNonIcuPatientCount() {
	return notInIcu.size()
    }
}
