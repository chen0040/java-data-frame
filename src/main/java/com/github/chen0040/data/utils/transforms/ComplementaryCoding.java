package com.github.chen0040.data.utils.transforms;


import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataRow;

import java.util.List;


/**
 * Created by xschen on 21/8/15.
 */
public class ComplementaryCoding implements Cloneable {
    private double[] minValues;
    private double[] maxValues;

    public void copy(ComplementaryCoding rhs){
        minValues = rhs.minValues.clone();
        maxValues = rhs.maxValues.clone();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ComplementaryCoding clone = (ComplementaryCoding)super.clone();
        clone.copy(this);

        return clone;
    }

    public ComplementaryCoding(){

    }

    public ComplementaryCoding(DataFrame batch) {
        query(batch);
    }

    public ComplementaryCoding(List<double[]> batch){
        query(batch);
    }

    public double[] revert(double[] x){
        int m = x.length / 2;
        double[] y = new double[m];
        for(int i = 0; i < m ; ++i){
            y[i] = x[i] * (maxValues[i] - minValues[i]) + minValues[i];
        }
        return y;
    }

    public double[] normalize(double[] x){
        double[] y = new double[x.length * 2];
        for(int i = 0; i < x.length; ++i){
            y[i] = (x[i] - minValues[i]) / (maxValues[i] - minValues[i]);
        }
        for(int i=x.length; i < x.length * 2; ++i){
            y[i] = 1 - y[i-x.length];
        }
        return y;
    }

    protected void query(List<double[]> batch){
        int dimension = batch.get(0).length;
        int m = batch.size();

        //normalization
        minValues = new double[dimension];
        maxValues = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            minValues[i] = Double.MAX_VALUE;
            maxValues[i] = Double.MIN_VALUE;
        }

        for (int i = 0; i < m; ++i) {
            double[] x = batch.get(i);
            for (int j = 0; j < dimension; ++j) {
                maxValues[j] = Math.max(x[j], maxValues[j]);
                minValues[j] = Math.min(x[j], minValues[j]);
            }
        }
    }

    protected void query(DataFrame batch) {

        int dimension = batch.row(0).toArray().length;
        int m = batch.rowCount();

        //normalization
        minValues = new double[dimension];
        maxValues = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            minValues[i] = Double.MAX_VALUE;
            maxValues[i] = Double.MIN_VALUE;
        }

        for (int i = 0; i < m; ++i) {
            DataRow tuple = batch.row(i);
            double[] x = tuple.toArray();
            for (int j = 0; j < dimension; ++j) {
                maxValues[j] = Math.max(x[j], maxValues[j]);
                minValues[j] = Math.min(x[j], minValues[j]);
            }
        }
    }
}
