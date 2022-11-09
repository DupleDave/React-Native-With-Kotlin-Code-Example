
import {NativeEventEmitter, NativeModules} from 'react-native';

class SampleModule {


  getBatteryLevel = async () => {
    return NativeModules.Battery.getBatteryLevel();
  }
  getBatteryStatus = async () => {
    return NativeModules.Battery.getBatteryStatus();
  }
  
  getDockedStatus = async () => {
    return NativeModules.Battery.getBatteryStatus();
  }
}

const Battery = new SampleModule();
export default Battery;