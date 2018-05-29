package com.patis.wms.android.screen;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;

public interface Initializable {


    void initializeData(Bundle bundle);
    void initializeView(Bundle bundle);

    default void startInitialization(Bundle bundle){

        if (bundle == null)
            bundle = new Bundle();

        new AsyncTask<Bundle, Bundle, Bundle>(){


            @Override
            protected Bundle doInBackground(Bundle... objects) {
                Bundle bundle = objects[0];
                initializeData(bundle);
                return bundle;
            }

            @Override
            protected void onPostExecute(Bundle bundle) {
                super.onPostExecute(bundle);
                initializeView(bundle);
            }
        }.execute(bundle);

    }



}
