package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;

public interface ModelBusiness {
    PageBO<ModelListVO> pageList(ModelListQuery query);

    ModelDetailVO selectModelDetail(Long modelId);
}
