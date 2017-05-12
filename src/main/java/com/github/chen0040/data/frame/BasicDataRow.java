package com.github.chen0040.data.frame;


import com.github.chen0040.data.utils.CollectionUtils;
import com.github.chen0040.data.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by xschen on 1/5/2017.
 * A data row consists two types of columns:
 *
 * column: a column represents an input column for which values are numeric
 * target column: a target column represents an output column for which values are numeric
 */
public class BasicDataRow implements DataRow {

   private final Map<String, Double> targets = new HashMap<>();
   private final Map<String, String> categoricalTargets = new HashMap<>();

   private final Map<String, Double> values = new HashMap<>();
   private final Map<String, String> categoricalValues = new HashMap<>();

   private final List<String> columns = new ArrayList<>();
   private final List<String> categoricalColumns = new ArrayList<>();

   private final List<String> targetColumns = new ArrayList<>();
   private final List<String> categoricalTargetColumns = new ArrayList<>();

   private final Map<String, List<String>> levels = new HashMap<>();

   @Override public double target() {
      return getTargetCell(targetColumnName());
   }

   @Override public String categoricalTarget() {
      return getCategoricalTargetCell(categoricalTargetColumnName());
   }

   @Override
   public double getTargetCell(String columnName){
      return targets.getOrDefault(columnName, 0.0);
   }

   @Override
   public String getCategoricalTargetCell(String columnName){
      return categoricalTargets.getOrDefault(columnName, "");
   }

   @Override
   public void setTargetCell(String columnName, double value) {
      if(value == 0.0) {
         targets.remove(columnName);
      }
      targets.put(columnName, value);
   }

   @Override
   public void setCategoricalTargetCell(String columnName, String value) {
      if(StringUtils.isEmpty(value)) {
         categoricalTargets.remove(columnName);
      }
      categoricalTargets.put(columnName, value);
   }

   @Override public void setColumnNames(List<String> inputColumns) {
      columns.clear();
      columns.addAll(inputColumns);
   }

   @Override public void setCategoricalColumnNames(List<String> inputColumns) {
      categoricalColumns.clear();
      categoricalColumns.addAll(inputColumns);
   }

   @Override public void setLevels(Map<String, List<String>> levels){
      this.levels.clear();
      this.levels.putAll(levels);
   }



   @Override public void setTargetColumnNames(List<String> outputColumns) {
      targetColumns.clear();
      targetColumns.addAll(outputColumns);
   }

   @Override public void setCategoricalTargetColumnNames(List<String> outputColumns) {
      categoricalTargetColumns.clear();
      categoricalTargetColumns.addAll(outputColumns);
   }

   @Override public DataRow makeCopy() {
      DataRow clone = new BasicDataRow();
      clone.copy(this);
      return clone;
   }


   @Override public void copy(DataRow that) {

      targets.clear();
      categoricalTargets.clear();
      values.clear();
      categoricalValues.clear();
      columns.clear();
      categoricalColumns.clear();
      targetColumns.clear();
      categoricalTargetColumns.clear();

      for(String c : that.getTargetColumnNames()){
         targets.put(c, that.getTargetCell(c));
      }

      for(String c : that.getColumnNames()) {
         values.put(c, that.getCell(c));
      }

      for(String c : that.getCategoricalColumnNames()){
         categoricalValues.put(c, that.getCategoricalCell(c));
      }

      for(String c : that.getCategoricalTargetColumnNames()) {
         categoricalTargets.put(c, that.getCategoricalTargetCell(c));
      }

      setColumnNames(that.getColumnNames());
      setCategoricalColumnNames(that.getCategoricalColumnNames());
      setTargetColumnNames(that.getTargetColumnNames());
      setCategoricalTargetColumnNames(that.getCategoricalTargetColumnNames());
   }


   @Override public String targetColumnName() {
      return getTargetColumnNames().get(0);
   }

   @Override public String categoricalTargetColumnName() {
      return getCategoricalTargetColumnNames().get(0);
   }


   @Override public double[] toArray() {
      List<String> cols = getColumnNames();

      List<Double> result = new ArrayList<>();
      for(int i=0; i < cols.size(); ++i) {
         result.add(getCell(cols.get(i)));
      }

      cols = getCategoricalColumnNames();
      for(int i=0; i < cols.size(); ++i) {
         String name = cols.get(i);
         String val = getCategoricalCell(name);
         List<String> levelsInFactor = levels.get(name);
         int index = levelsInFactor.indexOf(val);
         for(int j=0; j < levelsInFactor.size(); ++j){
            result.add(j == index ? 1.0 : 0.0);
            if(levelsInFactor.size()==2){
               break;
            }
         }
      }

      return CollectionUtils.toDoubleArray(result);
   }

   private void buildColumns(){
      List<String> cols = values.keySet().stream().collect(Collectors.toList());
      cols.sort(String::compareTo);
      columns.addAll(cols);
   }

   private void buildCategoricalColumns(){
      List<String> cols = categoricalValues.keySet().stream().collect(Collectors.toList());
      cols.sort(String::compareTo);
      categoricalColumns.addAll(cols);
   }

   private void buildTargetColumns(){
      List<String> cols = targets.keySet().stream().collect(Collectors.toList());
      cols.sort(String::compareTo);
      targetColumns.addAll(cols);
   }

   private void buildCategoricalTargetColumns(){
      List<String> cols = categoricalTargets.keySet().stream().collect(Collectors.toList());
      cols.sort(String::compareTo);
      categoricalTargetColumns.addAll(cols);
   }

   @Override public void setCell(String columnName, double value) {
      if(value == 0.0) {
         values.remove(columnName);
      }

      values.put(columnName, value);
   }


   @Override public void setCategoricalCell(String columnName, String value) {
      if(StringUtils.isEmpty(value)) {
         categoricalValues.remove(columnName);
      }

      categoricalValues.put(columnName, value);
   }

   @Override public List<String> getColumnNames() {
      if(columns.size() < values.size()) {
         buildColumns();
      }
      return columns;
   }

   @Override public List<String> getCategoricalColumnNames() {
      if(categoricalColumns.size() < categoricalValues.size()){
         buildCategoricalColumns();
      }
      return categoricalColumns;
   }

   @Override
   public List<String> getTargetColumnNames() {
      if(targetColumns.size() < targets.size()){
         buildTargetColumns();
      }
      return targetColumns;
   }

   @Override
   public List<String> getCategoricalTargetColumnNames() {
      if(categoricalTargetColumns.size() < categoricalTargets.size()){
         buildCategoricalTargetColumns();
      }
      return categoricalTargetColumns;
   }

   @Override public double getCell(String key) {
      return values.getOrDefault(key, 0.0);
   }

   @Override public String getCategoricalCell(String key) {
      return categoricalValues.getOrDefault(key, "");
   }

   @Override
   public String toString(){
      StringBuilder sb = new StringBuilder();
      List<String> keys = getColumnNames();
      for(int i=0; i < keys.size(); ++i){
         if(i != 0){
            sb.append(", ");
         }
         sb.append(keys.get(i)).append(":").append(getCell(keys.get(i)));
      }

      List<String> keys2 = getCategoricalColumnNames();
      if(!keys2.isEmpty() && !keys.isEmpty()){
         sb.append(", ");
      }
      for(int i=0; i < keys2.size(); ++i){
         if(i != 0){
            sb.append(", ");
         }
         sb.append(keys2.get(i)).append(":").append(getCategoricalCell(keys2.get(i)));
      }
      sb.append(" =>");

      keys = getTargetColumnNames();
      if(!keys.isEmpty()) {
         sb.append(" (");
         for (int i = 0; i < keys.size(); ++i) {
            if (i != 0) {
               sb.append(", ");
            }
            sb.append(keys.get(i)).append(":").append(getTargetCell(keys.get(i)));
         }
         sb.append(")");
      }

      keys = getCategoricalTargetColumnNames();
      if(!keys.isEmpty()){
         sb.append(" (");
         for(int i=0; i < keys.size(); ++i){
            if(i != 0){
               sb.append(", ");
            }
            sb.append(keys.get(i)).append(":").append(getCategoricalTargetCell(keys.get(i)));
         }
         sb.append(")");
      }


      return sb.toString();
   }
}
