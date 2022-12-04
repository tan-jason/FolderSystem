/* 
 * ManagerFrame.java
 * December 2nd 
 * Nathan and Jason 
 * @Version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.util.Queue; 
import java.util.Collections;


public class ManagerFrame extends JFrame{ 
    FolderSystem folderSystem; 
    FunctionPanel fp;
    DisplayPanel dp;
    CounterPanel cp;
    CurrentFolderDisplayPanel addressPanel;
    BackButtonPanel bp; 
    
    ManagerFrame(FolderSystem folderSystem){ 
       super("Folder System Manager");
       this.folderSystem = folderSystem; 
       fp = new FunctionPanel(); 
       dp = new DisplayPanel(); 
       cp = new CounterPanel();
       bp = new BackButtonPanel();
       addressPanel = new CurrentFolderDisplayPanel();
       fp.setLayout(new GridLayout(9,1));
       this.add(fp,BorderLayout.EAST);
       this.add(dp,BorderLayout.CENTER);
       this.add(cp,BorderLayout.SOUTH);
       this.add(addressPanel,BorderLayout.NORTH);
       this.add(bp,BorderLayout.WEST);
    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set size of the frame
        this.setSize(1200, 800);
        bp.setVisible(false);
        //Create mouse listener
        this.setResizable(false);
        this.setBackground(new Color(255,255,255));
        //Create key listener
        this.setVisible(true);
        
    }
    
    void redraw () {
        dp.updateDataArray(false);
        dp.repaint();
        fp.repaint();
        cp.repaint();
        if(this.folderSystem.getCurrentFolder().getName().equals("directory") != true){ 
            bp.setVisible(true);
             bp.repaint();
        }else{ 
            bp.setVisible(false);
        }
       

        
        addressPanel.repaint();
    }
    
    private class FunctionPanel extends JPanel implements ActionListener,KeyListener{ 
        private JTextField searchField;
        private JButton sortAlphabetical;
        private JButton save;
        private JButton addFileButton;
        private JButton addFolderButton;
        private JButton removeFileButton;
        private JButton removeFolderButton;
        private JButton moveFolderButton;
        private JButton moveFileButton;
        
        FunctionPanel(){ 
            searchField = new JTextField("SEARCH FOR FOLDER ");
            searchField.setPreferredSize(new Dimension(200,100));
            searchField.addKeyListener(this);
            this.add(searchField);
            this.setBackground(new Color(255,255,255));
            
            
            this.sortAlphabetical = new JButton("SORT ALPHABETICAL"); 
            sortAlphabetical.addActionListener(this); 
            
            this.addFileButton = new JButton("ADD FILE"); 
            addFileButton.addActionListener(this);
            
            this.addFolderButton = new JButton("ADD FOLDER"); 
            addFolderButton.addActionListener(this);
            
            this.removeFolderButton = new JButton("REMOVE FOLDER"); 
            removeFolderButton.addActionListener(this);
            
            this.removeFileButton = new JButton("REMOVE FILE"); 
            removeFileButton.addActionListener(this);
            
            this.moveFolderButton = new JButton("MOVE FOLDER"); 
            moveFolderButton.addActionListener(this);
            
            this.moveFileButton = new JButton("MOVE FILE"); 
            moveFileButton.addActionListener(this);
            
            this.save = new JButton("SAVE");
            save.addActionListener(this);
            
            this.add(addFileButton);
            this.add(addFolderButton);
            this.add(removeFolderButton); 
            this.add(removeFileButton);
            this.add(moveFolderButton);
            this.add(moveFileButton);
            this.add(sortAlphabetical);
            this.add(save);

            
        }
        
     
        public void keyPressed(KeyEvent e){ 
            if(e.getKeyCode() == KeyEvent.VK_ENTER){ 
                String searchName = searchField.getText();
                if(folderSystem.getFolder(searchName) != null){ 
                    folderSystem.setCurrentFolder(folderSystem.getFolder(searchName));
                    searchField.setText("");
                    dp.removeAll();
                    redraw();
                }else{ 
                    JOptionPane.showMessageDialog(null,"Folder not Found");
                }
                
                
            }
        }
        
        public void keyTyped(KeyEvent e){
            
        }
        public void keyReleased(KeyEvent e){ 
            
        }
        
        
        public void actionPerformed(ActionEvent e){ 
            String action = e.getActionCommand(); 
            boolean sort = false;
            boolean actionPreformed = false;
            boolean sorting = false;
            if(action.equals("ADD FILE")){
                boolean duplicateNames = false; 
                String fileName = JOptionPane.showInputDialog("Enter the name of file to add");
                
                if(folderSystem.getCurrentFolder().findFile(fileName) != null){
                    duplicateNames = true;
                    JOptionPane.showMessageDialog(null,"Duplicate File Names!");
                }
                
                if(fileName.equals("") != true && duplicateNames == false && folderSystem.addFile(fileName) == true){ 
                    dp.updateDataArray(false);
                    actionPreformed = true;
                }
                    
            }else if (action.equals("ADD FOLDER")){ 
                boolean duplicateNames = false;
                String folderName = JOptionPane.showInputDialog("Enter the name of folder to add");
                if(folderSystem.getCurrentFolder().findFolder(folderName) != null) {
                    duplicateNames = true;
                    JOptionPane.showMessageDialog(null,"Duplicate Folder Names!");
                }
                
                if(folderName.equals("") != true && duplicateNames == false &&  folderSystem.addFolder(folderName) == true){
                    dp.updateDataArray(false);
                    actionPreformed = true;
                }
            }else if (action.equals("REMOVE FOLDER")){ 
                String folderName = JOptionPane.showInputDialog("Enter the name of folder to remove");
                if(folderSystem.removeFolder(folderName) == true){ 
                    actionPreformed = true;
                    dp.updateDataArray(false);
                }
            }else if (action.equals("REMOVE FILE")){ 
                String fileName = JOptionPane.showInputDialog("Enter the name of file to remove");
                
                if(folderSystem.removeFile(fileName) == true){
                    dp.updateDataArray(false);
                    actionPreformed = true;
                }
            }else if (action.equals("MOVE FOLDER")){
                String folderName = JOptionPane.showInputDialog("Enter the name of folder to remove");
                String destinationName = JOptionPane.showInputDialog("Enter the name of folder to move to");
                if(folderSystem.moveFolder(folderName,destinationName)){ 
                    JOptionPane.showMessageDialog(null,"Moving you to destination folder");
                    folderSystem.setCurrentFolder(folderSystem.getFolder(destinationName));
                    dp.updateDataArray(false);
                    actionPreformed = true;
                    dp.removeAll();
                    dp.repaint();
                    bp.repaint();
                }else{ 
                     JOptionPane.showMessageDialog(null,"Please enter valid folders");
                }
                
            }else if (action.equals("MOVE FILE")){ 
                String fileName = JOptionPane.showInputDialog("Enter the name of file to remove");
                String destinationFolder = JOptionPane.showInputDialog("Enter the name of destination folder");
                if(folderSystem.moveFile(fileName,destinationFolder)) {
                    JOptionPane.showMessageDialog(null,"Moving you to destination folder");
                    folderSystem.setCurrentFolder(folderSystem.getFolder(destinationFolder));
                    dp.updateDataArray(false);
                    actionPreformed = true;
                    dp.removeAll();
                    dp.repaint();
                    bp.repaint();

                }else{ 
                    JOptionPane.showMessageDialog(null,"Please enter valid folders");
                }
                
            }else if(action.equals("SAVE")) {
                  String fileName = JOptionPane.showInputDialog("What is the name of the file you would like to save your current data on?");
                  PrintWriter output = null;
                  try{
                        output = new PrintWriter(fileName);
                  }catch(Exception exception) {
                        JOptionPane.showMessageDialog(null,"Exception caught");
                  }
                  if(output != null && folderSystem.getDirectory() != null) {
                        Queue<Folder<Object>> q = new LinkedList<Folder<Object>>();
                        Queue<Folder<Object>> folders = new LinkedList<Folder<Object>>();
                        q.add(folderSystem.getDirectory());
                        while(!q.isEmpty()) {
                              Folder<Object> currentFolder = q.remove();
                              for(Folder<Object> folder : currentFolder.getFoldersArray()) {
                                    folders.add(folder);
                                    q.add(folder);
                              }
                        }
                        while(!folders.isEmpty()) {
                              Folder currentFolder = folders.remove();
                              output.print(currentFolder.getName() + currentFolder.getFoldersArray() + currentFolder.getFilesArray());
                              output.println();
                        }
                  }else{
                        JOptionPane.showMessageDialog(null,"You have no data right now!");
                  }
                  output.close();
            }else if(action.equals("SORT ALPHABETICAL")){ 
                JOptionPane.showMessageDialog(null,"Alphabetically sorted folder system outputted to console");
                System.out.println("SORTED FOLDER SYSTEM PRINTED");
                folderSystem.sortFolderSystem();
                folderSystem.printFolderSystem();
               
            }
            
            if(actionPreformed) {
                if(folderSystem.getCurrentFolder().getName().equals("directory")) {
                    bp.repaint();
                    bp.setVisible(false);
                }else{
                    bp.repaint();
                    bp.setVisible(true);
                }
                dp.removeAll();
                dp.revalidate();
                dp.repaint();
                addressPanel.repaint();
                cp.repaint();
                actionPreformed = false;
            }
            
                      
        }
        
    }
    
    private class CounterPanel extends JPanel {
        JLabel fileCounter;
        JLabel folderCounter;
        
        CounterPanel() {
            this.fileCounter = new JLabel("Files: " + "0");
            this.folderCounter = new JLabel("Folders: " + "0");
            this.add(fileCounter);
            this.add(folderCounter);
            this.setBackground(new Color(255,255,255));
        }
        
        public void paintComponent(Graphics g){ 
            super.paintComponent(g);
            //change color
            g.setColor(new Color(255, 0, 255));
            
            
            g.setColor(new Color(255, 0, 255));
            this.fileCounter.setText("Files: " + folderSystem.countFiles());
            this.folderCounter.setText("Folders: " + folderSystem.countFolders());
        }
    }
    
    private class CurrentFolderDisplayPanel extends JPanel{ 
        JLabel address;
        
        CurrentFolderDisplayPanel(){ 
            this.address = new JLabel();
            this.add(address);
            this.setBackground(new Color(255,255,255));
        }
        
        public void paintComponent(Graphics g){ 
            super.paintComponent(g); 
            g.setColor(new Color(255, 0, 255));
            this.address.setText(folderSystem.getCurrentFolder().getAddressText() + " {" + folderSystem.getCurrentFolder().getName() + "}");
            
           
        }
        
    }
        
    
    public class DisplayPanel extends JPanel{ 
        int maxX; 
        int maxY; 
        int gridToScreenRatioY; 
        int gridToScreenRatioX; 
        ArrayList<DataType> dataArray;
        ImageIcon folder = new ImageIcon("folderImage.png"); 
        ImageIcon file = new ImageIcon("fileImage.png"); 
       
        
        DisplayPanel(){ 
            maxX = 800; 
            maxY = 750;
            gridToScreenRatioX = maxY/5;
            gridToScreenRatioY = maxX/5; 
            this.setBackground(new Color(255,255,255));
            this.dataArray = folderSystem.getCurrentFolder().combineFileAndFolder();
            
        }
        
        public void updateDataArray(boolean sorted){ 
            dataArray = folderSystem.getCurrentFolder().combineFileAndFolder();
            if(sorted && dataArray.size() > 1) {
                Collections.sort(folderSystem.getCurrentFolder().getFoldersArray());
                Collections.sort(folderSystem.getCurrentFolder().getFilesArray());
                dataArray = folderSystem.getCurrentFolder().combineFileAndFolder();
            }
        }
        public void paintComponent(Graphics g){ 
            super.paintComponent(g);
            setDoubleBuffered(true);
            int originalSize = dataArray.size();
            for(int dataNum = 0; dataNum < originalSize; dataNum++){
                if(this.dataArray.get(dataNum) instanceof Folder){ 
                    if(!dataArray.get(dataNum).getName().equals("")) {
                        JButton folderButton = new JButton();
                        folderButton.setIcon(folder);
                        folderButton.setBackground(new Color(0,0,0,0)); 
                        folderButton.setBorder(BorderFactory.createEmptyBorder());
                        folderButton.addActionListener(new FolderButtonListener());
                        folderButton.setText(dataArray.get(dataNum).getName()); 
                        folderButton.setHorizontalTextPosition(SwingConstants.CENTER);
                        this.add(folderButton);
                    }else{
                        folderSystem.removeFolder(dataArray.get(dataNum).getName());
                        dataArray.remove(dataNum);
                        cp.repaint();
                    }
                }else if (dataArray.get(dataNum) instanceof FileObject){
                    JButton fileButton = new JButton();
                    fileButton.setIcon(file);
                    fileButton.setBackground(new Color(0,0,0,0)); 
                    fileButton.setBorder(BorderFactory.createEmptyBorder());
                    fileButton.addActionListener(new FileButtonListener());
                    fileButton.setText(dataArray.get(dataNum).getName()); 
                    fileButton.setHorizontalTextPosition(SwingConstants.CENTER);
                    fileButton.setPreferredSize(new Dimension(100,100));
                    this.add(fileButton);
                }
                
            }
          
            
        }
        
        private class FolderButtonListener implements ActionListener{ 
            public void actionPerformed(ActionEvent event){ 
                String action = event.getActionCommand();
                Folder newCurrentFolder = folderSystem.getFolder(action);
                folderSystem.setCurrentFolder(newCurrentFolder);
                
                dp.removeAll();
                redraw();
                
            }
            
        }
        
         private class FileButtonListener implements ActionListener{ 
            public void actionPerformed(ActionEvent event){ 
                JOptionPane.showMessageDialog(null,"Opening File....");
            }
            
        }
         
         
        
        
    }//end of display panel
      
    private class BackButtonPanel extends JPanel{ 
        ImageIcon backIcon = new ImageIcon("backIcon.png");
        ImageIcon backIconPressed = new ImageIcon("backIconPressed.png");
        JButton back;
        BackButtonPanel(){ 
            this.back = new JButton(backIcon);
            this.back.setBackground(new Color(0,0,0,0));
            this.back.setBorder(BorderFactory.createEmptyBorder()); 
            this.back.setRolloverIcon(new ImageIcon("backIconPressed.png")); 
            this.back.setFocusPainted(false); 
            this.back.addActionListener(new BackButtonListener());
            this.back.setBounds(20,10,80,80);
            this.add(back);
            this.setBackground(new Color(255,255,255));
        }
        
        public void paintComponent(Graphics g){ 
            super.paintComponent(g);
        }
            
        private class BackButtonListener implements ActionListener{ 
            public void actionPerformed(ActionEvent event){ 
                if(folderSystem.getCurrentFolder().getName().equals("directory") != true){ 
                    LinkedList<Folder> currentFolderAddress = folderSystem.getCurrentFolder().getAddress();
                    folderSystem.setCurrentFolder(currentFolderAddress.get(currentFolderAddress.size()-1));
                    dp.removeAll();
                    redraw();
                }
            }
            
        }
    }
            
            
            
         

    
    
    }
