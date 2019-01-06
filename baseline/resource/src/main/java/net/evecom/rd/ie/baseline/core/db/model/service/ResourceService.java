package net.evecom.rd.ie.baseline.core.db.model.service;

import net.evecom.rd.ie.baseline.core.db.model.entity.*;
import net.evecom.rd.ie.baseline.core.db.exception.ResourceException;
import net.evecom.rd.ie.baseline.core.db.untis.JdbcUtil;
import net.evecom.rd.ie.baseline.core.db.untis.ValidtorUtil;
import net.evecom.rd.ie.baseline.tools.constant.consts.SqlConst;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.utils.report.exl.ExportExcel;
import net.evecom.rd.ie.baseline.tools.exception.CommonException;
import net.evecom.rd.ie.baseline.tools.service.Page;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisCacheAnno;
import net.evecom.rd.ie.baseline.utils.datetime.DTUtil;
import net.evecom.rd.ie.baseline.utils.iterable.IterableForamt;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.apache.commons.collections.map.HashedMap;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ResourceService
 * @Description: 资源操作服务
 * @author zhengc
 * @date： 2018年6月8日
 */
@Service("resourceService")
public class ResourceService{

    @Resource
    private SQLManager sqlManager;

    /**
     * 根据c资源名称得到资源对象
     * @param resourceName 资源名称
     * @return 资源对象
     */
    public Resources get(String resourceName) throws Exception {
        QueryParam<Resources> param = new QueryParam();
        param.append(Resources::getResourceName, resourceName);
        return (Resources)get(Resources.class, param);
    }

    /**
     * 根据clazz和id得到数据对象
     * @param clazz 类对象型
     * @param id 主键
     * @return 查询对象
     */
    public Object get(Class<?> clazz, String id) throws Exception {
        QueryParam<Resources> param = new QueryParam();
        param.append(Resources::getTid, id);
        return get(clazz, param);
    }

    /**
     * 根据clazz,property和value得到数据对象
     * @param clazz 对象类型
     * @param property 属性名
     * @param value 值
     * @return 查询对象
     */
    public Object get(Class<?> clazz, QueryParam.Property property,
                      String value) throws Exception {
        QueryParam<Resources> param = new QueryParam<Resources>();
        param.append(property, value);
        return get(clazz, param);
    }

    /**
     * 根据resources和id获取数据对象
     * @param resource 资源
     * @param id 主键
     * @return 查询对象
     */
    public Object get(Resources resource, String id) throws Exception {
        QueryParam<Resources> param = new QueryParam<Resources>();
        param.append(Resources::getTid, id);
        return get(resource, param);
    }

    /**
     * 根据resource和param得到数据对象
     * @param resource 资源
     * @param param 条件
     * @return 查询对象
     */
    public Object get(Resources resource, QueryParam<?> param) throws Exception {
        if (resource == null) {
            throw new ResourceException(ResourceException.RESOURCE_NO_FOUND);
        }
        Class clazz = Class.forName(resource.getClasspath());
        return get(clazz, param);
    }

    /**
     * 根据clazz和param得到数据对象，操作get的统一方法
     * @param clazz 对象类型
     * @param param 条件
     * @return 查询对象
     */
    public Object get(Class clazz, QueryParam<?> param) throws Exception {
        if (clazz == null) {
            throw new ResourceException(ResourceException.RESOURCE_NO_FOUND);
        }
        Query<?> query = param.getQuery(sqlManager.query(clazz));
        List<?> list = query.select();
        if (list.size() <= 0) {
            throw new ResourceException(ResourceException.ID_NO_EXIST);
        }
        return list.get(0);
    }

    /**
     * 根据serviceName和param得到数据对象
     * @param serviceName 服务名称
     * @param param 条件
     * @return 查询对象
     */
    private Object getBySql(String serviceName, QueryParam<?> param) throws Exception {
        Page page = listBySql(serviceName, param);
        if(page.getList().size()>0)
            return page.getList().get(0);
        else
            return null;
    }

    /**
     * 根据resource和param得到数据集合
     * @param resource 资源
     * @param param 条件
     * @return 结果集对象
     */
    @RedisCacheAnno()
    public Page<?> list(Resources resource, QueryParam<?> param) throws Exception {
        return list(resource, "", param);
    }

    /**
     * 根据resource，fields和param得到数据集合
     * @param resource 资源
     * @param fields 字段
     * @param param 条件
     * @return 结果集对象
     */
    @RedisCacheAnno()
    public Page<?> list(Resources resource, String fields, QueryParam<?> param) throws Exception {
        Class<?> clazz = Class.forName(resource.getClasspath());
        return list(clazz, fields, param);
    }

    /**
     * 根据clazz和param得到数据集合
     * @param clazz 类型
     * @param param 条件
     * @return 结果集对象
     */
    @RedisCacheAnno()
    public Page<?> list(Class<?> clazz, QueryParam<?> param) throws Exception {
        return list(clazz, "", param);
    }

    /**
     * 根据clazz，fields和param得到数据集合，操作list的统一方法
     * @param clazz 类型
     * @param fields 字段
     * @param param 条件
     * @return 结果集对象
     */
    @RedisCacheAnno()
    public Page<?> list(Class<?> clazz, String fields, QueryParam<?> param) throws Exception {
        Page page = new Page<>();
        List list;
        Query<?> query = sqlManager.query(clazz);
        query = param.getQuery(query);
        if (param.isNeedPage()) {
            query = query.limit(param.getStartSize(), param.getPageSize());
        }
        if (CheckUtil.isNotNull(fields)) {
            list = query.select(fields);
        }else{
            list = query.select();
        }
        page.setList(list);
        page.setPageSize(param.getPageSize());
        page.setPage(param.getPage());
        if (param.isNeedTotal()) {
            query = param.getQuery(query);
            page.setTotal(query.count());
        }
        return page;
    }

    /**
     * 根据serviceName和param得到数据集合
     * @param serviceName 服务名称
     * @param param 条件
     * @return
     */
    public Page<?> listBySql(String serviceName, QueryParam<?> param) throws Exception {
        QueryParam<ResService> serviceParam = new QueryParam<>();
        serviceParam.append(ResService::getServiceName, serviceName);
        ResService resService = (ResService)get(ResService.class, serviceParam);
        Resources resource = (Resources)get(Resources.class,resService.getResourceId());
        Class<?> itemBean = Class.forName(resource.getClasspath());
        return listBySql(itemBean, resService.getSql(), param);
    }

    /**
     * 根据clazz，sql和param得到数据集合，操作listBySql统一方法
     * @param clazz 对象类型
     * @param sql 语句
     * @param param 条件
     * @return 结果集对象
     */
    public Page<?> listBySql(Class<?> clazz, String sql, QueryParam<?> param) throws Exception {
        Query<?> query = sqlManager.query(clazz);
        query = param.getQuery(query);
        long pageNumber = 1L;
        if (param.isNeedPage()) {
            pageNumber = param.getPage();
        }
        long offset = (pageNumber - 1) * param.getPageSize() + (sqlManager.isOffsetStartZero() ? 0 : 1);
        String fullSql = param.buildSql(sql,query.getConditionSql().toString());
        String pageSql = sqlManager.getDbStyle().getPageSQLStatement(fullSql, offset, param.getPageSize());
        SQLReady sqlReady = new SQLReady(pageSql, query.getParams().toArray());
        List<?> list = sqlManager.execute(sqlReady, clazz);
        Page page = new Page<>();
        page.setList(list);
        if (param.isNeedTotal()) {
            page.setTotal(query.count(fullSql,query.getParams().toArray()));
        }
        page.setPageSize(param.getPageSize());
        page.setPage(param.getPage());
        return page;
    }

    /**
     * 根据List集合批量增数据
     * @param list 对象集合
     * @return 新对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public void add(List<? extends Object> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            Object entity = list.get(i);
            add(entity);
        }
    }

    /**
     * 根据实体类新增资源数据
     * @param entity 对象
     * @return 新对象
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public Object add(Object entity) throws Exception {
        Method method = null;
        Class<?> clazz = entity.getClass();
        while (clazz != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
            try {
                method = clazz.getDeclaredMethod("preInsert");
                break;
            } catch (Exception e) {
                clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
            }
        }
        if(method!=null){
            method.invoke(entity);
        }
        String str = ValidtorUtil.validbean(entity);
        if (CheckUtil.isNotNull(str)) {
            throw new ResourceException(str);
        }
        try {
            sqlManager.insert(entity.getClass(), entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
        return entity;
    }

    /**
     * 根据资源名和MAP对象新增数据（未完善）
     * @param resources
     * @param hashMap
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public void add(Resources resources, Map<String, Object> hashMap) throws Exception {
        Map<String, Object> map = JdbcUtil.getInsertSql(resources.getResourceName(), hashMap);
        List<Object> params = (List<Object>) map.get("params");
        Object[] array = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            array[i] = params.get(i);
        }
        SQLReady sqlReady = new SQLReady(map.get("insertSql").toString(), array);
        sqlManager.executeUpdate(sqlReady);
    }

    /**
     * 根据资源名和List集合新增数据（未完善）
     * @param resources
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public void add(Resources resources, List<Map<String, Object>> list) throws Exception {
        for (int j = 0; j < list.size(); j++) {
            Map<String, Object> hashMap = list.get(j);
            Map<String, Object> map = JdbcUtil.getInsertSql(resources.getResourceName(), hashMap);
            List<Object> params = (List<Object>) map.get("params");
            Object[] array = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
                array[i] = params.get(i);
            }
            SQLReady sqlReady = new SQLReady(map.get("insertSql").toString(), array);
            try {
                sqlManager.executeUpdate(sqlReady);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CommonException(CommonException.BATCH_ADD_FAILED + ":第" + j + "条错误," + "错误原因:" + e.getMessage());
            }
        }
    }

    /**
     * 根据resource和param删除数据
     *
     * @param resource 资源
     * @param param 条件
     * @return 改变记录数
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public int delete(Resources resource, QueryParam<?> param) throws Exception {
        Class<?> itemBean = Class.forName(resource.getClasspath());
        return delete(itemBean, param);
    }

    /**
     * 根据clazz和param删除数据
     *
     * @param clazz 对象
     * @param param 条件
     * @return 改变记录数
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public int delete(Class<?> clazz, QueryParam<?> param) throws Exception {
        Query<?> query = sqlManager.query(clazz);
        query = param.getQuery(query);
        return query.delete();
    }

    /**
     * 根据resource和ids批量删除数据
     *
     * @param resource 资源
     * @param ids 主键集合
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public List<String> delete(Resources resource, String... ids) throws Exception {
        List<String> result = new ArrayList<>();
        Object obj;
        try {
            Class<?> itemBean = Class.forName(resource.getClasspath());
            Map map = new HashMap<>();
            // map.put("status", DataEntity.DEL_FLAG_DELETE);
            if (ids.length == 1) {
                map.put("tid", ids[0]);
                obj = IterableForamt.mapToObject(map, itemBean);
                int i = sqlManager.deleteObject(obj);
                if (i <= 0) {
                    throw new CommonException(CommonException.OPERATE_FAILED);
                }
            } else {
                for (String id : ids) {
                    map.put("tid", id);
                    obj = IterableForamt.mapToObject(map, itemBean);
                    int i = sqlManager.deleteObject(obj);
                    if (i <= 0) {
                        result.add(id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
        return result;
    }

    /**
     * 根据实体类修改指定资源的数据
     *
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "edit")
    public Object update(Object entity) throws Exception {
        Method method = null;
        Class<?> clazz = entity.getClass();
        while (clazz != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
            try {
                method = clazz.getDeclaredMethod("preUpdate");
                break;
            } catch (Exception e) {
                clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
            }
        }
        if(method!=null){
            method.invoke(entity);
        }
        // String str = ValidtorUtil.validbean(entity);
        // if (CheckUtil.isNotNull(str)) {
        // throw new ResourceException(str);
        // }
        try {
            int i = sqlManager.updateTemplateById(entity);
            if (i <= 0) {
                throw new CommonException(CommonException.OPERATE_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "edit")
    public Object update(Object entity, Long id) throws Exception {
        String str = ValidtorUtil.validbean(entity);
        if (CheckUtil.isNotNull(str)) {
            throw new ResourceException(str);
        }
        QueryParam param = new QueryParam<>();
        param.append("tid", id);
        return update(entity, param);
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "edit")
    public void update(Resources resources, Map<String, Object> hashMap) throws Exception {
        Map<String, Object> map = JdbcUtil.getUpdateSql(resources.getResourceName(), hashMap);
        List<Object> params = (List<Object>) map.get("params");
        Object[] array = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            array[i] = params.get(i);
        }
        SQLReady sqlReady = new SQLReady(map.get("updateSql").toString(), array);
        sqlManager.executeUpdate(sqlReady);
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "edit")
    public Object update(Object entity, QueryParam<?> param) throws Exception {
        Query<?> resourceUpdate = param.getQuery(sqlManager.query(entity.getClass()));
        try {
            int i = resourceUpdate.updateSelective(entity);
            if (i <= 0) {
                throw new CommonException(CommonException.OPERATE_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
        return entity;
    }

//    public List<?> getAttribute(String name) throws Exception {
//        // 查询resource id
//        Resources resources = get(name);
//        // 根据resource id 查询 resource所有字段
//        QueryParam<ResProp> param = new QueryParam<>();
//        param.append(ResProp::getResourcesId, resources.getId());
//        param.setNeedPage(false);
//        Page<ResProp> attributeList = (Page<ResProp>) list(ResProp.class, param);
//        QueryParam<?> param2 = new QueryParam<>();
//        param2.setNeedPage(false);
//        Map<Long, String> map = new HashMap<Long, String>();
//        for (ResProp attribute : attributeList.getList()) {
//            map.put(attribute.getId(), attribute.getName());
//            param2.append("attribute_id", attribute.getId(), SqlConst.EQ, SqlConst.OR);
//        }
//        param2.append("sort", "", SqlConst.ORDERBY, SqlConst.DESC);
//        Page<ResPropHandle> modelList = (Page<ResPropHandle>) list(ResPropHandle.class, param2);
//        for (ResPropHandle temp : modelList.getList()) {
//            temp.setAttributeName(map.get(temp.getAttributeId()));
//        }
//        return modelList.getList();
//    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public void delete(Resources resources, Map<String, Object> hashMap) throws Exception {
        Map<String, Object> map = JdbcUtil.getDeleteSql(resources.getResourceName(), hashMap);
        System.out.println("deleteSql:" + map.get("deleteSql").toString());
        List<Object> params = (List<Object>) map.get("params");
        Object[] array = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            array[i] = params.get(i);
        }
        SQLReady sqlReady = new SQLReady(map.get("deleteSql").toString(), array);
        int i = sqlManager.executeUpdate(sqlReady);
        if (i <= 0) {
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public List<Long> delete(Resources resources, List<Map<String, Object>> list) throws Exception {
        List<Long> result = new ArrayList<Long>();
        for (Map<String, Object> temp : list) {
            try {
                delete(resources, temp);
            } catch (Exception e) {
                result.add(Long.valueOf(temp.get("tid").toString()));
            }
        }
        return result;
    }

    /**
     * 数据导出
     *
     * @param resource 资源对象
     * @param response
     */
    public void export(Resources resource, QueryParam<?> queryParam, HttpServletResponse response) throws Exception {
        List dataList = list(resource, queryParam).getList();
        List<Map<String, Object>> headList = getHeadList(resource, 1);
        String fileName = resource.getResourceName() + "数据" + DTUtil.getTodayString4() + ".xlsx";
        ExportExcel exportExcel = new ExportExcel(resource.getResourceName() + "数据", headList, dataList).write(response, fileName).dispose();
    }

    /**
     * 导入模板
     *
     * @param resource 资源对象
     * @param response
     */
    public void importTemplate(Resources resource, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> headList = getHeadList(resource, 2);
        String fileName = resource.getResourceName() + "导入模板" + DTUtil.getTodayString4() + ".xlsx";
        ExportExcel exportExcel = new ExportExcel(resource.getResourceName() + "导入模板", headList, null).write(response, fileName).dispose();
    }

    /**
     * 导入数据
     *
     * @param resource 资源对象
     * @param list      数据集合
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public void importData(Resources resource, List<List<Object>> list) throws Exception {
        List<Map<String, Object>> headList = getHeadList(resource, 2);
        QueryParam<ResProp> param = new QueryParam<>();
        param.append(ResProp::getResourcesId, resource.getTid());
        List<ResProp> resPropList = (List<ResProp>)list(ResProp.class, param).getList();
        Map<String, Object> resPropMap = new HashedMap();
        for (ResProp resProp : resPropList) {
            resPropMap.put(resProp.getTableField(), resProp);
        }
        List importData;
        //均采用map方式进行导入
//        if (resources.getResType() == 1) {
        //  sql 对象封装成map
        Class clazz = Class.forName(resource.getClasspath());
        importData = getImportData(headList, list, clazz, resPropMap);
        add(resource, importData);
//        }
//        else {
//            //bean 对象封装成bean
//            List<Object> dataList = new ArrayList<>();
//            Class<?> itemBean = Class.forName(resources.getClasspath());
//            importData = getImportData(headList, list, itemBean, request);
//            add(importData);
//        }
    }

    private List getImportData(List<Map<String, Object>> headList, List<List<Object>> list, Class<?> itemBean, Map<String, Object> resPropMap) throws Exception {
        List result = new ArrayList();
        //均采用map方式进行导入
        Map<String, Object> baseData = new HashMap();
        QueryParam<DataEntity> param = new QueryParam<>();
        String createDate = param.getFunctionName(DataEntity::getCreateTime, itemBean);
        String editDate = param.getFunctionName(DataEntity::getUpdateTime, itemBean);
        if (resPropMap.containsKey(createDate)) {
            baseData.put(createDate, DTUtil.nowStr());
        }
        if (resPropMap.containsKey(editDate)) {
            baseData.put(editDate, DTUtil.nowStr());
        }
        for (List<Object> tempList : list) {
            Map<String, Object> data = new HashMap();
            data.putAll(baseData);
            for (int i = 0; i < headList.size(); i++) {
                data.put(headList.get(i).get("jdbcField").toString(), tempList.get(i));
            }
            result.add(data);
        }
        return result;
    }

    /**
     * 获取表头信息
     *
     * @param resource 资源对象
     * @param type     1 导出 2导入
     */
    public List<Map<String, Object>> getHeadList(Resources resource, int type) throws Exception {
        QueryParam<ResProp> queryResProp = new QueryParam();
        queryResProp.append(ResProp::getResourcesId, resource.getTid());
        List<ResProp> resPropList = (List<ResProp>) list(ResProp.class, queryResProp).getList();
        if (resPropList.size() == 0) {
            throw new ResourceException(ResourceException.RESOURCE_PROP_NO_EXIST);
        }
        Map<String, ResProp> mapResProp = new HashMap<>();
        QueryParam<ResPropExl> queryResPropExl = new QueryParam<>();
        StringBuffer sb = new StringBuffer();
        for (ResProp resProp : resPropList) {
            sb.append(resProp.getTid().toString() + ",");
            mapResProp.put(resProp.getTid(), resProp);
        }
        queryResPropExl.append(ResPropExl::getPropId, sb.toString().substring(0, sb.length() - 1), SqlConst.IN, SqlConst.AND);
        queryResPropExl.append(ResPropExl::getSort, "", SqlConst.ORDERBY, SqlConst.ASC);
        List<ResPropExl> resPropExlList = (List<ResPropExl>) list(ResPropExl.class, queryResPropExl).getList();
        if (resPropExlList.size() == 0) {
            throw new ResourceException(ResourceException.RESOURCE_PROP_EXL_NO_EXIST);
        }
        List<Map<String, Object>> headList = new ArrayList<>();
        for (ResPropExl resPropExl : resPropExlList) {
            Map<String, Object> map = IterableForamt.objectToMap(resPropExl);
            map.put("propName", mapResProp.get(resPropExl.getPropId()).getPropName());
            map.put("propType", mapResProp.get(resPropExl.getPropId()).getPropType());
            map.put("tableField", mapResProp.get(resPropExl.getPropId()).getTableField());
            headList.add(map);
        }
        return headList;
    }


}
