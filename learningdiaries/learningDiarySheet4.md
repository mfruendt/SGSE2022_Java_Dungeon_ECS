---
title:  'Lerntagebuch zur Bearbeitung von Blatt 4'
author:
- Maxim Fründt (maxim.fruendt@fh-bielefeld.de)
- Dominik Haacke (dominik.haacke@fh-bielefeld.de)
- Benjamin Krüger (benjamin.krueger@fh-bielefeld.de)
...

# Aufgabe

Zu Beginn soll nun ein HUD (heads-up-display) implementiert werden, welches mindestens die Lebenspunkte, das Level, den Levelfortschritt und das Inventar des Helden sowie den Inhalt von Schatztruhen anzeigt.  

Im Zuge dessen soll also zusätzlich ein Erfahrungssystem implementiert werden, welches ein Levelaufstieg des Helden durch das Besiegen von Monstern vorsieht. Levelaufstiege sollen sich hierbei auf den Helden in Form von bspw. Erweiterung der Lebenspunkte auswirken. Des Weiteren erhält der Held auf Level 2 und 5 weitere Fähigkeiten, wie zum beispiel Zauber und Sprinten.  

Darüber hinaus sollen im Dungeon Fallen verteilt werden, welche durch Kontakt mit Monstern oder dem Helden sowie beim Ablegen von Items auf diesen ausgelöst werden. Fallen können hierbei sichtbar aber auch unsichtbar sein, sowie verschiedene Auswirkungen, wie Lebenspunkteentzug und Monster-Beschwörung, beinhalten. Weiter sollen sich Fallen darin unterscheiden, ob diese einmal oder mehrfach aktiviert werden können.

# Ansatz und Modellierung

## Erfahrungssystem

Um ein Erfahrungssystem des Helden zu ermöglichen, wird zunächst die `Hero`-Klasse um ein Attribut und Methoden erweitert.

* `float skillLevel`: Das aktuelle Level des Helden. Level von Helden werden üblicherweise ganzzahlig gewertet, Level-Fortschritte hingegen in %. Durch Verwendenung des Typs `float` kann dieses Attribut zum einen zur Interpretation des Levels sowie des Level-Fortschritts verwendet werden. Das aktuelle Level wird demnach durch einen Cast von float-Level zu einem int ermittelt. Der Level-Fortschritt durch eine Berechnung.
* `float getSkillLevel()`: Liefert das aktuelle Level des Helden.
* `void addExperience(float experience)`: Fügt dem Helden weitere Erfahrungspunkte in der Höhe `experience` hinzu. Wird verwendet, wenn der Held, ein Monster getötet hat.

Um zu definieren, wie viel Erfahrungspunkte der Held für jedes getöte Monster erhält, wird jeder Art Monster ein konstanter, spezifischer Wert (`float MONSTER_EXPERIENCE_AWARD`) diesbezüglich zugeordnet. Der Wert kann mithilfe einer dafür vorgesehenen getter-Methode (`float getExperiencePoints()`) abgefragt werden.  
Im `GameHandler` werden dem Helden demnach bei Tötung eines Monsters abhängig von der Art des Monsters Erfahrungspunkte hinzugefügt.

## HUD

Aufgrund der Wartbarkeit und Erweiterbarkeit wird die HUD zunächst in mehrere Segmente unterteilt, welche jeweils in Form der Klassen `InventoryHud` und `PlayerStatsHud` vergesehen werden. Die Klassen sind in sich gekapselt - weisen demnach lediglich Attribute und Methoden auf, um gezeichnet zu werden. So bestehen möglichst wenig Abhängigkeiten zu anderen Modulen, was den Aufwand für Änderungen und Fehlersuchen mindert.  

### PlayerStatsHud

Diese Klasse dient zur separaten Darstellung von Heldeninfos (Level, Level-fortschritt, Lebenspunkte). Umgesetzt wird dies durch Verwendung der Bibliothek `libgdx`, welche unter anderem definierte Farben, Label und Komponenten zum Rendern von Textelementen bietet. Darüber hinaus werden konstante Anzeigetexte und -eigenschaften definiert.

**Attribute**:  
* `TextStage statsTexts`: Objekt zur Definition/Darstellung der Label
* `int STATS_FONT_SIZE`: Konstante Textgröße
* `int STATS_Y_OFFSET`: Konstanter Y-Offset (Abstand zwischen HUD-Elementen)
* `int STATS_X0`: Konstanter Startpunkt für X
* `int STATS_Y0`: Konstanter Startpunkt für Y
* `String STATS_FONT_PATH`: Konstanter Pfad zur Schriftart-Datei. Hierzu wird eine Datei verwenden, welche aus dem Internet beschafft und dem Ordner `assets` hinzugefügt wird.
* `Color STATS_COLOR`: Konstante Farbe der Texte
* `Label healthLabel`: Label zur Darstellung der aktuellen Lebenspunkte
* `Label skillLabel`: Label zur Darstellung des aktuellen Helden-Level
* `Label skillProgressLevel`: Label zur Darstellung des Levelfortschritts
* `String STATS_TEXT_HP`: Konstanter Text zur Anzeige der Lebenspunkte
* `String STATS_TEXT_LEVEL`: Konstanter Text zur Anzeige des Helden-Level
* `String STATS_TEXT_LEVEL_PROGRESS`: Konstanter Text zur Anzeige des Levelfortschritts

Neben dem Konstruktor wird zunächst lediglich eine Methode zur Darstelleung der Elemente benötigt:

**Methoden**
* `void draw(float health, float skillLevel)`: Zeichnet die Label zur Darstellung der Heldeninfos (neu), unter Verwendung der variablen Werte `health` (aktuelle Lebenspunkte) und `skillLevel` (Level des Helden). Mithilfe des `skillLevel` wird der Levelfortschritt berechnet und ebenfalls angezeigt.

### InventoryHud

Diese Klasse dient zur separaten Darstellung von Helden- und (geöffneter) Schatztruhenitems (abhängig vom parametrisierten Typ). Wie auch die Klasse `PlayerStatsHud` wird auch hier die Bibliothek `libgdx` genutzt sowie konstante Anzeigetexte und -eigenschaften definiert. 

**Attribute**:  
* `TextStage statsTexts`: Objekt zur Definition/Darstellung der Label
* `int INV_SLOT_FONT_SIZE`: Konstante Textgröße
* `int INV_SLOT_X0_PLAYER`: Konstanter Startpunkt für X (Heldenitems)
* `int INV_SLOT_X0_CHEST`: Konstanter Startpunkt für X (schatztruhenitems)
* `int INV_SLOT_Y0`: Konstanter Startpunkt für Y
* `int INV_SLOT_Y_OFFSET`: Konstanter Y-Offset (Abstand zwischen HUD-Elementen)
* `String INV_SLOT_FONT_PATH`: Konstanter Pfad zur Schriftart-Datei
* `Color INV_SLOT_COLOR`: Konstante Farbe der Texte
* `List<Label> inventorySlots`: Items des Inventars repräsentiert als Liste von Label
* `InventoryTypes inventoryType`: Inventartyp (Heldeninventar oder Schatztruhe)
* `String INV_SLOT_PREFIX`: Konstanter Prefix zur Darstellung eines befüllten Slots
* `String INV_SLOT_EMPTY`: Konstanter Text zur Darstellung einer leeren Slots
* `String INV_TITLE_PLAYER`: Konstanter Titel zur Darstellung der Heldenitems
* `String INV_TITLE_CHEST`: Konstanter Titel zur Darstellung der Schatztruhenitems

Neben dem Konstruktor wird auch hier eine Methode zur Darstelleung der Elemente benötigt. Darüber hinaus muss die Möglichkeit bestehen, die Label-Liste zur Darstellung der items anhand eines Inventars zu definieren.

**Methoden**
* `void draw()`: Zeichnet die Label zur Darstellung der Heldeninfos (neu).
* `<T extends Inventory>void setInventoryContent(T inventory)`: Überführt die in `inventory` befindlichen Items in die darzustellende Labelliste.

Die in `InventoryHud` und `PlayerStatsHud` definierten HUD-Segmente werden an geeigneter Stelle angezeigt/verwaltet. Hierzu wird die Klasse `HudHandler` vorgesehen. Mithilfe eines Attributes der Klasse `HUD` (Teil von `pmdungeon.jar`) werden Heldenitems, Schatztruhenitems und Heldenstatus gezeichnet/angezeigt. 

### HudHandler

**Attribute**

* `IPlayerHudEntity playerEntity`: Verfolgbare Spielerinstanz. Das Interface `IPlayerHudEntity` definiert Methoden, um Objekte verfolgbar zu machen und dient somit dazu, anzuzeigende variable Werte zu erhalten. Wird auch der Klasse Hero implementiert, um dessen Status an die Hud zu binden.
  *  `float getHealth()`: Liefert Lebenspunkte 
  *  `float getSkillLevel()`: Liefert Level
* `HUD hud`: Instanz für HUD-Handling
* `PlayerStatsHud statsHudElement`: Instanz zur Darstellung des Heldenstatus
* `InventoryHud invHudElement`: Instanz zur Darstellung des Heldeninventars
* `InventoryHud chestInvHudElement`: Instanz zur Darstellung eines Schatztruheninventars

**Methoden**

* `void update()`: Aktualisierung der HUD / Neu zeichnen
* `<T extends Inventory> void displayInventory(T inventory, InventoryTypes inventoryType)`: Definiert Inventaritems abhängig vom Typen (Held- oder Schatztruheninventar)
* `boolean addPlayerEntity(IPlayerHudEntity entity, int inventorySize)`: Fügt neue, verfolgbare Heldeninstanz hinzu 
* `void removePlayerEntity()`: Entfernt verfolgbare Heldeninstanz 
* `boolean addChestView(int inventorySize)`: Initialisiert eine Truhenansicht mit `inventorySize`-Elementen. Diese ist von Truhe zu Truhe unterschiedlich, weshalb die Anzahl der Items hier definiert wird.
* `void removeChestView()`: Entfernt die Itemansicht einer Schatztruhe. Wird bspw. benötigt, wenn die Schatztruhe wieder geschlossen wird.

Zur Aktivierung/Nutzung des HudHandlers wird eine Instanz dieses Typs im `GameHandler` implementiert und zyklisch in der `endFrame()`-Methode die Hud mithilfe der Funktion `update` verwendet, wodurch die Anzeigesegemente fortlaufend aktualisiert werden. 

## Skills

Der Held erlangt mit steigendem Level 2 neue Fähigkeiten:

* `Teleport`: Der Held kann sich zu einer zufälligen Position teleportieren (ab Level 2).
* `Flash`: Der Held bewegt sich schneller. Umgesetzt wird dies vereinfachend durch einen Sprung in die dabei aktive Bewegungsrichtung (ab Level 5).

Zunächst wird die Klasse `Hero` um die hierfür benötigen Attribute und Methoden erweitert:

### Attribute

* `int TELEPORT_COOLDOWN`: Konstante vordefinierte Teleport-Refreshzeit zur Wiederverwendung der Teleport-Fähigkeit.
* `int teleportTimer`: Timer für die Aktivitätsdauer des Teleportierens. 
* `boolean hasTeleported`: Trigger, ob Held teleportiert wurde.
* `float FLASH_DISTANCE`: Bei Objekterstellung einmalig, fest definierte Sprungdistanz
* `int FLASH_COOLDOWN`: Konstante vordefinierte Flash-Refreshzeit zur Wiederverwendung der Flash-Fähigkeit.
* `int flashTimer`: Timer für die Aktivitätsdauer des Flashs.
* `boolean hasFlashed`: Trigger, ob Held gesprungen ist.

### Methoden

* `void updateSkillTimer()`: Updated (Wertaktualisierung) die Timer (`teleportTimer` und `flashTimer`) der Fähigkeiten
* `void handleSkills()`: Führt Fähigkeiten des Helden entsprechend der Benutzereingaben aus, wenn der Held das dafür benötigte Level aufweist. Bei einem Teleport (Taste "K") wird die aktuelle Position des Helden entsprechend neudefiniert. Wird die Tastenkombination "J + _A/W/S/D_" betätigt, springt der Held um die, bei der Objekterstellung fix definierte, Distanz in diese Richtung weiter vor.

Beide Methoden werden in der `update`-Methode des Helden aufgerufen und somit zyklisch ausgeführt, wodurch fortlaufend auf Benutzung der Skills reagiert wird.

## Fallen

Zur Implementierung von Fallen wird zunächst die Aufzählung `ActivationType` vorgesehen, um, wie in der Aufgabenbeschreibung genannt, Arten von Fallen hinsichtlich ihrer Eigenschaften zu unterscheiden. Es wird differenziert zwischen den Arten:

* `DAMAGE`: Fügt dem Charakter Schaden hinzu
* `TELEPORT`: Teleportiert den Charakter an eine andere Position
* `MONSTER`: Ruft ein Monster herbei

Zur Darstellung dienen die Klassen `TrapTexturen` und `TrapAnimations`, welche zum einen alle für die Traps notwendigen Texturen beinhalten und zum anderen aus diesen Animationen erstellen. Es werden Animationen vorgesehen für:
* `DAMAGE_TRAP_IDLE`: Idle Animation der Schadensfalle
* `DAMAGE_TRAP_ACTIVATING`: Aktivierungsanimation der Schadensfalle
* `DAMAGE_TRAP_ACTIVE`: Aktive Animation der Schadensfalle (Schadensfalle kann Schaden zufügen)
* `DAMAGE_TRAP_DEACTIVATION`: Deaktivierungsanimation der Schadensfalle
* `HOLE_TRAP`: Animation für die Fallgrube und Monster Falle

Fallen werden mithilfe der Klasse `Trap` definiert. Abhängig vom ActivationType, werden mithilfe dieser Objekte definiert, welche dem Charakter Schaden hinzufügen, den Charakter teleportieren oder ein Monster herbei rufen. Die Klasse implementiert IAnimatable und IEntity, um als Objekt im Dungeon verwaltet und gezeichnet zu werden. Um dies umzusetzen, werden die folgendenden Attribute vorgesehen.

* `Point currentPosition`: Aktuelle Position der Falle
* `DungeonWorld currentDungeonWorld`: Dungel level, in dem sich die Falle befindet
* `List<Animation> animations`: Liste aller Animationen der Falle
* `AnimationStates activeAnimation`: Enum-Wert der aktuellen Animation der Falle
* `float size`: Kontaktradius der Falle
* `ActivationType type`: Fallentyp (Schaden, Teleport oder Monster)
* `boolean cloaked`: Wahrheitswert, ob die Falle sichtbar oder unsichtbar ist
* `int acitvations`: Anzahl der Aktivierungen, bevor die Falle zerstört wird
* `boolean unlimited`: Wahrheitswert, ob die Falle unendlich oft aktiviert werden kann. 
* `boolean destroyed`: Falls die Falle zu oft aktiviert wurde, wird sie als zerstört markiert.
* `int INITIAL_DELAY`: Fest definierter Wert. Timerdauer, sobald die Falle getriggert ist. Läuft dieser ab, kann die Falle ihre entsprechende Aktion durchführen.
* `int ACTIVE_TIME`: Fest definierter Wert. Legt fest, wie lange die Falle aktiv bleibt.
* `int COOLDOWN_TIME`: Fest definierter Wert, cooldown Zeitraum nach Aktivierung.
* `int currentDelayTimer`: Timer für die Startverzögerung, bevor die Falle aktiv wird.
* `int activeTimer`: Timer für die Aktivitätsdauer.
* `int coolDownTimer`: Timer für den Cooldown.
* `boolean isActivated`: Trigger, ob Falle ausgelöst worden ist.
* `boolean isActive`: Wird gesetzt, sobald die Starverzögerung abgelaufen ist. Schaltet die Falle scharf.
* `boolean isCooldown`: Wahrheitswert, ob sich die Falle gerade im Cooldown befindet.
* `boolean decremented`: Wahrheitswert, ob schon eine Aktivierung im aktuellen Zyklus abgezogen wurde. 
* `boolean heroHasPotion`: Wahrheitswert, ob der Held die Fähigkeit hat, versteckte Fallen zu sehen.
* `float damage`: Möglicher Schaden, den die Falle zufügt

Um die Attribute setzen und abrufen zu können, werden entsprechend die notwendigen Setter- und Getter-Methoden definiert.  

In der Klasse `Hero` wird zusätzlich ein Attribut `boolean hasTrapPotion` sowie für diesen Zweck benötigte Set- und Get-Methoden definiert. Dieses verleiht dem Helden die Fähigkeit, versteckte Fallen zu sehen.

Fallen werden im GameHandler verwaltet. Hier wird eine Liste `List<Trap> traps` erzeugt und der `EntityController`-Instanz hinzugefügt. Zyklisch (in `endFrame()`) wird für jede Falle geprüft, ob Charaktere (Helden und Monster) Kontakt mit der Falle haben und im Falle dessen typ-spezifische Aktionen durchgeführt (z.B. Schadenserleiden). 

# Umsetzung

03.05.2021 - Implementierung HUD (3h)  
04.05.2021 - Implementierung Erfahrungssystem (0.5h)  
06.05.2021 - Implementierung Skills (1h)  
07.05.2021 - Implementierung Fallen (3h)

# Postmortem

Die Implementierung der HUD war durch das, in Blatt 3 bereits implementierte, Visitor-Pattern zum Loggen von Items ohne großen Aufwand verbunden. Auch das in diesem Zuge notwendige automatische Updaten der Anzeige wurde indirekt bereits in Blatt 3 umgesetzt und konnte somit durch etwas Refactoring (Ergänzung eines Triggers "onInventoryChanged") implementiert werden.  

Bei der Implementierung der Skills, konnte einfach auf die bisherigen Methoden zurückgegriffen werden. Der Teleport ist eine Random Position aus dem Dungeon und der Flash konnte durch eine Abänderung des bisherigen Movements umgesetzt werden.

Die Fallen waren etwas aufwendiger, für den Animationswechsel mit Timern konnten wir aber schon auf die Chest aus dem letzten Blatt zurückgreifen. Ebenfalls konnten wir hier auf die Kontakt Methode zurückgreifen. Etwas länger hat es noch gedauert, die getarnten Fallen zu implementieren, da wir hier keinen Einfluss auf die draw Methoden haben, uns das so über eine Flag machen mussten.