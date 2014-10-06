package models;

public interface IBaseResult<T> {
	void onSuccess(T result);
	void onError(String error);
}
