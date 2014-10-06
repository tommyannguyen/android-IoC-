package com.datalayer;

public interface IBaseReponsitory<T> {
	long insert(T entity);
	void update(T entiry);
	void delete(Object id);
	T get(Object id);
}
