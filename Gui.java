import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class PlayerFrames {
    static ArrayList<JFrame> player = new ArrayList<>();

    private static String stringifyScore(Integer count) {
        String value = "";
        if (count != -1) value = count.toString();
        else value = "       ";
        return value;
    }

    public static int paintDice(GridBagConstraints c, Players knifflers, int index, JPanel deck){
        int addition = 0;
        for (int d = 0; d < 5; d++) {   
            c.gridx = 0;
            c.gridy = d + addition;
            JLabel diceLabel = new JLabel(Language.dice + " " + (d + 1));            
            diceLabel.setFont(new Font(Language.font, Font.PLAIN, 18));
            deck.add(diceLabel, c);                        

            c.gridx = 0;
            c.gridy = d + addition +1;
            String diceCount = (knifflers.player.get(index).deck.dice[d].getCount() == 0 ? " --- " : String.valueOf(knifflers.player.get(index).deck.dice[d].getCount()));
            JLabel diceThrow = new JLabel(Language.diceThrow + " : " + diceCount);
            deck.add(diceThrow, c);

            c.gridx = 0;
            c.gridy = d + addition +2;
            JCheckBox checkRoll = new JCheckBox(Language.rollAgain);
            checkRoll.setSelected(knifflers.player.get(index).deck.dice[d].roll);                     
            
            checkRoll.setName(String.valueOf(d));

            checkRoll.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {     
                    int dN = 0;
                    dN = Integer.valueOf(checkRoll.getName());               
                    if (e.getStateChange() == ItemEvent.SELECTED)
                        knifflers.player.get(index).deck.dice[dN].setRollState(true);
                    else {  
                        if (knifflers.player.get(index).deck.dice[dN].getCount() != 0)
                            knifflers.player.get(index).deck.dice[dN].setRollState(false);
                    }
                }
            });
             
            deck.add(checkRoll, c);                        

            addition = addition + 3;            
        }   
        return addition;
    }

    private static boolean checkEndGame(Players knifflers) {
        Boolean gameEnds = true;
        for (int p = 0; p < knifflers.player.size(); p++) {
            for (int g = 0; g < knifflers.player.get(p).sheet.game.size(); g++) {
                if (knifflers.player.get(p).sheet.game.get(g).set.value == -1) gameEnds = false;
            }
        }        
        return gameEnds;
    }

    private static void addBoard(int index, Players knifflers) {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel deck = new JPanel();     
        JPanel score = new JPanel();               
        
        deck.setLayout(new GridBagLayout());        
        score.setLayout(new GridBagLayout());
        split.setDividerSize(0);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;

        int addition = paintDice(c, knifflers, index, deck);
        
        c.gridx = 0;
        c.gridy = 5 + addition + 2;        
        JButton rollNowButton = new JButton(Language.rollNow + "(" + knifflers.player.get(index).roll + ")");
        rollNowButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                for (int d = 0; d < 5; d++) {
                    knifflers.player.get(index).deck.dice[d].roll();                    
                }     

                player.get(index).removeAll();                
                player.get(index).dispose();
                knifflers.player.get(index).roll++;
                repaintPlayer(index, knifflers);   
            }});
        if (knifflers.player.get(index).roll == 3) rollNowButton.setEnabled(false);
        deck.add(rollNowButton,c);
        deck.setVisible(true);
        
        for (int s = 0; s < 6 ; s++) {            //knifflers.player.get(index).sheet.game.size()
            c.gridx = 0;
            c.gridy = s;
            JButton addButton = new JButton(knifflers.player.get(index).sheet.game.get(s).set.key);            
            
            c.fill = GridBagConstraints.HORIZONTAL;
            addButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String goalName = addButton.getText();

                    Kniffel.updateGoal(knifflers, index, goalName);
                    Kniffel.sumParts(knifflers, index);
                    
                    player.get(index).removeAll();                
                    player.get(index).dispose();
                    if (checkEndGame(knifflers)) {
                        Gui.endScore(knifflers);
                        // JOptionPane.showMessageDialog(null, Language.endGame, Language.winner, JOptionPane.INFORMATION_MESSAGE); 
                    } else {
                        knifflers.player.get(index).roll = 0;
                        int nextPlayer = index + 1;
                        if (nextPlayer > knifflers.player.size()-1) nextPlayer = 0;
                        knifflers.player.get(index).turn = false;
                        knifflers.player.get(nextPlayer).turn = true; 
                        repaintPlayer(nextPlayer, knifflers);
                    }
                }});
            if (knifflers.player.get(index).sheet.game.get(s).set.value == -1) addButton.setEnabled(true);
            else addButton.setEnabled(false);
            score.add(addButton,c);
            c.gridx = 1;
            c.gridy = s;            
            score.add(new JLabel(stringifyScore(knifflers.player.get(index).sheet.game.get(s).set.value)),c); 
        }
        c.gridx = 0;
        c.gridy = 6;        
        score.add(new JLabel(Language.partialSumPart1),c);
        c.gridx = 1;
        c.gridy = 6;
        score.add(new JLabel(knifflers.player.get(index).partSumPart1.toString()),c);
        c.gridx = 0;
        c.gridy = 7;
        score.add(new JLabel(Language.bonus),c);
        c.gridx = 1;
        c.gridy = 7;
        score.add(new JLabel(knifflers.player.get(index).bonusPart1.toString()),c);
        c.gridx = 0;
        c.gridy = 8;
        score.add(new JLabel(Language.sumPart1),c);
        c.gridx = 1;
        c.gridy = 8;
        score.add(new JLabel(knifflers.player.get(index).sumPart1.toString()),c);
        for (int s = 6; s < knifflers.player.get(index).sheet.game.size() ; s++) {           
            c.gridx = 0;
            c.gridy = s + 3;
            JButton addButton = new JButton(knifflers.player.get(index).sheet.game.get(s).set.key);
            c.fill = GridBagConstraints.HORIZONTAL;
            addButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String goalName = addButton.getText();
                    Kniffel.updateGoal(knifflers, index, goalName);
                    Kniffel.sumParts(knifflers, index);
                    
                    player.get(index).removeAll();                
                    player.get(index).dispose();
                    if (checkEndGame(knifflers)) {
                        Gui.endScore(knifflers);
                        // JOptionPane.showMessageDialog(null, Language.endGame, Language.winner, JOptionPane.INFORMATION_MESSAGE); 
                    } else {
                        knifflers.player.get(index).roll = 0;
                        int nextPlayer = index + 1;
                        if (nextPlayer > knifflers.player.size()-1) nextPlayer = 0; 
                        knifflers.player.get(index).turn = false;
                        knifflers.player.get(nextPlayer).turn = true;
                        repaintPlayer(nextPlayer, knifflers);  
                    }
                }});
            if (knifflers.player.get(index).sheet.game.get(s).set.value == -1) addButton.setEnabled(true);
            else addButton.setEnabled(false);
            score.add(addButton,c);
            c.gridx = 1;
            c.gridy = s + 3;
            score.add(new JLabel(stringifyScore(knifflers.player.get(index).sheet.game.get(s).set.value)),c);                                                                    
        }
        c.gridx = 0;
        c.gridy = 16;
        score.add(new JLabel(Language.sumPart2),c);
        c.gridx = 1;
        c.gridy = 16;
        score.add(new JLabel(knifflers.player.get(index).sumPart2.toString()),c);

        c.gridx = 0;
        c.gridy = 17;
        score.add(new JLabel(Language.totalSum),c);
        c.gridx = 1;
        c.gridy = 17;
        score.add(new JLabel(knifflers.player.get(index).sum.toString()),c);
        
        score.setVisible(true);

        split.setLeftComponent(deck);
        split.setRightComponent(score);
        split.setVisible(true);
        
        player.get(index).add(split);   
        player.get(index).setDefaultCloseOperation(1); //JFrame.HIDE_ON_CLOSE
        
        if (knifflers.player.get(index).turn) player.get(index).setVisible(true);                    
        else player.get(index).setVisible(false);                    
        player.get(index).repaint();
    }        

    public static void repaintPlayer(int i, Players knifflers) {        
        JFrame frame = new JFrame();
        
        frame.setDefaultCloseOperation(1); // JFrame.HIDE_ON_CLOSE
        frame.setTitle(knifflers.player.get(i).name);
        frame.setName(knifflers.player.get(i).name);        
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());
        player.remove(i);
        player.add(i, frame);  

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        addBoard(i, knifflers);      
        frame.setResizable(false);  
        frame.pack();        
    }

    public static void add(Players knifflers) {        
        for (int i = 0; i < knifflers.player.size(); i++) {
            JFrame frame = new JFrame();
            
            frame.setDefaultCloseOperation(1); // JFrame.HIDE_ON_CLOSE
            frame.setTitle(knifflers.player.get(i).name);
            frame.setName(knifflers.player.get(i).name);        
            frame.setVisible(false);
            frame.setLayout(new GridBagLayout());
            player.add(frame);  

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        
            addBoard(i, knifflers);      
            frame.setResizable(false);  
            frame.pack();
        }
        // First player may start
        knifflers.player.get(0).turn = true;
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
    JMenuItem m101 = new JMenuItem(Language.show);
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
                knifflers.player.get(0).turn = true;
                m10.setEnabled(false);
                m111.setEnabled(false);
                m112.setEnabled(false);
                m101.setEnabled(true);                            
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
        m101.setEnabled(false);
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

    public static void endScore(Players knifflers) {
        frame.removeAll();
        JLabel text = new JLabel(Language.endGame);    
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font(Language.font, Font.PLAIN, 18));
        frame.add(BorderLayout.CENTER, text);

        int winner = 0;
        int highestScore = 0;
        for (int p = 0; p < knifflers.player.size(); p++) {
            if (knifflers.player.get(p).sum > highestScore) {
                highestScore = knifflers.player.get(p).sum;
                winner = p;
            }
            JLabel player = new JLabel(knifflers.player.get(p).name + " : " + knifflers.player.get(p).sum);

            player.setVisible(true);
            frame.add(player);
        }

        JLabel winnerLabel = new JLabel(Language.winnerIs + knifflers.player.get(winner).name + " " + Language.with + " " + knifflers.player.get(winner).sum + " " + Language.points);
        winnerLabel.setVisible(true);
        frame.add(winnerLabel);

        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
        frame.repaint();
    }

    public static void createMainFrame(Players knifflers) {
        frame.setDefaultCloseOperation(3); // JFrame.EXIT_ON_CLOSE
        frame.setSize(700,600);   
        frame.setTitle(Language.appName);
        frame.setName(Language.appName); 

        frame.add(BorderLayout.NORTH, new MenuBar(knifflers));

        JLabel text = new JLabel(Language.welcome);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setFont(new Font(Language.font, Font.PLAIN, 18));
        frame.add(BorderLayout.CENTER, text);

        playerList(knifflers);

        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
    }

    Gui() {
        //
    }
}
