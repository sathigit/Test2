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
import com.atpl.mmg.model.report.ExpenseModel;
import com.atpl.mmg.service.report.ExpenseService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class ExpenseController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

	@Autowired
	ExpenseService expenseService;

	@RequestMapping(value = "/expense", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addExpense(@RequestBody ExpenseModel expenseModel) throws Exception {
		return prepareSuccessResponse(expenseService.addExpense(expenseModel));
	}

	@RequestMapping(value = "/expenseSource", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> addExpenseSource(@RequestBody ExpenseModel expenseModel) throws Exception {
		return prepareSuccessResponse(expenseService.addExpenseSource(expenseModel));
	}

	@RequestMapping(value = "/expenseSource", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getExpenseSource() throws Exception {
		return prepareSuccessResponse(expenseService.getExpenseSource());
	}

	@RequestMapping(value = "/expenseSource/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getExpenseSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(expenseService.getExpenseSource(id));
	}

	@RequestMapping(value = "/expenseSource", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateExpenseSource(@RequestBody ExpenseModel expenseModel) throws Exception {
		return prepareSuccessResponse(expenseService.updateExpenseSource(expenseModel));
	}

	@RequestMapping(value = "/expenseSource/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteExpenseSource(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(expenseService.deleteExpenseSource(id));
	}

}
