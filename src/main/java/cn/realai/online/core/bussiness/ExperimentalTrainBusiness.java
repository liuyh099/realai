package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.vo.ExperimentalTrainVO;

public interface ExperimentalTrainBusiness {
    PageBO<ExperimentalTrainVO> pageList(ExperimentalTrainQuery experimentalTrainQuery);

	int train(long experimentId);

	void testPreprocess(long experimentId);
}
