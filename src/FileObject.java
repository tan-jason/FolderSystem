/*
 * FileObject.java
 * Version 1.0
 * Nathan and Jason 
 * 11/29/2019
 */

public class FileObject<E> extends DataType<E> { 
      
//--------------------------------------------------------
      //constructor
      public FileObject(String n) {
          super(n);
      }
//--------------------------------------------------------
      //when a files is moved, update the moved file to its new address 
      public boolean updateAddress(Folder<E> parentFolder) {
          for(Folder<E> f : parentFolder.getAddress()) {
              getAddress().add(f);
          }
          return getAddress().add(parentFolder);
      }
      
      
}
            
            
            
            
            