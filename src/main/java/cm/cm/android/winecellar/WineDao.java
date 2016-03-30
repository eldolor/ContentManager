package cm.cm.android.winecellar;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.util.Anglicizer;
import com.cm.util.PMF;

@Component
class WineDao {
	private static final Logger LOGGER = Logger.getLogger(WineDao.class
			.getName());

	Wine save(Wine wine) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();

				wine.setRatingIdx(wine.getRating().toLowerCase());
				wine.setTextExtractIdx(wine.getTextExtract().toLowerCase());
				wine.setWineIdx(wine.getWine().toLowerCase());

				return pm.makePersistent(wine);

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

	Wine get(String uri) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Wine.class);
				q.setFilter("uri == uriParam");
				q.declareParameters("Long uriParam");
				Object[] _array = new Object[1];
				_array[0] = uri;
				List<Wine> lList = (List<Wine>) q.executeWithArray(_array);
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

	Wine get(Long rowId) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering get");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Wine.class);
				q.setFilter("rowId == rowIdParam");
				q.declareParameters("Long rowIdParam");
				Object[] _array = new Object[1];
				_array[0] = rowId;
				List<Wine> lList = (List<Wine>) q.executeWithArray(_array);
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

	void delete(Long rowId, Long timeUpdatedMs, Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Wine.class);
				q.setFilter("rowId == rowIdParam");
				q.declareParameters("Long rowIdParam");
				Object[] _array = new Object[1];
				_array[0] = rowId;
				List<Wine> wines = (List<Wine>) q.executeWithArray(_array);
				for (Wine wine : wines) {
					wine.setDeleted(true);
					wine.setTimeUpdatedMs(timeUpdatedMs);
					wine.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(wine.getId() + " marked for deletion");
				}

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting delete");
		}
	}

	void restore(Long rowId, Long timeUpdatedMs,
			Long timeUpdatedTimeZoneOffsetMs) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Wine.class);
				q.setFilter("rowId == rowIdParam");
				q.declareParameters("Long rowIdParam");
				Object[] _array = new Object[1];
				_array[0] = rowId;
				List<Wine> wines = (List<Wine>) q.executeWithArray(_array);
				for (Wine wine : wines) {
					wine.setDeleted(false);
					wine.setTimeUpdatedMs(timeUpdatedMs);
					wine.setTimeUpdatedTimeZoneOffsetMs(timeUpdatedTimeZoneOffsetMs);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(wine.getId() + " restored");
				}

			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting delete");
		}
	}

	Wine update(Wine wine) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			PersistenceManager pm = null;
			Wine rtn = null;
			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Wine.class);
				q.setFilter("rowId == rowIdParam");
				q.declareParameters("Long rowIdParam");
				Object[] _array = new Object[1];
				_array[0] = wine.getRowId();
				List<Wine> wines = (List<Wine>) q.executeWithArray(_array);
				for (Wine _wine : wines) {

					_wine.setRowId(wine.getRowId());

					_wine.setWine(wine.getWine());
					_wine.setWineIdx(wine.getWine().toLowerCase());

					_wine.setRating(wine.getRating());
					_wine.setRatingIdx(wine.getRatingIdx().toLowerCase());

					_wine.setTextExtract(wine.getTextExtract());
					_wine.setTextExtractIdx(wine.getTextExtract().toLowerCase());

					_wine.setNotes(wine.getNotes());

					_wine.setUri(wine.getUri());

					_wine.setDeleted(wine.isDeleted());

					_wine.setTimeCreatedMs(wine.getTimeCreatedMs());
					_wine.setTimeCreatedTimeZoneOffsetMs(wine
							.getTimeUpdatedTimeZoneOffsetMs());
					_wine.setTimeUpdatedMs(wine.getTimeUpdatedMs());
					_wine.setTimeUpdatedTimeZoneOffsetMs(wine
							.getTimeUpdatedTimeZoneOffsetMs());
					rtn = _wine;
				}

				return rtn;
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

	List<Wine> getAll() {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Wine.class);
				return (List<Wine>) q.execute();
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
