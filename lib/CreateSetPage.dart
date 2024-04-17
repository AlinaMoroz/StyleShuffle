import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:provider/provider.dart';

import 'create_photo.dart';
import 'data_base/data_base_cloth.dart';
import 'icons/my_flutter_app_icons.dart';

import 'mainButton/CustomBottomNavigationBar.dart';
import 'modal/DBcloth.dart';
import 'modal/user.dart';

class create_style extends StatefulWidget {
  final Set<String> imageUrls;

  create_style() : imageUrls = Set<String>();

  create_style.withImages(this.imageUrls);

  @override
  _create_styleState createState() => _create_styleState();
}

class _create_styleState extends State<create_style> {
  // Конструктор, принимающий Set<String>
  // _create_styleState.withImages(Set<String> images) {
  //   imageUrls = images;
  // }
  int currentIndex = 1;
  int iconsVisible = -1;

  @override
  Widget build(BuildContext context) {
    String nameSet = '';
    String assets1 = 'assets/images/base.jpg';
    var user = Provider.of<User_>(context); //получаем пользователя
    Set<String> imageUrlss = widget.imageUrls;

    //List<String> selectedPhotos = [];
    Widget panelForPhoto() {
      List<String> imageUrlList = imageUrlss?.toList() ?? [];

      return Container(
        padding: const EdgeInsets.all(25),
        height: MediaQuery.of(context).size.height * 5 / 12,
        width: MediaQuery.of(context).size.width * 4 / 5,
        decoration: BoxDecoration(
          color: Colors.blueGrey.withOpacity(0.5),
          borderRadius: BorderRadius.circular(0),
          border: Border.all(color: Colors.white.withOpacity(0.5), width: 1),
        ),
        child: GridView.builder(
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: imageUrlList.length > 4 ? 3 : 2, // Количество элементов в строке
            mainAxisSpacing: 0, // Отступы между элементами по горизонтали
            crossAxisSpacing: 0, // Отступы между элементами по вертикали
          ),
          itemCount: imageUrlList.length,
          itemBuilder: (context, index) {
            String imageUrl = imageUrlList[index];
            return Container(
              margin: EdgeInsets.zero,
              child: Image.network(
                imageUrl,
                fit: BoxFit.cover,
              ),
            );
          },
        ),
      );
    }

    void _openNextScreen(int iconsVisible) async {
      // Открывает следующее окно
      imageUrlss = await Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => CreateStylePhotos(
                idCloth: iconsVisible.toString(), imageUrls: imageUrlss)),
      );

      setState(() {});


    }

    Widget pictures1() {
      return Container(
        child: Stack(
          children: [
            Align(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  Expanded(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 16),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(2);
                            },
                            icon: Icon(
                              MyFlutterApp.sport_jacket_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 11),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(5);
                            },
                            icon: Icon(
                              MyFlutterApp.shorts_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 11),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(0);
                            },
                            icon: Icon(
                              MyFlutterApp.shoe_5_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                      ],
                    ),
                  ),
                  Expanded(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 16),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(4);
                            },
                            icon: Icon(
                              MyFlutterApp.tshirt_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 11),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(6);
                            },
                            icon: Icon(
                              MyFlutterApp.skirt_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 11),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(1);
                            },
                            icon: Icon(
                              MyFlutterApp.down_jacket_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                      ],
                    ),
                  ),
                  Expanded(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 16),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(3);
                            },
                            icon: Icon(
                              MyFlutterApp.jeans_boy_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                        SizedBox(
                            height: MediaQuery.of(context).size.width * 1 / 11),
                        IconButton(
                            onPressed: () {
                              _openNextScreen(7);
                            },
                            icon: Icon(
                              MyFlutterApp.evening_dress_svgrepo_com,
                              size: MediaQuery.of(context).size.width * 1 / 8,
                              color: Color(0xFF33737D),
                            )),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      );
    }

    Widget save() {
      String nameSet = '';
      MyClass myClass = MyClass();
      return Column(
        children: [

          TextButton(
            onPressed: () async {

              try {
                addDocument(user.uid, nameSet, imageUrlss);
                Fluttertoast.showToast(
                    msg: "Сохранено",
                    toastLength: Toast.LENGTH_LONG,
                    backgroundColor: Color(0x3378C8FB),
                    textColor: Colors.white,
                    timeInSecForIosWeb: 1,
                    gravity: ToastGravity.CENTER);
                myClass.resetToInitialState();
                Navigator.pushNamed(context, '/5');
              } catch (e) {
                Fluttertoast.showToast(
                    msg: e.toString(),
                    toastLength: Toast.LENGTH_LONG,
                    backgroundColor: Colors.red,
                    textColor: Colors.white,
                    timeInSecForIosWeb: 1,
                    gravity: ToastGravity.CENTER);
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
          ),
        ],
      );
    }

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: Container(
          decoration: BoxDecoration(
            color: Colors.cyanAccent.withOpacity(0.1),
            image: DecorationImage(
              image: AssetImage(assets1),
              fit: BoxFit.cover,
            ),
          ),
          child: Column(
            children: [
              Column(
                children: [
                  panelForPhoto(),
                  SizedBox(height: 10.0),
                  TextField(
                    onChanged: (value) {
                      setState(() {
                        nameSet = value;

                      });

                      // Обработ
                    },
                    decoration: InputDecoration(
                      labelText: 'Имя для вашего сета',

                      hintText: 'Введите текст сюда',
                      // Подсказка для ввода
                      //prefixIcon: Icon(Icons.text_fields),
                      // Иконка перед полем ввода (необязательно)
                      border: OutlineInputBorder(), // Стиль границы поля ввода
                    ),
                  ),
                  pictures1(),
                  SizedBox(height: 25.0),
                  save(),
                  //PhotoPanel(selectedPhotos: selectedPhotos,),
                  //FirstOrSecond,
                ],
              )
            ],
          ),
        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          currentIndex: 2, // Укажите активный элемент здесь
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
                  Navigator.pushNamed(context, '/7');
                  break;
              }
            });},

        ),
      ),
    );
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