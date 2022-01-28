package com.atpl.mmg.service.packaging;

import java.math.BigInteger;
import java.util.Map;

import com.atpl.mmg.model.packaging.PackagingModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface PackagingService {

	public String savePackaging(PackagingModel packagingModel) throws Exception;

	public ListDto getPackagingList(Map<String,String> reqParam) throws Exception;

	public PackagingModel getPackageDetails(BigInteger packagingId) throws Exception;

	public String updatePackagingStatus(PackagingModel packagingModel) throws Exception;

	public String deletePackaging(BigInteger packagingId) throws Exception;

	public String updatePackagingName(PackagingModel packagingModel) throws Exception;

}
