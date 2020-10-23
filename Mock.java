import java.util.Arrays;

public class Mock {
    
    public static void players(Players knifflers) {
        int[] table = new int[25];
        
        Kniffel.addPlayer(knifflers, "Jarvis");
        Kniffel.addPlayer(knifflers, "Ultron");

        for (int p = 0; p < 2; p++) {    

            System.out.println("Würfle zufällig:");
            System.out.println(Language.horizontalRuler);
            for (int d = 0; d < Config.diceAmount; d++) {
                knifflers.player.get(p).deck.dice[d].roll();
                System.out.println(Language.dice + d + " : " + knifflers.player.get(p).deck.dice[d].getCount());   
            }

            knifflers.player.get(p).deck.dice[2].setRollState(false);
            System.out.println("Würfle zufällig, aber Würfel 3 ist blockiert:");
            System.out.println(Language.horizontalRuler);
            for (int d = 0; d < Config.diceAmount; d++) {
                knifflers.player.get(p).deck.dice[d].roll();
                System.out.println(Language.dice + d + " : " + knifflers.player.get(p).deck.dice[d].getCount());   
            }

            knifflers.player.get(p).deck.dice[4].setRollState(false);
            System.out.println("Würfle erneut zufällig, aber Würfel 3 und 5 sind blockiert:");
            System.out.println(Language.horizontalRuler);
            for (int d = 0; d < Config.diceAmount; d++) {
                knifflers.player.get(p).deck.dice[d].roll();
                System.out.println(Language.dice + d + " : " + knifflers.player.get(p).deck.dice[d].getCount());   
            }

            System.out.println("");
            System.out.println("Nun bauen wir uns die Würfel, wie wir sie brauchen:");
            System.out.println(Language.horizontalRuler);
            System.out.println("");

            knifflers.player.get(p).deck.dice[0].setCount(5);
            knifflers.player.get(p).deck.dice[1].setCount(4);
            knifflers.player.get(p).deck.dice[2].setCount(3);
            knifflers.player.get(p).deck.dice[3].setCount(2);
            knifflers.player.get(p).deck.dice[4].setCount(1);

            for (int d = 0; d < Config.diceAmount; d++) {            
                System.out.println(Language.dice + d + " : " + knifflers.player.get(p).deck.dice[d].getCount());   
            }

            System.out.println("");
            System.out.println("Dieser Wurf könnte eines dieser Ergbnisse sein:");
            System.out.println(Language.doubleRuler);
            System.out.println("");

            Kniffel.addGoal(knifflers, p, Language.ones);
            Kniffel.addGoal(knifflers, p, Language.twos);
            Kniffel.addGoal(knifflers, p, Language.threes);
            Kniffel.addGoal(knifflers, p, Language.fours);
            Kniffel.addGoal(knifflers, p, Language.fives);
            Kniffel.addGoal(knifflers, p, Language.sixes);
            Kniffel.addGoal(knifflers, p, Language.threeEquals);
            Kniffel.addGoal(knifflers, p, Language.fourEquals);
            Kniffel.addGoal(knifflers, p, Language.fullHouse);
            Kniffel.addGoal(knifflers, p, Language.smallStreet);
            Kniffel.addGoal(knifflers, p, Language.bigStreet);
            Kniffel.addGoal(knifflers, p, Language.kniffel);
            Kniffel.addGoal(knifflers, p, Language.chance);        

            for (int x = 0; x < knifflers.player.size(); x++) {
                Kniffel.sumParts(knifflers, x);
                table = Kniffel.scoreTable(knifflers, x, true);             
            }       

            System.out.println("Ergebnisliste zur Weiterverarbeitung:");
            System.out.println(Language.dashedRuler);
            System.out.println(Arrays.toString(table));
        }
    }

    private Mock() {
        //
    }
}