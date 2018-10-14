package com.itunic.light.base;

import com.itunic.light.engine.BaseSqlProvider;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T> {

    /**
     * 根据id查询
     *
     * @param id id
     * @return Entity
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "selectById")
    T get(@Param("id") Serializable id);

    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    int insert(T entity);

    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    int update(T entity);

    @DeleteProvider(type = BaseSqlProvider.class, method = "delete")
    int delete(T entity);

    @SelectProvider(type = BaseSqlProvider.class, method = "select")
    long count(String sql);

    @SelectProvider(type = BaseSqlProvider.class, method = "select")
    List<T> queryForList(String sql);
}

