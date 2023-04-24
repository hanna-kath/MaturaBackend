package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
