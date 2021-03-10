package com.javaudemy.SpringBoot_Ionic.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.javaudemy.SpringBoot_Ionic.domain.SlipPayment;

@Service
public class SlipService {

	public void fillPayment(SlipPayment pay, Date instant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pay.setEndDate(cal.getTime());
	}
}
