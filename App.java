import java.util.*;
import java.util.Arrays;

class Language {
    static String ONES = "Einer";
    static String TWOS = "Zweier";
    static String THREES = "Dreier";
    static String FOURS = "Vierer";
    static String FIFES = "Fünfer";
    static String SIXES = "Sechser";

    static String THREE_EQUALS = "Drei Gleiche";
    static String FOUR_EQUALS = "Vier Gleiche";
    static String FULL_HOUSE = "Volles Haus";
    static String SMALL_STREET = "Kleine Straße";
    static String BIG_STREET = "Große Straße";
    static String KNIFFEL = "Kniffel";
    static String CHANCE = "Chance";

    static String HORIZONTAL_RULER = "-----------------------";
    static String DASHED_RULER     = "- - - - - - - - - - - -";
    static String DOUBLE_RULER     = "=======================";

    static String PART_SUM_PART1 = "Teilsumme Teil 1: ";
    static String SUM_PART1 = "Summe Teil 1: ";
    static String SUM_PART2 = "Summe Teil 2: ";
    static String BONUS = "Bonus Teil 1: ";
    static String SUM = "Gesamtsumme : ";

    static String DICE = "Würfel";

    Language(){
        // only language definition
    }
}    

class Tuple<TypeKey, TypeValue> {
    public final TypeKey key;
    public final TypeValue value;
    public Tuple(TypeKey key, TypeValue value) {
        this.key = key;
        this.value = value;
    }
}

class KniffelSheet {
    String[] part1 = {Language.ONES, Language.TWOS, Language.THREES, Language.FOURS, Language.FIFES, Language.SIXES};
    String[] part2 = {Language.ONE_PAIR, Language.TWO_PAIR, Language.THREE_EQUALS, Language.FOUR_EQUALS, Language.FULL_HOUSE, Language.SMALL_STREET, Language.BIG_STREET, Language.KNIFFEL, Language.CHANCE};    
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
        
    private static int getValidDiceSum(Players actualPlayers, Integer playerNumber, String goalName) {
        Integer goalValue = 0;
        if (goalName.equals(Language.CHANCE)) {
            for (int d = 0; d < 5; d++) {
                goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
            }
        }
        
        if (goalName.equals(Language.KNIFFEL)) {
            Boolean valid = true;
            // Finde 5 gleiche Würfel, wenn ja 50 Punkte
            if (valid.equals(true)) {
                //TODO: Fill with code (Kniffel)        
                goalValue = 50;      
            } else {
                goalValue = 0;
            }
        }
        
        if (goalName.equals(Language.THREE_EQUALS)) {
            Boolean valid = true;
            // Finde 3 gleiche Würfel und addiere alle Augenzahlen
            if (valid.equals(true)) {
                //TODO: Fill with code (Three equals)
                for (int d = 0; d < 5; d++) {
                    goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
                }   
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.FOUR_EQUALS)) {
            Boolean valid = true;
            // Finde 4 gleiche Würfel und addiere alle Augenzahlen
            if (valid.equals(true)) {
                //TODO: Fill with code (Four equals)
                for (int d = 0; d < 5; d++) {
                    goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
                }
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.FULL_HOUSE)) {            
            Boolean valid = true;
            // Finde ein Paar und einen Drilling, wenn ja, 25 Punkte
            if (valid.equals(true)) {
                //TODO: Fill with code (Full house)
                goalValue = 25;                
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.SMALL_STREET)) {
            Boolean valid = true;
            // Finde 4 aufeinander folgende Würfel, wenn ja 30 Punkte
            if (valid.equals(true)) {
                //TODO: Fill with code (Small street)
                goalValue = 30;   
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.BIG_STREET)) {
            Boolean valid = true;
            // Finde 5 aufeinander folgende Würfel, wenn ja 40 Punkte
            if (valid.equals(true)) {                
                //TODO: Fill with code (Big street)
                goalValue = 40;                
            } else {
                goalValue = 0;
            }
        }

        Integer goalCounter = 0;
        if (goalName.equals(Language.ONES)) goalCounter = 1;
        if (goalName.equals(Language.TWOS)) goalCounter = 2;
        if (goalName.equals(Language.THREES)) goalCounter = 3;
        if (goalName.equals(Language.FOURS)) goalCounter = 4;
        if (goalName.equals(Language.FIFES)) goalCounter = 5;
        if (goalName.equals(Language.SIXES)) goalCounter = 6;
        
        if (goalCounter > 0) {         
            for (int d = 0; d < 5; d++) {
                if (actualPlayers.player.get(playerNumber).deck.dice[d].getCount() == goalCounter) {
                    goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
                }
            }      
        }

        return goalValue;
    }

    private static boolean addGoal(Players actualPlayers, Integer playerNumber, String goalName) {
        Boolean keyExists = false;
        Integer goalValue = 0;
        
        goalValue = getValidDiceSum(actualPlayers, playerNumber, goalName);        

        for (int i = 0; i < actualPlayers.player.get(playerNumber).sheet.game.size(); i++) {
            if (actualPlayers.player.get(playerNumber).sheet.game.get(0).set.key.equals(goalName)) {
                keyExists = true;
            }
        }        
        
        if (keyExists) {
            // Wurf existiert
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
        String[] listPart1 = new KniffelSheet().part1;
        String[] listPart2 = new KniffelSheet().part2;

        int[] table = new int[listPart1.length + listPart2.length + 5];

        if (log) {
            System.out.println(actualPlayers.player.get(playerNumber).name);
            System.out.println(Language.HORIZONTAL_RULER);
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
            System.out.println(Language.HORIZONTAL_RULER);
            System.out.println(Language.PART_SUM_PART1 + actualPlayers.player.get(playerNumber).partSumPart1);
            System.out.println(Language.BONUS + actualPlayers.player.get(playerNumber).bonusPart1);
            System.out.println(Language.DOUBLE_RULER);
            System.out.println(Language.SUM_PART1 + actualPlayers.player.get(playerNumber).sumPart1);
            System.out.println(Language.DASHED_RULER);
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
            System.out.println(Language.HORIZONTAL_RULER);
            System.out.println(Language.SUM_PART2 + actualPlayers.player.get(playerNumber).sumPart2);
            System.out.println(Language.DOUBLE_RULER);
            System.out.println(Language.SUM + actualPlayers.player.get(playerNumber).sum);
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
        int[] table = new int[25];
        
        addPlayer(knifflers, "Jarvis");
    
        for (int d = 0; d < 5; d++) {
            knifflers.player.get(0).deck.dice[d].roll();
            System.out.println(Language.DICE + d + " : " + knifflers.player.get(0).deck.dice[d].getCount());   
        }

        System.out.println("");

        addGoal(knifflers, 0, Language.CHANCE);
        
        for (int x = 0; x < knifflers.player.size(); x++) {
            sumParts(knifflers, x);
            table = scoreTable(knifflers, x, true);             
        }       

        System.out.println(Arrays.toString(table));
    }


}