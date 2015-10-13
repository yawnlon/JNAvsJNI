package com.yawnlon.jnatest;

public class JNAUtil {
	public static void set(int i) {
		JNAInterface.INSTANCE.set(i);
	}

	public static int get() {
		return JNAInterface.INSTANCE.get();
	}

	static {
		try {
			System.setProperty("jna.library.path", "/system/lib/:/usr/lib/");
			System.loadLibrary("jnidispatch");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
