package com.github.chen0040.data.utils.discretizers;


import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataRow;


/**
 * Created by xschen on 18/8/15.
 */
public interface AttributeValueDiscretizer  {
    int discretize(double value, String index);
    DataRow transform(DataRow tuple);
    DataFrame fitAndTransform(DataFrame frame);
}
