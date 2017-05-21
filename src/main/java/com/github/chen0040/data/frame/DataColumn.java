package com.github.chen0040.data.frame;


import java.util.List;


/**
 * Created by xschen on 21/5/2017.
 */
public interface DataColumn {
   String getColumnName();

   void setColumnName(String columnName);

   boolean isCategorical();

   void setLevels(List<String> levels);

   List<String> getLevels();

   boolean isOutputColumn();

   default boolean isInputColumn(){
      return !isOutputColumn();
   }

   default boolean isNumerical(){
      return !isCategorical();
   }
}
