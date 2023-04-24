package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.PractitionerRole;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PractitionerRoleRepository extends PagingAndSortingRepository<PractitionerRole, UUID> {
}
