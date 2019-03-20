package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import cn.realai.online.core.bo.VariableDataBO;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
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

	/**
	 * 更新选择文件内容
	 * @param experimentBO
	 * @return
	 */
	Integer selectFileUpdate(ExperimentBO experimentBO);

	/**
	 * 更新实验参数
	 * @param experimentBO
	 * @return
	 */
	Integer updateParam(ExperimentBO experimentBO);

	/**
	 * 查询同质或者异质数据
	 * @param experimentalTrainCreateModelDataQuery
	 * @return
	 */
    PageBO<VariableDataBO> pageHomOrHemeList(ExperimentalTrainCreateModelDataQuery experimentalTrainCreateModelDataQuery);

	/**
	 * 删除VariableData 数据
	 * @param experimentId
	 * @param ids
	 */
    void deleteVariableData(Long experimentId, List<Long> ids);
}
