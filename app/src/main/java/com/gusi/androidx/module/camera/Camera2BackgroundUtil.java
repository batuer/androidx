package com.gusi.androidx.module.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @作者 Liushihua
 * @创建时间 2021-2-3 10:54
 * @描述
 */
public class Camera2BackgroundUtil {
    private static final String TAG = "Ylw_Camera2BackgroundUtil";
    private Context context;
    private CameraCallBack cameraCallBack;
    private CameraManager cameraManager;
    // 默认相机id是0 LENS_FACING_FRONT,LENS_FACING_BACK
    private int cameraId = CameraCharacteristics.LENS_FACING_FRONT;
    private CameraDevice mCameraDevice;
    private String savePath;

    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private CaptureRequest request;
    private ExecutorService service;
    private boolean isTakedPicture = false;//是否已经拍照

    private int needSetOrientation = 0;// 设置默认的拍照方向
    private boolean isInitOk = false;// 是否初始化成功
    private boolean isSessionClosed = true;// captureSession是否被关闭
    private boolean isCameraDoing = false;// 是否正在使用相机
    private final long CAPTURE_DELAY_TIME_LONG = 1200;// 延时拍照——聚焦需要时间

    private final int HANDLER_ERR = 3;// 拍照失败
    private final int HANDLER_TAKE_PHOTO_SUCCESS = 5;// 拍照成功
    private List<Integer> enableCameraList;//可用摄像头列表
    private boolean mFlashSupported = false;//是否支持闪光灯
    private List<Size> recordSizeList;// 录制尺寸
    private Size mPreviewOutputSize;// 预览尺寸
    private List<Size> imgOutputSizes;// 拍照尺寸

    private final int PREVIEW_TYPE_NORMAL = 0;// 默认预览
    private final int PREVIEW_TYPE_RECORD = 1;// 录屏预览
    private final int PREVIEW_TYPE_TAKE_PHOTO = 2;// 拍照预览
    private int previewType = 0;//默认预览

    private long lastSaveFileTime = 0;

    /**
     * 处理静态图片的输出
     */
    private ImageReader imageReader;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_ERR:
                    cameraCallBack.onErr("" + msg.obj);
                    isCameraDoing = false;
                    break;
                case HANDLER_TAKE_PHOTO_SUCCESS:
                    cameraCallBack.onTakePhotoOk(savePath);
                    break;
            }
        }
    };

    /**
     * 当相机设备的状态发生改变的时候，将会回调。
     */
    protected final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        /**
         * 当相机打开的时候，调用
         * @param cameraDevice
         */
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Log.i(TAG, "onOpened: ");
            mCameraDevice = cameraDevice;
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.i(TAG, "onDisconnected: " + cameraDevice);
            cameraDevice.close();
            mCameraDevice = null;
            Message message = new Message();
            message.what = HANDLER_ERR;
            message.obj = "后台相机断开连接";
            handler.sendMessage(message);
        }

        /**
         * 发生异常的时候调用
         *
         * 这里释放资源，然后关闭界面
         * @param cameraDevice
         * @param error
         */
        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            Log.i(TAG, "onError: ");
            cameraDevice.close();
            mCameraDevice = null;
            Message message = new Message();
            message.what = HANDLER_ERR;
            message.obj = "相机设备异常,请重启！";
            handler.sendMessage(message);
        }

        /**
         *当相机被关闭的时候
         */
        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            super.onClosed(camera);
            Log.i(TAG, "onClosed: ");
            mCameraDevice = null;
            isCameraDoing = false;
        }
    };

    /**
     * 相机状态回调
     */
    private CameraManager.AvailabilityCallback callback = new CameraManager.AvailabilityCallback() {
        @Override
        public void onCameraAvailable(@NonNull String cameraId) {// 相机可用
            super.onCameraAvailable(cameraId);
            Log.i(TAG, "onCameraAvailable: ");
        }

        @Override
        public void onCameraUnavailable(@NonNull String cameraId) {// 相机不可用
            super.onCameraUnavailable(cameraId);
            Log.i(TAG, "onCameraUnavailable: ");
        }
    };


    /**
     * 初始化
     *
     * @param activity
     * @param cameraCallBack 回调
     */
    public Camera2BackgroundUtil(Context activity, @NonNull CameraCallBack cameraCallBack) {
        this.context = activity;
        this.cameraCallBack = cameraCallBack;
        cameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        // 对于静态图片，使用可用的最大值来拍摄。
        if (cameraManager != null) {
            isInitOk = true;
            cameraManager.registerAvailabilityCallback(callback, null);
            getCameraInfo();
            setupImageReader();
            service = Executors.newSingleThreadExecutor();
        }
    }

    /**
     * 获取摄像头信息
     */
    public void getCameraInfo() {
        if (enableCameraList == null) {
            getCameras();
        }
        try {
            CameraCharacteristics mCameraCharacteristics =
                    cameraManager.getCameraCharacteristics(String.valueOf(cameraId));
            // 设置是否支持闪光灯
            Boolean available = mCameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            mFlashSupported = available == null ? false : available;
            StreamConfigurationMap map =
                    mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map == null) {
                Log.i(TAG, "getCameraInfo: Could not get configuration map");
                return;
            }
            int mSensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            //这个方法来获取CameraSensor的方向。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Log.i(TAG,
                        "camera sensor orientation:" + mSensorOrientation + ",display rotation=" + context.getDisplay().getRotation());
            }

            int[] formats = map.getOutputFormats();//获得手机支持的输出格式，其中jpeg是一定会支持的，yuv_420_888是api21才开始支持的
            for (int format : formats) {
                Log.i(TAG, "手机格式支持: " + format);
            }
            Size[] yuvOutputSizes = map.getOutputSizes(ImageFormat.YUV_420_888);
            Size[] mediaOutputSizes = map.getOutputSizes(MediaRecorder.class);
            Size[] previewOutputSizes = map.getOutputSizes(SurfaceTexture.class);
            Size[] jpegOutputSizes = map.getOutputSizes(ImageFormat.JPEG);

            recordSizeList = new ArrayList<>();
            imgOutputSizes = new ArrayList<>();

            for (Size size : mediaOutputSizes) {
                recordSizeList.add(new Size(size.getWidth(), size.getHeight()));
                Log.i(TAG, "mediaOutputSizes: " + size.toString());
            }
            for (Size size : jpegOutputSizes) {
                imgOutputSizes.add(new Size(size.getWidth(), size.getHeight()));
                Log.i(TAG, "jpegOutputSizes: " + size.toString());
            }
            for (Size size : previewOutputSizes) {
                Log.i(TAG, "previewOutputSizes: " + size.toString());
            }
            for (Size size : yuvOutputSizes) {
                Log.i(TAG, "yuvOutputSizes: " + size.toString());
            }
            Log.i(TAG, "---------------------------------------------------");
        } catch (Exception e) {
            Log.e(TAG, "getCameraInfo: ", e);
        }
    }

    /**
     * 获得可用的摄像头
     *
     * @return SparseArray of available cameras ids。key为摄像头方位，见CameraCharacteristics#LENS_FACING，value为对应的摄像头id
     */
    public void getCameras() {
        if (cameraManager == null) return;
        enableCameraList = new ArrayList<>();
        try {
            String[] camerasAvailable = cameraManager.getCameraIdList();
            CameraCharacteristics cam;
            Integer characteristic;

            for (String id : camerasAvailable) {
                Log.i(TAG, "getCameras: " + id);
                try {
                    enableCameraList.add(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "getCameras: ", e);
        }
    }


    private void setupImageReader() {
        //前三个参数分别是需要的尺寸和格式，最后一个参数代表每次最多获取几帧数据，本例的3代表ImageReader中最多可以获取2帧图像流
        imageReader = ImageReader.newInstance(640, 480, ImageFormat.JPEG, 1);
        //监听ImageReader的事件，当有图像流数据可用时会回调onImageAvailable方法，它的参数就是预览帧数据，可以对这帧数据进行处理
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Log.i(TAG, "onImageAvailable: " + reader);
                Image image = reader.acquireLatestImage();
                //我们可以将这帧数据转成字节数组，类似于Camera1的PreviewCallback回调的预览帧数据
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                image.close();
                saveFile(data, savePath);
            }
        }, null);
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        Log.i(TAG, "openCamera: ");
        isCameraDoing = true;
        // 设置TextureView的缓冲区大小
        // 获取Surface显示预览数据
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Message message = new Message();
            message.what = HANDLER_ERR;
            message.obj = "权限不足";
            handler.sendMessage(message);
            return;
        }
        try {
            if (cameraId == (enableCameraList.size() - 1)) {
                cameraId = 0;
            }
            cameraManager.openCamera(Integer.toString(cameraId), stateCallback, null);
            Log.i(TAG, "openCamera: " + cameraId++);
        } catch (Exception e) {
            Log.e(TAG, "openCamera: ", e);
            e.printStackTrace();
            Message message = new Message();
            message.what = HANDLER_ERR;
            message.obj = "打开相机失败";
            handler.sendMessage(message);
        }
    }


    /**
     * 开始视频录制预览
     */
    private void startPreview() {
        Log.i(TAG, "startPreview: ");
        // CaptureRequest添加imageReaderSurface，不加的话就会导致ImageReader的onImageAvailable()方法不会回调
        // 创建CaptureSession时加上imageReaderSurface，如下，这样预览数据就会同时输出到previewSurface和imageReaderSurface了
        try {
            // 创建CaptureRequestBuilder，TEMPLATE_PREVIEW比表示预览请求
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            mPreviewRequestBuilder.addTarget(imageReader.getSurface());// 设置Surface作为预览数据的显示界面
            // 创建相机捕获会话，第一个参数是捕获数据的输出Surface列表，第二个参数是CameraCaptureSession的状态回调接口，当它创建好后会回调onConfigured
            // 方法，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            mCameraDevice.createCaptureSession(Arrays.asList(imageReader.getSurface()), captureSessionStateCallBack,
                    null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Message messagef = new Message();
            messagef.what = HANDLER_ERR;
            messagef.obj = "捕获帧失败";
            handler.sendMessage(messagef);
            Log.e(TAG, "startPreview: ", e);
        }
    }

    /**
     * 捕获图片数据
     */
    private CameraCaptureSession.StateCallback captureSessionStateCallBack = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                mCameraCaptureSession = session;
                isSessionClosed = false;
                request = mPreviewRequestBuilder.build();
                // 设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                mCameraCaptureSession.setRepeatingRequest(request, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                Message message = new Message();
                message.what = HANDLER_ERR;
                message.obj = "开启图像预览失败";
                handler.sendMessage(message);
            }
            if (!isTakedPicture) {
                isTakedPicture = true;
                handler.postDelayed(() -> takePicture(), CAPTURE_DELAY_TIME_LONG);
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
            Log.i(TAG, "onConfigureFailed: ");
        }
    };


    public void startTakePicture(String savePath) {
        Log.i(TAG, "startTakePicture: ");
        this.savePath = savePath;
        if (isCameraDoing) {
            Log.i(TAG, "startTakePicture: ");
        } else {
            isTakedPicture = false;
            openCamera();
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        Log.i(TAG, "takePicture: ");
        try {
            if (mCameraDevice == null || mPreviewRequestBuilder == null) return;
            mPreviewRequestBuilder.addTarget(imageReader.getSurface());
            //设置拍照方向
            mPreviewRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, this.needSetOrientation);
            //这个回调接口用于拍照结束时重启预览，因为拍照会导致预览停止
            CameraCaptureSession.CaptureCallback mImageSavedCallback = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request,
                                               TotalCaptureResult result) {
                    Log.i(TAG, "onCaptureCompleted: ");
                    onStop();
                }
            };
            //开始拍照，然后回调上面的接口重启预览，因为mCaptureBuilder设置ImageReader作为target，所以会自动回调ImageReader的onImageAvailable()方法保存图片
            mCameraCaptureSession.capture(mPreviewRequestBuilder.build(), mImageSavedCallback, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "takePicture: ", e);
            e.printStackTrace();
            Message message = new Message();
            message.what = HANDLER_ERR;
            message.obj = "拍照失败";
            handler.sendMessage(message);
        }
    }

    /**
     * 停止预览，释放资源
     */
    public void stopRecord() {
        Log.i(TAG, "stopRecord: ");
        try {
            if (mCameraCaptureSession != null && !isSessionClosed) {
                mCameraCaptureSession.stopRepeating();
                mCameraCaptureSession.abortCaptures();
                mCameraCaptureSession.close();
                isSessionClosed = true;
            }
            if (mCameraDevice != null)
                mCameraDevice.close();
            isCameraDoing = false;
        } catch (Exception e) {
            Log.e(TAG, "stopRecord: ", e);
        }
    }

    /**
     * 重置后，开始预览
     */
    public void reset() {
        previewType = PREVIEW_TYPE_NORMAL;
        stopRecord();
        openCamera();
    }

    /**
     * 在 activity,fragment的onStop中调用
     */
    public void onStop() {
        stopRecord();
    }

    /**
     * 注销 回调
     */
    public void onDestroy() {
        this.cameraCallBack = null;
        if (cameraManager != null)
            cameraManager.unregisterAvailabilityCallback(callback);
    }


    /**
     * 设置输出数据尺寸选择器，在selectCamera之前设置有效
     * （一般手机支持多种输出尺寸，请用户根据自身需要选择最合适的一种）
     * 举例：
     * SizeSelector maxPreview = SizeSelectors.and(SizeSelectors.maxWidth(720), SizeSelectors.maxHeight(480));
     * SizeSelector minPreview = SizeSelectors.and(SizeSelectors.minWidth(320), SizeSelectors.minHeight(240));
     * camera.setmOutputSizeSelector(SizeSelectors.or(
     * SizeSelectors.and(maxPreview, minPreview)//先在最大和最小中寻找
     * , SizeSelectors.and(maxPreview, SizeSelectors.biggest())//找不到则按不超过最大尺寸的那个选择
     * ));
     */
    public void getOutputSizeSelector() {

    }


    //覆盖性保存
    private void saveFile(final byte[] data, final String savePath) {
        if (data == null || data.length == 0) return;
        if (System.currentTimeMillis() - lastSaveFileTime > 1000)
            service.execute(() -> {
                File file = new File(savePath);
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                if (file.exists()) {
                    file.delete();
                }
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.flush();
                    fos.close();
                    lastSaveFileTime = System.currentTimeMillis();
                    Message message = new Message();
                    message.what = HANDLER_TAKE_PHOTO_SUCCESS;
                    message.obj = savePath;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "saveFile: ", e);
                    Message message = new Message();
                    message.what = HANDLER_ERR;
                    message.obj = "图片保存失败";
                    handler.sendMessage(message);
                }
            });
    }
}
