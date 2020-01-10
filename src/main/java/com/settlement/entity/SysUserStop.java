package com.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kun
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserStop extends Model<SysUserStop> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String type;

    private String remark;

    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
