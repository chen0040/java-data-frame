package com.github.chen0040.data.frame;


import com.github.chen0040.data.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 18/8/15.
 */
public class Coefficients {
    private final List<Double> values = new ArrayList<>();
    private final List<InputDataColumn> descriptors = new ArrayList<>();

    public void copy(Coefficients rhs){
        values.clear();
        values.addAll(CollectionUtils.clone(rhs.values, x -> x));
        descriptors.clear();
        for(int i=0; i < rhs.descriptors.size(); ++i){
            descriptors.add(rhs.descriptors.get(i).makeCopy());
        }
    }

    public Coefficients makeCopy(){
        Coefficients clone = new Coefficients();
        clone.copy(this);
        return clone;
    }

    public Coefficients() {
    }

    public List<Double> getValues() {
        return CollectionUtils.clone(values, x -> x);
    }

    public void setValues(List<Double> values) {
        this.values.clear();
        this.values.addAll(CollectionUtils.clone(values, x -> x));
    }

    public List<InputDataColumn> getDescriptors() {
        return CollectionUtils.clone(descriptors, InputDataColumn::makeCopy);
    }

    public void setDescriptors(List<InputDataColumn> descriptors){
        this.descriptors.clear();
        this.descriptors.addAll(CollectionUtils.clone(descriptors, InputDataColumn::makeCopy));
    }

    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        if(values.isEmpty()){
            return "(null)";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(String.format("\"(Intercepter)\":%f, ", values.get(0)));
        for (int i = 1; i < values.size(); ++i){
            sb.append(String.format(", \"%s\":%f", descriptors.get(i-1).toString(), values.get(i)));
        }
        sb.append("}");
        return sb.toString();
    }
}
