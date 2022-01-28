package com.atpl.mmg.service.timedimension;

import java.util.List;

import com.atpl.mmg.model.timedimension.TimeDimensionModel;

public interface TimeDimensionService {

	public List<TimeDimensionModel> getDate(TimeDimensionModel timeDimensionModel) throws Exception;
	
	public TimeDimensionModel getWeekDays(String date) throws Exception;
}
