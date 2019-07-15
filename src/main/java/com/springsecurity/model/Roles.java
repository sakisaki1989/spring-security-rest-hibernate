package com.springsecurity.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "roles")
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roles_id")
	private int roles_id;
	@Column(name = "role")
	private String role;
	
	@OneToMany(cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(
			name="user_roles",
			joinColumns=@JoinColumn(name="roles_id"),
			inverseJoinColumns=@JoinColumn(name="users_id")
	)
	@JsonIgnoreProperties("roles")
	private List<Users> users;
	
	/**
	 * @return the users
	 */
	public List<Users> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<Users> users) {
		this.users = users;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public Roles(String role) {
		this.role = role;
	}

	public Roles() {
	}

	/**
	 * @return the roles_id
	 */
	public int getRoles_id() {
		return roles_id;
	}
	
	

}
