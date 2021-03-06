package cn.realai.online.core.dao;

import cn.realai.online.core.entity.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceDao {

    List<Service> findList(Service service);

    Integer insert(Service service);

    Integer delete(@Param("ids") List<Long> ids);

    Service get(Long id);

    Integer update(Service service);

    List<Service> getAlreadyPublishService();

    void online(@Param("id") Long serviceId);

    List<Service> findListByModelStatus(@Param("status") String status);

    //查询已发布的服务
    List<Service> findListByAlreadyPublishModel();
}
