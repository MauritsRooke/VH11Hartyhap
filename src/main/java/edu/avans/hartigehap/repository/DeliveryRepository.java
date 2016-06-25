package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import edu.avans.hartigehap.domain.Delivery;

public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, String> {
}
