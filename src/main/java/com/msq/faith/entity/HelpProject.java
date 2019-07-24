package com.msq.faith.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author msq
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HelpProject implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;

    private Double budget;

    private String description;

    private String logo;

    private Integer status;

    private Integer userId;

    private Integer immplentUserId;

    private Integer superviseUserId;

    private String country;

    private String province;

    private String city;

    private String district;

    private String street;

    private String endRaiseDate;

    private String mobile;

    private  Double fundAmount;

}
