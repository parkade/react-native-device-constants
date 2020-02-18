package com.reactlibrary.deviceconstants;

import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.util.HashMap;
import java.util.Map;


public class DeviceConstantsModule extends ReactContextBaseJavaModule {

    public DeviceConstantsModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    public enum DeviceType {
        HANDSET("Handset"),
        TABLET("Tablet"),
        TV("Tv"),
        UNKNOWN("Unknown");

        private final String value;

        DeviceType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public String getName() {
        return "DeviceConstants";
    }

    private boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.toLowerCase().contains("droid4x")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.HARDWARE.contains("vbox86")
            || Build.PRODUCT.contains("sdk")
            || Build.PRODUCT.contains("google_sdk")
            || Build.PRODUCT.contains("sdk_google")
            || Build.PRODUCT.contains("sdk_x86")
            || Build.PRODUCT.contains("vbox86p")
            || Build.PRODUCT.contains("emulator")
            || Build.PRODUCT.contains("simulator")
            || Build.BOARD.toLowerCase().contains("nox")
            || Build.BOOTLOADER.toLowerCase().contains("nox")
            || Build.HARDWARE.toLowerCase().contains("nox")
            || Build.PRODUCT.toLowerCase().contains("nox")
            || Build.SERIAL.toLowerCase().contains("nox")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"));
    }

    private boolean isTablet() {
        return getDeviceType() == DeviceType.TABLET;
    }

    private DeviceType getDeviceType() {
        // Detect TVs via ui mode (Android TVs) or system features (Fire TV).
        if (getReactApplicationContext().getPackageManager().hasSystemFeature("amazon.hardware.fire_tv")) {
            return DeviceType.TV;
        }


        UiModeManager uiManager = (UiModeManager) getReactApplicationContext().getSystemService(Context.UI_MODE_SERVICE);
        if (uiManager != null && uiManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            return DeviceType.TV;
        }

        DeviceType deviceTypeFromConfig = getDeviceTypeFromResourceConfiguration();

        if (deviceTypeFromConfig != null && deviceTypeFromConfig != DeviceType.UNKNOWN) {
            return deviceTypeFromConfig;
        }

        return getDeviceTypeFromPhysicalSize();
    }

    // Use `smallestScreenWidthDp` to determine the screen size
    // https://android-developers.googleblog.com/2011/07/new-tools-for-managing-screen-sizes.html
    private DeviceType getDeviceTypeFromResourceConfiguration() {
        int smallestScreenWidthDp = getReactApplicationContext().getResources().getConfiguration().smallestScreenWidthDp;

        if (smallestScreenWidthDp == Configuration.SMALLEST_SCREEN_WIDTH_DP_UNDEFINED) {
            return DeviceType.UNKNOWN;
        }

        return smallestScreenWidthDp >= 600 ? DeviceType.TABLET : DeviceType.HANDSET;
    }

    private DeviceType getDeviceTypeFromPhysicalSize() {
        // Find the current window manager, if none is found we can't measure the device physical size.
        WindowManager windowManager = (WindowManager) getReactApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        if (windowManager == null) {
            return DeviceType.UNKNOWN;
        }

        // Get display metrics to see if we can differentiate handsets and tablets.
        // NOTE: for API level 16 the metrics will exclude window decor.
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
        } else {
            windowManager.getDefaultDisplay().getMetrics(metrics);
        }

        // Calculate physical size.
        double widthInches = metrics.widthPixels / (double) metrics.xdpi;
        double heightInches = metrics.heightPixels / (double) metrics.ydpi;
        double diagonalSizeInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));

        if (diagonalSizeInches >= 3.0 && diagonalSizeInches <= 6.9) {
            // Devices in a sane range for phones are considered to be Handsets.
            return DeviceType.HANDSET;
        } else if (diagonalSizeInches > 6.9 && diagonalSizeInches <= 18.0) {
            // Devices larger than handset and in a sane range for tablets are tablets.
            return DeviceType.TABLET;
        } else {
            // Otherwise, we don't know what device type we're on/
            return DeviceType.UNKNOWN;
        }
    }

    private PackageInfo getPackageInfo() throws Exception {
        return getReactApplicationContext().getPackageManager().getPackageInfo(getReactApplicationContext().getPackageName(), 0);
    }

    private float getFontScale() {
        return getReactApplicationContext().getResources().getConfiguration().fontScale;
    }

    private String getAppVersion() {
        try {
            return getPackageInfo().versionName;
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getBuildNumber() {
        try {
            return Integer.toString(getPackageInfo().versionCode);
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getAppName() {
        try {
            return getReactApplicationContext().getApplicationInfo().loadLabel(getReactApplicationContext().getPackageManager()).toString();
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getDeviceName() {
        try {
            String bluetoothName = Settings.Secure.getString(getReactApplicationContext().getContentResolver(), "bluetooth_name");
            if (bluetoothName != null) {
                return bluetoothName;
            }

            if (Build.VERSION.SDK_INT >= 25) {
                String deviceName = Settings.Global.getString(getReactApplicationContext().getContentResolver(), Settings.Global.DEVICE_NAME);
                if (deviceName != null) {
                    return deviceName;
                }
            }
        } catch (Exception e) {
            // same as default unknown return
        }
        return "unknown";
    }

    private String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    private String getModel() {
        return Build.MODEL;
    }

    private String getBrand() {
        return Build.BRAND;
    }

    private String getBuildId() {
        return Build.ID;
    }

    private String getBundleId() {
        return getReactApplicationContext().getPackageName();
    }

    private WritableArray getSupportedAbis() {
        WritableArray array = new WritableNativeArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (String abi : Build.SUPPORTED_ABIS) {
                array.pushString(abi);
            }
        } else {
            array.pushString(Build.CPU_ABI);
        }
        return array;
    }

    private String getDeviceId() {
        return Build.BOARD;
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("appName", this.getAppName());
        constants.put("appVersion", this.getAppVersion());
        constants.put("appBuildNumber", this.getBuildNumber());
        constants.put("appBundleId", this.getBundleId());
        constants.put("deviceId", this.getDeviceId());
        constants.put("deviceName", this.getDeviceName());
        constants.put("deviceType", this.getDeviceType().getValue());
        constants.put("deviceModel", this.getModel());
        constants.put("deviceBrand", this.getBrand());
        constants.put("fontScale", this.getFontScale());
        constants.put("isEmulator", this.isEmulator());
        constants.put("isTablet", this.isTablet());
        constants.put("supportedAbis", this.getSupportedAbis());
        constants.put("systemBuildId", this.getBuildId());
        constants.put("systemName", this.getDeviceName());
        constants.put("systemVersion", this.getSystemVersion());

        return constants;
    }
}
