package edu.avans.hartigehap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.Delivery;
import edu.avans.hartigehap.repository.*;
import edu.avans.hartigehap.service.*;
import lombok.extern.slf4j.Slf4j;
@Service("DeliveryService")
@Repository
@Transactional
@Slf4j
public class DeliverySerivceImpl implements DeliveryService {
	
	@Autowired
    private DeliveryRepository deliveryRepo;
	
	public void saveDelivery(Delivery delivery){
		
		deliveryRepo.save(delivery);
	}

}
