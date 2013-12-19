package com.technion.coolie.letmein.util;

public class AsyncTaskResult<T> {
	private final T result;
	private final Exception error;

	public T getResult() {
		return result;
	}

	public Exception getError() {
		return error;
	}

	public AsyncTaskResult(final T result) {
		this.result = result;
		this.error = null;
	}

	public AsyncTaskResult(final Exception error) {
		this.result = null;
		this.error = error;
	}
}