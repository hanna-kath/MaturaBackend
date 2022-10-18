package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.NutritionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NutritionOrderRepository extends JpaRepository<NutritionOrder, UUID> {
}
