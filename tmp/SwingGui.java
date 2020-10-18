package tmp;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import javax.swing.border.EmptyBorder;

class Kniffel{

    private static Component inputBarComponent;    
    private static Component scoreTableComponent;
    private static Component diceBarComponent;
    
    private static JFrame frame = new JFrame("Kniffel");

    private static int small = 20;
    private static int big = 100;

    private static JTable table;

    public static class Players {
        private static String[] names = {"Computer", "", "", ""}; 
        private static int number=0;

        private Players() {}

        public static int getNumber() {
            return number;
        }
     
        public static int setNumber(int num) {
            number = num;
            return num;
        }
        public static String getName(int num) {
            return names[num];
        }
     
        public static String setName(int num, String name) {
            names[num] = name;
            return name;
        }
    }

    public static JScrollPane scoreTable() {
        table = new JTable(new ScoreTableModel());
        setColumnWidth();        
        return new JScrollPane(table);
    }

    private static void setColumnWidth() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn c = table.getColumnModel().getColumn(i);
            switch (i) {
            case 0:
                c.setPreferredWidth(big);
                break;

            case 1:
                c.setPreferredWidth(small);
                break;

            case 2:
            c.setPreferredWidth(small);
            break;

            default:
                c.setPreferredWidth(JTable.AUTO_RESIZE_ALL_COLUMNS);
            }
        }
    }

    static class ScoreTableModel extends DefaultTableModel {
            
        private static final long serialVersionUID = 1L;
    
        private int rows = 21;
        private int cols = Players.setNumber(Players.getNumber()+1);
        private String[] rowData = new String[rows];
    
        public ScoreTableModel() {
            super();
            initModelData();
        }
    
        private void initModelData() {
            this.addColumn(""); // empty col
            this.addColumn(Players.getName(0)); // first player (Computer)
            for (int i = 1; i < cols; i++) {                
                this.addColumn(Players.names[i]);
            }
    
            rowData[0] = "Einer";
            rowData[1] = "Zweier";
            rowData[2] = "Dreier";
            rowData[3] = "Vierer";
            rowData[4] = "Fünfer";
            rowData[5] = "Sechser";
            rowData[6] = "Zwischensumme";
            rowData[7] = "Bonus";
            rowData[8] = "Gesamtsumme 1. Teil";
            rowData[9] = "1 Paar";
            rowData[10] = "2 Paar";
            rowData[11] = "Drei Gleiche";
            rowData[12] = "Vier Gleiche";
            rowData[13] = "Volles Haus";
            rowData[14] = "Kleine Straße";
            rowData[15] = "Große Straße";
            rowData[16] = "5x gleiche Augenzahl";
            rowData[17] = "Chance";
            rowData[18] = "Gesamtsumme 2. Teil";
            rowData[19] = "Gesamtsumme 1. Teil";
            rowData[20] = "Endsumme";
            
            for (int j = 0; j < rows; j++) {
                this.addRow(new Object[]{rowData[j],""});
            }
        }
    }
   
    public static Integer throwDie()
    {
        return (int)(Math.random() * 6) + 1;
    }

    static class DicePane extends JPanel implements ActionListener {

        String btnLabel = "Neu würfeln";

        JButton btnDice1 = new JButton(btnLabel);
        JButton btnDice2 = new JButton(btnLabel);
        JButton btnDice3 = new JButton(btnLabel);
        JButton btnDice4 = new JButton(btnLabel);
        JButton btnDice5 = new JButton(btnLabel);
        JButton btnAll = new JButton("Alle neu würfeln");
        JLabel dice1 = new JLabel();
        JLabel dice2 = new JLabel();
        JLabel dice3 = new JLabel();
        JLabel dice4 = new JLabel();
        JLabel dice5 = new JLabel();

        private static final long serialVersionUID = 1L;

        public void actionPerformed (ActionEvent ae){
            if(ae.getSource() == this.btnDice1){
                System.out.println("Würfel 1 nochmal");                
                dice1.setText(throwDie().toString());
            }
            if(ae.getSource() == this.btnDice2){
                System.out.println("Würfel 2 nochmal");
                dice2.setText(throwDie().toString());
            }
            if(ae.getSource() == this.btnDice3){
                System.out.println("Würfel 3 nochmal");
                dice3.setText(throwDie().toString());
            }
            if(ae.getSource() == this.btnDice4){
                System.out.println("Würfel 4 nochmal");
                dice4.setText(throwDie().toString());
            }
            if(ae.getSource() == this.btnDice5){
                System.out.println("Würfel 5 nochmal");
                dice5.setText(throwDie().toString());                
            }
            if(ae.getSource() == this.btnAll){
                System.out.println("Alle Würfel");
                dice1.setText(throwDie().toString());
                dice2.setText(throwDie().toString());
                dice3.setText(throwDie().toString());
                dice4.setText(throwDie().toString());
                dice5.setText(throwDie().toString());
            }
        }

        public DicePane() {
            setBorder(new EmptyBorder(50, 50, 50, 50));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            btnAll.addActionListener(this);
            add(btnAll, gbc);
            add(new JLabel(" "), gbc);

            add(new JLabel("Würfel 1"), gbc);
            btnDice1.addActionListener(this);
            add(btnDice1, gbc);            
            add(dice1, gbc);
            add(new JLabel(" "), gbc);

            add(new JLabel("Würfel 2"), gbc);            
            btnDice2.addActionListener(this);
            add(btnDice2, gbc);
            add(dice2, gbc);
            add(new JLabel(" "), gbc);

            add(new JLabel("Würfel 3"), gbc);
            btnDice3.addActionListener(this);
            add(btnDice3, gbc);
            add(dice3, gbc);
            add(new JLabel(" "), gbc);

            add(new JLabel("Würfel 4"), gbc);
            btnDice4.addActionListener(this);
            add(btnDice4, gbc);
            add(dice4, gbc);
            add(new JLabel(" "), gbc);

            add(new JLabel("Würfel 5"), gbc);
            btnDice5.addActionListener(this);
            add(btnDice5, gbc);
            add(dice5, gbc);
            

            
        }

    }

    static class MenuBar extends JMenuBar implements ActionListener {
        
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
                inputBarComponent.setVisible(true);
                System.out.println("Add");                
            }
            if(ae.getSource() == this.m112){
                System.out.println("Remove");
            }
            if(ae.getSource() == this.m12){
                System.out.println("Quit");
            }
            if(ae.getSource() == this.m21){
                System.out.println("Manual");
            }
            if(ae.getSource() == this.m22){
                System.out.println("About");
            }
            if(ae.getSource() == this.m10){
                diceBarComponent.setVisible(true);
                System.out.println("Start");
            }
        }

        public MenuBar() {
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

    static class InputBar extends JPanel implements ActionListener {
        
        private static final long serialVersionUID = 1L;
        
        JPanel panel = new JPanel(); // the panel is not visible in output
        JButton send = new JButton("OK");
        JButton reset = new JButton("Löschen");
        JTextField playerName = new JTextField(20); // accepts upto 20 characters
        
        public void actionPerformed (ActionEvent ae){
            if(ae.getSource() == this.send){                    
                System.out.println("Send: " + playerName.getText());
                if (playerName.getText().trim().isEmpty()) {
                    inputBarComponent.setVisible(false);
                    System.out.println("Spielername darf nicht leer sein");
                    
                }
                else {
                    Players.setName(Players.getNumber(), playerName.getText());
                    inputBarComponent.setVisible(false);  
                    Component[] remover = frame.getContentPane().getComponents();
                    frame.remove(remover[3]);
                    frame.repaint();
                    frame.getContentPane().add(BorderLayout.EAST, scoreTable());
                    scoreTableComponent.setVisible(true);
                }
                playerName.setText(null);
            }
            if(ae.getSource() == this.reset){
                System.out.println("Reset");
                playerName.setText(null);
            }
        }

        public InputBar() {
            //Creating the panel and adding components            
            JLabel nameLabel = new JLabel("Spielername");            
            GridBagConstraints inputGbc = new GridBagConstraints();

            add(nameLabel, inputGbc); // Components Added using Flow Layout
            add(playerName, inputGbc);
            send.addActionListener(this);
            add(send, inputGbc);
            reset.addActionListener(this);
            add(reset, inputGbc);            

        }
    }
    
    public static void main(String args[]){
        //Creating the Frame
        frame.setDefaultCloseOperation(3); // JFrame.EXIT_ON_CLOSE
        frame.setSize(700,600);               

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, new MenuBar());

        inputBarComponent = frame.getContentPane().add(BorderLayout.SOUTH, new InputBar());        
        inputBarComponent.setVisible(false);
        
        diceBarComponent = frame.getContentPane().add(BorderLayout.WEST, new DicePane());
        diceBarComponent.setVisible(false);

        scoreTableComponent = frame.getContentPane().add(BorderLayout.EAST, scoreTable());
        scoreTableComponent.setVisible(false);
        
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
    }
}