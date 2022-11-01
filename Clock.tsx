
import {NativeEventEmitter, NativeModules} from 'react-native';

class SampleModule {
  getCurrentTime = async () => {
    return NativeModules.Clock.getCurrentTime();
  };

  dispatchEventEverySecond = () => {
    NativeModules.Clock.dispatchEventEverySecond();
  };

  getBatteryLevel = async () => {
    return NativeModules.Clock.getBatteryLevel();
  }
  getBatteryStatus = async () => {
    return NativeModules.Clock.getBatteryStatus();
  }
  
  getDockedStatus = async () => {
    return NativeModules.Clock.getBatteryStatus();
  }


  getCurrentTimeEvents = (callback: (time: number) => void): void => {
    const clockEvents = new NativeEventEmitter(NativeModules.Clock);

    clockEvents.addListener('onTimeUpdated', (time: {count: number}) => {
      callback(time.count);
    });
  };
}

const Clock = new SampleModule();
export default Clock;