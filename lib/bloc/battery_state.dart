abstract class BatteryState {}

class InitBatteryState extends BatteryState {}

class ShowingBatteryState extends BatteryState {
  final String battery;

  ShowingBatteryState(this.battery);
}

class ErrorBatteryState extends BatteryState {
  final String error;

  ErrorBatteryState(this.error);
}
