package com.github.chen0040.data.utils.discretizers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


/**
 * Created by xschen on 18/8/15.
 */
public class KMeansFilter {

    private static Random random = new Random();
    private int clusterCount;
    private double[] clusters;
    private int maxIters = 500;
    private String columnName;

    public KMeansFilter makeCopy(){
        KMeansFilter clone = new KMeansFilter();
        clone.copy(this);
        return clone;
    }

    public void copy(KMeansFilter rhs)
    {
        clusterCount = rhs.clusterCount;
        columnName = rhs.columnName;
        clusters = rhs.clusters == null ? null : rhs.clusters.clone();
        maxIters = rhs.maxIters;
    }

    public KMeansFilter(String columnName, int k) {
        this.columnName = columnName;
        clusterCount = k;
    }

    public KMeansFilter(){

        clusterCount = 10;
    }

    public int getClusterCount() {
        return clusterCount;
    }

    public void setClusterCount(int clusterCount) {
        this.clusterCount = clusterCount;
    }

    public int getMaxIters() {
        return maxIters;
    }

    public void setMaxIters(int maxIters) {
        this.maxIters = maxIters;
    }

    public void build(List<Double> values) {
        int m = values.size();
        HashSet<Integer> initialCenters = new HashSet<Integer>();
        if(clusterCount * 3 > m) {
            clusterCount = Math.min(clusterCount, m);
            for(int i=0; i < clusterCount; ++i){
                initialCenters.add(i);
            }
        }
        else{
            while (initialCenters.size() < clusterCount) {
                int r = random.nextInt(m);
                if (!initialCenters.contains(r)) {
                    initialCenters.add(r);
                }
            }
        }

        clusters = new double[clusterCount];

        int centerIndex = 0;
        for(Integer index : initialCenters){
            clusters[centerIndex] = values.get(index);
            centerIndex++;
        }

        List<List<Integer>> cluster_groups = new ArrayList<List<Integer>>();

        for(int i=0; i< clusterCount; ++i){
            cluster_groups.add(new ArrayList<Integer>());
        }

        for(int iter= 0; iter < maxIters; ++iter) {
            for(int i=0; i < clusterCount; ++i){
                cluster_groups.get(i).clear();
            }

            for (int i = 0; i < m; ++i) {
                int clusterIndex = closestClusterIndex(values.get(i));

                cluster_groups.get(clusterIndex).add(i);
            }

            for(int i=0; i < clusterCount; ++i){
                clusters[i] = calcCenter(values, cluster_groups.get(i), clusters[i]);
            }

        }
    }

    private double calcCenter(List<Double> values, List<Integer> cluster, double center){
        double newCenter = 0;
        int m = cluster.size();
        for(int i=0; i < m; ++i){
            newCenter += values.get(cluster.get(i));
        }
        if(m==0) return center;
        newCenter /= m;
        return newCenter;
    }

    public int discretize(double value) {
        return closestClusterIndex(value);
    }

    private int closestClusterIndex(double value){
        double min_distance = Double.MAX_VALUE;
        int closest_cluster_index = -1;
        double distance;
        for(int i=0; i < clusters.length; ++i){
            distance = (clusters[i] - value) * (clusters[i] - value);
            if(distance < min_distance){
                min_distance = distance;
                closest_cluster_index = i;
            }
        }
        return closest_cluster_index;
    }
}
