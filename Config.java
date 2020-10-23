import javax.swing.ImageIcon;

public class Config {
    static int diceAmount = 5;
    static ImageIcon iconLogo = new ImageIcon("src/Kniffel-Dice-Duel.png");
    static String font = "Verdana";

    private Config() {
        throw new IllegalStateException("Utility class");
    }
}
