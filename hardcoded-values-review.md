# Hard-Coded Values Review

This document identifies hard-coded numerical values in the source code that should potentially be elevated to Builder class parameters for better configurability.

## Found Hard-Coded Values

### 1. Room Visit Duration (Already Has Parameter, Not Being Used)
**Location**: `src/processes/PatientVisit.java:58`
```java
double elapse = distro.sample()*TimeUtils.MINUTE + 6.6*TimeUtils.MINUTE;
```
**Issue**: The value `6.6` is hard-coded, but `roomVisitDuration` parameter already exists in Builder.java (line 83).
**Recommendation**: Pass `roomVisitDuration` from Builder to PatientVisit and use it instead of the hard-coded value.

---

### 2. Initial Visit Check Times for HCWs
**Location**: `src/builders/Builder.java`, `buildHealthCareWorkers()` method

These values control when each HCW type makes their first visit check after simulation starts (in days).

#### Ward Doctors
**Line 228**: `new PatientVisit(0.02, hospital, doc1)`
- Hard-coded value: **`0.02`** days (~29 minutes)
- Used for: Ward physicians' initial visit check time

#### ICU Doctors
**Line 243**: `new PatientVisit(0.04, hospital, doc1)`
- Hard-coded value: **`0.04`** days (~58 minutes)
- Used for: ICU physicians' initial visit check time

#### Ward Nurses
**Line 258**: `new PatientVisit(0.02, hospital, nurse)`
- Hard-coded value: **`0.02`** days (~29 minutes)
- Used for: Ward nurses' initial visit check time

#### ICU Nurses
**Line 272**: `new PatientVisit(1.0/3.0, hospital, nurse)`
- Hard-coded value: **`1.0/3.0`** days (8 hours)
- Used for: ICU nurses' initial visit check time

#### ICU Respiratory Therapists
**Line 287**: `new PatientVisit(1.0/3.0, hospital, icuRt)`
- Hard-coded value: **`1.0/3.0`** days (8 hours)
- Used for: ICU RTs' initial visit check time

#### Ward Respiratory Therapists
**Line 304**: `new PatientVisit(0.1, hospital, hcw)`
- Hard-coded value: **`0.1`** days (~2.4 hours)
- Used for: Ward RTs' initial visit check time

#### Ward Physical Therapists
**Line 316**: `new PatientVisit(0.1, hospital, hcw)`
- Hard-coded value: **`0.1`** days (~2.4 hours)
- Used for: Ward PTs' initial visit check time

#### Ward Occupational Therapists
**Line 327**: `new PatientVisit(0.02, hospital, hcw)`
- Hard-coded value: **`0.02`** days (~29 minutes)
- Used for: Ward OTs' initial visit check time

---

### 3. Number of Nurses Assigned Per Patient
**Location**: `src/containers/Hospital.java:258-284`

The code assigns **2 nurses** to each patient (lines 258-261). This value appears to be implicit in the logic rather than parameterized.

**Current behavior**:
- If 2+ nurses available with minimum workload: assign 2
- If only 1 nurse at minimum: assign that nurse + find next-lowest workload nurse

**Recommendation**: Consider creating a `nursesPerPatientAssignment` parameter if this ratio should be configurable (currently it's hard-coded as 2 in the assignment algorithm).

---

## Recommended New Parameters

To address these hard-coded values, consider adding the following parameters to Builder.java:

```java
// Initial visit check times (in days)
private double wardDoctorInitialVisitTime = 0.02;
private double icuDoctorInitialVisitTime = 0.04;
private double wardNurseInitialVisitTime = 0.02;
private double icuNurseInitialVisitTime = 1.0/3.0;
private double icuRtInitialVisitTime = 1.0/3.0;
private double wardRtInitialVisitTime = 0.1;
private double wardPtInitialVisitTime = 0.1;
private double wardOtInitialVisitTime = 0.02;

// Nurse assignment
private int nursesPerPatientAssignment = 2;  // Number of nurses assigned to each patient
```

---

## Priority Assessment

**High Priority**:
1. Fix `roomVisitDuration` usage in PatientVisit.java (parameter exists but not used)

**Medium Priority**:
2. Parameterize initial visit check times for all HCW types
   - These affect model warm-up and early simulation dynamics
   - Currently appear to be arbitrary values

**Low Priority**:
3. Consider parameterizing nurses-per-patient assignment ratio
   - Only if this needs to vary across experiments
   - Currently works well as implicit logic

---

## Notes

- The initial visit check times don't affect steady-state behavior significantly, but they do impact the warm-up period
- All subsequent visit timing is controlled by the Gamma distributions (which are properly parameterized)
- The `roomVisitDuration` is already a parameter but the code doesn't reference it - this should be fixed
