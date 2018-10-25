package org.beetl.sql.core.mapper.internal;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.mapper.MapperInvoke;

import java.lang.reflect.Method;

/**
 * 
 *
 * @author xiandafu
 */
public class LambdaQueryAmi implements MapperInvoke {

    @Override
    public Object call(SQLManager sm, Class entityClass, String sqlId, Method m, Object[] args) {
       return sm.lambdaQuery(entityClass);
    }

}
