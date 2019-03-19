package cn.realai.online.userandperm.business;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.PageQuery;
import cn.realai.online.userandperm.bo.RoleBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public interface RoleBusiness {
    /**
     * 查询角色列表
     *
     * @param pageQuery
     * @return
     */
    PageBO<RoleBO> list(PageQuery pageQuery);

    /**
     * 增加角色
     *
     * @param roleBO
     * @return
     */
    boolean insert(RoleBO roleBO);

    /**
     * 检查角色名称
     *
     * @param checkName
     * @return
     */
    Boolean checkName(String checkName);

    /**
     * 删除用户角色
     *
     * @param ids
     * @return
     */
    Integer delete(List<Long> ids);
}
