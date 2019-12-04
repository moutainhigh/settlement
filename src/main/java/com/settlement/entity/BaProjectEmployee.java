package com.settlement.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @author admin
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaProjectEmployee extends Model<BaProjectEmployee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 员工编号
     */
    private String code;

    /**
     * 入场时间
     */
    private LocalDate entranceTime;

    /**
     * 证件号码
     */
    private String cardNo;

    /**
     * 员工所在地
     */
    private String place;

    /**
     * 客户负责人
     */
    private String responsiblePerson;

    /**
     * 客户负责人邮件地址
     */
    private String rpEmail;

    /**
     * 发送抄送人邮件地址
     */
    private String sendCopyEmail;

    /**
     * 岗位
     */
    private String position;

    /**
     * 级别
     */
    private String posLevel;

    /**
     * 单价月
     */
    private BigDecimal priceMonth;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价天
     */
    private BigDecimal priceDay;

    /**
     * 报销接口人
     */
    private String claimExpensePerson;

    /**
     * 考勤负责人
     */
    private String workAttendencePerson;

    /**
     * 员工月薪
     */
    private BigDecimal salaryMonth;

    /**
     * 员工日薪
     */
    private BigDecimal salaryDay;

    /**
     * 上传客户rate邮件
     */
    private String uploadRateEmail;

    /**
     * rate邮件文件名称
     */
    private String rateEmailFilename;

    /**
     * 提交状态： S-提交，N：未提交
     */
    private String subStatus;

    /**
     * 项目组ID
     */
    private Integer pgId;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 入场状态
     */
    private String enterStatus;

    /**
     * 删除标志
     */
    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}