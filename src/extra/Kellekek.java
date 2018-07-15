package extra;

import extra.Console;

public class Kellekek {
	
	static final char HIBA_UZ_MINTA='*';
	static final char TAJ_UZ_MINTA='-';
	
  // Az osztályból nem lehet példányt létrehozni:
  private Kellekek() {
  }
  
 public static int egyszeruMenu(String menuNev, String Menupont[],int db) {
	 int valasz=0;
	 StringBuilder seged = new StringBuilder();
	 menuNev=menuNev.toUpperCase();
	 for (int i=0;i<menuNev.length();i++)
		 seged.append(menuNev.charAt(i)+" ");
	 do {
   	 System.out.println();
   	 System.out.println("-----< "+seged.toString()+" >-----");
   	 System.out.println();
   	 for (int i=0;i<db;i++) 
   		 System.out.println(Menupont[i]);
   	 System.out.println("Válassz:");
   	 valasz = extra.Console.readInt();
	 } while (valasz<0 || valasz>db);
	return valasz;
 }
 
 public static int egesz_Beolvas(String uzenet, int min, int max, String hibaUzenet) {
	 int seged;
	 do {
		seged=Console.readInt(uzenet);
	 	if ((seged<min || seged>max) && !(hibaUzenet.isEmpty()))
	 		System.out.println(hibaUzenet);
	 } while (seged<min || seged>max);
	 return seged;
 }

/*Egy valós számot a megadott tizedesjegyre kerekít.*/
	public static double kerekit(double szam,int tizedes)
	{
		String seged, seged1;
		seged=Integer.toString(tizedes); // A tizedest szöveggé alakítjuk, hogy számformátumot tudjunk készíteni.
		seged1=String.format("%."+seged+"f", szam); // A megkapott szám paramétert a megadott formátum szerint megformázzuk, de az eredmény sztring.
		return Double.valueOf(seged1.replace(',','.')); // Visszaadjuk a megformázott számot számmá alakítva, de ki kell benne cserélni a tizedesvesszőt pontra.
	}

	//A metódus a paraméterként kapott sztring 0. karakterétől számított db hosszúságú sztringet
	//ad vissza. Ha a sztring rövidebb, mint a megadott db, akkor az egész stzringet visszaadja.
	public static String jobblevag(String szoveg, int db) {
	   if (szoveg.length()<=db)
	      return szoveg;
	   else
	      return szoveg.substring(0,db-1);
	} //jobblevag metódus
	
	//A metódus a pareméterként kapott string honnan. karakterétől visszaadja a string végéig lévő
	//karaktersorozatot.
	public static String ballevag(String szoveg, int honnan) {
	   if (szoveg.length()<=honnan)
	      return szoveg;
	   else
	      return szoveg.substring(honnan);
	} //ballevag metódus
	
	//A metódus a paraméterként kapott sztringet kiírja, előtte egy HIBA_UZ_MINTA karakterből álló sor van, azelőtt
	//egy üres sor. Az üzenet mögött HIBA_UZ_MINTA álló sor van, utána egy üres sor
	//Ha enter igaz Enter leütésre vár
	public static void hibaUzenet(String uzenet, boolean enter) {
		uzenet(uzenet,enter,HIBA_UZ_MINTA);
	}
	
	//A metódus a paraméterként kapott sztringet kiírja, előtte egy TAJ_UZ_MINTA karakterből álló sor van, azelőtt
   //egy üres sor. Az üzenet mögött TAJ_UZ_MINTA álló sor van, utána egy üres sor
   //Ha enter igaz Enter leütésre vár
	public static void tajUzenet(String uzenet, boolean enter) {
	   uzenet(uzenet,enter,TAJ_UZ_MINTA);
	}
	
	public static void uzenet(String uzenet, boolean enter, char minta) {
	   System.out.println();
      for (int i=1;i<=uzenet.length();i++)
         System.out.print(minta);
      System.out.println();
      System.out.println(uzenet);
      for (int i=1;i<=uzenet.length();i++)
         System.out.print(minta);
      System.out.println();
      System.out.println();
      if (enter)
         extra.Console.pressEnter();
	}
	//A paraméterként kapott szám alapján visszadja a hónap nevét
	public static String honap(int h) {
		switch (h) {
			case 1: return "Január";
			case 2: return "Február";
			case 3: return "Március";
			case 4: return "Április";
			case 5: return "Május";
			case 6: return "Június";
			case 7: return "Július";
			case 8: return "Augusztus";
			case 9: return "Szeptember";
			case 10: return "Október";
			case 11: return "November";
			case 12: return "December";
			default: return "Hibás hónapsorszám!";
		}
	} // honap metódus
	
	public static boolean igenNem(String kerdes) {
	   char v;
	   do {
         v=extra.Console.readChar(kerdes);
      } while ((Character.toUpperCase(v)!='I') && (Character.toUpperCase(v)!='N'));
	   return Character.toUpperCase(v)=='I';
	}
	
}


 