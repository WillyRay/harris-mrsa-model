package builders;



import containers.Hospital;
import processes.Admission;
import processes.PatientVisit;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import agents.HcwType;
import agents.HealthCareWorker;
import agents.Patient;
import utils.Chooser;
import utils.TimeUtils;



public class Builder implements ContextBuilder<Object> {
	
	ISchedule schedule ;
	Hospital hospital;
	Admission admissionProcess;
	Chooser chooser = new Chooser();
	
	
	private double defaultDouble = 0.1;
	private int defaultInt = 1;
	
	// adt parameters
	private int hospitalCapacity = 90;
	private int icuCapacity = 20;
	private double admissionsRate = 0.2; //mean intra_event time
	private double dischargeShape = 1.3;
	private double dischargeScale = 1.0;
	private double icuDischargeShape = 1.3;
	private double icuDischargeScale = 1.0;
	private double icuAdmitProbability = 0.1;
	private double generalMortality = 0.1;
	private double icuTransferShape = 0.5;
	private double icuTransferScale = 1.0;
	private double needsRt = 0.1;
	private double needsPt = 0.1;
	private double needsOt = 0.1;
	private double needsRtIcu = 0.1;
	private double needsPtIcu = 0.1;
	private double needsOtIcu = 0.1;
	
	//Staffing parameters
	//respiratory therapists, physical therapists, occupational therapists, nurses, physicians
	// ratios of these types to 
	private double rtsPerPatient = 0.1;
	private double ptsPerPatient = 0.1;
	private double otsPerPatient = 0.1;
	private double nursesPerPatient = 0.3;
	private double physiciansPerPatient = 0.2;
	
	//transmission parameters
	private double cleanPtColonizeddHcwColonizationProb = 0.1;
	private double contaminedPtCleanHcwColonizationProb = 0.1;
	
	//review on 1/27:
	// review ADT model, HCW staffing ratios, contact stuff
	
	
	 @ScheduledMethod(start = 1.0, interval = 1)
	  public void daily() {
	    // System.out.println(hospital.getPatientCount());
	
	  }	
	 
	 @ScheduledMethod(start =365, interval = 1)
	  public void endOfRun() {
	     RunEnvironment.getInstance().endRun();
	
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
		
		
		HealthCareWorker doc1 = new HealthCareWorker(HcwType.DOCTOR, hospital);
		PatientVisit pv = new PatientVisit(0.2, hospital, doc1);
		doc1.setAttribute("visit_process", pv);
		pv.start();
		
		
		return context;
	
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
