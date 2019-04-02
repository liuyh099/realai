package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bussiness.GroupDifBussiness;
import cn.realai.online.core.entity.GroupDif;
import cn.realai.online.core.service.GroupDifService;
import cn.realai.online.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GroupDifBusinessImpl implements GroupDifBussiness {

    @Autowired
    private GroupDifService groupDifService;

    @Override
    public Map<String, List> selectGroupImage(GroupDif groupDif) {
        Map<String, List> result = new HashMap<>();
        List<GroupDif> groupDifs = groupDifService.selectList(groupDif);
        if (CollectionUtils.isEmpty(groupDifs)) {
            return result;
        }

        for (GroupDif dif:groupDifs) {
            if (StringUtils.isNotEmpty(dif.getVariableName()) && StringUtils.isNotEmpty(dif.getGroupName1()) &&
                StringUtils.isNotEmpty(dif.getGroupName2())) {
                List list = Arrays.asList(dif.getGroupName1(), dif.getGroupName2());
                result.put(dif.getVariableName(), list);
            }
        }
        return result;
    }
}
