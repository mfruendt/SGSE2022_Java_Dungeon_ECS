---
title:  'Lerntagebuch zur Bearbeitung von Blatt 1'
author:
- Maxim Fründt (maxim.fruendt@fh-bielefeld.de)
- Dominik Haacke (dominik.haacke@fh-bielefeld.de)
- Benjamin Krüger (benjamin.krueger@fh-bielefeld.de)
...

# Aufgabe

Im Rahmen der Programmiermethoden-Praktika wird im Sommersemester 2021 ein Rogue-like entwickelt.
Als Einstieg wird hierzu auf Grundlage von, sich in Ilias bereitgestellten, Dateien ein Java-Projekt erstellt und bereits Texturen für Spielfiguren beschafft. Darüber hinaus wird mithilfe der PM-Dungeon-Dokumentation bereits ein Held und Funktionalitäten implementiert, sodass sich dieser durch das Dungeon bewegen kann. 

# Ansatz und Modellierung

Ein wichtiger Bestandteil bei Entwicklungsaufgaben ist die Kommunikation und ein kontinuirlicher Wissens- und Informationsaustausch der Teammitglieder. Deshalb wird ein von allen erreichbarer Chat beim Nachrichtendienstleister _Discord_ erstellt.  
Um eine im Team einheitliche Wissensgrundlage zu gewährleisten, sollte sich zunächst jedes Mitglied eigenständig mit der Aufgabenstellung und Dokumentation auseinandersetzen und im Zuge dessen ein lauffähiges Projekt erstellen.
Parallel wird ein Mitglied benannt, welches das Lerntagebuch für Blatt 1 führt. Mit jedem weiteren Aufgabenblatt wird die Zuständigkeit für das Führen des Lerntagebuchs weitergegeben.
Darauf aufbauend werden Ergebnisse und weitere Ideen disskutiert.

# Umsetzung

Zu Beginn erstellt jedes Teammitglied ein Java-Projekt und fügt diesem gemäß der Aufgabenstellung den, in Ilias bereitgestellten, (entpackten) _Asset_-Ordner (Dungeon-Bilder und Daten) und die (entzippte) _pmdungeon.jar_-Datei als Library hinzu. Anschließend werden Texturen für Spielfiguren beschafft und diese eingebunden.
Entsprechend der PM-Dungeon-Dokumentation werden Klassen/Funktionalitäten implementiert, welche ein lauffähiges Spiel erzeugen, bei welchem ein Held durch den Dungeon bewegt werden kann. Für diese Phase wurden 2 Tage angesetzt (13.04 bis 14.04).

Am 15.04 wurden in einem gemeinsamen Zoom-Meeting Ergebnisse und eventuell aufgetretene Fragen und Anregungen disskutiert.
    In diesem Zuge wurden Anpassungen an den Funktionalitäten der Held-Bewegungen gemacht: Zuvor konnte der Held an einer Wand bei gleichzeitiger Betätigung von _vorwärts_ und _rechts_/_links_ seitlich nicht bewegt werden, da bei Heldposition==Wandposition logisch keine Bewegung stattfand. Hierzu wurden weitere Abfragen implementiert, wodurch dies nun möglich ist.
    Texturen und Animationen des Charakters sind in separate Klassen ausgelagert worden. Dies verschafft eine getrennte Logik und bietet somit Vorteile bei zukünftigen Erweiterungen und Fehlersuchen.
    Weiter wurde zur Erleichterung von Erweiterungen/Wartung eine Code-Dokumentation implementiert.
    Darüber hinaus wurde bereits ein Projekt in einem GitLab-Repository angelegt, welches die Basis für alle folgenden Praktika-Aufgaben dient. Gemäß Aufgabenstellung ist eine .gitignore-Datei erstellt worden, wodurch vom Compiler generierte Dateien sowie IDE-Einstellungen nicht versioniert werden.

# Postmortem

Das eigenständige Erstellen des Projektes jedes Teammitglieds stellte zunächst einen insgesamt erhöhten Arbeitsaufwand dar, jedoch konnte hiermit eine gemeinsame Wissensbasis geschafft werden, was für die weitere Entwicklung essenziell ist. Nach eigenständiger Einarbeitung war somit ein effizienter Austausch über weitere Ideen möglich: beispielsweise das Anpassen der Hero-Bewegungen.
Für den Einstieg in das Projekt hat sich die Vorgehensweise somit als positiv herausgestellt. Alle weiteren Aufgaben sollten hingegen in separate Unteraufgaben gegliedert und den einzelnen Teammitgliedern zugeordnet werden, um die Effizienz zu erhöhen.