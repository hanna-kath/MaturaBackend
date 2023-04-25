package at.spengergasse.spengermed.repository;

import at.spengergasse.spengermed.model.InventoryReport;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InventoryReportRepository extends PagingAndSortingRepository<InventoryReport, UUID> {
}
