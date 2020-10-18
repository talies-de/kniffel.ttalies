import java.util.*;
import java.util.Arrays;

class Language {
    static String ONES = "Einer";
    static String TWOS = "Zweier";
    static String THREES = "Dreier";
    static String FOURS = "Vierer";
    static String FIVES = "Fünfer";
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
    String[] part1 = {Language.ONES, Language.TWOS, Language.THREES, Language.FOURS, Language.FIVES, Language.SIXES};
    String[] part2 = {Language.THREE_EQUALS, Language.FOUR_EQUALS, Language.FULL_HOUSE, Language.SMALL_STREET, Language.BIG_STREET, Language.KNIFFEL, Language.CHANCE};    
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

    public void setCount(int count) {
        this.count = count;
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
        
    private static Integer[] dicesToArray(Players actualPlayers, Integer playerNumber) {
        Integer[] dices = new Integer[actualPlayers.player.get(playerNumber).deck.dice.length];
        for (int k = 0; k<actualPlayers.player.get(playerNumber).deck.dice.length; k++) {
            dices[k] = actualPlayers.player.get(playerNumber).deck.dice[k].getCount();
        }
        return dices;
    }

    private static Boolean countEqualDices(Players actualPlayers, Integer playerNumber, Integer amount) {
        Integer[] dices = dicesToArray(actualPlayers, playerNumber);
        Integer count = 0;
        for (int c = 1; c < 7; c++) {
            Integer tempCount = 0;
            for (int d = 0; d < 5; d++) {
                if (dices[d] == c) tempCount++;
            }
            if (count < amount ) count = tempCount;
        }
        Boolean valid = false;
        if (count > amount - 1) valid = true;
        return valid;
    }

    private static int getValidDiceSum(Players actualPlayers, Integer playerNumber, String goalName) {
        Integer goalValue = 0;

        if (goalName.equals(Language.CHANCE)) {
            for (int d = 0; d < 5; d++) {
                goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
            }
        }
        
        if (goalName.equals(Language.KNIFFEL)) {
            Integer[] dices = dicesToArray(actualPlayers, playerNumber);
            // Finde 5 gleiche Würfel, wenn ja 50 Punkte
            Boolean valid = Arrays.asList(dices).stream().allMatch(t -> t.equals(dices[1]));
            if (valid.equals(true)) goalValue = 50;      
            else goalValue = 0;
        }
        
        if (goalName.equals(Language.THREE_EQUALS)) {                      
            // Finde 3 gleiche Würfel und addiere alle Augenzahlen                        
            Boolean valid = countEqualDices(actualPlayers, playerNumber, 3);            
            
            if (valid.equals(true)) {
                for (int d = 0; d < 5; d++) {
                    goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
                }   
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.FOUR_EQUALS)) {
            // Finde 4 gleiche Würfel und addiere alle Augenzahlen            
            Boolean valid = countEqualDices(actualPlayers, playerNumber, 4);
            
            if (valid.equals(true)) {            
                for (int d = 0; d < 5; d++) {
                    goalValue = goalValue + actualPlayers.player.get(playerNumber).deck.dice[d].getCount();
                }
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.FULL_HOUSE)) {            
            Boolean valid = false;
            // Finde ein Paar und einen Drilling, wenn ja, 25 Punkte
            if (valid.equals(true)) {
                //TODO: Fill with code (Full house)
                goalValue = 25;                
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.SMALL_STREET)) {
            Integer[] dices = dicesToArray(actualPlayers, playerNumber);
            Arrays.sort(dices);     

            Boolean valid1 = true;
            Boolean valid2 = true;

            // Finde 4 aufeinander folgende Würfel, wenn ja 30 Punkte

            // prüfe die ersten 4 Würfel
            for (int i = 1; i < dices.length -1; i++) {
                int dice1 = dices[i-1];
                int dice2 = dices[i];
                if ( dice2 != dice1 + 1) valid1 = false;
            }

            // prüfe die letzten 4 Würfel
            for (int i = 2; i < dices.length; i++) {
                int dice1 = dices[i-1];
                int dice2 = dices[i];
                if ( dice2 != dice1 + 1) valid2 = false;
            }

            if ((valid1.equals(true)) || (valid2.equals(true)))  {
            
                goalValue = 30;   
            } else {
                goalValue = 0;
            }
        }

        if (goalName.equals(Language.BIG_STREET)) {
            Integer[] dices = dicesToArray(actualPlayers, playerNumber);
            Arrays.sort(dices);            
            Boolean valid = true;
            // Finde 5 aufeinander folgende Würfel, wenn ja 40 Punkte
            for (int i = 1; i < dices.length; i++) {
                int dice1 = dices[i-1];
                int dice2 = dices[i];
                if ( dice2 != dice1 + 1) valid = false;
            }
            
            if (valid.equals(true)) goalValue = 40;
            else goalValue = 0;
        }

        Integer goalCounter = 0;
        if (goalName.equals(Language.ONES)) goalCounter = 1;
        if (goalName.equals(Language.TWOS)) goalCounter = 2;
        if (goalName.equals(Language.THREES)) goalCounter = 3;
        if (goalName.equals(Language.FOURS)) goalCounter = 4;
        if (goalName.equals(Language.FIVES)) goalCounter = 5;
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

        knifflers.player.get(0).deck.dice[0].setCount(3);
        knifflers.player.get(0).deck.dice[1].setCount(2);
        knifflers.player.get(0).deck.dice[2].setCount(1);
        knifflers.player.get(0).deck.dice[3].setCount(4);
        knifflers.player.get(0).deck.dice[4].setCount(6);

        for (int d = 0; d < 5; d++) {            
            System.out.println(Language.DICE + d + " : " + knifflers.player.get(0).deck.dice[d].getCount());   
        }

        addGoal(knifflers, 0, Language.ONES);
        addGoal(knifflers, 0, Language.TWOS);
        addGoal(knifflers, 0, Language.THREES);
        addGoal(knifflers, 0, Language.FOURS);
        addGoal(knifflers, 0, Language.FIVES);
        addGoal(knifflers, 0, Language.SIXES);
        addGoal(knifflers, 0, Language.THREE_EQUALS);
        addGoal(knifflers, 0, Language.FOUR_EQUALS);
        addGoal(knifflers, 0, Language.FULL_HOUSE);
        addGoal(knifflers, 0, Language.SMALL_STREET);
        addGoal(knifflers, 0, Language.BIG_STREET);
        addGoal(knifflers, 0, Language.KNIFFEL);
        addGoal(knifflers, 0, Language.CHANCE);
        
        for (int x = 0; x < knifflers.player.size(); x++) {
            sumParts(knifflers, x);
            table = scoreTable(knifflers, x, true);             
        }       

        System.out.println(Arrays.toString(table));
    }
}