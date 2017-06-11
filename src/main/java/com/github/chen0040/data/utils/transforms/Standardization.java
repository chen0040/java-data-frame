package com.github.chen0040.data.utils.transforms;

import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataRow;

import java.util.List;


/**
 * Created by xschen on 21/8/15.
 */
public class Standardization implements Cloneable {
    private double[] mu;
    private double[] std;

    public void copy(Standardization rhs){
        mu = rhs.mu.clone();
        std = rhs.std.clone();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Standardization clone = (Standardization)super.clone();
        clone.copy(this);

        return clone;
    }

    public Standardization(){

    }

    public Standardization(DataFrame batch) {
        query(batch);
    }

    public Standardization(List<double[]> batch){
        query(batch);
    }

    public void fit(List<double[]> batch) {
        query(batch);
    }

    public void fit(DataFrame batch) {
        query(batch);
    }


    public double[] revert(double[] x){
        double[] y = new double[x.length];
        for(int i = 0; i < x.length; ++i){
            y[i] = x[i] * std[i] + mu[i];
        }
        return y;
    }

    public double[] standardize(double[] x){
        double[] y = new double[x.length];
        for(int i = 0; i < x.length; ++i){
            y[i] = (x[i] - mu[i]) / std[i];
        }
        return y;
    }

    protected void query(List<double[]> batch){
        int dimension = batch.get(0).length;
        int m = batch.size();

        //normalization
        mu = new double[dimension];
        std = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            mu[i] = 0;
            std[i] = 0;
        }

        for (int i = 0; i < m; ++i) {
            double[] x = batch.get(i);
            for (int j = 0; j < dimension; ++j) {
                mu[j] += x[j];
            }
        }

        for (int i = 0; i < dimension; ++i) {
            mu[i] /= m;
        }

        for (int i = 0; i < m; ++i) {
            double[] x = batch.get(i);
            for (int j = 0; j < dimension; ++j) {
                std[j] += Math.pow(x[j] - mu[j], 2);
            }
        }

        for (int i = 0; i < dimension; ++i) {
            std[i] /= (m - 1);
        }

        for (int i = 0; i < dimension; ++i) {
            std[i] = Math.sqrt(std[i]);
        }
    }

    protected void query(DataFrame batch) {

        int dimension = batch.row(0).toArray().length;
        int m = batch.rowCount();

        //normalization
        mu = new double[dimension];
        std = new double[dimension];
        for (int i = 0; i < dimension; ++i) {
            mu[i] = 0;
            std[i] = 0;
        }

        for (int i = 0; i < m; ++i) {
            DataRow tuple = batch.row(i);
            double[] x = tuple.toArray();
            for (int j = 0; j < dimension; ++j) {
                mu[j] += x[j];
            }
        }

        for (int i = 0; i < dimension; ++i) {
            mu[i] /= m;
        }

        for (int i = 0; i < m; ++i) {
            DataRow tuple = batch.row(i);
            double[] x = tuple.toArray();
            for (int j = 0; j < dimension; ++j) {
                std[j] += Math.pow(x[j] - mu[j], 2);
            }
        }

        for (int i = 0; i < dimension; ++i) {
            std[i] /= (m - 1);
        }

        for (int i = 0; i < dimension; ++i) {
            std[i] = Math.sqrt(std[i]);
        }

    }
}
