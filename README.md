# Minesweeper

Dies ist das Minesweeper-Projekt; 
entstanden im Zuge der Software Engineering Vorlesung 
im Sommersemester 2020 
an der Universität Passau.

![Beispiel Screenshot](/docs/minesweeper_screenshot.png)

Mitwirkende aus Gruppe 14:

- Asya Abdulaeva
- Simon Höfer
- Sebastian Schicho
- Finn Ribbeck
- Christian Zavaczki

Die von uns entwickelte Implementierung des Minesweeper Spiels
umfasst neben den Standardfunktionen:

- Ein Spiel in drei Schwierigkeitsstufen starten
- Felder aufdecken
- Rekursives Aufdecken der benachbarten Felder, wenn diese keine Mine sind
- Ein Feld als Mine markieren
  
auch folgende erweiternde Funktionen:

- Speichern des aktuellen Spiels in einen Seed
- Laden eines bestimmten Spiels durch einen Seed
- Markieren eines Feldes als unsicher "?", durch erneutes Rechtsklicken auf ein als Mine markiertes Feld

Die Implementierung besitzt ein GUI und wird intern über das Model-View-Controller Pattern gesteuert.
Das Spiel startet im Hauptmenü, über das man die Möglichkeit hat ein neues Spiel auf Einfach, Mittel oder Schwer zu starten.
Außerdem kann der Spieler hier ein gespeichertes Spiel laden, sowie die Anwendung beenden.

Sobald ein Spiel gestartet wurde, kann der Pausezustand ausgerufen werden. Dadurch betritt der Spieler das Pausemenü,
welches dem Spieler die Gelegenheit gibt sein Spiel zu speichern, oder ins Hauptmenü zurückzukehren, 
sowie das aktuelle Spiel weiterzuspielen.
