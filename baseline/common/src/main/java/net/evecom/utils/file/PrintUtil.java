package net.evecom.utils.file;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName: PrintUtil
 * @Description: 打印信息辅助类
 * @author zhengc
 * @date 2017-8-25
 */
public class PrintUtil {

	public static void printObject(Object obj) {
		if (obj == null) {
			print("null");
		}
		Field[] fields = obj.getClass().getDeclaredFields();// 根据Class对象获得属性
															// 私有的也可以获得
		String s = "";
		try {
			for (Field f : fields) {
				f.setAccessible(true); // 设置些属性是可以访问的
				Object val = f.get(obj); // 得到此属性的值
				String name = f.getName(); // 得到此属性的名称
				s = name + ":" + val + ",";
				println(s);
			}
		} catch (IllegalAccessException e) {
		}
	}

	/**
	 * 打印全部request中的参数
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, Object> printRequestPara(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String next = enumeration.nextElement().toString();
			print(next);
			next = (request.getParameter(next) == null ? "空" : request.getParameter(next));
			print(" : " + next + ";");
			println("");
			param.put(next, request.getParameter(next));
		}
		return param;
	}

	public static void printMap(Map<? extends Object, ? extends Object> map) {
		if (map == null) {
			printlnError("map === NULL");
			return;
		}
		if (map.size() == 0) {
			printlnError("map 长度 === 0");
			return;
		}
		Iterator<? extends Object> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			print(key);
			print("==");
			print(map.get(key));
			println("");
		}
	}

	public static void printSet(Set<? extends Object> set) {
		if (set == null) {
			printlnError("set === NULL");
			return;
		}
		if (set.size() == 0) {
			printlnError("set 长度 === 0");
			return;
		}
		Iterator<? extends Object> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			println(key);
		}
	}

	public static void printList(List<? extends Object> list) {
		if (list == null) {
			printlnError("list === NULL");
			return;
		}
		if (list.size() == 0) {
			printlnError("list 长度 === 0");
			return;
		}
		for (Object object : list) {
			if (object.getClass().getSimpleName().equals("HashMap")) {
				printMap((Map<? extends Object, ? extends Object>) object);
			} else {
				println(object);
			}
		}
	}

	public static void println(Object object) {
		if (object != null) {
			System.out.println(object.toString());
		} else {
			System.out.println("null");
		}
	}

	public static void print(Object object) {
		if (object != null) {
			System.out.print(object.toString());
		} else {
			System.out.println("null");
		}
	}

	public static void printlnError(Object object) {

		if (object != null) {
			System.err.println(object.toString());
		} else {
			System.err.println("null");
		}
	}

}
