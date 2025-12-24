---
format:
  html:
    embed-resources: true
---

# MRSA ABM Model Parameters

*Last updated: December 23, 2025*

This document describes all model parameters defined in the simulation. Parameters are organized by functional category.

Sources (branch 'next'):
- Defaults / editable parameters: https://github.com/WillyRay/harris-mrsa-model/blob/next/harris-mrsa-model.rs/parameters.xml
- Runtime setup and usage: https://github.com/WillyRay/harris-mrsa-model/blob/next/src/builders/Builder.java
- Visit / transmission logic: https://github.com/WillyRay/harris-mrsa-model/blob/next/src/agents/HealthCareWorker.java
- Disease progression: https://github.com/WillyRay/harris-mrsa-model/blob/next/src/processes/Progression.java

Notation: Parameters marked with * are configurable via parameters.xml.

## Default Values

| Parameter | Value | Usage |
|-----------|-------|-------|
| `defaultDouble` | <br>0.1 | Default placeholder value for unspecified double parameters |
| `defaultInt` | <br>1 | Default placeholder value for unspecified integer parameters |

## Hospital Configuration

| Parameter | Value | Usage |
|-----------|-------|-------|
| `hospitalCapacity` | <br>120 | Total hospital capacity (ICU + Ward beds). Used to initialize Hospital (see Builder.java) |
| `icuCapacity` | <br>20 | Number of ICU beds. Ward capacity = hospitalCapacity - icuCapacity |

## Admission-Discharge-Transfer (ADT) Parameters

| Parameter | Value | Usage |
|-----------|-------|-------|
| `admissionsRate` | <br>0.05 | Mean inter-arrival time (days) for patient admissions. Used in Admission process (see Builder.java) |
| `icuAdmitProbability` | <br>0.15 | Probability that newly admitted patient goes directly to ICU |
| `dischargeShape` | <br>1.253 | Shape parameter for Gamma distribution of ward patient length of stay (days) |
| `dischargeScale` | <br>0.768 | Scale parameter for Gamma distribution of ward patient length of stay (days) |
| `icuDischargeShape` | <br>0.916 | Shape parameter for Gamma distribution of ICU patient length of stay (days) |
| `icuDischargeScale` | <br>0.820 | Scale parameter for Gamma distribution of ICU patient length of stay (days) |
| `icuTransferProbability` | <br>0.1 | Probability that ICU patient transfers to ward (vs. discharge) |
| `icuTransferShape` | <br>0.5 | Shape parameter for Gamma distribution of time until ICU-to-ward transfer (days) |
| `icuTransferScale` | <br>1.0 | Scale parameter for Gamma distribution of time until ICU-to-ward transfer (days) |
| `generalMortality` | <br>0.1 | General mortality probability (currently uses defaultDouble) |

## Therapy Needs Parameters

| Parameter | Value | Usage |
|-----------|-------|-------|
| `needsRt` | <br>0.1 | Probability that ward patient needs respiratory therapy |
| `needsPt` | <br>0.1 | Probability that ward patient needs physical therapy |
| `needsOt` | <br>0.1 | Probability that ward patient needs occupational therapy |
| `needsRtIcu` | <br>1.0 | Probability that ICU patient needs respiratory therapy |
| `needsPtIcu` | <br>0.1 | Probability that ICU patient needs physical therapy (inherits from needsPt) |
| `needsOtIcu` | <br>0.1 | Probability that ICU patient needs occupational therapy (inherits from needsOt) |

## Healthcare Worker Visit Timing Parameters

All timing parameters are in **minutes**. Shape and scale define Gamma distributions for inter-visit times.

| Parameter | Value | Usage |
|-----------|-------|-------|
| `nurseIntraVisitShape` | <br>0.54 | Shape parameter for Gamma distribution of time between nurse visits to ward patients (see Builder.java) |
| `nurseIntraVisitScale` | <br>55.1 | Scale parameter for Gamma distribution of time between nurse visits to ward patients |
| `nurseICUIntraVisitShape` | <br>0.54 | Shape parameter for Gamma distribution of time between nurse visits to ICU patients (see Builder.java) |
| `nurseICUIntraVisitScale` | <br>20 | Scale parameter for Gamma distribution of time between nurse visits to ICU patients |
| `doctorIntraVisitShape` | <br>0.52 | Shape parameter for Gamma distribution of time between doctor visits to ward patients (see Builder.java) |
| `doctorIntraVisitScale` | <br>90.7 | Scale parameter for Gamma distribution of time between doctor visits to ward patients |
| `doctorIcuIntraVisitShape` | <br>0.52 | Shape parameter for Gamma distribution of time between doctor visits to ICU patients (see Builder.java) |
| `doctorIcuIntraVisitScale` | <br>35.3 | Scale parameter for Gamma distribution of time between doctor visits to ICU patients |
| `specialistIntraVisitShape` | <br>0.62 | Shape parameter for Gamma distribution of time between specialist (RT/PT/OT) visits (see Builder.java) |
| `specialistIntraVisitScale` | <br>61.7 | Scale parameter for Gamma distribution of time between specialist visits |
| `roomVisitDuration` | <br>6.6 | Duration of room visit in minutes (currently defined but not actively used in code) |

## Staffing Ratios

These ratios determine the number of HCWs created per patient/bed.

| Parameter | Value | Usage |
|-----------|-------|-------|
| `nursesPerPatient` | <br>0.2 | Ward nurses per bed. Creates (hospitalCapacity-icuCapacity)*nursesPerPatient ward nurses (see Builder.java) |
| `physiciansPerPatient` | <br>0.2 | Ward physicians per bed. Creates (hospitalCapacity-icuCapacity)*physiciansPerPatient ward doctors (see Builder.java) |
| `icuNursesPerPatient` | <br>0.5 | ICU nurses per bed. Creates icuCapacity*icuNursesPerPatient ICU nurses (see Builder.java) |
| `icuPhysiciansPerPatient` | <br>0.3 | ICU physicians per bed. Creates icuCapacity*icuPhysiciansPerPatient ICU doctors (see Builder.java) |
| `icuRtsPerPatient` | <br>0.1 | ICU respiratory therapists per bed. Creates icuCapacity*icuRtsPerPatient ICU RTs (see Builder.java) |
| `rtsPerPatient` | <br>0.1 | Respiratory therapists per total hospital capacity (see Builder.java) |
| `ptsPerPatient` | <br>0.1 | Physical therapists per total hospital capacity (see Builder.java) |
| `otsPerPatient` | <br>0.1 | Occupational therapists per total hospital capacity (see Builder.java) |

## Transmission Parameters (updated in 'next')

Parameters governing MRSA transmission dynamics during HCW–patient visits.

| Parameter | Value | Usage |
|-----------|-------|-------|
| `transmission_probability_hcw_to_patient`* | <br>0.4 | Probability that a contaminated HCW transmits MRSA to a susceptible patient during a visit (after pre-visit hand hygiene check). |
| `transmission_probability_patient_to_hcw`* | <br>0.4 | Probability that an HCW acquires contamination from a colonized/infected patient during a visit. |

## Hand Hygiene, PPE, and Efficacy

| Parameter | Value | Usage |
|-----------|-------|-------|
| `hhAdherenceBase`* | <br>0.5 | Base hand hygiene adherence rate (placeholder) |
| `nurseHhAdherence`* | <br>0.5 | Nurse hand hygiene adherence before patient contact (applied during HCW setup; see Builder.java) |
| `doctorHhAdherence`* | <br>0.5 | Doctor hand hygiene adherence before patient contact (applied during HCW setup; see Builder.java) |
| `therapistHhAdherence`* | <br>0.5 | Therapist hand hygiene adherence before patient contact (applied during HCW setup; see Builder.java) |
| `nurseHhAdherencePost`* | <br>0.5 | Nurse hand hygiene adherence after patient contact (applied during HCW setup; see Builder.java) |
| `doctorHhAdherencePost`* | <br>0.5 | Doctor hand hygiene adherence after patient contact (applied during HCW setup; see Builder.java) |
| `therapistHhAdherencePost`* | <br>0.5 | Therapist hand hygiene adherence after patient contact (applied during HCW setup; see Builder.java) |
| `ppeAdherenceIfCp`* | <br>0.65 | PPE (glove) adherence when visiting contact precaution patients (see Builder.java and HealthCareWorker.java) |
| `hand_hygiene_efficacy`* | <br>0.95 | Efficacy of hand hygiene when performed (used to reduce transmission risk pre-visit). |
| `glove_efficacy`* | <br>0.5 | Efficacy of gloves when worn (used to reduce post-visit contamination persistence). |
| `hhEfficacy`* | <br>0.95 | Legacy/alternate parameter for hand hygiene efficacy referenced during setup. |

## Reproducibility

| Parameter | Value | Usage |
|-----------|-------|-------|
| `randomSeed`* | <br>(unset) | Default random seed; if not set, simulation uses runtime default. |

## Disease Importation and Mortality

### Importation on Admission

The model now uses a probability distribution to determine patient disease status on admission (implemented in `Hospital.createAndAdmitPatient()`):

| Status | Probability | Description |
|--------|-------------|-------------|
| Susceptible ("U") | 88% | Patient admitted with no MRSA colonization |
| Colonized ("C") | 10% | Patient admitted with MRSA colonization |
| Infected ("I") | 2% | Patient admitted with active MRSA infection |

**Note:** The legacy parameters below are still defined but the actual importation logic uses the probability distribution above.

| Parameter | Value | Usage |
|-----------|-------|-------|
| `admitImportationInfectionProbability` | <br>0.01 | (Legacy) Probability for ward patient colonization |
| `admitImportationInfectionProbabilityICU` | <br>0.01 | (Legacy) Probability for ICU patient colonization |
| `importerDieProbability` | <br>0.1 | Mortality probability for colonized ward patients (not yet implemented) |
| `importerDiePrombabilityicu` | <br>0.1 | Mortality probability for colonized ICU patients (not yet implemented) |

## Disease Progression Parameters

Parameters controlling the progression from COLONIZED to INFECTED state (implemented in `Progression.java` and `AgentDisease.java`).

| Parameter | Value | Usage |
|-----------|-------|-------|
| ICU progression shape | 1.5 | Shape parameter for Gamma distribution of time to progression (ICU patients) |
| ICU progression scale | 5.6 | Scale parameter for Gamma distribution of time to progression (ICU patients, days) |
| Ward progression shape | 1.5 | Shape parameter for Gamma distribution of time to progression (Ward patients) |
| Ward progression scale | 8.0 | Scale parameter for Gamma distribution of time to progression (Ward patients, days) |

**Note:** These parameters are currently hardcoded in `AgentDisease.setColonizedBy()`. ICU patients progress faster on average (~8.4 days) compared to Ward patients (~12 days).

## Parameter Status Summary

- **Parameters with specific values**: ADT parameters (admission rates, LOS distributions, transfer probabilities), HCW visit timing, staffing ratios for ICU and ward doctors/nurses, disease importation probabilities (88%/10%/2%)
- **Parameters using placeholder values**: Therapy needs probabilities, transmission probabilities, hand hygiene/PPE adherence rates, mortality rates, ward therapist staffing ratios
- **Hardcoded parameters**: Disease progression timing (Gamma distributions for C→I progression) - currently in `AgentDisease.java`
- **Not yet implemented**: Mortality (Death.java is a stub), I→R recovery transition
- **Note**: Any parameter set to `defaultDouble` (0.1) or `defaultInt` (1) or round placeholder values (0.5, 1.0) should be considered as using placeholder values that may need adjustment based on data or literature
