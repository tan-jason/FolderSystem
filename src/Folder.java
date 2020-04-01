/*
 * Folder.java
 * Version 1.0
 * Nathan and Jason
 * 11/29/2019
 */

import java.util.*;

public class Folder<E> extends DataType<E>{
      private ArrayList<FileObject<E>> filesArray;
      private ArrayList<Folder<E>> foldersArray;
// --------------------------------------------------------------------------------------
      //Constructor
      public Folder(String folderName){ 
          super(folderName);
          foldersArray = new ArrayList<Folder<E>>();
          filesArray = new ArrayList<FileObject<E>>();
      }
// --------------------------------------------------------------------------------------
      //getters 
      public ArrayList<FileObject<E>> getFilesArray(){
            return this.filesArray;
      }
      public ArrayList<Folder<E>> getFoldersArray() {
            return this.foldersArray;
      }
      public void setFoldersArray(ArrayList<Folder<E>> newFoldersArray){ 
          this.foldersArray = newFoldersArray;
      }
// --------------------------------------------------------------------------------------
      public String getAddressText(){ 
          String addressText = "";
          for(int addressNum = 0; addressNum < getAddress().size(); addressNum++){ 
              addressText = addressText + "[" +  getAddress().get(addressNum) + "]"; 
            }
          return addressText; 
      }
      
      public void sortFoldersArray(){ 
          ArrayList<Folder<E>> sortedFolder = new ArrayList<Folder<E>>();
          sortedFolder = this.foldersArray;
          Collections.sort(sortedFolder);
          this.foldersArray = sortedFolder;
      }
      
      public void sortFilesArray(){ 
          ArrayList<FileObject<E>> sortedFile = new ArrayList<FileObject<E>>();
          sortedFile = this.filesArray;
          Collections.sort(sortedFile);
          this.filesArray = sortedFile;
      }
      
      public boolean removeFile(FileObject<E> fileToRemove){ 
            return filesArray.remove(fileToRemove);
      }
      
      public boolean removeFolder(Folder<E> folderToRemove ){ 
            return foldersArray.remove(folderToRemove);
      }
      
      public boolean addFile(FileObject<E> fileToAdd){
            return filesArray.add(fileToAdd);
      }
      
      public boolean addFolder(Folder<E> newFolder){ 
            return foldersArray.add(newFolder);
      }
      
      //when a folder is moved, update the address of the moved folder to its new address
      public boolean updateAddress(Folder<E> parentFolder){
          if(parentFolder.getName().equals("directory")){ 
              return this.getAddress().add(parentFolder);
          }else{
              while(this.getAddress().isEmpty() != true){ 
                  getAddress().removeLast();
              }
              for(Folder<E> f : parentFolder.getAddress()) {
                  this.getAddress().add(f);
              }
              return this.getAddress().add(parentFolder);
          }
      }
      
      
      
      public Folder<E> findFolder(String folder) {
          for(int i = 0;i < this.foldersArray.size();i++) {
              if(this.foldersArray.get(i).getName().equals(folder)) {
                  return this.foldersArray.get(i);
              }
          }
          return null;
      }
      
      //find file methods by name and object
      public FileObject<E> findFile(String fileName) {
          for(FileObject<E> f : this.filesArray) {
              if(f.getName().equals(fileName)) {
                  return f;
              }
          }
          return null;
      }
      
       public FileObject<E> findFile(FileObject<E> desiredFileObject) {
          for(FileObject<E> f : this.filesArray) {
              if(f == desiredFileObject) {
                  return f;
              }
          }
          return null;
      }
      
      //combine files and data array to be outputted on the gui 
      public ArrayList<DataType<E>> combineFileAndFolder(){ 
          ArrayList<DataType<E>> dataTypeArray = new ArrayList<DataType<E>>();
          
          
          for(int folderNum = 0; folderNum < foldersArray.size(); folderNum++){ 
              dataTypeArray.add(foldersArray.get(folderNum)); 
          } 
          for(int fileNum = 0; fileNum < filesArray.size(); fileNum++){ 
              dataTypeArray.add(filesArray.get(fileNum)); 
          }
          return dataTypeArray;
      }
      
      
      //print out folder name and what it contains
      public void print() {
            System.out.println();
            System.out.println("\nCurrent Folder: " + this.getName());
            System.out.println("FileObjects: " + this.filesArray);
            System.out.print("Inner folder Names: " + this.foldersArray);
            System.out.println();
      }
} //end of Folder class