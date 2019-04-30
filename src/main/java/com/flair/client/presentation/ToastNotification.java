package com.flair.client.presentation;

import gwt.material.design.client.ui.MaterialToast;

public final class ToastNotification {
	private static final int DEFAULT_TIMEOUT_MS = 3 * 1000;

	public static void fire(String text) {
		MaterialToast.fireToast(text, DEFAULT_TIMEOUT_MS, "rounded");
	}
	public static void fire(String text, int timeout) {
		MaterialToast.fireToast(text, timeout, "rounded");
	}
}
