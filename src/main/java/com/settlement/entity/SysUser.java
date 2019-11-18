package com.settlement.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.Date;
    import java.util.List;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户表
    * </p>
*
* @author admin
* @since 2019-11-07
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String email;

    private String password;

    private String employeeNo;

    private String realName;

    private String city;

    private String mobile;

    private String enabled;

    private Integer createUserId;

    private Date createTime;

    private Integer deptId;

    private String delFlag;

    private String salt;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getCredentialsSalt() {
        return this.email + this.salt;
    }

    public SysUser() {
    }

    public SysUser(Integer id, String email, String password, String employeeNo, String realName, String city, String mobile, String enabled, Integer createUserId, Date createTime, Integer deptId, String delFlag, String salt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.employeeNo = employeeNo;
        this.realName = realName;
        this.city = city;
        this.mobile = mobile;
        this.enabled = enabled;
        this.createUserId = createUserId;
        this.createTime = createTime;
        this.deptId = deptId;
        this.delFlag = delFlag;
        this.salt = salt;
    }
}
