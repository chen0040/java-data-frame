package com.github.chen0040.data.evaluators;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 10/13/16.
 */
public class RegressionEvaluator {

   private List<Double> predictedOutputValues = new ArrayList<>();
   private List<Double> actualOutputValues = new ArrayList<>();

   // mean square error penalize series that have values which has large error
   private double meanSquaredError = 0;
   private double rootMeanSquaredError = 0;

   // mean absolute error penalize series that have lots of values with small errors
   private double meanAbsoluteError = 0;

   // R-square describes the proportion of variance in the response variable explained by the regression model
   private double RSquare = 0;

   public void reset(){
      predictedOutputValues.clear();
      actualOutputValues.clear();
   }

   public void evaluate(double predicted,double actual){
      predictedOutputValues.add(predicted);
      actualOutputValues.add(actual);
   }

   public void update(){
      int size = predictedOutputValues.size();

      if(size == 0) return;

      double mu_expected = 0;

      for(int i=0; i < size; ++i) {
         double expected = predictedOutputValues.get(i);
         mu_expected += expected;
      }
      mu_expected /= size;

      double squaredSum = 0;
      double absoluteSum = 0;
      double SS_total = 0;
      double SS_res = 0;
      for(int i=0; i < size; ++i){
         double expected = predictedOutputValues.get(i);
         double actual = actualOutputValues.get(i);

         double difference = expected - actual;

         squaredSum += difference * difference;
         absoluteSum += Math.abs(difference);

         SS_total += Math.pow(expected - mu_expected, 2);
         SS_res += difference * difference;
      }

      meanSquaredError = squaredSum / size;
      meanAbsoluteError = absoluteSum / size;

      rootMeanSquaredError = Math.sqrt(meanSquaredError);

      RSquare = 1 - SS_res / SS_total;

   }

   public double getMeanSquaredError(){
      return meanSquaredError;
   }

   public double getRootMeanSquaredError(){
      return rootMeanSquaredError;
   }


   public double getMeanAbsoluteError() {
      return meanAbsoluteError;
   }

   public double getRSquare() {
      return RSquare;
   }
}
