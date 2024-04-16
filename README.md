# Fingerprint Scanner POC

[![Release](https://jitpack.io/v/legend295/FingerprintScannerSDK.svg)](https://jitpack.io/#legend295/FingerprintScannerSDK)

A GitHub repo demonstrating seamless integration of fingerprint scanning into Android apps.

### How to add to your project
Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { 
            url "https://jitpack.io"
            credentials { username authToken }
         }
    }
}
```
and:

```gradle
dependencies {
      implementation 'com.github.legend295.FingerprintScannerSDK:scanner:{latest version}'
}
```

Add the above line to your project's `build.gradle` file and `settings.gradle` file to include FingerprintScannerLib in your Android project.

**authToken will be provided by our support.** 