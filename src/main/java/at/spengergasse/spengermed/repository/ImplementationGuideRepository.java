package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.ImplementationGuide;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ImplementationGuideRepository extends PagingAndSortingRepository<ImplementationGuide, UUID> {}
