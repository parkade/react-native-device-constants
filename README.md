# react-native-device-constants

## Project overview
This project is based on the [react-native-device-info](https://github.com/react-native-community/react-native-device-info/) 
project and all the difficult bits were written by them.

All this project adds is that when you import react-native-device-constants, you have access to all of your config 
values as soon as the package is imported.

## Getting started

`$ npm install react-native-device-constants --save`

### Mostly automatic installation

`$ react-native link react-native-device-constants`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-device-constants` and add `DeviceConstants.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libDeviceConstants.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.DeviceConstantsPackage;` to the imports at the top of the file
  - Add `new DeviceConstantsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-device-constants'
  	project(':react-native-device-constants').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-device-constants/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-device-constants')
  	```


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
yarn add /Users/plowman/projects/react-native-device-constants
rm -rf /park/mobile/node_modules/react-native-device-constants/node_modules
yarn run:ios:beta

yarn add /Users/plowman/projects/react-native-device-constants && yarn run:ios:beta:device

```
