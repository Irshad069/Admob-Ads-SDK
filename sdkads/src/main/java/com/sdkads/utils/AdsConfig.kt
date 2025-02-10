package com.sdkads.utils

import com.sdkads.BuildConfig

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * Configuration object for managing ad settings across the application.
 * Dynamically assigns AdMob ad unit IDs based on the build variant.
 */
object AdsConfig {
    /**
     * The AdMob ad unit ID for fixed-size banner ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val BANNER_ID: String = BuildConfig.BANNER_ID

    /**
     * The AdMob ad unit ID for interstitial ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val INTERSTITIAL_AD_ID: String = BuildConfig.INTERSTITIAL_AD_ID

    /**
     * The AdMob ad unit ID for adaptive banner ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val ADAPTIVE_BANNER_ID: String = BuildConfig.ADAPTIVE_BANNER_ID

    /**
     * The AdMob ad unit ID for reward interstitial ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val REWARD_INTERSTITIAL_AD_ID: String = BuildConfig.REWARD_INTERSTITIAL_AD_ID

    /**
     * The AdMob ad unit ID for reward ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val REWARDED_AD_ID: String = BuildConfig.REWARDED_AD_ID

    /**
     * The AdMob ad unit ID for native ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val NATIVE_AD_ID: String = BuildConfig.NATIVE_AD_ID

    /**
     * The AdMob ad unit ID for native ads.
     * Defaults to test ad unit ID in debug builds.
     */
    const val APP_OPEN_ID: String = BuildConfig.APP_OPEN_ID

    /**
     * default value for adType
     */
    const val LARGE: String = "large"
}
