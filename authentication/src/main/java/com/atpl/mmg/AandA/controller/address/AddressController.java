package com.atpl.mmg.AandA.controller.address;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.profile.Address;
import com.atpl.mmg.AandA.service.address.AddressService;

@RestController
@RequestMapping("/v2")
@SuppressWarnings("rawtypes")
public class AddressController {

	@Autowired
	AddressService addressService;
	
	@RequestMapping(value = "/address", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody Address address) throws Exception {
		return prepareSuccessResponse(addressService.save(address));
	}

	@RequestMapping(value = "/address/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAddresses(@PathVariable("profileId") String profileId,@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(addressService.getAddresses(profileId,reqParam));
	}
	
	@RequestMapping(value = "/address/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAddressesByUuid(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(addressService.getAddressesByUuid(uuid));
	}
	
	@RequestMapping(value = "/address", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody Address address) throws Exception {
		return prepareSuccessResponse(addressService.update(address));
	}
	
	@RequestMapping(value = "/address/activate/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> activateTandC(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(addressService.activateAddress(uuid));
	}
	
	@RequestMapping(value = "/address/deactivate/{uuid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deActivateTandC(@PathVariable("uuid") String uuid) throws Exception {
		return prepareSuccessResponse(addressService.deActivateAddress(uuid));
	}

	
}
