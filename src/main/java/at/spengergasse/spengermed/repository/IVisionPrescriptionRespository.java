package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.VisionPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IVisionPrescriptionRespository extends JpaRepository<VisionPrescription, UUID> {
}
