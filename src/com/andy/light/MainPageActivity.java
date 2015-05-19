package com.andy.light;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainPageActivity extends Activity{
	private TextView mStatusTV;
	private View mSwitchView;
	
	private Camera mCamera;
	private Parameters mParameters;
	private boolean isOpenTorch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page_layout);
		initView();
		mCamera = Camera.open();
	}
	
	private void initView() {
		mSwitchView = this.findViewById(R.id.light_switch_area);
		mStatusTV = (TextView) this.findViewById(R.id.light_status_tv);
		
		mStatusTV.setOnClickListener(mOnClickListener);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mCamera) {
			mCamera.release();
			mCamera = null;
		}
	}

	OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.light_status_tv:
				if (isOpenTorch) {
					closeTorch();
				}else {
					openTorch();
				}
				break;
			default:
				break;
			}
		}
	};
	
	private void openTorch() {
		mParameters = mCamera.getParameters();
		mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
		mCamera.setParameters(mParameters);
		isOpenTorch = true;
		mSwitchView.setBackgroundResource(R.drawable.bg_on);
	}
	
	private void closeTorch() {
		mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
		mCamera.setParameters(mParameters);
		isOpenTorch = false;
		mSwitchView.setBackgroundResource(R.drawable.bg_off);
	}
}
