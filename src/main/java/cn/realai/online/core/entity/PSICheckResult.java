package cn.realai.online.core.entity;

/**
 * psi检测结果
 *
 * @author lyh
 */
public class PSICheckResult {

    private long id;

    //值
    private double psi;

    //模型id
    private Long modelId;  
    
    //变量Id
    private Long variableId;
    
    //变量类型
    private int variableType;
    
    //变量名称
    private String variableName;

    private long experimentId;

    //预警标志
    private int aler; //1.不预警 2.预警
    
    public static enum STATUS {
        YES(1, "不预警"),
        NO(2, "预警");

        private int value;
        private String desc;

        STATUS(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static String getDesc(int value) {
            for (STATUS c : STATUS.values()) {
                if (c.value == value) {
                    return c.desc;
                }
            }
            return null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPsi() {
        return psi;
    }

    public void setPsi(double psi) {
        this.psi = psi;
    }

    public Long getVariableId() {
		return variableId;
	}

	public void setVariableId(Long variableId) {
		this.variableId = variableId;
	}

	public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public int getAler() {
        return aler;
    }

    public void setAler(int aler) {
        this.aler = aler;
    }

	public int getVariableType() {
		return variableType;
	}

	public void setVariableType(int variableType) {
		this.variableType = variableType;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
    
}
