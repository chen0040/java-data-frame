package com.github.chen0040.data.utils;


import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Created by xschen on 14/6/2017.
 */
public class StdDevUnitTest {

   @Test
   public void test_stddev(){
      double sigma = StdDev.apply(new double[] { 1, 2, 3} , 2);

      System.out.println("sigma: " + sigma);
      assertEquals(sigma, 1.0);
   }
}
