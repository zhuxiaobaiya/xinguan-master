package com.hl.common.vo.business;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/26 19:36
 * @Version 1.0
 * @Description
 */
@Data
public class InStockDetailVO {
    private String inNum;

    private Integer status;

    private Integer type;

    private String operator;

    private SupplierVO supplierVO;

    private long total;/** 总数**/

    private List<InStockItemVO> itemVOS=new ArrayList<>();
}
