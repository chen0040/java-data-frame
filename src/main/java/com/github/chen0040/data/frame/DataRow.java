package com.github.chen0040.data.frame;


import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 28/4/2017.
 */
public interface DataRow {
   double target();
   String categoricalTarget();

   double[] toArray();

   void setCell(String columnName, double value);

   void setCategoricalCell(String columnName, String value);

   List<String> getColumnNames();

   List<String> getCategoricalColumnNames();

   List<String> getTargetColumnNames();

   List<String> getCategoricalTargetColumnNames();

   double getCell(String key);

   double getTargetCell(String columnName);

   String getCategoricalTargetCell(String columnName);

   void setTargetCell(String columnName, double value);

   void setCategoricalTargetCell(String columnName, String label);

   void setColumnNames(List<String> inputColumns);

   void setCategoricalColumnNames(List<String> inputColumns);

   void setLevels(Map<String, List<String>> levels);

   void setTargetColumnNames(List<String> outputColumns);

   void setCategoricalTargetColumnNames(List<String> outputColumns);

   DataRow makeCopy();

   void copy(DataRow that);

   String targetColumnName();

   String categoricalTargetColumnName();

   String getCategoricalCell(String key);

   Map<String,List<String>> getLevels();
}
