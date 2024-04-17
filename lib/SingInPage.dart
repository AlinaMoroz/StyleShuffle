import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:shuffle1/modal/user.dart';

import 'modal/auth_service.dart';


class First extends StatefulWidget{
  @override
  State<StatefulWidget> createState() => _First();

}

class _First extends State<First>{
  String login = '';
  String password = '';
  @override
  // void initState() {
  //   super.initState();
  //   WidgetsFlutterBinding.ensureInitialized();
  //   Firebase.initializeApp().whenComplete(() {
  //     print("completed");
  //     setState(() {});
  //   });
  // }


  //const All({Ke})


  @override
  Widget build(BuildContext context) {

    User_? user_;
    AuthService authService = AuthService();

    const String assets1 = 'assets/images/first2.jpg';
    Widget logo() {
      return const Padding(
        padding: EdgeInsets.only(top: 25),
        child: Align(
          child: Text(
            "StyleShuffle",
            style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 45,
                color: Color.fromRGBO(51, 115, 125, 5)),
          ),
        ),
      );
    }
    void loginButtonAction() async{
      print("________________________________________________");
      print(login);
      print(password);
      if(login.isEmpty || password.isEmpty)return;
      user_ = await authService.signIn(login.trim(), password.trim());
      print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
      if(user_ == null){
        Fluttertoast.showToast(msg: "Can't singIn. Please check your email/password",
            toastLength: Toast.LENGTH_LONG,
            backgroundColor: Colors.red,
            textColor: Colors.white,
            timeInSecForIosWeb: 1,
            gravity: ToastGravity.CENTER
        );

      }else{
        print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        final navigator = Navigator.of(context);
        navigator.pushReplacementNamed('/2');
      }


    }

    Widget base() {
      return Column(
        children: [
          SizedBox(
            width: double.minPositive + 150,
            height: MediaQuery.of(context).size.height * 5 / 20,
          ),
          Text(
            "Логин",
            style: TextStyle(
                fontWeight: FontWeight.normal,
                fontSize: 22,
                height: 1.5,
                color: Color.fromRGBO(51, 115, 125, 5)),
          ),
          /*
            TextFormField(validator: (value){
              if(value.isEmpty){
                return "Пожалуйста введите";
              }
            }),
            */

          SizedBox(
            width: double.minPositive + 300,
            height: double.minPositive + 40,
            child: TextField(

              //cursorColor: Color.fromRGBO(51,115,125,5),
              //cursorColor: Color.fromRGBO(51,115,125,5),
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                fillColor: Color.fromRGBO(51, 115, 125, 5),

                // enabled: true,

                //labelText: 'Password',
              ),
              style: TextStyle(
                  fontWeight: FontWeight.normal,
                  fontSize: 22,
                  color: Color.fromRGBO(51, 115, 125, 5)),
              onChanged: (String value) {
                login = value;
                print(login);
              },
              // onSubmitted: (text) {
              //   login = text;
              //   print("onSubmitted");
              //   print("Введенный текст: $login");
              // },
            ),
          ),
          Text(
            "Пароль",
            style: TextStyle(
                fontWeight: FontWeight.normal,
                fontSize: 22,
                height: 1.5,
                color: Color.fromRGBO(51, 115, 125, 5)),
          ),
          SizedBox(
            width: double.minPositive + 300,
            height: double.minPositive + 40,
            child: TextField(
              obscureText: true,
              //cursorColor: Color.fromRGBO(51,115,125,5),
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                enabled: true,

                //labelText: 'Password',
              ),
              style: TextStyle(
                  fontWeight: FontWeight.normal,
                  fontSize: 22,
                  color: Color.fromRGBO(51, 115, 125, 5)),
              // onSubmitted: (text) {
              //   password = text;
              //   print("onSubmitted");
              //   print("Введенный текст: $password");
              // },
              onChanged: (value) {
                password = value;
                print(password);
              },
            ),
          ),
          TextButton(
            child: Text('Войти'),
            style: TextButton.styleFrom(
              textStyle: TextStyle(
              fontWeight: FontWeight.normal,
              fontSize: 22,
              height: 1,
              color: Color.fromRGBO(51, 115, 125, 5),
            ),), onPressed: () {

            loginButtonAction();//метод входа
          },
          ),
        ],
      );
    }

    Widget registration() {
      return (Padding(
        padding: const EdgeInsets.all(5),
        child: GestureDetector(
          child: const Text(
            "Регистрация",
            style: TextStyle(
                fontWeight: FontWeight.normal,
                fontSize: 22,
                height: 1,
                color: Color.fromRGBO(51, 115, 125, 5)),
          ),
          onTap: () {
            final navigator = Navigator.of(context);
            navigator.pushReplacementNamed('/3');
            //navigator.push('/2');
          },
        ),
      ));

      // return ElevatedButton(onPressed: () {
      //   final navigator = Navigator.of(context);
      //   navigator.pushReplacementNamed('/2');
      //   //MaterialPageRoute(builder: (context) => const registration());
      //   // Navigator.pushAndRemoveUntil(context, '/1',(route)=> false);
      // },
      //   child: const Text(
      //     "Регистрация",
      //     style: TextStyle(
      //       fontWeight: FontWeight.normal,
      //       fontSize: 22,
      //       height: 2,
      //       color: Color.fromRGBO(51, 115, 125, 5),
      //     ),
      //   ),
      // );
    }




    return MaterialApp(
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          resizeToAvoidBottomInset: false,
          body: Container(
            //padding: const EdgeInsets.all(10.0).add(const EdgeInsets.only(top: 50.0)),
            //color: Colors.redAccent,
            ///////////////////////////////////(1)
            alignment: Alignment.center,
            //padding: const EdgeInsets.all(10.0).add(const EdgeInsets.only(top: 50.0)),

            decoration: const BoxDecoration(
                image: DecorationImage(
              image: AssetImage(assets1),
              fit: BoxFit.cover,
            )),
            constraints: const BoxConstraints.expand(height: double.infinity),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                logo(),
                base(),
                registration(),
              ],
            ),
          ),
        ));
  }
}
