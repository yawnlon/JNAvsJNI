package com.yawnlon.jnatest;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface JNAInterface extends Library {
	JNAInterface INSTANCE = (JNAInterface) Native.loadLibrary(
			"JNATest", JNAInterface.class);

	public void set(int i);

	public int get();
}
