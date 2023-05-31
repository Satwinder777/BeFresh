package com.example.befresh.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.befresh.R;


public class LoadingDialog {

    static LoadingDialog mInstance;
    Dialog mProgress;

    public synchronized static LoadingDialog getLoader() {
        if (mInstance != null) {
        }
        else {
            mInstance = new LoadingDialog();
        }
        return mInstance;
    }

  /*  public boolean visibile()
    {
        if (mProgress!= null)

    }*/

    public void showLoader(Context con) {
        if (mProgress == null) {
            mProgress = new Dialog(con);
            mProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams wmlp = mProgress.getWindow().getAttributes();

            wmlp.gravity = Gravity.CENTER;
            mProgress.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mProgress.setCancelable(false);
            mProgress.setContentView(R.layout.view_progress_dialog);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            mProgress.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int mScreenwidth = displayMetrics.widthPixels;
            mProgress.show();
            // mProgress.cancel();
        }

    }

    public void dismissLoader() {
        try {
            if (mProgress != null) {
                mProgress.cancel();
                mProgress = null;
            }
        } catch (Exception e) {
        }
    }
}
