package com.example.app;

import android.app.Activity;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import org.junit.Rule;
import android.widget.TextView;
import java.util.concurrent.Callable;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.awaitility.Awaitility.await;
import com.robotium.solo.Solo;


// Testing if app moves to new screen based on correct QR scan

public class MUT1_0a extends ActivityInstrumentationTestCase2<MainActivity>{


    public MUT1_0a() {
        super(MainActivity.class);
    }
    private Solo solo;
    @Rule
    public ActivityTestRule<ScanSuccess> activityTestRule =
            new ActivityTestRule<>(ScanSuccess.class);

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        super.setUp();

    }

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
        TextView tv = (TextView) getActivity().findViewById(R.id.txtResult);
        assertEquals("jorvik", tv.getText().toString());
        onView(withId(R.id.scan_text)).check(matches(withText("Jorvik Viking Centre")));
    }
}
