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
 * 合同管理表
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaContract extends Model<BaContract> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String contractNo;

    private String invoiceIssuing;

    private Date startTime;

    private Date endTime;

    private String remark;

    private Integer createUserId;

    private Date createTime;

    private Integer customerId;

    private Integer baProjectGroupId;

    private String status;

    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
