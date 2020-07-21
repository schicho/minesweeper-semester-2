Dies ist das Minesweeper-Projekt; 
entstanden im Zuge der Software Engineering Vorlesung 
im Sommersemester 2020 
an der Universität Passau.

Mitwirkende sind:

-Asya Abdulaeva
-Simon Höfer
-Sebastian Schicho
-Finn Ribbeck
-Christian Zavaczki

==> Gruppe 14

Die von uns entwickelte Implementierung des Minesweeper Spiels
umfasst neben den Standardfunktionen

( -Ein Spiel in drei Schwierigkeitsstufen starten
  -Felder aufdecken
  -Rekursives Aufdecken der benachbarten Felder, wenn diese keine Mine sind
  -Ein Feld als Mine markieren),
  
auch folgende erweiternde Funktionen:

-Speichern des aktuellen Spiels in einen Seed
-Laden eines bestimmten Spiels durch einen Seed
-Markieren eines Feldes als unsicher "?", durch erneutes Rechtsklicken auf ein als Mine markiertes Feld

Die Implementierung besitzt ein GUI und wird intern über das Model-View-Controller Pattern gesteuert.
