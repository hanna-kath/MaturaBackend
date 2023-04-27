package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.InventoryItem;
import at.spengergasse.spengermed.model.Medication;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InventoryItemRepository extends PagingAndSortingRepository<InventoryItem, UUID> {
}
