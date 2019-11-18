package com.settlement.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.Date;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 部门表
    * </p>
*
* @author admin
* @since 2019-11-07
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String deptCode;

    private String deptName;

    private Integer chief;

    private Integer parentId;

    private Integer sort;

    private String enabled;

    private String remark;

    private Integer createUserId;

    private Date createTime;

    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
