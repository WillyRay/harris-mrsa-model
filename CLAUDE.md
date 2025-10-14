# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an **Agent-Based Model (ABM)** for simulating MRSA transmission in a hospital setting, built using the **Repast Simphony** framework. The model simulates patient admissions, transfers, discharges, healthcare worker (HCW) interactions, and potential disease transmission dynamics.

## Technology Stack

- **Framework**: Repast Simphony (Java-based ABM framework)
- **Language**: Java (previously Groovy, now ported to Java)
- **IDE**: Eclipse (configured via `.project`, `.classpath`)
- **Analysis**: R with Quarto (.qmd files) for data analysis and visualization
- **Dependencies**: Apache Commons Math3 for statistical distributions

## Project Structure

### Core Java Packages

- **`builders/`**: Contains `Builder.java` - the main model initialization and parameter configuration class
  - Implements `ContextBuilder<Object>` - the Repast Simphony entry point
  - Configures model parameters (hospital capacity, staffing ratios, disease transmission probabilities)
  - Scheduled methods for simulation lifecycle (daily operations, end-of-run outputs)

- **`agents/`**: Agent classes representing entities in the simulation
  - `Agent.java` - Base agent class with attribute storage
  - `Patient.java` - Patient agents with admission/discharge/transfer tracking
  - `HealthCareWorker.java` - HCW agents (doctors, nurses)
  - `Therapist.java` - Specialized HCWs (RT, PT, OT)
  - `DischargedPatient.java` - Record object for discharged patients

- **`containers/`**:
  - `Hospital.java` - Main container managing patients, HCWs, beds (ICU/ward), discharges
  - `Discharged.java` - Container for discharged patient records

- **`processes/`**: Event-driven processes using stochastic timing
  - `Process.java` - Abstract base class for scheduled processes with exponential distributions
  - `Admission.java` - Handles patient admissions
  - `Discharge.java` - Handles patient discharges (separate ICU/ward parameterizations)
  - `Transfer.java` - Handles ICU-to-ward transfers
  - `PatientVisit.java` - Schedules HCW visits to patients
  - `AssignedVisit.java`, `SpecialtyVisit.java` - Visit specializations

- **`processes/disease/`**: Disease-related enumerations
  - `DiseaseTypes.java`, `DiseaseStates.java`

- **`events/`**: Event communication system
  - `EventSender.java`, `EventListener.java`

- **`outputs/`**: Output file generation
  - `OutputFileSingle.java`, `DischargedLos.java`

### R Analysis Files

Located in `R/`:
- **`analysis.qmd`**: Comprehensive ADT (Admission-Discharge-Transfer) analysis report
  - LOS (length of stay) distributions by location
  - ICU vs Ward admission statistics
  - Transfer patterns and timing
  - HCW visit summaries by type
- **`visits.qmd`**, **`visits2.qmd`**: HCW visit analysis with detailed histograms and summary statistics
- Generated PDFs: `analysis.pdf`, `visits.pdf`, `visits2.pdf`

### Configuration Files

- **`harris-mrsa-model.rs/`**: Repast Simphony scenario configuration
  - `parameters.xml` - Model parameters (editable via Repast GUI)
  - `scenario.xml` - Scenario metadata
  - `context.xml` - Simulation context configuration
  - Data loaders, chart configurations, launch properties

## Running the Model

### From Eclipse/Repast Simphony IDE

1. Open the project in Eclipse with Repast Simphony installed
2. Right-click on the project → Run As → Repast Simphony Model
3. The model initializes via `Builder.build()` and runs for 365 ticks (days)
4. Output files are generated at the end of the run

### Model Outputs

The simulation generates two primary output files:

1. **`discharged_patients.txt`** - CSV with columns:
   - `agentId`, `admitTime`, `dischargeTime`, `icuAdmit`, `transferTime`, `admitLocation`, `dischargeLocation`

2. **`visit_data.txt`** - CSV with columns:
   - `hcwId`, `hcwType`, `patientId`, `patientLocation`, `visitTime`

### Analyzing Results

After running the simulation:

```r
# From R directory
quarto render analysis.qmd
quarto render visits.qmd
```

This generates PDF reports with:
- ADT statistics and visualizations
- LOS distributions
- HCW visit patterns and workload analysis

## Key Model Parameters

Defined in `Builder.java` (lines 47-82):

- **Hospital Configuration**: `hospitalCapacity` (85), `icuCapacity` (15)
- **ADT Parameters**:
  - `admissionsRate` (0.2) - mean inter-arrival time
  - `dischargeShape/Scale` - Gamma distribution parameters for ward discharges
  - `icuDischargeShape/Scale` - Gamma parameters for ICU discharges
  - `icuAdmitProbability` (0.15)
  - `icuTransferProbability` (0.1) with Gamma timing
- **Staffing Ratios**:
  - `nursesPerPatient` (0.3)
  - `physiciansPerPatient` (0.2)
  - `rtsPerPatient`, `ptsPerPatient`, `otsPerPatient` (currently 0.0)
- **Visit Patterns**:
  - `nurseIntraVisitShape/Scale` - Gamma(0.585, 48.22) for time between nurse visits

## Architecture Patterns

### Process-Based Scheduling

The model uses a discrete-event simulation pattern:
- `Process.java` uses `RealDistribution` (typically Exponential or Gamma) for inter-event times
- Each process schedules itself recursively via `start()` → `fire()` → `start()`
- Built on Repast's `ISchedule` system

### Agent Lifecycle

1. **Admission** (`Admission.java`): Creates `Patient`, assigns to ICU/Ward, schedules discharge
2. **Transfer** (`Transfer.java`): Moves patient ICU→Ward based on scheduled time
3. **Visits** (`PatientVisit.java`): HCWs visit patients stochastically
4. **Discharge** (`Discharge.java`): Removes patient, creates `DischargedPatient` record

### Data Collection

- `Hospital.visitData` - StringBuffer accumulating visit records
- `Hospital.dischargedPatients` - ArrayList of DischargedPatient objects
- Written to files in `Builder.writeSingleRunFiles()` at tick 365

## Development Notes

- **Recent Migration**: Code was ported from Groovy to Java (see commit history)
- **Therapy Needs**: Daily reset of `patientsNeedingOt/Pt/Rt` lists (Builder.daily() at line 89)
- **HCW Assignment**: Doctors and nurses currently use generic PatientVisit (see lines 153-156, note about assignment procedure)
- **Disease Model**: Disease types/states defined but transmission logic not yet fully implemented

## Known TODOs (from analysis.qmd)

- Fix HCW visit inter-event times (gamma() + gamma() for intra-visit + duration)
- Improve HCW-to-patient assignment procedures for nurses and doctors
- Implement disease transmission probabilities (currently defined as parameters but not used)

## File Locations

- Java source: `src/`
- Compiled classes: `bin/` (gitignored)
- R analysis: `R/`
- Documentation: `docs/`
- Model configuration: `harris-mrsa-model.rs/`
- Output data: Root directory (`discharged_patients.txt`, `visit_data.txt`)
