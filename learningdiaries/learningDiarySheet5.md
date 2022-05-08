---
title:  'Lerntagebuch zur Bearbeitung von Blatt 5'
author:
- Maxim Fründt (maxim.fruendt@fh-bielefeld.de)
- Dominik Haacke (dominik.haacke@fh-bielefeld.de)
- Benjamin Krüger (benjamin.krueger@fh-bielefeld.de)
...

# Aufgabe

Als erste Aufgabe sollen verschiedene Quests implementiert werden, dabei soll das Observer-Pattern benutzt werden. Die Quest soll eine Belohnung haben und vom Spieler beispielsweise über einen NPC angenommen werden. Eine Quest kann dabei eine Kampfhandlung oder auch ein Lootauftrag sein. Das Logging soll entsprechend ausgeweitet werden und auch das HUD soll die Quests anzeigen könnnen.

Die Zweite Aufgabe dreht sich um JUnit Tests. Dabei sollen geeignete Testfälle zu den Quests erstellt werden.

# Ansatz und Modellierung

## Quests

### IQuestProgressObserver

Da wir die Quests mit dem Observer Pattern implementieren sollen, haben wir zuerst diese Klasse als Observer erstellt. Sie bietet nur die Methode `void onProgressChanged` an, um die Quest zu aktualisieren.

### Quest

Die Hauptklasse für die Quests bildet unsere Klasse `Quest`. Sie enthält alle notwendigen Attribute und Methoden für die Grundfunktionalität.

**Attribute**:
* `IQuestProgressObserver questProgressObserver`: Observer, der bei der Beendigung aufgerufen wird.
* `int targetCount`: Der zu erreichende Zielwert.
* `int actualCount`: Der aktuelle Fortschritt.
* `String questName`: Der Name der Quest.
* `String questDescription`: Die Beschreibung der Quest.
* `QuestProgressAttributes questProgressAttribute`: Enum-Wert für die Art der Quest, entweder NONE, MONSTER oder PICKUP.
* `QuestRewards reward`: Enum-Wert für die Belohnung der Quest, entweder EXPERIENCE oder ITEM.

Neben dem Konstruktor, werden einige Methoden benötigt, um die Quest zu managen.

**Methoden**:
* `void onProgressChanged()`: Erhöht den aktuellen Fortschritt.
* `QuestProgressAttributes getQuestProgressAttribute()`: Liefert den QuestProgressAttributes Wert.
* `void takeReward(Hero hero)`: Gibt dem angegebenen Hero die Belohnung, falls die Quest beendet wurde.
* `String getQuestDescription()`: Liefert die Questbeschreibung.
* `String getQuestName()`: Liefert den Questnamen.
* `int getActualCount()`: Liefert den aktuellen Fortschritt.
* `void onCompleted()`: Ereignis, welches aufgerufen wird, wenn die Quest abgeschlossen ist.
* `void register(IQuestProgressObserver questProgressObserver)`: Registriert einen Observer für den Questfortschritt.

### QuestList

Das Enum `QuestList` definiert 2 Werte mit jeweils einer Quest. Einmal eine Quest, bei der man eine bestimmte Anzahl an Monstern töten und eine, bei der man eine bestimmte Anzahl an Items aufheben muss.

**Attribute**:
* `Quest quest`: Objekt, das eine der Quests hält.

Im Konstruktor wird eine neue Quest angelegt, dazu kann man entweder einen der vorher definierten Werte nehmen, oder man erstellt eine neue.

**Methoden**:
* `Quest getQuest()`: Gibt das Quest-Objekt zurück.


### Wizard

Der Wizard ist ein NPC, von dem man eine Quest entgegen nehmen kann. Er erbt von der Klasse `Character`, bewegt sich aber nicht und interagiert auch sonst nicht weiter mit der Umwelt.

**Attribute**:
* `List<Quest> quests`: Liste der Quests, die der Wizard an den Spieler weitergeben kann.

**Methoden**:
* `List<Quest> getQuests()`: Liefert die Questliste.
* `void showQuests()`: Zeigt alle Quests an. Der Spieler kann eine davon auswählen.
* `receiveDamage(DamageDetails damageDetails)`: Nicht weiter Implementiert, da der Wizard keinen Schaden nehmen kann.
* `void onDeath()`: Nicht weiter Implementiert, da der Wizard nicht sterben kann.
* `void update()`: Zeichnet den Wizard an einer zufälligen Stelle im Dungeon.

### Hero

Der Hero wurde um einige Methoden und Attribute erweitert und bestehende Methoden wurden angepasst, damit der Hero Quests verwalten kann. Zusätzlich implementiert der Hero jetzt auch das Interface `IQuestProgressObserver`.

**Attribute**:
* `IQuestProgressObserver questProgressObserver`: Speichert die aktuelle Quest.
* `QuestProgressAttributes questProgressAttribute`: Legt fest, welche Art von Quest der Hero gerade hat.

**Methoden**:
* `void register(IQuestProgressObserver questProgressObserver, QuestProgressAttributes progressAttribute)`: Registriert eine neue Quest und speichert die Questart.
* `void unregister(IQuestProgressObserver questProgressObserver)`: Entfernt eine Quest.
* `void handleQuestInputs()`: Managed die entsprechenden Inputs für die Quests.
* `void onProgressChanged()`: Callback, der aufgerufen wird, wenn die aktuelle Quest beendet wurde.

Außerdem wurden die Methoden `boolean addItemToInventory(InventoryItem toBeAddedItem)` und `void addExperience(float experience)` entsprechend erweitert, damit der Questfortschritt bei den richtigen Ereignissen geupdatet wird.

Der `GameHandler` wurde auch entsprechend angepasst. Die Klassen, die für das Logging und das Hud zuständig sind, wurden ebenfalls erweitert, um jetzt die auch die Quests zu unterstützen.

## JUnit

Um JUnit in unserem Projekt zu nutzen, muss zunächst die JUnit-Jar heruntergeladen und entsprechend im Projekt eingebunden werden. Wie in der Vorlesung und grundsätzlich empfohlen, wird ein zusätzlicher Ordnerpfad für die Tests vorgesehen, welche eine Spiegelung des src-Pfads entspricht:
* src
    * main
        * java
            * newgame
                * characters
                * gui
                * inventory
                * quests
                    * IQuestProgressObserver.java
                    * Quest.java
                    * QuestList.java
                    * QuestProgressAttributes
                * ...
* test
    * main
        * java
            * newgame
                * characters
                * gui
                * inventory
                * quests
                    * QuestTest.java
                * ...

Durch Separierung und zugleich Spiegelung der Pfade für Tests und Sources wird eine übersichtliche Ordnerstruktur erzielt, wodurch notwendige Inhalte bei Weitergabe an Kunden leichter auf das Notwendige beschränkt werden können. Darüber hinaus ist durch die Spiegelung bei einer Paketansicht immer Source- und Testklasse direkt untereinander.  
Wie oben zu sehen, werden auch bereits Testklassen für sämtliche Sourceklassen angelegt aber entsprechend der Aufgabenstellung zunächst nur die Quests aktiv getestet. Im Zuge dessen wird also die Klasse Quests.java getestet. Setter- und Gettermethoden werden entsprechend der Empfehlung aus der Vorlesung nicht getestet. Andere Methoden und Konstruktoren hingegen werden in Bezug auf ihre Übergabeparameter mittels Grenzwertanalyse getestet:

`Konstruktor`
* Übergabeparameter `int targetCount`
    * Erwartete IllegaleArgumentException bei kleiner 1
    * Keine obere Grenze
* Übergabeparameter `String questName`
    * Erwartete IllegaleArgumentException bei null oder leer
    * Sonst Kein Fehler
* Übergabeparameter `String questDescription`
    * Erwartete IllegaleArgumentException bei null oder leer
    * Sonst kein Fehler
* Validierung der Attributwerte bei Objekterzeugung mit validen Parametern

`Methode boolean takeReward`
* Übergabeparameter `Hero hero`
    * Erwartete IllegaleArgumentException bei null
    * Ansonsten ...
        * Return true, wenn `actualCount` >= `targetCount`
        * Sonst false

`Methode void register`
* Übergabeparameter `IQuestProgressObserver questProgressObserver`
    * Erwartete IllegaleArgumentException bei null
    * Ansonsten ok
    
# Umsetzung

12.05.2021 - Implementierung Quests (4h)  
16.05.2021 - Implementierung JUnit (4h)

# Postmortem

Die Implementierung der Quests hat sich als einfach gestaltet, da wir für Sachen wie die beispielsweise die Charakter Klasse für den Questgeber und der Darstellung auf der HUD auf bereits erstellte Module zurückgreifen konnten. Lediglich für die Quests und das Observer-Pattern mussten neue Basisklassen angelegt werden.

Zuvor waren keine bzw. wenige Erfahrungen bezüglich Softwaretests (vor allem mit JUnit4) vorhanden. Daher waren für den Einstieg der ersten Tests zunächst Recherchearbeiten notwendig, welche jedoch erfolgreich umgesetzt werden konnten. Das Testen hat bereits früh Schwachstellen im Code gezeigt. Es zeigten sich so beispielsweise vergessene Parameterabfragen und zuvor als void definierte Funktionen wurden zu bool-Funktionen geändert, wodurch diese leichter zu testen sind und zugleich zukünftig gewinnbringender eingesetzt werden können. Auch zeigte sich, dass die derzeitige Implementierung bzw. Nutzung des global verwendeten Loggers `GameEventsLogger` eine Schwachstelle mit sich bringt: Das Loggen wird bei den Tests mitgetestet - was möglichst vermieden werden sollte. In Hinblick auf Praktikumsaufgabe 6 wird diese Tatsache vorgemerkt und soll beim Refactoring angepasst werden.