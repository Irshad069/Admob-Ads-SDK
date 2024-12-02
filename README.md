# SDKAdMobAds Integration Guide

This app demonstrates how to integrate and use the SDK to show ads and handle user consent.

---

## 1. Add SDK Dependency to Your App

Add the following SDK dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation(project(":sdkads"))
}
```

## 2. Update App Manifest
Add the following metadata to your AndroidManifest.xml file under the <application> tag:

```kotlin
<application
    android:name=".MyApplication"
    ...>
    <!-- Replace with your original App ID -->
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="ca-app-pub-3940256099942544~3347511713" />
</application>
```

## 3. Application Class
Create an Application class to initialize the AdMob SDK.

File: MyApplication.kt
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AdSdkInitializer.initialize(application = this)
    }
}

```
Ensure the Application class is declared in the AndroidManifest.xml:
```kotlin
<application
    android:name=".MyApplication"
    ...>
</application>
```

## 4. MainActivity Setup
In MainActivity, handle user consent and demonstrate SDK usage.

File: MainActivity.kt
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle user consent
        AdSdkInitializer.handleConsent(this) { consentGiven ->
            // Handle the consent result
        }
    }

    override fun onResume() {
        super.onResume()
        AdSdkInitializer.registerActivity(this)
    }
}
```

## 5. Banner Ad Integration
Add a banner ad to your layout.

Layout File: activity_main.xml
```kotlin
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom">

    <com.sdkads.fixedsizebanner.BannerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</FrameLayout>
```

## 6. Interstitial Ad Integration
Display an interstitial ad on a button click.

Key Code in MainActivity:
```kotlin
btnShowInterstitial.setOnClickListener {
    InterstitialHelper.showAd(this) {
        // Action to perform when ad is closed
    }
}
```

## 7. Adaptive Banner Ad Integration
Add an adaptive banner ad to your layout.

Layout File: activity_main.xml
```kotlin
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom">

    <com.sdkads.adaptivebanner.AdaptiveBannerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</FrameLayout>
```

## 8. Reward Interstitial Ad Integration
Display a reward interstitial ad on a button click.

Key Code in MainActivity:
```kotlin
btnShowRewardInterstitial.setOnClickListener {
    RewardedInterstitialHelper.showAd(
        activity = this,
        onAdClosed = {
            // Action to perform when ad is closed
        },
        onUserEarnedReward = { rewardAmount, rewardType ->
            // Handle the reward
        }
    )
}
```

## 9. Reward Ad Integration
Display a reward ad on a button click.

Key Code in MainActivity:
```kotlin
btnShowReward.setOnClickListener {
    RewardedAdHelper.showAd(
        activity = this,
        onAdClosed = {
            // Action to perform when ad is closed
        },
        onUserEarnedReward = { rewardAmount, rewardType ->
            // Handle the reward
        }
    )
}
```

## 10. Native Ad Integration
Add a native ad to your layout.

Layout File: activity_main.xml
```kotlin
<FrameLayout
    android:id="@+id/ad_frame"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom">

    <com.sdkads.nativeview.NativeAdView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:viewType="medium" />
</FrameLayout>
```

## 11. App Open Ad Integration
Display an AppOpen ad when the activity resumes.

Key Code in MainActivity:
```kotlin
override fun onResume() {
    super.onResume()
    AdSdkInitializer.registerActivity(this)
}
```