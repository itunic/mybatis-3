package com.itunic.light.engine;


import com.itunic.light.annotation.Table;

import java.util.Optional;
/**
 * table注解解析器
 */
public class TableEngine {

    public static void main(String[] args) {

    }

    public static Optional<String> getTableName(Class<?> clazz){
        boolean flag = clazz.isAnnotationPresent(Table.class);
        if (!flag){
            return Optional.empty();
        }
        Table annotation = clazz.getAnnotation(Table.class);

        String tableName;
        if ("".equals((tableName = annotation.value()))){
            return Optional.of(clazz.getSimpleName());
        }
        return Optional.of(tableName);
    }
}
