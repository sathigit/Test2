package com.atpl.mmg.controller.packagetype;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.packagetype.PackageTypeModel;
import com.atpl.mmg.service.packagetype.PackageTypeService;

@RestController
@RequestMapping("/v1")
public class PackageTypeController implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(PackageTypeController.class);

	@Autowired
	PackageTypeService packageTypeService;

	@RequestMapping(value = "/package/type", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> save(@RequestBody PackageTypeModel packageTypeModel)
			throws Exception {
		return prepareSuccessResponse(packageTypeService.save(packageTypeModel));
	}

	@RequestMapping(value = "/package/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPackageType(@RequestParam( value="status", required = false) Boolean status) throws Exception {
		return prepareSuccessResponse(packageTypeService.getPackageType(status));
	}

	@RequestMapping(value = "/package/type/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPackageTypeById(@PathVariable("uuid") String uuId) throws Exception {
		return prepareSuccessResponse(packageTypeService.getPackageTypeById(uuId));
	}

	@RequestMapping(value = "/package/type/{uuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> delete(@PathVariable("uuid") String uuId) throws Exception {
		return prepareSuccessResponse(packageTypeService.delete(uuId));
	}

	@RequestMapping(value = "/package/type", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> update(@RequestBody PackageTypeModel packageTypeModel)
			throws Exception {
		return prepareSuccessResponse(packageTypeService.update(packageTypeModel));
	}
	
	@RequestMapping(value = "/package/type/{uuid}/status/{status}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateStatus(@PathVariable("uuid") String uuid,@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(packageTypeService.updateStatus(uuid,status));
	}


}
