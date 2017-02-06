# React Native Network Connection Class
Android Bridge to Facebook's [Network Connection Class](https://github.com/facebook/network-connection-class)

>Network Connection Class is an Android library that allows you to figure out the quality of the current user's internet connection. The connection gets classified into several "Connection Classes"  that make it easy to develop against.

[![NPM](https://nodei.co/npm/react-native-network-connection-class.png)](https://www.npmjs.com/package/react-native-network-connection-class)


## Installation
```bash
1) npm i --save react-native-network-connection-class

or manually:
Add repository to package.json
"react-native-device-year-class": "git+ssh://git@github.com:manuelkch/react-native-network-connection-class.git"

2) Run
npm install

or manually:
git clone the directory to [node_modules/react-native-network-connection-class]
```

### Add it to your react-native project
#### React Native Link
`react-native link react-native-network-connection-class`
#### or manually

* In `android/setting.gradle`
	```gradle
	...
  include ':react-native-network-connection-class'
  project(':react-native-network-connection-class').projectDir = new File(settingsDir, '../node_modules/react-native-network-connection-class/android')

	```
* In `android/app/build.gradle`

	```gradle
	...
	dependencies {
	    ...
      compile project(':react-native-network-connection-class')
	}

  ```

* register module in `MainApplication.java`

  ```java
    import com.ymc.NetworkConnectionClass.Package;  
    ...

    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new Package() // <-- Add this line.
        );
    }
	```

## Example

```javascript
...
import NetworkConnectionClass from 'react-native-network-connection-class'  
	...
	//listen to connection class changes
	DeviceEventEmitter.addListener('connectionClassChange', function(e: Event) {
		// new state
    console.warn(e.state)
  })
	...
	//get current class string (POOR, MODERATE,GOOD or EXCELENT)
	NetworkConnectionClass.getCurrentQuality().then((status) => {
		//current state
		console.warn(status)
	})
	...
	//start/stop sampling bandwith data on newtork activities
	NetworkConnectionClass.startSampling()
	.fetch('https://jsonplaceholder.typicode.com/photos')
		.then(function(response) {
			NetworkConnectionClass.stopSampling()
			...
	})
	...
	// get boolean if sampling thread is running
	NetworkConnectionClass.getSamplingState().then((isSampling) => {
		console.warn(isSampling)
	})

```
