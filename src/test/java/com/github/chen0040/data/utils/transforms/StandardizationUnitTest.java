package com.github.chen0040.data.utils.transforms;


import com.github.chen0040.data.frame.BasicDataFrame;
import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataRow;
import com.github.chen0040.data.utils.CollectionUtils;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 6/6/2017.
 */
public class StandardizationUnitTest {

   @Test
   public void test_simple(){
      DataFrame frame = new BasicDataFrame();
      DataRow row = frame.newRow();
      row.setCell("c1", 1.0);
      row.setCell("c2", 1.0);
      row.setCell("c3", 1.0);
      row.setCell("c4", 1.0);
      frame.addRow(row);

      row = frame.newRow();
      row.setCell("c1", 0.0);
      row.setCell("c2", 0.0);
      row.setCell("c3", 0.0);
      row.setCell("c4", 0.0);
      frame.addRow(row);

      frame.lock();

      Standardization coding = new Standardization(frame);
      double[] y = coding.standardize(new double[] { 1, 0, 1, 0});
      System.out.println(CollectionUtils.toList(y));
      assertThat(y).hasSize(4);
   }
}
