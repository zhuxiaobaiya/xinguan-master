package com.hl.common.vo.business;

import lombok.Data;

/**
 * @Author huangliang
 * @Date 2021/11/27 15:27
 * @Version 1.0
 * @Description
 */
@Data
public class ProductStockVO {
    private Long id;

    private String name;

    private String pNum;

    private String model;

    private String unit;

    private String remark;

    private Long stock;

    private String imageUrl;
}
