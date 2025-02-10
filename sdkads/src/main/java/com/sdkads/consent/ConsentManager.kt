package com.sdkads.consent

import android.app.Activity
import android.util.Log
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.sdkads.BuildConfig

/**
 * Created by Irshad khan
 * Date: 12/11/2024$
 */

/**
 * Manages user consent for personalized ads using Google's User Messaging Platform (UMP).
 */
object ConsentManager {

    /**
     * Test device hashed ID used for debugging purposes to simulate consent scenarios.
     */
    const val TEST_DEVICE_HASHED_ID =
        "007DEB0576C579A6A35DA74A5E3C5186" // use your Test device hashed ID

    /**
     * Requests user consent for showing ads.
     *
     * @param activity The current activity context.
     * @param onConsentResult Callback invoked with the result:
     * - `true` if ads can be shown.
     * - `false` if ads should not be shown.
     */
    fun requestConsent(activity: Activity, onConsentResult: (Boolean) -> Unit) {
        // Retrieve the consent information instance
        val consentInformation = UserMessagingPlatform.getConsentInformation(activity)

        // If consent is not required and it's not a debug build, return the consent result
        if (!BuildConfig.DEBUG) {
            if (consentInformation.isNotRequired) {
                onConsentResult.invoke(consentInformation.canRequestAds())
                return
            }
        }

        // Set up debug settings for testing purposes
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .apply {
                if (BuildConfig.DEBUG) {
                    setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                    addTestDeviceHashedId(TEST_DEVICE_HASHED_ID)
                }
            }
            .build()

        // Set up consent request parameters
        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .build()

        // Success listener for consent info update
        val successListener = {
            if (consentInformation.isConsentFormAvailable && consentInformation.consentStatus == 2) {
                Log.d("TAG", "requestConsent: ${consentInformation.consentStatus}")
                Log.d("TAG", "status: ${consentInformation.consentStatus == 2}")

                // Load and show the consent form if required
                loadAndShowConsentForm(activity, consentInformation, onConsentResult)
            } else {
                onConsentResult(true) // Consent is not required, ads can be shown
            }
        }

        // Failed listener for consent info update
        val failedListener: (FormError) -> Unit = {
            onConsentResult(false) // Ads cannot be shown due to failure
        }
        // Request consent information update
        consentInformation.requestConsentInfoUpdate(
            activity, params, successListener, failedListener
        )
    }

    /**
     * Loads and displays the consent form if required.
     *
     * @param activity The current activity context.
     * @param consentInformation The consent information object.
     * @param onConsentResult Callback invoked with the result:
     * - `true` if ads can be shown.
     * - `false` otherwise.
     */
    private fun loadAndShowConsentForm(
        activity: Activity,
        consentInformation: ConsentInformation,
        onConsentResult: (Boolean) -> Unit
    ) {
        // Load the consent form
        UserMessagingPlatform.loadConsentForm(
            activity,
            { consentForm ->
                // Show the consent form and process the user's response
                consentForm.show(activity) {
                    Log.d("TAG", "loadAndShowConsentForm: ${consentInformation.canRequestAds()}")
                    onConsentResult(consentInformation.canRequestAds())
                }
            }, {
                onConsentResult(false) // Ads cannot be shown due to failure
            }
        )
    }

    /**
     * Extension property to check if consent is required.
     */
    private val ConsentInformation.isRequired: Boolean
        get() = consentStatus == ConsentInformation.ConsentStatus.REQUIRED

    /**
     * Extension property to check if consent is not required.
     */
    private val ConsentInformation.isNotRequired: Boolean
        get() = isRequired.not()
}