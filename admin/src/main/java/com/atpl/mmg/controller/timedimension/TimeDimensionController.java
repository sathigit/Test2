package com.atpl.mmg.controller.timedimension;

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
import com.atpl.mmg.model.timedimension.TimeDimensionModel;
import com.atpl.mmg.service.timedimension.TimeDimensionService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
public class TimeDimensionController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(TimeDimensionController.class);

	@Autowired
	TimeDimensionService timeDimensionService;

	@RequestMapping(value = "/date", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDate(@RequestBody TimeDimensionModel timeDimensionModel) throws Exception {
		return prepareSuccessResponse(timeDimensionService.getDate(timeDimensionModel));
	}

	@RequestMapping(value = "/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getWeekDays(@PathVariable("date") String date) throws Exception {
		return prepareSuccessResponse(timeDimensionService.getWeekDays(date));
	}

}
