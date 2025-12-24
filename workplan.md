---
format:
  html:
    embed-resources: true
---


# MRSA ABM Model - Work Plan
**Project Period**: December 2025 - March 2025
---

## December 2025

### Week of December 8-14, 2025

Goals:

- [x] Finish Visit behavior

Tasks:

- Finish transmission-eligible visit behavior
   - [x] HH->GG->Transmission?->HH
   - [x] ~~Q: Model all as nested or sequenced steps, or multiply probabilities?~~ Just model independent events

Deliverables:

- ~~Visit data file contains all visits with disease state of hcw and patient, whether or not transmission occurred.~~
- new transmission.txt data file contains all hcw->patient transmissions.  

Notes:

- Importation of I-state patients already implemented.  This will allow tracking of transmission at the visit.
- The rest of the disease model next week.  

---

### Week of December 15-21, 2025

Goals:

- Finish Disease Model
- Feature Complete

Tasks:

- [x] Implement transition from C->I (colonized to infected)
  - Added `Progression.java` with Gamma-distributed time-to-event
  - ICU: Gamma(1.5, 5.6), Ward: Gamma(1.5, 8.0)
- [ ] Implement transition from I->R (infected to recovered) - **deferred**
- [x] Update importation model to use probability distribution (88% S, 10% C, 2% I)

Deliverables:

- [x] Disease data file contains importations, transmissions, progressions.
- [x] transmission_data.txt captures all HCW→patient transmission events

Notes:

- C→I progression implemented and tested
- I→R transition deferred to January

---

### Week of December 22-23, 2025

Goals:

- Calibrate Visit Rates
- Create documentation website
- Fix DischargedPatient field population

Tasks:

- [ ] Adjust HCW-specific intra-visit rates to match observed frequencies in HCW Room Visit Frequencies.docx in Box folder.
- [x] Fix DischargedPatient field population for disease tracking:
  - Added `dischargeLocation` - set before discharge
  - Added `colonizedOnAdmission` / `colonizedTime` - set during admission or transmission
  - Added `infectedOnAdmission` / `infectedTime` - set during admission or progression
  - Fixed `AgentDisease.doProgression()` to record proper infection time
- [x] Create `R/discharged_patients_analysis.qmd` for comprehensive outcome analysis
- [x] Update documentation (description.md, parameters.md, workplan.md)

Deliverables:

- Visit rate report already exists.  Just need to get the histograms to match calibration targets.
- Automatically generated documentation website.
- [x] discharged_patients.txt now contains complete disease tracking data
- [x] discharged_patients_analysis.qmd provides analysis of LOS, disease status, transfers

Notes:

- **Q: We're using mean intra-event times in the visit frequencies document.  We're currently implemented as gamma distributions in the model.  Do we need to adjust the shape parameters to match the observed distributions?  Guidance needed**
- Short Week - Christmas Holiday
- DischargedPatient now properly tracks all disease-related fields for post-simulation analysis

---

### Week of December 29 - January 2, 2026

Goals: 

-Calibrate Visit Rates (cont'd)


Tasks:

- Adjust HCW-specific intra-visit rates to match observed frequencies in HCW Room Visit Frequencies.docx in Box folder. (cont'd)

Deliverables:

- Visit rate report already exists.  Just need to get the histograms to match calibration targets. (cont'd)

Notes:


---

## January 2026

### Week of January 5-11, 2026

Goals:

- Implement I→R (infected to recovered) transition
- Implement mortality process
- Parameter finalization for disease model, visit behavior, all submodels
- Calibrate disease outcomes.
  - **Q: What are the calibration targets for disease outcomes? Infections/10000pt days?**

Tasks:

- [ ] Implement I→R transition (recovery from infection)
- [ ] Implement Death.java (mortality process)
- [ ] Calibration process.  Should be one tuning variable, right?

Deliverables:

- Calibration report for disease
- Complete disease model with S→C→I→R transitions

Notes:

- I→R transition was deferred from December

---

### Week of January 12-18, 2026

Goals:

- set up batch runner
- initial sensitivity analyses

Tasks:

- Configure batch runner.
- Set up initial sensitivity analyse

Deliverables:
 
- Sensitivity analysis reports

Notes:

- ** What experimental parameters should be promoted up to user-control level? **
---

### Week of January 19-25, 2026

Goals:

- prepare to run experiments

Tasks:

- Probably run more sensitivity analyses
- Last adjustments for calibration, verification, etc.

Deliverables:

- TBD

Notes:

- MLK Day - January 20

---

### Week of January 26 - February 1, 2026

Goals:

- Prepare to run experiments
- finalize documentation website

Tasks:

- Write good documentation for all model features.
- Work through website, make sure it's accurate and complete

Deliverables:

- Website progress.

Notes:

-

---

## February 2026

### Week of February 2-8, 2026

Goals:

- Run Experiments

Tasks:

- Setup and run batches.
- Manage and analyse initial batch results

Deliverables:

- Prelim analysis

Notes:

-

---

### Week of February 9-15, 2026

Goals:  

- Run Experiments (cont'd)
- I think should be done by this time.

Tasks:

-

Deliverables:

- experimental data w/ prelim analysis

Notes:

-

---

### Week of February 16-22, 2026

Goals:

-

Tasks:

-

Deliverables:

-

Notes:

- Presidents Day - February 16

---

### Week of February 23 - March 1, 2026

Goals:

-

Tasks:

-

Deliverables:

-

Notes:

-

---

## March 2026

### Week of March 2-8, 2026

Goals:

-

Tasks:

-

Deliverables:

-

Notes:

-

---

### Week of March 9-15, 2026

Goals:

-

Tasks:

-

Deliverables:

-

Notes:

-

---

### Week of March 16-22, 2026

Goals:

-

Tasks:

-

Deliverables:

-

Notes:

-

---


---

