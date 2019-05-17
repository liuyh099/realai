package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.DeployInfo;

public interface DeployInfoDao {

	DeployInfo selectDeployInfoById(@Param("serviceId")Long serviceId);

	int insertDeployInfo(@Param("di")DeployInfo deployInfo);

	int updateDeployInfo(@Param("di")DeployInfo deployInfo);

}
