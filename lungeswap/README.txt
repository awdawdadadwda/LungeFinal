Lunge Swap (Fabric, Client-only, MC 1.21.11)

Ablauf: Keybind (Standard R) -> Slot 9 (Speer) -> Lunge-Angriff -> zurueck auf Slot 3 (Mace)
Slots/Delays aendern: src/main/java/com/example/lungeswap/LungeSwapClient.java

Bauen:
1. Java 21 installieren
2. Gradle Wrapper aus dem Fabric-Template (fabricmc.net/develop) in diesen Ordner kopieren
   (gradlew, gradlew.bat, Ordner gradle/) ODER lokal installiertes Gradle nutzen
3. Versionen in gradle.properties mit fabricmc.net/develop abgleichen
4. ./gradlew build   (Windows: gradlew.bat build)
5. JAR liegt in build/libs/ (die ohne -sources) -> in den mods-Ordner legen, Fabric API muss installiert sein

OHNE LOKALE INSTALLATION (GitHub Actions):
1. Neues GitHub-Repo erstellen, den gesamten Inhalt dieses Ordners hochladen (inkl. .github)
2. Reiter "Actions" -> Workflow "build" laeuft automatisch (oder "Run workflow")
3. Fertigen Lauf oeffnen -> unten bei "Artifacts" lungeswap-jar herunterladen -> JAR in den mods-Ordner
