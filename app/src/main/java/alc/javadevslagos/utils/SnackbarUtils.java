package alc.javadevslagos.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

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
}
