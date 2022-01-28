package com.atpl.mmg.dao.timedimension;

import java.util.List;

import com.atpl.mmg.constant.Weeks;
import com.atpl.mmg.domain.timedimension.TimeDimensionDomain;

public interface TimeDimensionDAO {
	
	public List<TimeDimensionDomain> getDate(String contractStartDate,String contractEndDate,String day) throws Exception;

	public TimeDimensionDomain getWeekDays(String date) throws Exception;
}
