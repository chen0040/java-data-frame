package com.github.chen0040.data.utils.discretizers;


import com.github.chen0040.data.frame.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xschen on 18/8/15.
 */
@Getter
@Setter
public class KMeansDiscretizer implements AttributeValueDiscretizer, Serializable {

    private static final long serialVersionUID = 2193706516691610469L;
    @Setter(AccessLevel.NONE)
    private final Map<String, KMeansFilter> filters = new HashMap<>();

    private int maxLevelCount = 10;

    private int maxIters = 500;

    public void copy(KMeansDiscretizer that){
        maxLevelCount = that.maxLevelCount;

        filters.clear();
        for(String index : that.filters.keySet()){
            filters.put(index, that.filters.get(index).makeCopy());
        }
    }

    public KMeansDiscretizer makeCopy(){
        KMeansDiscretizer clone = new KMeansDiscretizer();
        clone.copy(this);
        return clone;
    }

    public KMeansDiscretizer(){

    }

    @Override
    public int discretize(double value, String columnName) {
        if(filters.containsKey(columnName)){
            return filters.get(columnName).discretize(value);
        }else{
            return (int)value;
        }
    }


    @Override public DataRow transform(DataRow tuple) {
        DataRow newRow = new BasicDataRow();

        for(String columnName : tuple.getCategoricalColumnNames()){
            newRow.setCategoricalCell(columnName, tuple.getCategoricalCell(columnName));
        }

        for(String columnName: tuple.getColumnNames()){
            int value = discretize(tuple.getCell(columnName), columnName);
            newRow.setCategoricalCell(columnName, "" + value);
        }

        for(String columnName : tuple.getCategoricalTargetColumnNames()){
            newRow.setCategoricalTargetCell(columnName, tuple.getCategoricalTargetCell(columnName));
        }

        for(String columnName : tuple.getTargetColumnNames()){
            int value = discretize(tuple.getTargetCell(columnName), columnName);
            newRow.setCategoricalTargetCell(columnName, "" + value);
        }

        return newRow;

    }


    @Override public DataFrame fitAndTransform(DataFrame frame) {
        fit(frame);

        DataFrame newFrame = new BasicDataFrame();

        for(int rowIndex = 0; rowIndex < frame.rowCount(); ++rowIndex){
            newFrame.addRow(transform(frame.row(rowIndex)));
        }

        newFrame.lock();

        return newFrame;
    }

    public void fit(DataFrame frame) {

        int m = frame.rowCount();

        filters.clear();
        for(DataColumn c : frame.getAllColumns()){
            if(!c.isCategorical()){
                KMeansFilter f = new KMeansFilter(c.getColumnName(), maxLevelCount);
                f.setMaxIters(maxIters);
                filters.put(c.getColumnName(), f);
            }
        }

        Map<String, List<Double>> values = new HashMap<>();
        for(String columnName : filters.keySet()){
            values.put(columnName, new ArrayList<>());
        }

        for(int i=0; i < m; ++i){
            DataRow tuple = frame.row(i);
            for(DataColumn c : frame.getAllColumns()) {
                if(!c.isCategorical()) {
                    if(c.isOutputColumn()) {
                        values.get(c.getColumnName()).add(tuple.getTargetCell(c.getColumnName()));
                    } else {
                        values.get(c.getColumnName()).add(tuple.getCell(c.getColumnName()));
                    }
                }
            }
        }

        for(Map.Entry<String, KMeansFilter> filter : filters.entrySet()){
            filter.getValue().build(values.get(filter.getKey()));
        }
    }


}
