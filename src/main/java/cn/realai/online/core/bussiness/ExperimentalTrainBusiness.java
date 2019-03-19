package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.vo.ExperimentalTrainVO;

import java.util.List;

public interface ExperimentalTrainBusiness {
    PageBO<ExperimentalTrainVO> pageList(ExperimentalTrainQuery experimentalTrainQuery);

	Integer deleteExperimentByIds(List<Long> ids);

	int train(long experimentId);

	void testPreprocess(long experimentId);

	ExperimentalTrainDetailBO detail(long experimentId);

}
