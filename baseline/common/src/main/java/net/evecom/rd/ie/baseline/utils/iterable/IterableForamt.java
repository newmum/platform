package net.evecom.rd.ie.baseline.utils.iterable;

import net.evecom.rd.ie.baseline.utils.datetime.DTUtil;
import net.evecom.rd.ie.baseline.utils.object.ClassUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IterableForamt
 * @Description: 集合转换组件 @author： zhengc @date： 2009年6月7日
 */
public class IterableForamt {

    /**
     * 自动注入
     *
     * @param a   对象类型
     * @param map 主动注入的属性
     * @return 对象
     * @throws Exception 如对应属性名有不同,需修改方法
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> a) throws Exception {
        Object obj = a.newInstance();
        List<Field> list = new ArrayList<Field>();
        ClassUtil.getAllfield(list, obj);
        Field[] fields = new Field[list.size()];
        for (int i = 0; i < list.size(); i++) {
            fields[i] = list.get(i);
        }
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            String fieldName = field.getName();
            Object temp = map.get(fieldName);
            if (temp != null) {
                String type = field.getType().getSimpleName();
                try {
                    switch (type) {
                        case "Date":
                            field.set(obj, DTUtil.stringToDate(temp.toString(),null));
                            break;
                        case "long":
                        case "Long":
                            Long l= Double.valueOf(temp.toString()).longValue();
                            field.set(obj,l);
                            break;
                        case "integer":
                        case "int":
                            Integer i= Double.valueOf(temp.toString()).intValue() ;
                            field.set(obj, i);
                            break;
                        default:
                            field.set(obj, temp.toString());
                            break;
                    }
                } catch (Exception e) {

                }
            }
        }
        return obj;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Object mapToObject2(Map<String, Object> map, Class<?> beanClass)
            throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();// 创建 JavaBean 对象
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());// 获取类属性
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * java对象中list中的对象
     *
     * @param obj     list中的泛型对象
     * @param listNum 对象中第nlist
     * @param num     list中第n个集合
     * @return
     */
    public Map<String, String> getSubObjectMap(Object obj, Map<String, String> map, int listNum, int num) {
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null && !o.toString().trim().equals("0"))
                    map.put("list" + listNum + "-" + num + "@" + varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 对象转byte[]
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] objectToBytes(Object obj) throws Exception {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        byte[] bytes = bo.toByteArray();
        bo.close();
        oo.close();
        return bytes;
    }

    /**
     * byte[]转对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

    /**
     * 对象转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        List<Field> list = new ArrayList<Field>();
        ClassUtil.getAllfield(list, obj);
        for (Field field : list) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * 数组转列表
     * @param arr
     * @return
     */
    public static List arrToList(Object []arr){
        List arrList=new ArrayList();
        for(int i=0;i<arr.length;i++){
            arrList.add(arr[i]);
        }
        return arrList;
    }
}
