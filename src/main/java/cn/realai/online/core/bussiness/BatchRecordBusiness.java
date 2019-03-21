package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.vo.OfflineBatchListVO;

public interface BatchRecordBusiness {
    PageBO<OfflineBatchListVO> pageList(OfflineBatchListQuery query);
}
