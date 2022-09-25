package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Encounter;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

//PagingAndSortingRepository kann mehr als CRUD
//Datenbankzugriff
public interface EncounterRepository extends PagingAndSortingRepository<Encounter, UUID> {
}
