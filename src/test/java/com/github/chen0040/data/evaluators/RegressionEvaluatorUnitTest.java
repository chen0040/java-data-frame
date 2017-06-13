package com.github.chen0040.data.evaluators;


import org.testng.annotations.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 13/6/2017.
 */
public class RegressionEvaluatorUnitTest {

   @Test
   public void test_evaluate(){
      Random random = new Random();
      RegressionEvaluator evaluator = new RegressionEvaluator();
      for(int i=0; i < 10; ++i) {
         double x = i;
         double actual_y = x * x + x + random.nextDouble();
         double predicted_y = x * x + x;
         evaluator.evaluate(predicted_y, actual_y);
      }

      evaluator.update();

      System.out.println("R^2: " + evaluator.getRSquare());
      assertThat(evaluator.getRSquare()).isCloseTo(1.0, within(0.01));
   }
}
