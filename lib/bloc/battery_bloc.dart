import 'package:battery/bloc/battery_event.dart';
import 'package:battery/bloc/battery_state.dart';
import 'package:bloc/bloc.dart';
import 'package:flutter/services.dart';

class BatteryBloc extends Bloc<BatteryEvent, BatteryState> {
  static const platform = MethodChannel('com.battery/battery');
  String _batteryLevel;
  BatteryBloc() : super(InitBatteryState());

  @override
  Stream<BatteryState> mapEventToState(BatteryEvent event) async* {
    if (event is GetBatteryEvent) {
      try {
        final int result = await platform.invokeMethod('getBatteryLevel');
        _batteryLevel = 'The battery is on $result%';
        yield ShowingBatteryState(_batteryLevel);
      } on PlatformException catch (e) {
        _batteryLevel = 'ERROR! $e';
        yield ErrorBatteryState(_batteryLevel);
      } catch (e) {
        _batteryLevel = 'ERROR! $e';
        yield ErrorBatteryState(_batteryLevel);
      }
    }
  }
}