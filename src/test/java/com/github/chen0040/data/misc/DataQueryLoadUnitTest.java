package com.github.chen0040.data.misc;


import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataQuery;
import com.github.chen0040.data.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by xschen on 12/5/2017.
 */
public class DataQueryLoadUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(DataQueryLoadUnitTest.class);
   @Test
   public void test_load_calcium_dat() throws IOException {
      InputStream inputStream = FileUtils.getResource("calcium.dat");
      DataQuery.DataFrameQueryBuilder schema = DataQuery.csv().from(inputStream)
              .skipRows(34)
              .selectColumn(0).asCategory().asInput("Treatment")
              .selectColumn(1).asNumeric().asInput("Begin")
              .selectColumn(2).asNumeric().asInput("End")
              .selectColumn(3).asNumeric().asOutput("Decrease");

      DataFrame dataFrame = schema.build();
      logger.info("\n{}", dataFrame.head(10));
   }

   @Test
   public void test_load_carmileage_dat() throws IOException {
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
      logger.info("\n{}", dataFrame.head(10));
   }

   @Test
   public void test_load_clouds_dat() throws IOException {
      InputStream inputStream = FileUtils.getResource("clouds.dat");


      DataQuery.DataFrameQueryBuilder schema = DataQuery.csv().from(inputStream)
              .skipRows(30)
              .selectColumn(0).asNumeric().asInput("Unseeded_Clouds")
              .selectColumn(1).asNumeric().asOutput("Seeded_Clouds");

      DataFrame dataFrame = schema.build();
      logger.info("\n{}", dataFrame.head(10));
   }

   @Test
   public void test_load_iris() throws IOException {
      InputStream inputStream = FileUtils.getResource("iris.data");


      DataFrame dataFrame = DataQuery.csv(",", false)
              .from(inputStream)
              .selectColumn(0).asNumeric().asInput("Sepal Length")
              .selectColumn(1).asNumeric().asInput("Sepal Width")
              .selectColumn(2).asNumeric().asInput("Petal Length")
              .selectColumn(3).asNumeric().asInput("Petal Width")
              .selectColumn(4).asCategory().asOutput("Iris Type")
              .build();
      logger.info("\n{}", dataFrame.head(10));
   }
}
