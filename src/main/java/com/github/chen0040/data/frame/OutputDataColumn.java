package com.github.chen0040.data.frame;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 5/5/2017.
 */
public class OutputDataColumn implements Serializable, DataColumn {
   private String columnName;
   private final List<String> levels = new ArrayList<>();

   public OutputDataColumn(){

   }

   public OutputDataColumn(String columnName) {
      this.columnName = columnName;
   }

   @Override
   public String getColumnName(){
      return columnName;
   }

   @Override
   public void setColumnName(String columnName) {
      this.columnName = columnName;
   }

   @Override
   public boolean isCategorical(){
      return !levels.isEmpty();
   }


   public OutputDataColumn makeCopy() {
      OutputDataColumn clone = new OutputDataColumn(columnName);
      clone.copy(this);
      return clone;
   }

   @Override
   public void setLevels(List<String> levels) {
      this.levels.clear();
      this.levels.addAll(levels);
   }

   @Override
   public List<String> getLevels(){
      return levels;
   }

   public void copy(OutputDataColumn that){
      columnName = that.columnName;
      levels.clear();
      levels.addAll(that.levels);
   }

   @Override
   public boolean isOutputColumn(){
      return true;
   }
}
