import 'dart:async';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:shuffle1/LoadModel.dart';
import 'package:shuffle1/modal/DBcloth.dart';
import 'package:tflite/tflite.dart';
import 'package:tflite/tflite.dart';
import 'package:fluttertoast/fluttertoast.dart';


import 'additionalP/api_client.dart';
import 'package:tflite/tflite.dart';

import 'mainButton/CustomBottomNavigationBar.dart';
import 'modal/user.dart';


// ignore: camel_case_types
class To_start_class_down extends StatefulWidget {
  const To_start_class_down({super.key});


  @override
  State<StatefulWidget> createState() => List_of_the_clothes();
}

// ignore: camel_case_types
class List_of_the_clothes extends State<To_start_class_down> {
  String assets1 = 'assets/images/base.jpg';
  //const List_of_the_clothes({Key? key}) : super(key: key);
  File? imageFile;
  Load_M? load_m1;
  Load_M? load_m2;
  List? _outputS;
  Uint8List? imageFile1;
  bool _loading = true;
  List? _output;
  List? _output1;
  String? imagePath;
  int selected = -1;
  int selectedS= -1;
  var output;


  @override
  initState(){
    super.initState();
    load_m1 = Load_M('assets/model_unquant.tflite', 'assets/labels.txt');
    load_m2 = Load_M('assets/model_unquant2.tflite', 'assets/labels2.txt');


    Timer.run(() {
      load_m1?.loadModel().then((value){
        setState(() {

        });


      });
      // you have a valid context here
    });
    //
    // new Future.delayed(load_m2?.loadModel().then((value){
    //   setState(() {
    //
    //   });
    // }));





  }

  @override
  didChangeDependencies(){
    Tflite.close();
    load_m2?.loadModel().then((value){
      setState(() {

      });


    });
    //
    super.didChangeDependencies();
  }


  Future classifyImage(File image)async{

    var output = await Tflite.runModelOnImage(
      path: image.path,
      numResults: 8,
      threshold: 0.5,
      imageMean: 127.5,
      imageStd: 127.5,
    );


    setState(() {
      if(_output == null && _loading == true) {
        _output = output!;
        _loading = false;
        selected = int.parse(_output![0]['label']);
      }
      else if(_outputS == null){
        _outputS = output!;
        selectedS = int.parse(_outputS![0]['label']);
      }
    });
    // if(_outputS != null){
    //   print("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    //   selectedS = int.parse(_outputS![0]['label']);
    // }
    // if(_output != null){
    //   print("*********************************");
    //   selected = int.parse(_output![0]['label']);
    // }
    // else{
    //   print(")))))))))))");
    // }

    print("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }









  @override
  void dispose() {
    Tflite.close();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var user = Provider.of<User_>(context);//получаем пользователя
    final MyClass myClass = new MyClass();


    Widget save(){
      return TextButton(
        onPressed: () async{
          print(user.uid1);
          print("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
          try {
            if(imageFile1 != null){
              File a  = await convertUint8ListToFile(imageFile1!);
              uploadDataToFirebase(
                  a as File, selectedS.toString(), selected.toString(),
                  user.uid1);
            }
            else{
              uploadDataToFirebase(
                  imageFile as File, selectedS.toString(), selected.toString(),
                  user.uid1);

            }

            //onPressed: resetToInitialState;,
          //MyClass().resetToInitialState();
            // Navigator.of(context).pushReplacement(
            //   MaterialPageRoute(builder: (BuildContext context) => To_start_class_down()),
            // );
            Fluttertoast.showToast(msg: "Сохранено",
                toastLength: Toast.LENGTH_LONG,
                backgroundColor: Color(0x3378C8FB),
                textColor: Colors.white,
                timeInSecForIosWeb: 1,
                gravity: ToastGravity.CENTER
            );
            myClass.resetToInitialState();
            Navigator.pushNamed(context, '/2');
            // Navigator.of(context).pushReplacement(
            //   MaterialPageRoute(builder: (BuildContext context) => To_start_class_down()),
            // );


          }catch(e){
            Fluttertoast.showToast(msg: e.toString(),
                toastLength: Toast.LENGTH_LONG,
                backgroundColor: Colors.red,
                textColor: Colors.white,
                timeInSecForIosWeb: 1,
                gravity: ToastGravity.CENTER
            );

          }
          },
        child: Container(
          height: MediaQuery.of(context).size.height * 1 / 17,
          width: MediaQuery.of(context).size.width * 4 / 6,
          margin: EdgeInsets.symmetric(horizontal: 40),
          decoration: BoxDecoration(
            color: Color(0xFF33737D),
            borderRadius: BorderRadius.circular(20),
          ),
          child: Center(
            child: Text(
              'Сохранить',
              style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.bold,
                fontSize: 20,
              ),
            ),
          ),
        ),
      );
    }

    Widget seasonButton(String name, int index){
      return TextButton(
        onPressed: (){
          setState(() {
            selectedS = index;
          });
        },
        child:Container(

          padding: EdgeInsets.all(1),

          width:
          MediaQuery.of(context).size.width * 7 / 20,
          height:
          MediaQuery.of(context).size.height * 1.3 / 35,
          decoration: BoxDecoration(
            color: (selectedS == index) ? Color(0x0e2149).withOpacity(0.7):Color(0xFF0A4D58).withOpacity(0.7),
            borderRadius: BorderRadius.circular(20),
          ),
          child: Center(
            child: Text(name,
                style: TextStyle(
                    fontSize: 15,
                    color: Colors.white)),
          ),
        ),
      );
    }

    Widget clothButton(String name, int index){
      return TextButton(
        onPressed: (){
          setState(() {
            selected = index;
          });
        },
        child:Container(

          padding: EdgeInsets.all(1),

          width:
          MediaQuery.of(context).size.width * 10 / 20,
          height:
          MediaQuery.of(context).size.height * 1.3 / 35,
          decoration: BoxDecoration(
            color: (selected == index) ? Color(0x0e2149).withOpacity(0.7):Color(0xFF0A4D58).withOpacity(0.7),
            borderRadius: BorderRadius.circular(20),
          ),
          child: Center(
            child: Text(name,
                style: TextStyle(
                    fontSize: 15,
                    color: Colors.white)),
          ),
        ),
      );
    }


    Widget? result(){
      if(_output != null){
        // print('${_output![0]['label']}');
        return Text('${_output![0]['label']}');
      }
      else{
        return null;
      }
    }

    Widget photo() {
      if (imageFile == null && imageFile1==null) {
        return Container(
          //width: double.maxFinite,
          padding: const EdgeInsets.all(25),
          height: MediaQuery.of(context).size.height * 3 / 12,
          width: MediaQuery.of(context).size.width * 4 / 5,
          decoration: BoxDecoration(
            color: Colors.blueGrey.withOpacity(0.3),
            borderRadius: BorderRadius.circular(10),
            border: Border.all(color: Colors.black.withOpacity(0.5), width: 0.5),
          ),
          child: Stack(
            children: [
              Align(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Container(
                      width: MediaQuery.of(context).size.width * 3 / 10,
                      height: MediaQuery.of(context).size.height * 1 / 2,
                      child: IconButton(
                        icon: Icon(
                          Icons.photo,
                          size: MediaQuery.of(context).size.width * 2.5 / 8,
                          color: Color(0xFF33737D),
                        ),
                        onPressed: (){
                          getImage(source: ImageSource.gallery);

                        }
                      ),
                    ),
                    Container(
                      width: MediaQuery.of(context).size.width * 3 / 9,
                      height: MediaQuery.of(context).size.height * 1 / 2,
                      child: IconButton(
                        icon: Icon(
                          Icons.photo_camera,
                          size: MediaQuery.of(context).size.width * 2.5 / 8,
                          color: Color(0xFF33737D),
                        ),
                        onPressed: () { getImage(source: ImageSource.camera);

                        }
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        );
      }
      else if (imageFile1 != null)
      {
        return Container(
            padding: const EdgeInsets.all(25),
            height: MediaQuery.of(context).size.height * 3 / 12,
            width: MediaQuery.of(context).size.width * 4 / 5,
            decoration: BoxDecoration(
              color: Colors.white.withOpacity(0.3),
              //borderRadius: BorderRadius.circular(20),
              //image: DecorationImage(image: FileImage(imageFile!)),
            ),
            child: Image.memory(imageFile1!)
        );
      }

      else {
        return Container(
            padding: const EdgeInsets.all(25),
            height: MediaQuery.of(context).size.height * 3 / 12,
            width: MediaQuery.of(context).size.width * 4 / 5,
            decoration: BoxDecoration(
              color: Colors.white.withOpacity(0.3),
              //borderRadius: BorderRadius.circular(20),
              image: DecorationImage(image: FileImage(imageFile!)),
            )



        );
      }
    }


    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: Container(
          alignment: Alignment.center,
          decoration: BoxDecoration(
            color: Colors.cyanAccent.withOpacity(0.1),
            image: DecorationImage(image: AssetImage(assets1),
            fit:BoxFit.cover, )
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              //SizedBox(height: 6),
              photo(),
              //SizedBox(height: 5.0),
              Container(
                color: Colors.transparent,
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                height: MediaQuery.of(context).size.height * 1 / 25,
                child: Column(
                  children: [
                    Text(
                      "Сезон",
                      style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.w500,
                          color: Color(0xFF33737D)),
                    ),
                  ],
                ),
              ),
              Container(
                //width: double.maxFinite,
                padding: const EdgeInsets.all(10),
                height: MediaQuery.of(context).size.height * 4.35 / 25,
                width: MediaQuery.of(context).size.width * 4.2 / 5,
                decoration: BoxDecoration(
                  color: Colors.blueGrey.withOpacity(0.3),
                  borderRadius: BorderRadius.circular(10),
                  border: Border.all(color: Colors.black.withOpacity(0.5), width: 0.5),
                ),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Column(
                          children: [
                            seasonButton('Лето', 2),
                            // const SizedBox(height: 13.0),
                            seasonButton('Весна', 1),

                          ],
                        ),
                        Column(
                          children: [
                            seasonButton('Осень', 0),
                            // const SizedBox(height: 13.0),
                            seasonButton('Зима', 3),
                          ],
                        ),
                      ],
                    ),
                  ],
                ),

              ),
              //SizedBox(height: 5.0),
              Container(
                color: Colors.transparent,
                padding: const EdgeInsets.symmetric(horizontal: 15.0),
                height: MediaQuery.of(context).size.height * 1 / 20,
                child: Column(
                  children: const [
                    SizedBox(height: 10),
                    Text(
                      "Тип одежды",
                      style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.w500,
                          color: Color(0xFF33737D)),
                    ),
                  ],
                ),
              ),
              Container(
                  /*/padding: const EdgeInsets.all(15),
                  decoration: const BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage(assets1),
                        fit: BoxFit.cover,
                      )), */
                  child:Container(
                    //width: double.maxFinite,
                    //padding: EdgeInsets.all(15),
                    height: MediaQuery.of(context).size.height * 3.36 / 15,
                    width: MediaQuery.of(context).size.width * 9 / 10,
                    decoration: BoxDecoration(
                      color: Colors.blueGrey.withOpacity(0.3),
                      borderRadius: BorderRadius.circular(10),
                      border: Border.all(color: Colors.black.withOpacity(0.5), width: 0.5),
                    ),
                    child: Column(
                      children: [Row(
                        mainAxisAlignment: MainAxisAlignment.spaceAround,

                        children: [
                          Expanded(
                            child: Column(
                              children: [
                                clothButton("Кофта",2),

                                clothButton("Футболка",4),

                                clothButton("Штаны",3),
                              ],
                            ),
                          ),
                          SizedBox(width: 2),
                          Expanded(
                            child: Column(

                              children: [
                                clothButton("Шорты",5),

                                clothButton("Юбка",6),

                                clothButton("Платье",7),

                              ],
                            ),
                          ),
                          SizedBox(width: 2),
                          Expanded(
                            child: Column(
                              children: [
                                clothButton("Куртка",1),

                                clothButton("Обувь",0),
                              ],
                            ),
                          ),
                        ],
                      ),

                      ],
                    ),
                  )
              ),
              SizedBox(height: 10),

              Container(child: save(),),


              //Container(child: result()),


            ],
          ),
        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          currentIndex: 1, // Укажите активный элемент здесь
          onTap: (index) {
            setState(() {
              switch (index) {
                case 0:
                  Navigator.pushNamed(context, '/4');
                  break;
                case 1:
                  Navigator.pushNamed(context, '/2');
                  break;
                case 2:
                  Navigator.pushNamed(context, '/5');
                  break;
                case 3:
                  Navigator.pushNamed(context, '/8');//7
                  break;
              }
            });},

        ),

      ),
    );
  }


  void getImage({required ImageSource source})async {
    final file = await ImagePicker().pickImage(source: source);
    try {
      if (file?.path != null) {
        imageFile1 = await file?.readAsBytes();
        imagePath = file?.path;
        imageFile = File(file!.path);

        await classifyImage(imageFile!);
        didChangeDependencies();
        await classifyImage(imageFile!);

        // print("5555555555555555555555555555555555555");
        // _output = load_m1?.classifyImage(imageFile!);
        // print("5555555555555555555555555555555555555");
        //
        // _output1 = load_m2?.classifyImage(imageFile!);

        print("5555555555555555555555555555555555555");
        print(int.parse(_output![0]['label']));
        if(int.parse(_output![0]['label']) < 7){
          selected = int.parse(_output![0]['label']);
          print("666666666666666666666666666666");
        }

        if(int.parse(_outputS![0]['label']) > 7){
          selectedS = int.parse(_outputS![0]['label'])-10;
          print("666666666666666666666666666666");
        }
        imageFile1 = await ApiClient().removeBgApi(imagePath!);     //обрезание фона





        setState(() {



        });

      }
    }catch(e){
      imageFile1 = null;
      setState(() {});
    }


  }
}
class MyClass extends StatefulWidget {
  void resetToInitialState() {
    // To_start_class_down();


    // Ваша логика сброса состояния
  }

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    throw UnimplementedError();
  }

}


