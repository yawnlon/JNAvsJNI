# JNAvsJNI 
JNA 与 JNI 性能比较，在Android上的一个实例

## Introduction
对于Java代码调用本地C/C++的DLL/so的问题，大家以前所熟识的是采用JNI方式调用。但是 JNI 存在一个弊端：如果你有一个已经写的DLL/so（libA），简单的采用JNI机制调用，是不能够调用成功的，因为JNI 有着一套严格的命名规约，我们需要按照这样一套规约自己实现一个DLL/so（libB），在libB中调用已有的libA。<br>
而这是十分麻烦的。<br>
JNA(Java Native Access)框架是一个开源的Java框架，是建立在经典的JNI的基础之上的一个框架。很好地解决了JNI上面存在的问题。<br>
然而在带来开发便利的同时，JNA是否会在其他方面有所损失呢？笔者在二者的性能方面进行了对比，得到了以下结论。<br>

## Standard of Comparison
笔者采用二者分别N次调用的本地同一C-Method的方式，比较二者所用的时间复杂度。<br>
时间越小，性能越高。<br>
具体实现如下：<br>
分别用JNI和JNA的方式建立so，so中包含一个get方法和一个set方法，在java端进行循环调用N*N次，比较所耗费的时间。<br>

## Key Code
'JNI'实现(分为C和Java代码)
```C
// C Code
#include <jni.h>

int tmp = 0;
JNIEXPORT void JNICALL Java_com_yawnlon_jnitest_JNIUtil_set(JNIEnv *env,
		jobject thiz, jint value) {
	tmp = value;
}
JNIEXPORT jint JNICALL Java_com_yawnlon_jnitest_JNIUtil_get(JNIEnv *env,
		jobject thiz) {
	return tmp;
}
```
```Java
// Java Interface
public class JNIUtil {
	public native static void set(int i);
	public native static int get();
	static {
		System.loadLibrary("JNITest");
	}
}
```
```Java
// Test
for (int i = 0; i < n; i++) {
	for (int j = 0; j < n; j++) {
		JNIUtil.set(i + j);
	  JNIUtil.get();
	}
}
```

'JNA'实现(分为C和Java)
```C
// C Code
int tmp = 0;
void set(int i) {
	tmp = i;
}
int get() {
	return tmp;
}
```
```Java
// Java Interface
public interface JNAInterface extends Library {
	JNAInterface INSTANCE = (JNAInterface) Native.loadLibrary(
			"JNATest", JNAInterface.class);
	public void set(int i);
	public int get();
}
```
```Java
// Test
for (int i = 0; i < n; i++) {
  for (int j = 0; j < n; j++) {
		JNAUtil.set(i + j);
		JNAUtil.get();
	}
}
```

## Test
在'OPPO N5207'机型上进行了测试，结果如下。由此可初步得出结论，'JNA'在性能上完全不如'JNI'
![](https://github.com/yawnlon/JNAvsJNI/blob/master/result/result.PNG)


