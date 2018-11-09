package net.evecom.core.db.model.service;

import net.evecom.core.db.model.dao.TableColumnDao;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.core.db.exception.ResourceException;
import net.evecom.core.db.model.entity.ResProp;
import net.evecom.core.db.model.entity.ResPropExl;
import net.evecom.core.db.model.entity.ResPropVerify;
import net.evecom.core.db.model.entity.Resources;
import net.evecom.core.db.untis.JdbcUtil;
import net.evecom.tools.constant.consts.SqlConst;
import net.evecom.tools.exception.CommonException;
import net.evecom.tools.service.Page;
import net.evecom.utils.database.redis.RedisCacheAnno;
import net.evecom.utils.verify.CheckUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiejun
 * @date： 2018年8月6日
 * @Description: 资源属性操作服务
 */
@Service("resPropService")
public class ResPropService {

    @Resource
    private ResourceService resourceService;
    @Resource
    private TableColumnDao tableColumnDao;
    @Resource
    private SQLManager sqlManager;

    public ResPropService() {
    }

    public void editCheck(ResProp resProp) throws Exception {
        QueryParam<ResProp> param = new QueryParam<>(ResProp.class);
        param.setNeedPage(true);
        param.setPage(1);
        param.setPageSize(1);
        param.append(ResProp::getResourcesId, resProp.getResourcesId());
        param.append(ResProp::getJdbcField, resProp.getJdbcField());
        param.append(ResProp::getId, resProp.getId(), SqlConst.NOT_EQ);
        boolean bo = resourceService.list(ResProp.class, param).getList().size() > 0;
        if (bo) {
            throw new ResourceException(ResourceException.JDBC_FIELD_HAS_EXIST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "edit")
    public void update(ResProp resProp) throws Exception {
        resourceService.update(resProp);
        //删除旧的验证规则,重新添加
        QueryParam<ResPropVerify> paramResPropVerify = new QueryParam();
        paramResPropVerify.append(ResPropVerify::getResPropId, resProp.getId());
        resourceService.delete(ResPropVerify.class, paramResPropVerify);
        List<ResPropVerify> verifyList = resProp.getVerifyList();
        resourceService.add(verifyList);
//        for (ResPropVerify resPropVerify : verifyList) {
//            resPropVerify.setResPropId(resProp.getId());
//            resourceService.add(resPropVerify);
//        }
        ResPropExl resPropExl = resProp.getResPropExl();
        if (CheckUtil.isNull(resPropExl)) {
            resPropExl = ResPropExl.getDefaultData();
            resPropExl.setResPropId(resProp.getId());
            resourceService.add(resPropExl);
        } else if (CheckUtil.isNull(resPropExl.getId())) {
            QueryParam<ResPropExl>resPropExlParam=new QueryParam<>();
            resPropExlParam.append(ResPropExl::getResPropId,resProp.getId());
            resourceService.delete(ResPropExl.class,resPropExlParam);
            resPropExl.setResPropId(resProp.getId());
            resourceService.add(resPropExl);
        } else {
            resourceService.update(resPropExl);
        }
    }

    public void addCheck(ResProp resProp) throws Exception {
        QueryParam<ResProp> param = new QueryParam<ResProp>();
        param.setNeedPage(true);
        param.setPage(1);
        param.setPageSize(1);
        param.append(ResProp::getResourcesId, resProp.getResourcesId());
        param.append(ResProp::getJdbcField, resProp.getJdbcField());
        boolean bo = resourceService.list(ResProp.class, param).getList().size() > 0;
        if (bo) {
            throw new ResourceException(ResourceException.JDBC_FIELD_HAS_EXIST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public void add(ResProp resProp) throws Exception {
        //找到表格,添加列
        Resources resources = (Resources) resourceService.get(Resources.class, resProp.getResourcesId());
        String tableName = resources.getTableName();
        JdbcUtil.setJdbcType(sqlManager.getDbStyle().getName(), resProp);
        String addColumnSql = JdbcUtil.getAddColumn(tableName, resProp);
        System.out.println("addColumnSql:" + addColumnSql);
        try {
            sqlManager.executeUpdate(new SQLReady(addColumnSql));
        } catch (Exception e) {
        }
        //添加数据
        addData(resProp);
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public void addData(ResProp resProp) throws Exception {
        //添加资源属性
        resourceService.add(resProp);
        //添加资源属性验证规则
        List<ResPropVerify> verifyList = resProp.getVerifyList();
        if (verifyList != null && verifyList.size() > 0) {
            resourceService.add(verifyList);
//            for (ResPropVerify resPropVerify : verifyList) {
//                resPropVerify.setResPropId(resProp.getId());
//                resourceService.add(resPropVerify);
//            }
        }
        //添加资源属性导出规则
        ResPropExl resPropExl = resProp.getResPropExl();
        if (CheckUtil.isNull(resPropExl)) {
            //无导出规则时,添加默认导出规则
            resPropExl = ResPropExl.getDefaultData();
        }
        if (CheckUtil.isNull(resPropExl.getTitle())) {
            resPropExl.setTitle(resProp.getTitle());
        }
        resPropExl.setResPropId(resProp.getId());
        resourceService.add(resPropExl);
    }

    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "del")
    public void delete(Long id) throws Exception {
        //获取表格名称,删除列
        ResProp resProp = (ResProp) resourceService.get(ResProp.class, id);
        if (resProp.getIsSystem() == 1) {
            throw new CommonException(CommonException.SYSTEM_FIELD_NOT_DELETE);
        }
        Resources resources = (Resources) resourceService.get(Resources.class, resProp.getResourcesId());
        String delColumnSql = JdbcUtil.delColumn(resources.getTableName(), resProp.getJdbcField());
        System.out.println("delColumnSql:" + delColumnSql);
        sqlManager.executeUpdate(new SQLReady(delColumnSql));
        //删除资源属性
        QueryParam<ResProp> queryParam = new QueryParam();
        queryParam.append(ResProp::getId, id);
        resourceService.delete(ResProp.class, queryParam);
        //删除验证属性
        QueryParam<ResPropVerify> paramResPropVerify = new QueryParam();
        paramResPropVerify.append(ResPropVerify::getResPropId, resProp.getId());
        resourceService.delete(ResPropVerify.class, paramResPropVerify);
        //删除导出规则
        QueryParam<ResPropExl> paramResPropExl = new QueryParam();
        paramResPropExl.append(ResPropExl::getResPropId, resProp.getId());
        resourceService.delete(ResPropExl.class, paramResPropExl);

    }

    public ResProp get(Long id) throws Exception {
        QueryParam<ResProp> paramResProp = new QueryParam<>();
        paramResProp.append(ResProp::getId, id);
        ResProp resProp = (ResProp) resourceService.get(ResProp.class, paramResProp);
        QueryParam<ResPropVerify> paramResPropVerify = new QueryParam<>();
        paramResPropVerify.append(ResPropVerify::getResPropId, resProp.getId());
        Page<?> page = resourceService.list(ResPropVerify.class, paramResPropVerify);
        if (page != null && page.getList() != null && page.getList().size() > 0) {
            resProp.setVerifyList((List<ResPropVerify>) page.getList());
        }
        QueryParam<ResPropExl> paramResPropExl = new QueryParam();
        paramResPropExl.append(ResPropExl::getResPropId, resProp.getId());
        Page<?> pageResPropExl = resourceService.list(ResPropExl.class, paramResPropExl);
        if (pageResPropExl != null && pageResPropExl.getList() != null && pageResPropExl.getList().size() > 0) {
            resProp.setResPropExl((ResPropExl) pageResPropExl.getList().get(0));
        }
        return resProp;
    }

    public Page<ResProp> list(QueryParam<?> queryParam) throws Exception {
        Page<ResProp> page = (Page<ResProp>) resourceService.list(ResProp.class, queryParam);
        for (ResProp resProp : page.getList()) {
            QueryParam<ResPropVerify> paramResPropVerify = new QueryParam<>();
            paramResPropVerify.append(ResPropVerify::getResPropId, resProp.getId());
            Page<?> temp = resourceService.list(ResPropVerify.class, paramResPropVerify);
            if (temp != null && temp.getList() != null && temp.getList().size() > 0) {
                resProp.setVerifyList((List<ResPropVerify>) temp.getList());
            }
        }
        return page;
    }

    public List<ResProp> getByResource(Long resourceId) throws Exception {
        QueryParam<ResProp> param = new QueryParam<>();
        param.append(ResProp::getResourcesId, resourceId);
        List<ResProp> result = (List<ResProp>) resourceService.list(ResProp.class, param).getList();
        return result;
    }

    public void checkResPropVerify(Long resourceId, Map<String, Object> map) throws Exception {
        QueryParam<ResProp> queryParam = new QueryParam<>();
        queryParam.append(ResProp::getResourcesId, resourceId);
        List<ResProp> list = list(queryParam).getList();
        for (ResProp resProp : list) {
            Object obj = map.get(resProp.getJdbcField());
            List<ResPropVerify> verifyList = resProp.getVerifyList();
            if (verifyList == null || verifyList.size() == 0) {
                continue;
            } else {
                for (ResPropVerify resPropVerify : verifyList) {
                    if (obj == null || !CheckUtil.match(obj.toString(), resPropVerify.getVerifyRule())) {
                        throw new CommonException(resPropVerify.getErrorTips());
                    }
                }
            }
        }

    }

    //mvc 资源对应的资源属性不存在,重新添加默认属性
    @Transactional(rollbackFor = Exception.class)
    @RedisCacheAnno(type = "add")
    public List<ResProp> setBaseResProp(Resources resource) throws Exception {
        List<ResProp> list = new ArrayList<>();
        if (resource.getResType() == 0) {
            list = tableColumnDao.getList(resource.getTableName());
            for (ResProp resProp : list) {
                resProp.setResourcesId(resource.getId());
                resProp.setTitle(resProp.getComments());
                resProp.setUiView("{\"isShowFont\":0}");
                JdbcUtil.setAttrType(resProp);
                addData(resProp);
            }
        }
        return list;
    }
}
