package com.springsecurity.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "users_id")
	private int users_id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "enabled")
	private int enabled;
	@Transient
	private String token;
	
    @OneToOne(cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
    		name="user_roles",
			joinColumns=@JoinColumn(name="users_id"),
			inverseJoinColumns=@JoinColumn(name="roles_id")
    )
    @JsonIgnoreProperties("users")
	private Roles roles;
	
	public Users() {}



	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	


	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	

	public Users(String username, String password, int enabled, String token, Roles role) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.token = token;
		this.roles = role;
	}

	
	public Users(String username, String password, int enabled, Roles roles) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}



	/**
	 * @return the id
	 */
	public int getUsers_id() {
		return users_id;
	}

	

	/**
	 * @return the loggedRole
	 */
	public String getLoggedRole(Roles r) {
		String loggedRole = null;
		if(r != null){
			loggedRole = r.getRole();
		}
		return loggedRole;
	}



	/**
	 * @return the roles
	 */
	public Roles getRoles() {
		return roles;
	}



	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	
	

	
	
}
