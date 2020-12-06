package com.r2tech.validations;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ProjectValidations {
	
	@SuppressWarnings("static-access")
	public boolean isWeekEnd(Date ldtDate) {
		
		LocalDate ldt = ldtDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DayOfWeek day = DayOfWeek.of(ldt.get(ChronoField.DAY_OF_WEEK));
		
		return (day == day.SATURDAY || day == day.SUNDAY);
	}

}
