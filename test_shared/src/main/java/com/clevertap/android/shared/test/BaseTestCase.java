package com.clevertap.android.shared.test;

import android.os.Build;
import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.CleverTapInstanceConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.annotation.Config;

import static com.clevertap.android.shared.test.Constant.ACC_ID;
import static com.clevertap.android.shared.test.Constant.ACC_TOKEN;

@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P},
        application = TestApplication.class
)
@RunWith(AndroidJUnit4.class)
@PrepareForTest({CleverTapAPI.class, CleverTapInstanceConfig.class})
public abstract class BaseTestCase {

    protected CleverTapAPI cleverTapAPI;
    protected TestApplication application;
    protected CleverTapInstanceConfig cleverTapInstanceConfig;

    public static void assertBundlesEquals(Bundle expected, Bundle actual) {
        assertBundlesEquals(null, expected, actual);
    }

    public static void assertBundlesEquals(String message, Bundle expected, Bundle actual) {
        if (!areEqual(expected, actual)) {
            Assert.fail(message + " <" + expected.toString() + "> is not equal to <" + actual.toString() + ">");
        }
    }

    public static boolean areEqual(Bundle expected, Bundle actual) {
        if (expected == null) {
            return actual == null;
        }

        if (expected.size() != actual.size()) {
            return false;
        }

        for (String key : expected.keySet()) {
            if (!actual.containsKey(key)) {
                return false;
            }

            Object expectedValue = expected.get(key);
            Object actualValue = actual.get(key);

            if (expectedValue == null) {
                if (actualValue != null) {
                    return false;
                }

                continue;
            }

            if (expectedValue instanceof Bundle && actualValue instanceof Bundle) {
                if (!areEqual((Bundle) expectedValue, (Bundle) actualValue)) {
                    return false;
                }

                continue;
            }

            if (!expectedValue.equals(actualValue)) {
                return false;
            }
        }

        return true;
    }

    @Before
    public void setUp() throws Exception {
        application = TestApplication.getApplication();
        cleverTapAPI = Mockito.mock(CleverTapAPI.class);
        cleverTapInstanceConfig = CleverTapInstanceConfig.createInstance(application, ACC_ID, ACC_TOKEN);
    }

    public TestApplication getApplication() {
        return TestApplication.getApplication();
    }

}
