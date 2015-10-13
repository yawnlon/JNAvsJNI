package com.yawnlon.jnavsjni;

import com.yawnlon.jnatest.JNAUtil;
import com.yawnlon.jnitest.JNIUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static TextView text, jni, jna;
	private EditText edit;
	private Button btn;
	private static long start1, start2;
	private static long end1, end2;

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				text.setText("JNI正在运行中，请稍候...");
				break;
			case 1:
				jni.setText("JNI一共运行了" + (end1 - start1) + "ms");
				text.setText("JNA正在运行中，请稍候...");
				break;
			case 2:
				jna.setText("JNA一共运行了" + (end2 - start2) + "ms");
				text.setText("运行结束！");
				break;
			default:
				break;
			}
//			text.setText("一共运行了" + (end - start) + "ms");
			// text.setText("JNA get " + msg.what);
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.text);
		edit = (EditText) findViewById(R.id.edit);
		btn = (Button) findViewById(R.id.button);
		jni = (TextView) findViewById(R.id.jni_result);
		jna = (TextView) findViewById(R.id.jna_result);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				text.setText("初始化...");
				jni.setText("");
				jna.setText("");
				final int n = Integer.parseInt(edit.getText().toString());
				handler.sendEmptyMessage(0);
				start1 = System.currentTimeMillis();
				new Thread() {
					public void run() {
						for (int i = 0; i < n; i++) {
							for (int j = 0; j < n; j++) {
								JNIUtil.set(i + j);
								JNIUtil.get();
							}
						}
						// JNAUtil.INSTANCE.set(158);
						end1 = System.currentTimeMillis();
						handler.sendEmptyMessage(1);
						start2 = System.currentTimeMillis();
						for (int i = 0; i < n; i++) {
							for (int j = 0; j < n; j++) {
								JNAUtil.set(i + j);
								JNAUtil.get();
							}
						}
						end2 = System.currentTimeMillis();
						handler.sendEmptyMessage(2);
					}
				}.start();

			}
		});
	}

}
