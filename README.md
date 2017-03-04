# Remotecraft for Android [![Build Status](https://travis-ci.org/RemotecraftProject/RemotecraftApp.svg?branch=feature/search_server)](https://travis-ci.org/RemotecraftProject/RemotecraftApp) [![codecov](https://codecov.io/gh/RemotecraftProject/RemotecraftApp/branch/feature%2Fsearch_server/graph/badge.svg)](https://codecov.io/gh/RemotecraftProject/RemotecraftApp)
**(Work in progress)**

Connect and remotely manage your Minecraft server using cutting edge technology!

## How it works
I wrote a fake server so you don't actually need to be running the videogame itself or install extra stuff.

1. You need to run the [MockServer](https://github.com/RemotecraftProject/RemotecraftApp/blob/feature/search_server/mockserver/src/main/java/com/zireck/remotecraft/server/mock/MockServer.java) class within the mockserver module as a pure java application. <br />
*(Edit configurations -> + -> Application -> Browse Main class -> MockServer)*
2. Run the Android app.
3. Make sure the computer running the server and the Android device running the app are both in the same WiFi network.
4. Profit!

## Running tests
Run the unit tests
```
./gradlew check
```

Run the UI/Espresso tests
```
./gradlew connectedCheck
```

## Credits

**Architecting Android...The clean way?** by Fernando Cejas <br />
https://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/ <br />
https://github.com/android10/Android-CleanArchitecture

**Infrastructure (or Platform-Specific) layer in Clean Architecture** <br />
https://github.com/android10/Android-CleanArchitecture/issues/151 <br />

**Wait for it… IdlingResource and ConditionWatcher** <br />
https://medium.com/azimolabs/wait-for-it-idlingresource-and-conditionwatcher-602055f32356#.z8jin4693 <br />
https://github.com/AzimoLabs/ConditionWatcher

**Android UI Test — Espresso Matcher for ImageView** <br />
https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f#.pmayfwrf9

**Asserting for a Toast message using Robolectric** <br />
http://www.jameselsey.co.uk/blogs/techblog/asserting-for-a-toast-message-using-robolectric/

## License

    Copyright 2017 Andrés Hernández

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
