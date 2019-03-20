package cn.realai.online.core.bo;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
public class PsiResultBO {

    private long psiId;

    private String name;

    private String meaning;

    private String psi;

    private int aler;

    public long getPsiId() {
        return psiId;
    }

    public void setPsiId(long psiId) {
        this.psiId = psiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPsi() {
        return psi;
    }

    public void setPsi(String psi) {
        this.psi = psi;
    }

    public int getAler() {
        return aler;
    }

    public void setAler(int aler) {
        this.aler = aler;
    }
}
