package main;

import android.app.Application;
import android.content.Context;

/**
 * Created by David on 26-Sep-16.
 */

public class App extends Application {

        private static Context context;

        public void onCreate() {
            super.onCreate();
            App.context = getApplicationContext();
        }

        public static Context getAppContext() {
            return App.context;
        }

}
