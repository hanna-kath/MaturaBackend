package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.RiskAssessment;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface RiskAssessmentRepository extends PagingAndSortingRepository<RiskAssessment, UUID> {
}
