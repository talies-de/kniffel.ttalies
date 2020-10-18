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
    Tuple<String, Integer> set;
}

class Sheet {
    ArrayList<Goal> game = new ArrayList<>();
}

class Player {
    String name;
    Sheet sheet;
    Integer sumPart1 = 0;
    Integer partSumPart1 = 0;
    Integer sumPart2 = 0;
    Integer bonusPart1 = 0;
    Integer sum = 0;
    DiceDeck deck = new DiceDeck();
}

class Players {
    ArrayList<Player> player = new ArrayList<>();
}

class Dice {
    Integer count;
    boolean roll;

    public Dice() {
        this.count = 0;
        this.roll = true;
    }

    public void roll() {
        if (this.roll) {
            this.count = (int)(Math.random() * 6) + 1;        
        }
        else {
            // Roll not permitted
        }
    }

    public int getCount() {
        return this.count;
    }

    public boolean getRollState() {
        return this.roll;
    }

    public void setRollState(boolean state) {
        this.roll = state;
    }
}

class DiceDeck {
    Dice[] dice = new Dice[5];

    public DiceDeck() {
        for (int d = 0; d<5; d++) {
            dice[d] = new Dice();
        }
    }
}

class Kniffel {

    private static int addPlayer(Players actualPlayers, String name) {
        Integer currentSize = actualPlayers.player.size();
        Player player = new Player();
        player.name = name;
        player.sheet = new Sheet();        
        actualPlayers.player.add(currentSize, player);        
        return actualPlayers.player.size();
    }
    
    private static boolean addGoal(Players actualPlayers, Integer playerNumber, String goalName, Integer goalValue) {
        Boolean keyExists = false;
        for (int i = 0; i < actualPlayers.player.get(playerNumber).sheet.game.size(); i++) {
            if (actualPlayers.player.get(playerNumber).sheet.game.get(0).set.key.equals(goalName)) {
                keyExists = true;
            }
        }        
        
        if (keyExists) {
            System.out.println("Wurf existiert");
            return false;
        }
        else {
            Goal newGoal = new Goal();
            newGoal.set = new Tuple<>(goalName, goalValue);        
            Sheet oldSheet = actualPlayers.player.get(playerNumber).sheet;
            oldSheet.game.add(newGoal);
            actualPlayers.player.get(playerNumber).sheet = oldSheet;
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
            System.out.println(actualPlayers.player.get(playerNumber).name);
            System.out.println(HORIZONTAL_RULER);
        }

        for (int j=0; j < listPart1.length; j++) {
            if (log) System.out.print(listPart1[j] + " : ");
            for (int i = 0; i < actualPlayers.player.get(playerNumber).sheet.game.size(); i++) {                
                if (listPart1[j].equals(actualPlayers.player.get(playerNumber).sheet.game.get(i).set.key)) {
                    if (log) System.out.print(actualPlayers.player.get(playerNumber).sheet.game.get(i).set.value);  
                    table[j] = actualPlayers.player.get(playerNumber).sheet.game.get(i).set.value;      
                }
                else {
                    // Not found
                }
            }
            if (log) System.out.println("");
        }

        if (log) {
            System.out.println(HORIZONTAL_RULER);
            System.out.println("Teilsumme Teil 1: " + actualPlayers.player.get(playerNumber).partSumPart1);
            System.out.println("Bonus Teil 1: " + actualPlayers.player.get(playerNumber).bonusPart1);
            System.out.println(DOUBLE_RULER);
            System.out.println("Summe Teil 1: " + actualPlayers.player.get(playerNumber).sumPart1);
            System.out.println(DASHED_RULER);
        }
        table[listPart1.length + 1] = actualPlayers.player.get(playerNumber).partSumPart1;
        table[listPart1.length + 2] = actualPlayers.player.get(playerNumber).bonusPart1;
        table[listPart1.length + 3] = actualPlayers.player.get(playerNumber).sumPart1;

        for (int j=0; j < listPart2.length; j++) {
            if (log) System.out.print(listPart2[j] + " : ");
            for (int i = 0; i < actualPlayers.player.get(playerNumber).sheet.game.size(); i++) {                
                if (listPart2[j].equals(actualPlayers.player.get(playerNumber).sheet.game.get(i).set.key)) {
                    if (log) System.out.print(actualPlayers.player.get(playerNumber).sheet.game.get(i).set.value);  
                    table[listPart1.length + 3 + j] = actualPlayers.player.get(playerNumber).sheet.game.get(i).set.value;      
                }
                else {
                    // Not found
                }
            }
            if (log) System.out.println("");
        }
        if (log) {
            System.out.println(HORIZONTAL_RULER);
            System.out.println("Summe Teil 2: " + actualPlayers.player.get(playerNumber).sumPart2);
            System.out.println(DOUBLE_RULER);
            System.out.println("Gesamtsumme : " + actualPlayers.player.get(playerNumber).sum);
            System.out.println("");
        }
        table[listPart1.length + 3 + listPart2.length + 1] = actualPlayers.player.get(playerNumber).sumPart2;
        table[listPart1.length + 3 + listPart2.length + 1] = actualPlayers.player.get(playerNumber).sum;

        return table;
    }

    private static void sumParts(Players actualPlayers, Integer playerNumber) {        
        String[] listPart1 = new KniffelSheet().part1;
        String[] listPart2 = new KniffelSheet().part2;
        
        Integer part1 = actualPlayers.player.get(playerNumber).partSumPart1;
        Integer part2 = actualPlayers.player.get(playerNumber).sumPart2;
        
        for (int i = 0; i < actualPlayers.player.get(playerNumber).sheet.game.size(); i++) {
            String key = actualPlayers.player.get(playerNumber).sheet.game.get(i).set.key;
            if (Arrays.asList(listPart1).contains(key)) {
                part1 += actualPlayers.player.get(playerNumber).sheet.game.get(i).set.value;
            }
            else {
                // Not found
            }
            if (Arrays.asList(listPart2).contains(key)) {
                part2 += actualPlayers.player.get(playerNumber).sheet.game.get(i).set.value;
            }
            else {
                // Not found
            }
        }
        actualPlayers.player.get(playerNumber).partSumPart1 = part1;
        actualPlayers.player.get(playerNumber).sumPart2 = part2;

        // Calculate bonus
        if (part1 > 62 ) actualPlayers.player.get(playerNumber).bonusPart1 = 35;

        actualPlayers.player.get(playerNumber).sumPart1 = part1 + actualPlayers.player.get(playerNumber).bonusPart1;
        actualPlayers.player.get(playerNumber).sum = part1 + actualPlayers.player.get(playerNumber).bonusPart1 + part2;
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

        for (int x = 0; x < knifflers.player.size(); x++) {
            sumParts(knifflers, x);
            table = scoreTable(knifflers, x, true);             
        }
        
        knifflers.player.get(0).deck.dice[1].roll();
        System.out.println(knifflers.player.get(0).deck.dice[1].getCount());    
       
    }


}