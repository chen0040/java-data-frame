package com.github.chen0040.data.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.testng.Assert.*;


/**
 * Created by xschen on 14/6/2017.
 */
public class CountRepositoryUnitTest {

   private static final Logger logger = LoggerFactory.getLogger(CountRepositoryUnitTest.class);

   @Test
   public void test_setValue(){
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


      logger.info("sub-events: {}",repository.getSubEventNames("eventA"));
      assertThat(repository.getSubEventNames("eventA")).isEqualTo(Arrays.asList("eventB"));
      logger.info("sub-events: {}",repository.getSubEventNames("eventA", "eventB"));
      assertThat(repository.getSubEventNames("eventA", "eventB")).isEqualTo(Arrays.asList("eventC"));

      CountRepository clone = repository.makeCopy();
      logger.info("sub-events: {}",clone.getSubEventNames("eventA"));
      assertThat(repository.getSubEventNames("eventA")).isEqualTo(Arrays.asList("eventB"));
      logger.info("sub-events: {}",clone.getSubEventNames("eventA", "eventB"));
      assertThat(repository.getSubEventNames("eventA", "eventB")).isEqualTo(Arrays.asList("eventC"));
   }

   @Test
   public void test_addSupportCount(){
      CountRepository repository = new CountRepository();
      repository.addSupportCount(1, "eventA", "eventB");
      repository.addSupportCount("eventA");
      double prob = repository.getConditionalProbability("eventA", "eventB");  // probability of B given A;
      assertThat(prob).isEqualTo(1.0);
      repository.addSupportCount(1, "eventA");
      prob = repository.getConditionalProbability("eventA", "eventB");  // probability of A given B;
      assertThat(prob).isEqualTo(0.5);

      repository.addSupportCount(1, "eventA", "eventB", "eventC");
      assertThat(repository.getValue("eventA", "eventB")).isEqualTo(1.0);
      assertThat(repository.getValue("eventA", "eventB", "eventC")).isEqualTo(1.0);


      logger.info("sub-events: {}",repository.getSubEventNames("eventA"));
      assertThat(repository.getSubEventNames("eventA")).isEqualTo(Arrays.asList("eventB"));
      logger.info("sub-events: {}",repository.getSubEventNames("eventA", "eventB"));
      assertThat(repository.getSubEventNames("eventA", "eventB")).isEqualTo(Arrays.asList("eventC"));

      CountRepository clone = repository.makeCopy();
      logger.info("sub-events: {}",clone.getSubEventNames("eventA"));
      assertThat(repository.getSubEventNames("eventA")).isEqualTo(Arrays.asList("eventB"));
      logger.info("sub-events: {}",clone.getSubEventNames("eventA", "eventB"));
      assertThat(repository.getSubEventNames("eventA", "eventB")).isEqualTo(Arrays.asList("eventC"));
   }
}
