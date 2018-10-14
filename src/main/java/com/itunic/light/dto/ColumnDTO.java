package com.itunic.light.dto;

import java.io.Serializable;

public class ColumnDTO implements Serializable {
    private String name;
    private boolean nullable;
    private int length;
    private String columnDefinition;
    private boolean primaryKey;
    private String fieldName;

    public ColumnDTO(String fieldName,String name, boolean nullable, int length, String columnDefinition, boolean primaryKey) {
        this.name = name;
        this.nullable = nullable;
        this.length = length;
        this.columnDefinition = columnDefinition;
        this.primaryKey = primaryKey;
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
