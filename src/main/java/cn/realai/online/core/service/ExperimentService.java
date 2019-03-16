package cn.realai.online.core.service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.entity.Experiment;

import java.util.List;

public interface ExperimentService {

	ExperimentBO selectExperimentById(long id);

    List<ExperimentBO> findList(Experiment experiment);
}
