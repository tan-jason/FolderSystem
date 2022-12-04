/*
 * DataType.java
 * Version 1.0
 * Jason and Nathan 
 * 12/4/2019
 */

import java.util.LinkedList;

public abstract class DataType<E> implements Comparable<DataType<E>>{
      private String name;
      private LinkedList<Folder<E>> address;
      
      DataType(String n) {
            this.name = n;
            this.address = new LinkedList<Folder<E>>();
      }

      public String getName() {
            return this.name;
      }
      
      public LinkedList<Folder<E>> getAddress() {
            return this.address;
      }
      
      abstract boolean updateAddress(Folder<E> parentFolder);
     
      @Override 
      public int compareTo(DataType<E> otherData) {
          if(otherData.getName().charAt(0) < this.name.charAt(0)) {
              return 1;
          }else if(otherData.getName().charAt(0) > this.name.charAt(0)) {
              return -1;
          }else{
              return 0;
          }
      }
      
      @Override 
      public String toString() {
            return this.name;
      }
      
}
      