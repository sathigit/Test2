package com.atpl.mmg.dao.fdtransdetail;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.domain.fdtransdetail.FareDistributionTransactionDetailDomain;

public interface FareDistributionTransactionDetailDAO {

	public String save(FareDistributionTransactionDetailDomain fareDistrbutionTransactionDetailDomain) throws Exception;

	public String update(FareDistributionTransactionDetailDomain fareDistrbutionTransactionDetailDomain)
			throws Exception;

	public List<FareDistributionTransactionDetailDomain> getFareDistributioneTransDetail() throws Exception;

	public List<FareDistributionTransactionDetailDomain> getFareDistributioneTransDetail(String fdType,
			String franchiseId, BigInteger bookingId) throws Exception;

	public FareDistributionTransactionDetailDomain getFareDistributionTransDetail(String uuid) throws Exception;
}
