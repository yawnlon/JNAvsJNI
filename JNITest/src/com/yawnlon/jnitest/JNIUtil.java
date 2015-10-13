package com.yawnlon.jnitest;

public class JNIUtil {
	public native static String stringFromJNI();

	public native static void set(int i);

	public native static int get();

	static {
		System.loadLibrary("JNITest");
	}
}
