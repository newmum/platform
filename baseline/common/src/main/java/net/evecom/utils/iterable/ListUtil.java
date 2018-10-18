package net.evecom.utils.iterable;

import java.util.List;

/**
 * @ClassName: ListUtil
 * @Description: List集合组件
 * @author： zhengc
 * @date： 2016年6月3日
 */
public class ListUtil {

    /**
     * list 数据去重
     * @param list
     * @return
     */
    public static List<?> removeDuplicate(List<?> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
