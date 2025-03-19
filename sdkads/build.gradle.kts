plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
//    id("com.vanniktech.maven.publish") version "0.28.0"
//    id("com.gradleup.nmcp") version "0.0.7"
}

android {
    namespace = "com.sdkads"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //google ad service
    implementation(libs.play.services.ads)
    //ump for consent
    implementation(libs.user.messaging.platform)
    // for lifecycle process
    implementation(libs.androidx.lifecycle.process)
}
publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
//publishing {
//    publications {
//        register<MavenPublication>("release") {
//            groupId = "com.irshad069"
//            artifactId = "sdk_ads"
//            version = "1.0"
//
//            afterEvaluate {
//                from(components["release"])
//            }
//        }
//    }
//    repositories {
//        maven {
//            name = "sonatype"
//            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//
//            credentials {
//                username = System.getenv("SONATYPE_USERNAME") ?: "your-zVBmE8CQ"
//                password = System.getenv("SONATYPE_PASSWORD") ?: "your-2B6vgJiv1jw27SUqy6ZZeYsqWnFd3mEuHHAN0CVkz03t"
//            }
//        }
//    }
//}

