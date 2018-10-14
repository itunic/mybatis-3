package com.itunic.light.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TableDTO implements Serializable {
    private String name;
    private String className;
    private Set<ColumnDTO> columnDTOSet = new HashSet<>();


    public TableDTO(String className,String name, Set<ColumnDTO> columnDTOSet) {
        this.name = name;
        this.columnDTOSet = columnDTOSet;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ColumnDTO> getColumnDTOSet() {
        return columnDTOSet;
    }

    public void setColumnDTOSet(Set<ColumnDTO> columnDTOSet) {
        this.columnDTOSet = columnDTOSet;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
