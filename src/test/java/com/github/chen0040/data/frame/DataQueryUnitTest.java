package com.github.chen0040.data.frame;


import com.github.chen0040.data.utils.FileUtils;
import com.github.chen0040.data.utils.NumberUtils;
import com.github.chen0040.data.utils.StringUtils;
import com.github.chen0040.data.utils.TupleTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Created by xschen on 2/5/2017.
 */
public class DataQueryUnitTest {

   private Logger logger = LoggerFactory.getLogger(DataQueryUnitTest.class);

   @Test
   public void test_csv() throws IOException {

      int column_use = 3;
      int column_livch = 4;
      int column_age = 5;
      int column_urban = 6;

      String columnSplitter = ",";
      InputStream inputStream = FileUtils.getResource("contraception.csv");
      DataFrame frame = DataQuery.csv(columnSplitter)
              .from(inputStream)
              .skipRows(1)
              .selectColumn(column_livch).asInput("livch")
              .selectColumn(column_age).asNumeric().asInput("age")
              .selectColumn(column_age).transform(age -> Math.pow(StringUtils.parseDouble(age), 2)).asInput("age^2")
              .selectColumn(column_urban).asInput("urban")
              .selectColumn(column_use).transform(label -> label.equals("Y") ? 1.0 : 0.0).asOutput("use")
              .build();

      for(int i=0; i < 10; ++i){
         logger.info("row[{}]: {}", i, frame.row(i));
      }

      logger.info("row count: {}", frame.rowCount());

      List<InputDataColumn> columnList = frame.getInputColumns();

      for (int i=0; i < columnList.size(); ++i){
         logger.info("column: {}", columnList.get(i).summary());
      }

   }

   @Test
   public void test_libsvm() throws IOException {
      DataFrame frame = DataQuery.libsvm().from(FileUtils.getResource("heart_scale.txt")).build();

      for(int i=0; i < 10; ++i){
         logger.info("row[{}]: {}", i, frame.row(i));
      }

      logger.info("row count: {}", frame.rowCount());
   }


   @Test
   public void test_testdata() throws IOException {
      InputStream is = FileUtils.getResource("testdata.csv");
      DataFrame dataFrame = DataQuery.csv(",")
              .from(is)
              .selectColumn(0).asCategory().asInput("a")
              .selectColumn(1).asCategory().asInput("b")
              .selectColumn(2).asNumeric().asInput("c")
              .selectColumn(3).asNumeric().asInput("d")
              .selectColumn(4).asCategory().asInput("e")
              .selectColumn(5).asCategory().asInput("f")
              .selectColumn(6).asNumeric().asInput("g")
              .selectColumn(7).asNumeric().asInput("h")
              .selectColumn(8).asCategory().asInput("i")
              .selectColumn(9).asCategory().asInput("j")
              .selectColumn(10).asCategory().asOutput("OUT")
              .build();

      TupleTwo<DataFrame, DataFrame> parts = dataFrame.shuffle().split(0.9);

      DataFrame trainingData = parts._1();
      DataFrame crossValidationData = parts._2();

      System.out.println(crossValidationData.head(10));

      for(int i=0; i < trainingData.rowCount(); ++i){
         double[] x = trainingData.row(i).toArray();
         assertThat(x).isNotNull();
      }
   }


}
