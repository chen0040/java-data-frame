package com.github.chen0040.data.frame;


import com.github.chen0040.data.utils.TupleTwo;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;


/**
 * Created by xschen on 28/4/2017.
 */
public interface DataFrame extends Iterable<DataRow> {
   int rowCount();

   DataRow row(int i);

   List<InputDataColumn> getInputColumns();

   List<OutputDataColumn> getOutputColumns();

   List<String> rowArrayDescriptors();

   void unlock();

   boolean isLocked();

   void lock();

   DataRow newRow();

   void addRow(DataRow row);

   String head(int limit);

   DataFrame shuffle();

   TupleTwo<DataFrame, DataFrame> split(double ratio);

   Stream<DataRow> stream();

   DataFrame makeCopy();

   DataFrame filter(Predicate<DataRow> predicate);

   Iterable<? extends DataRow> rows();
}
