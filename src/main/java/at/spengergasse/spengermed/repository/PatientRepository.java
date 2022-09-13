package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Patient;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, UUID> {}
