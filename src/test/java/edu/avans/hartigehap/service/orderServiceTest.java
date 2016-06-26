package edu.avans.hartigehap.service;

import static org.junit.Assert.*;



import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.MenuItem;
import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class orderServiceTest extends AbstractTransactionRollbackTest {
	@Autowired
	OrderService orderservice;
	
	@Test
    public void dummy() {
        // empty - tests configuration of test context.
    }


	@Test
	public void testStates() throws StateException {
		Order order = new Order();
		assertTrue(order.getMyState().getStatusType().contains("created"));

	}
	
	

}
