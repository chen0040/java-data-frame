package com.github.chen0040.data.utils;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by xschen on 12/8/15.
 */
public class FileUtils {
    public static InputStream getResource(String fileName) throws IOException {

        ClassLoader classLoader = FileUtils.class.getClassLoader();
        return classLoader.getResource(fileName).openStream();

    }
}
