package com.hl.common.vo.business;

import lombok.Data;

/**
 * @Author huangliang
 * @Date 2021/11/26 19:36
 * @Version 1.0
 * @Description
 */
@Data
public class InStockItemVO {
    private Long id;

    private String pNum;

    private String name;

    private String model;

    private String unit;

    private String imageUrl;

    private int count;
}
