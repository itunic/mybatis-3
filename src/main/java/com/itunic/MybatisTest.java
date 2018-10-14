package com.itunic;


import com.github.pagehelper.PageHelper;
import com.itunic.pojo.UserVo;
import com.itunic.light.base.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        PageHelper.startPage(1,2);
        UserVo vo = new UserVo();
        vo.setId("2222");
        vo.setUserName("bbbb");
        vo.setAge("12");
        int delete = mapper.delete(vo);
        // long count = mapper.count("select count(1) from t_jeeweb_user where id = #{id}",1);
        System.out.println(delete);
        // UserVo user = mapper.get(1);
        //UserVo user = mapper.getUser("1", "1");
        sqlSession.commit();
        sqlSession.close();
       // System.out.println(user.toString());




    }
}
