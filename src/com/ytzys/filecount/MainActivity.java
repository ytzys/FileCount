package com.ytzys.filecount;

import java.io.File;

import com.ytzys.filecount.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

/**
 * 
 * @author ytzys
 * 
 *         检查一个文件夹下按顺序编号的文件，是否有缺失
 * 
 */
public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text = (TextView) findViewById(R.id.text);

		countFile();
	}

	private void countFile() {
		String path = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.sumavision.talktv2/files/TVfan/cache/"
				+ "156661533756";
		File file = new File(path);
		File[] files = file.listFiles();
		int segCount = 0;
		for (File tmp : files) {
			String name = tmp.getName();
			if (name.endsWith("m3u8")) {
				continue;
			}
			int namePre = Integer
					.parseInt(name.substring(0, name.indexOf(".")));
			if (namePre > segCount) {
				segCount = namePre;
			}
		}
		boolean[] result = new boolean[segCount + 1];
		for (File tmp : files) {
			String name = tmp.getName();
			if (name.endsWith("m3u8")) {
				continue;
			}
			int namePre = Integer
					.parseInt(name.substring(0, name.indexOf(".")));
			result[namePre] = true;
		}
		String countResult = "missing file:\t";
		int i;
		for (i = 0; i < result.length; i++) {
			if (!result[i]) {
				countResult += i + ".flv\t";

			}
		}
		Log.i(TAG, countResult);
		text.setText(countResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
