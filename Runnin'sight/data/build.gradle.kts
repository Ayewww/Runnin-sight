plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.runninsight.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

//        buildConfigField("String", "BACKEND_BASE_URL", "\"http://10.0.2.2:8000/api/\"")
//        buildConfigField("String", "NAVER_NEWS_BASE_URL", "\"https://openapi.naver.com/\"")
//        buildConfigField("String", "NAVER_MAP_BASE_URL", "\"https://naveropenapi.apigw.ntruss.com/\"")
//
//        buildConfigField("String", "NAVER_MAP_CLIENT_ID", "\"${prop("NAVER_MAP_CLIENT_ID")}\"")
//        buildConfigField("String", "NAVER_MAP_CLIENT_SECRET", "\"${prop("NAVER_MAP_CLIENT_SECRET")}\"")
//        buildConfigField("String", "NAVER_NEWS_CLIENT_ID", "\"${prop("NAVER_CLIENT_ID")}\"")
//        buildConfigField("String", "NAVER_NEWS_CLIENT_SECRET", "\"${prop("NAVER_CLIENT_SECRET")}\"")
    }

    buildFeatures {
        compose = false
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    jvmToolchain(21)
}


dependencies {
    implementation(project(":domain"))
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("com.google.mlkit:text-recognition:16.0.0")
}