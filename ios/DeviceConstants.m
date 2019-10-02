/*
* This is taken mostly from the react-native-device-info package:
*   https://github.com/react-native-community/react-native-device-info
*/
#include <ifaddrs.h>
#include <arpa/inet.h>
#import <mach/mach.h>
#import <mach-o/arch.h>
#import <CoreLocation/CoreLocation.h>
#import <React/RCTUtils.h>
#import "DeviceConstants.h"

@import CoreTelephony;
@import Darwin.sys.sysctl;

typedef NS_ENUM(NSInteger, DeviceType) {
    DeviceTypeHandset,
    DeviceTypeTablet,
    DeviceTypeTv,
    DeviceTypeUnknown
};

#define DeviceTypeValues [NSArray arrayWithObjects: @"Handset", @"Tablet", @"Tv", @"Unknown", nil]

@implementation DeviceConstants

RCT_EXPORT_MODULE()

+ (BOOL) requiresMainQueueSetup
{
    return YES;
}

- (DeviceType) getDeviceType
{
    switch ([[UIDevice currentDevice] userInterfaceIdiom]) {
        case UIUserInterfaceIdiomPhone: return DeviceTypeHandset;
        case UIUserInterfaceIdiomPad: return DeviceTypeTablet;
        case UIUserInterfaceIdiomTV: return DeviceTypeTv;
        default: return DeviceTypeUnknown;
    }
}

- (NSString *) getDeviceTypeName {
    return [DeviceTypeValues objectAtIndex: [self getDeviceType]];
}

- (NSString *) getSystemName {
    UIDevice *currentDevice = [UIDevice currentDevice];
    return currentDevice.systemName;
}

- (NSString *) getSystemVersion {
    UIDevice *currentDevice = [UIDevice currentDevice];
    return currentDevice.systemVersion;
}

- (NSString *) getDeviceName {
    UIDevice *currentDevice = [UIDevice currentDevice];
    return currentDevice.name;
}

- (NSString *) getAppName {
    return [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleDisplayName"];
}

- (NSString *) getBundleId {
    return [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleIdentifier"];
}

- (NSString *) getAppVersion {
    return [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleShortVersionString"];
}

- (NSString *) getAppBuildNumber {
    return [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleVersion"];
}

- (NSString *) getSystemBuildId {
    size_t bufferSize = 64;
    NSMutableData *buffer = [[NSMutableData alloc] initWithLength:bufferSize];
    int status = sysctlbyname("kern.osversion", buffer.mutableBytes, &bufferSize, NULL, 0);
    if (status != 0) {
        return @"unknown";
    }
    NSString* buildId = [[NSString alloc] initWithCString:buffer.mutableBytes encoding:NSUTF8StringEncoding];
    return buildId;
}

- (NSString *) getDeviceId {
    struct utsname systemInfo;
    uname(&systemInfo);
    NSString* deviceId = [NSString stringWithCString:systemInfo.machine
                                            encoding:NSUTF8StringEncoding];
    if ([deviceId isEqualToString:@"i386"] || [deviceId isEqualToString:@"x86_64"] ) {
        deviceId = [NSString stringWithFormat:@"%s", getenv("SIMULATOR_MODEL_IDENTIFIER")];
    }
    return deviceId;
}

- (BOOL) isEmulator {
    struct utsname systemInfo;
    uname(&systemInfo);
    NSString* deviceId = [NSString stringWithCString:systemInfo.machine
                                            encoding:NSUTF8StringEncoding];
    
    if ([deviceId isEqualToString:@"i386"] || [deviceId isEqualToString:@"x86_64"] ) {
        return YES;
    } else {
        return NO;
    }
}

- (BOOL) isTablet {
    if ([self getDeviceType] == DeviceTypeTablet) {
        return YES;
    } else {
        return NO;
    }
}

- (float) getFontScale {
    // Font scales based on font sizes from https://developer.apple.com/ios/human-interface-guidelines/visual-design/typography/
    float fontScale = 1.0;
    UIApplication *application = RCTSharedApplication();
    
    // Shared application is unavailable in an app extension.
    if (application) {
        NSString *contentSize = application.preferredContentSizeCategory;
        
        if ([contentSize isEqual: @"UICTContentSizeCategoryXS"]) fontScale = 0.82;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryS"]) fontScale = 0.88;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryM"]) fontScale = 0.95;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryL"]) fontScale = 1.0;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryXL"]) fontScale = 1.12;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryXXL"]) fontScale = 1.23;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryXXXL"]) fontScale = 1.35;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryAccessibilityM"]) fontScale = 1.64;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryAccessibilityL"]) fontScale = 1.95;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryAccessibilityXL"]) fontScale = 2.35;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryAccessibilityXXL"]) fontScale = 2.76;
        else if ([contentSize isEqual: @"UICTContentSizeCategoryAccessibilityXXXL"]) fontScale = 3.12;
    }
    
    return fontScale;
}

- (NSArray *) getSupportedAbis {
    /* https://stackoverflow.com/questions/19859388/how-can-i-get-the-ios-device-cpu-architecture-in-runtime */
    const NXArchInfo *info = NXGetLocalArchInfo();
    NSString *typeOfCpu = [NSString stringWithUTF8String:info->description];
    return @[typeOfCpu];
}

- (NSDictionary *)constantsToExport
{
    return @{
        @"appName": [self getAppName],
        @"appVersion": [self getAppVersion],
        @"appBuildNumber": [self getAppBuildNumber],
        @"appBundleId": [self getBundleId],
        @"deviceId": [self getDeviceId],
        @"deviceName": [self getDeviceName],
        @"deviceType": [self getDeviceTypeName],
        @"fontScale": @([self getFontScale]),
        @"isEmulator": @([self isEmulator]),
        @"isTablet": @([self isTablet]),
        @"supportedAbis": [self getSupportedAbis],
        @"systemBuildId": [self getSystemBuildId],
        @"systemName": [self getSystemName],
        @"systemVersion": [self getSystemVersion],
    };
}

@end
