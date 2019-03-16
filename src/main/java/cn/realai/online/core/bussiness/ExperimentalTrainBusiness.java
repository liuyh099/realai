package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.vo.ExperimentalTrainVO;
import com.github.pagehelper.Page;

public interface ExperimentalTrainBusiness {
    PageBO<ExperimentalTrainVO> pageList(ExperimentalTrainQuery experimentalTrainQuery);
}
