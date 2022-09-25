package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.StructureMap;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface StructureMapRepository extends PagingAndSortingRepository<StructureMap, UUID> {
}
