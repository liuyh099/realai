package cn.realai.online.userandperm.query;

import cn.realai.online.core.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserPageQuery extends PageQuery {

    @ApiModelProperty(value = "是否是查询忘记密码的 1:表示是查询忘记密码的用户 其他或者不传为所有用户", example = "1:表示是查询忘记密码的用户 其他为所有用户")
    private Integer forget;

    public Integer getForget() {
        return forget;
    }

    public void setForget(Integer forget) {
        this.forget = forget;
    }
}
