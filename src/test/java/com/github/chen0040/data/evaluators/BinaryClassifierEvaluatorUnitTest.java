package com.github.chen0040.data.evaluators;


import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 13/6/2017.
 */
public class BinaryClassifierEvaluatorUnitTest {

   @Test
   public void test_evaluate(){
      BinaryClassifierEvaluator evaluator = new BinaryClassifierEvaluator();
      for(int i=0; i < 50; i++){
         boolean actual = true;
         boolean predicted = false;
         evaluator.evaluate(actual, predicted);
      }
      for(int i=0; i < 50; ++i) {
         boolean actual = true;
         boolean predicted = true;
         evaluator.evaluate(actual, predicted);
      }

      assertThat(evaluator.getPrecision()).isEqualTo(1.0);
      assertThat(evaluator.getRecall()).isEqualTo(0.5);
      assertThat(evaluator.getF1Score()).isCloseTo(0.666, within(0.001));
      assertThat(evaluator.getFallout()).isEqualTo(Double.POSITIVE_INFINITY);
      assertThat(evaluator.getAccuracy()).isEqualTo(0.5);
      assertThat(evaluator.getMisclassificationRate()).isEqualTo(0.5);
      assertThat(evaluator.getFalseNegative()).isEqualTo(50);
      assertThat(evaluator.getTrueNegative()).isEqualTo(0);
      assertThat(evaluator.getTruePositive()).isEqualTo(50);
      assertThat(evaluator.getFalsePositive()).isEqualTo(0);

      System.out.println(evaluator.getSummary());
   }
}
