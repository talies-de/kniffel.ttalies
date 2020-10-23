public class Language {    
    static String appName = "Kniffel";
    static String welcome = "<html><b>Spielen wir eine Runde Kniffel?</b> <br><br> Füge ein paar Spieler hinzu und starte das Spiel!</html>";

    static String ones = "Einer";
    static String twos = "Zweier";
    static String threes = "Dreier";
    static String fours = "Vierer";
    static String fives = "Fünfer";
    static String sixes = "Sechser";

    static String threeEquals = "Drei Gleiche";
    static String fourEquals = "Vier Gleiche";
    static String fullHouse = "Volles Haus";
    static String smallStreet = "Kleine Straße";
    static String bigStreet = "Große Straße";
    static String kniffel = "Kniffel";
    static String chance = "Chance";

    // only used for debugging purposes
    static String horizontalRuler = "-----------------------";
    static String dashedRuler     = "- - - - - - - - - - - -";
    static String doubleRuler     = "=======================";

    static String partialSumPart1 = "Teilsumme Teil 1";
    static String sumPart1 = "Summe Teil 1";
    static String sumPart2 = "Summe Teil 2";
    static String bonus = "Bonus Teil 1";
    static String totalSum = "Gesamtsumme";

    static String dice = "<html><b>Würfel</b><html>";
    static String diceThrow = "Wurf";
    static String rollAgain = "Neu würfeln";
    static String rollNow = "Jetzt würfeln";    

    static String game = "Spiel";
    static String help = "Hilfe";
    static String start = "Starten";    
    static String player = "Spieler/in";
    static String show = player + " anzeigen";
    static String add = "Hinzufügen";
    static String remove = "Entfernen";
    static String addAI = "Computer hinzufügen";
    static String about = "Über";
    static String quit = "Beenden";
    static String manual = "Anleitung";
    
    static String added = "hinzugefügt";
    static String addnew = "Neue/r";
    static String addempty = "Name darf/kann nicht leer sein!";

    static String name = "Name";

    static String addButton = "+";

    static String aboutMessage = "(c) 2020 Thorsten Talies";
    static String aboutTitle = "Über " + appName;

    static String manualMessage = "Kniffel Spielregeln";
    static String manualTitle = "Anleitung";

    static String quitMessage = "Wirklich beenden?";
    static String quitTitle = "Beenden?";

    static String removePlayerMessage = "Welche/r Spieler/in soll gelöscht werden?";
    static String removePlayerTitle = player + " " + remove.toLowerCase();
    static String noPlayersMessage = "Keine " + player + " vorhanden.";
    static String singlePlayerMessage = "Der/die letzte " + player + " kann nicht gelöscht werden.";

    static String playing = "Es spielen derzeit";

    static String endGame = "Punktestand";
    static String winner = "Gewinner/in";
    static String with = "mit";
    static String winnerIs = "Gewonnen hat ";
    static String points = "Punkten";

    static String noDiceThrowMessage = "Es wurde noch nicht gewürfelt.";
    static String noDiceThrowTitle = "Kein Wurf";

    private Language() {
        throw new IllegalStateException("Utility class");
    }
} 