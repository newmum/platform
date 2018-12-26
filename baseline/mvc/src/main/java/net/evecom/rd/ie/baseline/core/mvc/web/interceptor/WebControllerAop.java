package net.evecom.rd.ie.baseline.core.mvc.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.User;
import net.evecom.rd.ie.baseline.core.mvc.model.entity.SystemLog;
import net.evecom.rd.ie.baseline.core.rbac.model.service.AuthCertService;
import net.evecom.rd.ie.baseline.tools.exception.CommonException;
import net.evecom.rd.ie.baseline.tools.service.Result;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisClient;
import net.evecom.rd.ie.baseline.utils.datetime.DTUtil;
import net.evecom.rd.ie.baseline.utils.file.PrintUtil;
import net.evecom.rd.ie.baseline.utils.string.StringUtil;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class WebControllerAop {

	private final String POINT_CUT = "execution(* net.evecom..*.controller..*.*(..)))";

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

	@AfterThrowing(value = "executeService()", throwing = "exception")
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		String msg = ">>>" + joinPoint.getSignature().getName() + "异常通知:";
		if (exception instanceof NullPointerException) {
			msg += "空指针" + exception.getStackTrace()[0];
		} else {
			msg += exception.getMessage();
		}
		System.out.println(msg);
	}

	@After("executeService()")
	public void doAfterAdvice(JoinPoint joinPoint) {
	}

	@Resource(name = "redisClient")
	protected RedisClient redisClient;
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private AuthCertService authCertService;

	@Around("executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		long begin = System.currentTimeMillis();
		Object obj;
		SystemLog sysLog = new SystemLog();
		boolean bo = true;
		try {// obj之前可以写目标方法执行前的逻辑
				// 获取当前登录用户
			Object[] args = proceedingJoinPoint.getArgs();
			for (Object object : args) {
				// 文件操作==>不做操作记录
				if (!CheckUtil.isNull(object)) {
					if (MultipartFile.class.isAssignableFrom(object.getClass())) {
						bo = false;
						break;
					}
				}
			}
			if (bo) {
				Signature signature = proceedingJoinPoint.getSignature();
				sysLog.setMethod(proceedingJoinPoint.getTarget().getClass().getName()+"."+signature.getName());
				sysLog.setCreateTime(DTUtil.nowDate());
				sysLog.setParameter(objectMapper.writeValueAsString(args));
				System.out.println("本次请求入参是:" + objectMapper.writeValueAsString(args));
			}
			// 获取方法的参数 obj 即为返回值
			try {
				RequestAttributes ra = RequestContextHolder.getRequestAttributes();
				ServletRequestAttributes sra = (ServletRequestAttributes) ra;
				HttpServletRequest request = sra.getRequest();
				PrintUtil.printRequestPara(request);
				User user = authCertService.getUser(request);
				Long id = user.getTid();
				sysLog.setUserId(id);
			} catch (Exception e) {
			}

			obj = proceedingJoinPoint.proceed(args);// 调用执行目标方法
			long end = System.currentTimeMillis();
			System.out.println(">>>控制器环绕通知：" + proceedingJoinPoint.getSignature().getName() + "耗时" + (end - begin));
			sysLog.setTime(end - begin);
			sysLog.setIsSuccess(1);
		} catch (Throwable exception) {
			boolean isRuntime = exception instanceof CommonException;
			if (!isRuntime) {
				exception.printStackTrace();
			}
			if (exception instanceof NullPointerException) {
				obj = Result.failed(CommonException.NULL_POINTER, exception.getStackTrace()[0]);
			} else if (exception instanceof ClassNotFoundException) {
				obj = Result.failed(CommonException.CLASS_NOT_FOUND, StringUtil.getExt(exception.getMessage()));
			} else {
				obj = Result.failed(exception.getMessage());
			}
			sysLog.setIsSuccess(1);
		} finally {
			try {
				if (bo) {
//					RsMq.send("system_log", "java-platform-rsmq", "controller", sysLog);
				}
			} catch (Exception e) {
				System.out.println("mq error" + e.getMessage());
			}
		}
		return obj;
	}

}
