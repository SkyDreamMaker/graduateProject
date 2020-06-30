package com.haiyu.manager.dto;

import lombok.Data;

;

/**
 * @Title: AdminUserDTO
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/3 12:13
 */
@Data
public class AdminProductDTO {

    private Integer id;

    private String productId;

    private String productName;

    private String apiKey;

    private String productType;

    private String  regTime;

    private Integer productStatus;

}
