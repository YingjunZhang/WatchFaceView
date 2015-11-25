package com.example.myviewtest2;

import com.holy.library.watchfaceview.WatchFaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private WatchFaceView watchFaceView;
	private Button displayValueBtn;
	private TextView displayValueTv;
	private Button totalValueBtn;
	private EditText totalValueEt;
	
	private float value = 1.5f;
	private float totalValue = 10f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		watchFaceView = (WatchFaceView)findViewById(R.id.watchface_view);
		displayValueBtn = (Button)findViewById(R.id.setvalue_btn);
		displayValueBtn.setOnClickListener(this);
		displayValueTv = (TextView)findViewById(R.id.display_value_tv);
		totalValueBtn = (Button)findViewById(R.id.set_totalvalue_btn);
		totalValueBtn.setOnClickListener(this);
		totalValueEt = (EditText)findViewById(R.id.total_value_et);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.setvalue_btn){
			
			value += 0.5f;
			if(value<totalValue){
				watchFaceView.setValue(value);
			}else{
				value = 1.0f;
				watchFaceView.setValue(value);
			}
			displayValueTv.setText(String.valueOf(value));
			
			return;
		}
		
		if(id == R.id.set_totalvalue_btn){
			String tempStr = totalValueEt.getText().toString();
			if(!tempStr.isEmpty()){
				try{
					totalValue = Float.parseFloat(tempStr);
					watchFaceView.setTotalValue(totalValue);
					}catch(NumberFormatException e){
						e.printStackTrace();
						Toast.makeText(MainActivity.this, "ÇëÊäÈëÊý×Ö", Toast.LENGTH_SHORT).show();
					}
				
			}
			return;
		}
	}
}
