#include <jni.h>
#include <string.h>

int tmp = 0;

jstring Java_com_yawnlon_jnitest_JNIUtil_stringFromJNI(JNIEnv* env,
		jobject thiz) {
	return (*env)->NewStringUTF(env, "Hello from JNI !");
}

JNIEXPORT void JNICALL Java_com_yawnlon_jnitest_JNIUtil_set(JNIEnv *env,
		jobject thiz, jint value) {
	tmp = value;
}

JNIEXPORT jint JNICALL Java_com_yawnlon_jnitest_JNIUtil_get(JNIEnv *env,
		jobject thiz) {
	return tmp;
}
void set(int i) {
	tmp = i;
}
int get() {
	return tmp;
}

