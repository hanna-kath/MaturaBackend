package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Practitioner;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

//PagingAndSortingRepository kann mehr als CRUD
//Datenbankzugriff
public interface PractitionerRepository extends PagingAndSortingRepository<Practitioner, UUID> {
    //um auf Practitionerdaten hinzufügen zu können
}
