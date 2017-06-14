package com.github.chen0040.data.evaluators;

import com.github.chen0040.data.utils.TupleTwo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


/**
 * Created by xschen on 11/16/16.
 */
public class ConfusionMatrix {
   private Map<TupleTwo<String, String>, Integer> matrix = new HashMap<>();
   private Set<String> labels = new HashSet<>();

   public void incCount(String actual, String predicted) {
      labels.add(actual);
      labels.add(predicted);
      TupleTwo<String, String> key = new TupleTwo<>(actual, predicted);
      matrix.put(key, matrix.getOrDefault(key, 0) + 1);
   }

   public List<String> getLabels(){
      List<String> result = new ArrayList<>();

      result.addAll(labels.stream().collect(Collectors.toList()));

      return result;
   }



   // sum of a row representing class c, which is sum of cases that truely belong to class c
   public int getRowSum(String actual) {
      List<String> list = this.getLabels();
      int sum = 0;
      for(int i=0; i < list.size(); ++i) {
         String predicted = list.get(i);
         sum += getCount(actual, predicted);
      }
      return sum;
   }


   // sum of a column representing class c, which is sum of cases the classifiers claims to belong to class c
   public int getColumnSum(String predicted) {
      List<String> list = this.getLabels();
      int sum = 0;
      for(int i=0; i < list.size(); ++i) {
         String actual = list.get(i);
         sum += getCount(actual, predicted);
      }
      return sum;
   }



   public int getCount(String actual, String predicted) {
      return matrix.getOrDefault(new TupleTwo<>(actual, predicted), 0);
   }


   public void reset() {
      matrix.clear();
   }


}
