# java-data-frame

Package provides the core data frame implementation for numerical computation

[![Build Status](https://travis-ci.org/chen0040/java-glm.svg?branch=master)](https://travis-ci.org/chen0040/java-glm) [![Coverage Status](https://coveralls.io/repos/github/chen0040/java-glm/badge.svg?branch=master)](https://coveralls.io/github/chen0040/java-glm?branch=master) 

# Features

* Load data frame from CSV file
* Load libsvm format files
* Create data frame using data sampling
 
In the future more option will be added for the supported format

# Install

Add the following dependency to your POM file:

```xml
<dependency>
  <groupId>com.github.chen0040</groupId>
  <artifactId>java-data-frame</artifactId>
  <version>1.0.3</version>
</dependency>
```

# Usage

## Crate a data frame manually

The sample code below shows how to create a data frame manually:

```java
DataFrame dataFrame = new BasicDataFrame();

DataRow row = dataFrame.newRow();
row.setCell("inputColumn1", 0.1);
row.setCategoricalCell("inputColumn2", "Hello");
row.setTargetCell("numericOutput", 0.2);
row.setCategoricalTargetCell("categoricalOutput", "YES");

dataFrame.addRow(row);

// add more rows here

// call lock to perform aggregation and prevent further addition of new rows
dataFrame.lock();
```

Note that you need to call "dataFrame.lock()" after you finish adding rows so that aggregation can be performed. After this api call, the data frame
will prevent further addition of new rows. To start adding new rows again, call "dataFrame.unlock()" before adding more rows.

## Create a data frame using Sampler

The sample code belows shows how to create a data frame using Sampler class:

```java
DataQuery.DataFrameQueryBuilder schema = DataQuery.blank()
      .newInput("x1")
      .newInput("x2")
      .newOutput("y")
      .end();

// y = 4 + 0.5 * x1 + 0.2 * x2
Sampler.DataSampleBuilder sampler = new Sampler()
      .forColumn("x1").generate((name, index) -> randn() * 0.3 + index)
      .forColumn("x2").generate((name, index) -> randn() * 0.3 + index * index)
      .forColumn("y").generate((name, index) -> 4 + 0.5 * index + 0.2 * index * index + randn() * 0.3)
      .end();

DataFrame dataFrame = schema.build();

dataFrame = sampler.sample(dataFrame, 200);
```

The sample code above creates a data frame consisting of 200 rows and 3 columns ("x1", "x2", "y")

```java

```

# Print contents in a data frame

The sample code below shows how to print the content in the data frame:

```java
System.out.pritnln(dataFrame.head(2));

dataFrame.stream().forEach(r -> System.out.println("row: " + r));
for(DataRow r : irisData) {
 System.out.println("row: "+ r);
}
```

# Sample and split

The shuffle the content of a data frame:

```java
dataFrame.shuffle()
```

To split a data frame into two data frames:

```
TupleTwo<DataFrame, DataFrame> miniFrames = dataFrame.split(0.9);
DataFrame frame1 = miniFrames._1();
DataFrame frame2 = miniFrames._2();
```

The frame1 contains 90% of the rows in the original data frame, while frame2 contains the other 10% of the rows in the original data frame.

## Load from CSV file

Suppose you have a csv file named contraception.csv that has the following file format:

```
"","woman","district","use","livch","age","urban"
"1","1","1","N","3+",18.44,"Y"
"2","2","1","N","0",-5.5599,"Y"
"3","3","1","N","2",1.44,"Y"
"4","4","1","N","3+",8.44,"Y"
"5","5","1","N","0",-13.559,"Y"
"6","6","1","N","0",-11.56,"Y"
```

An example of java code to create a data frame from the above CSV file:

```java
import com.github.chen0040.data.frame.DataFrame;
import com.github.chen0040.data.frame.DataQuery;
import com.github.chen0040.data.utils.StringUtils;

int column_use = 3;
int column_livch = 4;
int column_age = 5;
int column_urban = 6;
boolean skipFirstLine = true;
String columnSplitter = ",";
InputStream inputStream = new FileInputStream("contraception.csv");
DataFrame frame = DataQuery.csv(columnSplitter, skipFirstLine)
        .from(inputStream)
        .selectColumn(column_livch).asCategory().asInput("livch")
        .selectColumn(column_age).asNumeric().asInput("age")
        .selectColumn(column_age).transform(cell -> Math.pow(StringUtils.parseDouble(cell), 2)).asInput("age^2")
        .selectColumn(column_urban).asCategory().asInput("urban")
        .selectColumn(column_use).transform(cell -> cell.equals("Y") ? 1.0 : 0.0).asOutput("use")
        .build();
```

The code above create a data frame which has the following columns

* livch1 (input): value = 1 if the "livch" column of the CSV contains value 1 ; 0 otherwise
* livch2 (input): value = 1 if the "livch" column of the CSV contains value 2 ; 0 otherwise
* livch3 (input): value = 1 if the "livch" column of the CSV contains value 3+ ; 0 otherwise
* age (input): value = numeric value in the "age" column of the CSV
* age^2 (input): value = square of numeric value in the "age" column of the CSV
* urban (input): value = 1 if the "urban" column of the CSV has value "Y" ; 0 otherwise
* use (output): value = 1 if the "use" column of the CSV has value "Y" ; 0 otherwise

In the above case, the output of the data frame is numerical, the code sample below shows how a data frame can be loaded for which the output is categorical:

```java
InputStream irisStream = new FileInputStream("iris.data");
DataFrame irisData = DataQuery.csv(",", false)
      .from(irisStream)
      .selectColumn(0).asNumeric().asInput("Sepal Length")
      .selectColumn(1).asNumeric().asInput("Sepal Width")
      .selectColumn(2).asNumeric().asInput("Petal Length")
      .selectColumn(3).asNumeric().asInput("Petal Width")
      .selectColumn(4).asCategory().asOutput("Iris Type")
      .build();
```

# Load libsvm formatted file

The sample code belows shows how a data frame can be created from "heart-scale.txt" which is in libsvm format:

```java
DataFrame frame = DataQuery.libsvm().from(new FileInputStream("heart_scale.txt")).build();
```







