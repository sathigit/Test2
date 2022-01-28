package com.atpl.mmg.controller.bookedItems;

import static com.atpl.mmg.exception.HttpResponseUtils.prepareSuccessResponse;

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

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.bookedItems.BookedItemsModel;
import com.atpl.mmg.service.bookedItems.BookedItemsService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class BookedItemsController {

	@Autowired
	BookedItemsService bookedItemsService;

	@RequestMapping(value = "/save/Qutation/bookedItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveBookedItem(@RequestBody BookedItemsModel bookedItemsModel) throws Exception {
		return prepareSuccessResponse(bookedItemsService.saveBookedItem(bookedItemsModel));
	}

	@RequestMapping(value = "/qutationBookedItem/{quotationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCancelReason(@PathVariable("quotationId") String quotationId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(bookedItemsService.getBookedItem(quotationId, reqParam));
	}

	@RequestMapping(value = "/upadte/Qutation/bookedItem", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateBookedItem(@RequestBody BookedItemsModel bookedItemsModel)
			throws Exception {
		return prepareSuccessResponse(bookedItemsService.updateBookedItem(bookedItemsModel));
	}

}
