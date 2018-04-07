package com.moelive.media.streamer.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.ksyun.media.streamer.encoder.VideoEncodeFormat;
import com.ksyun.media.streamer.framework.AVConst;
import com.ksyun.media.streamer.kit.StreamerConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Standard demo fragment.
 */

public class StdDemoFragment extends BaseDemoFragment {
    private static final String TAG = "StdDemoFragment";

    @BindView(com.moelive.live.demo.R.id.capture_res_group)
    protected RadioGroup mCaptureResGroup;
    @BindView(com.moelive.live.demo.R.id.preview_res_group)
    protected RadioGroup mPreviewResGroup;
    @BindView(com.moelive.live.demo.R.id.preview_view_group)
    protected RadioGroup mPreviewViewGroup;
    @BindView(com.moelive.live.demo.R.id.background_switch_group)
    protected RadioGroup mBgSwitchGroup;
    @BindView(com.moelive.live.demo.R.id.video_codec_id)
    protected RadioGroup mVideoCodecIdGroup;
    @BindView(com.moelive.live.demo.R.id.encode_scene)
    protected RadioGroup mEncodeSceneGroup;
    @BindView(com.moelive.live.demo.R.id.encode_profile)
    protected RadioGroup mEncodeProfileGroup;
    @BindView(com.moelive.live.demo.R.id.aac_profile)
    protected RadioGroup mAACProfileGroup;
    @BindView(com.moelive.live.demo.R.id.zoom_touch_focus)
    protected CheckBox mZoomFocus;
    @BindView(com.moelive.live.demo.R.id.stereo_stream)
    protected CheckBox mStereoStream;
    @BindView(com.moelive.live.demo.R.id.bluetooth_mic)
    protected CheckBox mBluetoothMicCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.moelive.live.demo.R.layout.std_demo_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadParams(BaseCameraActivity.BaseStreamConfig cfg, String url) {
        super.loadParams(cfg, url);
        StdCameraActivity.StdStreamConfig config = (StdCameraActivity.StdStreamConfig) cfg;

        // capture resolution
        switch (mCaptureResGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.cap_360:
                config.mCaptureResolution = StreamerConstants.VIDEO_RESOLUTION_360P;
                break;
            case com.moelive.live.demo.R.id.cap_480:
                config.mCaptureResolution = StreamerConstants.VIDEO_RESOLUTION_480P;
                break;
            case com.moelive.live.demo.R.id.cap_720:
                config.mCaptureResolution = StreamerConstants.VIDEO_RESOLUTION_720P;
                break;
            case com.moelive.live.demo.R.id.cap_1080:
            default:
                config.mCaptureResolution = StreamerConstants.VIDEO_RESOLUTION_1080P;
                break;
        }

        // preview resolution
        switch (mPreviewResGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.preview_360:
                config.mPreviewResolution = StreamerConstants.VIDEO_RESOLUTION_360P;
                break;
            case com.moelive.live.demo.R.id.preview_480:
                config.mPreviewResolution = StreamerConstants.VIDEO_RESOLUTION_480P;
                break;
            case com.moelive.live.demo.R.id.preview_720:
                config.mPreviewResolution = StreamerConstants.VIDEO_RESOLUTION_720P;
                break;
            case com.moelive.live.demo.R.id.preview_1080:
            default:
                config.mPreviewResolution = StreamerConstants.VIDEO_RESOLUTION_1080P;
                break;
        }

        // preview view type
        switch (mPreviewViewGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.preview_GLSurfaceView:
                config.mPreviewViewType = StdCameraActivity.PREVIEW_TYPE_GLSURFACEVIEW;
                break;
            case com.moelive.live.demo.R.id.preview_TextureView:
                config.mPreviewViewType = StdCameraActivity.PREVIEW_TYPE_TEXTUREVIEW;
                break;
            case com.moelive.live.demo.R.id.preview_offscreen:
            default:
                config.mPreviewViewType = StdCameraActivity.PREVIEW_TYPE_OFFSCREEN;
                break;
        }

        // background switch mode
        switch (mBgSwitchGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.bg_keep_streaming:
                config.mBgSwitchMode = StdCameraActivity.BG_SWITCH_MODE_NORMAL_STREAMING;
                break;
            case com.moelive.live.demo.R.id.bg_image_streaming:
                config.mBgSwitchMode = StdCameraActivity.BG_SWITCH_MODE_BITMAP_STREAMING;
                break;
            case com.moelive.live.demo.R.id.bg_keep_last_frame:
            default:
                config.mBgSwitchMode = StdCameraActivity.BG_SWITCH_MODE_KEEP_LAST_FRAME;
                break;
        }

        // video codec id
        switch (mVideoCodecIdGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.codec_h265:
                config.mVideoCodecId = AVConst.CODEC_ID_HEVC;
                break;
            case com.moelive.live.demo.R.id.codec_h264:
            default:
                config.mVideoCodecId = AVConst.CODEC_ID_AVC;
                break;
        }

        // video encode scene
        switch (mEncodeSceneGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.encode_scene_show_self:
                config.mVideoEncodeScene = VideoEncodeFormat.ENCODE_SCENE_SHOWSELF;
                break;
            case com.moelive.live.demo.R.id.encode_scene_game:
                config.mVideoEncodeScene = VideoEncodeFormat.ENCODE_SCENE_GAME;
                break;
            case com.moelive.live.demo.R.id.encode_scene_default:
            default:
                config.mVideoEncodeScene = VideoEncodeFormat.ENCODE_SCENE_DEFAULT;
                break;
        }

        // video encode profile
        switch (mEncodeProfileGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.encode_profile_balance:
                config.mVideoEncodeProfile = VideoEncodeFormat.ENCODE_PROFILE_BALANCE;
                break;
            case com.moelive.live.demo.R.id.encode_profile_high_perf:
                config.mVideoEncodeProfile = VideoEncodeFormat.ENCODE_PROFILE_HIGH_PERFORMANCE;
                break;
            case com.moelive.live.demo.R.id.encode_profile_low_power:
            default:
                config.mVideoEncodeProfile = VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER;
                break;
        }

        // audio encode profile
        switch (mAACProfileGroup.getCheckedRadioButtonId()) {
            case com.moelive.live.demo.R.id.aac_he:
                config.mAudioEncodeProfile = AVConst.PROFILE_AAC_HE;
                break;
            case com.moelive.live.demo.R.id.aac_he_v2:
                config.mAudioEncodeProfile = AVConst.PROFILE_AAC_HE_V2;
                break;
            case com.moelive.live.demo.R.id.aac_lc:
            default:
                config.mAudioEncodeProfile = AVConst.PROFILE_AAC_LOW;
                break;
        }

        // zoom focus
        config.mZoomFocus = mZoomFocus.isChecked();
        // stereo stream
        config.mStereoStream = mStereoStream.isChecked();
        // bluetooth mic
        config.mBluetoothMicFirst = mBluetoothMicCheckBox.isChecked();
    }

    @Override
    public void start(String url) {
        StdCameraActivity.StdStreamConfig config = new StdCameraActivity.StdStreamConfig();
        loadParams(config, url);
        StdCameraActivity.startActivity(getActivity(), config, StdCameraActivity.class);
    }
}
