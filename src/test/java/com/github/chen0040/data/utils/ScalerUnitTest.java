package com.github.chen0040.data.utils;


import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataQuery;
import com.github.chen0040.data.frame.DataRow;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
         DataRow original = frame.row(i);
         DataRow processed = scaler.inverseTransform(scaler.transform(frame.row(i)));

         System.out.println(original);
         System.out.println(processed);

         for(int j =0; j < original.getColumnNames().size(); ++j) {
            assertThat(original.getCell(original.getColumnNames().get(j))).isCloseTo(processed.getCell(processed.getColumnNames().get(j)), within(0.001));
         }
      }

      Scaler clone = scaler.makeCopy();
      for(int i=0; i < 10; i++) {
         assertThat(scaler.transform(frame.row(i))).isEqualToComparingFieldByField(clone.transform(frame.row(i)));
      }


   }
}
