package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentBO;
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

	/**
	 * 新增选择文件
	 * @param experimentBO
	 * @return
	 */
    Long selectFileAdd( ExperimentBO experimentBO);

	/**
	 * 检查文件名是否重复
	 * @param name
	 * @param id
	 * @return
	 */
	boolean checkTrainName(String name, Long id);

	/**
	 * 根据Id查询实验信息
	 * @param trainId
	 * @return
	 */
    ExperimentBO selectById(Long trainId);
}
