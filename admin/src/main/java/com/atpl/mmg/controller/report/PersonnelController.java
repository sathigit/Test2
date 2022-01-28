package com.atpl.mmg.controller.report;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.report.PersonnelModel;
import com.atpl.mmg.service.report.PersonnelService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class PersonnelController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(PersonnelController.class);

	@Autowired
	PersonnelService personnelService;

	@RequestMapping(value = "/personnel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addDirectCost(@RequestBody PersonnelModel personnelModel) throws Exception {
		return prepareSuccessResponse(personnelService.addPersonnel(personnelModel));
	}

	@RequestMapping(value = "/personnelsource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addPersonnelSource(@RequestBody PersonnelModel personnelModel) throws Exception {
		return prepareSuccessResponse(personnelService.addPersonnelSource(personnelModel));
	}

	@RequestMapping(value = "/personnelsource", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPersonnelSource() throws Exception {
		return prepareSuccessResponse(personnelService.getPersonnelSource());
	}

	@RequestMapping(value = "/personnelsource/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getPersonnelSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(personnelService.getPersonnelSource(id));
	}

	@RequestMapping(value = "/personnelsource", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePersonnelSource(@RequestBody PersonnelModel personnelModel)
			throws Exception {
		return prepareSuccessResponse(personnelService.updatePersonnelSource(personnelModel));
	}

	@RequestMapping(value = "/personnelsource/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deletePersonnelSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(personnelService.deletePersonnelSource(id));
	}

}
