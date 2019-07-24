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
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GeneralUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int id;

    private String userName;

    private String pwd;

    private String mobile;

    private String eMail;

    private Integer pwdQuestionSecond;

    private Integer pwdAnsFirst;

    private Integer pwdAnsSecond;

    private Integer pwdQuestionFirst;

    private String updateTime;

    private String createTime;

    private Integer isDeleted;

    private String trueName;

    private Integer cardCategory;

    private String cardId;


}
