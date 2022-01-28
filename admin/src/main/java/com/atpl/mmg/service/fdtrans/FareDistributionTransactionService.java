package com.atpl.mmg.service.fdtrans;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.model.faredist.EarningsModel;
import com.atpl.mmg.model.fdtrans.FareDistributionTransactionModel;

public interface FareDistributionTransactionService {

	public String save(FareDistributionTransactionModel fareDistributionTransactionModel) throws Exception;

	public EarningsModel getEarnings(int roleId, @RequestParam Map<String, String> reqParam) throws Exception;

	public EarningsModel getTotalEarningAndDetails(int roleId, @RequestParam Map<String, String> reqParam)
			throws Exception;

	public EarningsModel getEarningsDataForDownload(int roleId, @RequestParam Map<String, String> reqParam,
			String startDate, String endDate) throws Exception;

}
