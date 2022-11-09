
import {NativeEventEmitter, NativeModules} from 'react-native';

class SampleModule {
  getSdkVersion = async () => {
    return NativeModules.QCar.getSdkVersion();
  };
}

const QCar = new SampleModule();
export default QCar;