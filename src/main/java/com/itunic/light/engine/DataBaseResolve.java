package com.itunic.light.engine;


import com.itunic.light.util.ClassSearchTool;
import com.itunic.light.dto.ColumnDTO;
import com.itunic.light.dto.TableDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseResolve {
    private static final Map<String, TableDTO> RESOLVE_CACHE = new ConcurrentHashMap<>();

    /**
     * 解析database
     */
    public static void dataBaseResolve() {
        List<Class<?>> classes = ClassSearchTool.getClasses("");
        classes.parallelStream().forEach(f -> {
            Optional<String> tableName = TableEngine.getTableName(f);
            tableName.ifPresent(table -> {
                Set<ColumnDTO> fields = ColumnEngine.getFields(f);
                if (fields.size() > 0) {
                    RESOLVE_CACHE.put(f.getName(), new TableDTO(f.getSimpleName(), table, fields));
                }else {
                    throw new RuntimeException(f.getName() + "没有对应的 Field注解");
                }
            });
        });
    }

    public static void main(String[] args) {
       /* dataBaseResolve();
        System.out.println(RESOLVE_CACHE.size());
        //TableBaseDTO userVo = RESOLVE_CACHE.get(UserVo.class.getName());
        System.out.println(userVo.getDbName());
        System.out.println(userVo.getName());
        Set<FieldBaseDTO> fields = userVo.getFields();
        fields.forEach(fieldBaseDTO -> {
            System.out.println(fieldBaseDTO.getDbName());
            System.out.println(fieldBaseDTO.getName());
        });*/
    }

    public static TableDTO getResolveCache(String key) {
        return RESOLVE_CACHE.get(key);
    }
}
