package com.hl.common.vo.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/26 14:34
 * @Version 1.0
 * @Description
 */
@Data
public class ProductCategoryTreeNodeVO {
    private String id;

    private String name;

    private String remark;

    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    private String pid;

    private int lev;

    private List<ProductCategoryTreeNodeVO> children;

    /*
     * 排序,根据order排序
     */
    public static Comparator<ProductCategoryTreeNodeVO> order(){
        Comparator<ProductCategoryTreeNodeVO> comparator = (o1, o2) -> {
            if(o1.getSort() != o2.getSort()){
                return (int) (o1.getSort() - o2.getSort());
            }
            return 0;
        };
        return comparator;
    }
}
