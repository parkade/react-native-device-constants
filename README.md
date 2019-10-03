# react-native-device-constants

## Project overview
This project is based on the [react-native-device-info](https://github.com/react-native-community/react-native-device-info/) 
project and all the difficult bits were written by them.

All this project adds is that when you import react-native-device-constants, you have access to all of your config 
values as soon as the package is imported.

## Getting started
Right now this is only available on github and not via an npm package. If there's any interest, I'll publish it to npm.

`$ npm install https://github.com/parkwithpark/react-native-device-constants --save`

OR

`$ yarn add https://github.com/parkwithpark/react-native-device-constants`


## Usage
```javascript
import DeviceConstants from 'react-native-device-constants';

// Examples from Android Emulator
DeviceConstants.appBuildNumber; // "123"
DeviceConstants.appBundleId; // "com.parkwithpark.android.beta"
DeviceConstants.appName; // "Park Beta"
DeviceConstants.appVersion; // "1.0.9"
DeviceConstants.deviceBrand; // "google"
DeviceConstants.deviceId; // "unknown"
DeviceConstants.deviceModel; // "Android SDK built for x86"
DeviceConstants.deviceName; // "Android SDK built for x86"
DeviceConstants.deviceType; // "Handset"
DeviceConstants.fontScale; // 1
DeviceConstants.hasNotch; // false
DeviceConstants.isEmulator; // true
DeviceConstants.isTablet; // false
DeviceConstants.supportedAbis; // ["x86"]
DeviceConstants.systemBuildId; // "OSR1.180418.019"
DeviceConstants.systemName; // "Android SDK built for x86"
DeviceConstants.systemVersion; // "8.0.0"

// Example from Android Device
DeviceConstants.appBuildNumber; // "123"
DeviceConstants.appBundleId; // "com.parkwithpark.android.beta"
DeviceConstants.appName; // "Park Beta"
DeviceConstants.appVersion; // "1.0.9"
DeviceConstants.deviceBrand; // "google"
DeviceConstants.deviceId; // "marlin"
DeviceConstants.deviceModel; // "Pixel XL"
DeviceConstants.deviceName; // "Pixel XL"
DeviceConstants.deviceType; // "Handset"
DeviceConstants.fontScale; // 1
DeviceConstants.hasNotch; // false
DeviceConstants.isEmulator; // false
DeviceConstants.isTablet; // false
DeviceConstants.supportedAbis; // (3) ["arm64-v8a", "armeabi-v7a", "armeabi"]
DeviceConstants.systemBuildId; // "QP1A.190711.019"
DeviceConstants.systemName; // "Pixel XL"
DeviceConstants.systemVersion; // "10"

// Example from iOS Simulator of iPhone 11 running iOS 13
DeviceConstants.appBuildNumber; // "123"
DeviceConstants.appBundleId; // "com.parkwithpark.ios.beta"
DeviceConstants.appName; // "Park Staging"
DeviceConstants.appVersion; // "1.0.9"
DeviceConstants.deviceBrand; // "Apple"
DeviceConstants.deviceId; // "iPhone12,1"
DeviceConstants.deviceModel; // "iPhone 11"
DeviceConstants.deviceName; // "iPhone 11"
DeviceConstants.deviceType; // "Handset"
DeviceConstants.fontScale; // 1
DeviceConstants.hasNotch; // true
DeviceConstants.isEmulator; // true
DeviceConstants.isTablet; // false
DeviceConstants.supportedAbis; // ["Intel x86-64h Haswell"]
DeviceConstants.systemBuildId; // "18G103"
DeviceConstants.systemName; // "iOS"
DeviceConstants.systemVersion; // "13.0"

// Example from an iPhone XS running iOS 13.1
DeviceConstants.appName; // 'Park Staging'
DeviceConstants.appVersion; // '1.0.9'
DeviceConstants.appBuildNumber; // '123'
DeviceConstants.appBundleId; // 'com.parkwithpark.ios.beta'
DeviceConstants.deviceBrand; // 'Apple'
DeviceConstants.deviceId; // 'iPhone11,2'
DeviceConstants.deviceName; // "Joseph's iPhone"
DeviceConstants.deviceType; // 'Handset'
DeviceConstants.deviceModel; // 'iPhone XS'
DeviceConstants.fontScale; // 1
DeviceConstants.hasNotch; // true
DeviceConstants.isEmulator; // false
DeviceConstants.isTablet; // false
DeviceConstants.supportedAbis; // [ 'ARM64E' ]
DeviceConstants.systemBuildId; // '17A844'
DeviceConstants.systemName; // 'iOS'
DeviceConstants.systemVersion; // '13.1'
```


## Developing locally from source
```bash
export working_directory=/my/working/directory/react-native-device-constants
yarn add $working_directory
react-native run-ios
```
