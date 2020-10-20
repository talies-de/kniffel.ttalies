import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerFrames {
    static ArrayList<JFrame> player = new ArrayList<>();

    private static void addDices(int index, Players knifflers) {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel deck = new JPanel();     
        JPanel score = new JPanel();           
        deck.setLayout(new GridLayout(22,1));
        score.setLayout(new GridLayout(19,2));
        split.setDividerSize(0);
        split.setResizeWeight(0.5);  
        for (int d = 0; d < 5; d++) {            
            deck.add(new JLabel("Würfel " + (d + 1)));
            deck.add(new JLabel("Wurf: " + knifflers.player.get(index).deck.dice[d].getCount()));
            deck.add(new JCheckBox("Neu würfeln"));            
            deck.add(new JLabel(""));            
        }     
        JButton button = new JButton("Jetzt würfeln");        
        button.setMaximumSize(new Dimension(50, 50));
        deck.add(button);
        deck.setVisible(true);
        
        for (int s = 0; s < 6 ; s++) {            //knifflers.player.get(index).sheet.game.size()
            score.add(new JButton("+"));
            score.add(new JLabel(knifflers.player.get(index).sheet.game.get(s).set.key + " : " + knifflers.player.get(index).sheet.game.get(s).set.value));                                                                    
        }
        score.add(new JLabel(Language.partialSumPart1 + knifflers.player.get(index).partSumPart1));
        score.add(new JLabel(""));
        score.add(new JLabel(Language.bonus + knifflers.player.get(index).bonusPart1));
        score.add(new JLabel(""));
        score.add(new JLabel(Language.sumPart1 + knifflers.player.get(index).sumPart1));
        score.add(new JLabel(""));
        for (int s = 6; s < knifflers.player.get(index).sheet.game.size() ; s++) {           
            score.add(new JButton("+"));
            score.add(new JLabel(knifflers.player.get(index).sheet.game.get(s).set.key + " : " + knifflers.player.get(index).sheet.game.get(s).set.value));                                                                    
        }
        score.add(new JLabel(Language.sumPart2 + knifflers.player.get(index).sumPart2));
        score.add(new JLabel(""));
        score.add(new JLabel(Language.totalSum + knifflers.player.get(index).sum));
        
        score.setVisible(true);

        split.setLeftComponent(deck);
        split.setRightComponent(score);
        split.setVisible(true);
        
        player.get(index).add(split);   
        
        player.get(index).setVisible(true);                    
        player.get(index).repaint();
    }        

    public static void add(Players knifflers) {        
        for (int i = 0; i < knifflers.player.size(); i++) {
            JFrame frame = new JFrame();
            
            frame.setDefaultCloseOperation(1); // JFrame.HIDE_ON_CLOSE
            frame.setTitle(knifflers.player.get(i).name);
            frame.setName(knifflers.player.get(i).name);        
            frame.setVisible(true);
            frame.setLayout(new GridBagLayout());
            player.add(frame);  

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        
            addDices(i, knifflers);      
            frame.setResizable(false);  
            frame.pack();
        }
    }

    public static boolean remove(int index) {
        try {
            player.get(index).dispose();
            player.remove(index);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    PlayerFrames() {}
}

class MenuBar extends JMenuBar implements ActionListener {
    private static Players knifflers;    
    private static final long serialVersionUID = 1L;
    
    JMenuBar mb = new JMenuBar();
    JMenu m1 = new JMenu("Spiel");
    JMenu m2 = new JMenu("Hilfe");
    JMenuItem m10 = new JMenuItem("Starten");
    JMenu m11 = new JMenu("Spieler");
    JMenuItem m111 = new JMenuItem("Hinzufügen");
    JMenuItem m112 = new JMenuItem("Entfernen");
    JMenuItem m12 = new JMenuItem("Beenden");
    JMenuItem m21 = new JMenuItem("Anleitung");
    JMenuItem m22 = new JMenuItem("Über");

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.m111){                          
            String result = JOptionPane.showInputDialog(null, "Name:");   
            Kniffel.addPlayer(knifflers, result);            
            JOptionPane.showMessageDialog(null, "Spieler/in " + result + " hinzugefügt", "Neue/r Spieler/in", JOptionPane.INFORMATION_MESSAGE);  
            System.out.println("Added " + result);                
        }
        if(ae.getSource() == this.m112){
            System.out.println("Remove");
        }
        if(ae.getSource() == this.m12){        
            System.out.println("Quit");
            System.exit(0);
        }
        if(ae.getSource() == this.m21){
            System.out.println("Manual");
        }
        if(ae.getSource() == this.m22){
            System.out.println("About");
        }
        if(ae.getSource() == this.m10){
            PlayerFrames.add(knifflers);   
            System.out.println("Start");
        }
    }   

    private static void setPlayer(Players kniffelPlayers) {
        MenuBar.knifflers = kniffelPlayers;
    }

    public MenuBar(Players kniffelPlayers) {
        setPlayer(kniffelPlayers);
        GridBagConstraints mbGbc = new GridBagConstraints();

        //Creating the MenuBar and adding components           
        add(m1, mbGbc);
        add(m2, mbGbc);
        
        m1.add(m10);
        m10.addActionListener(this);
        m1.add(m11);
        
        m111.addActionListener(this);
        m11.add(m111);

        m112.addActionListener(this);
        m11.add(m112);
        
        m12.addActionListener(this);
        m1.add(m12);
        
        m21.addActionListener(this);
        m2.add(m21);
        
        m22.addActionListener(this);
        m2.add(m22);
    }
}

public class Gui {
    
    private static JFrame frame = new JFrame();

    public static void createMainFrame(Players knifflers) {
        frame.setDefaultCloseOperation(3); // JFrame.EXIT_ON_CLOSE
        frame.setSize(700,600);   
        frame.setTitle("Kniffel");
        frame.setName("Kniffel"); 

        frame.add(BorderLayout.NORTH, new MenuBar(knifflers));

        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
    }

    Gui() {
        //
    }
}
