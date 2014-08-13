package com.cm.usermanagement.user.transfer;

import java.io.Serializable;

public class PasswordChangeRequest implements Serializable {

	private static final long serialVersionUID = 1629672935573849314L;

	private long userId;
	private String oldPassword;
	private String password;
	private String password2;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
