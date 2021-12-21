package com.spark.study.product;

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.List;
import java.util.Objects;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.sum;

public class ProductReader {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName(ProductReader.class.getName())
                .config("spark.driver.bindAddress", "localhost")
                .master("local")
                .getOrCreate();

        Dataset<Row> rawData = sparkSession
                .read()
                .option("multiline", true)
                .schema(getSchema())
                .json(getFilePath());
        rawData.printSchema();
        rawData.show();

        Encoder<ProductAnalyze> encoder = Encoders.bean(ProductAnalyze.class);
        Dataset<ProductAnalyze> dataset = rawData
                .select(
                        "id",
                        "view",
                        "cart",
                        "purchase",
                        "revenue"
                )
                .where(col("updatedAt").$greater$eq("2021-01-01").and(col("updatedAt").$less$eq("2021-12-31")))
                .groupBy(col("id"))
                .agg(
                        sum("view").as("view"),
                        sum("cart").as("cart"),
                        sum("purchase").as("purchase"),
                        sum("revenue").as("revenue")
                )
                .orderBy(col("revenue").desc())
                .as(encoder);
        dataset.show();

        List<ProductAnalyze> products = dataset.collectAsList();
        products.forEach(System.out::println);
    }

    private static String getFilePath() {
        ClassLoader classLoader = ProductReader.class.getClassLoader();
        return Objects.requireNonNull(classLoader.getResource("products.json")).getPath();
    }

    private static StructType getSchema() {
        return new StructType(new StructField[]{
                new StructField("id", DataTypes.LongType, false, Metadata.empty()),
                new StructField("view", DataTypes.LongType, false, Metadata.empty()),
                new StructField("cart", DataTypes.LongType, false, Metadata.empty()),
                new StructField("purchase", DataTypes.LongType, false, Metadata.empty()),
                new StructField("revenue", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("updatedAt", DataTypes.DateType, false, Metadata.empty())
        });
    }
}
