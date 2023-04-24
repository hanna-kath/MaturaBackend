package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MedicationRepository extends PagingAndSortingRepository<Medication, UUID> {
}
