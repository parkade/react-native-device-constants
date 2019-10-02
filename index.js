import {NativeModules, Platform} from 'react-native';

import {DEVICE_NAMES_BY_CODE, DEVICES_WITH_NOTCH} from "./constants";

const {DeviceConstants} = NativeModules;

const OS = Platform.OS;

function getHasNotch() {
  const brand = getDeviceBrand().toLowerCase();
  const model = getDeviceModel().toLowerCase();
  return DEVICES_WITH_NOTCH.findIndex(
    item =>
      item.brand.toLowerCase() === brand &&
      item.model.toLowerCase() === model
  ) !== -1;
}

function getDeviceModel() {
  let model = 'unknown';
  if (OS === 'ios') {
    let deviceName;
    const deviceId = DeviceConstants.deviceId;
    if (deviceId) {
      deviceName = DEVICE_NAMES_BY_CODE[deviceId];
      if (!deviceName) {
        // Not found on database. At least guess main device type from string contents:
        if (deviceId.startsWith('iPod')) {
          deviceName = 'iPod Touch';
        } else if (deviceId.startsWith('iPad')) {
          deviceName = 'iPad';
        } else if (deviceId.startsWith('iPhone')) {
          deviceName = 'iPhone';
        } else if (deviceId.startsWith('AppleTV')) {
          deviceName = 'Apple TV';
        }
      }
    }
    // Use unknown, otherwise getModel will never be cached.
    model = deviceName || 'unknown';
  } else if (OS === 'android' || OS === 'windows') {
    model = DeviceConstants.deviceModel;
  } else {
    model = 'unknown';
  }
  return model;
}

function getDeviceBrand() {
  let brand = 'unknown';
  if (OS === 'android' || OS === 'windows') {
    brand = DeviceConstants.deviceBrand;
  } else if (OS === 'ios') {
    brand = 'Apple';
  } else {
    brand = 'unknown';
  }
  return brand;
}

export default {
  // e.g. "Park Staging"
  appName: DeviceConstants.appName,

  // The external-facing version of the app, e.g. "1.0.10"
  appVersion: DeviceConstants.appVersion,

  // Internal-facing app build number, e.g. "145"
  appBuildNumber: DeviceConstants.appBuildNumber,

  // bundle ID of the app, something like "com.parkwithpark.ios"
  appBundleId: DeviceConstants.appBundleId,

  // "Brand" of the device, e.g. "Apple"
  deviceBrand: getDeviceBrand(),

  // non-marketing device ID, e.g. "iPhone12,1"
  deviceId: DeviceConstants.deviceId,

  // marketing device ID, e.g. "iPhone 11"
  deviceName: DeviceConstants.deviceName,

  // One of: "Handset", "Tablet", "Tv", "Unknown"
  deviceType: DeviceConstants.deviceType,

  // Generic model of this device, e.g. "iPod", "iPhone", "iPad", "Apple TV", or "Pixel XL"
  deviceModel: getDeviceModel(),

  // How much to scale up or scale down your font pt values
  // For example, 0.95 means you can scale your 16pt down to (16 * .95 = ) 15pt and have the font be the same size.
  fontScale: DeviceConstants.fontScale,

  // Whether the phone has a "notch" at the top like an iPhone X.
  hasNotch: getHasNotch(),

  // Whether the device is an emulator
  isEmulator: DeviceConstants.isEmulator,

  // Whether the device is a tablet
  isTablet: DeviceConstants.isTablet,

  // Supported application binary interfaces
  supportedAbis: DeviceConstants.supportedAbis,

  // Build ID of the system, e.g. 18G103
  systemBuildId: DeviceConstants.systemBuildId,

  // Name of the system, e.g. iOS
  systemName: DeviceConstants.systemName,

  // Version of Android/iOS, e.g. "11.0"
  systemVersion: DeviceConstants.systemVersion,
};
