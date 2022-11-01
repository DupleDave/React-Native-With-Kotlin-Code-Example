import React, {useEffect, useState} from 'react';
import {Text, View, StyleSheet, NativeSyntheticEvent, Button} from 'react-native';
import Clock from './Clock';
import SampleNativeComponent from './SampleNativeComponent';

interface BatteryStatusType {
  acCharge: boolean;
  chargePlug: number;
  isCharging: boolean;
  usbCharge: boolean;
}

interface DockStatusType {
  isDocked: boolean;
  isCar: boolean;
  isDesk: boolean;
}

const App = () => {
  const [date, setDate] = useState<string>('');
  const [seconds, setSeconds] = useState<number>(0);
  const [color, setColor] = useState<string>('red');
  const [batteryLevel, setBatteryLevel] = useState<number>(0);
  // const [isCharging, setIsCharging] = useState<boolean>('N/A');
  const [batteryStatus, setBatteryStatus] = useState<BatteryStatusType | null>(null);
  const [dockStatus, setDockStatus] = useState<DockStatusType | null>(null);

  useEffect(() => {
    Clock.getCurrentTime().then((time: string) => {
      setDate(new Date(time).toDateString());
    });
    Clock.getCurrentTimeEvents(setSeconds);
    Clock.dispatchEventEverySecond();
    Clock.getBatteryLevel().then((level: number) => {
      console.log({level})
      setBatteryLevel(level);
    });

    Clock.getBatteryStatus().then((status: BatteryStatusType) => {
      console.log({status})
      setBatteryStatus(status);
    });

    Clock.getDockedStatus().then((status: DockStatusType) => {
      console.log({status})
      setDockStatus(status);
    });

  }, []);

  const onUpdate = (e: NativeSyntheticEvent<{isPressed: boolean}>) => {
    if (e.nativeEvent.isPressed) {
      setColor(color === 'purple' ? '#78c49e' : 'purple');
    }
  };

  return (
    <View style={styles.container}>
      <Text>{date}</Text>
      
      <View style={{padding:20}}>
      <Text style={{fontWeight:"900", marginBottom:5}}>BATTERY STATS:</Text>
      <Text>The battery level is: {batteryLevel}</Text>
      <Text>Is Charging: {batteryStatus&& batteryStatus.isCharging?"Yes":"No"}</Text>
      <Text>Is plugged in: {batteryStatus&& batteryStatus.chargePlug?"Yes":"No"}</Text>
      <Text>Is USB charging: {batteryStatus&& batteryStatus.usbCharge?"Yes":"No"}</Text>
      <Text>Is AC charging: {batteryStatus&& batteryStatus.acCharge?"Yes":"No"}</Text>
      </View>

      <View style={{padding:20}}>
      <Text style={{fontWeight:"900", marginBottom:5}}>Dock STATS:</Text>
      <Text>Is Docked: {dockStatus&& dockStatus.isDocked?"Yes":"No"}</Text>
      <Text>Is docked to Car: {dockStatus&& dockStatus.isCar?"Yes":"No"}</Text>
      <Text>Is docked to Desk: {dockStatus&& dockStatus.isDesk?"Yes":"No"}</Text>
      
      </View>
      <View style={{flex:1, display:'flex',width:'100%', borderRadius:10, overflow:'hidden'}}>
      <SampleNativeComponent
        myColor={color}
        style={styles.button}
        onUpdate={onUpdate}
      />
      </View>

    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding:20,
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button: {
    flex:1,
    width: '100%',
  },
});

export default App;