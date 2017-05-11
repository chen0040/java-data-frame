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
  <version>1.0.1</version>
</dependency>
```

# Usage

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
        .selectColumn(column_livch).transform(cell -> cell.equals("1") ? 1.0 : 0.0).asInput("livch1")
        .selectColumn(column_livch).transform(cell -> cell.equals("2") ? 1.0 : 0.0).asInput("livch2")
        .selectColumn(column_livch).transform(cell -> cell.equals("3+") ? 1.0 : 0.0).asInput("livch3")
        .selectColumn(column_age).asInput("age")
        .selectColumn(column_age).transform(cell -> Math.pow(StringUtils.parseDouble(cell), 2)).asInput("age^2")
        .selectColumn(column_urban).transform(cell -> cell.equals("Y") ? 1.0 : 0.0).asInput("urban")
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
      .selectColumn(0).asInput("Sepal Length")
      .selectColumn(1).asInput("Sepal Width")
      .selectColumn(2).asInput("Petal Length")
      .selectColumn(3).asInput("Petal Width")
      .selectColumn(4).transform(label -> label).asOutput("Iris Type")
      .build();
```

# Load libsvm formatted file

The sample code belows shows how a data frame can be created from "heart-scale.txt" which is in libsvm format:

```java
DataFrame frame = DataQuery.libsvm().from(new FileInputStream("heart_scale.txt")).build();
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




