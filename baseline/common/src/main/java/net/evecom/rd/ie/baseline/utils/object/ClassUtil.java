package net.evecom.rd.ie.baseline.utils.object;

import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ClassUtil
 * @Description: 类操作组件
 * @author： zhengc
 * @date： 2017年10月24日
 */
public class ClassUtil {

    /**
     * 获取类下所有方法名称
     * @param clazz
     * @return
     */
    public static List getClassMethodName(Class clazz) {
        List<String> methodList = new ArrayList<String>();
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method m = (Method) methods[i];
            methodList.add(m.getName());
        }
        return methodList;
    }

    /**
     * 判断是该类在数组中
     * @param arry
     * @param clazz
     * @return
     */
    public static boolean isInClassAry(Class[] arry,Class clazz){
        if(arry==null||arry.length<1)return false;
        for(int i=0;i<arry.length;i++){
            if(arry[i].equals(clazz))return true;
        }
        return false;
    }

    /**
     * 确定class是否可以被加载
     * @param className 完整类名
     * @param classLoader 类加载
     * @return {boolean}
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            Class.forName(className, true, classLoader);
            return true;
        }
        catch (Throwable ex) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }

    /**
     * 获取所有属性
     * @param list
     * @param obj
     */
    public static void getAllfield(List<Field> list, Object obj) {
        Class<?> tempClass = obj.getClass();
        getAllfield(list, tempClass);
    }

    /**
     * 获取所有属性
     * @param list
     * @param tempClass
     */
    public static void getAllfield(List<Field> list, Class<?> tempClass) {
        while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
            Field[] fields = tempClass.getDeclaredFields();
            for (Field field : fields) {
                list.add(field);
            }
            tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
        }
    }

    /**
     * 获取所有方法
     * @param methodlist
     * @param obj
     */
    public static void getAllMethods(List<Method> methodlist, Object obj) {
        Class<?> tempClass = obj.getClass();
        while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method != null) {
                    methodlist.add(method);
                }
            }
            tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
        }
    }

    /**
     * 根据对象属性名称,获取值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object temp = null;
        if (obj == null || CheckUtil.isNull(fieldName)) {
            return null;
        }
        Field field = null;
        try {
            List<Field> list = new ArrayList<Field>();
            ClassUtil.getAllfield(list, obj);
            for (Field field2 : list) {
                if (field2.getName().equals(fieldName)) {
                    field = field2;
                    break;
                }
            }
            if (field != null) {
                field.setAccessible(true);
                temp = field.get(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
