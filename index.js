import {Platform, NativeModules} from 'react-native';

import {DEVICE_NAMES_BY_CODE, DEVICES_WITH_NOTCH} from "./constants";

const {DeviceConstants} = NativeModules;

const OS = Platform.OS;

let notch;

function hasNotch() {
  if (!notch) {
    const _brand = getBrand().toLowerCase();
    const _model = getModel().toLowerCase();
    notch =
      DEVICES_WITH_NOTCH.findIndex(
        item =>
          item.brand.toLowerCase() === _brand &&
          item.model.toLowerCase() === _model
      ) !== -1;
  }
  return notch;
}

let model;

export function getModel() {
  if (!model) {
    if (OS === 'ios') {
      let deviceName;
      const _deviceId = DeviceConstants.deviceId;
      if (_deviceId) {
        deviceName = DEVICE_NAMES_BY_CODE[_deviceId];
        if (!deviceName) {
          // Not found on database. At least guess main device type from string contents:
          if (_deviceId.startsWith('iPod')) {
            deviceName = 'iPod Touch';
          } else if (_deviceId.startsWith('iPad')) {
            deviceName = 'iPad';
          } else if (_deviceId.startsWith('iPhone')) {
            deviceName = 'iPhone';
          } else if (_deviceId.startsWith('AppleTV')) {
            deviceName = 'Apple TV';
          }
        }
      }
      // Use unknown, otherwise getModel will never be cached.
      model = deviceName || 'unknown';
    } else if (OS === 'android' || OS === 'windows') {
      // TODO: Add Android logic for fetching model
      model = DeviceConstants.model;
    } else {
      model = 'unknown';
    }
  }
  return model;
}

let brand;

function getBrand() {
  if (!brand) {
    if (OS === 'android' || OS === 'windows') {
      // TODO: add logic for fetching Android brand
      brand = DeviceConstants.brand;
    } else if (OS === 'ios') {
      brand = 'Apple';
    } else {
      brand = 'unknown';
    }
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
  deviceBrand: getBrand(),

  // non-marketing device ID, e.g. "iPhone12,1"
  deviceId: DeviceConstants.deviceId,

  // marking device ID, e.g. "iPhone 11"
  deviceName: DeviceConstants.deviceName,

  // One of: "Handset", "Tablet", "Tv", "Unknown"
  deviceType: DeviceConstants.deviceType,

  // Generic model of this device, e.g. "iPod", "iPhone", "iPad", "Apple TV", or Android thing
  deviceModel: getModel(),

  // How much to scale up or scale down your font pt values, e.g. 0.95 means you can scale your 16pt down to ~15pt
  fontScale: DeviceConstants.fontScale,

  // Whether the phone has a "notch" at the top like an iPhone X.
  hasNotch: hasNotch(),

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

  // Version of the system, e.g. "11.0"
  systemVersion: DeviceConstants.systemVersion,
};
