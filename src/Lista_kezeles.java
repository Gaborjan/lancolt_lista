
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
	
	static final String FOMENUPONTOK[] = {"1. Fájlműveletek, beállítások","2. Karbantartás","3. Lekérdezések","0. Program vége"};
	static final String FAJLMUVELETEK[] = {"1. Tesztadatok betöltése", "2. Lista mentése fájlba","3. Betöltés fájlból listába","4. Demonstrációs üzemmód beállítása","0. FŐMENÜ"};
	static final String KARBANTARTAS[] = {"1. Lista bővítése","2. Törlés a listából","3. Keresés a listában","0. FŐMENÜ"};
	static final String LEKERDEZESEK[] = {"1. Névsor szerinti lista","2. Fizetés szerinti lista","3. Felvitel sorrendje szerinti lista", "4. Lista elemszáma, üres helyek", 
				"5. Átlagéletkor","6. Átlagfizetés","0. FŐMENÜ"};
	static final int MAXADAT=12; // Ennyi adatunk lehet maximum
	static int[] link_nev = new int[MAXADAT];
	static int[] link_fiz = new int[MAXADAT];
	static String[] nev = new String[MAXADAT];
	static String[] cim = new String[MAXADAT];
	static String[] szev = new String[MAXADAT];
	static double[] fiz = new double[MAXADAT];
	static final int NULL=999;
	static RuleBasedCollator myCollator = (RuleBasedCollator) Collator.getInstance(new Locale("hu", "HU"));
	static final String teszt_file_nev="teszt_adatok.csv";
	static int nev_elso = NULL; // A lista üres
	static int fiz_elso = NULL; // A lista üres
	static int ures_elso_n = 0; // Az első üres hely nésor szerint
	static int ures_elso_f = 0; // Az első üres hely fizetés szerint
	static final int[] EREDETISORTABLAZAT = {8,29,50,61,76,89};
	static final int[] NEVSORTABLAZAT = {21,42,53};
	
		
public static void main(String[] args) {
	int menuP=0;
	//Létrehozzunk a mutató tömböket, mindegyiket üres helyekkel feltöltve, illetve a többi tömböt is feltöltjük 
	for (int i=0;i<MAXADAT-1;i++) {
		link_nev[i]=i+1;
		link_fiz[i]=i+1;
	}
	link_nev[MAXADAT-1]=NULL;
	link_fiz[MAXADAT-1]= NULL;

	for (int i=0;i<MAXADAT;i++) {
		nev[i]="";
		cim[i]="";
		szev[i]="";
		fiz[i]=0;
	}
	
	teszt_adat_betolt();
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
	            lista_nevsor();
	            menuP=99;
	            break;
	         } // Lekérdezések/Névsor szerinti lista case ág
	         case 2: // Lekérdezések/Fizetés szerinti lista
	         {
	            lista_fizetes();
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
	         	altag_fizetes();
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
	
	static void teszt_adat_betolt() {
		String egySor;
		String adatok[];
		try {
         RandomAccessFile fajl = new RandomAccessFile(teszt_file_nev,"r");
         egySor=fajl.readLine();
         while (egySor!=null) { 
            adatok=egySor.split(";");
            beszur(adatok[0],adatok[1],adatok[2],adatok[3]);
            egySor=fajl.readLine();
         } // while
         fajl.close();
         Kellekek.tajUzenet("Teszt adatok betöltése sikeres!",true);
      } //try
      catch (IOException e ) {
         Kellekek.hibaUzenet("A teszt adatokat tartalmazó fájlt nem sikerült megnyitni!", true);
      }   
	}
	
	static void lista_nevsor() {
		int akt; //Aktuális elem mutatója
		int i=0;
		int j=4;
		Kijelzo Screen1= new Kijelzo((elemszam()*2)+4,80);
		
		akt=nev_elso;
		Screen1.torol();
		Screen1.irXY(0, 13, " N É V S O R    S Z E R I N T I    L I S T A");
		Screen1.tablazat(1, 1, 69, elemszam()+1,NEVSORTABLAZAT);
		Screen1.irXY(2,10,"NÉV");
		Screen1.irXY(2,31,"CÍM");
		Screen1.irXY(2,47,"SZ.ÉV");
		Screen1.irXY(2,59, "FIZETÉS");
		while (akt!=NULL) { //Amíg nem a lista végén vagyunk
			Screen1.irXY(j, 2, Kellekek.jobblevag(nev[akt],20));
	      Screen1.irXY(j, 23, Kellekek.jobblevag(cim[akt],20));
	      Screen1.irXY(j, 47, Kellekek.jobblevag(szev[akt],4));
	      Screen1.irXY(j, 55, String.format("%,10.0f Ft", fiz[akt]));
			j+=2;
			i++;
			akt=link_nev[akt]; // Lépés a követő elemre
		} 
		Screen1.kiir();
		extra.Console.pressEnter();
	}
	
	static void lista_fizetes() {
		int akt; //Aktuális elem mutatója
		int j=4;
		Kijelzo Screen1= new Kijelzo((elemszam()*2)+4,80);
		
		akt=fiz_elso;
		Screen1.torol();
		Screen1.irXY(0, 12, " F I Z E T É S    S Z E R I N T I    L I S T A");
		Screen1.tablazat(1, 1, 69, elemszam()+1,NEVSORTABLAZAT);
		Screen1.irXY(2,10,"NÉV");
		Screen1.irXY(2,31,"CÍM");
		Screen1.irXY(2,47,"SZ.ÉV");
		Screen1.irXY(2,59, "FIZETÉS");
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
	}
	
	static void lista_eredeti() {
		Kijelzo Screen1= new Kijelzo(30,130);
		Screen1.torol();
		Screen1.irXY(0, 32, "F E L V I T E L    S O R R E N D J E    S Z E R I N T I    L I S T A");
		Screen1.tablazat(1, 15, 103, 13,EREDETISORTABLAZAT);
		Screen1.irXY(7, 0, "START NÉV : "+ nev_elso);
		Screen1.irXY(11, 0, "ÜRES NÉV  : "+ ures_elso_n);
		Screen1.irXY(15, 0, "START FIZ : "+ fiz_elso);
		Screen1.irXY(19, 0, "ÜRES FIZ  : "+ures_elso_f);
		Screen1.irXY(2,16,"T.INDEX");
		Screen1.irXY(2,32,"NÉV");
		Screen1.irXY(2,53,"CÍM");
		Screen1.irXY(2, 68,"SZ.ÉV");
		Screen1.irXY(2, 81, "FIZETÉS");
		Screen1.irXY(2, 94, "LINK NÉV");
		Screen1.irXY(2, 106, "LINK FIZ.");
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
	}
	
	public static int elemszam() {
		int db=0; //elemszám számlálója
		int akt=nev_elso; // aktuális elem mutatója
		while (akt!=NULL) {
			db++; // számláló növelése
			akt=link_nev[akt]; // következő elemre lépés
		}
		return db;
	}
	
	public static void elemsz_ures_hely() {
		if (elemszam()==0) Kellekek.tajUzenet("A lista üres, szabad helyek száma: "+MAXADAT, true);
		else
			Kellekek.tajUzenet(" A listában "+elemszam()+ " elem van, az üres helyek száma: "+(MAXADAT-elemszam()), true);
	}
	
	public static void altag_fizetes() {
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
	}
	
	public static void atlag_eletkor() {
		LocalDate ma = LocalDate.now();
		int eletKor;
		double eletKorOssz=0;
		String atlagEletkor="";
		int akt=nev_elso;
		if (akt==NULL)
			Kellekek.tajUzenet("A lista üres, így az átlagéletkor nem értelmezhető.", true);
		else
			while (akt!=NULL) {
				eletKor=ma.getYear()-Integer.valueOf(szev[akt]);
				eletKorOssz+=eletKor;
				akt=link_nev[akt];
			}
		atlagEletkor=String.format("%,4.2f év", (eletKorOssz/elemszam()));
		Kellekek.tajUzenet("A listában lévő emberek átlagéletkora "+atlagEletkor, true);

	}
	
	public static boolean beszur(String a_nev,String a_cim, String a_szev, String a_fiz) {
		int hely; // Annak az elemnek a helye, amely után be kell szúrni az új nevet
		int akt; // Az aktuális elem mutatója
		int elozo; // Az aktuálist megelőző elem mutatója
		int uj; //Az új elem mutatója
		//Megkeressük a helyet, ahová be kell szúrni az új elemet
		hely=NULL;
		if (nev_elso==NULL) hely=NULL; //Ha üres a lista
		else {// A lista min. 1 elemet tartalmaz
			if (myCollator.compare(a_nev, nev[nev_elso])<0) hely=NULL;
			else {
				elozo=nev_elso;
				akt=link_nev[nev_elso]; //Az aktuális hely beállítása
				while ((akt!=NULL) && (hely==NULL)) { // ciklus, amíg nem lista vége és nincs meg a keresett hely
					if (myCollator.compare(a_nev, nev[akt])<0) //Megvan a hely ?
						hely=elozo; //A hely az aktuális előtti legyen e mögé fogunk beszúrni
					elozo=akt; //Előző legyen az aktuális
					akt=link_nev[akt]; // Aktuális legyen a következő
				}
				if ((hely==NULL) && (akt==NULL)) // Az utolsó helyre kell beszúrni
					hely=elozo; //A hely a lista utolsó eleme, az új elem lesz meajd a legutolsó
			}
		}
		//Itt jön a beszúrás a megkeresett helyre 
		if (ures_elso_n==NULL) return false; // nincs több üres hely a listán
		else {
			uj=ures_elso_n; // az uj helyre tesszuk a beszurandó elemet
			ures_elso_n=link_nev[ures_elso_n]; // A következő szabad hely állítása
			nev[uj]=a_nev;
			cim[uj]=a_cim;
			szev[uj]=a_szev;
			fiz[uj]=Double.parseDouble(a_fiz);
			if (hely==NULL) { // Ha az elso helyre történt a beszúrás
				link_nev[uj]=nev_elso; // az új elem az első
				nev_elso=uj; // az új elem az list eleje
			}
			else {
				link_nev[uj]=link_nev[hely]; //Az új elem követője
				link_nev[hely]=uj; // Ami mögé beszúrtunk az után jöjjön az új elem
			}
		}
		hely=NULL;
   	if (fiz_elso==NULL) hely=NULL; //Ha üres a lista
   	else {// A lista min. 1 elemet tartalmaz
   		if (Double.parseDouble(a_fiz)<fiz[fiz_elso]) hely=NULL;
   		else {
   			elozo=fiz_elso;
   			akt=link_fiz[fiz_elso]; //Az aktuális hely beállítása
   			while ((akt!=NULL) && (hely==NULL)) { // ciklus, amíg nem lista vége és nincs meg a keresett hely
   				if (Double.parseDouble(a_fiz)<fiz[akt]) //Megvan a hely ?
   					hely=elozo; //A hely az aktuális előtti legyen e mögé fogunk beszúrni
   				elozo=akt; //Előző legyen az aktuális
   				akt=link_fiz[akt]; // Aktuális legyen a következő
   			}
   			if ((hely==NULL) && (akt==NULL)) // Az utolsó helyre kell beszúrni
   				hely=elozo; //A hely a lista utolsó eleme, az új elem lesz meajd a legutolsó
   		}
   	}
   	
   	//Itt jön a beszúrás a megkeresett helyre 
   	uj=ures_elso_f; // az uj helyre tesszuk a beszurandó elemet
     	ures_elso_f=link_fiz[ures_elso_f]; // A következő szabad hely állítása
     	if (hely==NULL) { // Ha az elso helyre történt a beszúrás
     		link_fiz[uj]=fiz_elso; // az új elem az első
     		fiz_elso=uj; // az új elem az list eleje
     	}
     	else {
     		link_fiz[uj]=link_fiz[hely]; //Az új elem követője
     		link_fiz[hely]=uj; // Ami mögé beszúrtunk az után jöjjön az új elem
     	}
     		
   	
		return true;
	} //beszúr metótus

} // Lista_kezeles class
