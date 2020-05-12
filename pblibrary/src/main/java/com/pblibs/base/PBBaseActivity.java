package com.pblibs.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pblibrary.proggyblast.BuildConfig;
import com.pblibrary.proggyblast.R;
import com.pblibs.utility.GenericModel;
import com.pblibs.utility.PBFileOperations;
import com.pblibs.utility.PBSessionManager;

import java.io.File;

import static com.pblibs.utility.PBConstants.*;

public abstract class PBBaseActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SCREEN_NAME = "screen";
    private static final int PERMISSION_CODE = 112;
    private static final int SETTINGS_CODE = 117;
    private static final int REQUEST_CAPTURE_IMAGE = 1001;
    private static final int REQUEST_PICK_IMAGE = 1002;
    public static boolean isPermissionGranted = false;
    public Context mContext;
    public View mView;
    public PBSessionManager mPbSessionManager;
    private File photoFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext = PBApplication.getInstance().getContext();
        mPbSessionManager = PBSessionManager.getInstance();
    }

    /**
     * Ask required Permission
     *
     * @param permission
     */

    public void askPermission(String permission[]) {
        ActivityCompat.requestPermissions((Activity) mContext, permission, PERMISSION_CODE);
    }

    /**
     * Check if required permission is granted or not
     *
     * @param permission
     * @return
     */

    public boolean checkPermission(String permission[]) {
        boolean isPermissionGranted = false;
        for (int i = 0; i < permission.length; i++) {
            isPermissionGranted =
                    ActivityCompat.checkSelfPermission(mContext, permission[i]) == PackageManager.PERMISSION_GRANTED;
        }
        return isPermissionGranted;
    }

    /**
     * Navigate to next activity
     *
     * @param redirectClassName
     */

    public void navigateActivity(String redirectClassName, boolean isNewActivity, Bundle bundle) {
        try {
            Class aClass = Class.forName(mContext.getPackageName() + "." + redirectClassName.trim());
            Intent intent = new Intent(PBApplication.getInstance().getContext(), aClass);
            if (isNewActivity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            PBApplication.getInstance().getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hide soft keyboard
     *
     * @param activity
     */

    public void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (null != view && null != view.getWindowToken() && EditText.class.isAssignableFrom(view.getClass())) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), ZERO);
        } else {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /**
     * Show soft keyboard
     *
     * @param activity
     */

    public void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (null != view && null != view.getWindowToken() && EditText.class.isAssignableFrom(view.getClass())) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), ONE);
        } else {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Change Statusbar color
     */

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * Redirect to Settings Activity
     *
     * @param title
     * @param message
     * @param positiveText
     */

    public void redirectToSettingsAlert(String title, String message, String positiveText) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ((Activity) mContext).startActivityForResult(intent, SETTINGS_CODE);
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * Start Intent for camera and gallery
     *
     * @param context
     * @param type
     * @param tempFileName
     * @param directoryName
     * @param isExternal
     */

    public void openCameraIntent(Context context, int type, String tempFileName, String directoryName,
                                 boolean isExternal) {
        if (type == 1) {
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(context.getPackageManager()) != null) {
                photoFile = PBFileOperations.getInstance().createImageFile(tempFileName, directoryName, isExternal);
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(
                            context,
                            BuildConfig.APPLICATION_ID + PROVIDER,
                            photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
                }
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, context.getString(R.string.select_picture)),
                    REQUEST_PICK_IMAGE);
        }
    }

    /**
     * Update Fragment with or without backstack
     *
     * @param fragment
     * @param isBackStack
     */

    public void navigateFragment(Fragment fragment, boolean isBackStack) {
        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isBackStack) {
            transaction.addToBackStack(null);
        }
        transaction = transaction.replace(GenericModel.getInstance().getFrameID(), fragment);
        transaction.commit();
    }

    /**
     * Get current fragment
     *
     * @return
     */

    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        return fragmentManager.findFragmentById(GenericModel.getInstance().getFrameID());
    }

    /**
     *
     */

    public void navigateToPreviousFragment() {
        ((FragmentActivity) mContext).getSupportFragmentManager().popBackStackImmediate();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
    }

    public abstract int getContentView();

}
