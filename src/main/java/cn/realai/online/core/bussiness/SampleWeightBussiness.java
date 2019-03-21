package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.GlobalVariableQuery;
import cn.realai.online.core.vo.WhileBoxScoreCardVO;

import java.util.List;

public interface SampleWeightBussiness {
	/**
	 * 样本权重
	 * @param experimentalResultWhileBoxQuery
	 * @return
	 */
	PageBO<WhileBoxScoreCardVO> pageBO(ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery);

	PageBO<WhileBoxScoreCardVO> pageBO(GlobalVariableQuery globalVariableQuery);

}
