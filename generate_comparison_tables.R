library(dplyr)

# Read visit data
df <- read.table("visit_data.txt", header = TRUE, sep = ",", stringsAsFactors = FALSE)

# Simulation runs for 365 days (ticks)
sim_days <- 365

cat("=== TABLE 1: Average Patient Room Visit Frequency per 24 Hours ===\n")
cat("(Visits per HCW per 24-hour day, across all patients and days)\n\n")

table1 <- df %>%
  group_by(hcwType, patientLocation) %>%
  summarise(
    total_visits = n(),
    unique_hcws = n_distinct(hcwId),
    visits_per_hcw_per_day = n() / (n_distinct(hcwId) * sim_days),
    visits_per_hcw_per_hour = n() / (n_distinct(hcwId) * sim_days * 24),
    .groups = 'drop'
  ) %>%
  mutate(
    visits_per_hcw_per_day = round(visits_per_hcw_per_day, 2),
    visits_per_hcw_per_hour = round(visits_per_hcw_per_hour, 2)
  )

print(table1)

cat("\n=== TABLE 2: Estimated Number of Unique Patients Visited per HCW in 24 Hours ===\n\n")

table2 <- df %>%
  group_by(hcwType, patientLocation) %>%
  summarise(
    unique_patients_total = n_distinct(patientId),
    unique_hcws = n_distinct(hcwId),
    unique_patients_per_hcw = n_distinct(patientId) / n_distinct(hcwId),
    .groups = 'drop'
  ) %>%
  mutate(
    unique_patients_per_hcw = round(unique_patients_per_hcw, 2)
  )

print(table2)

cat("\n=== TABLE 3: Number of Unique HCWs Visiting Each Patient per 24 Hours ===\n\n")

table3 <- df %>%
  group_by(hcwType, patientLocation) %>%
  summarise(
    unique_hcws_total = n_distinct(hcwId),
    unique_patients = n_distinct(patientId),
    unique_hcws_per_patient = n_distinct(hcwId) / n_distinct(patientId),
    .groups = 'drop'
  ) %>%
  mutate(
    unique_hcws_per_patient = round(unique_hcws_per_patient, 2)
  )

print(table3)

cat("\n=== TABLE 4: Overall Summary by HCW Type ===\n\n")

table4 <- df %>%
  group_by(hcwType) %>%
  summarise(
    total_visits = n(),
    unique_hcws = n_distinct(hcwId),
    unique_patients = n_distinct(patientId),
    visits_per_hcw_per_day = n() / (n_distinct(hcwId) * sim_days),
    patients_per_hcw = n_distinct(patientId) / n_distinct(hcwId),
    hcws_per_patient = n_distinct(hcwId) / n_distinct(patientId),
    .groups = 'drop'
  ) %>%
  mutate(
    visits_per_hcw_per_day = round(visits_per_hcw_per_day, 2),
    patients_per_hcw = round(patients_per_hcw, 2),
    hcws_per_patient = round(hcws_per_patient, 2)
  )

print(table4)

