---
title:  'Lerntagebuch zur Bearbeitung von Blatt 6'
author:
- Maxim Fründt (maxim.fruendt@fh-bielefeld.de)
- Dominik Haacke (dominik.haacke@fh-bielefeld.de)
- Benjamin Krüger (benjamin.krueger@fh-bielefeld.de)
...

# Aufgabe

Zunächst soll das Kampfsystem erweitert werden, indem Fernkampf für den Helden und Monster implementiert wird. Dies umfasst, dass die Charactere eine Angriffsweite erhalten, wodurch Kämpfe (Schadenszufuhr) aus der Distanz möglich werden. Hierzu sollen unterschiedliche Waffen (Bogen, Zauberstab) erstellt werden, welche entsprechend unterschiedliche Reichweiten bieten. Die Wirksamkeit/Genaugkeit sol hierbei mit zunehmender Distanz abnehmen.  

Weiter soll das Verhalten des Monster erweitert werden. Hierzu werden drei unterschiedliche Bewegungen und Angriffsstrategien entwickelt. Zusätzlich soll ein Monster implementiert werden, welches zwischen Nah- und Fernkampf wechseln kann.  

Dies ist die letzte Aufgabe im Praktikum. Daher wird Refactoring betrieben und im Zuge dessen Bad-Smells identifiziert, die Dokumentation bei Bedarf nachgetragen/angepasst und im Sinne des OOP-Gedankens gemeinsame Eigenschaften von Klassen in Oberklassen vereint. Zusätzlich soll die Kollisionserkennung durch Einbindung einer Hashtable verbessert werden.

# Ansatz und Modellierung

## Fernkampf

### Generelles

Für den Fernkampf musste unsere bisherige Klasse `Weapon` erweitert werden. `Weapon` wurde abstract gemacht, und hält weiterhin die Basismethoden und -attribute für die Waffen. Neu dazu gekommen sind jetzt die Klassen `MeleeWeapon` für Nahkampfwaffen und `RangedWeapon` für Fernkampfwaffen. Um zwischen den Waffentypen zu unterscheiden, gibt es zusätzlich auch das Enum `WeaponType` mit den Werten `MELEE` und `RANGED`. `MeleeWeapon` bleibt dabei recht unverändert, gibt lediglich den WeaponType `MELEE` in der überschriebenen Methode `WeaponType getWeaponType()` zurück.

### RangedWeapon

`RangedWeapon` enthält alle Parameter für das zu schießende Object der Klasse `Projectile` und die Schusslogik.

**Attribute**
* `Texture projectileTexture`: Textur für das zu schießende `Projectile`.
* `final int COOLDOWN`: Cooldown zwischen den einzelnen Schüssen.
* `int cooldownTimer`: Cooldowntimer für den Schusscooldown.
* `boolean isCooldown`: Flag, ob der Cooldown aktiv ist.
* `float speed`: Geschwindigkeit für das zu schießende `Projectile`.
* `float damageFalloff`:  Schadensverlust für das zu schießende `Projectile`.
* `float accuracyFalloff`:  Genauigkeitsverlust für das zu schießende `Projectile`.
* `float distance`: Schussdistanz für das zu schießende `Projectile`.
* `boolean unlimitedDistance`: Flag, die festlegt, ob das zu schießende `Projectile` unendlich weit fliegen kann.
* `float projectileSize`: Größe für die Hitbox für das zu schießende `Projectile`.

Außer dem Konstruktor gibt es "getter" für die Parameter und folgende Methoden für die Schusslogik:

**Methoden**
* `boolean canShoot()`: Gibt zurück, ob geschossen werden kann und setzt in dem Fall die cooldown Parameter entsprechend.
* `void updateCooldownTimer()`: Zählt `cooldownTimer` runter, und falls dieser abgelaufen ist, wird der `isCooldown` auf false gesetzt.

### Projectile

`Projectile` enthält alle Parameter zum geschossenen Object und die Logik für die Schadens- und Flugbahnberechnung.

**Attribute**
* `Point currentPosition` : Die aktuelle Position im Dungeon.
* `double rotation` : Die aktuelle Rotation in Grad.
* `float speed` : Die Geschwindigkeit.
* `float distance` : Die verbleibende Distanz, falls diese begrenzt ist.
* `float damageFalloff` : Der Schadensverlust pro Bewegungsschritt.
* `float accuracyFalloff` : Der Genauigkeitsverlust pro Bewegungsschritt.
* `float damage` : Der aktuelle Schaden.
* `float size` : Die Größe für die Kollisionserkennung.
* `boolean unlimitedDistance` : Flag, die festlegt, ob das `Projectile` unendlich weit fliegen kann.
* `boolean isDestroyed` : Flag, die festlegt, ob das `Projectile` zerstört wurde.
* `DungeonWorld currentDungeonWorld` : Die aktuelle Dungeon Welt.
* `Texture texture` : Die Textur des `Projectile`.

Neben dem Konstruktor und den "getter" Methoden implementiert `Projectile` noch die Interfaces `IDrawable` und `IEntity`, sowie die folgenden Methoden:

**Methoden**
* `boolean checkForWall(float directionX, float directionY, float speed)`: Prüft iterativ, ob sich zwischen den Bewegungspunkten eine Mauer befindet.
* `void move(float speed)`: Bewegt das `Projetile` in Abhängigkeit von der `rotation` und dem übergebenen `speed`.
* `void updateParameter()`: Updatet `distance`, `damage` und `rotation` in Abhängigkeit von `speed`, `damageFalloff` und `accuracyFalloff`.
* `void destroy() `: Zerstört das `Projetile`.

### Andere Klassen

Die Klasse `GameHandler` wurde entsprechend erweitert, damit auch Objects der Klasse `Projectile` richtig verwaltet werden und die Kollisionserkennung durchgeführt werden kann. Der `Hero` wurde um das Attribut `Projectile projectile` erweitert, das nach einem Schuss entsprechend gesetzt wird. Die Methode `handleAttacks()` wurde erweitert, hier wird jetzt, falls eine Waffen equipt ist geprüft, um welchen `WeaponType` es sich handelt, und die entsprechende Aktion ausgeführt.

## Schlaue Monster

Um den Monstern verschiedene Angriffs- und Bewegungsstrategien zu ermöglichen, wurde das Strategy-Pattern verwendet. Dafür wurde zunächst das Interface `IAttackStrategy` generiert, welches die Funktionen `boolean move(Hero hero, Monster monster)` und `boolean attack(Hero hero, Monster monster)` enthält. Über diese Funktionen wird die Bewegung und das Angriffsverhalten des Monsters ausgeführt. Übergeben wird jeweils der Held, welcher vom Monster anvisiert wird und das Monster selbst. Ausgehend davon sind drei Angriffsstrategien für die Monster umgesetzt worden.

- `MeleeAttackStrategy`: Diese Strategie lässt das Monster den Helden in Reichweite verfolgen, wenn das Monster nahe genug ist, wird es angrifen.
- `RangedAttackStrategy`: Mit dieser Strategie wird das Monster sich nicht mehr bewegen, wenn der Held in Reichweite ist und den Fernkampfangriff beginnen.
- `PassiveAttackStrategy`: In dieser Strategie wird das Monster nicht angreifen und sich willkürlich im Dungeon bewegen.

Für die Umsetzung ist eine dritte Variante der Monsterklasse angelegt worden, das Monster `MiniBoss`, welches eine höhere Gesundheit und Reichweite hat. Die Strategien wurden dabei wie folgt zugewiesen:

- `EasyMonster`: Dieses Monster wird die `PassiveAttackStrategy` anwenden, bis der Held es angreift, wodurch es zur `MeleeAttackStrategy` wechselt.
- `HardMonster`: Das schwere Monster wird ebenfalls die `PassiveAttackStrategy` anwenden, bis der Held in Erfassungsreichweite ist. Sobald dies der Fall ist, wechselt es automatisch zur `MeleeAttackStrategy`.
- `MiniBoss`: Der Miniboss befindet sich ebenfalls zu beginn in der `PassiveAttackStrategy`. Sobald der Held in Erfassungsreichweite ist, wird in die `RangedAttackStrategy` gewechselt. Kommt der Held dabei noch näher, wird in die `MeleeAttackStrategy` gewechselt.

## Refactoring
    
### Grundlegendes zu Testimplementierung
In der vorangegangenen Praktikumsaufgabe sind Testfälle für Quests implementiert worden. Da Refactoringschritte stehts durch wiederholte Tests validiert werden sollten, werden nun zunächst weitere Testfälle für alle Klassen definiert.

### Grundlegendes zu Bad Smells
Bad Smells (zu lange Methoden, zu viele Übergabeparameter, etc.) werden mit den in der Vorlesung vorgestellten Metriken ausfindig gemacht. Um die Prüfung der Metriken nicht manuell durchführen zu müssen, wird in der IDE die Extension `Checkstyle for Java` installiert.
* Es wurde das `Naming` überprüft und gegebenfalls angepasst
* Es wurden Methodenparameter mit final als konstante Übergabeparameter definiert, um ein Ändern dieser zu verhindern.
* Es wurden `TODO-Kommentare` entfernt.
* Es wurden `JavaDoc` überprüft und angepasst.
* Es wurde der Typ von Methoden gegebenfalls verändert, um diese besser Valdidieren zu können: `void` -> `boolean`/`object`.

### Objektorientierung
Bereits in den vorherigen Praktikumsaufgaben wurde darauf geachtet, wiederholte/redundante Methoden- und Attributedeklarationen/-definitionen beispielsweise in einer Oberklasse zu vereinigen. So haben wir bereits zu Beginn eine Oberklasse Charakter implementiert, um verschiedene Charaktere wie den Helden und verschiedene Monster (ebenfalls mit der Oberklasse Monster) zu implementieren.

Im Zuge des Refactoring wurden jedoch für die, zu Beginn der Aufgabe, implementierten Klassen `MeleeWeapon` und `RangedWeapon` die Kindklassen `Bow`, `Staff` und `RegularSword` erzeugt, um unter anderem zu vermeiden, bei der Erzeugung von Objekten viele Konstruktor-Parameter definieren zu müssen. Alternativ hätte das Builder-Pattern implementiert werden können, dies stellte jedoch einen "zu hohen" Aufwand dar.

### Kollisionserkennung mittels Hashtabelle

Bisher wurde für jedes Item geprüft, ob der Held in Reichweite des Items ist, um es aufsammeln zu können. Dieser Algorithmus wurde mit einer Hashtabelle ersetzt. Dafür werden beim Laden des Levels alle Items in eine Hashtabelle mit ihrer Position als Key eingetragen. Um mit der Hashtabelle zu erkennen, ob ein Item in Reichweite des Helden ist, wird die Position des Helden ebenfalls als Key übergeben und alle Einträge, welche denselben Key haben zurückgegeben. Für die Umsetzung der Hashtabelle ist eine neue Klasse `CollisionHandler` angelegt worden. Diese enthält eine Liste `entityHashTable`, welche die Hashtabelle darstellt und folgende Funktionen:

- `void clear()`: Leert die Hashtabelle.
- `boolean addEntity(Point key, T entity)`: Füge das gegebene Entity in die Hashtabelle ein, mit der Position als Key.
- `boolean removeEntity(Point key, T entity)`: Entferne das gegebene Entity aus der Hashtabelle, mit der Position als Key.
- `List<T> getCollisions(Point key)`: Finde alle Kollisionen zwischen dem gegebenen Key und den Einträgen in der Hashtabelle.

Zur Berechnung des Index ist zudem die private Funktion `int calculateHash(Point key, int numberOfHashCollisions)` erstellt worden. Die Berechnung des Index ergibt sich zu ((X + Y) + i²) % Tabellengröße.

### Weiteres
Weiter musste zur Instanzierung aller Items die entsprechende Textur des Items übergeben werden. Dies stellte sich als überflüssig und auch fehleranfällig dar, weil der Anwender bspw. auch einer `Potion` die Textur eines `RegularSword` geben konnte. Der Übergabeparameter `texture` wurde somit bei allen Items entfernt und wird nun statisch für jedes Item entsprechend gesetzt, indem der Konstruktor der `abstrakten Oberklasse Item` mit der entsprechenden Textur aufgerufen wird (bspw. für `Potion` im Konstruktor: _super(ItemType.POTION)_).

Während den vorangegangen Praktikumsaufgaben stellte sich heraus, dass die bis Dato implementierte Struktur der Items das Handling dieser (Item im Dungeon erzeugen, diese aufheben und uns Heldeninventar hinzufügen und diese wieder in den Dungeon droppen) erschwert. Dies lag zunächst daran, dass zur Instanzierung eines im Dungeon befindlichen Item (also ein `DungeonItem`) die dafür benötigten Attribute entsprechend und die nicht benötigten Attribute mit 0/null im Konstruktor definiert werden mussten. Zur Instanzierung eines `DungeonItem` mussten demnach sehr viele Überparameter definiert werden. Im Konstruktor selbst musste dann differenziert werden, welche Parameter wie definiert wurden und dementsprechend ein Item des entsprechendes Typs erzeugt werden.
Dies wurde gelöst, in dem nun ein `DungeonItem` basierend auf ein Item (`Potion`, `RegularSword`, `Bow`, etc.) erzeugt wird. Dies erleichtert dem Anwender die Erzeugung eines `DungeonItems`: _new DungeonItem(new Potion()_. Darüber hinaus ist auch das aufheben dieser intuitiver, indem mittels Getter-Methode das Item erhalten werden kann: _DungeonItem.getItem()_.

# Umsetzung

27.05.2021 - Implementierung Fernkampf (4h)  
21.05.2021 - Implementierung schlaue Monster (3h)  
28.05.2021-30.05.2021 - Refactoring (16 h)

# Postmortem

Für die Umsetzung vom Fernkampf konnte man die schon vorhandene Kollisionserkennung gut nutzen. Das Movement in Abhängigkeit von der Drehung musste komplett neu implementiert werden, da dies bisher nicht benötigt wurde. Als Problem ist uns noch aufgefallen, als wir die Drehung und Skalierung der Grafiken anpassen wollten, dass wir Texturen nicht drehen können und scheinbar gleiche Texturen, die per equals verglichen werden, nicht gleich sind (Bug?). 

Für die Umsetzung der schlauen Monster konnte auf die bisherigen Strukturen zur Verwaltung der Monster zugegriffen werden. Für die Anwendung des Strategy-Patterns wurde die Logik für das Bewegen und Angrifen der Monster in einzelne Strategy-Klassen ausgelagert.

Durch Refactoring konnte die "Qualität des Codes" verbessert werden. Für zukünftige Projekte hingegen sollte häufiger bzw. zyklisch Refactoring betrieben werden, da sich dies ansonsten als äußerst aufwändig erweist, da die "Kette" von notwendigen Änderungen mit Umfang des Codes zunimmt.