import 'package:flutter/material.dart';
import 'package:shuffle1/additionalP/api_client.dart';

import 'mainButton/CustomBottomNavigationBar.dart';
import 'modal/weather_model.dart';


class WeatherAll extends StatefulWidget {
  @override
  _WeatherState createState() => _WeatherState();
}

class _WeatherState extends State<WeatherAll> {
  WeatherApiClient client = WeatherApiClient();
  Weather? data;



  Future<void> getData() async {
    data = await client.getCurrentWeather("Moskow") as Weather?;
  }

  @override
  Widget build(BuildContext context) {
    String assets1 = 'assets/images/base.jpg';

    TextStyle titleFont =
        const TextStyle(fontWeight: FontWeight.w600, fontSize: 18.0);
    TextStyle infoFont =
        const TextStyle(fontWeight: FontWeight.w400, fontSize: 18.0);

    Widget currentWeather(
        IconData icon, String temp, String location, String condition,String imageUrl) {
      return Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            SizedBox(
              height: MediaQuery.of(context).size.height * 3 / 25,
            ),
            Text(
              "$location",
              style: TextStyle(
                fontSize: 30.0,
                //color: Color.fromRGBO(51,115,125,5),
              ),
            ),
            SizedBox(
              height: 10.0,
            ),
            Text(
              "$condition",
              style: TextStyle(
                fontSize: 20.0,
              ),
            ),
            SizedBox(
              height: 10.0,
            ),
            Text(
              "$temp",
              style: TextStyle(
                fontSize: 45.0,
              ),
            ),
            SizedBox(height: 10.0),
            Container(
              width: 200,
              height: 200,
              child: Image.network(
                imageUrl,
                fit: BoxFit.cover,
              ),
            ),
            SizedBox(height: 3.0),
            Divider()
          ],
        ),
      );
    }

    Widget dopinformation(
        String wind, String humidity, String pressure, String feels_like) {
      return Container(
          width: double.infinity,
          padding: EdgeInsets.all(18),
          child: FutureBuilder(
            future: getData(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.done) {
                return Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text(
                              "Ветер",
                              style: titleFont,
                            ),
                            SizedBox(height: 18.0),
                            Text(
                              "Давление",
                              style: titleFont,
                            ),
                          ],
                        ),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text(
                              "$wind",
                              style: infoFont,
                            ),
                            SizedBox(height: 18.0),
                            Text(
                              "$pressure",
                              style: infoFont,
                            ),
                          ],
                        ),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text(
                              "Влажность",
                              style: titleFont,
                            ),
                            SizedBox(height: 18.0),
                            Text(
                              "Ощущается",
                              style: titleFont,
                            ),
                          ],
                        ),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text(
                              "$humidity",
                              style: infoFont,
                            ),
                            SizedBox(height: 18.0),
                            Text(
                              "$feels_like",
                              style: infoFont,
                            ),
                          ],
                        )
                      ],
                    ),
                  ],
                );
              } else {
                return Container();
              }
            },
          ));
    }

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        resizeToAvoidBottomInset: false,
        body:

            ///FutureBuilder(builder: builder)
            Container(
          alignment: Alignment.center,
          decoration: BoxDecoration(
            color: Colors.cyanAccent.withOpacity(0.1),
            //image: DecorationImage(image: AssetImage(assets1),
            //fit:BoxFit.cover, )
          ),
          constraints: const BoxConstraints.expand(height: double.infinity),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              // Padding(
              //padding: EdgeInsets.all(MediaQuery.of(context).size.height * 3 / 25), // Задайте нужное вам значение отступа
              //SizedBox(height: MediaQuery.of(context).size.height * 3 / 25),
              Container(
                  decoration: BoxDecoration(
                      color:Color.fromRGBO(51,115,125,5).withOpacity(0.6),
                    //   image: DecorationImage(
                    //   image: AssetImage(assets1),
                    //   fit: BoxFit.cover,
                    // ),
                      borderRadius: BorderRadius.circular(0),
                      border: Border.all(color: Colors.white.withOpacity(0.5), width: 0),
                    ),
                  child: FutureBuilder(
                      future: getData(),
                      builder: (context, snapshot) {
                        if (snapshot.connectionState == ConnectionState.done) {
                          return Align(
                            alignment: Alignment.topCenter,
                            child: Column(
                              children: [
                                currentWeather(Icons.wb_sunny_rounded,
                                    "${data!.tempC}", "Москва", "${data!.conditionText}","${data?.getFullConditionIconUrl()}"),
                                dopinformation("${data?.tempC}", "24", "1200", "16"),

                              ],
                            ),
                          );
                        }
                        return Container();
                      })),
              //),


            ],
          ),
        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          currentIndex: 0, // Укажите активный элемент здесь
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
        // BottomNavigationBar(
        //   backgroundColor: Color(0xff0c1241).withOpacity(0.1),
        //   elevation: 1.0,
        //   type: BottomNavigationBarType.fixed,
        //   // добавленное свойство
        //   currentIndex: 0,
        //   selectedItemColor: Colors.blue,
        //   unselectedItemColor: Colors.black,
        //   onTap: (index) {
        //     setState(() {
        //       switch (index) {
        //         case 0:
        //           Navigator.pushNamed(context, '/4');
        //           break;
        //         case 1:
        //           Navigator.pushNamed(context, '/2');
        //           break;
        //         case 2:
        //           Navigator.pushNamed(context, '/5');
        //           break;
        //         case 3:
        //           Navigator.pushNamed(context, '/7');
        //           break;
        //       }
        //     });
        //   },
        //
        //   items: const <BottomNavigationBarItem>[
        //     BottomNavigationBarItem(
        //       icon: Icon(
        //         Icons.ac_unit,
        //         color: Color(0xFF33737D),
        //       ),
        //       label: 'Погода',
        //     ),
        //     BottomNavigationBarItem(
        //       icon: Icon(
        //         Icons.add_a_photo,
        //         color: Color(0xFF33737D),
        //       ),
        //       label: 'Добавить',
        //     ),
        //     BottomNavigationBarItem(
        //       icon: Icon(
        //         Icons.add_outlined,
        //         color: Color(0xFF33737D),
        //       ),
        //       label: 'Создать образ',
        //     ),
        //     BottomNavigationBarItem(
        //       icon: Icon(
        //         Icons.accessibility,
        //         color: Color(0xFF33737D),
        //       ),
        //       label: 'Образы',
        //     ),
        //   ],
        // ),
      ),
    );
  }
}
