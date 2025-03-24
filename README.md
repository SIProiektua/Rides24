Github esteka: https://github.com/SIProiektua/Rides24

Taldeko Kideak: Ander Alvarez, Andoni Armendariz, Beñat Arruti, Maider Delera

Inplementatutako erabilpen kasuak: 
Driver: Create Ride
Traveler: Cancel Ride, Query Rides, Book Ridesç
No Registered: Register, Query Rides, Login

Internalizazioa: Bai, adaptatzeko erreztasuna dago beste hizkuntz lengoai batera aldatuko bagenu. Hizkuntz desberdinen araberako beharrezko aldaketak ezarriz

Bideoa:
https://upvehueus-my.sharepoint.com/:v:/g/personal/aalvarez186_ikasle_ehu_eus/EVdhkSyzqv5BowPi0erFk8gBLhtrBxsW-ZMoaLG_q92npw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=Cehs6F


1. Aurkitutako arazoak. Zeintzuk dira eduki dituzuen arazo garrantzitsuenak eta
nola bideratu dituzue.

Arazo larriena LOGIN-ean izan dugu informazioa ez zen modu egokian gordetzen email eta kontraseña jastozen ziren, kontraeña zuzena bada, sartzen zen. Baina gertatzen zen informazioa ez zela maneiatzen ondo, zeren eta, char array bat sotzen zen eta horri toString bat egiten genion [C@60f52a05 motatako stringa gordetzen, konpontzeko new String(arraya) egin behar zen.
Beste izndako arazoa hurrengokoa izan da: dataAccess-en datuak bikoiztu egiten ziren. Hori konpontzenko dataAccess klasean errepikatutako kode deia ezabatu egin genuen eta horren bidez informazioaren bikoizketa ezabatu egiten zen.
Beste arazorik larriena hurrengokoa da: bookRides klasean informazioa ez zen gordetzen modu egokian datu basean zeren eta falta zitzaion bi cast egitea, hurrengoko GUI-an sartzeko zeren eta ez ziren bihurtzen Ride klasera gero PaymentGUI-n agertzeko bidairen prezioa


StarUML+Argazkietan topatuko dituzu:
1. Erabilpen kasu hedatuen eredua.
StarUML
2. Domeinu eredu hedatua.
StarUML
3. Diseinatutako erabilpen kasuen UML sekuentzia diagramak.
EskakizunBilketaFluxu eta EskakizunBilketaFluxu2 pdf formatuan agertuko da
4. Diseinuzko klaseak
StarUML
5. Inplementazioaren iturburu-kodea formatu elektronikoan.
src karpetan

Erabilitako Orduak: 
Klase orduak: 4-5 ordu
Klase kanpo: 20 ordu
Guztira 24-25 ordu
