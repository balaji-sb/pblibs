package com.pblibs.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.pblibs.base.PBApplication;
import com.pblibs.pbinterfaces.NextActionCallback;

public class PBAlertWidgets {

    private static PBAlertWidgets mInstance;

    public static PBAlertWidgets getInstance() {
        if (mInstance == null) {
            mInstance = new PBAlertWidgets();
        }
        return mInstance;
    }

    /**
     * Show Alert dialog with custom title, message, icon
     *
     * @param context
     * @param title
     * @param message
     * @param icon
     * @param positiveText
     * @param negativeText
     * @param callback
     */

    public void showAlertDialog(Context context, String title, String message, int icon, String positiveText,
                                String negativeText, final NextActionCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(icon);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onPositiveClick();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onNegativeClick();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Show information dialog
     *
     * @param context
     * @param title
     * @param message
     * @param icon
     * @param neutralBtnText
     */

    public void showInfoDialog(Context context, String title, String message, int icon, String neutralBtnText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(icon);
        builder.setCancelable(false);
        builder.setNeutralButton(neutralBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Show custom dialog with custom view and theme
     *
     * @param context
     * @param title
     * @param message
     * @param view
     * @param theme
     */

    public void showCustomDialog(Context context, String title, String message, int view, int theme) {
        Dialog dialog = new Dialog(context, theme);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public void showSnack(View view, String message, String action, int length, boolean isSuccess) {
        if (isSuccess) {

        }
    }

    /**
     * Show Message as Toast
     *
     * @param message
     * @param length
     */

    public void showToast(String message, int length) {
        Toast.makeText(PBApplication.getInstance().getContext(), message, length).show();
    }

}
