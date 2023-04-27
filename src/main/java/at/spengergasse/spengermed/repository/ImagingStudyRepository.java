package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.ImagingStudy;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ImagingStudyRepository extends PagingAndSortingRepository<ImagingStudy, UUID> {
}
