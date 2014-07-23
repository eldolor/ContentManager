package com.cm.usermanagement.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contains the basic user information for the administrator of the journal.
 * Could be expanded later to provide user accounts that do not qualify as
 * admin, but this would require additional infrastructure for managing users,
 * resetting passwords, etc.
 * 
 * Implements the UserDetails interface to support authentication via Spring.
 * Currently there is only one role supported, ROLE_ADMIN, and it is hard coded
 * since we only ever have a single admin user. This would need to be expanded
 * to provide additional user accounts.
 */
@PersistenceCapable
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1629672935573849314L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String firstName;
	@Persistent
	private String lastName;

	@Persistent
	private String email;
	// default
	@Persistent
	private String role = User.ROLE_USER;

	@Persistent
	private Long accountId;

	@Persistent
	private String username;

	@Persistent
	private String password;

	@Persistent
	private Boolean enabled = true;

	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	// Admin role is only role for an authenticated user so it's hard coded here
	@NotPersistent
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	@NotPersistent
	public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	@NotPersistent
	public static final String ROLE_USER = "ROLE_USER";
	@NotPersistent
	public static final String DEFAULT_SUPER_ADMIN_USER_NAME = "su";
	@NotPersistent
	public static final String DEFAULT_SUPER_ADMIN_PASSWORD = "pass1word";

	@NotPersistent
	private GrantedAuthority adminGrantedAuthority = new SimpleGrantedAuthority(
			ROLE_ADMIN);
	@NotPersistent
	private GrantedAuthority userGrantedAuthority = new SimpleGrantedAuthority(
			ROLE_USER);
	@NotPersistent
	private GrantedAuthority superGrantedAuthority = new SimpleGrantedAuthority(
			ROLE_SUPER_ADMIN);
	@NotPersistent
	private Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();

	public User() {
		super();
		// default to user role
		authorities.add(userGrantedAuthority);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getTimeCreatedMs() {
		return timeCreatedMs;
	}

	public void setTimeCreatedMs(Long timeCreatedMs) {
		this.timeCreatedMs = timeCreatedMs;
	}

	public Long getTimeCreatedTimeZoneOffsetMs() {
		return timeCreatedTimeZoneOffsetMs;
	}

	public void setTimeCreatedTimeZoneOffsetMs(Long timeCreatedTimeZoneOffsetMs) {
		this.timeCreatedTimeZoneOffsetMs = timeCreatedTimeZoneOffsetMs;
	}

	public Long getTimeUpdatedMs() {
		return timeUpdatedMs;
	}

	public void setTimeUpdatedMs(Long timeUpdatedMs) {
		this.timeUpdatedMs = timeUpdatedMs;
	}

	public Long getTimeUpdatedTimeZoneOffsetMs() {
		return timeUpdatedTimeZoneOffsetMs;
	}

	public void setTimeUpdatedTimeZoneOffsetMs(Long timeUpdatedTimeZoneOffsetMs) {
		this.timeUpdatedTimeZoneOffsetMs = timeUpdatedTimeZoneOffsetMs;
	}

	/**
	 * All methods below this point are to implement the UserDetails interface.
	 * They are all Transient because they are hard coded in the class for the
	 * time being. If account expiration, etc, were implemented as part of a
	 * broader move to make this a multi-user app, these would be stored as
	 * well.
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		// hard-wired for now
		if (username.equals(DEFAULT_SUPER_ADMIN_USER_NAME)) {
			authorities.add(superGrantedAuthority);
		}
		if (role != null) {
			if (role.equals(ROLE_USER)) {
				authorities.add(userGrantedAuthority);
			}
			if (role.equals(ROLE_ADMIN)) {
				authorities.add(adminGrantedAuthority);
			}
		}
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return (enabled != null) ? enabled : false;
	}
}
