package com.atpl.mmg.AandA.dao.profile;

import java.util.List;

import com.atpl.mmg.AandA.domain.profile.AddressDomain;

public interface AddressDAO {
	public AddressDomain save(AddressDomain addressDomain) throws Exception;

	String update(AddressDomain addressDomain) throws Exception;

//	List<AddressDomain> getAddressListsByProfileId(String profileId) throws Exception;
	
	List<AddressDomain> getAddressListsByProfileId(String profileId,int roleId,boolean isActive) throws Exception;

	AddressDomain getAddressByUuid(String uuid) throws Exception;

	List<AddressDomain> getAddressByTypeAndProfileId(String type, String profileId) throws Exception;

	List<AddressDomain> getAddressByTypeAndProfileIdWithRoleId(String type, String profileId, int roleId) throws Exception;
	
	AddressDomain getActiveAddressByTypeAndProfileIdWithRoleId(String type, String profileId, int roleId) throws Exception;

	AddressDomain getAddressByvalidateAddress(AddressDomain addressDomain) throws Exception;
	
	public String activateDeactivateAddress(String uuid, boolean isActive) throws Exception;
	
	public String updateAddressRoleId(String uuid, int roleId) throws Exception;

}
