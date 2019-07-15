package com.springsecurity.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.springsecurity.constants.IConstants;
import com.springsecurity.model.Users;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

	public Users getUserInfo(String username) {
		List<Users> userList = new ArrayList<Users>();
		String sql = "SELECT U FROM Users AS U JOIN U.roles AS R WHERE U.enabled ="+ IConstants.ONE +" AND U.username = :username";
		Query<Users> theQuery = getCurrentSession().createQuery(sql, Users.class);
		theQuery.setParameter("username", username);

		userList = theQuery.list();
		if (userList.size() > 0) {
			return userList.get(IConstants.ZERO);
		} else {
			return null;
		}
	}

	public void saveUser(Users user) {
		getCurrentSession().save(user);
	}
}
