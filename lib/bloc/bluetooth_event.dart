abstract class BluetoothEvent{}

class ListenToBluetoothEvent extends BluetoothEvent{
}

class OnBluetoothStatusChanged extends BluetoothEvent{
  final String status;

  OnBluetoothStatusChanged({this.status});
}