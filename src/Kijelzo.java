/*Mivel a konzolra elég nehézkes a kiírás, virtuális képernyőt hozhatunk létre az osztállyal, amire a különféle metódusokkal
 * írhatunk, rajzolhatunk. A virtuális kijelző tartalmát a kiír metódussal jeleníthetjük meg a konzolon.
 * Jánvári Gábor 2018.
 */
public class Kijelzo {
	private static final int MAXOSZLOP	 = 200;
	private static final int MAXSOR		 = 50;
	private char			  tartalom[][]; // A kijelző "tartalma"
	private int				  aktSor		 = 0, aktOszlop = 0; // A képernyő kurzorának poziciója
	private int				  kijelzoSor = 0, kijelzoOszlop = 0; // Ennyi sorból és oszlopból áll a kijelző
	public static final char VS = '\u2500'; // vízszintes vonal, szimpla
	public static final char BFS = '\u250c'; // bal felső sarok, szimpla
	public static final char JFS = '\u2510'; //jobb felső sarok, szimpla
	public static final char FS = '\u2502'; // föggőleges vonal, szimpla
	public static final char BAS = '\u2514'; // bal alsó sarok, szimpla
	public static final char JAS = '\u2518'; // jobb alsó sarok, szimpla
	public static final char BFD = '\u2554'; // bal felső sarok, dupla
	public static final char JFD = '\u2557'; // jobb felső sarok, dupla
	public static final char VD = '\u2550'; // vizszintes vonal, dupla
	public static final char FD = '\u2551'; // függőleges vonal, dupla
	public static final char BAD = '\u255A'; // bal alsó sarok, dupla
	public static final char JAD = '\u255D'; // jobb alső sarok, dupla
	public static final char ARNY = '\u2591'; // árnyékolás 
	public static final char JBEKS = '\u251C'; // jobb bekötő elem -| szimpla
	public static final char BBEKS = '\u2524'; // bal bekötő elem |- szimpla
	public static final char KERS = '\u253C'; // szimpla kereszt elem -|- (táblázatba)
	public static final char ABEKS = '\u2534'; // alsó bekötő elem táblázatba, szimpla
	public static final char FBEKS = '\u252C'; // felső bekötő elem táblázatba, szimpla
	
	public Kijelzo() {
		
	}
	
	//Konstruktor
	public Kijelzo(int sor, int oszlop) {
		if (sor > 0 && sor <= MAXSOR && oszlop > 0 && oszlop <= MAXOSZLOP) {
			tartalom = new char[sor][oszlop];
			kijelzoSor = sor;
			kijelzoOszlop = oszlop;
		} else {
			tartalom = new char[MAXSOR][MAXOSZLOP];
			kijelzoSor = MAXSOR;
			kijelzoOszlop = MAXOSZLOP;
		}
		for (int i = 0; i < kijelzoSor; i++)
			for (int j = 0; j < kijelzoOszlop; j++)
				tartalom[i][j] = ' ';
		aktSor = 0;
		aktOszlop = 0;

	}

	//A tárolt kijelző kiírása konzolra
	public void kiir() {
		for (int i = 0; i < kijelzoSor; i++) {
			for (int j = 0; j < kijelzoOszlop; j++)
				System.out.print(tartalom[i][j]);
			System.out.println();
		}
	}
	
	//A tárolt kijelző törlése = szóközzel feltöltése, kurzort a bal felső sarokba állítja
	public void torol() {
		for (int i = 0; i < kijelzoSor; i++)
			for (int j = 0; j < kijelzoOszlop; j++)
				tartalom[i][j] = ' ';
		aktSor = 0;
		aktOszlop = 0;
	}
	
	//A megadott sor, megadott oszlop poziciójába visszi a "kurzort"
	public void poz(int sor, int oszlop) {
		if (sor < kijelzoSor && sor >= 0)
			aktSor = sor;
		if (oszlop < kijelzoOszlop && oszlop >= 0)
			aktOszlop = oszlop;
	}

	//Az aktuális kurzor pozicíóba kiírja a megadott szöveget
	public void ir(String szoveg) {
		int o = aktOszlop;
		int i = 0;
		while (o < kijelzoOszlop && i < szoveg.length()) {
			tartalom[aktSor][o] = szoveg.charAt(i);
			o++;
			i++;
		}
	}

	//A megadott pozicióba kiírja az megadott szöveget, a kurzor pozicíója nem változik
	public void irXY(int sor, int oszlop, String szoveg) {
		int mSor = aktSor, mOszlop = aktOszlop; // kurzor helyének eltárolása
		poz(sor, oszlop);
		ir(szoveg);
		//kurzor helyének visszaállítása
		aktSor = mSor;
		aktOszlop = mOszlop;
	}
	/*A megadott sorba, a bOszlop és jOszlop közé, középre igazítva kiírja a szoveg
	 * stringet. Ellenőrzést nem végez, hibás adatokkal a kiírás helytelen lesz.
	 */
	public void kozepIr(int sor, int bOszlop, int jOszlop, String szoveg) {
	   irXY(sor,(int) (bOszlop+((((jOszlop-bOszlop)+1)-szoveg.length())/2)),szoveg);
	}
	
	/* Megrajzol egy adott hosszúságú sort, adott karakterből, megadott sor/oszloptól,  megadott hosszban
	 * a kurzor poziciója nem változik.*/
	public void sorRajzol(int sor, int oszlop, int hossz, char mibol) {
		for (int i = 0; i < hossz; i++)
			tartalom[sor][oszlop + i] = mibol;
	}

	/* Megrajzol egy adott hosszúságú oszlopot, adott karakterből, megadott sor/oszloptól,  megadott hosszban
	 * a kurzor poziciója nem változik.*/
	public void oszlopRajzol(int oszlop, int sor, char mibol, int hossz) {
		for (int i = 0; i < hossz; i++)
			tartalom[sor + i][oszlop] = mibol;
	}

	/* Keretet rajzol az aktuális kurzor poziciótól, kurzor pozicíója nem változik.
	 * sor,oszlop: a keretnek ennyi sor és oszlopa lesz
	 * tipus: 		S vagy D, egy vonalas, dupla vonalas keret
	 * arnyek: 		ha igaz akkor árnyékot is rajzol a keretnek
	 * fejléc: 		fejléc szöveg a keret tetejére
	 */
	public void keret(int sor, int oszlop, char tipus, boolean arnyek, String fejlec) {
		// Rajzoló karakterek definiálása
		
		// Aktuális rajzoló karakterek, 'típus' paramétertől függően.
		char bfA = ' ', jfA = ' ', vA = ' ', fA = ' ', jaA = ' ', baA = ' ';
		// Rajzoló karakterek beállítása
		if (Character.toUpperCase(tipus) == 'S') {
			bfA = BFS;
			jfA = JFS;
			vA = VS;
			fA = FS;
			baA = BAS;
			jaA = JAS;
		} else if (Character.toUpperCase(tipus) == 'D') {
			bfA = BFD;
			jfA = JFD;
			vA = VD;
			fA = FD;
			baA = BAD;
			jaA = JAD;
		}
		//Kurzor pozició mentése
		int mSor, mOszlop;
		mSor = aktSor;
		mOszlop = aktOszlop;
		irXY(mSor, mOszlop, bfA + ""); // Bal felső sarok
		sorRajzol(mSor, mOszlop + 1, oszlop - 2, vA); // Első sor, keret teteje
		irXY(mSor, mOszlop + oszlop - 1, jfA + ""); // Jobb felső sarok
		oszlopRajzol(mOszlop, mSor + 1, fA, sor - 1);
		oszlopRajzol(mOszlop + oszlop - 1, mSor + 1, fA, sor - 1);
		if (arnyek)
			oszlopRajzol(mOszlop + oszlop, mSor + 1, ARNY, sor);
		irXY(mSor + sor - 1, mOszlop, baA + ""); // bal alsó sarok
		poz(mSor + sor - 1, mOszlop + 1);
		sorRajzol(mSor + sor - 1, mOszlop + 1, oszlop - 2, vA); // utolsó sor, keret alja
		if (arnyek) {// jobb alsó sarok
			irXY(mSor + sor - 1, mOszlop + oszlop - 1, jaA + ""); // Sarok
			irXY(mSor + sor - 1, mOszlop + oszlop, ARNY + ""); // Mögé az árnyék
		} else {
			irXY(mSor + sor - 1, mOszlop + oszlop - 1, jaA + "");
		}
		if (arnyek) { // Ha kell, árnyék rajzolás
			poz(mSor + sor, mOszlop + 2);
			sorRajzol(mSor + sor, mOszlop + 2, oszlop - 1, ARNY); // Ánryék megrajzolása
		}
		//Fejléc
		if (fejlec.length()>0) {
			if (Character.toUpperCase(tipus)=='S') 
				fejlec='\u2524'+fejlec+'\u251C';
			if (Character.toUpperCase(tipus)=='D') 
				fejlec='\u2561'+fejlec+'\u255E';
			irXY(mSor,(int) (mOszlop+((oszlop-fejlec.length())/2)),fejlec);
		}
		//Kurzor visszaállítása
		aktSor = mSor;
		aktOszlop = mOszlop;
		poz(aktSor, aktOszlop);
	}

	//Kurzor aktuális sora
	public int getAktSor() {
		return aktSor;
	}

	//Kurzor aktuális oszlopa
	public int getAktOszlop() {
		return aktOszlop;
	}
	/*Táblázatot rajzol, szimpla vonalakból.
	 *sor: a táblázat bal felső sarka
	 *oszlop: a táblázat bal felső sarka
	 *szelesseg: a táblázat szélessége
	 *sordb: hány sora legyen a táblázatnak
	 *oszlopokSz: ebben e tömbben kell megadni melyik oszlopokban legyenek az oszlopelválasztók 
	 */
	public void tablazat(int sor, int oszlop, int szelesseg, int sordb, int[] oszlopokSz) {
		int mSor, mOszlop;
		
		//Kurzor pozició mentése
		mSor = aktSor;
		mOszlop = aktOszlop;
		poz(sor,oszlop);
		keret((sordb*2)+1, szelesseg, 'S', false, ""); // A táblázat külső keretének megrajzolása
		for (int i=1; i<sordb; i++) { // Megrajzoljuk a táblázat sorait
			sorRajzol(sor+(i*2), oszlop, szelesseg, VS);
			irXY(sor+(i*2),oszlop,JBEKS+""); // jobb bekötő elem
			irXY(sor+(i*2),(oszlop+szelesseg)-1,BBEKS+""); // bal bekötő elem
		}
		for (int i=0;i<oszlopokSz.length;i++) { //Megrajzoljuk a táblazat oszlopait
			oszlopRajzol(oszlop+oszlopokSz[i],sor,FS,(sordb*2));
			for (int j=1;j<=sordb-1; j++) { // A közbülső kereszt bekötők kirajzolása
				irXY(sor+(j*2),oszlop+oszlopokSz[i],KERS+"");
			}
			irXY(sor,oszlop+oszlopokSz[i],FBEKS+""); // Felső bekötő megrajzolása
			irXY(sor+(sordb*2),oszlop+oszlopokSz[i],ABEKS+""); // Alsó bekötő megrajzolása
		}
		//Kurzor visszaállítása
		aktSor = mSor;
		aktOszlop = mOszlop;
		poz(aktSor, aktOszlop);
	}
	
} //class Kijelzo