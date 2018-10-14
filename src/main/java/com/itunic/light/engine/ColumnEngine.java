package com.itunic.light.engine;


import com.itunic.light.annotation.Column;
import com.itunic.light.annotation.Id;
import com.itunic.light.dto.ColumnDTO;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ColumnEngine {

    public static Set<ColumnDTO> getFields(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Set<ColumnDTO> set = new HashSet<>(declaredFields.length);
        for (Field field : declaredFields) {
            Column annotation = field.getAnnotation(Column.class);
            if (null == annotation) {
                continue;
            }
            String column;
            String fieldName = field.getName();
            if ("".equals((column = annotation.name()))) {
                column = fieldName;
            }
            boolean flag = field.isAnnotationPresent(Id.class);

            set.add(new ColumnDTO(fieldName,column, annotation.nullable(), annotation.length(), annotation.columnDefinition(), flag));

            //set.add(new ColumnDTO(fieldName,column,annotation.id()));
        }
        return set;
    }

}
