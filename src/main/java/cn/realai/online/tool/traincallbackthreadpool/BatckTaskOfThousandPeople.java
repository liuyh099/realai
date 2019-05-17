package cn.realai.online.tool.traincallbackthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.model.PeoplelInformetion;
import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.entity.PersonalHomoResultSet;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.PersonalComboResultSetService;
import cn.realai.online.core.service.PersonalHetroResultSetService;
import cn.realai.online.core.service.PersonalHomoResultSetService;
import cn.realai.online.core.service.PersonalInformationService;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatckTaskOfThousandPeople extends BaseBatchTask{

	private static Logger logger = LoggerFactory.getLogger(BatckTaskOfThousandPeople.class);
	
	public BatckTaskOfThousandPeople(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}

	@Override
	public void run() {
		Long startTime = System.currentTimeMillis();
		logger.info("BatchTaskOfHetro run. this{}, 当前线程信息, 线程数{}, 任务数{}", 
				JSON.toJSONString(this), 
				ModelCallPool.modelCallPool.getPoolSize(), ModelCallPool.modelCallPool.getQueue().size());
		try {
			RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
			
			String redisValue = redisClientTemplate.get(redisKey);
			
			if (redisValue == null) {
				logger.info("BatchTaskOfHetro run PersonalInformetion is null. this{}.", JSON.toJSONString(this)); 
				return ;
			}
			
			PeoplelInformetion peoplelInformetion = JSON.parseObject(redisValue, PeoplelInformetion.class);
			
			if (peoplelInformetion == null) {
				logger.info("BatchTaskOfHetro run PersonalInformetion is null. this{}.", JSON.toJSONString(this)); 
				return ;
			}
			
			logger.info("BatchTaskOfHetro run PersonalInformetion analysisJSONTime{}", System.currentTimeMillis() - startTime);
			
	    	//变量信息
	    	VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
	        List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
	        Map<String, Long> vdMap = new HashMap<String, Long>();
	        for (VariableData vd : vdList) {
	            vdMap.put(vd.getName() + vd.getVariableType(), vd.getId());
	        }
			
	        logger.info("BatchTaskOfHetro run PersonalInformetion variableDataTime{}", System.currentTimeMillis() - startTime);
	        
	        //List<PersonalInformation> allPiList = JSON.parseArray(peoplelInformetion.getPersonalInformation(), PersonalInformation.class);
	        
	        List<PersonalInformation> allPiList = analysisPersonalInformation(peoplelInformetion.getPersonalInformation());
	        
			//处理千人千面信息
	        /*for (PersonalInformation pi : allPiList) {
				//处理人员信息
				analysisPersonalInformation(pi);
			}*/
			
	        Map<String, Long> piMap = new HashMap<String, Long>();
	        for (PersonalInformation pi : allPiList) {
	            piMap.put(pi.getPersonalId() + batchId, pi.getId());
	        }
	        
	        logger.info("BatchTaskOfHetro run PersonalInformetion PersonalInformationTime{}, 数据条数{}", System.currentTimeMillis() - startTime, allPiList.size());
	        
	        analysisPersonalCombo(peoplelInformetion.getPersonalComboResultSet(),piMap, vdMap);
			
	        analysisPersonalHetro(peoplelInformetion.getPersonalHetroResultSet(), piMap, vdMap);
	    	
	        analysisPersonalHomo(peoplelInformetion.getPersonalHomoResultSet(), piMap, vdMap);
			
	    	logger.info("BatckTaskOfThousandPeople run, 千人千面解析完毕. 耗时{}", System.currentTimeMillis() - startTime);
	    	redisClientTemplate.delete(redisKey);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("BatchTaskOfHomo run exception.");
		}
	}

	private void analysisPersonalCombo(String personalCombo, Map<String, Long> piMap, Map<String, Long> vdMap) {
		if (personalCombo == null || "".equals(personalCombo)) {
			return ;
		}
		
		List<PersonalComboResultSet> comboList = JSON.parseArray(personalCombo, PersonalComboResultSet.class);
		
		for (PersonalComboResultSet pcrs : comboList) {
			Long pid = piMap.get(pcrs.getPersonalId() + batchId);
    		pcrs.setPid(pid);
    		pcrs.setBatchId(batchId);
    		pcrs.setExperimentId(experimentId);
    		pcrs.setVariableId1(vdMap.get(pcrs.getVariableName1() + VariableData.VARIABLE_TYPE_HETRO));
    		pcrs.setVariableId2(vdMap.get(pcrs.getVariableName2() + VariableData.VARIABLE_TYPE_HETRO));
    		pcrs.setVariableId3(vdMap.get(pcrs.getVariableName3() + VariableData.VARIABLE_TYPE_HETRO));
    	}
		
		PersonalComboResultSetService personalComboResultSeService = SpringContextUtils.getBean(PersonalComboResultSetService.class);
    	personalComboResultSeService.insertList(comboList);
	}

	private void analysisPersonalHetro(String personalHetro, Map<String, Long> piMap, Map<String, Long> vdMap) {
		if (personalHetro == null || "".equals(personalHetro)) {
			return ;
		}
		
		List<PersonalHetroResultSet> hetroList = JSON.parseArray(personalHetro, PersonalHetroResultSet.class);
		
		for (PersonalHetroResultSet phrs : hetroList) {
    		phrs.setBatchId(batchId);
    		phrs.setExperimentId(experimentId);
    		Long pid = piMap.get(phrs.getPersonalId() + batchId);
    		phrs.setPid(pid);
    		phrs.setVariableId(vdMap.get(phrs.getVariableName() + VariableData.VARIABLE_TYPE_HETRO));
    	}
		PersonalHetroResultSetService personalHetroResultSetService = SpringContextUtils.getBean(PersonalHetroResultSetService.class);
    	personalHetroResultSetService.insertList(hetroList);
	}

	private void analysisPersonalHomo(String personalHomo, Map<String, Long> piMap, Map<String, Long> vdMap) {
		if (personalHomo == null || "".equals(personalHomo)) {
			return ;
		}
		
		List<PersonalHomoResultSet>homoList = JSON.parseArray(personalHomo, PersonalHomoResultSet.class);
		
		for (PersonalHomoResultSet phrs : homoList) {
			Long pid = piMap.get(phrs.getPersonalId() + batchId);
			phrs.setPid(pid);
			phrs.setBatchId(batchId);
			phrs.setExperimentId(experimentId);
    		phrs.setVariableId(vdMap.get(phrs.getVariableName() + VariableData.VARIABLE_TYPE_HOMO));
		}
		PersonalHomoResultSetService personalHomoResultSetService = SpringContextUtils.getBean(PersonalHomoResultSetService.class);
		personalHomoResultSetService.insertList(homoList);
	}

	private List<PersonalInformation> analysisPersonalInformation(String personalInformation) {
		//样本分组
    	SampleGroupingService sampleGroupingService = SpringContextUtils.getBean(SampleGroupingService.class);
        List<SampleGrouping> sampleGroupingList = sampleGroupingService.findListByExperimentId(experimentId, true, true);

        Map<String, Long> sgMap = new HashMap<String, Long>();
        for (SampleGrouping sg : sampleGroupingList) {
            sgMap.put(sg.getGroupName(), sg.getId());
        }
        
        List<PersonalInformation> allPiList = JSON.parseArray(personalInformation, PersonalInformation.class);
        
        for (int i = 0; i < allPiList.size(); i++) {
        	PersonalInformation pi = allPiList.get(i);
        	pi.setBatchId(batchId);
        	pi.setExperimentId(experimentId);
        	pi.setGroupId(sgMap.get(pi.getGroupName()));
        }
        
        PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
        personalInformationService.insertList(allPiList);
        
        for (int i = 0; i < allPiList.size(); i++) {
        	PersonalInformation pi = allPiList.get(i);
        	logger.info("BatchTaskOfPersonalInfo analysisPersonalInformation. pid{}, personalId{}", pi.getId(), pi.getPersonalId());
        }
        
        return allPiList;
	}
	
	/*private Long analysisPersonalInformation(PersonalInformation personalInformation) {
    	if (personalInformation == null) {
    		logger.info("BatckTaskOfThousandPeople analysisPersonalInformetion str is null");
    		return null;
    	}
    	
    	//样本分组
    	SampleGroupingService sampleGroupingService = SpringContextUtils.getBean(SampleGroupingService.class);
        List<SampleGrouping> sampleGroupingList = sampleGroupingService.findListByExperimentId(experimentId, true, true);

        Map<String, Long> sgMap = new HashMap<String, Long>();
        for (SampleGrouping sg : sampleGroupingList) {
            sgMap.put(sg.getGroupName(), sg.getId());
        }
		
        personalInformation.setBatchId(batchId);
        personalInformation.setExperimentId(experimentId);
        personalInformation.setGroupId(sgMap.get(personalInformation.getGroupName()));
    	
		PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
		Long pid = personalInformationService.insert(personalInformation);
		logger.info("BatchTaskOfPersonalInfo analysisPersonalInformation. pid{}, personalId{}", pid, personalInformation.getPersonalId());
		return pid;
	}*/
	
}
