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
      implementation 'com.github.legend295.FingerprintScannerSDK:scanner:x.y.z'
}
```

Add the above line to your project's `build.gradle` file and `settings.gradle` file to include FingerprintScannerLib in your Android project.

**authToken will be provided by our support.** 