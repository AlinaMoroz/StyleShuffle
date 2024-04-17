import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'data_base/data_base_cloth.dart';
import 'modal/auth_service.dart';
import 'modal/user.dart';
class Registration extends StatefulWidget{
  @override
  State<StatefulWidget> createState() => registration_();

}

class registration_ extends State<Registration> {


  @override
  Widget build(BuildContext context) {
    // User? user;
    // DataBaseCloth dataBaseCloth = DataBaseCloth();
    String login = '';
    String password = '';
    String add_password = '';
    AuthService authService = AuthService();




    const String assets1 = 'assets/images/first2.jpg';



    void registerButtonAction() async{
      if(login.isEmpty || password.isEmpty)return;
      User_? user_;
      if(password == add_password){
        user_ = await authService.registerIn(login.trim(), password.trim());

      }

      if(user_ == null){
        Fluttertoast.showToast(msg: "Can't register you. Please check your email/password",
            toastLength: Toast.LENGTH_LONG,
            backgroundColor: Colors.red,
            textColor: Colors.white,
            timeInSecForIosWeb: 1,
            gravity: ToastGravity.CENTER
        );


      }else{
        final navigator = Navigator.of(context);
        navigator.pushReplacementNamed('/3');
      }


    }
    Widget logo(){
      return const Padding(
        padding: EdgeInsets.only(top: 50),
        child: Align(
          child: Text("Регистрация",
            style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 45,
                color: Color.fromRGBO(51,115,125,5)

            ),),
        ),
      );
    }
    Widget base(){
      return Column(
        children: [
          SizedBox(
            width: double.minPositive+50,
            height: double.minPositive + 50,
          ),
          Text("Логин",
            style: TextStyle(
                fontWeight: FontWeight.normal,
                fontSize: 22,
                height: 1.5 ,
                color: Color.fromRGBO(51,115,125,5)
            ),),
          SizedBox(
            width: double.minPositive+300,
            height: double.minPositive + 40,

            child: TextField(
            //  obscureText: true,   // иконка ввода поменять!!!!!!!!!!!!!
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                fillColor: Color.fromRGBO(51,115,125,5),

              ),
              style: TextStyle(
                  fontWeight: FontWeight.normal,
                  fontSize: 22,
                  color: Color.fromRGBO(51,115,125,5)
              ),
              onChanged: (String value) {
                login = value;
               },
            ),
          ),


          /*ElevatedButton(onPressed: (){
            FirebaseFirestore.instance.collection('Логин').add({'логин': login},);
          },
            child: Text('Отправить'),
          ), */


          SizedBox(height: 10.0),
          Text("Пароль",style: TextStyle(
              fontWeight: FontWeight.normal,
              fontSize: 22,
              height: 1.5 ,
              color: Color.fromRGBO(51,115,125,5)
          ),),
          SizedBox(
            width: double.minPositive+300,
            height: double.minPositive + 40,

            child:  TextField(obscureText: true,   // иконка ввода поменять!!!!!!!!!!!!!
              //cursorColor: Color.fromRGBO(51,115,125,5),
              //cursorColor: Color.fromRGBO(51,115,125,5),
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                fillColor: Color.fromRGBO(51,115,125,5),

                // enabled: true,
                //labelText: 'Password',
              ),
              style: TextStyle(
                  fontWeight: FontWeight.normal,
                  fontSize: 22,
                  color: Color.fromRGBO(51,115,125,5)
              ),
              onChanged: (String value) {
                password = value;
              },
            ),

          ),

          SizedBox(height: 10.0),

          Text("Повторить пароль",style: TextStyle(
              fontWeight: FontWeight.normal,
              fontSize: 22,
              height: 1.5 ,
              color: Color.fromRGBO(51,115,125,5)
          ),),


          SizedBox(
            width: double.minPositive+400,
            height: double.minPositive + 40,

            child: TextField(obscureText: true,
              //cursorColor: Color.fromRGBO(51,115,125,5),
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                enabled: true,

                //labelText: 'Password',
              ),
              style: TextStyle(
                  fontWeight: FontWeight.normal,
                  fontSize: 22,
                  color: Color.fromRGBO(51,115,125,5)
              ),
              onChanged: (String value) {
                add_password = value;
              },

            ),
          ),
          SizedBox(height: 20.0),
          TextButton(
            child: Text('Зарегистрироваться'),
            style: TextButton.styleFrom(
              textStyle: TextStyle(
                fontWeight: FontWeight.normal,
                fontSize: 22,
                height: 1,
                color: Color.fromRGBO(51, 115, 125, 5),
              ),), onPressed: () {
            registerButtonAction();//метод входа
          },
          ),

          // InkWell(
          //   onTap: () async {
          //     bool userExists = true;
          //     // bool userExists = await dataBaseCloth.checkUser(login);
          //     print("--------------------------------------------------------");
          //
          //     // Добавление пользователя в базу данных
          //     if(password == add_password){
          //       if (userExists == false) {
          //
          //         // user!.log = login;
          //         // user!.path = password;
          //         //dataBaseCloth.addOrUpload(user);
          //         print("++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
          //
          //
          //         // FirebaseFirestore.instance.collection('users').add({
          //         //   'login': login,
          //         //   'password': password,
          //         // });
          //         final navigator = Navigator.of(context);
          //         navigator.pushReplacementNamed('/1');
          //       }
          //       else{
          //         showDialog(
          //           context: context,
          //           builder: (BuildContext context) {
          //             return AlertDialog(
          //               title: Text('Ошибка'),
          //               content: Text('Пользователь уже зарегистрирован'),
          //               actions: [
          //                 TextButton(
          //                   onPressed: () {
          //                     Navigator.of(context).pop();
          //                   },
          //                   child: Text('ОК'),
          //                 ),
          //               ],
          //             );
          //           },
          //         );
          //       }
          //     }
          //     else{
          //       showDialog(
          //         context: context,
          //         builder: (BuildContext context) {
          //           return AlertDialog(
          //             title: Text('Ошибка'),
          //             content: Text('Пароли не совподают, проверьте и попробуйте ещё раз'),
          //             actions: [
          //               TextButton(
          //                 onPressed: () {
          //                   Navigator.of(context).pop();
          //                 },
          //                 child: Text('ОК'),
          //               ),
          //             ],
          //           );
          //         },
          //       );
          //     }
          //   },
          //   child: Text(
          //     'Зарегистрироваться',
          //     style: TextStyle(
          //       fontWeight: FontWeight.normal,
          //       fontSize: 22,
          //       height: 1,
          //       color: Color.fromRGBO(51, 115, 125, 5),
          //     ),
          //   ),
          // ),

        ],
      );
    }


   /* Widget registration(){
      return(
          Padding(
            padding: const EdgeInsets.all(5),

            child: GestureDetector(
              child: const Text("Зарегистрироваться",style: TextStyle(
                  fontWeight: FontWeight.normal,
                  fontSize: 22,
                  height: 1,
                  color: Color.fromRGBO(51,115,125,5)
              ),),
              onTap: (){
                //FirebaseFirestore.instance.collection('');
                final navigator = Navigator.of(context);
                navigator.pushReplacementNamed('/1');
                //navigator.push('/2');

              },
            ),

          ));
    }
    */




    return MaterialApp(
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          resizeToAvoidBottomInset : false,

          body: Container(
            //padding: const EdgeInsets.all(10.0).add(const EdgeInsets.only(top: 50.0)),
            //color: Colors.redAccent,
            ///////////////////////////////////(1)
            alignment: Alignment.center,
            //padding: const EdgeInsets.all(10.0).add(const EdgeInsets.only(top: 50.0)),

            decoration: const BoxDecoration(
                image: DecorationImage(image: AssetImage(assets1),
                  fit:BoxFit.cover, )
            ),
            constraints: const BoxConstraints.expand(height: double.infinity),
            child: Column(

              mainAxisAlignment: MainAxisAlignment.start,


              children: [
                logo(),
                base(),
                //registration(),

              ],
            ),
          ) ,


        )
    );
  }




}
