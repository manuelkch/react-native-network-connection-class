package com.ymc.NetworkConnectionClass;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.network.connectionclass.*;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.Arguments;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class Module extends ReactContextBaseJavaModule {

  private static final String TAG = "ReactNativeConnectionClass";

  private ReactContext mReactContext;
  private DeviceBandwidthSampler mDeviceBandwidthSampler;
  private ConnectionChangedListener mListener;
  private ConnectionClassManager mConnectionClassManager;

  public Module(ReactApplicationContext reactContext) {
    super(reactContext);
    mReactContext = reactContext;
    mDeviceBandwidthSampler = DeviceBandwidthSampler.getInstance();
    mListener = new ConnectionChangedListener();
    mConnectionClassManager = ConnectionClassManager.getInstance();
    mConnectionClassManager.register(mListener);
  }

  @Override
  public String getName() {
    return "NetworkConnectionClass";
  }

  private void sendEvent(ReactContext reactContext,
                         String eventName,
                         WritableMap params) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }

  @ReactMethod
  public void startSampling() {
    mDeviceBandwidthSampler.startSampling();
  }

  @ReactMethod
  public void stopSampling() {
    mDeviceBandwidthSampler.stopSampling();
  }

  @ReactMethod
  public void getCurrentQuality(Promise promise) {
        try {
          ConnectionQuality cq = mConnectionClassManager.getCurrentBandwidthQuality();
          String value = cq.toString();
          promise.resolve(value);
        } catch (Exception e) {
          promise.reject(e);
        }
  }

  private class ConnectionChangedListener
     implements ConnectionClassManager.ConnectionClassStateChangeListener {

       @Override
       public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
           WritableMap params = Arguments.createMap();
           params.putString("state", bandwidthState.toString() );
           sendEvent(mReactContext, "connectionClassChange", params);
           Log.e(TAG, bandwidthState.toString());
         }
   }
}
