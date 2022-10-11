package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConditionRepository extends JpaRepository<Condition, UUID> {
}
