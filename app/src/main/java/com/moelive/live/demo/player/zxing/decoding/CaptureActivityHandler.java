/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moelive.live.demo.player.zxing.decoding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.moelive.live.demo.player.activity.CaptureActivity;
import com.moelive.live.demo.player.zxing.camera.CameraManager;
import com.moelive.live.demo.player.zxing.view.ViewfinderResultPointCallback;

import java.util.Vector;


/**
 * This class handles all the messaging which comprises the state machine for capture.
 */
public final class CaptureActivityHandler extends Handler {

	private static final String TAG = CaptureActivityHandler.class.getSimpleName();

	private final CaptureActivity activity;
	private final DecodeThread decodeThread;
	private State state;

	private enum State {
		PREVIEW,
		SUCCESS,
		DONE
	}

	public CaptureActivityHandler(CaptureActivity activity, Vector<BarcodeFormat> decodeFormats,
			String characterSet) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity, decodeFormats, characterSet,
				new ViewfinderResultPointCallback(activity.getViewfinderView()));
		decodeThread.start();
		state = State.SUCCESS;
		// Start ourselves capturing previews and decoding.
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		if (message.what == com.moelive.live.demo.R.id.auto_focus) {
			//Log.d(TAG, "Got auto-focus message");
			// When one auto focus pass finishes, start another. This is the closest thing to
			// continuous AF. It does seem to hunt a bit, but I'm not sure what else to do.
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, com.moelive.live.demo.R.id.auto_focus);
			}
		}else if (message.what == com.moelive.live.demo.R.id.restart_preview) {
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode();
		}else if (message.what == com.moelive.live.demo.R.id.decode_succeeded) {
			Log.d(TAG, "Got decode succeeded message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();

			/***********************************************************************/
			Bitmap barcode = bundle == null ? null :
				(Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);//���ñ����߳�

			activity.handleDecode((Result) message.obj, barcode);//���ؽ��
			/***********************************************************************/
		}else if (message.what == com.moelive.live.demo.R.id.decode_failed) {
			// We're decoding as fast as possible, so when one decode fails, start another.
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), com.moelive.live.demo.R.id.decode);
		}else if (message.what == com.moelive.live.demo.R.id.return_scan_result) {
			Log.d(TAG, "Got return scan result message");
			activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
			activity.finish();
		}else if (message.what == com.moelive.live.demo.R.id.launch_product_query) {
			Log.d(TAG, "Got product query message");
			String url = (String) message.obj;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			activity.startActivity(intent);
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), com.moelive.live.demo.R.id.quit);
		quit.sendToTarget();
		try {
			decodeThread.join();
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(com.moelive.live.demo.R.id.decode_succeeded);
		removeMessages(com.moelive.live.demo.R.id.decode_failed);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), com.moelive.live.demo.R.id.decode);
			CameraManager.get().requestAutoFocus(this, com.moelive.live.demo.R.id.auto_focus);
			activity.drawViewfinder();
		}
	}

}
