package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.ImagingSelection;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ImagingSelectionRepository extends PagingAndSortingRepository<ImagingSelection, UUID> {
}
