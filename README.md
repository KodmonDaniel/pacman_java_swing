# pacman_java_swing
2D pacman game created with java. The GUI is create with swing.


<img src="images/1.png" width="400"> 

## Rövid ismertető

Pacman játék A játék lényege, hogy a felhasználó, az általa irányított karakterrel összeszedje a pályán lévő összes pontot. A játék akkor ér véget, ha az összes pont fel lett véve vagy ha a játékosnak elfogynak az életei. A játékot ellenfelek (szellemek) nehezítik, akik ugyanúgy a pályán járkálnak. Ha a játékos ütközik egy ilyen szellemmel, az előrehaladása megmarad, de egy életet elveszít a háromból. Ha mind a három élete elveszik, a játékot vesztett és előröl kell kezdeni a játékot. A megszerzett pontokat rangsorba mentjük, amelyek külső szöveges fileban kerülnek mentésre (nem vesznek el, de csak a legjobb 5-öt tároljuk). Nehézség beállítására is lehetőség lesz.

## Use-case-ek

<img src="images/2.png" width="400"> 
> A játék egyszerű use-case diagramja

### Pacman irányítás

Ennek a use-case-nek az aktorja a Játékos. Főeseménye, hogy a játékos pacmant
jobbra, balra, fel vagy le indítja el. Az események ezen belül a következőek lehetnek
(alternatív forgatókönyvek):
