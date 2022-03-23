package com.radanov.movieapp10.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import androidx.core.content.ContextCompat;

import com.radanov.movieapp10.R;

public class ViewUtils {


    private static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, context.getString(R.string.please_wait));
    }
    public static void showProgressDialog(Context context, String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            dismissDialog();
        }
        mProgressDialog = new ProgressDialog(context);

        Typeface new_font = Typeface.createFromAsset(context.getAssets(), "fonts/Inter-UI-Regular.otf");
        SpannableString message1 = new SpannableString(message);
        message1.setSpan(new CustomTypefaceSpan("", new_font), 0, message.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        message1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.black)), 0, message.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        message1.setSpan(new RelativeSizeSpan(1.2f), 0, message.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        mProgressDialog.setMessage(message1);
        mProgressDialog.setProgressStyle(ContextCompat.getColor(context, R.color.black));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    private static void dismissDialog() {
        Context context = ((ContextWrapper) mProgressDialog.getContext()).getBaseContext();
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                mProgressDialog.dismiss();
            }
        } else {
            mProgressDialog.dismiss();
        }
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            dismissDialog();
        }
        mProgressDialog = null;
    }


}
