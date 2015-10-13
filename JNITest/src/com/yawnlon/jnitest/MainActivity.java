package com.yawnlon.jnitest;

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

	private static TextView text;
	private EditText edit;
	private Button btn;
	private static long start;
	private static long end;

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			text.setText("一共运行了" + (end - start) + "ms");
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

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				text.setText("正在运行，请稍候...");

				final int n = Integer.parseInt(edit.getText().toString());
				start = System.currentTimeMillis();

				new Thread() {
					public void run() {
						for (int i = 0; i < n; i++) {
							for (int j = 0; j < n; j++) {
								JNIUtil.set(i + j);
								JNIUtil.get();
							}
						}
						end = System.currentTimeMillis();
						handler.sendEmptyMessage(0);
					}
				}.start();

			}
		});
	}

}
