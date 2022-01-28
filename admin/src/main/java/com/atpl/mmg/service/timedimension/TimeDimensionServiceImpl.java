package com.atpl.mmg.service.timedimension;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Weeks;
import com.atpl.mmg.dao.timedimension.TimeDimensionDAO;
import com.atpl.mmg.domain.timedimension.TimeDimensionDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.timedimension.TimeDimensionMapper;
import com.atpl.mmg.model.timedimension.TimeDimensionModel;

@Service("timeDimensionService")
public class TimeDimensionServiceImpl implements TimeDimensionService {

	@Autowired
	TimeDimensionDAO timeDimensionDAO;

	@Autowired
	TimeDimensionMapper timeDimensionMapper;

	List<TimeDimensionDomain> dates = new ArrayList<TimeDimensionDomain>();
	List<TimeDimensionDomain> allDate = new ArrayList<TimeDimensionDomain>();

	private static final Logger logger = LoggerFactory.getLogger(TimeDimensionServiceImpl.class);

	public TimeDimensionServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Modified Date: 26/2/2020 
	 * 
	 */
	@Override
	public List<TimeDimensionModel> getDate(TimeDimensionModel timeDimensionModel) throws Exception {
		List<TimeDimensionDomain> allDateDomain = new ArrayList<TimeDimensionDomain>();
		List<TimeDimensionDomain> dateDomain = new ArrayList<TimeDimensionDomain>();
		validateWeeks(timeDimensionModel);
		if (timeDimensionModel.isMonday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Monday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		if (timeDimensionModel.isTuesday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Tuesday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		if (timeDimensionModel.isWednesday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Wednesday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		if (timeDimensionModel.isThursday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Thursday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		if (timeDimensionModel.isFriday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Friday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		if (timeDimensionModel.isSaturday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Saturday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		if (timeDimensionModel.isSunday() == true) {
			dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
					timeDimensionModel.getContractEndDate(), Weeks.Sunday.name());
			for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
				ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
				arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(),
						timeDimensionDomain.getDay_name(), timeDimensionDomain.getDay()));
				allDateDomain.addAll(arraylist);
			}
		}
		return timeDimensionMapper.entityList(allDateDomain);
	}

	/* Unused Method */
	private List<TimeDimensionDomain> contractStartAndEndDate(TimeDimensionModel timeDimensionModel, String day)
			throws Exception {
		List<TimeDimensionDomain> dateDomain = new ArrayList<TimeDimensionDomain>();
		dateDomain = timeDimensionDAO.getDate(timeDimensionModel.getContractStartDate(),
				timeDimensionModel.getContractEndDate(), day);
		for (TimeDimensionDomain timeDimensionDomain : dateDomain) {
			ArrayList<TimeDimensionDomain> arraylist = new ArrayList<TimeDimensionDomain>();
			arraylist.add(new TimeDimensionDomain(timeDimensionDomain.getDb_date(), timeDimensionDomain.getDay_name(),
					timeDimensionDomain.getDay()));
			dates.addAll(arraylist);
		}
		return dates;
	}

	private void validateWeeks(TimeDimensionModel timeDimensionModel) {
		if (timeDimensionModel.isSunday() != true && timeDimensionModel.isMonday() != true
				&& timeDimensionModel.isTuesday() != true && timeDimensionModel.isWednesday() != true
				&& timeDimensionModel.isThursday() != true && timeDimensionModel.isFriday() != true
				&& timeDimensionModel.isSaturday() != true)
			throw new NOT_FOUND("Week days not found");
	}

	/**
	 * Author:Vidya S K Modified Date: 25/2/2020 Description:
	 * 
	 */
	@Override
	public TimeDimensionModel getWeekDays(String date) throws Exception {
		TimeDimensionDomain timeDomain = timeDimensionDAO.getWeekDays(date);
		TimeDimensionModel timeModel = new TimeDimensionModel();
		if (null == timeDomain)
			throw new NOT_FOUND("Week days not found");
		BeanUtils.copyProperties(timeDomain, timeModel);
		return timeModel;

	}

}
