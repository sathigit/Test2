package com.atpl.mmg.AandA.service.address;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.model.profile.Address;

public interface AddressService {

	public String save(Address address) throws Exception;
	
	public List<Address> getAddresses(String profileId,@RequestParam Map<String,String> reqParam) throws Exception;
	
	public Address getAddressesByUuid(String uuid) throws Exception;
	
	public String update(Address address) throws Exception;
	
    public String activateAddress(String uuid)throws Exception;
    
    public String deActivateAddress(String uuid)throws Exception;

}
