---
title:  'Lerntagebuch zur Bearbeitung von Blatt 3'
author:
- Maxim Fründt (maxim.fruendt@fh-bielefeld.de)
- Dominik Haacke (dominik.haacke@fh-bielefeld.de)
- Benjamin Krüger (benjamin.krueger@fh-bielefeld.de)
...

# Aufgabe

Das Dungeon soll um ein Inventarsystem erweitert werden. Im Inventar sollen begrenzt viele Gegenstände hinein und auch wieder hinaus gepackt werden können. Dafür werden diverse Gegenstände in das Spiel implementiert, welche aus dem Inventar heraus ausrüstbar sein sollen. Die Gegenstände werden zufällig im Dungeon zum aufsammeln generiert.

# Ansatz und Modellierung

## Items

Für die verschiedenen Items haben wir zunächst Texturen herausgesucht. Das Laden der Texturen haben wir in die Klasse `ItemTextures` ausgelagert.
Da mehrere Itemtypen erstellt werden sollen, wird eine abstrakte Basisklasse `InventoryItem` erstellt. Diese implementiert die Klassen `IDrawable` und `IEntity`, da die Items im Dungeon gezeichnet werden müssen. In dieser Basisklasse wird die Textur des Items gespeichert und eine Methode zur Erkennung des Itemtypen bereitgestellt. Für Items welche sich in der Welt befinden wird zudem die Klasse `DungeonItem` angelegt. In der Klasse werden zusätzliche Informationen, wie die Positionim Dungeon gespeichert.

Die Klasse DungeonItem erhält folgende Grundattribute:

- `Point currentPosition`: Gibt die Position im Level an.
- `boolean isPickedUp`: Gibt an, ob das Item aufgesammelt worden ist.
- `Texture texture`: Enthält die Itemtextur.
- `int capacity`: Gibt den Platzbedarf des Items an.
- `int damage`: Gibt den Schaden an, welchen das Item austeilen kann.
- `String spell`: Enthält den Zauberspruch des Items.
- `ItemType type`: Spezifiziert, um was für ein Item es sich handelt.
- `ItemType satchelType`: Spezifiziert, um was für eine Tasche es sich handelt, wenn das Item eine Tasche ist.
- `DungeonWorld currentDungeonWorld`: Gibt an in welchem Level sich das Item befindet.

Zusätzlich zu Get- und Set-Methoden werden folgende Methoden bereitgestellt:

- `boolen deletable()`: Gibt Auskunft darüber, ob das Item gelöscht werden kann.
- `findRandomPosition()`: Legt die Position auf einen zufälligen Ort im Dungeon fest.
- `InventoryItem pickUp()`: Gibt beim Aufsammeln das InventoryItem zurück, welches durch das DungeonItem repräsentiert wird.
- `update()`: Update Methode, welche vom GameHandler aufgerufen wird.

Als Grundtypen `ItemType` definieren wir zunächst `WEAPON`, `PROTECTION`, `POTION`, `SPELL` und `SATCHEL`. Für diese Grundtypen werden zusätzlich Klassen angelegt, welche `InventoryItem` erben und zusätzliche spezifische Attribute ergänzen. Die Items werden wie folgt spezifiziert:

- `WEAPON`: Stellt ein Schwert dar mit vordefinierten Werten für den Schaden und die Haltbarkeit.
- `PROTECTION`: Kann sowohl ein Schild, als auch ein Rüstungsteil sein. Erhält vordefinierte Werte für den Verteidigungswert und die Haltbarkeit.
- `POTION`: Stellt Tränke dar, welche den Spieler heilen können. Dafür erhalten sie einen vordefinierten Wert für die Menge an wiederhergestellten Lebenspunkten.
- `SPELL`: Stellt einen Zauberspruch dar mit vordefiniertem Spruch.
- `SATCHEL`: Stellt eine Tasche dar, welche mehrere Items desselben Typs halten kann. Erhält vordefinierte Werte für die Kapazität, eine Liste an gelagerten Items. Darüber hinaus werden Funktionen implementiert, zum einlagern und herausnehmen von Items und um den Inhalt in der Konsole darzustellen.

Für das Handling der Items und der im späteren beschriebenen Schatztruhen, welche sich im aktuellen Dungeon-Level befinden, wird jeweils eine Liste der Items und eine Liste der Schatztruhen im `GameHandler` angelegt. Diese Listen werden beim Laden des Levels mit Items und Schatztruhen gefüllt. Im `GameHandler` wird im Verlauf des Spiels fortwährend geprüft, ob ein Item eingesammelt worden ist. Ist dies der Fall wird es aus dem Level entfernt. Wenn hingegen eine Schatztruhe geöffnet worden ist, wird diese zum Looten geöffnet.

## Inventarsystem

Um die vom Held aufgesammelten Items zu Speichern muss ein Inventar implementiert werden. Dafür wird zunächst die Klasse `Inventory` angelegt.

Das Inventar erhält die Attribute 
- `List<InventoryItem> items`: Liste der im Inventar gelagerten Items.
- `int capacity`: Kapazität des Inventars.

Darüberhinaus werden folgende Funktionen, zusätzlich zu Get- und Set-Methoden implementiert:

- `InventoryItem getItemAt(int)`: Gibt das Item am ausgewählten Indexplatz zurück.
- `boolean dropItem(InventoryItem)`: Entfernt ein Item aus dem Inventar.
- `boolean dropItemAt(int)`: Entfernt ein Item am ausgewählten Index aus dem Inventar.
- `boolean addItem(InventoryItem)`: Fügt ein Item dem Inventar hinzu.
- `int getIndexOfFreePlace()`: Gibt den Index des ersten freien Inventarplatzes zurück.
- `log(InventoryConsoleLogVisitor)`: Abstrakte Log-Methode, um den Inventarinhalt darzustellen.

Das Inventar des Helden wird als Klasse `HeroInventory` implementiert, welche die `Inventory` Klasse erbt und eine Kapazität von 10 Items vorgibt.

Um die vom Held ausgerüsteten Gegenstände zu verwalten, wird eine neue Klasse `HeroEquipment` angelegt. Diese enthält derzeit das vom Held angelegte Schwert und Schild, auf welche mit Get- und Set-Methoden zugegriffen werden kann.

Als letztes Inventarobjekt werden Kisten mit der Klasse `Chest` implementiert, welche ebenfalls von `Inventory` erbt. Da Kisten im Dungeon dargestellt werden sollen, erben sie zudem `IAnimatable` und `IEntity`. Kisten implementieren Get- und Set-Methoden für ihre Attribute und alle vorgegebenen abstrakten Funktionen zur Darstellung im Dungeon.

Um den Inhalt von Inventaren darzustellen, wird das Visitor-Pattern angewandt. Dabei soll die Darstellung der Visitor sein. Der Visitor definiert für die Klassen `Satchel`, `Chest` und `Heroinventory` eigene Log-Funktionen.

Die implementierten Klassen `HeroInventory` und `HeroEquipment` werden schlussendlich als Instanz in der `Hero` Klasse angelegt. Um mit den Items, welche aufgesammelt wurden, etwas anzufangen, werden in der `Hero` Klasse neben neuen Get- und Set-Mehoden folgende Methoden ergänzt:

- `<T extends InventoryItem> boolean useItemFromInventory(T)`: Benutzt das eingegebene Item aus dem Inventar des Helden. Dafür werden je nach Typ die nachfolgenden Unterfunktionen aufgerufen.
- `boolean drinkPotion(Potion)`: Benutzt einen Trank aus dem Inventar.
- `boolean readSpell(Spell)`: Benutzt einen Zauberspruch aus dem Inventar.
- `<T extends InventoryItem> boolean equipItemFromInventory(T)`: Rüstet einen Gegenstand aus dem Inventar aus.
- `logInventoryItems(InventoryConsoleLogVisitor)`: Stellt den Inventarinhalt auf der Konsole dar.
- `onEquipmentChanged()`: Event, wenn ein Gegenstand ausgerüstet/abgerüstet wurde.
- `handlePickUp()`: Event, wenn ein Item aufgesammelt wurde.
- `handleInputsForInventoryHandling()`: Handler für Inventarbezogene Benutzereingaben.
- `handleInputsForPickingUpChestItems(Chest<InventoryItem>)`: Handler für Benutzereingaben in Bezug auf die Interaktion mit Kisten.

Um die Interaktion des Spielers mit dem Inventar zu ermöglichen wird der `GameHandler` ergänzt. Im `GameHandler` wird auf Interaktionen mit Kisten, welche sich im Dungeon befindet geprüft und entsprechend reagiert. Über die, in Blatt 1 implementierte, Funktion `update()` des Helden, wird auf Benutzereingaben zur Steuerung des Inventars geprüft und diese entsprechend ausgeführt.

Die Funktionalität um Monster anzugreifen, wurde bereits bei Blatt 2 umgesetzt. Es wurde jedoch die Kampfanimation für den Helden ergänzt.

# Umsetzung

29.04.2021 - Implementierung Items (2h)  
30.04.2021 - Implementierung Inventar (2h)  
01.05.2021 - Implementierung Taschen (2h)  
01.05.2021 - Implementierung Kisten (2h)  
01.05.2021 - Implementierung Visitor-Pattern zur Inventardarstellung (1h)  
01.05.2021 - Implementierung Kampfanimation (0.5h)

# Postmortem

Da wir fortwährendes Refactoring betreiben, ist das erweitern der erstellten Funktionen ohne große Kollisionen möglich. Durch die erstellten abstrakten Klassen, können auch in Zukunft ohne größeren Aufwand neue Items für das Dungeon angelegt und verwaltet werden.