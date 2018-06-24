package com.flair.client.interop;


import com.google.gwt.user.client.rpc.AsyncCallback;

/*
 * Wrapper around AsyncCallback that supports lambdas
 */
public class FuncCallback<T> implements AsyncCallback<T>
{
	public interface Success<T> {
		public void handle(T result);
	}
	
	public interface Failure {
		public void handle(Throwable caught);
	}
	
	private final Success<T>	success;
	private final Failure		failure;
	
	private FuncCallback(Success<T> s, Failure f) {
		success = s;
		failure = f;
	}

	@Override
	public void onFailure(Throwable caught) {
		failure.handle(caught);
	}

	@Override
	public void onSuccess(T result) {
		success.handle(result);
	}
	
	public static <T> FuncCallback<T> get(Success<T> s, Failure f) {
		return new FuncCallback<>(s, f);
	}
	
	public static <T> FuncCallback<T> get(Success<T> s) {
		return new FuncCallback<>(s, e -> {});
	}
}
