import android.app.Application;
import android.content.Context;

/**
 * Created by dannytee on 07/03/2017.
 */

public class JavaDevsApplication extends Application {


    private static JavaDevsApplication sInstance;

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public static JavaDevsApplication getNewInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}
