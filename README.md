# Fingerprint Scanner POC

[//]: # ([![Release]&#40;https://jitpack.io/v/legend295/FingerprintScannerSDK.svg&#41;]&#40;https://jitpack.io/#legend295/FingerprintScannerSDK&#41;)

A GitHub repo demonstrating seamless integration of fingerprint scanning into Android apps.

### How to integrate into your project
1. Add the below lines to your project's `build.gradle` file and `settings.gradle` file to include FingerprintScannerLib in your Android project:

```bash
dependencies {
      implementation 'com.github.legend295.FingerprintScannerSDK:scanner:main-SNAPSHOT'
}
```
and:

```bash
maven {
      url 'https://jitpack.io'
      credentials { username authToken }
}

```

**The `authToken` will be provided by our support.**

2. StartActivityForResult on any View's click in your Activity/Fragment:

```
scanningLauncher.launch(Intent(this, ScannerActivity()::class.java))
```

and register launcher in same Activity/Fragment for result like below:

```
 private val scanningLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val list: ArrayList<File>? = it.data?.serializable(ScannerConstants.DATA)
            }
        }
```

list variable has the files data.

3. That's it. Run the project and see the results.