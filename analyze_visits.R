library(dplyr)

# Read visit data
df <- read.table("visit_data.txt", header = TRUE, sep = ",", stringsAsFactors = FALSE)

cat("=== DATA OVERVIEW ===\n")
cat("Total rows:", nrow(df), "\n")
cat("Columns:", paste(colnames(df), collapse=", "), "\n\n")

cat("=== UNIQUE VALUES ===\n")
cat("HCW Types:", paste(unique(df$hcwType), collapse=", "), "\n")
cat("Patient Locations:", paste(unique(df$patientLocation), collapse=", "), "\n")
cat("Unique HCWs:", n_distinct(df$hcwId), "\n")
cat("Unique Patients:", n_distinct(df$patientId), "\n\n")

cat("=== SUMMARY BY HCW TYPE AND LOCATION ===\n")
summary_table <- df %>%
  group_by(hcwType, patientLocation) %>%
  summarise(
    total_visits = n(),
    unique_hcws = n_distinct(hcwId),
    unique_patients = n_distinct(patientId),
    .groups = 'drop'
  )

print(summary_table)

cat("\n=== VISITS BY HCW TYPE (ALL) ===\n")
by_hcw_type <- df %>%
  group_by(hcwType) %>%
  summarise(
    total_visits = n(),
    unique_hcws = n_distinct(hcwId),
    visits_per_hcw = n() / n_distinct(hcwId),
    .groups = 'drop'
  )
print(by_hcw_type)

