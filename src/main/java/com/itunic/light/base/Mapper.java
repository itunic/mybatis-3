package com.itunic.light.base;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

public interface Mapper  {
    @Select("forVo")
    <T> T get(Class<T> clazz, @Param("id") Serializable id);
}
