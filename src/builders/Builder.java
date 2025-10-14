package builders;



import containers.Hospital;
import processes.Admission;
import processes.PatientVisit;
import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.graph.Network;
import agents.DischargedPatient;
import agents.Doctor;
import agents.HcwType;
import agents.HealthCareWorker;
import agents.Patient;
import agents.Therapist;
import utils.Chooser;
import utils.TimeUtils;

import java.io.IOException;
import java.nio.channels.NetworkChannel;
import java.util.logging.FileHandler;
import java.util.logging.Level; 
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;

//	newDistro = new LogNormalDistribution(0.7032373, 0.9986254)

public class Builder implements ContextBuilder<Object> {
	
    	
	ISchedule schedule ;
	Hospital hospital;
	Admission admissionProcess;
	Chooser chooser = new Chooser();
	
	
	private double defaultDouble = 0.1;
	private int defaultInt = 1;
	
	// adt parameters
	private int hospitalCapacity = 85;
	private int icuCapacity = 15;
	private double admissionsRate = 0.2; //mean intra_event time
	private double dischargeShape = 1.253;
	private double dischargeScale = 0.768;
	private double icuDischargeShape = 0.916;
	private double icuDischargeScale = 0.820;
	private double icuAdmitProbability = .15;
	private double generalMortality = defaultDouble;
	private double icuTransferProbability = 0.1; //probability of transfer to ICU
	private double icuTransferShape = 0.5;
	private double icuTransferScale = 1.0;
	private double needsRt = defaultDouble;
	private double needsPt = defaultDouble;
	private double needsOt = defaultDouble;
	private double needsRtIcu = defaultDouble;
	private double needsPtIcu = defaultDouble;
	private double needsOtIcu = defaultDouble;
	private double nurseIntraVisitShape = 0.585;
	private double nurseIntraVisitScale = 48.22;
	private double doctorIntraVisitShape = 0.585;
	private double doctorIntraVisitScale = 75.22;
	private double specialistIntraVisitShape = 0.585;
	private double specialistIntraVisitScale = 100.22;
	

	
	//Staffing parameters
	//respiratory therapists, physical therapists, occupational therapists, nurses, physicians
	// ratios of these types to 
	private double rtsPerPatient = defaultDouble;
	private double ptsPerPatient = defaultDouble;
	private double otsPerPatient = defaultDouble;
	private double nursesPerPatient = 0.3;
	private double physiciansPerPatient = 0.2;
	
	//transmission parameters
	private double cleanPtColonizeddHcwColonizationProb = defaultDouble;
	private double contaminedPtCleanHcwColonizationProb = defaultDouble;
	
	//review on 1/27:
	// review ADT model, HCW staffing ratios, contact stuff
	
	
	 @ScheduledMethod(start = 1.0, interval = 1)
	  public void daily() {
	     hospital.resetTherapyNeeds();
	  }	
	   @ScheduledMethod(start = 0.5, interval = 0.5)
	    public void perShiftOperations() {
	   } 
	    
	 @ScheduledMethod(start =365, interval = 1)
	  public void endOfRun() {
	     
	     if (true) {
	         writeSingleRunFiles();
	     }
	     RunEnvironment.getInstance().endRun();
	
	  }
	 
	 public void writeSingleRunFiles() {
	     
	     //print out discharged patients to a file
	     try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("discharged_patients.txt", false))) {
	         writer.write("agentId,admitTime,dischargeTime,icuAdmit,transferTime,admitLocation,dischargeLocation");
	         writer.newLine();
	         for (DischargedPatient dp : hospital.getDischargedPatients()) {
	              writer.write(dp.toString());
	              writer.newLine();
	          }
	          
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     
	     //print the hospital.visitData StringBuffer to a file
	     try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("visit_data.txt", false))) {
	         writer.write(hospital.visitData.toString());
	         writer.newLine();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	 }
	 
	

	

	@Override
	public Context<Object> build(Context<Object> context) {
	    	
	   
		    
	
	   
	    
		schedule = repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule();
		
		//WRR: Create a hospital with 90 beds.
		hospital = new Hospital(this, hospitalCapacity, icuCapacity);
		context.add(hospital);
		admissionProcess = new Admission(admissionsRate, hospital);
		admissionProcess.start();
		TimeUtils.getSchedule().schedule(this);
		
		
		buildHealthCareWorkers();
		HealthCareWorker doc1 = new HealthCareWorker(HcwType.DOCTOR, hospital);
		PatientVisit pv = new PatientVisit(0.2, hospital, doc1);
		doc1.setAttribute("visit_process", pv);
		pv.start();
		
		
		NetworkBuilder networkBuilder = new NetworkBuilder("icu", context, true);
		Network icuNetwork = networkBuilder.buildNetwork();
		networkBuilder = new NetworkBuilder("wards", context, true);
		Network wardsNetwork = networkBuilder.buildNetwork();
		
		
		return context;
	
	}
	
	private void buildHealthCareWorkers() {
	   
	    
	    for (int i = 0; i<hospitalCapacity*physiciansPerPatient; i++) {
		Doctor doc1 = new Doctor(HcwType.DOCTOR, hospital);
		PatientVisit pv = new PatientVisit(0.02, hospital, doc1);
		pv.setDistro(new GammaDistribution(doctorIntraVisitShape, doctorIntraVisitScale));	
		doc1.setAttribute("visit_process", pv);
		pv.start();
		hospital.add(doc1);
	    }
	    
	    for (int i = 0; i<hospitalCapacity*nursesPerPatient; i++) {
		HealthCareWorker nurse = new HealthCareWorker(HcwType.NURSE, hospital);
		PatientVisit pv = new PatientVisit(0.02, hospital, nurse);
		pv.setDistro(new GammaDistribution(nurseIntraVisitShape, nurseIntraVisitScale));
		nurse.setAttribute("visit_process", pv);
		pv.start();
	    }
	    
	    for (int i = 0; i<hospitalCapacity*rtsPerPatient; i++) {
		Therapist hcw  = new Therapist(HcwType.RT, hospital);
		hcw.setNeedsArray(hospital.patientsNeedingRt);
		PatientVisit pv = new PatientVisit(0.1, hospital, hcw);
		pv.setDistro(new GammaDistribution(specialistIntraVisitShape, specialistIntraVisitScale));
		hcw.setAttribute("visit_process", pv);
		pv.start();	
	    }
	    
	    for (int i = 0; i<hospitalCapacity*ptsPerPatient; i++) {
		Therapist hcw  = new Therapist(HcwType.PT, hospital);
		hcw.setNeedsArray(hospital.patientsNeedingPt);
		PatientVisit pv = new PatientVisit(0.1, hospital, hcw);
		pv.setDistro(new GammaDistribution(specialistIntraVisitShape, specialistIntraVisitScale));
		hcw.setAttribute("visit_process", pv);
		pv.start();
	    }
	    
	    for (int i = 0; i<hospitalCapacity*otsPerPatient; i++) {
		Therapist hcw  = new Therapist(HcwType.OT, hospital);
		hcw.setNeedsArray(hospital.patientsNeedingOt);
		PatientVisit pv = new PatientVisit(0.02, hospital, hcw);
		pv.setDistro(new GammaDistribution(specialistIntraVisitShape, specialistIntraVisitScale));
		hcw.setAttribute("visit_process", pv);
		pv.start();
	    }
	}
	


	public ISchedule getSchedule() {
	    return schedule;
	}

	public void setSchedule(ISchedule schedule) {
	    this.schedule = schedule;
	}

	public Hospital getHospital() {
	    return hospital;
	}

	public void setHospital(Hospital hospital) {
	    this.hospital = hospital;
	}

	public Chooser getChooser() {
	    return chooser;
	}

	public void setChooser(Chooser chooser) {
	    this.chooser = chooser;
	}

	public double getDefaultDouble() {
	    return defaultDouble;
	}

	public void setDefaultDouble(double defaultDouble) {
	    this.defaultDouble = defaultDouble;
	}

	public int getDefaultInt() {
	    return defaultInt;
	}

	public void setDefaultInt(int defaultInt) {
	    this.defaultInt = defaultInt;
	}

	public int getHospitalCapacity() {
	    return hospitalCapacity;
	}

	public void setHospitalCapacity(int hospitalCapacity) {
	    this.hospitalCapacity = hospitalCapacity;
	}

	public int getIcuCapacity() {
	    return icuCapacity;
	}

	public void setIcuCapacity(int icuCapacity) {
	    this.icuCapacity = icuCapacity;
	}

	public double getAdmissionsRate() {
	    return admissionsRate;
	}

	public void setAdmissionsRate(double admissionsRate) {
	    this.admissionsRate = admissionsRate;
	}

	public double getDischargeShape() {
	    return dischargeShape;
	}

	public void setDischargeShape(double dischargeShape) {
	    this.dischargeShape = dischargeShape;
	}

	public double getDischargeScale() {
	    return dischargeScale;
	}

	public void setDischargeScale(double dischargeScale) {
	    this.dischargeScale = dischargeScale;
	}

	public double getIcuDischargeShape() {
	    return icuDischargeShape;
	}

	public void setIcuDischargeShape(double icuDischargeShape) {
	    this.icuDischargeShape = icuDischargeShape;
	}

	public double getIcuDischargeScale() {
	    return icuDischargeScale;
	}

	public void setIcuDischargeScale(double icuDischargeScale) {
	    this.icuDischargeScale = icuDischargeScale;
	}

	public double getIcuAdmitProbability() {
	    return icuAdmitProbability;
	}

	public void setIcuAdmitProbability(double icuAdmitProbability) {
	    this.icuAdmitProbability = icuAdmitProbability;
	}

	public double getGeneralMortality() {
	    return generalMortality;
	}

	public void setGeneralMortality(double generalMortality) {
	    this.generalMortality = generalMortality;
	}

	public double getIcuTransferShape() {
	    return icuTransferShape;
	}

	public void setIcuTransferShape(double icuTransferShape) {
	    this.icuTransferShape = icuTransferShape;
	}

	public double getIcuTransferScale() {
	    return icuTransferScale;
	}

	public void setIcuTransferScale(double icuTransferScale) {
	    this.icuTransferScale = icuTransferScale;
	}

	public double getNeedsRt() {
	    return needsRt;
	}

	public void setNeedsRt(double needsRt) {
	    this.needsRt = needsRt;
	}

	public double getNeedsPt() {
	    return needsPt;
	}

	public void setNeesPt(double neesPt) {
	    this.needsPt = neesPt;
	}

	public double getNeedsOt() {
	    return needsOt;
	}

	public void setNeedsOt(double needsOt) {
	    this.needsOt = needsOt;
	}

	public double getNeedsRtIcu() {
	    return needsRtIcu;
	}

	public void setNeedsRtIcu(double needsRtIcu) {
	    this.needsRtIcu = needsRtIcu;
	}

	public double getNeedsPtIcu() {
	    return needsPtIcu;
	}

	public void setNeesPtIcu(double neesPtIcu) {
	    this.needsPtIcu = neesPtIcu;
	}

	public double getNeedsOtIcu() {
	    return needsOtIcu;
	}

	public void setNeedsOtIcu(double needsOtIcu) {
	    this.needsOtIcu = needsOtIcu;
	}

	public double getRtsPerPatient() {
	    return rtsPerPatient;
	}

	public void setRtsPerPatient(double rtsPerPatient) {
	    this.rtsPerPatient = rtsPerPatient;
	}

	public double getPtsPerPatient() {
	    return ptsPerPatient;
	}

	public void setPtsPerPatient(double ptsPerPatient) {
	    this.ptsPerPatient = ptsPerPatient;
	}

	public double getOtsPerPatient() {
	    return otsPerPatient;
	}

	public void setOtsPerPatient(double otsPerPatient) {
	    this.otsPerPatient = otsPerPatient;
	}

	public double getNursesPerPatient() {
	    return nursesPerPatient;
	}

	public void setNursesPerPatient(double nursesPerPatient) {
	    this.nursesPerPatient = nursesPerPatient;
	}

	public double getPhysiciansPerPatient() {
	    return physiciansPerPatient;
	}

	public void setPhysiciansPerPatient(double physiciansPerPatient) {
	    this.physiciansPerPatient = physiciansPerPatient;
	}

	public double getCleanPtColonizeddHcwColonizationProb() {
	    return cleanPtColonizeddHcwColonizationProb;
	}

	public void setCleanPtColonizeddHcwColonizationProb(double cleanPtColonizeddHcwColonizationProb) {
	    this.cleanPtColonizeddHcwColonizationProb = cleanPtColonizeddHcwColonizationProb;
	}

	public double getContaminedPtCleanHcwColonizationProb() {
	    return contaminedPtCleanHcwColonizationProb;
	}

	public void setContaminedPtCleanHcwColonizationProb(double contaminedPtCleanHcwColonizationProb) {
	    this.contaminedPtCleanHcwColonizationProb = contaminedPtCleanHcwColonizationProb;
	}

}
