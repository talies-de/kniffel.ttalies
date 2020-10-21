import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerFrames {
    static ArrayList<JFrame> player = new ArrayList<>();

    private static String stringifyScore(Integer count) {
        String value = "";
        if (count != -1) value = count.toString();
        return value;
    }

    private static void addBoard(int index, Players knifflers) {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel deck = new JPanel();     
        JPanel score = new JPanel();    
        
        deck.setLayout(new GridLayout(22,1));        
        score.setLayout(new GridLayout(19,2));
        split.setDividerSize(0);
        split.setResizeWeight(0.5);  

        for (int d = 0; d < 5; d++) {   
            JCheckBox checkRoll = new JCheckBox(Language.rollAgain);
            checkRoll.setSelected(true);         
            deck.add(new JLabel(Language.dice + " " + (d + 1)));
            deck.add(new JLabel(Language.diceThrow + " : " + knifflers.player.get(index).deck.dice[d].getCount()));
            deck.add(checkRoll);            
            deck.add(new JLabel(""));            
        }     
        JButton button = new JButton(Language.rollNow);        
        button.setMaximumSize(new Dimension(50, 50));
        deck.add(button);
        deck.setVisible(true);
        
        for (int s = 0; s < 6 ; s++) {            //knifflers.player.get(index).sheet.game.size()
            score.add(new JButton(Language.addButton));
            score.add(new JLabel(knifflers.player.get(index).sheet.game.get(s).set.key + " : " + stringifyScore(knifflers.player.get(index).sheet.game.get(s).set.value)));                                                                    
        }
        score.add(new JLabel(Language.partialSumPart1 + " : "  + knifflers.player.get(index).partSumPart1));
        score.add(new JLabel(""));
        score.add(new JLabel(Language.bonus + " : "  + knifflers.player.get(index).bonusPart1));
        score.add(new JLabel(""));
        score.add(new JLabel(Language.sumPart1 + " : "  + knifflers.player.get(index).sumPart1));
        score.add(new JLabel(""));
        for (int s = 6; s < knifflers.player.get(index).sheet.game.size() ; s++) {           
            score.add(new JButton(Language.addButton));
            score.add(new JLabel(knifflers.player.get(index).sheet.game.get(s).set.key + " : " + stringifyScore(knifflers.player.get(index).sheet.game.get(s).set.value)));                                                                    
        }
        score.add(new JLabel(Language.sumPart2 + " : " + knifflers.player.get(index).sumPart2));
        score.add(new JLabel(""));
        score.add(new JLabel(Language.totalSum + " : "  + knifflers.player.get(index).sum));
        
        score.setVisible(true);

        split.setLeftComponent(deck);
        split.setRightComponent(score);
        split.setVisible(true);
        
        player.get(index).add(split);   
        player.get(index).setDefaultCloseOperation(1); //JFrame.HIDE_ON_CLOSE
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
        
            addBoard(i, knifflers);      
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
    JMenu m1 = new JMenu(Language.game);
    JMenu m2 = new JMenu(Language.help);
    JMenuItem m10 = new JMenuItem(Language.start);
    JMenuItem m101 = new JMenuItem(Langage.show);
    JMenu m11 = new JMenu(Language.player);
    JMenuItem m111 = new JMenuItem(Language.add);
    JMenuItem m112 = new JMenuItem(Language.remove);
    JMenuItem m12 = new JMenuItem(Language.quit);
    JMenuItem m21 = new JMenuItem(Language.manual);
    JMenuItem m22 = new JMenuItem(Language.about);    

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.m111){                          
            String result = JOptionPane.showInputDialog(null, Language.name);   
            Kniffel.addPlayer(knifflers, result);            
            JOptionPane.showMessageDialog(null, Language.player + " " + result + " " + Language.added, Language.addnew + " " + Language.player, JOptionPane.INFORMATION_MESSAGE);                           
            Gui.playerList(knifflers);
        }
        if(ae.getSource() == this.m112){
            if (knifflers.player.isEmpty()) {
                JOptionPane.showMessageDialog(null, Language.noPlayersMessage, Language.removePlayerTitle, JOptionPane.INFORMATION_MESSAGE); 
            } 
            if (knifflers.player.size() == 1) {
                JOptionPane.showMessageDialog(null, Language.singlePlayerMessage, Language.removePlayerTitle, JOptionPane.INFORMATION_MESSAGE); 
            }
            if (!(knifflers.player.isEmpty()) && (knifflers.player.size() != 1)) {
                String[] choices = new String[knifflers.player.size()];            
                for (int p = 0; p < knifflers.player.size(); p++) {
                    choices[p] = "[" + (p+1) + "] " + knifflers.player.get(p).name;
                }            
                String input = (String) JOptionPane.showInputDialog(null, Language.removePlayerMessage, Language.removePlayerTitle, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]); 

                if (input == null) {
                    //
                } else {
                    int start = input.indexOf("[") + 1;
                    int end = input.indexOf("]");
                    int removePlayerNumber = (int)Integer.valueOf(input.substring(start, end)) - 1;
                    Kniffel.removePlayer(knifflers, removePlayerNumber);
                    Gui.playerList(knifflers);
                }
            }
        }
        if(ae.getSource() == this.m12){        
            int reply = JOptionPane.showConfirmDialog(null, Language.quitMessage, Language.quitTitle, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                //
            }
        }
        if(ae.getSource() == this.m21){
            JOptionPane.showMessageDialog(null, Language.manualMessage, Language.manualTitle, JOptionPane.INFORMATION_MESSAGE);
        }
        if(ae.getSource() == this.m22){
            JOptionPane.showMessageDialog(null, Language.aboutMessage, Language.aboutTitle, JOptionPane.INFORMATION_MESSAGE);
        }
        if(ae.getSource() == this.m10){
            if (knifflers.player.isEmpty()) {
                JOptionPane.showMessageDialog(null, Language.noPlayersMessage, Language.appName, JOptionPane.INFORMATION_MESSAGE); 
            } else {
                for (int playerNumber = 0; playerNumber < knifflers.player.size(); playerNumber++) {
                    Kniffel.initGoals(knifflers, playerNumber);
                }
                m10.setEnabled(false);
                
                PlayerFrames.add(knifflers);               
            }
        }
        if(ae.getSource() == this.m101){
            if (knifflers.player.isEmpty()) {
                JOptionPane.showMessageDialog(null, Language.noPlayersMessage, Language.appName, JOptionPane.INFORMATION_MESSAGE); 
            } else {
                for (int p = 0; p < PlayerFrames.player.size(); p++) {
                    PlayerFrames.player.get(p).setVisible(true);
                }
            }
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
        
        m1.add(m101);
        m101.addActionListener(this);
        
        m1.addSeparator();
        m1.add(m11);
        
        m111.addActionListener(this);
        m11.add(m111);

        m112.addActionListener(this);
        m11.add(m112);
        
        m1.addSeparator();

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
    private static Component activePlayers;

    public static void refresh() {
        frame.repaint();        
    }

    public static void playerList(Players knifflers) {

        if (!knifflers.player.isEmpty()) {
            JLabel players = new JLabel();
            String text = Language.playing + " : ";

            for (int p = 0; p < knifflers.player.size(); p++) {
                if (p == knifflers.player.size() -1) text = text + knifflers.player.get(p).name;
                else text = text + knifflers.player.get(p).name + ", ";
            }
            players.setText(text);
            if (activePlayers != null) {
                frame.remove(activePlayers);
            }

            activePlayers = (frame.add(BorderLayout.SOUTH, players));
            activePlayers.setVisible(true);

            frame.setVisible(true);
            frame.repaint();
        }
    }

    public static void createMainFrame(Players knifflers) {
        frame.setDefaultCloseOperation(3); // JFrame.EXIT_ON_CLOSE
        frame.setSize(700,600);   
        frame.setTitle(Language.appName);
        frame.setName(Language.appName); 

        frame.add(BorderLayout.NORTH, new MenuBar(knifflers));

        JLabel text = new JLabel(Language.welcome);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font("Verdana", Font.PLAIN, 18));
        frame.add(BorderLayout.CENTER, text);

        playerList(knifflers);

        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
    }

    Gui() {
        //
    }
}
