package com.springsecurity.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springsecurity.constants.IConstants;
import com.springsecurity.model.Roles;
import com.springsecurity.model.Users;
@Repository
public class RoleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
	
	 public Roles getRole(String role) {
		 	List<Roles> roleList = new ArrayList<Roles>();
		 	String sql = "SELECT R FROM Roles AS R WHERE R.role = :role";
			Query<Roles> theQuery = getCurrentSession().createQuery(sql, Roles.class);
			theQuery.setParameter("role", role);

			roleList = theQuery.list();
			if (roleList.size() > 0) {
				return roleList.get(IConstants.ZERO);
			} else {
				return null;
			}
	    }
}
