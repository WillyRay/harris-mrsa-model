# MRSA ABM Model Parameters

This document describes all model parameters defined in `builders.Builder` class. Parameters are organized by functional category.

## Default Values

| Parameter | Value | Usage |
|-----------|-------|-------|
| `defaultDouble` | 0.1 | Default placeholder value for unspecified double parameters |
| `defaultInt` | 1 | Default placeholder value for unspecified integer parameters |

## Hospital Configuration

| Parameter | Value | Usage |
|-----------|-------|-------|
| `hospitalCapacity` | 120 | Total hospital capacity (ICU + Ward beds). Used to initialize Hospital object (line 200) |
| `icuCapacity` | 20 | Number of ICU beds. Ward capacity = hospitalCapacity - icuCapacity |

## Admission-Discharge-Transfer (ADT) Parameters

| Parameter | Value | Usage |
|-----------|-------|-------|
| `admissionsRate` | 0.05 | Mean inter-arrival time (days) for patient admissions. Used in Admission process (line 202) |
| `icuAdmitProbability` | 0.15 | Probability that newly admitted patient goes directly to ICU |
| `dischargeShape` | 1.253 | Shape parameter for Gamma distribution of ward patient length of stay (days) |
| `dischargeScale` | 0.768 | Scale parameter for Gamma distribution of ward patient length of stay (days) |
| `icuDischargeShape` | 0.916 | Shape parameter for Gamma distribution of ICU patient length of stay (days) |
| `icuDischargeScale` | 0.820 | Scale parameter for Gamma distribution of ICU patient length of stay (days) |
| `icuTransferProbability` | 0.1 | Probability that ICU patient transfers to ward (vs. discharge) |
| `icuTransferShape` | 0.5 | Shape parameter for Gamma distribution of time until ICU-to-ward transfer (days) |
| `icuTransferScale` | 1.0 | Scale parameter for Gamma distribution of time until ICU-to-ward transfer (days) |
| `generalMortality` | 0.1 | General mortality probability (currently uses defaultDouble) |

## Therapy Needs Parameters

| Parameter | Value | Usage |
|-----------|-------|-------|
| `needsRt` | 0.1 | Probability that ward patient needs respiratory therapy |
| `needsPt` | 0.1 | Probability that ward patient needs physical therapy |
| `needsOt` | 0.1 | Probability that ward patient needs occupational therapy |
| `needsRtIcu` | 1.0 | Probability that ICU patient needs respiratory therapy |
| `needsPtIcu` | 0.1 | Probability that ICU patient needs physical therapy (inherits from needsPt) |
| `needsOtIcu` | 0.1 | Probability that ICU patient needs occupational therapy (inherits from needsOt) |

## Healthcare Worker Visit Timing Parameters

All timing parameters are in **minutes**. Shape and scale define Gamma distributions for inter-visit times.

| Parameter | Value | Usage |
|-----------|-------|-------|
| `nurseIntraVisitShape` | 0.54 | Shape parameter for Gamma distribution of time between nurse visits to ward patients (line 259) |
| `nurseIntraVisitScale` | 55.1 | Scale parameter for Gamma distribution of time between nurse visits to ward patients |
| `nurseICUIntraVisitShape` | 0.54 | Shape parameter for Gamma distribution of time between nurse visits to ICU patients (line 273) |
| `nurseICUIntraVisitScale` | 20 | Scale parameter for Gamma distribution of time between nurse visits to ICU patients |
| `doctorIntraVisitShape` | 0.52 | Shape parameter for Gamma distribution of time between doctor visits to ward patients (line 229) |
| `doctorIntraVisitScale` | 90.7 | Scale parameter for Gamma distribution of time between doctor visits to ward patients |
| `doctorIcuIntraVisitShape` | 0.52 | Shape parameter for Gamma distribution of time between doctor visits to ICU patients (line 244) |
| `doctorIcuIntraVisitScale` | 35.3 | Scale parameter for Gamma distribution of time between doctor visits to ICU patients |
| `specialistIntraVisitShape` | 0.62 | Shape parameter for Gamma distribution of time between specialist (RT/PT/OT) visits (lines 305, 317, 328) |
| `specialistIntraVisitScale` | 61.7 | Scale parameter for Gamma distribution of time between specialist visits |
| `roomVisitDuration` | 6.6 | Duration of room visit in minutes (currently defined but not actively used in code) |

## Staffing Ratios

These ratios determine the number of HCWs created per patient/bed.

| Parameter | Value | Usage |
|-----------|-------|-------|
| `nursesPerPatient` | 0.2 | Ward nurses per bed. Creates (hospitalCapacity-icuCapacity)*nursesPerPatient ward nurses (line 253) |
| `physiciansPerPatient` | 0.2 | Ward physicians per bed. Creates (hospitalCapacity-icuCapacity)*physiciansPerPatient ward doctors (line 223) |
| `icuNursesPerPatient` | 0.5 | ICU nurses per bed. Creates icuCapacity*icuNursesPerPatient ICU nurses (line 267) |
| `icuPhysiciansPerPatient` | 0.3 | ICU physicians per bed. Creates icuCapacity*icuPhysiciansPerPatient ICU doctors (line 238) |
| `icuRtsPerPatient` | 0.1 | ICU respiratory therapists per bed. Creates icuCapacity*icuRtsPerPatient ICU RTs (line 281) |
| `rtsPerPatient` | 0.1 | Respiratory therapists per total hospital capacity. Uses defaultDouble (line 298) |
| `ptsPerPatient` | 0.1 | Physical therapists per total hospital capacity. Uses defaultDouble (line 310) |
| `otsPerPatient` | 0.1 | Occupational therapists per total hospital capacity. Uses defaultDouble (line 322) |

## Transmission Parameters

Parameters governing MRSA transmission dynamics. **Most are placeholder values needing proper values.**

| Parameter | Value | Usage |
|-----------|-------|-------|
| `cleanPtColonizeddHcwColonizationProb` | 0.1 | Probability of clean patient acquiring colonization from colonized HCW. Uses defaultDouble |
| `contaminedPtCleanHcwColonizationProb` | 0.1 | Probability of clean HCW acquiring contamination from contaminated patient. Uses defaultDouble |

## Hand Hygiene and PPE Adherence

| Parameter | Value | Usage |
|-----------|-------|-------|
| `hhAdherenceBase` | 0.5 | Base hand hygiene adherence rate (placeholder) |
| `nurseHhAdherence` | 0.5 | Nurse hand hygiene adherence before patient contact (lines 255, 269). Inherits from hhAdherenceBase |
| `doctorHhAdherence` | 0.5 | Doctor hand hygiene adherence before patient contact (lines 225, 240). Inherits from hhAdherenceBase |
| `therapistHhAdherence` | 0.5 | Therapist hand hygiene adherence before patient contact (lines 284, 300, 312, 324). Inherits from hhAdherenceBase |
| `nurseHhAdherencePost` | 0.5 | Nurse hand hygiene adherence after patient contact (lines 256, 270). Inherits from hhAdherenceBase |
| `doctorHhAdherencePost` | 0.5 | Doctor hand hygiene adherence after patient contact (lines 226, 241). Inherits from hhAdherenceBase |
| `therapistHhAdherencePost` | 0.5 | Therapist hand hygiene adherence after patient contact (line 285). Inherits from hhAdherenceBase |
| `ppeAdherenceIfCp` | 0.5 | PPE (glove) adherence when visiting contact precaution patients (lines 227, 242, 257, 271, 286, 302, 314, 325) |

## Disease Importation and Mortality

| Parameter | Value | Usage |
|-----------|-------|-------|
| `admitImportationInfectionProbability` | 0.01 | Probability that newly admitted ward patient is colonized with MRSA |
| `admitImportationInfectionProbabilityICU` | 0.01 | Probability that newly admitted ICU patient is colonized with MRSA (inherits from ward value) |
| `importerDieProbability` | 0.1 | Mortality probability for colonized ward patients |
| `importerDiePrombabilityicu` | 0.1 | Mortality probability for colonized ICU patients (inherits from ward value, note typo in variable name) |

## Parameter Status Summary

- **Parameters with specific values**: ADT parameters (admission rates, LOS distributions, transfer probabilities), HCW visit timing, staffing ratios for ICU and ward doctors/nurses
- **Parameters using placeholder values**: Therapy needs probabilities, transmission probabilities, hand hygiene/PPE adherence rates, disease importation/mortality rates, ward therapist staffing ratios
- **Note**: Any parameter set to `defaultDouble` (0.1) or `defaultInt` (1) or round placeholder values (0.5, 1.0) should be considered as using placeholder values that may need adjustment based on data or literature
