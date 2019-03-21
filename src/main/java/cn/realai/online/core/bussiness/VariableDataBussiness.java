package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.VariableDataQuery;
import cn.realai.online.core.vo.VariableDataVO;

public interface VariableDataBussiness {
	/**
	 * 同质数据schema/异质数据schema
	 * @param variableDataQuery
	 * @return
	 */
    PageBO<VariableDataVO> pageList(VariableDataQuery variableDataQuery);

}
