package com.winson.widget;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.io.File;
import java.lang.reflect.TypeVariable;

import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;

/**
 * @date on 2018/9/18
 * @Author Winson
 */
public class PhotoSelectUtils {

    public static final int REQ_ALBUM = 12019;
    public static final int REQ_CAMERA = 12020;
    public static final int REQ_CROP = 12021;

    public static final int REQ_SELECT_PHOTO_PERMISSION = 20001;
    public static final int REQ_SELECT_CAMERA_PERMISSION = 20002;

    public interface OnPhotoSelectListener {

        void onPhotoSelect(String path);

    }

    private Activity activity;
    private int outputX;
    private int outputY;
    private boolean circle;
    private String cameraPhotoPath;
    private OnPhotoSelectListener onPhotoSelectListener;

    public PhotoSelectUtils(Activity activity) {
        this(activity, 300, 300);
    }

    public PhotoSelectUtils(Activity activity, int outputX, int outputY) {
        this.activity = activity;
        float density = activity.getResources().getDisplayMetrics().density;
        outputX = (int) (outputX * density);
        outputY = (int) (outputY * density);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_SELECT_PHOTO_PERMISSION) {
            if (checkSelectPhotoPermission()) {
                startPhotoPick();
            }
        } else if (requestCode == REQ_SELECT_CAMERA_PERMISSION) {
            if (checkSelectCameraPermission()) {
                startCamera();
            }
        }
    }

    public boolean isCircle() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public void setOnPhotoSelectListener(OnPhotoSelectListener onPhotoSelectListener) {
        this.onPhotoSelectListener = onPhotoSelectListener;
    }

    private boolean checkSelectPhotoPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || PermissionChecker.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSelectPhotoPermissionAndRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        if (!checkSelectPhotoPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_SELECT_PHOTO_PERMISSION);
            }
            return false;
        }
        return true;
    }

    private boolean checkSelectCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || PermissionChecker.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || PermissionChecker.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSelectCameraPermissionAndRequest() {
        if (!checkSelectCameraPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_SELECT_CAMERA_PERMISSION);
            }
            return false;

        }
        return true;
    }

    public void selectFromPhotoAlbum() {
        if (checkSelectPhotoPermissionAndRequest()) {
            startPhotoPick();
        }
    }

    private void startPhotoPick() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, REQ_ALBUM);
    }

    public void selectByCamera() {
        if (checkSelectCameraPermissionAndRequest()) {
            startCamera();
        }
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraPhotoPath = getPath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraPhotoPath)));
        activity.startActivityForResult(intent, REQ_CAMERA);
    }

    private void startPhotoCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", Uri.parse(getOutputPath()));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("circleCrop", circle);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, REQ_CROP);
    }

    private String getOutputPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Android/data/cache" + activity.getPackageName());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            return directory.getAbsolutePath() + "/avatar.jpg";
        } else {
            return activity.getCacheDir().getAbsolutePath() + File.separator + "avatar.jpg";
        }
    }

    private String getPath() {
        String dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory() + "/adui/pictures/";
        } else {
            dir = activity.getCacheDir().getAbsolutePath() + "/adui/pictures/";
        }
        File dirFolder = new File(dir);
        if (!dirFolder.exists()) {
            dirFolder.mkdirs();
        }
        return dir + System.currentTimeMillis() + ".jpg";
    }

    public void deleteSelectCacheFile() {
        File file = new File(getOutputPath());
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_ALBUM) {
                if (onPhotoSelectListener != null) {
                    onPhotoSelectListener.onPhotoSelect(getRealPathFromUri(activity, data.getData()));
                }
                return true;
            } else if (requestCode == REQ_CAMERA) {
                if (onPhotoSelectListener != null) {
                    onPhotoSelectListener.onPhotoSelect(cameraPhotoPath);
                }
                return true;
            }
        }
        return false;
    }

    public boolean onActivityResultForCrop(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_ALBUM) {
                startPhotoCrop(data.getData());
                return true;
            } else if (requestCode == REQ_CAMERA) {
                startPhotoCrop(Uri.fromFile(new File(cameraPhotoPath)));
                return true;
            } else if (requestCode == REQ_CROP) {
                if (onPhotoSelectListener != null) {
                    onPhotoSelectListener.onPhotoSelect(getOutputPath());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public void showPhotoSelectActionSheet(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_select_photo, null);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        contentView.setMinimumWidth(windowManager.getDefaultDisplay().getWidth());

        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(contentView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        final View cancel = contentView.findViewById(R.id.cancel);
        final View album = contentView.findViewById(R.id.album);
        final View camera = contentView.findViewById(R.id.camera);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectByCamera();
                dialog.dismiss();
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromPhotoAlbum();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

}
