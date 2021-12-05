package com.hl.common.vo.business;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/26 22:46
 * @Version 1.0
 * @Description
 */
@Data
public class OutStockDetailVO {
    private String outNum;

    private Integer status;

    private Integer type;

    private String operator;

    private ConsumerVO consumerVO;

    private long total;/** 总数**/

    private List<OutStockItemVO> itemVOS=new ArrayList<>();
}
