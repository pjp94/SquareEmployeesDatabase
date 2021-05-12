SQUARE EMPLOYEE DIRECTORY APP
by Pramath Pancholi

For this app, I chose to mostly focus on the architecture to keep code decoupled and make data easy to handle across configuration changes. I followed MVVM best practices, using view models to store the data so that it could persist through a configuration change like a screen rotation. I loaded the data retrieved from the network into LiveData so that the activity could observe on it and make changes to the UI accordingly. While writing my code, I chose to write it in such a way that it was easily testable. I made sure to keep dependencies loose, passing in objects to different classes rather than implementing them within the classes so that mock objects could be passed in during testing. I wanted to make sure each class I tested could be tested in isolation, which mock objects also help achieve.

For testing, I chose to test the network layer, data layer, and presentation layer. The tests I wrote achieved the following:

1. Verify the network calls returned expected results using MockWebServer
2. Verify the JSON retrieved from the network would properly deserialize into objects
3. Verify the ViewModel backing the activity handled laoding the data into LiveData correctly

The NetworkResponse class within the project is based off of Google's Resource class from their GithubBrowserSample respository. It wraps the data in a class with a status attached to it and then loads that into LiveData so that the activity observing the LiveData can know if the network response was successful or not: https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt

Android Studio version: 4.1.2
Hours spent on project: About 6 hours
Device focus: The app will work on either phone or tablet, but the UI looks best on phone