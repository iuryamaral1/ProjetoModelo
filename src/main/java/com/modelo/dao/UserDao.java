package com.modelo.dao;

import com.modelo.users.model.User;

public interface UserDao extends GenericDAO<User> {

	User findByUserName(String username);

}