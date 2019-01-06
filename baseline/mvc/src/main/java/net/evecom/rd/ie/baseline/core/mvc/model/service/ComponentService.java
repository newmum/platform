package net.evecom.rd.ie.baseline.core.mvc.model.service;

import net.evecom.rd.ie.baseline.core.mvc.model.dao.ComponentDao;
import net.evecom.rd.ie.baseline.core.mvc.model.entity.UiElement;
import net.evecom.rd.ie.baseline.core.rbac.base.BaseService;
import net.evecom.rd.ie.baseline.core.rbac.model.entity.Priv;
import net.evecom.rd.ie.baseline.core.db.model.entity.Resources;
import net.evecom.rd.ie.baseline.core.mvc.model.entity.UiComEle;
import net.evecom.rd.ie.baseline.core.mvc.model.entity.UiComponent;
import net.evecom.rd.ie.baseline.core.mvc.model.entity.UiComponentProp;
import net.evecom.rd.ie.baseline.core.db.model.service.ResourceService;
import net.evecom.rd.ie.baseline.tools.constant.consts.SqlConst;
import net.evecom.rd.ie.baseline.core.db.database.query.QueryParam;
import net.evecom.rd.ie.baseline.tools.service.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ComponentService
 * @Description:
 * @author： zhengc
 * @date： 2018/10/11
 */
@Service("componentService")
public class ComponentService extends BaseService {
    @Resource
    private ResourceService resourceService;
    @Resource
    private ComponentDao componentDao;

    public List<UiComponent> getComponents(List<Priv> list, Long routerId) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (Priv power : list) {
            sb.append(power.getTid().toString() + ",");
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
                List<UiComponent> childList = componentDao.getChildListById(uiComponent.getTid());
                uiComponent.setChildList(childList);
            }
        }
        sb = new StringBuffer();
        Map<String, UiComponent> map = new HashMap();
        for (UiComponent uiComponent : componentList) {
            map.put(uiComponent.getTid(), uiComponent);
            sb.append(uiComponent.getTid().toString() + ",");
            if (uiComponent.getChildList().size() > 0) {
                for (UiComponent temp : uiComponent.getChildList()) {
                    map.put(temp.getTid(), temp);
                    sb.append(temp.getTid().toString() + ",");
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
            prop.setComponentId(uiComponent.getTid());
            prop.setElementId(uiElement.getTid());
            prop.setPropValue(uiElement.getValue());
            prop.setIsUse(1);
            resourceService.add(prop);
        }
    }

    public void editCheck(UiComponent uiComponent) throws Exception {
    }

    @Transactional
    public void update(UiComponent uiComponent) throws Exception {
        UiComponent old_uiComponent = (UiComponent) resourceService.get(UiComponent.class, uiComponent.getTid());
        if (!old_uiComponent.getElementId().equals(uiComponent.getElementId())) {
            QueryParam<UiComponentProp> param = new QueryParam<UiComponentProp>();
            param.append(UiComponentProp::getComponentId, uiComponent.getTid());
            resourceService.delete(UiComponentProp.class, param);
            addUiProp(uiComponent);
        }
        resourceService.update(uiComponent);
    }

    public List<String> delete(String id) throws Exception {
        List<UiComponent> list = getChildFile(id);
        String[] ids = new String[list.size() + 1];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getTid();
        }
        ids[ids.length - 1] = id;
        Resources resource = resourceService.get("ui_component");
        return resourceService.delete(resource, ids);
    }

    public List<String> delete(String... ids) throws Exception {
        List<String> result=new ArrayList<>();
        for (String id : ids) {
            result.addAll(delete(id));
        }
        return null;
    }

    public List<UiComponent> getChildFile(String componentId) {
        List<UiComponent> fileChildList = componentDao.getChildListById(componentId);
        return fileChildList;
    }
}
