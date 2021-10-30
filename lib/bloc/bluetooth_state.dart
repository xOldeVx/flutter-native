abstract class BluetoothState {}

class InitialBluetoothState extends BluetoothState{}

class ErrorBluetoothState extends BluetoothState{
  final String error;

  ErrorBluetoothState(this.error);
}
class ChangedBluetoothState extends BluetoothState{
  final String status;

  ChangedBluetoothState(this.status);
}