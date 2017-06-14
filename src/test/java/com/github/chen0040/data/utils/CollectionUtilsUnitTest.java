package com.github.chen0040.data.utils;


import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 14/6/2017.
 */
public class CollectionUtilsUnitTest {

   @Test
   public void test_toList(){
      List<String> list = CollectionUtils.toList(new String[] {"Hello", "World"}, x -> x);
      assertThat(list).isEqualTo(Arrays.asList("Hello", "World"));
   }
}
