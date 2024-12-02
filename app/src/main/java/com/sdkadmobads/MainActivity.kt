package com.sdkadmobads

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sdkadmobads.databinding.ActivityMainBinding
import com.sdkads.AdSdkInitializer
import com.sdkads.utils.AdsConfig

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AdSdkInitializer.handleConsent(this) { consentGiven ->
            Log.d("TAG", "Consent given: $consentGiven")
            Log.d("TAG", "Ads enabled: ${AdsConfig.areAdsEnabled}")
        }

    }

    override fun onResume() {
        super.onResume()
        // Register activity for App Open Ads
        AdSdkInitializer.registerActivity(this)
    }
}