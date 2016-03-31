
package com.cm.android.winecellar;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.common.entity.Result;

@Controller
public class WineCellarController {
	@Autowired
	private WineService wineService;

	private static final Logger LOGGER = Logger
			.getLogger(WineCellarController.class.getName());

	@RequestMapping(value = "/winecellar/wine", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = { "content-type=application/json" })
	public @ResponseBody
	Result doCreateContent(@RequestBody Wine wine, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateContent");

			Wine _wine = wineService.save(wine);
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			response.setStatus(HttpServletResponse.SC_OK);
			return result;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			Result result = new Result();
			result.setResult(Result.FAILURE);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateContentGroup");
		}
	}

	@RequestMapping(value = "/winecellar/wine", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	Result doUpdateContent(@RequestBody Wine wine, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateContent");

			Wine _wine = wineService.update(wine);
			Result result = new Result();
			result.setResult(Result.SUCCESS);
			response.setStatus(HttpServletResponse.SC_OK);
			return result;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			Result result = new Result();
			result.setResult(Result.FAILURE);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateContentGroup");
		}
	}

	@RequestMapping(value = "/winecellar/wine/{rowId}/{timeUpdatedMs}/{timeUpdatedTimeZoneOffsetMs}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteContent(@PathVariable Long rowId,
			@PathVariable Long timeUpdatedMs,
			@PathVariable Long timeUpdatedTimeZoneOffsetMs,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering deleteContent");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Row ID: " + rowId);
			if (rowId == null || rowId.equals("")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Id Found!");
			}
			wineService.delete(rowId, timeUpdatedMs,
					timeUpdatedTimeZoneOffsetMs);

			Result result = new Result();
			result.setResult(Result.SUCCESS);
			response.setStatus(HttpServletResponse.SC_OK);
			return result;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			Result result = new Result();
			result.setResult(Result.FAILURE);
			return result;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting deleteContent");
		}
	}

}
