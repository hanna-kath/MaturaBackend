package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.InsurancePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, UUID> {
}
