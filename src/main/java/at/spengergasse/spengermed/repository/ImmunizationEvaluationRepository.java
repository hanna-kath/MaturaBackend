package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ImmunizationEvaluationRepository extends PagingAndSortingRepository<ImmunizationEvaluation, UUID> {
}
