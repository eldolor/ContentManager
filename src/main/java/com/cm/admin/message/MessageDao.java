package com.cm.admin.message;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Component;

import com.cm.stripe.StripeCustomer;
import com.cm.util.PMF;

@Component
public class MessageDao {
	private static final Logger LOGGER = Logger.getLogger(MessageDao.class
			.getName());

	public Message save(Message message) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering save");
			PersistenceManager pm = null;
			try {
				pm = PMF.get().getPersistenceManager();
				return pm.makePersistent(message);

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

	Message getUnviewedMessageByType(String pType) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getUnviewedMessageByType");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Message.class);
				q.setFilter("type == typeParam && timeViewedMs == timeViewedMsParam  && deleted == deletedParam");
				q.declareParameters("String typeParam, Long timeViewedMsParam, Boolean deletedParam");
				q.setOrdering("timeCreatedMs desc");
				List<Message> lList = (List<Message>) q.execute(pType, null,
						Boolean.valueOf(false));
				// return the latest
				if (lList != null && (!lList.isEmpty()))
					return lList.get(0);
				else
					return null;

			} catch (JDOObjectNotFoundException e) {
				LOGGER.log(Level.WARNING, e.getMessage());
				return null;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getUnviewedMessageByType");
		}
	}

	Message getMessageByType(String pType) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering getMessageByType");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Query q = pm.newQuery(Message.class);
				q.setFilter("type == typeParam && deleted == deletedParam");
				q.declareParameters("String typeParam, Boolean deletedParam");
				q.setOrdering("timeCreatedMs desc");
				List<Message> lList = (List<Message>) q.execute(pType,
						Boolean.valueOf(false));
				// return the latest
				if (lList != null && (!lList.isEmpty()))
					return lList.get(0);
				else
					return null;

			} catch (JDOObjectNotFoundException e) {
				LOGGER.log(Level.WARNING, e.getMessage());
				return null;
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		} finally {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Exiting getMessageByType");
		}
	}

	void update(Message pMessage) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering update");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Message lAlert = pm.getObjectById(Message.class,
						pMessage.getId());
				lAlert.setTimeViewedMs(pMessage.getTimeViewedMs());
				lAlert.setTimeViewedTimeZoneOffsetMs(pMessage
						.getTimeViewedTimeZoneOffsetMs());

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

	void delete(Long id) {
		try {
			if (LOGGER.isLoggable(Level.INFO))
				LOGGER.info("Entering delete");
			PersistenceManager pm = null;

			try {
				pm = PMF.get().getPersistenceManager();
				Message lMessage = pm.getObjectById(Message.class, id);
				if (lMessage != null) {
					lMessage.setDeleted(true);
					if (LOGGER.isLoggable(Level.INFO))
						LOGGER.info(id + " message marked for deletion");
				} else {
					LOGGER.log(Level.WARNING, id + "  message NOT FOUND!");
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

}
