package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.ModelNameSelectVO;
import cn.realai.online.core.vo.ModelSelectVO;

import java.util.List;

public interface ModelBussiness {
    PageBO<ModelListVO> pageList(ModelListQuery query);

    ModelDetailVO selectModelDetail(Long modelId);

    List<ModelNameSelectVO> selectModelNameList(Long serviceId);

    ModelSelectVO selectRecentModelNameList(Long modelId);
}
