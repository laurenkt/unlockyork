package com.example.app;

import android.app.Activity;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

import com.robotium.solo.Solo;


// Testing if error message is displayed on incorrect QR scan

public class MUT1_0b extends ActivityInstrumentationTestCase2<MainActivity>{


    public MUT1_0b() {
        super(MainActivity.class);
    }
    private Solo solo;

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        super.setUp();

    }


    // Verify that the text in the textView is not equal to the default string,
    // meaning a QR code has been scanned.
    private Callable<Boolean> newQRscan(Activity thisActivity) {
        final TextView v = thisActivity.findViewById(R.id.txtResult);
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return !(v.getText().toString().equals("Please focus camera to QR Code")); // The condition that must be fulfilled
            }
        };
    }

    @SmallTest
    public void test() {
        await().until(newQRscan(getActivity()));
        assertTrue(solo.waitForText("Invalid QR Code"));
    }

}