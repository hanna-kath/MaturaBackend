package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Immunization;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ImmunizationRepository extends PagingAndSortingRepository<Immunization, UUID> {
}
