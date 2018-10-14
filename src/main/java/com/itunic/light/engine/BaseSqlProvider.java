package com.itunic.light.engine;

import com.itunic.light.dto.TableDTO;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class BaseSqlProvider {

    static {
        DataBaseResolve.dataBaseResolve();
    }

    public String select(String sql){
        return sql;
    }

    public String selectById(ProviderContext context) {
        System.out.println(getEntityClass(context));
        SQL sql = new SQL();
        TableDTO resolveCache = DataBaseResolve.getResolveCache(getEntityClass(context));

        resolveCache.getColumnDTOSet().forEach(f -> {
            sql.SELECT(f.getName() + " AS " + f.getFieldName());
            if (f.isPrimaryKey()) {
                sql.WHERE(f.getName().concat("= #{id}"));
            }
        });
        sql.FROM(resolveCache.getName());
        return sql.toString();
    }

    public <T> String insert(T entity) {
        String name = entity.getClass().getName();
        TableDTO resolveCache = DataBaseResolve.getResolveCache(name);
        SQL sql = new SQL();
        sql.INSERT_INTO(resolveCache.getName());
        resolveCache.getColumnDTOSet().forEach(f -> {
            sql.VALUES(f.getName(), "#{".concat(f.getFieldName()).concat("}"));
        });
        return sql.toString();
    }

    public <T> String update(T entity) {
        String name = entity.getClass().getName();
        TableDTO resolveCache = DataBaseResolve.getResolveCache(name);
        SQL sql = new SQL();
        sql.UPDATE(resolveCache.getName());
        resolveCache.getColumnDTOSet().forEach(f -> {
            if (f.isPrimaryKey()) {
                sql.WHERE(f.getName()
                        .concat(" = #{")
                        .concat(f.getFieldName())
                        .concat("}"));

            } else {
                sql.SET(f.getName()
                        .concat(" = #{")
                        .concat(f.getFieldName())
                        .concat("}"));

            }
        });
        return sql.toString();
    }

    public <T> String delete(T entity) {
        String name = entity.getClass().getName();
        TableDTO resolveCache = DataBaseResolve.getResolveCache(name);
        SQL sql = new SQL();
        sql.DELETE_FROM(resolveCache.getName());
        resolveCache.getColumnDTOSet().stream()
                .filter(f -> nonNullByFieldForObject(f.getFieldName(), entity))
                .forEach(f -> sql.WHERE(f.getName()
                        .concat(" = #{")
                        .concat(f.getFieldName())
                        .concat("}")));
        return sql.toString();
    }

    private String getEntityClass(ProviderContext context) {
        Class<?> mapperType = context.getMapperType();

        for (Type parent : mapperType.getGenericInterfaces()) {
            Type[] actualTypeArguments = ((ParameterizedType) parent).getActualTypeArguments();
            String typeName = actualTypeArguments[0].getTypeName();
            return typeName;
        }
        return null;
    }

    protected <T> boolean nonNullByFieldForObject(String fieldName, T vo) {
        Class<?> clazz = vo.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object o = field.get(vo);
            return Objects.nonNull(o);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
