import 'package:battery/bloc/battery_bloc.dart';
import 'package:battery/bloc/battery_event.dart';
import 'package:battery/bloc/battery_state.dart';
import 'package:battery/bloc/bluetooth_bloc.dart';
import 'package:battery/bloc/bluetooth_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'bloc/bluetooth_event.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MultiBlocProvider(providers: [
        BlocProvider(create: (context) => BatteryBloc()),
        BlocProvider(create: (context) => BluetoothBloc()),
      ], child: MyHomePage()),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  void initState() {
    super.initState();
    BlocProvider.of<BluetoothBloc>(context).add(ListenToBluetoothEvent());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(onPressed: getBattery, child: Text('Get battery level')),
              BlocBuilder<BatteryBloc, BatteryState>(
                builder: (context, state) {
                  if (state is ErrorBatteryState) return Text(state.error);
                  if (state is ShowingBatteryState) return Text(state.battery);
                  return Text('Click to get the battery level');
                },
              ),
              SizedBox(height: 20),
              ElevatedButton(onPressed: getBluetoothState, child: Text('Get bluetooth state')),
              BlocBuilder<BluetoothBloc, BluetoothState>(
                builder: (context, state) {
                  if (state is ErrorBluetoothState) return Text(state.error);
                  if (state is ChangedBluetoothState) return Text(state.status);
                  return Text('Unknown state');
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  void getBattery() {
    BlocProvider.of<BatteryBloc>(context).add(GetBatteryEvent());
  }

  void getBluetoothState() {
    // BlocProvider.of<BluetoothBloc>(context).add(ListenToBluetoothEvent());
  }
}
