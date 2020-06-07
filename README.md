# MusicLooper
Android music looper project

         
Prerequisites:
	      
	A device running Android 4.0 (Ice Cream Sandwich) or newer, and Google Play services 15.0.0 or higher
	The latest version of Android Studio

Add Firebase to your app:

	Use the Firebase Assistant -
	To open the Firebase Assistant in Android Studio:
	Click Tools > Firebase to open the Assistant window.
	Click to expand one of the listed features (for example, Analytics), then click the provided tutorial link (for example, 			Log an Analytics event).
	Click the Connect to Firebase button to connect to Firebase and add the necessary code to your app.


Add Firebase to Your Android Project -
Prerequisites :

	If you don't have an Android Studio project already, you can download one of our quickstart samples if you just want 
	to try 	a Firebase feature. If you're using a quickstart, remember to get the application ID from the build.gradle 
	file in your project's module folder (typically app/), as you'll need this package name for the next step.
	Note: If you are upgrading from a 2.X version of the Firebase SDK, see our upgrade guide for Android to get started.
	
Add Firebase to your app :

	If you're using Android Studio version 2.2 or later, the Firebase Assistant is the simplest way to connect 
	your app to Firebase. The Assistant can connect your existing project or create a new one for you with all 
	the necessary gradle dependencies.
	If you're using an older version of Android Studio or have a more complex project configuration, 
	you can still manually add Firebase to your app.
	
Use the Firebase Assistant :

	To open the Firebase Assistant in Android Studio:
	Click Tools > Firebase to open the Assistant window.
	Click to expand one of the listed features (for example, Analytics), then click the provided tutorial link (for example, Log an Analytics event).
	Click the Connect to Firebase button to connect to Firebase and add the necessary code to your app.

Manually add Firebase :

	To add Firebase to your app you'll need a Firebase project and a Firebase configuration file for your app.
	1.	Create a Firebase project in the Firebase console, if you don't already have one. If you already have an existing 
			Google project associated with your mobile app, click Import Google Project. Otherwise, click Add project.
	2.	Click Add Firebase to your Android app and follow the setup steps. If you're importing an existing
			Google project, this may happen automatically and you can just download the config file.
	3.	When prompted, enter your app's package name. It's important to enter the package name your app is using;
			this can only be set when you add an app to your Firebase project.
	4.	At the end, you'll download a google-services.json file. You can download this file again at any time.
	5.	If you haven't done so already, copy this into your project's module folder, typically app/.

Add the SDK : 

	If you would like to integrate the Firebase libraries into one of your own projects, you need to perform a few basic 
	tasks to prepare your Android Studio project. You may have already done this as part of adding Firebase to your app.
	First, add rules to your root-level build.gradle file, to include the google-services plugin and the Google's Maven 
	repository:
	
	buildscript {
			// ...
			dependencies {
					// ...
					classpath 'com.google.gms:google-services:3.2.1' // google-services plugin
					}
				}

			allprojects {
					// ...
					repositories {
							// ...
							maven {
								url "https://maven.google.com" // Google's Maven repository
							}
					}
			}
	Then, in your module Gradle file (usually the app/build.gradle), add the apply plugin line at the bottom of the 
	file to enable the Gradle plugin:
	apply plugin: 'com.android.application'


		dependencies {
				// ...
				compile 'com.google.firebase:firebase-core:15.0.0'

				// Getting a "Could not find" error? Make sure you have
				// added the Google maven respository to your root build.gradle
			}


Available libraries:

	The following libraries are available for the various Firebase features.
	
	Gradle Dependency Line	Service
	com.google.firebase:firebase-core:15.0.0	Analytics
	com.google.firebase:firebase-database:15.0.0	Realtime Database
	com.google.firebase:firebase-firestore:15.0.0	Cloud Firestore
	com.google.firebase:firebase-storage:15.0.0	Storage
	com.google.firebase:firebase-crash:15.0.0	Crash Reporting
	com.google.firebase:firebase-auth:15.0.0	Authentication
	com.google.firebase:firebase-messaging:15.0.0	Cloud Messaging
	com.google.firebase:firebase-config:15.0.0	Remote Config
	com.google.firebase:firebase-invites:15.0.0	Invites and Dynamic Links
	com.google.firebase:firebase-ads:15.0.0	AdMob
	com.google.firebase:firebase-appindexing:15.0.0	App Indexing
	com.google.firebase:firebase-perf:15.0.0	Performance Monitoring
	com.google.firebase:firebase-functions:15.0.0	Cloud Functions for Firebase Client SDK

	We are using this one in our application - com.google.firebase:firebase-auth:15.0.0
