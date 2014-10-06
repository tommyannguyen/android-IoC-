package com.datalayer;

public interface IBaseRepository<T> {
	long insert(T entity);
	void update(T entity);
	void delete(Object id);
	T get(Object id);
}
