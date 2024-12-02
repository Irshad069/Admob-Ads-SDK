Usage
App (sdkadmobads)
The app demonstrates how to integrate and use the SDK to show ads and handle consent.

1. Add SDK dependency to your app
    dependencies {
        implementation(project(":sdkads"))
    }
2. App Manifest File
    Code Details:
    <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713" />
    <!--        replace with your original App Id-->

3. Application Class
    Purpose: Initializes the AdMob SDK at the app level.
      - File: MyApplication.kt
        
      Code Details:
      class MyApplication : Application() {
        override fun onCreate() {
        super.onCreate()
        AdSdkInitializer.initialize(
            application = this
          )
        }
      }
   
  And also in Manifest file add this:
<application android:name=".MyApplication">

4. MainActivity
    Purpose: Demonstrates consent handling and SDK usage in an activity.
  - File: MainActivity.kt
    
    Code Details:
    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AdSdkInitializer.handleConsent(this) { consentGiven ->
            
        }
    }
}

5. Banner Ad Integration
    Purpose: Shows a banner ad using the SDK’s BannerView.
  - Layout File: activity_main.xml
  
    Code Details:
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom">

        <com.sdkads.fixedsizebanner.BannerView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

    </FrameLayout>

6. Interstitial Ad Integration
    Purpose: Displays an interstitial ad on a button click.
    - Key Code in MainActivity:
    
    Code Details:
    btnShowInterstitial.setOnClickListener {
        InterstitialHelper.showAd(requireActivity()){
           // on ad close what you want
        }
    }

 7. Adaptive Banner Ad Integration
    Purpose: Shows a adaptive banner ad using the SDK’s AdaptiveBannerView.
      - Layout File: activity_main.xml
    
    Code Details:
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom">

        <com.sdkads.adaptivebanner.AdaptiveBannerView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

    </FrameLayout>

8. Reward Interstitial Ad Integration
    Purpose: Displays a reward interstitial ad on a button click.
    - Key Code in MainActivity:
    
    Code Details:
    btnShowRewardInterstitial.setOnClickListener {
        RewardedInterstitialHelper.showAd(
            activity = requireActivity(),
            onAdClosed = {
                // when ad is closed
            },
            onUserEarnedReward = { rewardAmount, rewardType ->
                // when user have earned reward
            }
        )

    }

9. Reward Ad Integration
    Purpose: Displays a reward ad on a button click.
    - Key Code in MainActivity:
    
    Code Details:
    btnShowReward.setOnClickListener {
        RewardedAdHelper.showAd(
            activity = requireActivity(),
            onAdClosed = {
                // when ad is closed
            },
            onUserEarnedReward = { rewardAmount, rewardType ->
                //when user have earned reward
            }
        )

    }

10. Native Ad Integration
    Purpose: Displays a native ad .
    - Key Code in MainActivity:

     Code Details:
    <FrameLayout
        android:id="@+id/ad_frame"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom">

        <com.sdkads.nativeview.NativeAdView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:viewType="medium"/>
    </FrameLayout>

11. AppOpen Ad Integration
    Purpose: Displays an AppOpen Ad .
    - Key Code in MainActivity:
    
    Code Details:
    override fun onResume() {
        super.onResume()
        AdSdkInitializer.registerActivity(this)
    }
