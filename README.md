# Remotecraft for Android [![Build Status](https://travis-ci.org/RemotecraftProject/RemotecraftApp.svg?branch=feature/search_server)](https://travis-ci.org/RemotecraftProject/RemotecraftApp) [![codecov](https://codecov.io/gh/RemotecraftProject/RemotecraftApp/branch/feature%2Fsearch_server/graph/badge.svg)](https://codecov.io/gh/RemotecraftProject/RemotecraftApp)
**(Work in progress)**

Connect and remotely manage your Minecraft server using cutting edge technology!

## Table of contents
- [How it works](#how-it-works)
- [Running tests](#running-tests)
- [Technical Documentation](#technical-documentation)
    - [AutoValue](#autovalue)
    - [Injecting mock dependencies in Robolectric tests using Dagger subcomponents](#injecting-mock-dependencies-in-robolectric-tests-using-dagger-subcomponents)
    - [Mock network responses](#mock-network-responses)
    - [Notification flow when the activity is in background](#notification-flow-when-the-activity-is-in-background)
- [Credits](#credits)
- [License](#license)

## How it works
If you don't have an actual server to run against, just run the *mockDebug* build variant. No extra stuff needed.


Otherwise: <br />
1. Run Minecraft on your computer, with a previously installed version of the **Remotecraft Forge Mod** (currently unavailable) found [here](https://github.com/RemotecraftProject/RemotecraftMod). <br />
2. Run the Android app (using a *prod* flavor) <br />
3. Make sure the computer running the game and the Android device running the app are both in the same WiFi network. <br />
4. Profit!

## Running tests
Run the unit tests
```bash
./gradlew check
```

Run the UI/Espresso tests
```bash
./gradlew connectedCheck
```

## Technical Documentation

### AutoValue
[AutoValue](https://github.com/google/auto/tree/master/value) is a nice little library by Google that allows you to make immutable value classes without all the noisy boilerplate. It also lets you easily generate builder classes, implement parcelable and even make it work flawlessly with Gson, just by using a couple extensions by [Ryan Harter](https://github.com/rharter) [[AutoValue: Parcel Extension](https://github.com/rharter/auto-value-parcel)] [[AutoValue: Gson Extension](https://github.com/rharter/auto-value-gson)]

As an example of that, by using AutoValue I managed to reduce the size of the ServerModel class from [159](https://github.com/RemotecraftProject/RemotecraftApp/blob/ea55ba37e0d2b8a37297c5643d5d12b9c05ae576/presentation/src/main/java/com/remotecraft/app/model/ServerModel.java) to merely [38 lines](https://github.com/RemotecraftProject/RemotecraftApp/blob/fe7ed548f98cdd9651ec25581d9f9e9b48f0518d/presentation/src/main/java/com/remotecraft/app/model/ServerModel.java) preserving the exact same class traits (immutability + builder pattern + parcelable), plus introducing new ones: ready-to-use **hashCode()** and **equals(Object)** methods.

It is also worth noting that it is not only desirable but also a good practice to remove the "**get**" prefix for the accessor methods. Since there are no mutator methods, you know for sure it's an accessor.

Learn more about AutoValue: <br />
http://rst-it.com/blog/autovalue/

### Injecting mock dependencies in Robolectric tests using Dagger subcomponents
Once I started learning Robolectric I was facing the problem of injecting mock collaborators when testing activities. At first, I tried using [DaggerMock](https://github.com/fabioCollini/DaggerMock) but I was unable make it work, got frustrated and forgot about it for quite some time. Recently I came across this blog post ["Activities Subcomponents Multibinding in Dagger 2"](http://frogermcs.github.io/activities-multibinding-in-dagger-2/) which was so enlightening because I learned about this new way of injecting dependencies using subcomponents, only available in Dagger 2.7 onwards.

After that, I just had to define my mock collaborators, create a new subcomponent where I get an activity reference that lets me inject these collaborators, mock the subcomponent builder to return the subcomponent I previously created, and finally add it to the application's **activityComponentBuilders** map.

```java
  @Mock private ServerSearchComponent.Builder mockBuilder;
  @Mock private Navigator mockNavigator;
  @Mock private ServerSearchPresenter mockServerSearchPresenter;
  @Mock private ImageLoader mockImageLoader;
  
  private ServerSearchComponent serverSearchComponent = new ServerSearchComponent() {
    @Override public void injectMembers(ServerSearchActivity instance) {
      instance.navigator = mockNavigator;
      instance.presenter = mockServerSearchPresenter;
      instance.imageLoader = mockImageLoader;
    }
  };
  
  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(mockBuilder.build()).thenReturn(serverSearchComponent);
    when(mockBuilder.activityModule(any(ServerSearchModule.class))).thenReturn(mockBuilder);
    ((RemotecraftMockApp) RuntimeEnvironment.application).putActivityComponentBuilder(mockBuilder, ServerSearchActivity.class);

    // ...
    serverSearchActivity = Robolectric.setupActivity(ServerSearchActivity.class);
    // ...
  }
```
*ServerSearchActivityTest.java*

### Mock network responses
At first I wrote a pure java module called *mockserver* that I had to run everytime I wanted to test the app. It was a simple java app emulating a server and returning fake hardcoded responses.

Later on, I figured out a way to avoid this dependency and achieve self-contained mock responses within the app itself.

I have two different implementations for the **NetworkConnectionlessTransmitter** abstraction. <br />
The real one using a *DatagramSocket*:

```java
public class NetworkDatagramTransmitter implements NetworkConnectionlessTransmitter {

  // ...

  @Override public void send(NetworkPacket networkPacket) throws IOException {
    datagramSocket.send(networkPacket.getDatagramPacket());
  }

  @Override public NetworkPacket receive(NetworkPacket networkPacket) throws IOException {
    datagramSocket.receive(networkPacket.getDatagramPacket());
    return new NetworkPacket(networkPacket.getDatagramPacket());
  }
  
  // ...
}
```

And the fake one:

```java
public class NetworkConnectionlessMockTransmitter implements NetworkConnectionlessTransmitter {
  // ...
  
  @Override public void send(NetworkPacket networkPacket) throws IOException {

  }

  @Override public NetworkPacket receive(NetworkPacket networkPacket) throws IOException {
    ServerMessage mockServerMessageResponse = getMockMessageResponse();
    String mockServerMessageResponseJson = jsonSerializer.toJson(mockServerMessageResponse);

    return new NetworkPacket(mockServerMessageResponseJson);
  }
  
  // ...
}
```

I made a new build type and a new BuildConfig variable:

```groovy
  buildTypes {
  
	// ...

    mock {
      debuggable true
      testCoverageEnabled = true
      signingConfig signingConfigs.debug

      buildConfigField("boolean", "IS_MOCK", "true")
    }

	// ...
	
  }
```

And finally, I just have to provide the proper *NetworkConnectionlessTransmitter* implementation using Dagger:

```java
@Module public class NetworkModule {

	// ...
	
  @Provides @Singleton NetworkConnectionlessTransmitter provideNetworkConnectionlessTransmitter(
      NetworkDatagramTransmitter networkDatagramTransmitter,
      NetworkConnectionlessMockTransmitter networkConnectionlessMockTransmitter) {
    if (BuildConfig.IS_MOCK) {
      return networkConnectionlessMockTransmitter;
    } else {
      return networkDatagramTransmitter;
    }
  }
	
	// ...

}
```

### Notification flow when the activity is in background

![Notification Flow](https://github.com/RemotecraftProject/Documentation/blob/master/images/notification_flow.png)

When a server is found we need to be able to display a notification. Only when the activity is in background, though.

1. The user clicks on the "Scan Wi-Fi" Floating Action Button from the *ServerSearchActivity*.
2. The *ServerSearchPresenter* receives the event and executes the *SearchServerInteractor* passing an observer as an argument: the **presentationObserver**.
3. The interactor creates a second observer: the **domainObserver**. And both observers are subscribed to the same observable provided by the *ServerSearchManager* from the infrastructure layer. (There's actually a NetworkDataProvider in-between)
4. Let's say that, whilst the ServerSearchManager is working, the activity is stopped. At this point, the presenter will invoke the dispose() method from the *SearchServerInteractor*.

	```java
	@Override public void dispose() {
	  if (presentationObserver != null && !presentationObserver.isDisposed()) {
	    disposables.remove(presentationObserver);
	  }
	}
	```
	Thus, effectively unsubscribing the presentationObserver from the observable.
5. Now let's say the ServerSearchManager finally found a valid server, and the observable emits the value. Since the presentationObserver was previously unsubscribed, only the domainObserver will receive the data.

	```java
	private final class SearchServerDomainObserver extends DefaultObservableObserver<Server> {
		@Override public void onNext(Server server) {
		  processFoundServer(server);
		}
		// ...
	}
	```
	And the notification will be thrown only if the presentationObserver is unsubscribed.

	```java
	private void processFoundServer(Server server) {
		if (presentationObserver != null && !presentationObserver.isDisposed()) {
		  return;
		}
	
		notifyServerFoundService.notifyServerFound(server);
	}
	```

	Now, we want to pass the Server model from the infrastructure to the presentation layer through the notification.<br />
	Remember that:
	* Presentation layer uses ServerModel, and has a mapper ServerModel <-> Server
	* Domain layer uses Server.
	* Infrastructure and Data layers uses ServerEntity, and they have a mapper Server <-> ServerEntity

6. The *AndroidNotificationManager* maps the ServerEntity into a Server object and serializes it to JSON. The serialized Server bundled to the intent and attached to the notification.

	```java
	public void notifyServerFound(ServerEntity serverEntity) {
		// ...
	
	  	Intent notificationIntent = new Intent(context, serverSearchActivityClass);
	  	Server server = serverEntityDataMapper.transform(serverEntity);
	  	String serializedServer = jsonSerializer.toJson(server);
	  	notificationIntent.putExtra(KEY_DOMAIN_SERVER_FOUND_SERIALIZED, serializedServer);
	  	PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, requestCode, notificationIntent,
	        PendingIntent.FLAG_UPDATE_CURRENT);
	  	notificationCompatBuilder.setContentIntent(notificationPendingIntent);
	
	  	// ...
	
	  	displayNotification(NOTIFICATION_SERVER_FOUND, notificationCompatBuilder.build());
	}
	```
	Mapping is an important step, since the presentation layer wouldn't be able to understand a ServerEntity object.

7. When the user clicks the notification, the *ServerSearchActivity* will open with the previously attached intent.

	```java
	@Override protected void onNewIntent(Intent intent) {
	  mapExtras(intent);
	  super.onNewIntent(intent);
	}
	```
	
	```java
	private void mapExtras(Intent intent) {
	  if (intent == null || intent.getExtras() == null) {
	    return;
	  }
	
	  Bundle extras = intent.getExtras();
	  // ...
	
	  if (extras.getString(KEY_DOMAIN_SERVER_FOUND_SERIALIZED) != null) {
	    String serializedServer = extras.getString(KEY_DOMAIN_SERVER_FOUND_SERIALIZED);
	    presenter.onSerializedDomainServerFound(serializedServer);
	  }
	}
	```

8. The *ServerSearchPresenter* will be able to properly deserialize the json to a Server (domain) object, map it to a ServerModel, and display the server details.

	```java
	public void onSerializedDomainServerFound(String serializedDomainServer) {
	  checkViewAttached();
	
	  Server server = serverDeserializer.deserialize(serializedDomainServer);
	
	  ServerModel serverModel = serverModelDataMapper.transform(server);
	  getView().navigateToServerDetail(serverModel);
	}
	```

## Credits

**Architecting Android...The clean way?** by Fernando Cejas <br />
https://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/ <br />
https://github.com/android10/Android-CleanArchitecture

**Infrastructure (or Platform-Specific) layer in Clean Architecture** <br />
https://github.com/android10/Android-CleanArchitecture/issues/151 <br />

**Network discovery using UDP Broadcast (Java)** <br />
http://michieldemey.be/blog/network-discovery-using-udp-broadcast/

**Wait for it… IdlingResource and ConditionWatcher** <br />
https://medium.com/azimolabs/wait-for-it-idlingresource-and-conditionwatcher-602055f32356#.z8jin4693 <br />
https://github.com/AzimoLabs/ConditionWatcher

**Android UI Test — Espresso Matcher for ImageView** <br />
https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f#.pmayfwrf9

**Asserting for a Toast message using Robolectric** <br />
http://www.jameselsey.co.uk/blogs/techblog/asserting-for-a-toast-message-using-robolectric/

**AutoValue - get rid of boilerplate code in your Android app** <br />
http://rst-it.com/blog/autovalue/

**Activities Subcomponents Multibinding in Dagger 2** <br />
http://frogermcs.github.io/activities-multibinding-in-dagger-2/

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
