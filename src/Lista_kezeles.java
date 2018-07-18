
/*Két irányban láncolt lista adatszerkezet modellezése tömbök segítségével, demonstrációs célú funkciókkal


A program láncolt listát valósít meg, tömbök segítségével. A listában emberek nevét, lakcímét, munkahelyét, 
születési évét és fizetését tároljuk. A lista alap rendezettsége név szerinti legyen, illetve másodlagosan 
fizetésük szerint növekvő sorrendben is legyen láncolva. Az egyes adatokat tárolhatjuk külön tömbökben 
(név, cím, fizetés), feltehetjük, hogy a tárolandó adatok száma max. 20.
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
	c.	Felvitel sorrendje szerinti lista – kiírjuk az adatokat és mögéjük írjuk ki az adott rendezettség szerinti mutatókat is, 
	demonstrációs céllal. A program 	kiírja a névsor szerinti sorrend első indexét (lista kezdete), az üres helyek listájának kezdetét, 
	valamint a fizetés szerinti sorrend első indexét.
	d.	A listában tárolt emberek száma, üres helyek száma
	e.	Az emberek átlagéletkora
	f.	Az emberek átlagfizetése 
4.)	Kilépés

A program csak alap ellenőrzéseket végez, célja a lista adatszerkezet modellezése, működésének bemutatása tömbök segítségével.
*/


import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.time.LocalDate;
import java.util.Locale;

import extra.*;

public class Lista_kezeles {
	
	static final String FOMENUPONTOK[] = {"1. Fájlműveletek, beállítások","2. Karbantartás, keresés","3. Lekérdezések","0. Program vége"};
	static final String FAJLMUVELETEK[] = {"1. Tesztadatok betöltése", "2. Lista mentése fájlba","3. Betöltés fájlból listába","4. Demonstrációs üzemmód beállítása","0. FŐMENÜ"};
	static final String KARBANTARTAS[] = {"1. Lista bővítése","2. Törlés a listából","3. Keresés a listában","4. A teljes lista törlése","0. FŐMENÜ"};
	static final String LEKERDEZESEK[] = {"1. Névsor szerinti lista","2. Fizetés szerinti lista","3. Felvitel sorrendje szerinti lista", "4. Lista elemszáma, üres helyek", 
				"5. Átlagéletkor","6. Átlagfizetés","0. FŐMENÜ"};
	static final int MAXADAT=12; // Ennyi adatunk lehet maximum
	static int[] link_nev = new int[MAXADAT]; //a névsor szerinti linkek tömbje
	static int[] link_fiz = new int[MAXADAT]; // a fizetés szerinti linkek tömbje
	static String[] nev = new String[MAXADAT]; // nevek tárolása
	static String[] cim = new String[MAXADAT]; // címek tárolása
	static String[] szev = new String[MAXADAT]; // születési évek tárolása
	static double[] fiz = new double[MAXADAT]; // fizetések tárolása
	static final int NULL=999; // a lista vége érték
	static RuleBasedCollator myCollator = (RuleBasedCollator) Collator.getInstance(new Locale("hu", "HU")); // Magyar abc szerinti összehasonlítás definiálása
	static final String teszt_file_nev="teszt_adatok.csv"; // A tesztadatok itt vannak
	static int nev_elso = NULL; // A lista üres
	static int fiz_elso = NULL; // A lista üres
	static int ures_elso_n = 0; // Az első üres hely nésor szerint
	static int ures_elso_f = 0; // Az első üres hely fizetés szerint
	static final int[] EREDETISORTABLAZAT = {8,29,50,61,76,89}; // felvitel sorrendje szerinti táblázat elválasztó helyei
	static final String[] EREDETITABLAZATFEJLEC = {"T.INDEX","NÉV","CÍM","SZ.ÉV","FIZETÉS","LINK NÉV","LINK FIZ."}; // fejlécek
	static final int[] NEVSORTABLAZAT = {21,42,53}; // névsor szerinti táblázat elválasztó helyei
	static String[] NEVSORTABLAZATFEJLEC = {"NÉV","CÍM","SZ.ÉV","FIZETÉS"}; // fejlécek
	static final int[] BEALLITASTABLAZAT = {70}; // beállítástáblázat elválasztó helye
	static boolean uresHelyTorolve = true; //A felszabaduló helyen lévő adatokat töröljük-e vagy ne?- > Demonstrációs üzmód
	static boolean muveletUtanLista = true; //Karbantartó művelet után legyen-e automatikusan lista vagy ne? Demonstrációs üzmód

		
public static void main(String[] args) {
	int menuP=0;
	//Létrehozzunk a mutató tömböket, mindegyiket üres helyekkel feltöltve, illetve a többi tömböt is feltöltjük üres adatokkal 
	teljesListaTorles();
	//teszt_adat_betolt();
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
	            teszt_adat_betolt();
	            menuP=99;
	            break;
	         } // Fájműveletek/Tesztadatok betöltése case ág
	         case 2: //Fájlműveletek/Lista mentése fájlba
	         {
	            if (elemszam()==0)
	               Kellekek.hibaUzenet("A lista üres, nincs mit menteni.", true);
	            else
	               lista_mentese_fajlba();
	            menuP=99;
	            break;
	         } // //Fájlműveletek/Lista mentése fájlba case ág
	         case 3: //Fájlműveletek/Betöltés fájlból listába
	         {
	            betoltes_fajlbol_listaba();
	            menuP=99;
	            break;
	         } // Fájműveletek/Lista betöltése fájlból case ág
	         case 4: //Fájlműveletek/Drmonstrációs üzemmód
	         {
	            beallitasok();
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
	         menuP=Kellekek.egyszeruMenu(Kellekek.ballevag(FOMENUPONTOK[1],3),KARBANTARTAS, 5);
	         switch (menuP) {
	         case 1: //Karbantartás/Lista bővítése
	         {
	            if (elemszam()==MAXADAT) 
	            	Kellekek.hibaUzenet("A listában nincs több üres hely!", true);
	         	else { 
	         		lista_bovitese();
	         	}
	         	menuP=99;
	            break;
	         } // Karbantartás/Lista bővítése case ág
	         case 2: //Karbantatás/Törlés a listáról
	         {
	          if (nev_elso==NULL)
	         	 Kellekek.hibaUzenet("A lista üres!", true);
	          else {
	         	 torles_listarol();
	          }
	            menuP=99;
	            break;
	         } // Karbantartás/Lista bővítése case ág
	         case 3: //Karbantartás/Keresés a listában
	         {
	            if (nev_elso==NULL)
	            	Kellekek.hibaUzenet("A lista üres!",true);
	            else
	            	kereses();
	            menuP=99;
	            break;
	         } // Karbantartás/Keresés a listában
	         case 4: //Teljes lista törlése
	         {
	            if (elemszam()>0) {
	               System.out.println();
	               Kellekek.tajUzenet("FIGYELEM! A TELJES LISTÁT TÖRÖLNI AKARJA?", false);
	               if (Kellekek.igenNem("<I>gen/<N>em  : ")) {
	                  teljesListaTorles();
	                  Kellekek.tajUzenet("A lista minden eleme törölve lett.", true);
	               }
	               else
	                  Kellekek.tajUzenet("A lista nem került törlésre.", true);
	            }
	            else
	                  Kellekek.tajUzenet("A lista jelenleg is üres.", true);
	            menuP=99;
	            break;
	         } //teljes lista törlése
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
	            if (nev_elso!=NULL) lista_nevsor();
	            else Kellekek.tajUzenet("A lista üres!", true);
	            menuP=99;
	            break;
	         } // Lekérdezések/Névsor szerinti lista case ág
	         case 2: // Lekérdezések/Fizetés szerinti lista
	         {
	            if (nev_elso!=NULL) lista_fizetes();
	            else Kellekek.tajUzenet("A lista üres!", true);
	            menuP=99;
	            break;
	         } // Lekérdezések/Fizetés szerinti lista
	         case 3: //Lekérdezések/Felvitel sorrendje szerinti lista
	         {
	            lista_eredeti();
	            menuP=99;
	            break;
	         } // Lekérdezések/Felvitel sorrendje szerinti lista case ág
	         case 4: //Lekérdezések/lista elemszáma, üres helyek
	         {
	            elemsz_ures_hely();
	            menuP=99;
	            break;
	         } // Lekérdezések/lista elemszáma, üres helyek case ág
	         case 5: //Lekérdezések/Átlagéletkor
	         {
	            atlag_eletkor();
	            menuP=99;
	            break;
	         } // Lekérdezések/Átlagéletkor case ág
	         case 6: //Lekérdezések/Átlag fizetés
	         {
	         	atlag_fizetes();
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

   
   static void teljesListaTorles() { // Teljesen kitörlünk minden adatot, mutatókat nullázzuk, felépítjük üres helyek listáját
      nev_elso = NULL; // A lista üres
      fiz_elso = NULL; // A lista üres
      ures_elso_n = 0; // Az első üres hely nésor szerint
      ures_elso_f = 0; // Az első üres hely fizetés szerint
      for (int i=0;i<MAXADAT-1;i++) { // Üres helyek listáját felépítjük
         link_nev[i]=i+1;
         link_fiz[i]=i+1;
      }
      link_nev[MAXADAT-1]=NULL; // Utolsó üres hely nem mutat sehová
      link_fiz[MAXADAT-1]= NULL; // Utolsó üres hely nem mutat sehová

      for (int i=0;i<MAXADAT;i++) { // Adatokat tároló tömbök nullázása, törlése
         nev[i]="";
         cim[i]="";
         szev[i]="";
         fiz[i]=0;
      }
   } // teljesListaTorles metódus
   
   static void teszt_adat_betolt() { // A tesztadatokat betöltő metódus.
		teljesListaTorles(); // Törölni kell a listát
      if ((adat_betolt_fajlbol(teszt_file_nev)) && (muveletUtanLista)) lista_eredeti(); // Fájlbetöltő meghívása teszt fájllal és ha kell akkor lista 
	}
   
   static boolean adat_betolt_fajlbol(String FNev) { // Az FNev paraméterben megadott fájlt betölti a listába
      String egySor; // egy sor a fájlból
      String adatok[]; // ebbe kerülnek szétdarabolásra az adatok egySor-ból
      
      try {
         RandomAccessFile fajl = new RandomAccessFile(FNev,"r");
         teljesListaTorles(); // Törölni kell a listát
         egySor=fajl.readLine();
         while (egySor!=null) { //Amíg nem fájl vége
            adatok=egySor.split(";"); //beolvasott sor darabolása
            beszur(adatok[0],adatok[1],adatok[2],adatok[3]); // az adatok beszúrása a listába
            egySor=fajl.readLine();
         } // while
         fajl.close();
         Kellekek.tajUzenet("Adatok betöltése sikeres! (Fájlnév: "+FNev+") Betöltött elemek száma: "+elemszam()+ " darab",true);
         return true;
      } //try
      catch (IOException e ) {
         Kellekek.hibaUzenet("A(z) "+FNev+" fájlt nem sikerült megnyitni!", true);
         return false;
      } 
   }
   
   static void lista_mentese_fajlba() { //A lista aktuális tartalmát .csv fájlba menti
      String fNev="";
      RandomAccessFile fajl;
      String egySor="";
      
      System.out.println();
      fNev=extra.Console.readLine("Kérem a fájl nevét, amibe a listát szeretné menteni, kiterjesztést ne adjon meg: (Kilépés=0) ");
      if (!fNev.equals("0")) { // Ha nem kilépés
      	try {
      		fajl= new RandomAccessFile(fNev+".csv","rw"); //Létrehozzuk a fájlt
            for (int i=0;i<elemszam();i++) { //Végigmegyünk a listán
            	egySor=egySor+nev[i]+";"+cim[i]+";"+szev[i]+";"+fiz[i]+"\n"; // Egy sor összeállítása
               fajl.writeBytes(egySor); // Kiírás
               egySor="";
             }
             fajl.close();
             Kellekek.tajUzenet("A lista a(z) "+fNev+".csv fájlba kimentve. Mentett elemek száma: "+elemszam()+" darab.", true);
      	}
         catch (IOException e) {
         	System.out.println("Hiba történt fájlművelet közben!");
            extra.Console.pressEnter();
         }
      }
   } // lista_mentese_fajlba metódus 
	
	static void betoltes_fajlbol_listaba() { // Korábban mentett lista adatok betöltése a listába
	   String fNev="";
	   
	   System.out.println();
	   if (Kellekek.igenNem("FIGYELEM! A listában lévő adatok felülírásra kerülnek a betölteni kívánt adatokkal. Folytatja? (<I>gen/<N>em) ")) {
	      fNev=extra.Console.readLine("Kérem a fájl nevét, kiterjesztés nélkül. A fájlnak 'csv' (elválasztó: ';') formátumúnak kell lennie. (Kilépés=0) ");
	      if (!fNev.equals("0")) {
	      	//Meghavjuk a betöltő metódust, a megadott fájlnévvel, ha kell akkor kiírjuk a listát
            if ((adat_betolt_fajlbol(fNev+".csv")) && (muveletUtanLista)) lista_eredeti();
	      }
	      else 
	         Kellekek.tajUzenet("A betöltés megszakítva.", true);
	   }
	   else
	      Kellekek.tajUzenet("A betöltés megszakítva.", true);
	} // betoltes_fajlbol_listaba metódus
	
	
	static void aktualisBeallitasok() { //A demonstrációs lehetőségek beállításait írja ki
	   Kijelzo Screen1= new Kijelzo(7,90);
	   String[] fejlec = {""};
	   System.out.println();
      Screen1.kozepIr(0, 1, 80, "A K T U Á L I S    B E Á L L Í T Á S O K");
      Screen1.tablazat(1, 1, 80, 2,BEALLITASTABLAZAT,fejlec);
      Screen1.irXY(2,2,"Listából törölt elemek adatai is törlésre kerülnek");
      Screen1.irXY(4,2,"Lista műveletek után a lista aktuális állapotának megjelenítése");
      if (uresHelyTorolve) 
         Screen1.kozepIr(2,BEALLITASTABLAZAT[0]+1,80,"IGEN");
      else
         Screen1.kozepIr(2,BEALLITASTABLAZAT[0]+1,80,"NEM");
      if (muveletUtanLista)
         Screen1.kozepIr(4,BEALLITASTABLAZAT[0]+1,80,"IGEN");
      else
         Screen1.kozepIr(4,BEALLITASTABLAZAT[0]+1,80,"NEM");
      Screen1.kiir();
	} // akutalisBeallitasok metódus
	
	
	static void beallitasok() { // A demonstrációs opciók ki-/bekapcsolása
	   aktualisBeallitasok();
      if (Kellekek.igenNem("Szeretne a beállításokon változtatni? (<I>gen/<N>em)")) {
         uresHelyTorolve=Kellekek.igenNem("Listából törölt elemek adatai törlésere kerüljenek? <I>gen/<N>em : ");
         muveletUtanLista=Kellekek.igenNem("Lista műveletek után a lista aktuális állapota megjelenjen? <I>gen/<N>em : ");
         aktualisBeallitasok();
         extra.Console.pressEnter();
      }
	} // beallitasok metódus
	
	static int listan_vane(String kNev) { //Segédmetódus, ami eldönti egy kNev nevű ember a listán van-e és visszaadja hanyadik névsor szerint
		// Ha nincs a listán ilyen nevű, akkor NULL értékkel tér vissza
		int akt;
		
		if (nev_elso==NULL) // A lista üres
			return NULL;
		else
		{
			akt=nev_elso; 
			while ((akt!=NULL) && (!nev[akt].equals(kNev))) // Végiglépegetünk a listán, amíg vége vagy meg nem találjuk a keresett nevet. 
				akt=link_nev[akt];
			if (akt==NULL)
				return NULL;
			else 
				return akt;
		}
	} // listan_vane metódus
	
	static void lista_bovitese() { // Lista bővítése új elemmel
		String uNev;
		String uCim;
		String uSzev;
		int s;
		double uFiz;
		boolean kilep=false;
		int hol;
			
   	do { //Ciklus amíg nem lép ki
   		System.out.println();
   		uNev=extra.Console.readLine("Kérem az új nevet (Kilépés=0): ");
   		if (uNev.equals("0")) { // Kilépés
   			kilep=true;
   			continue;
   		}
   		else {
   			hol=listan_vane(uNev); //Megnézzük van-e már ilyen nevű ember
   			if (hol!=NULL) // Ha van ilyen nevű
   				Kellekek.hibaUzenet("Ilyen nevű ember már van a listában!", true);
   			else { // Nincs még ilyen nevű, bekérjük a többi adatot
   				uCim=Console.readLine("Kérem a(z) " + uNev+" lakcímét :");
   				s=Kellekek.egesz_Beolvas("Kérem a(z) "+uNev+" születési évét (1900-2018): ", 1900, 2018, "Helytelen adat!");
   				uSzev=Integer.toString(s);
   				uFiz= (int) Kellekek.egesz_Beolvas("Kérem a(z) "+uNev+" fizetését (1-1.000.000): ", 1, 1000000, "Helytelen adat!");
   				//uFiz=s; 
   				beszur(uNev,uCim, uSzev, Double.toString(uFiz)); // A megadott adatokkal bővítjük a listát
   				Kellekek.tajUzenet("Az új személy felvéve a listára.", true);
   				if (muveletUtanLista) lista_eredeti(); //Ha a beállítás az, hogy kiírást kér, akkor kiírjuk a lista aktuális állapotát
   				if (elemszam()==MAXADAT) { //Ha az utolsó hely is betelt tájékoztatás és kilépünk
   					Kellekek.tajUzenet("FIGYELEM! A lista betelt, nem tud több személyt felvenni.", true);
   					kilep=true;
   				}
   			} // adatbekérés és felvétel
   		} // Nem lépett ki a név megadásnál
   	} while (!kilep); 
	} //lista bővítése metódus
	
	static void torles_listarol() { //Megadott nevű ember törlése a listáról
		String tNev;
		boolean kilep=false;
		int hol;

		do {
			System.out.println();
			tNev=extra.Console.readLine("Kérem a törlendő személy nevét (Kilépés=0): ");
			if (tNev.equals("0")) { // Ha nem lépünk ki
				kilep=true;
				continue;
			}
			else {
				hol=listan_vane(tNev); //Megnézzük van-e ilyen nevű embr
				if (hol==NULL) // Ha nincs, táj.
					Kellekek.hibaUzenet("Ilyen nevű ember nincs a listában!", true);
				else { //Van ilyen nevű ember
					if (Kellekek.igenNem("Biztos törli? (<I>gen/<N>em) ")) { // Ha biztos törli
						listarol(tNev); // listáról törlés
						Kellekek.tajUzenet("A(z) "+tNev+" nevű személy törölve lett a listáról.", true);
						if (muveletUtanLista) lista_eredeti(); //Ha kell, akkor kiírjuk a listát képernyőre
						if (elemszam()==0) {
							Kellekek.tajUzenet("FIGYELEM! A lista kiürült, nem tud több embert törölni!", true);
	   					kilep=true;
						}
					}
				} // Törlés ág
			} // Nem kilépés ág
		} while (!kilep); 
	} // törlés listaról metódus
	
	
	static void kereses() { // Adott nevű ember adatainak kiírása
		String knev;
		int hol;
		Kijelzo Screen1= new Kijelzo(7,80);
		
		
		do { //Amíg nem akar kilépni
			System.out.println();
			knev=extra.Console.readLine("Kérem a keresett nevet (Kilépés=0): ");
			if (!knev.equals("0")) { //Nem kilépés
				hol=listan_vane(knev); // Megkeressük van-e ilyen nevű
				if (hol==NULL) // Nincs a listán ilyen
					Kellekek.tajUzenet("Ilyen nevű személy nincs a listában", true);
				else { // Van ilyen nevű, kiírjuk az adatait
					Screen1.torol();
					Screen1.tablazat(2, 1, 69, 2,NEVSORTABLAZAT,NEVSORTABLAZATFEJLEC);
					Screen1.kozepIr(1, 1, 69, " A    K E R E S E T T    S Z E M É L Y    A D A T A I");
					Screen1.irXY(5, 2, Kellekek.jobblevag(nev[hol],20));
					Screen1.irXY(5, 23, Kellekek.jobblevag(cim[hol],20));
					Screen1.irXY(5, 47, Kellekek.jobblevag(szev[hol],4));
					Screen1.irXY(5, 55, String.format("%,10.0f Ft", fiz[hol]));
					Screen1.kiir();
					extra.Console.pressEnter();
				} // Kiírás
			} //Nem kilépés ág
		} while (!knev.equals("0"));
	} // kereses() metódus
	

	static void lista_nevsor() { //Névsor szerint kiírja a listán lévő elemeket
		int akt; //Aktuális elem mutatója
		int j=4;
		Kijelzo Screen1= new Kijelzo((elemszam()*2)+4,80);
		
		akt=nev_elso; // név szerinti lista elejére állunk
		Screen1.torol();
		Screen1.tablazat(1, 1, 69, elemszam()+1,NEVSORTABLAZAT,NEVSORTABLAZATFEJLEC);
		Screen1.kozepIr(0, 1, 70,"N É V S O R    S Z E R I N T I    L I S T A");
		while (akt!=NULL) { //Amíg nem a lista végén vagyunk
			Screen1.irXY(j, 2, Kellekek.jobblevag(nev[akt],20));
	      Screen1.irXY(j, 23, Kellekek.jobblevag(cim[akt],20));
	      Screen1.irXY(j, 47, Kellekek.jobblevag(szev[akt],4));
	      Screen1.irXY(j, 55, String.format("%,10.0f Ft", fiz[akt]));
			j+=2;
			akt=link_nev[akt]; // Lépés a követő elemre
		} 
		Screen1.kiir();
		extra.Console.pressEnter();
	} // lista_nevsor metódus
	
	static void lista_fizetes() { // Fizetés szerint emelkedő sorrendben listázza a lista elemeit
		int akt; //Aktuális elem mutatója
		int j=4;
		Kijelzo Screen1= new Kijelzo((elemszam()*2)+4,80);
		
		akt=fiz_elso; // fizetés szerinti lista elejére állunk
		Screen1.torol();
		Screen1.irXY(0, 12, " F I Z E T É S    S Z E R I N T I    L I S T A");
		Screen1.tablazat(1, 1, 69, elemszam()+1,NEVSORTABLAZAT,NEVSORTABLAZATFEJLEC);
		while (akt!=NULL) { //Amíg nem a lista végén vagyunk
			Screen1.irXY(j, 2, Kellekek.jobblevag(nev[akt],20));
	      Screen1.irXY(j, 23, Kellekek.jobblevag(cim[akt],20));
	      Screen1.irXY(j, 47, Kellekek.jobblevag(szev[akt],4));
	      Screen1.irXY(j, 55, String.format("%,10.0f Ft", fiz[akt]));
			j+=2;
			akt=link_fiz[akt]; // Lépés a követő elemre
		} 
		Screen1.kiir();
		extra.Console.pressEnter();
	} // lista_fizetes metódus
	//static final int[] EREDETISORTABLAZAT = {8,29,50,61,76,89};
	static void lista_eredeti() { // Felvitel sorrendje szerinti lista, illetve minden a láncolt listával kapcsolatos egyéb infó
		Kijelzo Screen1= new Kijelzo(30,130);
		
		Screen1.torol();
		Screen1.tablazat(1, 15, 103, 13,EREDETISORTABLAZAT,EREDETITABLAZATFEJLEC);
		Screen1.kozepIr(0, 15, 118,"F E L V I T E L    S O R R E N D J E    S Z E R I N T I    L I S T A");
		Screen1.irXY(7, 0, "START NÉV : "+ nev_elso);
		Screen1.irXY(11, 0, "ÜRES NÉV  : "+ ures_elso_n);
		Screen1.irXY(15, 0, "START FIZ : "+ fiz_elso);
		Screen1.irXY(19, 0, "ÜRES FIZ  : "+ures_elso_f);
		for (int i=0,j=4;i<=nev.length-1;i++,j=j+2) {
         Screen1.irXY(j, 16, String.format("%6d",i));
         Screen1.irXY(j, 24, Kellekek.jobblevag(nev[i],20));
         Screen1.irXY(j, 45, Kellekek.jobblevag(cim[i],20));
         Screen1.irXY(j, 69, Kellekek.jobblevag(szev[i],4));
         Screen1.irXY(j, 77, String.format("%,10.0f Ft", fiz[i]));
         Screen1.irXY(j, 98, String.format("%4d", link_nev[i]));
         Screen1.irXY(j, 111, String.format("%4d", link_fiz[i]));
      }
		Screen1.kiir();
		extra.Console.pressEnter();
	} //lista_eredeti metódus
	
	public static int elemszam() { // Visszaadja hány eleme van a listának
		int db=0; //elemszám számlálója
		int akt=nev_elso; // aktuális elem mutatója
		while (akt!=NULL) {
			db++; // számláló növelése
			akt=link_nev[akt]; // következő elemre lépés
		}
		return db;
	} // elemszam metódus
	
	public static void elemsz_ures_hely() { // Kiiírja az lista elemeinek számát és az üres helyek számát
		if (elemszam()==0) Kellekek.tajUzenet("A lista üres, szabad helyek száma: "+MAXADAT, true);
		else
			Kellekek.tajUzenet(" A listában "+elemszam()+ " elem van, az üres helyek száma: "+(MAXADAT-elemszam()), true);
	} // elemsz_ures_hely metódus
	
	public static void atlag_fizetes() { // Kiszámolja a listában lévő emberek átlafizetését és kiírja a konzolra
		double fizetesek_osszege=0;
		String atlag="";
		int akt=fiz_elso; // aktuális elem mutatója
		if (akt==NULL)
			Kellekek.tajUzenet("A lista üres, így az átlagfizetés nem értelmezhető.", true);
		else { 
			while (akt!=NULL) {
				fizetesek_osszege+=fiz[akt]; // számláló növelése
				akt=link_fiz[akt]; // következő elemre lépés
			}
			atlag=String.format("%,10.2f Ft", (fizetesek_osszege)/elemszam());
			Kellekek.tajUzenet("A listában lévő emberek átlagfizetése: "+atlag, true);
		}
	} // atlag_fizetes metódus
	
	public static void atlag_eletkor() { //Kiszámolja a listában lévő emberek átlagéletkorát és kiírja konzolra 
		LocalDate ma = LocalDate.now();
		int eletKor;
		double eletKorOssz=0;
		String atlagEletkor="";
		int akt=nev_elso;
		
		if (akt==NULL) // A lista üres
			Kellekek.tajUzenet("A lista üres, így az átlagéletkor nem értelmezhető.", true);
		else {
			while (akt!=NULL) { // Végigmegyünk a listán
				eletKor=ma.getYear()-Integer.valueOf(szev[akt]);
				eletKorOssz+=eletKor;
				akt=link_nev[akt];
			}
			atlagEletkor=String.format("%,4.2f év", (eletKorOssz/elemszam()));
			Kellekek.tajUzenet("A listában lévő emberek átlagéletkora "+atlagEletkor, true);
		}
	} // atlag_eletkor metódus
	
	public static boolean beszur(String a_nev,String a_cim, String a_szev, String a_fiz) { 
		// A paraméterként kapott adatokkal egy új elemet szúr be a listára
		// A visszaadott érték igaz, ha a beszúrás sikerült, hamis ha nem
		
		int hely; // Annak az elemnek a helye, amely után be kell szúrni az új nevet
		int akt; // Az aktuális elem mutatója
		int elozo; // Az aktuálist megelőző elem mutatója
		int uj; //Az új elem mutatója
		
		if (ures_elso_n==NULL) return false; // Ha nincs több üres hely, akkor azonnal visszatérünk a metódusból
		//Elsőként megkeressük a helyet, ahová be kell szúrni az új elemet névsor szerint
		hely=NULL;
		if (nev_elso==NULL) hely=NULL; //Ha üres a lista
		else {// A lista min. 1 elemet tartalmaz
			if (myCollator.compare(a_nev, nev[nev_elso])<0) hely=NULL; // A lista első eleme lesz az új név
			else { // A lista közepébe kell beszúrni
				elozo=nev_elso; // Előzőbe eltesszük az első helyet
				akt=link_nev[nev_elso]; // Aktuális a második hely
				while ((akt!=NULL) && (hely==NULL)) { // ciklus, amíg nem lista vége és nincs meg a keresett hely
					if (myCollator.compare(a_nev, nev[akt])<0) // Aktuális név és új név összehasonlítása, megtaláltuk-e a helyet?
						hely=elozo; //A hely az aktuális előtti legyen e mögé fogunk beszúrni és ciklusból ki fogunk lépni megvan az új elem helye
					elozo=akt; //Előző legyen az aktuális
					akt=link_nev[akt]; // Aktuális legyen a következő
				} // helykereső ciklus vége
				if ((hely==NULL) && (akt==NULL)) // Az utolsó helyre kell beszúrni
					hely=elozo; //A hely a lista utolsó eleme, az új elem lesz meajd a legutolsó
			}
		} // Helykeresés vége
		
		//Itt jön a beszúrás a megkeresett helyre 
		uj=ures_elso_n; // Az új hely lesz első név szerint üres hely
		ures_elso_n=link_nev[ures_elso_n]; // A következő szabad hely állítása
		// Adatokat tartalmazó tömbök uj. helyére bemásoljuk az adatokat
		nev[uj]=a_nev;
		cim[uj]=a_cim;
		szev[uj]=a_szev;
		fiz[uj]=Double.parseDouble(a_fiz);
		if (hely==NULL) { // Ha az elso helyre történt a beszúrás
				link_nev[uj]=nev_elso; // az új elem az első
				nev_elso=uj; // az új elem az lista eleje
			}
			else { 
				link_nev[uj]=link_nev[hely]; //Az új elem követője a most már az új elem elé kerülő eddigi elem követője
				link_nev[hely]=uj; // Ami mögé beszúrtunk az után pedig jöjjön az új elem
			}
		
		//Megkeressük a fizetés sorrendje szerinti helyet is és a mutatók átállításával beszúrjuk az új
		//elemet a helyére. A keresés és beszúrás szinte teljesen azonos a név szerinti beszúrással.
		hely=NULL; 
   	if (fiz_elso==NULL) hely=NULL; //Ha üres a lista
   	else {// A lista min. 1 elemet tartalmaz
   		if (Double.parseDouble(a_fiz)<fiz[fiz_elso]) hely=NULL; // Ha pont a lista első eleme lesz az új elem
   		else { // Lista közepébe kell beszúrni
   			elozo=fiz_elso; // Előzőbe eltesszük az első helyet
   			akt=link_fiz[fiz_elso]; //Az aktuális a második hely
   			while ((akt!=NULL) && (hely==NULL)) { // ciklus, amíg nem lista vége és nincs meg a keresett hely
   				if (Double.parseDouble(a_fiz)<fiz[akt]) // Aktuális fizetés és új fizetés összehasonlítása, megtaláltuk-e a helyet?
   					hely=elozo; //A hely az aktuális előtti legyen e mögé fogunk beszúrni és ciklusból ki fogunk lépni megvan az új elem helye
   				elozo=akt; //Előző legyen az aktuális
   				akt=link_fiz[akt]; // Aktuális legyen a következő
   			}
   			if ((hely==NULL) && (akt==NULL)) // Az utolsó helyre kell beszúrni
   				hely=elozo; //A hely a lista utolsó eleme, az új elem lesz meajd a legutolsó
   		} 
   	} // Keresés vége
   	
   	//Itt jön a beszúrás a megkeresett helyre a fizetés szerint 
   	uj=ures_elso_f; // Az új hely lesz első fizetés szerint üres hely
     	ures_elso_f=link_fiz[ures_elso_f]; // A következő szabad hely állítása
     	if (hely==NULL) { // Ha az elso helyre kell beszúrni
     		link_fiz[uj]=fiz_elso; // Az új elem az első
     		fiz_elso=uj; // Az új elem az lista eleje
     	}
     	else {
     		link_fiz[uj]=link_fiz[hely]; //Az új elem követője a most már az új elem elé kerülő eddigi elem követője
     		link_fiz[hely]=uj; // Ami mögé beszúrtunk az után pedig jöjjön az új elem
     	}
		return true; // A beszúrás sikeres volt, igaz értékkel térünk vissza
	} //beszúr metótus

	static boolean listarol(String kit) { //A paraméterben megkapott nevű embert (elemet) törli a listából
		int hely; //Az a hely, ahonnan törölni kell
		int elozo=0; // Annak a helynek az előzője, amit törölni kell
		int akt; // aktuális hely
		int koveto; // aktualis követője
			
		//A fizetés szerinti láncból törölni kell az elemet		
		hely=NULL;
		
		if (fiz_elso==NULL) {// Ha a lista üres, kilépünk a metódusból
			return false;
		}
		else { // A lista min. egy elemet tartalmaz
			if (kit.equals(nev[fiz_elso])) { // A törlendő éppen az első
				hely=fiz_elso; //Az első helyről kell törölni
				elozo=NULL; // Az első előtt nincs semmi
			}
			else { //Nem az első helyen van a törlendő elem
				akt=fiz_elso; // Aktuális legyen az első
				koveto=link_fiz[fiz_elso]; // Aktuális követője (második)
				while ((koveto!=NULL) && (hely==NULL)) { // Ciklus amíg nem értünk a lista végére, vagy nincs meg a keresett elem
					if (kit.equals(nev[koveto])) { // megvan a keresett elem?
						hely=koveto; // Innen kell törölni
						elozo=akt; // a törlendő hely előzője
					}
					akt=koveto; // aktuális állítása a következő elemre
					koveto=link_fiz[koveto]; // következő legyen a kovetkező utáni
				} //while
				if ((koveto==NULL) && (hely==NULL)) hely=NULL; //nincs meg a keresett elem
			}
			//Itt kezdődik maga a törlés
			if (hely==NULL) //Ha nincs meg a törlendő elem
				return false;
			else 
			{
				if (elozo==NULL) { // az első a törlendő elem
					if (uresHelyTorolve) fiz[fiz_elso]=0;
					fiz_elso=link_fiz[fiz_elso]; //Az első elem ezentúl a törlendő elem
				}
				else
					link_fiz[elozo]=link_fiz[hely]; //A törlendő elem "kikerülése"
				link_fiz[hely]=ures_elso_f; //A törölt elem mutasson a mostani első üres helyre
				ures_elso_f=hely; // Az első üres hely a törölt elem helye
			}
			if (uresHelyTorolve)  //Ha a felhasználó kérte, akkor az adat tömböt is töröljük, vagyis 0 Ft-ra állítjuk 
				fiz[hely]=0;	
		}	
		
		hely=NULL;
		if (nev_elso==NULL) {// Ha a lista üres, akkor visszatérünk a metódusból hamis értékkel
			return false;
		}
		else { // A lista min. egy elemet tartalmaz
			if (myCollator.compare(kit, nev[nev_elso])==0) { // A törlendő éppen az első elem
				hely=nev_elso; //Az első helyről kell törölni
				elozo=NULL; // Az első előtt nincs semmi
			}
			else { //Nem az első helyen van a törlendő elem
				akt=nev_elso; // Aktuális legyen az első
				koveto=link_nev[nev_elso]; // Aktuális követője (második)
				while ((koveto!=NULL) && (hely==NULL)) { // ciklus amíg nem érünk a lista végére, vagy nincs meg a keresett elem
					if (myCollator.compare(kit, nev[koveto])==0) { // Összehasonlítjuk az aktuális elemet a törlendő elemmel
						hely=koveto; // Innen kell törölni
						elozo=akt; // A törlendő hely előzője
					}
					akt=koveto; // aktuális állítása
					koveto=link_nev[koveto]; // következő legyen a kovetkező utáni
				} //while
				if ((koveto==NULL) && (hely==NULL)) hely=NULL; //nincs meg a keresett elem
			}
			
			//Itt kezdődik maga a törlés
			if (hely==NULL) //Ha nincs meg a törlendő elem hamis értékkel visszatérünk
				return false;
			else 
			{
				if (elozo==NULL) // Az első a törlendő elem
					nev_elso=link_nev[nev_elso]; //Az első elem ezentúl a törlendő elem
				else // Lista belsejében van a törlendő elem
					link_nev[elozo]=link_nev[hely]; //A törlendő elem "kikerülése"
				link_nev[hely]=ures_elso_n; //A törölt elem mutasson a mostani első üres helyre 
				ures_elso_n=hely; // Az első üres hely a törölt elem helye
				if (uresHelyTorolve) { // Ha a felhasználó kérte, akkor az adat tömböket is töröljük - egyébként ott még megmaradnak a törölt adatok, de
					// ezek nem érhetőek el, mivel a tömb elem már üresként van nyilvántartva.
					nev[hely]="";
					cim[hely]="";
					szev[hely]="";
				}
				return true;
			}
		} // keresés és törlés név szerint
		
		
	} // listaról metódus

} // Lista_kezeles class
