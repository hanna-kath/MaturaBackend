package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.Ingredient;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, UUID> {
}
