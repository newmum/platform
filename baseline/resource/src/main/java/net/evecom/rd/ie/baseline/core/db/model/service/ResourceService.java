package net.evecom.rd.ie.baseline.core.db.model.service;

import net.evecom.rd.ie.baseline.core.db.model.entity.*;
import net.evecom.rd.ie.baseline.core.db.exception.ResourceException;
import net.evecom.rd.ie.baseline.core.db.untis.JdbcUtil;
import net.evecom.rd.ie.baseline.core.db.untis.ValidtorUtil;
import net.evecom.rd.ie.baseline.tools.constant.consts.SqlConst;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryBuilder;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.utils.report.exl.ExportExcel;
import net.evecom.rd.ie.baseline.tools.exception.CommonException;
import net.evecom.rd.ie.baseline.tools.service.Page;
import net.evecom.rd.ie.baseline.utils.database.redis.RedisCacheAnno;
import net.evecom.rd.ie.baseline.utils.datetime.DTUtil;
import net.evecom.rd.ie.baseline.utils.iterable.IterableForamt;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.formula.functions.T;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.kit.PageKit;
import org.beetl.sql.core.query.GroupBy;
import org.beetl.sql.core.query.OrderBy;
import org.beetl.sql.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
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
    @Resource
    private ResPropService resPropService;
    @Resource
    private ResourceService resourceService;

    /**
     * 根据资源id获取资源对象
     *
     * @param id
     * @return
     */
    public Resources get(Long id) throws Exception {
        Query<Resources> resourceQuery = sqlManager.lambdaQuery(Resources.class).andEq(Resources::getTid, id);
        List<Resources> resourceList = resourceQuery.select();
        if (resourceList.size() <= 0) {
            throw new ResourceException(ResourceException.RESOURCE_NO_EXIST + ":" + id + ";");
        }
        return resourceList.get(0);
    }

    /**
     * 根据资源id获取资源对象
     *
     * @param id
     * @return
     */
    public ResService getRes(Long id) throws Exception {
        Query<ResService> resourceQuery = sqlManager.lambdaQuery(ResService.class).andEq(ResService::getResourceId, id).andEq(ResService::getServiceType,4);
        List<ResService> resourceList = resourceQuery.select();
        if (resourceList.size() <= 0) {
            throw new ResourceException(ResourceException.RESOURCE_NO_EXIST + ":" + id + ";");
        }
        return resourceList.get(0);
    }


    /**
     * 根据resourceName获取资源对象
     *
     * @param resourceName
     * @return
     */
    public Resources get(String resourceName) throws Exception {
        Query<Resources> resourceQuery = sqlManager.lambdaQuery(Resources.class).andEq(Resources::getResourceName, resourceName);
        List<Resources> resourceList = resourceQuery.select();
        if (resourceList.size() <= 0) {
            throw new ResourceException(ResourceException.RESOURCE_NO_EXIST + ":" + resourceName + ";");
        }
        return resourceList.get(0);
    }

    /**
     * 根据clazz和id得到数据对象
     *
     * @param clazz
     * @param id
     * @return
     */
    public Object get(Class<?> clazz, Long id) throws Exception {
        QueryParam<Resources> param = new QueryParam<Resources>();
        param.append(Resources::getTid, id);
        return get(clazz, param);
    }

    /**
     * 根据resources和id获取数据对象
     *
     * @param resources
     * @param id
     * @return
     */
    public Object get(Resources resources, Long id) throws Exception {
        QueryParam<Resources> param = new QueryParam<Resources>();
        param.append(Resources::getTid, id);
        return get(resources, param);
    }

    /**
     * 根据resource和param得到数据对象
     *
     * @param resource
     * @param param
     * @return
     */
    public Object get(Resources resource, QueryParam<?> param) throws Exception {
        if (resource == null) {
            throw new ResourceException(ResourceException.RESOURCE_NO_FOUND);
        }
        if (resource.getResourceType() == 1) {
            return getBySql(resource.getResourceSql(), param);
        }
        Class clazz = Class.forName(resource.getClasspath());
        return get(clazz, param);
    }


    /**
     * 根据clazz和param得到数据对象
     *
     * @param clazz
     * @param param
     * @return
     */
    public Object get(Class clazz, QueryParam<?> param) throws Exception {
        if (clazz == null) {
            throw new ResourceException(ResourceException.RESOURCE_NO_FOUND);
        }
        Query<?> resourceQuery = QueryBuilder.getQuery(param.getList(), sqlManager.query(clazz));
        List<?> resourceList = resourceQuery.select();
        if (resourceList.size() <= 0) {
            throw new ResourceException(ResourceException.ID_NO_EXIST);
        }
        return resourceList.get(0);
    }

    /**
     * 根据sql和param得到数据对象
     *
     * @param sql
     * @param param
     * @return
     */
    private Object getBySql(String sql, QueryParam<?> param) throws Exception {
        Query<?> query = sqlManager.query(ResourceService.class);
        query = QueryBuilder.getQuery(param.getList(), query);
        query.setSql(new StringBuilder(sql+" "));
//        通过beetl默认方法来设置sql语句
//        Method method = null;
//        Class<?> clazz = query.getClass();
//        method = clazz.getDeclaredMethod("addAdditionalPartSql");
//        method.setAccessible(true);
//        method.invoke(query);
//        sql = query.getSql().toString();
        //addAdditionalPartSql
//        Field field = null;
//        Class<?> clazz = query.getClass().getSuperclass();
//        field = clazz.getDeclaredField(SqlConst.ORDERBY);
//        field.setAccessible(true);
//        OrderBy orderBy = (OrderBy) field.get(query);
//        field = clazz.getDeclaredField(SqlConst.GROUPBY);
//        field.setAccessible(true);
//        GroupBy groupBy = (GroupBy) field.get(query);
        StringBuilder sb=query.getSql();
//        if (groupBy != null) {
//            sb.append(groupBy.getGroupBy()).append(" ");
//        }
//        if (orderBy != null) {
//            sb.append(orderBy.getOrderBy()).append(" ");
//        }
        sql=sb.toString();
        List<Object> params = query.getParams();
        Object[] array = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            array[i] = params.get(i);
        }
        SQLReady sqlReady = new SQLReady(sql, array);
        long pageNumber = 1;
        long pageSize = 1;
        long offset = (pageNumber - 1) * pageSize + (sqlManager.isOffsetStartZero() ? 0 : 1);
        String pageSql = sqlManager.getDbStyle().getPageSQLStatement(sql, offset, pageSize);
        List<HashMap> list = sqlManager.execute(new SQLReady(pageSql, sqlReady.getArgs()), HashMap.class);
        if (list.size() <= 0) {
            throw new ResourceException(ResourceException.RESOURCE_NO_EXIST);
        }
        return list.get(0);
    }

    /**
     * 根据resource和param得到数据集合
     *
     * @param resource
     * @param param
     * @return
     */
    @RedisCacheAnno()
    public Page<?> list(Resources resource, QueryParam<?> param) throws Exception {
        return list(resource, "", param);
    }

    /**
     * 根据resource，fields和param得到数据集合
     *
     * @param resource
     * @param fields
     * @param param
     * @return
     */
    @RedisCacheAnno()
    public Page<?> list(Resources resource, String fields, QueryParam<?> param) throws Exception {
        Class<?> itemBean = Class.forName(resource.getClasspath());
        return list(itemBean, fields, param);
    }

    /**
     * 根据clazz和param得到数据集合
     *
     * @param clazz
     * @param param
     * @return
     */
    @RedisCacheAnno()
    public Page<?> list(Class<?> clazz, QueryParam<?> param) throws Exception {
        return list(clazz, "", param);
    }

    /**
     * 根据clazz，fields和param得到数据集合
     *
     * @param clazz
     * @param param
     * @return
     */
    @RedisCacheAnno()
    public Page<?> list(Class<?> clazz, String fields, QueryParam<?> param) throws Exception {
        Page page = new Page<>();
        List resourceList;
        Query<?> resourceQuery = sqlManager.query(clazz);
        resourceQuery = QueryBuilder.getQuery(param.getList(), resourceQuery);
        if (param.isNeedPage()) {
            resourceQuery = resourceQuery.limit(param.getStartSize(), param.getPageSize());
        }
        if (CheckUtil.isNotNull(fields)) {
            resourceList = resourceQuery.select(fields);
        }else{
            resourceList = resourceQuery.select();
        }
        page.setList(resourceList);
        page.setPageSize(param.getPageSize());
        page.setPage(param.getPage());
        if (param.isNeedTotal()) {
            resourceQuery = QueryBuilder.getQuery(param.getList(), resourceQuery);
            page.setTotal(resourceQuery.count());
        }
        return page;
    }


    /**
     * 根据sql和param得到数据集合
     *
     * @param sql
     * @param param
     * @return
     */
//    private Page<?> listBySql(String sql, QueryParam<?> param) throws Exception {
//        return listBySql(sql,  param);
//    }

    /**
     * 根据sql，fields和param得到数据集合
     * @param sql
     * @param param
     * @return
     */
    public Page<?> listBySql(Class<?> clazz, String sql, QueryParam<?> param) throws Exception {
        Page page = new Page<>();

        Query<?> query = sqlManager.query(clazz);
        query = QueryBuilder.getQuery(param.getList(), query);
        //将拼接语句中的'WHERE'替换为'AND'
        String str = query.getSql().toString();
        str = str.replaceAll("WHERE"," AND");
        str = str.replaceAll("`TID`","user.tid");
        str = str.replaceAll("`CREATE_TIME`","user.create_time");
        sql += str;
        query.setSql(new StringBuilder(sql+" "));
//        通过beetl默认方法来设置sql语句
//        Method method = null;
//        Class<?> clazz = query.getClass();
//        method = clazz.getDeclaredMethod("addAdditionalPartSql");
//        method.setAccessible(true);
//        method.invoke(query);
//        sql = query.getSql().toString();
        //addAdditionalPartSql
//        Field field = null;
//        Class<?> clazzs = query.getClass().getSuperclass();
//        field = clazzs.getDeclaredField(SqlConst.ORDERBY);
//        field.setAccessible(true);
//        OrderBy orderBy = (OrderBy) field.get(query);
//        field = clazzs.getDeclaredField(SqlConst.GROUPBY);
//        field.setAccessible(true);
//        GroupBy groupBy = (GroupBy) field.get(query);
        StringBuilder sb=query.getSql();
//        if (groupBy != null) {
//            sb.append(groupBy.getGroupBy()).append(" ");
//        }
//        if (orderBy != null) {
//            sb.append(orderBy.getOrderBy()).append(" ");
//        }
        sql=sb.toString();
        List<HashMap> list = new ArrayList<>();
        int size = 20;//这里要读配置
        if (param.isNeedPage() && param.getPageSize() < size) {
            size = param.getPageSize();
        }
        List<Object> params = query.getParams();
        Object[] array = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            array[i] = params.get(i);
        }
        SQLReady sqlReady = new SQLReady(sql, array);
        long pageNumber = 1L;
        if (param.isNeedPage()) {
            pageNumber = param.getPage();
        }
        long pageSize = size;
        long offset = (pageNumber - 1) * pageSize + (sqlManager.isOffsetStartZero() ? 0 : 1);
        String pageSql = sqlManager.getDbStyle().getPageSQLStatement(sql, offset, pageSize);
        list = sqlManager.execute(new SQLReady(pageSql, sqlReady.getArgs()), HashMap.class);
        page.setList(list);
        if (param.isNeedTotal()) {
            sql = sqlReady.getSql();
            String countSql = PageKit.getCountSql(sql);
            List<Long> countList = sqlManager.execute(new SQLReady(countSql, sqlReady.getArgs()), Long.class);
            Long count = countList.get(0);
            page.setTotal(count);
        }
        page.setPageSize(param.getPageSize());
        page.setPage(param.getPage());
        return page;
    }

    /**
     * 根据资源名和MAP对象新增数据
     *
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
     * 根据资源名和List集合新增数据
     *
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
     * 根据List集合新增数据
     *
     * @param list
     * @return
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
     *
     * @param entity
     * @return
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
        method.invoke(entity);
        String str = ValidtorUtil.validbean(entity);
        if (CheckUtil.isNotNull(str)) {
            throw new ResourceException(str);
        }
        try {
            sqlManager.insert(entity, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonException.OPERATE_FAILED);
        }
        return entity;
    }

    /**
     * 根据条件删除数据
     *
     * @param resource
     * @param param
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public int delete(Resources resource, QueryParam<?> param) throws Exception {
        Class<?> itemBean = Class.forName(resource.getClasspath());
        return delete(itemBean, param);
    }

    /**
     * 根据类删除数据
     *
     * @param clazz
     * @param param
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public int delete(Class<?> clazz, QueryParam<?> param) throws Exception {
        Query<?> query = sqlManager.query(clazz);
        query = QueryBuilder.getQuery(param.getList(), query);
        return query.delete();
    }

    /**
     * 根据资源批量删除数据
     *
     * @param resource
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public List<Long> delete(Resources resource, Long... ids) throws Exception {
        List<Long> result = new ArrayList<Long>();
        Object obj;
        try {
            Class<?> itemBean = Class.forName(resource.getClasspath());
            QueryParam<Resources> param = new QueryParam<Resources>();
            //String tidName = param.getFunctionName(Resources::getTid, Resources.class);
            Map map = new HashMap<>();
            // map.put("status", DataEntity.DEL_FLAG_DELETE);
            if (ids.length == 1) {
                map.put("tid", ids[0]);
                System.out.println("id号:" + ids[0].toString());
                obj = IterableForamt.mapToObject(map, itemBean);
                int i = sqlManager.deleteObject(obj);
                if (i <= 0) {
                    throw new CommonException(CommonException.OPERATE_FAILED);
                }
            } else {
                for (Long id : ids) {
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
        method.invoke(entity);
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
        Query<?> resourceUpdate = QueryBuilder.getQuery(param.getList(), sqlManager.query(entity.getClass()));
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
        List<ResProp> resPropList = resPropService.getByResource(resource.getTid());
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
        Map<Long, ResProp> mapResProp = new HashMap<>();
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
