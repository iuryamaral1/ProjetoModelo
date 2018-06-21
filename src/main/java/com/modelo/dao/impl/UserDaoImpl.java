package com.modelo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.modelo.dao.UserDao;
import com.modelo.users.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession().createQuery("from User where username=?").setParameter(0, username)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<User> listAll() {
		Session openSession = sessionFactory.getCurrentSession();
		Transaction transaction = openSession.beginTransaction();
		
		String sql = "select u from User u";
		
		List<User> listUsers = ((List<User>) openSession.createQuery(sql).list());
		
		transaction.commit();
		
		return listUsers;
	}

	@Override
	public List<User> filterBy(Map<String, Object> params) {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		StringBuilder sql = new StringBuilder("select u from User where 1 = 1 ");
		
		params.keySet().stream().forEach(p -> {
			String value = (String) params.get(p);
			if(value != null && !value.isEmpty()) {
				sql.append(" and u." + p + " = " + value + " ");
			}
		});
		
		Query query = session.createQuery(sql.toString());
		transaction.commit();
		
		return query.list();
	}

	@Transactional
	@Override
	public void insert(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
	}

	@Override
	public void update(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
	}

	@Override
	public void remove(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(user);
		transaction.commit();
	}

}