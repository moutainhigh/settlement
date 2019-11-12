package com.settlement.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 功能菜单表
    * </p>
*
* @author admin
* @since 2019-11-07
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private String pCode;

    private String pName;

    private String url;

    private Integer sort;

    private String type;

    private String permission;

    private String icon;

    private Integer createUserId;

    private Date createTime;

    private String delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
