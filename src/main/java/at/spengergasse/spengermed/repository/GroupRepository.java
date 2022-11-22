package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Group;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface GroupRepository extends PagingAndSortingRepository<Group, UUID> {
}
