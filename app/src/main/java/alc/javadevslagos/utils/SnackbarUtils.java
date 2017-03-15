package alc.javadevslagos.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import alc.javadevslagos.R;

/**
 * Created by dannytee on 06/03/2017.
 */

public class SnackbarUtils {

    public static void show(View view, String message) {

        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void show(View view, String message, String actionLabel, View.OnClickListener actionClickListener) {

        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(actionLabel, actionClickListener)
                .show();

    }

//    public static void show(Context context, View view, String message) {
//
//        Snackbar snackbar  =  Snackbar.make(view, message, Snackbar.LENGTH_LONG);
//        View snackbarView = snackbar.getView();
////        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.error_yellow));
////        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white));
//        snackbar.show();
//    }

}
