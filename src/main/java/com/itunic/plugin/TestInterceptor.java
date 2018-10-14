package com.itunic.plugin;

import com.itunic.light.engine.DataBaseResolve;
import com.itunic.light.dto.ColumnDTO;
import com.itunic.light.dto.TableDTO;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})}
)
public class TestInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
     Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        MapperMethod.ParamMap  value =(MapperMethod.ParamMap) args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        BoundSql boundSql = ms.getBoundSql(value);
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        String sql = boundSql.getSql();


          Class clazz = (Class)value.get("arg0");

        if (ms.getSqlCommandType().name().equals("SELECT")){
            StringBuilder sb = new StringBuilder("SELECT ");
            //执行select
            if (sql.equals("forVo")){
                String idField = "";

                TableDTO cache = DataBaseResolve.getResolveCache(clazz.getName());

                for (ColumnDTO cln:cache.getColumnDTOSet()) {
                    sb.append(cln.getName()).append(",");
                    if (cln.isPrimaryKey()){
                        idField = cln.getName();
                    }
                }
                sb.deleteCharAt(sb.length() - 1).
                        append(" FROM ").append(cache.getName())
                        .append(" WHERE ").append(idField).append(" = #{id}");
            }else if (sql.equals("forList")){

            }
            sql = sb.toString();
            RawSqlSource rawSqlSource = new RawSqlSource(ms.getConfiguration(), sql, value.getClass());
           // BoundSql bound = new BoundSql(ms.getConfiguration(),sql,boundSql.getParameterMappings(),value);
            BoundSql bound = rawSqlSource.getBoundSql(value);
            List<ResultMap> resultMaps = ms.getResultMaps();
            ResultMap map = resultMaps.get(0);
            ResultMap.Builder builder = new ResultMap.Builder(ms.getConfiguration(), map.getId(), clazz, map.getResultMappings(), map.getAutoMapping());
            ResultMap build = builder.build();
            //resultMaps.clear();
            resultMaps.add(build);
            CacheKey cacheKey = executor.createCacheKey(ms, value, rowBounds, bound);
            List<Object> query = executor.query(ms, value, rowBounds, resultHandler, cacheKey, bound);
            return query;
        }
        return  invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("11111111222");
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        DataBaseResolve.dataBaseResolve();
    }
}
