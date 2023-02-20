package com.hubino.expensetrackingapp.service;

import com.hubino.expensetrackingapp.dto.TrackerDto;

public interface ExpenseService {

	Double calculateBillAmount(TrackerDto trackerDto);

}
