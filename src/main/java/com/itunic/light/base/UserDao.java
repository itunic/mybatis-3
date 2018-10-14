package com.itunic.light.base;


import com.itunic.pojo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserDao extends BaseMapper<UserVo>{

    @Select("select id,user_name userName from t_jeeweb_user where id = #{id} and userName = #{a}")
    UserVo getUser(@Param("a") String a,@Param("id") String id);

    long count(String sql, Object... params);
}
