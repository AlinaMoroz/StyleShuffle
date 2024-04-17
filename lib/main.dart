//import 'dart:js';

import 'dart:io';

import 'package:firebase_app_check/firebase_app_check.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:shuffle1/TakePhotoPage.dart';
import 'package:shuffle1/RegistrationPage.dart';

import 'ShopAllPage.dart';
import 'SingInPage.dart';
import 'WeatherPage.dart';
import 'CollectionSetPage.dart';
import 'create_photo.dart';
import 'CreateSetPage.dart';
import 'modal/auth_service.dart';
import 'modal/user.dart';

Future <void> main() async {

  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();

  // Активация провайдера App Check
  await FirebaseAppCheck.instance.activate();
  HttpOverrides.global = MyHttpOverrides();

  runApp(const Main());
}

class Main extends StatelessWidget {
  const Main({Key? key});

  @override
  Widget build(BuildContext context) {
    return StreamProvider<User_?>.value(
      value: AuthService().currentUser.map((user) => user ?? User_()),
      initialData: User_(),
      child: Consumer<User_>(
        builder: (context, user, _) {
          // final initialRoute = user != null ? '/2' : '/1';
          // final initialRoute = '/2';
          return MaterialApp(
            theme: ThemeData(
              primaryColor: Colors.deepOrange,
            ),
            routes: {
              '/1': (context) => First(),
              '/2': (context) => To_start_class_down(),
              '/3': (context) => Registration(),
              '/4': (context) => WeatherAll(),
              '/5': (context) => create_style(),
              '/6': (context) => To_start_class_down(),
              '/7': (context) => CollectionAll(),
              '/8': (context) => ShopAllPage(),
            },
            //initialRoute: initialRoute,
            initialRoute: '/4',
          );
        },
      ),
    );
  }
}




class MyHttpOverrides extends HttpOverrides{

  @override
  HttpClient createHttpClient(SecurityContext? context){
    return super.createHttpClient(context)
      ..badCertificateCallback = (X509Certificate cert, String host, int port)=> true;
  }
}



