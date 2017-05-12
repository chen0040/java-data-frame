package com.github.chen0040.data.misc;


import com.github.chen0040.data.frame.DataQuery;
import com.github.chen0040.data.utils.FileUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by xschen on 12/5/2017.
 */
public class DataQueryLoadUnitTest {
   @Test
   public void test_load_calcium_dat() throws IOException {
      InputStream inputStream = FileUtils.getResource("calcium.dat");
      
   }
}
