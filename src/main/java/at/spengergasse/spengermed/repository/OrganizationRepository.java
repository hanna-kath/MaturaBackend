package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, UUID> {
}
