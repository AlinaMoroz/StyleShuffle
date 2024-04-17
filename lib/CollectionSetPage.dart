import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';

import 'package:provider/provider.dart';
import 'mainButton/CustomBottomNavigationBar.dart';
import 'modal/DBcloth.dart';
import 'modal/user.dart';

class CollectionAll extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _CollectionAll();


}

class _CollectionAll extends State<CollectionAll> {


  @override
  Widget build(BuildContext context) {
    var user = Provider.of<User_>(context); //получаем пользователя


    String assets1 = 'assets/images/base.jpg';

    Widget panelForPhoto(List<String> imageUrlList) {

      print(imageUrlList.length);


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
          child: FutureBuilder<List<DocumentSnapshot>>(
            future: getDocumentsByUid(user.uid),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return CircularProgressIndicator();
              } else if (snapshot.hasError) {
                return Text('Ошибка при получении данных');
              } else {
                // Получите список документов
                List<DocumentSnapshot> documents = snapshot.data!;

                return ListView.builder(
                  itemCount: documents.length,
                  itemBuilder: (context, index) {
                    // Получите данные для текущего документа
                    DocumentSnapshot document = documents[index];
                    String nameCollection = document['nameCollection'] as String;
                    List<String> links = List<String>.from(document['links'] as List<dynamic>);

                    return panelForPhoto(links);
                  },
                );
              }
            },
          )



        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          currentIndex: 3, // Укажите активный элемент здесь
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
