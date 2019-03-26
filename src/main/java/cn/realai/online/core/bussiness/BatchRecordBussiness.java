package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.vo.OfflineBatchCreateVO;
import cn.realai.online.core.vo.OfflineBatchListVO;

public interface BatchRecordBussiness {
    //查询离线跑批
    PageBO<OfflineBatchListVO> pageList(OfflineBatchListQuery query);

    //新增离线跑批
    Long createBatchRecord(OfflineBatchCreateVO createVO);

    //执行跑批运算
    void executeBatchRecord(Long recordId);

}
