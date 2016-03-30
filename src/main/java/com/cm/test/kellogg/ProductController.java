package com.cm.test.kellogg;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm.util.ValidationError;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

@Controller
public class ProductController {
	@Autowired
	private ProductDao productDao;

	private static final Logger LOGGER = Logger
			.getLogger(ProductController.class.getName());
	private BlobstoreService mBlobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private final BlobInfoFactory mBlobInfoFactory = new BlobInfoFactory();
	private final GcsService mGcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView displayProductAsGrid(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayProductAsGrid");
		try {
			// pass it along to the view
			// model.addAttribute("contentGroupId", contentGroupId);
			return new ModelAndView("product_portfolio_view", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayProductAsGrid");
		}
	}

	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public ModelAndView displayProductAsList(ModelMap model) {
		if (LOGGER.isLoggable(Level.INFO))
			LOGGER.info("Entering displayProductAsList");
		try {
			// pass it along to the view
			// model.addAttribute("contentGroupId", contentGroupId);
			return new ModelAndView("product_list_view", model);
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting displayProductAsList");
		}
	}

	/**
	 * 
	 * @param contentGroupUuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Product> getAllProduct(HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getAllProduct");
			List<Product> content = productDao.getAll();
			if (content != null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info(content.size() + " Content found");
			} else {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return content;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getAllProduct");
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Product getProduct(@PathVariable Long id, HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getProduct");
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Content ID: " + id);
			if (id == null || id.equals("")) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Id Found!");
				return null;
			}
			Product content = productDao.get(id);
			if (content == null) {
				if (LOGGER.isLoggable(Level.INFO))
					LOGGER.info("No Content Found!");
			}
			response.setStatus(HttpServletResponse.SC_OK);
			return content;
		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getProduct");
		}
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doCreateProduct(@RequestBody Product product,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doCreateProduct");

			Product lProduct = productDao.save(product);
			response.setStatus(HttpServletResponse.SC_CREATED);
			return null;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doCreateProduct");
		}
	}

	@RequestMapping(value = "/content", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody
	List<ValidationError> doUpdateProduct(@RequestBody Product product,
			HttpServletResponse response) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering doUpdateProduct");
			Product lProduct = productDao.update(product);
			response.setStatus(HttpServletResponse.SC_OK);

			return null;

		} catch (Throwable e) {
			// handled by GcmManager
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			return null;
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting doUpdateProduct");
		}
	}

}
