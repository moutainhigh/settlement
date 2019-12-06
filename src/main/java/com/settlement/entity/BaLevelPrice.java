package com.settlement.entity;

import java.math.BigDecimal;
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
 * 级别单价表
 * </p>
 *
 * @author admin
 * @since 2019-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaLevelPrice extends Model<BaLevelPrice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String levelName;

    private BigDecimal price;

    private String unit;

    private String city;

    private Integer sort;

    private Integer createUserId;

    private Date createTime;

    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
