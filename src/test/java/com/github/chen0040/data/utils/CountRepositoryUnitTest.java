package com.github.chen0040.data.utils;


import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 14/6/2017.
 */
public class CountRepositoryUnitTest {

   @Test
   public void test_count(){
      CountRepository repository = new CountRepository();
      repository.setValue(1, "eventA", "eventB");
      repository.setValue(1, "eventA");
      repository.setValue(4);
      double prob = repository.getConditionalProbability("eventA", "eventB");  // probability of B given A;
      assertThat(prob).isEqualTo(1.0);
      repository.setValue(2, "eventA");
      prob = repository.getConditionalProbability("eventA", "eventB");  // probability of A given B;
      assertThat(prob).isEqualTo(0.5);

      assertThat(repository.getProbability("eventA")).isEqualTo(0.5);

      repository.setValue(1, "eventA", "eventB", "eventC");
      assertThat(repository.getValue("eventA", "eventB")).isEqualTo(1.0);
      assertThat(repository.getValue("eventA", "eventB", "eventC")).isEqualTo(1.0);
      assertThat(repository.getProbability("eventA")).isEqualTo(0.5);

      
   }
}
