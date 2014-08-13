package com.cm.usermanagement.user.transfer;

import java.io.Serializable;

public class ForgotPasswordRequest implements Serializable {

	private static final long serialVersionUID = 1629672935573849314L;

	private String email;
	private String guid;
	private String password;
	private String password2;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

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


}
