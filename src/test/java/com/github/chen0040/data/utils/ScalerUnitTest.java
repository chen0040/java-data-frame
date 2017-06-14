package com.github.chen0040.data.utils;


import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataQuery;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;


/**
 * Created by xschen on 14/6/2017.
 */
public class ScalerUnitTest {

   @Test
   public void test_scale() throws IOException {
      DataFrame frame = DataQuery.libsvm().from(FileUtils.getResource("heart_scale.txt")).build();
      Scaler scaler = new Scaler();
      scaler.fit(frame);
      for(int i=0; i < 10; i++) {
         System.out.println(scaler.transform(frame.row(i)));
      }
   }
}
