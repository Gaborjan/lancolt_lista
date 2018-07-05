# lancolt_lista
Két irányban láncolt lista adatszerkezet modellezése tömbök segítségével, demonstrációs célú funkciókkal


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
