package com.cm.usermanagement.user;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
class ForgotPasswordDao {

	void save(ForgotPasswordRequest request) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			pm.makePersistent(request);

		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	void update(ForgotPasswordRequest pRequest) {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(ForgotPasswordRequest.class);
			q.setFilter("guid == guidParam");
			q.declareParameters("String guidParam");
			Object[] _array = new Object[1];
			_array[0] = pRequest.getGuid();
			List<ForgotPasswordRequest> list = (List<ForgotPasswordRequest>) q
					.executeWithArray(_array);
			if (list != null && list.size() > 0) {
				ForgotPasswordRequest lRequest = list.get(0);
				lRequest.setTimeUpdatedMs(pRequest.getTimeUpdatedMs());
				lRequest.setTimeUpdatedTimeZoneOffsetMs(pRequest
						.getTimeUpdatedTimeZoneOffsetMs());
			}

		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

	ForgotPasswordRequest get(String guid) {
		PersistenceManager pm = null;

		try {
			pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery(ForgotPasswordRequest.class);
			q.setFilter("guid == guidParam");
			q.declareParameters("String guidParam");
			Object[] _array = new Object[1];
			_array[0] = guid;
			List<ForgotPasswordRequest> list = (List<ForgotPasswordRequest>) q
					.executeWithArray(_array);
			if (list != null && list.size() > 0)
				return list.get(0);
			else
				return null;

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
	}

}
