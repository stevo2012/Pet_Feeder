# Pet Feeder App

## Introduction
This application is a concept applicaiton for a non-existant pet feeder device that would be automated to distribute a given amount of food to a pet based upon weight at any time the user schedules. Because this app is for a theoretical product, it doesn't actually function beyond its own internal workings.

As of now this application exists as an Android App but is only available if loaded from Android Studio which can be downloaded at https://developer.android.com/studio.

## General Information
The user of the app has the ability to create a schedule of feeding times that are stored in a SQLite database within the app. In theory, the app would be able to take this database, convert it to a .csv file, and send it to a physical device that would read the data in that file and distribute food to a pet accoordingly. The application also offers the user the ability to send a message to the device to distribute a specific amount of food based upon weight at any given time without the need for a schedule to be made.

This application is built mainly in Java with small amounts of SQLite commands. 

## Getting Started
In order to run this application as of now, the user will need to open it in Android Studio by downloading the programs main folder and opening it in the latest version of Android Studio (Chipmunk). From here the user can run the program on a local emulator or download it to their own android device.

## Problems, Bugs, and Future Improvements
Upon fairly thorough testing, no major bugs in the current version seem to exist. As of now the app links to a pet food website which is in no way affiliated with the application itself. No future builds of this app are expected to be created or released unless the project is actually produced, which is highly unlikely.
