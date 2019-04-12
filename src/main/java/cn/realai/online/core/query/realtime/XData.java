package cn.realai.online.core.query.realtime;

/**
 * 数据载体
 * @author lyh
 */
public class XData {

	//异质数据源
	private String hetro;
	
	//同质数据源
	private String homo;

	public String getHetro() {
		return hetro;
	}

	public void setHetro(String hetro) {
		this.hetro = hetro;
	}

	public String getHomo() {
		return homo;
	}

	public void setHomo(String homo) {
		this.homo = homo;
	}
	
}
