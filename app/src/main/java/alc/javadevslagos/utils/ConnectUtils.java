package alc.javadevslagos.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dannytee on 06/03/2017.
 */

public class ConnectUtils {

    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        // NetworkInfo information
        if (networkInfo != null && networkInfo.isConnected()
                && networkInfo.isAvailable()) {

            return true;

        }

        return false;

    }
}
