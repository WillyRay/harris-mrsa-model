package containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Comparator;

import agents.Agent;
import agents.DischargedPatient;
import agents.Doctor;
import agents.HealthCareWorker;
import agents.Patient;
import builders.Builder;
import processes.Discharge;
import processes.Transfer;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;
import utils.Chooser;
import utils.TimeUtils;

public class Hospital extends DefaultContext<Agent> {
    Builder builder;

    ArrayList<Patient> patients;
    ArrayList<HealthCareWorker> hcws;
    int bedCount;
    public ArrayList<Patient> inIcu;
    int icuBedCount;
    ArrayList<Patient> notInIcu;
    int wardBedCount;
    Discharge icuDischarger;
    Discharge nonIcuDischarger;
    Transfer transferer;
    ArrayList<String> patientOutputs;
    ArrayList<DischargedPatient> dischargedPatients;
    private Network<Agent> hospitalnet;
    
    public ArrayList<Patient> patientsNeedingOt;
    public ArrayList<Patient> patientsNeedingRt;
    public ArrayList<Patient> patientsNeedingPt;
    
    public Context<Agent> wardContext;
    public Context<Agent> icuContext;
    
    public StringBuffer visitData;

    public Hospital(Builder builder, int bedCount, int icuBedCount) {
	super();
	
	this.wardContext = builder.getWardContext();
	this.icuContext = builder.getIcuContext();
	this.visitData = new StringBuffer();
	visitData.append("hcwId,hcwType,patientId,patientLocation,visitTime\n");
	
	
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
	NetworkBuilder<Agent> networkBuilder = new NetworkBuilder<Agent>("doctorNet", this, true);
	this.hospitalnet = networkBuilder.buildNetwork();
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
		this.icuContext.add(p);
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
		this.wardContext.add(p);
	    }
	  
	    if (p.isNeedsOt()) {
		this.patientsNeedingOt.add(p);
	    }
	    if (p.isNeedsPt()) {
		this.patientsNeedingPt.add(p);
	    }
	    if (p.isNeedsRt())	 {
		this.patientsNeedingRt.add(p);
	    }
	    setPatientDoctorAssignments(p);
	}
	
	
    }

    public void transferPatient(Patient p) {
	//System.out.println("transfer patient " + p.getAgentId());
	inIcu.remove(p);
	notInIcu.add(p);
	p.setCurrentLocation("Ward");
	p.setTransferTime(TimeUtils.getSchedule().getTickCount());
    }
    
    public void setPatientDoctorAssignments(Patient p) {
	java.util.List<Doctor> doctorList = new ArrayList<Doctor>();
	if (p.getCurrentLocation().equals("Ward")) {
        // Get all doctors as a list
	    doctorList = wardContext.getObjectsAsStream(Agent.class)
	    .filter(a -> a instanceof Doctor)
            .map(a -> (Doctor)a)
            .collect(Collectors.toList());
        
	} else {
	    	// Get all doctors as a list
	    doctorList = icuContext.getObjectsAsStream(Agent.class)
	    .filter(a -> a instanceof Doctor)
	    .map(a -> (Doctor)a)
	    .collect(Collectors.toList());
	} 
	    
	

        if (doctorList.isEmpty()) {
            System.out.println("No doctors available for assignment.");
            return;
        }

        // Sort by number of assigned patients (edges)
        doctorList.sort(Comparator.comparingInt(d -> hospitalnet.getDegree(d)));

        // Find the minimum degree
        int minDegree = hospitalnet.getDegree(doctorList.get(0));

        // Filter to all doctors with the minimum degree
        java.util.List<Doctor> minDoctors = doctorList.stream()
            .filter(d -> hospitalnet.getDegree(d) == minDegree)
            .collect(Collectors.toList());

        // Randomly choose one if there are ties
        Doctor assignedDoctor = (Doctor) Chooser.chooseOne(minDoctors);

        //System.out.println("Assigning patient " + p.getAgentId() +" " +p.getCurrentLocation() + " to doctor " + assignedDoctor.getAgentId() + " ICU: " + assignedDoctor.icu);
        hospitalnet.addEdge(assignedDoctor, p);
    
    }
    
    public void setPatientNurseAssignments() {
    // Remove any edges in hospitalnet that are between a nurse and a patient
    java.util.List<repast.simphony.space.graph.RepastEdge<Agent>> edgesToRemove = new ArrayList<>();
    for (repast.simphony.space.graph.RepastEdge<Agent> edge : hospitalnet.getEdges()) {
        Agent source = edge.getSource();
        Agent target = edge.getTarget();
        boolean sourceIsPatient = source instanceof Patient;
        boolean targetIsPatient = target instanceof Patient;
        boolean sourceIsNurse = (source instanceof HealthCareWorker) && !(source instanceof Doctor);
        boolean targetIsNurse = (target instanceof HealthCareWorker) && !(target instanceof Doctor);
        // If one is patient and the other is nurse
        if ((sourceIsPatient && targetIsNurse) || (targetIsPatient && sourceIsNurse)) {
            edgesToRemove.add(edge);
        }
    }
    // Remove all nurse-patient edges
    for (repast.simphony.space.graph.RepastEdge<Agent> edge : edgesToRemove) {
        hospitalnet.removeEdge(edge);
    }
    //loop over all patients
    for (Patient p : patients) {
        // Get all nurses as a list (TYPE == HcwType.NURSE)
	java.util.List<HealthCareWorker> nurseList = new ArrayList<HealthCareWorker>();
        
	if (p.getCurrentLocation().equals("Ward")) {
	nurseList = wardContext.getObjectsAsStream(Agent.class)
	    .filter(a -> (a instanceof HealthCareWorker) && (((HealthCareWorker)a).getTYPE() == agents.HcwType.NURSE))
	    .map(a -> (HealthCareWorker)a)
	    .collect(Collectors.toList());
	} else {
	    nurseList = icuContext.getObjectsAsStream(Agent.class)
	    .filter(a -> (a instanceof HealthCareWorker) && (((HealthCareWorker)a).getTYPE() == agents.HcwType.NURSE))
	    .map(a -> (HealthCareWorker)a)
	    .collect(Collectors.toList());
	}
	if (nurseList.isEmpty()) {
	    System.out.println("No nurses available for assignment.");
	    continue;
	}

        // Sort by number of assigned patients (edges)
        nurseList.sort(Comparator.comparingInt(n -> hospitalnet.getDegree(n)));

        // Find the minimum degree
        int minDegree = hospitalnet.getDegree(nurseList.get(0));

        // Filter to all nurses with the minimum degree
        java.util.List<HealthCareWorker> minNurses = nurseList.stream()
            .filter(n -> hospitalnet.getDegree(n) == minDegree)
            .collect(Collectors.toList());

        HealthCareWorker assignedNurse1;
        HealthCareWorker assignedNurse2;
        if (minNurses.size() >= 2) {
            java.util.Collections.shuffle(minNurses);
            assignedNurse1 = minNurses.get(0);
            assignedNurse2 = minNurses.get(1);
        } else {
            assignedNurse1 = minNurses.get(0);
            // Find nurses with the next minimum degree, excluding assignedNurse1
            int nextMinDegree = Integer.MAX_VALUE;
            for (HealthCareWorker n : nurseList) {
                int deg = hospitalnet.getDegree(n);
                if (deg > minDegree && deg < nextMinDegree) {
                    nextMinDegree = deg;
                }
            }
            java.util.List<HealthCareWorker> nextMinNurses = new ArrayList<>();
            for (HealthCareWorker n : nurseList) {
                if (hospitalnet.getDegree(n) == nextMinDegree && n != assignedNurse1) {
                    nextMinNurses.add(n);
                }
            }
            if (!nextMinNurses.isEmpty()) {
                assignedNurse2 = (HealthCareWorker) Chooser.chooseOne(nextMinNurses);
            } else {
                // If all nurses have the same degree, pick another nurse from minNurses
                assignedNurse2 = assignedNurse1;
            }
        }
        // Assign nurses, ensuring they are distinct
        if (assignedNurse1 != null) {
            //System.out.println("Assigning patient " + p.getAgentId() +" " +p.getCurrentLocation() + " to nurse " + assignedNurse1.getAgentId() + " ICU: " + assignedNurse1.icu);
            hospitalnet.addEdge(assignedNurse1, p);
        }
        if (assignedNurse2 != null && assignedNurse2 != assignedNurse1) {
            //System.out.println("Assigning patient " + p.getAgentId() +" " +p.getCurrentLocation() + " to nurse " + assignedNurse2.getAgentId() + " ICU: " + assignedNurse2.icu);
            hospitalnet.addEdge(assignedNurse2, p);
        }
    }
    // Now assign patients to nurses
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
	    if (p.isNeedsOt()) {
		this.patientsNeedingOt.add(p);
	    }
	    if (p.isNeedsPt()) {
		this.patientsNeedingPt.add(p);
	    }
	    if (p.isNeedsRt())	 {
		this.patientsNeedingRt.add(p);
	    }
	}
    }



    public ArrayList<DischargedPatient> getDischargedPatients() {
        return dischargedPatients;
    }



    public void setDischargedPatients(ArrayList<DischargedPatient> dischargedPatients) {
        this.dischargedPatients = dischargedPatients;
    }



    public Network<Agent> getHospitalnet() {
        return hospitalnet;
    }



    public void setHospitalnet(Network<Agent> hospitalnet) {
        this.hospitalnet = hospitalnet;
    }
}
