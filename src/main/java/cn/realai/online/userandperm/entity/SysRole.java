package cn.realai.online.userandperm.entity;

/**
 * 系统角色
 */
public class SysRole {

    //id
    private Long id;
    //角色名称
    private String name;
    //创建时间
    private Long createTime;
    //创建用户ID
    private Long createUserId;
    //创建备注
    private String notes;

    private String createUser;


    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
