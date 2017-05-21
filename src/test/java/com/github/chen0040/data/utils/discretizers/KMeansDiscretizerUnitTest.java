package com.github.chen0040.data.utils.discretizers;


import com.github.chen0040.data.frame.DataColumn;
import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataQuery;
import com.github.chen0040.data.utils.FileUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by xschen on 21/5/2017.
 */
public class KMeansDiscretizerUnitTest {


   @Test
   public void test_convert_numerical_continuous_values_to_discrete_values() throws IOException {
      InputStream inputStream = FileUtils.getResource("carmileage.dat");


      DataQuery.DataFrameQueryBuilder schema = DataQuery.csv().from(inputStream)
              .skipRows(29)
              .selectColumn(0).asCategory().asInput("MAKE/MODEL")
              .selectColumn(1).asNumeric().asInput("VOL")
              .selectColumn(2).asNumeric().asInput("HP")
              .selectColumn(3).asNumeric().asOutput("MPG")
              .selectColumn(4).asNumeric().asInput("SP")
              .selectColumn(5).asNumeric().asInput("WT");

      DataFrame dataFrame = schema.build();
      System.out.println(dataFrame.head(10));
      System.out.println("categorical column count: " + dataFrame.getAllColumns().stream().filter(DataColumn::isCategorical).count());
      System.out.println("numerical column count: " + dataFrame.getAllColumns().stream().filter(DataColumn::isNumerical).count());

      KMeansDiscretizer discretizer =new KMeansDiscretizer();
      discretizer.setMaxLevelCount(12); // set number of discrete values for each numerical column

      DataFrame newFrame = discretizer.fitAndTransform(dataFrame);

      System.out.println(newFrame.head(10));
      System.out.println("categorical column count: " + newFrame.getAllColumns().stream().filter(DataColumn::isCategorical).count());
      System.out.println("numerical column count: " + newFrame.getAllColumns().stream().filter(DataColumn::isNumerical).count());


   }
}
