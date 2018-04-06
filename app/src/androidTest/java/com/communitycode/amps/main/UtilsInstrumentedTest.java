package com.communitycode.amps.main;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.communitycode.amps.main.Utils.flattenViewGroup;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {
    @Test
    public void flattenViewGroup_null() throws Exception {
        View[] expected = {};
        assertArrayEquals(expected, flattenViewGroup(null).toArray());
    }


    @Test
    public void flattenViewGroup_singleElement() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ViewGroup viewGroup = new LinearLayout(appContext);
        View A = new View(appContext);
        viewGroup.addView(A);
        View[] expected = {
                A
        };
        assertArrayEquals(expected, flattenViewGroup(viewGroup).toArray());
    }

    @Test
    public void flattenViewGroup_multipleElements() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ViewGroup viewGroup = new LinearLayout(appContext);
        View A = new View(appContext);
        View B = new View(appContext);
        viewGroup.addView(A);
        viewGroup.addView(B);
        View[] expected = {
                A, B
        };
        assertArrayEquals(expected, flattenViewGroup(viewGroup).toArray());
    }

    @Test
    public void flattenViewGroup_nestedElements() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ViewGroup viewGroup = new LinearLayout(appContext);
        View A = new View(appContext);
        View B = new View(appContext);
        ViewGroup C = new LinearLayout(appContext);
        viewGroup.addView(A);
        viewGroup.addView(C);
        C.addView(B);
        View[] expected = {
                A, C, B
        };
        assertArrayEquals(expected, flattenViewGroup(viewGroup).toArray());
    }
}
