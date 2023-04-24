package at.spengergasse.spengermed.repository;


import at.spengergasse.spengermed.model.Consent;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ConsentRepository extends PagingAndSortingRepository<Consent, UUID> {
}
