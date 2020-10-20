import java.util.*;

public class Dice {
    Integer count;
    boolean roll;
    Random r = new Random();

    public Dice() {
        this.count = 0;
        this.roll = true;
    }

    public void roll() {
        if (this.roll) {            
            this.count = r.nextInt(6) + 1;            
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