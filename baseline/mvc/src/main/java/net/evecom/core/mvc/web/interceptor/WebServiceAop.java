package net.evecom.core.mvc.web.interceptor;

import net.evecom.core.db.model.entity.DataEntity;
import net.evecom.core.db.model.entity.Resources;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.utils.database.redis.RedisCacheAnno;
import net.evecom.utils.database.redis.RedisClient;
import net.evecom.utils.verify.CheckUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.annotatoin.Table;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class WebServiceAop {

	private final String POINT_CUT = "execution(* net.evecom.*.*.model.service..*.*(..))";

	@Pointcut(POINT_CUT)
	public void executeService() {

	}

	@Before("executeService()")
	public void doBeforeAdvice(JoinPoint joinPoint) {
	}

	@AfterReturning(value = "executeService()", returning = "keys")
	public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys) {
	}

	@AfterReturning(value = "executeService()", returning = "keys", argNames = "keys")
	public void doAfterReturningAdvice2(String keys) {
	}

	@AfterThrowing(value = POINT_CUT, throwing = "exception")
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
	}

	@After("executeService()")
	public void doAfterAdvice(JoinPoint joinPoint) {
	}

	@Resource(name = "redisClient")
	protected RedisClient redisClient;
	@Resource
	protected SQLManager sqlManager;
	@Resource
	protected ResourceService resourceService;

	@Around("executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long begin = System.currentTimeMillis();
		// obj之前可以写目标方法执行前的逻辑
		Object[] args = proceedingJoinPoint.getArgs();
		Signature signature = proceedingJoinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		Object obj = null;
		if (method.isAnnotationPresent(RedisCacheAnno.class)) {
			RedisCacheAnno anno = (RedisCacheAnno) method.getAnnotation(RedisCacheAnno.class);
			int index = anno.index();
			String type = anno.type();
			Object temp = null;
			String groupKey = "";
			String cacheKey = "";
			Resources resources = null;
			for (Object temp_obj : args) {
				boolean isFather = QueryParam.class.isAssignableFrom(temp_obj.getClass());
				if (isFather) {
					QueryParam<?> param = (QueryParam<?>) temp_obj;
					groupKey = param.getGroupKey(sqlManager.query(WebServiceAop.class));
				} else if (String.class.isAssignableFrom(temp_obj.getClass())) {
					cacheKey = temp_obj.toString();
				} else if (Resources.class.isAssignableFrom(temp_obj.getClass())) {
					resources = (Resources) temp_obj;
					cacheKey = resources.getTableName();
				} else if (DataEntity.class.isAssignableFrom(temp_obj.getClass())) {
					Class<? extends Object> methodClass = temp_obj.getClass();
					if (methodClass.isAnnotationPresent(Table.class)) {
						Table table = (Table) methodClass.getAnnotation(Table.class);
						cacheKey = table.name();
					}
				}
			}
			int is_cache = 0;
			try {
				if (resources == null&&CheckUtil.isNotNull(cacheKey)) {
					resources = resourceService.get(cacheKey);
				}
				is_cache = resources.getIsCache();
			} catch (Exception e) {
				System.out.println("环绕通知的逻辑类 Exception:" + e.getMessage());
				// 新增resourecs 情况下
			}
			// 资源设置有进行缓存
			System.out.println(">>>环绕通知的逻辑类 redis： index:" + index + ";type:" + type + ";cacheKey:" + cacheKey
					+ ";groupKey:" + groupKey + ";is_cache:" + is_cache);
			switch (type) {
			case "query":
				// 查询时 查询缓存 存在则返回缓存,不存在则加入
				if (is_cache == 1) {
					if (CheckUtil.isNotNull(cacheKey) && CheckUtil.isNotNull(groupKey)) {
						try {
							temp = redisClient.hget(index, cacheKey, groupKey);
						} catch (Exception e) {
							temp = null;
							System.out.println("redis hget error");
						}
					}
					if (CheckUtil.isNull(temp)) {
						obj = proceedingJoinPoint.proceed(args);// 调用执行目标方法
						redisClient.hset(index, cacheKey, groupKey, obj);
					} else {
						obj = temp;
					}
				}
				break;
			case "add":
			case "edit":
			case "del":
				// 增删改 目前均清空缓存
				try {
					if (CheckUtil.isNotNull(cacheKey) && redisClient.existsKey(index, cacheKey)) {
						redisClient.del(index, cacheKey);
					}
				} catch (Exception e) {
					System.out.println("redis hget error");
				}
				break;
			default:
				break;
			}
		}
		if (CheckUtil.isNull(obj)) {
			obj = proceedingJoinPoint.proceed(args);// 调用执行目标方法
		}
		// 获取方法的参数 obj 即为返回值
		long end = System.currentTimeMillis();
		System.out.println(">>>逻辑类环绕通知：" + proceedingJoinPoint.getTarget().getClass().getName()+"."+proceedingJoinPoint.getSignature().getName() + "耗时" + (end - begin));
		return obj;
	}

}
