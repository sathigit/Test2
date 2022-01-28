package com.atpl.mmg.controller.goodstype;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

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
import com.atpl.mmg.model.goodstype.GoodstypeModel;
import com.atpl.mmg.service.goodstype.GoodstypeService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("unused")
public class GoodstypeController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(GoodstypeController.class);

	@Autowired
	GoodstypeService organizationService;

	@RequestMapping(value = "/goodsType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveGoodsType(@RequestBody GoodstypeModel organizationModel) throws Exception {
		return prepareSuccessResponse(organizationService.saveGoodsType(organizationModel));
	}

	@RequestMapping(value = "/goodsType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getGoodsType(@RequestParam Map<String,String> reqParam) throws Exception {
		return prepareSuccessResponse(organizationService.getGoodsType(reqParam));
	}

	@RequestMapping(value = "/goodsType", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateGoodsType(@RequestBody GoodstypeModel organizationModel) throws Exception {
		return prepareSuccessResponse(organizationService.updateGoodsType(organizationModel));
	}

	@RequestMapping(value = "/goodsType/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getGoodsType(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(organizationService.getGoodsType(id));
	}

	@RequestMapping(value = "/goodsType/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getGoodsType(@PathVariable("name") String name) throws Exception {
		return prepareSuccessResponse(organizationService.getGoodsTypes(name));
	}

	@RequestMapping(value = "/goodsType/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteGoodsType(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(organizationService.deleteGoodsType(id));
	}

}
