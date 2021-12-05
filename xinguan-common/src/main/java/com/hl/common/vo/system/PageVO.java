package com.hl.common.vo.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/23 13:43
 * @Version 1.0
 * @Description
 */
@Data
public class PageVO<T> {
    private long total;

    private List<T> rows=new ArrayList<>();

    public PageVO(long total, List<T> data) {
        this.total = total;
        this.rows = data;
    }
}
