package com.cm.usermanagement.user;

import javax.jdo.PersistenceManager;

import org.springframework.stereotype.Component;

import com.cm.util.PMF;

@Component
class PasswordChangeRequestDao {

	void save(PasswordChangeRequest request) {
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

}
