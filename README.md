# SDKAdMobAds Integration Guide

This app demonstrates how to integrate and use the SDK to show ads and handle user consent.

---
## Option 1
## Add Library
implementation 'com.github.Irshad069:Admob-Ads-SDK:1.0.2'
## Add the JitPack repository to your setting.gradle
```groovy
dependencyResolutionManagement {

    --
    
    repositories {
    
        --
        
        --
        
        maven { url = uri ( "https://jitpack.io" ) }
        
    }
    
}
```
## Option 2
Add SDK Dependency to Your App
Add the following SDK dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation(project(":sdkads"))
}
And also in setting.gradle file Add this
include(":sdkads")
```
## common for both options
## 1. Ad ID Configuration Using Build Types

We configure Ad IDs for different build types in the build.gradle file

First add this to your gradle file:

```groovy
buildFeatures {
    buildConfig = true
}
```

```groovy
buildTypes {
    debug {
        buildConfigField("String", "BANNER_ID", "ca-app-pub-3940256099942544/6300978111") // Test Banner Ad
        buildConfigField("String", "INTERSTITIAL_AD_ID", "ca-app-pub-3940256099942544/1033173712") // Test Interstitial Ad
        buildConfigField("String", "ADAPTIVE_BANNER_ID", "ca-app-pub-3940256099942544/9214589741") // Test Adaptive Banner Ad
        buildConfigField("String", "REWARD_INTERSTITIAL_AD_ID", "\"ca-app-pub-3940256099942544/5354046379\"") // Test Reward Interstitial Ad
        buildConfigField("String", "REWARDED_AD_ID", "\"ca-app-pub-3940256099942544/5224354917\"") // Test Reward Ad
        buildConfigField("String", "NATIVE_AD_ID", "\"ca-app-pub-3940256099942544/2247696110\"") // Test Native Ad
        buildConfigField("String", "APP_OPEN_ID", "\"ca-app-pub-3940256099942544/9257395921\"") // Test AppOpen Ad
    }
    release {
        isMinifyEnabled = false
        buildConfigField("String", "BANNER_ID", "\"your-production-banner-id\"") // Production Banner Ad
        buildConfigField("String", "INTERSTITIAL_AD_ID", "\"your-production-interstitial-id\"") // Production Interstitial Ad
        buildConfigField("String", "ADAPTIVE_BANNER_ID", "\"your-production-adaptive-banner-id\"") // Production Adaptive Banner Ad
        buildConfigField("String", "REWARD_INTERSTITIAL_AD_ID", "\"your-production-reward-interstitial-id\"") //Production Reward Interstitial Ad
        buildConfigField("String", "REWARDED_AD_ID", "\"your-production-reward-ad-id\"") //Production Reward Ad
        buildConfigField("String", "NATIVE_AD_ID", "\"your-production-native-id\"") //Production Native Ad
        buildConfigField("String", "APP_OPEN_ID", "\"your-production-app-open-id\"") //Production AppOpen Ad
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
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
        AdSdkInitializer.initialize(
            application = this,
            isDebug = BuildConfig.DEBUG,
            testDeviceIds = listOf("007DEB0576C579A6A35DA74A5E3C5186"),//your device id
            bannerId = BuildConfig.BANNER_ID,
            appOpenAd = BuildConfig.APP_OPEN_ID,
            interstitialAd = BuildConfig.INTERSTITIAL_AD_ID,
            adaptiveBannerAd = BuildConfig.ADAPTIVE_BANNER_ID,
            rewardInterstitialAd = BuildConfig.REWARD_INTERSTITIAL_AD_ID,
            rewardAd = BuildConfig.REWARDED_AD_ID,
            nativeAd = BuildConfig.NATIVE_AD_ID
        )
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
