package com.modelo.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T> {

	public List<T> listAll();
	public List<T> filterBy(Map<String, Object> params);
	public void insert(T t);
	public void update(T t);
	public void remove(T t);
}
