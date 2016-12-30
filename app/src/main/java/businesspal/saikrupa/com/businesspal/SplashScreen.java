package businesspal.saikrupa.com.businesspal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Ndroid on 12/15/2016.
 */

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        splash();
    }

    //this method makes the welcome screen stay on the device screen for 2500 milliseconds
    public void splash() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
