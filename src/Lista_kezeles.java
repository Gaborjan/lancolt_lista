
/*Két irányban láncolt lista adatszerkezet modellezése tömbök segítségével, demonstrációs célú funkciókkal


A program láncolt listát valósít meg, tömbök segítségével. A listában emberek nevét, lakcímét, munkahelyét, születési évét és fizetését tároljuk. A lista alap rendezettsége név szerinti legyen, illetve másodlagosan fizetésük szerint növekvő sorrendben is legyen láncolva. Az egyes adatokat tárolhatjuk külön tömbökben (név, cím, fizetés), feltehetjük, hogy a tárolandó adatok száma max. 20.
A program az alábbi funkciókkal rendelkezik:
1.)	Fájlműveletek, beállítások
	a.	Tesztadatok betöltése: egy teszt_adatok.csv fájlból töltsünk be teszt adatokat
	b.	A listában tárolt adatok kiírása .csv formátumban
	c.	.csv formátumban tárolt adatok betöltése listába
	d.	Beállítás: meg lehet adni, hogy a karbantartó műveletek esetén  szeretnénk-e látni a 3/c szerinti listát vagy nem (demonstrációs cél)
Feltesszük, hogy a fájlok nem kerülnek módosításra, abban megfelelő formában állnak rendelkezésre az adatok, ezt nem ellenőrizzük.
2.)	Karbantartás
	a.	Új ember felvétele a listára
	b.	Adott nevű ember megkeresése, adatainak kiírása
3.)	Lekérdezések
	a.	Névsor szerinti lista
	b.	Fizetés szerinti lista
	c.	Felvitel sorrendje szerinti lista – kiírjuk az adatokat és mögéjük írjuk ki az adott rendezettség szerinti mutatókat is,  demonstrációs céllal. A program 	kiírja a névsor szerinti sorrend első indexét (lista kezdete), az üres helyek listájának kezdetét, valamint a fizetés szerinti sorrend első indexét.
	d.	A listában tárolt emberek száma, üres helyek száma
	e.	Az emberek átlagéletkora
	f.	Az emberek átlagfizetése 
4.)	Kilépés

A program csak alap ellenőrzéseket végez, célja a lista adatszerkezet modellezése, működésének bemutatása tömbök segítségével.
*/

	import extra.*;


public class Lista_kezeles {
	
	static final String FOMENUPONTOK[] = {"1. Fájlműveletek, beállítások","2. Karbantartás","3. Lekérdezések","0. Program vége"};
	static final String FAJLMUVELETEK[] = {"1. Tesztadatok betöltése", "2. Lista mentése fájlba","3. Betöltés fájlból listába","4. Demonstrációs üzemmód beállítása","0. FŐMENÜ"};
	static final String KARBANTARTAS[] = {"1. Lista bővítése","2. Törlés a listából","3. Keresés a listában","0. FŐMENÜ"};
	static final String LEKERDEZESEK[] = {"1. Névsor szerinti lista","2. Fizetés szerinti lista","3. Felvitel sorrendje szerinti lista", "4. Lista elemszáma, üres helyek", 
				"5. Átlagéletkor","6. Átlagfizetés","0. FŐMENÜ"};
	
		
public static void main(String[] args) {
	int menuP=0;
		do {
			menuP=Kellekek.egyszeruMenu("Főmenü",FOMENUPONTOK, 4);
			switch (menuP) {
				case 1: //Főmenü/Fájlműveletek
					{
						do {
   						menuP=Kellekek.egyszeruMenu(Kellekek.ballevag(FOMENUPONTOK[0],3),FAJLMUVELETEK, 5);
   						switch (menuP) {
   							case 1: //Fájlműveletek/Tesztadatok betöltése
   							{
   								Kellekek.tajUzenet("Fájlműveletek/"+FAJLMUVELETEK[0], true);
   								menuP=99;
   								break;
   							} // Fájműveletek/Tesztadatok betöltése case ág
   							case 2: //Fájlműveletek/Lista mentése fájlba
   							{
   								Kellekek.tajUzenet("Fájlműveletek/"+FAJLMUVELETEK[1], true);
   								menuP=99;
   								break;
   							} // //Fájlműveletek/Lista mentése fájlba case ág
   							case 3: //Fájlműveletek/Betöltés fájlból listába
   							{
   								Kellekek.tajUzenet("Fájlműveletek/"+FAJLMUVELETEK[2], true);
   								menuP=99;
   								break;
   							} // Fájműveletek/Lista betöltése fájlból case ág
   							case 4: //Fájlműveletek/Drmonstrációs üzemmód
   							{
   								Kellekek.tajUzenet("Fájlműveletek/"+FAJLMUVELETEK[3], true);
   								menuP=99;
   								break;
   							} // Fájműveletek/Demonstrációs üzemmód case ág
   						}
						} while (menuP!=0); // FÖMENÜ/Fájlműveletek almenü
						menuP=99;
						break;
					} // Főmenü/Fájlműveletek case ág
				case 2: 
				{
					do {
						menuP=Kellekek.egyszeruMenu(Kellekek.ballevag(FOMENUPONTOK[1],3),KARBANTARTAS, 4);
						switch (menuP) {
							case 1: //Karbantartás/Lista bővítése
							{
								Kellekek.tajUzenet("Karbantartás/"+KARBANTARTAS[0], true);
								menuP=99;
								break;
							} // Karbantartás/Lista bővítése case ág
							case 2: //Karbantatás/Törlés a listáról
							{
								Kellekek.tajUzenet("Karbantartás/"+KARBANTARTAS[1], true);
								menuP=99;
								break;
							} // Karbantartás/Lista bővítése case ág
							case 3: //Karbantartás/Keresés a listában
							{
								Kellekek.tajUzenet("Karbantartás/"+KARBANTARTAS[2], true);
								menuP=99;
								break;
							} // Karbantartás/Keresés a listában
							case 4: //Tartalék
							{

								menuP=99;
								break;
							} // Tartalék
						}
					} while (menuP!=0); // FÖMENÜ/Karbantartás case ág
					menuP=99;
					break;
				} // Főmenü/Karbantartás case ág
				case 3: //Fömenü/Lekérdezések 
				{
					do {
						menuP=Kellekek.egyszeruMenu(Kellekek.ballevag(FOMENUPONTOK[2],3),LEKERDEZESEK, 7);
						switch (menuP) {
							case 1: //Lekérdezések/Névsor szerinti lista
							{
								Kellekek.tajUzenet("Lekérdezések/"+LEKERDEZESEK[0], true);
								menuP=99;
								break;
							} // Lekérdezések/Névsor szerinti lista case ág
							case 2: // Lekérdezések/Fizetés szerinti lista
							{
								Kellekek.tajUzenet("Lekérdezések/"+LEKERDEZESEK[1], true);
								menuP=99;
								break;
							} // Lekérdezések/Fizetés szerinti lista
							case 3: //Lekérdezések/Felvitel sorrendje szerinti lista
							{
								Kellekek.tajUzenet("Lekérdezések/"+LEKERDEZESEK[2], true);
								menuP=99;
								break;
							} // Lekérdezések/Felvitel sorrendje szerinti lista case ág
							case 4: //Lekérdezések/lista elemszáma, üres helyek
							{
								Kellekek.tajUzenet("Lekérdezések/"+LEKERDEZESEK[3], true);
								menuP=99;
								break;
							} // Lekérdezések/lista elemszáma, üres helyek case ág
							case 5: //Lekérdezések/Átlagéletkor
							{
								Kellekek.tajUzenet("Lekérdezések/"+LEKERDEZESEK[4], true);
								menuP=99;
								break;
							} // Lekérdezések/Átlagéletkor case ág
							case 6: //Lekérdezések/Átlag fizetés
							{
								Kellekek.tajUzenet("Lekérdezések/"+LEKERDEZESEK[5], true);
								menuP=99;
								break;
							} // Lekérdezések/Átlag fizetés case ág
						}
					} while (menuP!=0); // FÖMENÜ/Lekérdezések case ág
					menuP=99;
					break;
				} // Főmenü/Karbantartás case ág
			}
		}  while (menuP!=0);
		Kellekek.tajUzenet("PROGRAM VÉGE", false);
	} //main

} // Lista_kezeles class
