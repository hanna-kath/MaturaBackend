package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.model.ImmunizationRecommendation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ImmunizationRecommendationRepository extends PagingAndSortingRepository<ImmunizationRecommendation, UUID> {
}
