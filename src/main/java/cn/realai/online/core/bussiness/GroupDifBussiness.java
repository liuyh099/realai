package cn.realai.online.core.bussiness;

import cn.realai.online.core.entity.GroupDif;

import java.util.List;
import java.util.Map;

public interface GroupDifBussiness {

    //白盒决策分组对比图
    Map<String, List>  selectGroupImage(GroupDif groupDif);
}
