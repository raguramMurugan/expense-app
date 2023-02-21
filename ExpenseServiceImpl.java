package com.hubino.expensetrackingapp.service;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.descriptor.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hubino.expensetrackingapp.entity.Activity;
import com.hubino.expensetrackingapp.entity.ExpenseTracker;
import com.hubino.expensetrackingapp.entity.Things;
import com.hubino.expensetrackingapp.repository.ExpenseTrackerRepository;
import com.hubino.expensetrackingapp.repository.ThingsRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	
	private static final Logger log=LoggerFactory.getLogger(ExpenseServiceImpl.class);
	
	@Autowired
	private ThingsRepository repo;
	
	@Autowired
	private ExpenseTrackerRepository expenseRepo;
	
	@Override
	public Double calculateBillAmount(List<Activity> act) {
		
		double totalAmount=0;
		double singleAmount=0;
		int availableQuantity=0;
		
		for(Activity activity:act)
		{
			Integer thingsId=activity.getThingsId();
			log.info("getting the thingsId from db"+thingsId);
			
			Optional<Things> thingsId1=repo.findById(thingsId);
			if(thingsId1.isPresent())
			{
				Things thingsIdFromDb=thingsId1.get();
				log.info("get the things from db if is not null"+ thingsIdFromDb);
				
				if(thingsIdFromDb.getAvailableQuantity() < activity.getQuantity())
				{
					singleAmount=thingsIdFromDb.getAvailableQuantity() * thingsIdFromDb.getPrice();
					log.info("singleAmount:"+singleAmount);
					
					activity.setQuantity(thingsIdFromDb.getAvailableQuantity());
					
					
				}
				else {
					singleAmount=activity.getQuantity() * thingsIdFromDb.getPrice();
					availableQuantity=thingsIdFromDb.getAvailableQuantity() - activity.getQuantity();
				}
				totalAmount=totalAmount + singleAmount;
				
				log.info("setting the quantity in db");
				thingsIdFromDb.setAvailableQuantity(availableQuantity);
				availableQuantity=0;
				
				activity.setThingsName(thingsIdFromDb.getThingsName());
				activity.setBillAmount(singleAmount);
				repo.save(thingsIdFromDb);
			}
		}
		return totalAmount;
	}

	@Override
	public void save(ExpenseTracker tracker) {
		expenseRepo.save(tracker);
		
	}

}
