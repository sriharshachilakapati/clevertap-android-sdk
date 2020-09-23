package com.clevertap.android.geofence;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.clevertap.android.geofence.CTGeofenceSettings.ACCURACY_HIGH;
import static com.clevertap.android.geofence.CTGeofenceSettings.ACCURACY_MEDIUM;
import static com.clevertap.android.geofence.CTGeofenceSettings.DEFAULT_GEO_MONITOR_COUNT;
import static com.clevertap.android.geofence.CTGeofenceSettings.FETCH_CURRENT_LOCATION_PERIODIC;
import static com.clevertap.android.geofence.CTGeofenceSettings.FETCH_LAST_LOCATION_PERIODIC;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28,
        application = TestApplication.class
)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "androidx.*", "org.json.*"})
@PrepareForTest({Utils.class})
public class CTGeofenceSettingsTest extends BaseTestCase {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Utils.class);
        super.setUp();
    }

    @Test
    public void testDefaultSettings() {

        CTGeofenceSettings defaultSettings = new CTGeofenceSettings.Builder().build();

        assertEquals(true, defaultSettings.isBackgroundLocationUpdatesEnabled());
        assertEquals(ACCURACY_HIGH, defaultSettings.getLocationAccuracy());
        assertEquals(FETCH_LAST_LOCATION_PERIODIC, defaultSettings.getLocationFetchMode());
        assertEquals(Logger.DEBUG, defaultSettings.getLogLevel());
        assertEquals(DEFAULT_GEO_MONITOR_COUNT, defaultSettings.getGeofenceMonitoringCount());
        assertEquals(null, defaultSettings.getId());
        assertEquals(GoogleLocationAdapter.INTERVAL_IN_MILLIS, defaultSettings.getInterval());
        assertEquals(GoogleLocationAdapter.INTERVAL_FASTEST_IN_MILLIS, defaultSettings.getFastestInterval());
        assertEquals(GoogleLocationAdapter.SMALLEST_DISPLACEMENT_IN_METERS, defaultSettings.getSmallestDisplacement(), 0);
    }

    @Test
    public void testCustomSettings() {

        // when interval, fastestInterval and displacement are valid
        CTGeofenceSettings customSettings = new CTGeofenceSettings.Builder()
                .enableBackgroundLocationUpdates(false)
                .setLogLevel(Logger.INFO)
                .setLocationAccuracy(ACCURACY_MEDIUM)
                .setLocationFetchMode(FETCH_CURRENT_LOCATION_PERIODIC)
                .setGeofenceMonitoringCount(98)
                .setInterval(2000000)
                .setFastestInterval(1900000)
                .setSmallestDisplacement(780)
                .build();


        assertEquals(false, customSettings.isBackgroundLocationUpdatesEnabled());
        assertEquals(ACCURACY_MEDIUM, customSettings.getLocationAccuracy());
        assertEquals(FETCH_CURRENT_LOCATION_PERIODIC, customSettings.getLocationFetchMode());
        assertEquals(Logger.INFO, customSettings.getLogLevel());
        assertEquals(98, customSettings.getGeofenceMonitoringCount());
        assertEquals(null, customSettings.getId());
        assertEquals(2000000, customSettings.getInterval());
        assertEquals(1900000, customSettings.getFastestInterval());
        assertEquals(780, customSettings.getSmallestDisplacement(), 0);

        // when interval, fastestInterval and displacement are invalid

        CTGeofenceSettings inValidSettings = new CTGeofenceSettings.Builder()
                .setInterval(120000)
                .setFastestInterval(120000)
                .setSmallestDisplacement(100)
                .build();


        assertEquals(1800000, inValidSettings.getInterval());
        assertEquals(1800000, inValidSettings.getFastestInterval());
        assertEquals(200, inValidSettings.getSmallestDisplacement(), 0);
    }

}
