import 'dart:io';

import 'package:tflite/tflite.dart';

class Load_M{
  String? s;
  String? s1;
  Load_M(String s, String s1){
    this.s = s;
    this.s1 = s1;
  }

  loadModel()async {
    await Tflite.loadModel(
      model: s!,
      labels: s1!,
    );
  }





}