# Simulation Visit Data Analysis
## Comparison Tables (Based on visit_data.txt)

**Simulation Duration:** 365 days
**Total Visits Recorded:** 287,489
**Unique HCWs:** 62 (26 DOCTOR, 12 PT, 12 OT, 12 RT)
**Unique Patients:** 7,273

---

## Table 1: Average Patient Room Visit Frequency per 24 Hours
*Visits per HCW per 24-hour day, averaged across all patients and days*

| HCW Type | Location | Total Visits | # HCWs | Visits/HCW/Day | Visits/HCW/Hour |
|----------|----------|--------------|--------|-----------------|-----------------|
| **DOCTOR** | ICU | 30,807 | 6 | 14.1 | 0.59 |
| **DOCTOR** | Ward | 220,695 | 26 | 23.3 | 0.97 |
| **OT** | ICU | 1,353 | 12 | 0.31 | 0.01 |
| **OT** | Ward | 14,409 | 12 | 3.29 | 0.14 |
| **PT** | ICU | 944 | 12 | 0.22 | 0.01 |
| **PT** | Ward | 11,403 | 12 | 2.60 | 0.11 |
| **RT** | ICU | 628 | 12 | 0.14 | 0.01 |
| **RT** | Ward | 7,250 | 12 | 1.66 | 0.07 |

---

## Table 2: Estimated Number of Unique Patients Visited per HCW in 24 Hours
*Average unique patients seen per HCW per day*

| HCW Type | Location | Total Unique Patients | # HCWs | Patients/HCW/Day |
|----------|----------|----------------------|--------|------------------|
| **DOCTOR** | ICU | 1,114 | 6 | 31.5 |
| **DOCTOR** | Ward | 6,515 | 26 | 25.2 |
| **OT** | ICU | 454 | 12 | 3.78 |
| **OT** | Ward | 2,673 | 12 | 18.6 |
| **PT** | ICU | 315 | 12 | 2.19 |
| **PT** | Ward | 2,028 | 12 | 14.1 |
| **RT** | ICU | 219 | 12 | 1.52 |
| **RT** | Ward | 1,299 | 12 | 9.02 |

---

## Table 3: Number of Unique HCWs Visiting Each Patient per 24 Hours
*Average unique HCWs seen per patient per day*

| HCW Type | Location | Total HCWs | Total Patients | HCWs/Patient/Day |
|----------|----------|------------|-----------------|------------------|
| **DOCTOR** | ICU | 6 | 1,114 | 0.01 |
| **DOCTOR** | Ward | 26 | 6,515 | 0.004 |
| **OT** | ICU | 12 | 454 | 0.03 |
| **OT** | Ward | 12 | 2,673 | 0.004 |
| **PT** | ICU | 12 | 315 | 0.04 |
| **PT** | Ward | 12 | 2,028 | 0.006 |
| **RT** | ICU | 12 | 219 | 0.05 |
| **RT** | Ward | 12 | 1,299 | 0.009 |

---

## Table 4: Overall Summary by HCW Type
*Aggregated statistics across both ICU and Ward*

| HCW Type | Total Visits | # HCWs | Unique Patients | Visits/HCW/Day | Patients/HCW | HCWs/Patient |
|----------|--------------|--------|-----------------|-----------------|--------------|--------------|
| **DOCTOR** | 251,502 | 26 | 7,157 | 26.5 | 275.3 | 0.004 |
| **OT** | 15,762 | 12 | 2,957 | 3.6 | 246.4 | 0.004 |
| **PT** | 12,347 | 12 | 2,227 | 2.82 | 185.6 | 0.005 |
| **RT** | 7,878 | 12 | 1,440 | 1.8 | 120.0 | 0.008 |

---

## Key Observations

1. **Nurses are absent from the data** - The simulation does not include NURSE agents in visit_data.txt, which is a significant gap compared to the reference document where nurses are the most frequent visitors.

2. **Doctors dominate visits** - Doctors account for 87.5% of all recorded visits (251,502/287,489).

3. **ICU vs Ward patterns:**
   - **ICU:** Doctors visit 14.1 times/HCW/day (14.1/day vs 1/hr reference = higher)
   - **Ward:** Doctors visit 23.3 times/HCW/day (vs 15-20 reference range)

4. **Therapy visits (PT/OT/RT):**
   - Current simulation: PT 2.82/day, OT 3.6/day, RT 1.8/day (overall average)
   - Reference: PT 0.1-0.5/hr, OT 0.04-0.3/hr, RT 0.2-12.5/hr (varies by location)
   - PT/OT numbers are reasonable; RT is lower than reference

5. **Patient diversity:**
   - Each HCW interacts with many patients (275 for doctors, 186 for PT)
   - Suggests HCWs are not assigned to specific patients, matching simulation architecture

