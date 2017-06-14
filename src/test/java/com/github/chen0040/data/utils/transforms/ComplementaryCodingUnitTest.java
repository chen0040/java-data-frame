package com.github.chen0040.data.utils.transforms;


import com.github.chen0040.data.frame.BasicDataFrame;
import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataRow;
import com.github.chen0040.data.utils.CollectionUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 6/6/2017.
 */
public class ComplementaryCodingUnitTest {

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

      ComplementaryCoding coding = new ComplementaryCoding(frame);
      double[] y = coding.normalize(new double[] { 1, 0, 1, 0});
      assertThat(y).isEqualTo(new double[] { 1, 0, 1, 0, 0, 1, 0, 1});
   }

   @Test
   public void test_list_of_double_array() throws CloneNotSupportedException {
      List<double[]> data = new ArrayList<>();
      data.add(new double[] { 1.0, 1.0, 1.0, 1.0 });
      data.add(new double[] { 0.0, 0.0, 0.0, 0.0 });


      ComplementaryCoding coding = new ComplementaryCoding(data);
      double[] y = coding.normalize(new double[] {1, 0, 1, 0});

      System.out.println(CollectionUtils.toList(y));
      assertThat(y).isEqualTo(new double[] { 1, 0, 1, 0, 0, 1, 0, 1});

      ComplementaryCoding clone = (ComplementaryCoding)coding.clone();
      double[] y2 = clone.normalize(new double[] {1, 0, 1,0});
      assertThat(y2).isEqualTo(new double[] { 1, 0, 1, 0, 0, 1, 0, 1});
   }
}
