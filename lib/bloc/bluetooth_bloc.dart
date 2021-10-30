import 'package:battery/bloc/bluetooth_event.dart';
import 'package:battery/bloc/bluetooth_state.dart';
import 'package:bloc/bloc.dart';
import 'package:flutter/services.dart';

class BluetoothBloc extends Bloc<BluetoothEvent, BluetoothState> {
  static const platform = EventChannel('com.battery/bluetooth');
  String networkStatus;

  BluetoothBloc() : super(InitialBluetoothState());

  @override
  Stream<BluetoothState> mapEventToState(BluetoothEvent event) async* {
    if (event is ListenToBluetoothEvent) {
      platform.receiveBroadcastStream().listen((data) {
        add(OnBluetoothStatusChanged(status: data));
      });
    }
    if (event is OnBluetoothStatusChanged) {
      yield ChangedBluetoothState(event.status);
    }
  }
}
