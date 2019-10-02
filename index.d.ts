

declare const _default: {
    // e.g. "Park Staging"
    appName: () => string,

    // The external-facing version of the app, e.g. "1.0.10"
    appVersion: () => string,

    // The internal-facing build number, e.g. "145"
    buildNumber: () => string,
    bundleId: () => string,
    // non-marketing device ID, e.g. "iPhone12,1"
    deviceId: () => string,
    // marking device ID, e.g. "iPhone 11"
    deviceName: () => string,
    // One of: "Handset", "Tablet", "Tv", "Unknown"
    deviceTypeName: () => string,

    //
    fontScale: () => string,
    isEmulator: () => string,
    isTablet: () => string,
    supportedAbis: () => string,
    systemName: () => string
    systemVersion: () => string,
};

export default _default;
