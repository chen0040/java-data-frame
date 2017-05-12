package com.github.chen0040.data.frame;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 5/5/2017.
 */
public class OutputDataColumn {
   private String columnName;
   private final List<String> levels = new ArrayList<>();

   public OutputDataColumn(){

   }

   public OutputDataColumn(String columnName) {
      this.columnName = columnName;
   }

   public String getColumnName(){
      return columnName;
   }

   public void setColumnName(String columnName) {
      this.columnName = columnName;
   }

   public boolean isCategorical(){
      return !levels.isEmpty();
   }


   public OutputDataColumn makeCopy() {
      OutputDataColumn clone = new OutputDataColumn(columnName);
      clone.copy(this);
      return clone;
   }

   public void setLevels(List<String> levels) {
      this.levels.clear();
      this.levels.addAll(levels);
   }

   public List<String> getLevels(){
      return levels;
   }

   public void copy(OutputDataColumn that){
      columnName = that.columnName;
      levels.clear();
      levels.addAll(that.levels);
   }
}
