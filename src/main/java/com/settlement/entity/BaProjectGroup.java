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
 * 项目表
 * </p>
 *
 * @author admin
 * @since 2019-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaProjectGroup extends Model<BaProjectGroup> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String pgName;

    private String code;

    private Integer customerId;

    private String checkStatus;

    private String enabled;

    private Integer createUserId;

    private Integer ownerUserId;

    private Date createTime;

    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
