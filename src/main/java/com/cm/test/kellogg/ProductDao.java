package com.cm.test.kellogg;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Persistent;

import org.springframework.stereotype.Component;

import com.cm.util.Anglicizer;
import com.cm.util.PMF;

@Component
class ProductDao {
	private static final Logger LOGGER = Logger.getLogger(ProductDao.class
			.getName());


	Product save(Product product) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				return pm.makePersistent(product);

			} finally {
				if (pm != null) {
					pm.close();
				}
			}

		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting save");
		}
	}

	Product get(String uri) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Product.class);
				q.setFilter("uri == uriParam");
				q.declareParameters("Long uriParam");
				Object[] _array = new Object[1];
				_array[0] = uri;
				List<Product> lList = (List<Product>) q
						.executeWithArray(_array);
				if (lList != null && (!lList.isEmpty()))
					return lList.get(0);
				else
					return null;

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}

	Product get(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Product _product = pm.detachCopy(pm.getObjectById(
						Product.class, id));
				return _product;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting get");
		}
	}


	Product update(Product product) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Product _product = pm.getObjectById(Product.class,
						product.getId());

				_product.setAllergens(product.getAllergens());
				_product.setAvailableSizes(product.getAvailableSizes());
				_product.setIngredients(product.getIngredients());
				_product.setName(product.getName());
				_product.setType(product.getType());
				_product.setAllergens(product.getAllergens());
				_product.setBrand(product.getBrand());
				_product.setUri(product.getUri());
				_product.setMediaType(product.getMediaType());
				
				_product.setDescription(product.getDescription());

				// for existing contents
				if (_product.getTimeCreatedMs() == null) {
					_product.setTimeCreatedMs(product.getTimeCreatedMs());
					_product.setTimeCreatedTimeZoneOffsetMs(product
							.getTimeUpdatedTimeZoneOffsetMs());
				}
				_product.setTimeUpdatedMs(product.getTimeUpdatedMs());
				_product.setTimeUpdatedTimeZoneOffsetMs(product
						.getTimeUpdatedTimeZoneOffsetMs());


				return _product;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting update");
		}
	}


	List<Product> getAll() {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Product.class);
				return (List<Product>) q.execute();
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting");
		}
	}

}
