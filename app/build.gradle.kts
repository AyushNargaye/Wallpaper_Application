import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.wallpaperapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wallpaperapplication"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }

        // Pass the API key as a BuildConfig field
        buildConfigField("String", "MY_API_KEY", "\"${properties.getProperty("MY_API_KEY")}\"")

        buildFeatures {
            buildConfig = true
        }


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation ("com.android.volley:volley:1.2.1")
   // implementation("com.github.Shashank02051997:FancyToast-Android:0.1.8")
   // implementation ("com.shashank.sony:fancytoastlib:0.1.6")
    implementation ("io.github.shashank02051997:FancyToast:2.0.2")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}