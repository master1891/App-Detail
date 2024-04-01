// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    //alias(libs.plugins.hilt.plugin)  apply false
    alias(libs.plugins.hiltPlugin)  apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}