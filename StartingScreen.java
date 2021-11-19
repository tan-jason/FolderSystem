/*
 * StartingScreen.java
 * Version 1.0
 * @Jason and Nathan 
 * 11/28/2019
 */


//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.io.File;

public class StartingScreen extends JFrame {
    JFrame thisFrame;
    MainPanel mainPanel;
    FolderSystem folderSystem;
    
    StartingScreen() {
        super("Starting Screen");
        this.thisFrame = this;
        this.folderSystem = new FolderSystem();
        //initializing panel on frame 
        this.mainPanel = new MainPanel();
        mainPanel.setLayout(new GridLayout(2,1));
        this.add(mainPanel,BorderLayout.CENTER);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set size of the frame
        this.setSize(1200, 800);
        
        this.setResizable(false);
        this.setBackground(new Color(255,255,255));
        this.setVisible(true);
        
    }
    
    void redraw() {
        mainPanel.repaint();
    }
    
    //main panel which everything will be portrayed on 
    private class MainPanel extends JPanel implements KeyListener{
        JTextField fileInput;
        JButton dontLoad;
        
        MainPanel() {
            //jtextfield for user to import file from
            this.fileInput = new JTextField("Enter file name to load from");
            this.fileInput.setPreferredSize(new Dimension(400,100));
            this.fileInput.addKeyListener(this);
            this.fileInput.setFont(new Font("SansSerif", Font.BOLD, 32));
            this.fileInput.setHorizontalAlignment(JTextField.CENTER);
            this.setBackground(new Color(255,255,255));
            
            //option to not load from file
            this.dontLoad = new JButton("DON'T LOAD");
            this.dontLoad.setFont(new Font("SansSerif", Font.BOLD, 32));
            this.dontLoad.addActionListener(new DontLoadButtonListener());
            
            this.add(fileInput);
            this.add(dontLoad);
        }
        
        //key listener to detect when user presses enter and searches for file 
        public void keyPressed(KeyEvent e) { 
            if(e.getKeyCode() == KeyEvent.VK_ENTER){ 
                String fileName = fileInput.getText();
                try{
                    loadFile(fileName);
                }catch(Exception exception) { //catches NullFileException
                    JOptionPane.showMessageDialog(null,"File not found!");
                }
            }
        }
        
        public void keyTyped(KeyEvent e){
            
        }
        public void keyReleased(KeyEvent e){ 
            
        }
        
        //loading data from file creating folder system with data 
        public void loadFile(String fileName) throws Exception{
            File sourceFile = new File(fileName);
            Scanner input = new Scanner(sourceFile);
            while(input.hasNext()) {
                String line = input.nextLine();
                String folderName = line.substring(0,line.indexOf('['));
                line =  line.substring(line.indexOf('['),line.length());
                String allInnerFolders = line.substring(line.indexOf('[')+1,line.indexOf(']'));
                line = line.substring(line.indexOf(']')+1,line.length());
                String[] innerFolderNames = allInnerFolders.split(", ");
                String allInnerFiles = line.substring(line.indexOf('[')+1,line.indexOf(']'));
                String[] innerFileNames = allInnerFiles.split(", ");
                
                if(innerFolderNames != null) {
                    if(folderSystem.getFolder(folderName) == null) {
                        folderSystem.addFolder(folderName);
                        for(String s : innerFolderNames) {
                            folderSystem.insertFolder(s,folderName);
                        }
                    }
                }
                
                if(innerFileNames != null) {
                    for(String s : innerFileNames) {
                        if(!(s.equals(""))){ 
                            folderSystem.insertFile(s,folderName);
                        }
                    }
                }
                
            }
            input.close();
            thisFrame.dispose();
            new ManagerFrame(folderSystem);
        }
        
        public void paintComponent(Graphics g){     
            super.paintComponent(g); //required
            repaint();
        }
        
        private class DontLoadButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent event){ 
                thisFrame.dispose();
                new ManagerFrame(folderSystem);
            }
        }
    }
    public static void main(String[] args) {
        new StartingScreen();
    }
}      