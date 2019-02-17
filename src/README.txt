NUME: IUGA FLORIN-EUGEN
GRUPA SI SERIA: 325CA

		 TEMA 1
	--SHERIFF OF NOTTINGHAM--

Ideea generala a implementarii:
   Am o clasa Player care contine 2 metode de baza pentru joc,
metoda inspect(), respectiv metoda abstracta playAsMerchant(),
cele 2 metode reprezentand situatiile in care se poate gasi
un jucator, in ipostaza de serif sau in ipostaza de comerciant.
Pe langa cele doua metode, exista metoda createBag() care
creeaza efectiv sacul unui jucator, metoda drawAssets()
pentru a trage carti din pachet (pachetul fiind o coada),
metoda getWantedAsset() care intoarce cartea dorita de
jucator din mana, metoda isSherif() care verifica daca un
jucator este serif sau nu, metoda putAssetsOnStand() care
pune pe stand-ul fiecarui jucator bunurile din bag trecute
dupa inspectie, getteri, respectiv setteri. Aceasta clasa
este clasa parinte, intrucat BasicPlayer, BribePlayer si 
GreedyPlayer. O alta metoda importanta este addIllegalBonusOnStand()
care adauga la finalul jocului bonusurile pentru fiecare
bun ilegal ajuns pe stand.

BasicPlayer
-> se comporta exact ca Player, astfel
ca puteam sa o consider pe aceasta clasa parinte, dar am
zis sa aiba fiecare jucator propria clasa.
 
BribePlayer
-> contine si implementeaza metoda abstracta playAsMerchant()
din clasa parinte Player, aceasta metoda se bazeaza pe
numarul de monede curent astfel incat el sa stie daca poate
introduce mita in sac sau nu. In functie de acest lucru,
el poate juca propria strategie (adica sa bage numai bunuri
ilegale in cazul in care are monede) sau poate juca ca
BasicPlayer, astfel ca se apeleaza metoda createBag() din
super clasa. De asemenea, este suprascrisa metoda inspect(), el
mai intai verificand daca jucatorul pe care il inspecteaza
are sau nu mita in sac.

GreedyPlayer
-> implementeaza metoda abstracta playAsMerchant(), care
creeaza sacul jucatorului pe  baza paritatii rundei in care
este comerciant. Daca este o runda para, verifica prin
metoda existIllegals() daca exista carti ilegale in mana.
Daca da, atunci se apeleaza metoda getMostProfitableIllegal()
care intoarce cartea ilegala cu cel mai mare profit cu
scopul de a o introduce in sac. Altfel, daca este o runda
impara atunci se apeleaza metoda createBag() din clasa
parinte, acesta comportandu-se ca BasicPlayer. Totodata,
este suprascrisa metoda inspect(), Greedy fiind jucatorul
care accepta mita.

Bag
-> fiecare jucator are un Bag (agregare), contine o lista
de carti, un String declaredType care specifica tipul
declarat de jucator, respectiv un intreg pentru mita. In
rest, contine setteri si getteri, plus metode de prelucrare
a sacului.

Game
-> aceasta este clasa in care se regaseste toata logica
jocului. Se initializeaza jucatorii in metoda initializePlayers(),
metoda decideSherif() decide efectiv seriful curent, metoda
makeListOfBonus() creeaza cele 8 liste de kingBonus si
queenBonus. Aceste liste contin id-urile jucatorilor care
merita bonus daca a ajuns sa fie rege sau regina la ceva.
Dupa, metodele addKingBonuses() si addQueenBonuses() adauga
bonusurile pentru regi si regine pe baza acelor liste.
Metoda play() este metoda care apeleaza metodele din clasa
Game pentru a urma logica jocului.

Asset
-> fiecare bun este un Asset, fiecare bun avand diferite
caracteristici, precum profit, penalty etc.

AssetComparator si ScoreComparator sunt 2 clase care sunt
folosite la sortare.
 
