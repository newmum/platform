package net.evecom.core.mvc.model.service;

import net.evecom.core.mvc.model.dao.IUiComponentDao;
import net.evecom.core.mvc.model.entity.UiElement;
import net.evecom.core.rbac.base.BaseService;
import net.evecom.core.rbac.model.entity.Power;
import net.evecom.core.db.model.entity.Resources;
import net.evecom.core.mvc.model.entity.UiComEle;
import net.evecom.core.mvc.model.entity.UiComponent;
import net.evecom.core.mvc.model.entity.UiComponentProp;
import net.evecom.core.db.model.service.ResourceService;
import net.evecom.tools.constant.consts.SqlConst;
import net.evecom.core.db.database.query.QueryParam;
import net.evecom.tools.service.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiejun
 */

@Service("uiComponentService")
public class UiComponentService extends BaseService {
    @Resource
    private ResourceService resourceService;
    @Resource
    private IUiComponentDao iUiComponentDao;

    public List<UiComponent> getComponents(List<Power> list, Long routerId) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (Power power : list) {
            sb.append(power.getId().toString() + ",");
        }
        String value = sb.toString().substring(0, sb.length() - 1);
        QueryParam<UiComponent> param = new QueryParam<>();
        param.append(UiComponent::getCrmPowerId, value, SqlConst.IN, SqlConst.AND);
        param.append(UiComponent::getUiRouterId, routerId);
        // 获取权限下的所有组件集合
        List<UiComponent> componentList = (List<UiComponent>) resourceService.list(UiComponent.class, param).getList();
        if (componentList == null || componentList.size() == 0) {
            return componentList;
        }
        for (UiComponent uiComponent : componentList) {
            if (uiComponent.getName().equals("table")||uiComponent.getName().equals("form")) {
                List<UiComponent> childList = iUiComponentDao.getChildListById(uiComponent.getId());
                uiComponent.setChildList(childList);
            }
        }
        sb = new StringBuffer();
        Map<Long, UiComponent> map = new HashMap<Long, UiComponent>();
        for (UiComponent uiComponent : componentList) {
            map.put(uiComponent.getId(), uiComponent);
            sb.append(uiComponent.getId().toString() + ",");
            if (uiComponent.getChildList().size() > 0) {
                for (UiComponent temp : uiComponent.getChildList()) {
                    map.put(temp.getId(), temp);
                    sb.append(temp.getId().toString() + ",");
                }
            }
        }
        // 通过组件id获取组件的属性与值
        param.clear();
        value = sb.toString().substring(0, sb.length() - 1);
        param.append("component_id", value, SqlConst.IN, SqlConst.AND);
        Resources resources = resourceService.get("ui_component_element_view");
        List<UiComEle> comEleList = (List<UiComEle>) resourceService.list(resources, param).getList();
        for (UiComEle uiComEle : comEleList) {
            long componentId = uiComEle.getComponentId();
            UiComponent temp = map.get(componentId);
            if (temp != null) {
                map.get(componentId).addEle(uiComEle);
            }
        }

        return componentList;
    }

    public void addCheck(UiComponent uiComponent) throws Exception {
    }

    @Transactional
    public void add(UiComponent uiComponent) throws Exception {
        resourceService.add(uiComponent);
        addUiProp(uiComponent);
    }

    @Transactional
    public void addUiProp(UiComponent uiComponent) throws Exception {
        QueryParam<UiElement> param = new QueryParam<UiElement>();
        param.append(UiElement::getParentId, uiComponent.getElementId());
        Page<UiElement> page = (Page<UiElement>) resourceService.list(UiElement.class, param);
        boolean bo = (page != null && page.getList() != null && page.getList().size() > 0);
        //为组件添加所有元素属性,并设置最初默认值
        if (bo) for (UiElement uiElement : page.getList()) {
            UiComponentProp prop = new UiComponentProp();
            prop.setComponentId(uiComponent.getId());
            prop.setElementId(uiElement.getId());
            prop.setPropValue(uiElement.getValue());
            prop.setIsUse(1);
            resourceService.add(prop);
        }
    }

    public void editCheck(UiComponent uiComponent) throws Exception {
    }

    @Transactional
    public void update(UiComponent uiComponent) throws Exception {
        UiComponent old_uiComponent = (UiComponent) resourceService.get(UiComponent.class, uiComponent.getId());
        if (!old_uiComponent.getElementId().equals(uiComponent.getElementId())) {
            QueryParam<UiComponentProp> param = new QueryParam<UiComponentProp>();
            param.append(UiComponentProp::getComponentId, uiComponent.getId());
            resourceService.delete(UiComponentProp.class, param);
            addUiProp(uiComponent);
        }
        resourceService.update(uiComponent);
    }

    public List<Long> delete(Long id) throws Exception {
        List<UiComponent> list = getChildFile(id);
        Long[] ids = new Long[list.size() + 1];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getId();
        }
        ids[ids.length - 1] = id;
        Resources resource = resourceService.get("ui_component");
        return resourceService.delete(resource, ids);
    }

    public List<Long> delete(Long... ids) throws Exception {
        List<Long> result=new ArrayList<Long>();
        for (Long id : ids) {
            result.addAll(delete(id));
        }
        return null;
    }

    public List<UiComponent> getChildFile(Long componentId) {
        List<UiComponent> fileChildList = iUiComponentDao.getChildListById(componentId);
        return fileChildList;
    }
}
