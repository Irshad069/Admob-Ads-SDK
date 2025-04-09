package com.sdkadmobads

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sdkadmobads.databinding.ActivityMainBinding
import com.sdkads.AdSdkInitializer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AdSdkInitializer.handleConsent(this) { consentGiven ->
            Log.d("TAG", "Consent given: $consentGiven")
        }

    }

}