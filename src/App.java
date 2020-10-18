import java.util.*;
import java.util.Arrays;

class Tuple<TypeKey, TypeValue> {
    public final TypeKey key;
    public final TypeValue value;
    public Tuple(TypeKey key, TypeValue value) {
        this.key = key;
        this.value = value;
    }
}

class KniffelSheet {
    String[] part1 = {"Einer", "Zweier", "Dreier", "Vierer", "Fünfer", "Sechser"};
    String[] part2 = {"1 Paar", "2 Paar", "Drei Gleiche", "Vier Gleiche", "Volles Haus", "Kleine Straße", "Große Straße", "5x gleiche Augenzahl", "Chance"};    
}

class Goal {
    Tuple<String, Integer> gameset;
}

class Sheet {
    ArrayList<Goal> gameSheet = new ArrayList<>();
}

class Player {
    String name;
    Sheet personalSheet;
    Integer sumPart1 = 0;
    Integer partSumPart1 = 0;
    Integer sumPart2 = 0;
    Integer bonusPart1 = 0;
    Integer sum = 0;
}

class Players {
    ArrayList<Player> playerSheet = new ArrayList<>();
}


class Kniffel {

    private static int addPlayer(Players actualPlayers, String name) {
        Integer currentSize = actualPlayers.playerSheet.size();
        Player player = new Player();
        player.name = name;
        player.personalSheet = new Sheet();        
        actualPlayers.playerSheet.add(currentSize, player);        
        return actualPlayers.playerSheet.size();
    }
    
    private static boolean addGoal(Players actualPlayers, Integer playerNumber, String goalName, Integer goalValue) {
        Boolean keyExists = false;
        for (int i = 0; i < actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.size(); i++) {
            if (actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(0).gameset.key.equals(goalName)) {
                keyExists = true;
            }
        }        
        
        if (keyExists) {
            System.out.println("Wurf existiert");
            return false;
        }
        else {
            Goal newGoal = new Goal();
            newGoal.gameset = new Tuple<>(goalName, goalValue);        
            Sheet oldSheet = actualPlayers.playerSheet.get(playerNumber).personalSheet;
            oldSheet.gameSheet.add(newGoal);
            actualPlayers.playerSheet.get(playerNumber).personalSheet = oldSheet;
            return true;
        }
    }

    private static int[] scoreTable(Players actualPlayers, Integer playerNumber, boolean log) {
        final String HORIZONTAL_RULER = "-----------------------";
        final String DASHED_RULER     = "- - - - - - - - - - - -";
        final String DOUBLE_RULER     = "=======================";

        String[] listPart1 = new KniffelSheet().part1;
        String[] listPart2 = new KniffelSheet().part2;

        int[] table = new int[listPart1.length + listPart2.length + 5];

        if (log) {
            System.out.println(actualPlayers.playerSheet.get(playerNumber).name);
            System.out.println(HORIZONTAL_RULER);
        }

        for (int j=0; j < listPart1.length; j++) {
            if (log) System.out.print(listPart1[j] + " : ");
            for (int i = 0; i < actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.size(); i++) {                
                if (listPart1[j].equals(actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.key)) {
                    if (log) System.out.print(actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.value);  
                    table[j] = actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.value;      
                }
                else {
                    // Not found
                }
            }
            if (log) System.out.println("");
        }

        if (log) {
            System.out.println(HORIZONTAL_RULER);
            System.out.println("Teilsumme Teil 1: " + actualPlayers.playerSheet.get(playerNumber).partSumPart1);
            System.out.println("Bonus Teil 1: " + actualPlayers.playerSheet.get(playerNumber).bonusPart1);
            System.out.println(DOUBLE_RULER);
            System.out.println("Summe Teil 1: " + actualPlayers.playerSheet.get(playerNumber).sumPart1);
            System.out.println(DASHED_RULER);
        }
        table[listPart1.length + 1] = actualPlayers.playerSheet.get(playerNumber).partSumPart1;
        table[listPart1.length + 2] = actualPlayers.playerSheet.get(playerNumber).bonusPart1;
        table[listPart1.length + 3] = actualPlayers.playerSheet.get(playerNumber).sumPart1;

        for (int j=0; j < listPart2.length; j++) {
            if (log) System.out.print(listPart2[j] + " : ");
            for (int i = 0; i < actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.size(); i++) {                
                if (listPart2[j].equals(actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.key)) {
                    if (log) System.out.print(actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.value);  
                    table[listPart1.length + 3 + j] = actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.value;      
                }
                else {
                    // Not found
                }
            }
            if (log) System.out.println("");
        }
        if (log) {
            System.out.println(HORIZONTAL_RULER);
            System.out.println("Summe Teil 2: " + actualPlayers.playerSheet.get(playerNumber).sumPart2);
            System.out.println(DOUBLE_RULER);
            System.out.println("Gesamtsumme : " + actualPlayers.playerSheet.get(playerNumber).sum);
            System.out.println("");
        }
        table[listPart1.length + 3 + listPart2.length + 1] = actualPlayers.playerSheet.get(playerNumber).sumPart2;
        table[listPart1.length + 3 + listPart2.length + 1] = actualPlayers.playerSheet.get(playerNumber).sum;

        return table;
    }

    private static void sumParts(Players actualPlayers, Integer playerNumber) {        
        String[] listPart1 = new KniffelSheet().part1;
        String[] listPart2 = new KniffelSheet().part2;
        
        Integer part1 = actualPlayers.playerSheet.get(playerNumber).partSumPart1;
        Integer part2 = actualPlayers.playerSheet.get(playerNumber).sumPart2;
        
        for (int i = 0; i < actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.size(); i++) {
            String key = actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.key;
            if (Arrays.asList(listPart1).contains(key)) {
                part1 += actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.value;
            }
            else {
                // Not found
            }
            if (Arrays.asList(listPart2).contains(key)) {
                part2 += actualPlayers.playerSheet.get(playerNumber).personalSheet.gameSheet.get(i).gameset.value;
            }
            else {
                // Not found
            }
        }
        actualPlayers.playerSheet.get(playerNumber).partSumPart1 = part1;
        actualPlayers.playerSheet.get(playerNumber).sumPart2 = part2;

        // Calculate bonus
        if (part1 > 62 ) actualPlayers.playerSheet.get(playerNumber).bonusPart1 = 35;

        actualPlayers.playerSheet.get(playerNumber).sumPart1 = part1 + actualPlayers.playerSheet.get(playerNumber).bonusPart1;
        actualPlayers.playerSheet.get(playerNumber).sum = part1 + actualPlayers.playerSheet.get(playerNumber).bonusPart1 + part2;
    }

    public static void main(String args[]) {
        Players knifflers = new Players();
        int[] table;
        
        addPlayer(knifflers, "Jarvis");
        addPlayer(knifflers, "Darwin");
        addPlayer(knifflers, "Thorsten");

        addGoal(knifflers, 0, "Einer", 11);
        addGoal(knifflers, 0, "Zweier", 22);
        
        addGoal(knifflers, 1, "Dreier", 44);
        addGoal(knifflers, 1, "Fünfer", 44);

        addGoal(knifflers, 2, "Chance", 44);
        addGoal(knifflers, 2, "Volles Haus", 44);

        for (int x = 0; x < knifflers.playerSheet.size(); x++) {
            sumParts(knifflers, x);
            table = scoreTable(knifflers, x, true);             
        }
    }


}