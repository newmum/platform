package org.beetl.sql.core.orm;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 对象对应的数据库表明，默认通过类名，也可以通过此指定
 * @author xiandafu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OrmCondition {
	
	public Class target();
	public String attr();
	public String targetAttr();
	public String sqlId() default "";
	public String alias() default "";
	public OrmQuery.Type type() default OrmQuery.Type.MANY;
	public boolean lazy() default false;

}


