package com.atpl.mmg.dao.fdtrans;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.constant.Role;
import com.atpl.mmg.domain.fdtrans.FareDistributionTransactionDomain;

public interface FareDistributionTransactionDAO {

	public FareDistributionTransactionDomain save(FareDistributionTransactionDomain fareDistributionTransactionDomain)
			throws Exception;

	public String update(FareDistributionTransactionDomain fareDistributionTransactionDomain) throws Exception;

	public List<FareDistributionTransactionDomain> getFareDistributioneTrans() throws Exception;

	public FareDistributionTransactionDomain getFareDistributionTrans(String uuid) throws Exception;

	public FareDistributionTransactionDomain getFareDistributionTrans(BigInteger bookingId) throws Exception;

	public List<FareDistributionTransactionDomain> getFDTransDetails(String companyProfileId, Role role, int limit)
			throws Exception;

	public List<FareDistributionTransactionDomain> getFDTransDetails(String companyProfileId, Role role,
			String startDate, String endDate) throws Exception;
}
