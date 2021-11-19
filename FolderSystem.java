/*
 * FolderSystem.java
 * Jason and Nathan
 * November 25th
 */

import java.util.Queue; 
import java.util.LinkedList;

public class FolderSystem<E> { //tree
    private Folder<E> directory;
    private Folder<E> currentFolder; 
    private int numFolders;
    private int numFiles;
    
    public FolderSystem(){ 
        directory = new Folder<E>("directory");
        this.currentFolder = this.directory;
        this.numFolders = 0;
        this.numFiles = 0;
    }
    
    //------------------------------------------------------------------------------
    public boolean addFolder(String folderName) {
        Folder<E> newFolder = new Folder<E>(folderName);
        if(this.currentFolder.findFolder(folderName) == newFolder) {
            return false;
        }else{
            newFolder.updateAddress(this.currentFolder);
            return currentFolder.addFolder(newFolder); 
        }
    }
    
    public boolean addFile(String fileName) {
        FileObject<E> newFile = new FileObject<E>(fileName);
        if(this.currentFolder.findFile(fileName) == newFile) {
            return false;
        }else{
            newFile.updateAddress(this.currentFolder);
            return this.currentFolder.addFile(newFile);
        }
    }
    
    public boolean insertFolder(String folderName,String parentFolderName) {
          Folder<E> parentFolder = this.getFolder(parentFolderName);
          if(parentFolder.findFolder(folderName) != null) {
                return false;
          }else{
                Folder<E> folderToInsert = new Folder<E>(folderName);
                folderToInsert.updateAddress(parentFolder);
                return parentFolder.addFolder(folderToInsert);
          }
    }
    
    public boolean insertFile(String fileName,String parentFolderName) {
          Folder<E> parentFolder = this.getFolder(parentFolderName);
          if(parentFolder.findFile(fileName) != null) {
                return false;
          }else{
                FileObject<E> fileToInsert = new FileObject<E>(fileName);
                fileToInsert.updateAddress(parentFolder);
                return parentFolder.addFile(fileToInsert);
          }
    }
    
    public boolean removeFolder(String folderName) {
        Folder<E> folderToRemove = this.getFolder(folderName);
        if(folderToRemove == null) {
            return false;
        }else{
            return this.currentFolder.removeFolder(folderToRemove);
        }
    }
    
    public boolean removeFile(String fileName) {
        FileObject<E> fileToRemove = this.getFile(fileName);
        if(fileToRemove == null) {
            return false;
        }else{
            return this.currentFolder.removeFile(fileToRemove);
        }
        
    }
    
    public FileObject<E> getFile(String desiredFile) {
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(this.directory);
        folders.add(this.directory);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
        while(!folders.isEmpty()) {
              Folder<E> currentFolder = folders.remove();
              if(currentFolder.findFile(desiredFile) != null){ 
                  return currentFolder.findFile(desiredFile);
              }
        }
        return null;
    }
    
    public Folder<E> getFolder(String desiredFolder){ 
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(this.directory);
        folders.add(this.directory);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
        while(!folders.isEmpty()) {
              Folder<E> currentFolder = folders.remove();
              if(currentFolder.getName().equals(desiredFolder)){ 
                  return currentFolder;
              }
        }
        return null;
    }
    
    //update addresses of a moved folder, and the elements it contains
    private void updateMovedAddress(Folder<E> parentFolder){ 
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(parentFolder);
        folders.add(parentFolder);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
        folders.remove();
        while(!folders.isEmpty()) {
              Folder<E> currentFolder = folders.remove();
              LinkedList<Folder<E>> address = currentFolder.getAddress();
              currentFolder.updateAddress(address.get(address.size()-1));
        }
    }

    
    
    public boolean moveFolder(String folderToMoveName, String destinationFolderName){ 
        Folder<E> folderToMove = getFolder(folderToMoveName); 
        Folder<E> destinationFolder = getFolder(destinationFolderName); 
        if(folderToMove == null || destinationFolder == null){ 
            return false;
        }else{ 
            if(destinationFolder.findFolder(folderToMove.getName()) != null) {
                return false;
            }else{
                Folder<E> sourceFolder = folderToMove.getAddress().get(folderToMove.getAddress().size()-1);
                sourceFolder.removeFolder(folderToMove);
                folderToMove.updateAddress(destinationFolder);
                this.updateMovedAddress(folderToMove);
                return destinationFolder.addFolder(folderToMove);
            }
        }
        
    }
    
    public boolean moveFile(String fileName,String destinationName) {
        FileObject<E> fileToMove = getFile(fileName);
        Folder<E> destinationFolder = getFolder(destinationName);
        if(destinationFolder.getFilesArray() != null && destinationFolder.findFile(fileName) != null) {
            return false;
        }else{
            Folder<E> sourceFolder = fileToMove.getAddress().get(fileToMove.getAddress().size()-1);
            sourceFolder.removeFile(fileToMove);
            fileToMove.updateAddress(destinationFolder);
            return destinationFolder.addFile(fileToMove);
        }
    }
    
    public void sortFolderSystem(){
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(this.directory);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
        folders.add(this.directory);
        while(!folders.isEmpty()) {
            
              Folder<E> currentFolder = folders.remove();
              currentFolder.sortFoldersArray();
              currentFolder.sortFilesArray();
              
              
        }
        System.out.println(this.getCurrentFolder().getFoldersArray());
    }
    
    public void printFolderSystem() {
        if(this.directory == null) {
            return;
        }
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(this.directory);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
        while(!folders.isEmpty()) {
              Folder<E> currentFolder = folders.remove();
              currentFolder.print();
        }
    }
    
    public int countFolders() {
        int totalFolders = 0;
        if(this.directory == null) {
            return 0;
        }
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(this.directory);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
        while(!folders.isEmpty()) {
              folders.remove();
              totalFolders++;
              
        }
        this.numFolders = totalFolders;
        return this.numFolders;
    }
    
    public int countFiles() {
        int totalFiles = 0;
        for(FileObject<E> f : this.directory.getFilesArray()) {
            totalFiles++;
        }
        if(this.directory == null) {
            return 0;
        }
        Queue<Folder<E>> q = new LinkedList<Folder<E>>();
        Queue<Folder<E>> folders = new LinkedList<Folder<E>>();
        q.add(this.directory);
        while(!q.isEmpty()) {
            Folder<E> currentFolder = q.remove();
              for(Folder<E> folder : currentFolder.getFoldersArray()) {
                    folders.add(folder);
                    q.add(folder);
              }
        }
     
        while(!folders.isEmpty()) {
            Folder<E> currentFolder = folders.remove();
            for(FileObject<E> f : currentFolder.getFilesArray()) {
                totalFiles++;
            }
        }
        this.numFiles = totalFiles;
        return this.numFiles;
    }
    
//---------------------------------------------
    public Folder<E> getDirectory(){ 
        return this.directory; 
    }
    
    public Folder<E> getCurrentFolder(){ 
        return this.currentFolder;
    }
    
    public void setCurrentFolder(Folder<E> folder){ 
        this.currentFolder = folder;
    }
 //---------------------------------------------
} //end of FolderSystem class


