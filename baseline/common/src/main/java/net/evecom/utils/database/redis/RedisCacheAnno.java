package net.evecom.utils.database.redis;

import net.evecom.tools.constant.consts.CacheGroupConst;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisCacheAnno {

	@AliasFor("cacheNames")
	String[] value() default {};

	@AliasFor("value")
	String[] cacheNames() default {};

	/**
	 * redis中存放时的key值
	 */
	String key()

	default "";

	/**
	 * redis中库序号
	 */
	int index() default CacheGroupConst.DATACACHE_REIDS;

	/**
	 * 类型,query 查询 add新增 edit修改 del删除
	 */
	String type() default "query";

	String keyGenerator()

	default "";

	String cacheManager()

	default "";

	String cacheResolver()

	default "";

	String condition()

	default "";

	String unless()

	default "";

	boolean sync() default false;

}
