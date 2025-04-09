package com.sdkads.core

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
    var BANNER_ID: String = ""

    /**
     * The AdMob ad unit ID for interstitial ads.
     * Defaults to test ad unit ID in debug builds.
     */
    var INTERSTITIAL_AD_ID: String = ""

    /**
     * The AdMob ad unit ID for adaptive banner ads.
     * Defaults to test ad unit ID in debug builds.
     */
    var ADAPTIVE_BANNER_ID: String = ""

    /**
     * The AdMob ad unit ID for reward interstitial ads.
     * Defaults to test ad unit ID in debug builds.
     */
    var REWARD_INTERSTITIAL_AD_ID: String = ""

    /**
     * The AdMob ad unit ID for reward ads.
     * Defaults to test ad unit ID in debug builds.
     */
    var REWARDED_AD_ID: String = ""

    /**
     * The AdMob ad unit ID for native ads.
     * Defaults to test ad unit ID in debug builds.
     */
    var NATIVE_AD_ID: String = ""

    /**
     * The AdMob ad unit ID for app open.
     * Defaults to test ad unit ID in debug builds.
     */
    var APP_OPEN_ID: String = ""

    /**
     * The AdMob ad unit ID for collapsible banner.
     * Defaults to test ad unit ID in debug builds.
     */
    var COLLAPSIBLE_BANNER_ID: String = ""

    /**
     * Is Debug check for showing consent
     */
    var IS_DEBUG:Boolean = false

    /**
     * default value for adType large
     */
    const val LARGE: String = "large"

    /**
     * default value for adType medium
     */
    const val MEDIUM: String = "medium"
}
