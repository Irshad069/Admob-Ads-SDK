plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.sdkadmobads"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sdkadmobads"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BANNER_ID",
                "\"ca-app-pub-3940256099942544/6300978111\""
            ) // Test Id in debug builds
            buildConfigField(
                "String",
                "INTERSTITIAL_AD_ID",
                "\"ca-app-pub-3940256099942544/1033173712\""
            ) // Test Id in debug builds
            buildConfigField(
                "String",
                "ADAPTIVE_BANNER_ID",
                "\"ca-app-pub-3940256099942544/9214589741\""
            ) // Test Id in debug builds
            buildConfigField(
                "String",
                "REWARD_INTERSTITIAL_AD_ID",
                "\"ca-app-pub-3940256099942544/5354046379\""
            ) // Test Id in debug builds
            buildConfigField(
                "String",
                "REWARDED_AD_ID",
                "\"ca-app-pub-3940256099942544/5224354917\""
            ) // Test Id in debug builds
            buildConfigField(
                "String",
                "NATIVE_AD_ID",
                "\"ca-app-pub-3940256099942544/2247696110\""
            ) // Test Id in debug builds
            buildConfigField(
                "String",
                "APP_OPEN_ID",
                "\"ca-app-pub-3940256099942544/9257395921\""
            ) // Test Id in debug builds
        }
        release {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "BANNER_ID",
                "\"your-production-banner-id\""
            ) //Add original Id
            buildConfigField(
                "String",
                "INTERSTITIAL_AD_ID",
                "\"your-production-interstitial-id\""
            ) //Add original Id
            buildConfigField(
                "String",
                "ADAPTIVE_BANNER_ID",
                "\"your-production-adaptive-banner-id\""
            ) //Add original Id
            buildConfigField(
                "String",
                "REWARD_INTERSTITIAL_AD_ID",
                "\"your-production-reward-interstitial-id\""
            ) //Add original Id
            buildConfigField(
                "String",
                "REWARDED_AD_ID",
                "\"your-production-reward-ad-id\""
            ) //Add original Id
            buildConfigField(
                "String",
                "NATIVE_AD_ID",
                "\"your-production-native-id\""
            ) //Add original Id
            buildConfigField(
                "String",
                "APP_OPEN_ID",
                "\"your-production-app-open-id\""
            ) //Add original Id
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        buildFeatures {
            viewBinding = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":sdkads"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation ("com.github.Irshad069:Admob-Ads-SDK:1.0.1")

}