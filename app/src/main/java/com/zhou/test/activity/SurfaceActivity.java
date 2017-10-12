package com.zhou.test.activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;

import com.faceunity.wrapper.faceunity;
import com.zhou.test.R;
import com.zhou.test.gles.FullFrameRect;
import com.zhou.test.gles.Texture2dProgram;
import com.zhou.test.gles.authpack;
import com.zhou.test.util.LiveShowConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SurfaceActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback, SurfaceTexture.OnFrameAvailableListener {

    private static final String TAG = "";
    private GLSurfaceView mSurfaceView;
    private GLRenderer glRenderer;
    private byte[] mCameraNV21Byte;

    public static final String MY_DOWNLOADS = "mydownloads";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        mSurfaceView = (GLSurfaceView) findViewById(R.id.surface_view);
        mSurfaceView.setEGLContextClientVersion(2);
        glRenderer = new GLRenderer();
        mSurfaceView.setRenderer(glRenderer);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mHandler = new MainHandler(this);
    }

    private int cameraWidth = 1280;
    private int cameraHeight = 720;
    private int mEffectItem = 0; //道具

    public int m_cur_item_id = -1;
    public static final String[] m_filters = {"nature", "delta", "electric", "slowlived", "tokyo", "warm"};
    public int m_cur_filter_id = 0;

    public double m_faceunity_color_level = 0.5;
    public double m_faceunity_blur_level = 3.0;
    public double m_face_level = 0;
    public double m_big_eye_level = 0;
    private boolean isUsing = false;

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mCameraNV21Byte = data;
    }


    private MainHandler mHandler;

    static class MainHandler extends Handler {

        static final int HANDLE_CAMERA_START_PREVIEW = 1;

        private WeakReference<SurfaceActivity> mActivityWeakReference;

        MainHandler(SurfaceActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SurfaceActivity activity = mActivityWeakReference.get();
            switch (msg.what) {
                case HANDLE_CAMERA_START_PREVIEW:
                    activity.handleCameraStartPreview((SurfaceTexture) msg.obj);
                    break;
            }
        }
    }


    /**
     * set preview and start preview after the surface created
     */
    private void handleCameraStartPreview(SurfaceTexture surfaceTexture) {
        if (mCamera == null) {
            return;
        }
        try {
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewTexture(surfaceTexture);
            surfaceTexture.setOnFrameAvailableListener(this);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GLRenderer implements GLSurfaceView.Renderer {

        FullFrameRect mFullScreenFUDisplay;
        FullFrameRect mFullScreenCamera;

        int mCameraTextureId;
        SurfaceTexture mCameraSurfaceTexture;

        boolean isFirstOnDrawFrame;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.e(TAG, "onSurfaceCreated");
            mFullScreenCamera = new FullFrameRect(new Texture2dProgram(
                    Texture2dProgram.ProgramType.TEXTURE_EXT));
            mCameraTextureId = mFullScreenCamera.createTextureObject();
            mCameraSurfaceTexture = new SurfaceTexture(mCameraTextureId);
            mHandler.sendMessage(mHandler.obtainMessage(
                    MainHandler.HANDLE_CAMERA_START_PREVIEW,
                    mCameraSurfaceTexture));

            mFullScreenFUDisplay = new FullFrameRect(new Texture2dProgram(
                    Texture2dProgram.ProgramType.TEXTURE_2D));

            try {
                InputStream is = getAssets().open("v3.mp3");
                byte[] v3data = new byte[is.available()];
                is.read(v3data);
                is.close();
                faceunity.fuSetup(v3data, null, authpack.A());
                faceunity.fuSetMaxFaces(1);
                Log.e(TAG, "fuSetup");

                is = getAssets().open("face_beautification.mp3");
                byte[] itemData = new byte[is.available()];
                is.read(itemData);
                is.close();
                mFacebeautyItem = faceunity.fuCreateItemFromPackage(itemData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            isFirstOnDrawFrame = true;
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.e(TAG, "onSurfaceChanged " + width + " " + height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            if (isFirstOnDrawFrame) {
                //第一次onDrawFrame并不是由camera内容驱动的
                isFirstOnDrawFrame = false;
                return;
            }

            /**
             * 获取camera数据, 更新到texture
             */
            float[] mtx = new float[16];
            mCameraSurfaceTexture.updateTexImage();
            mCameraSurfaceTexture.getTransformMatrix(mtx);
            if (m_cur_item_id >= 0 && m_cur_item_id < 30) {
                isUsing = LiveShowConstants.tiezhiList.get(m_cur_item_id).isUsing;
            }
            if (!isUsing) {
                mEffectItem = 0;
                m_cur_item_id = -1;
            }
            try {
                if (m_cur_item_id >= 0 && m_cur_item_id < 30) {
                    File tmpItem = new File(getFilesDir() + "/" + MY_DOWNLOADS + "/" + LiveShowConstants.tiezhiList.get(m_cur_item_id).tiezhi_name);

                    if (tmpItem.exists()) {
                        FileInputStream fis = new FileInputStream(tmpItem);
                        byte[] item_data = new byte[fis.available()];
                        fis.read(item_data);
                        fis.close();
                        mEffectItem = faceunity.fuCreateItemFromPackage(item_data);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            faceunity.fuItemSetParam(mEffectItem, "isAndroid", 1.0);

            faceunity.fuItemSetParam(mFacebeautyItem, "filter_name", m_filters[m_cur_filter_id]);
            faceunity.fuItemSetParam(mFacebeautyItem, "blur_level", Math.floor(m_faceunity_blur_level));
            faceunity.fuItemSetParam(mFacebeautyItem, "color_level", m_faceunity_color_level);
            faceunity.fuItemSetParam(mFacebeautyItem, "cheek_thinning", m_face_level);
            faceunity.fuItemSetParam(mFacebeautyItem, "eye_enlarging", m_big_eye_level);
            /**
             * 这里拿到fu处理过后的texture，可以对这个texture做后续操作，如硬编、预览。
             */
            boolean isOESTexture = true; //camera默认的是OES的
            int flags = isOESTexture ? faceunity.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE : 0;
            int fuTex = faceunity.fuDualInputToTexture(mCameraNV21Byte, mCameraTextureId, flags,
                    cameraWidth, cameraHeight, mFrameId++, new int[]{mEffectItem, mFacebeautyItem});

            //mFullScreenCamera.drawFrame(mCameraTextureId, mtx);
            if (mFullScreenFUDisplay == null) {
                return;
            }
            mFullScreenFUDisplay.drawFrame(fuTex, mtx);
        }

        public void notifyPause() {
            if (mFullScreenFUDisplay != null) {
                mFullScreenFUDisplay.release(false);
            }

            if (mFullScreenCamera != null) {
                mFullScreenCamera.release(false);
            }

            if (mCameraSurfaceTexture != null) {
                mCameraSurfaceTexture.release();
            }
        }
    }

    private int mFrameId = 0;

    private int mFacebeautyItem = 0; //美颜道具

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 记得释放camera，方便其他应用调用
         */
        releaseCamera();

        mFrameId = 0;
        if (mSurfaceView != null) {
            mSurfaceView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    //Note: 切忌使用一个已经destroy的item

                    faceunity.fuDestroyItem(mFacebeautyItem);
                    mFacebeautyItem = 0;
                    faceunity.fuOnDeviceLost();
                }
            });
        }
        if (glRenderer != null) {
            glRenderer.notifyPause();
        }
        if (mSurfaceView != null) {
            mSurfaceView.onPause();
        }
    }
//
//    @Override
//    public void customSysExceptionCallBack() {
//        super.customSysExceptionCallBack();
//        glRenderer = null;
//        mSurfaceView = null;
//        releaseCamera();
//        ToastUtil2.showToast(this, getString(R.string.livehost_camera_permission));
//    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 当surfaceview关闭时，关闭预览并释放资源
        /**
         * 记得释放camera，方便其他应用调用
         */
        releaseCamera();
        holder = null;
        mSurfaceView = null;
    }


    private boolean isFirstOnFrameAvailable;

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (isFirstOnFrameAvailable) {
            isFirstOnFrameAvailable = false;
        }
        mSurfaceView.requestRender();
    }


    private Camera mCamera;

    /**
     * 释放mCamera
     */
    private void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.setPreviewTexture(null);
                mCamera.release();
                mCamera = null;
                Log.e(TAG, "release camera");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


