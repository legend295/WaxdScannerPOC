# Fingerprint Scanner POC

A GitHub repo demonstrating seamless integration of fingerprint scanning into Android apps.

### How to integrate into your project
1. Add the below lines to your project's `build.gradle` file of your Android project:

```bash
dependencies {
      implementation 'com.github.legend295.FingerprintScannerSDK:scanner:1.0.7'
}
```
Add this under `settings.gradle` file to include FingerprintScannerLib :

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
