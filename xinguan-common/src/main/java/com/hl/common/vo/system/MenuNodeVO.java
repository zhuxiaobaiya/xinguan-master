package com.hl.common.vo.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/17 9:33
 * @Version 1.0
 * @Description
 */
@Data
public class MenuNodeVO {

    private Long id;

    private Long parentId;

    private String menuName;

    private String url=null;

    private String icon;

    private Long orderNum;

    private Integer open;

    private boolean disabled;

    private String perms;

    private Integer type;


    private List<MenuNodeVO> children=new ArrayList<>();

    /*
     * 排序,根据order排序
     */
    public static Comparator<MenuNodeVO> order(){
        Comparator<MenuNodeVO> comparator = (o1, o2) -> {
            if(o1.getOrderNum() != o2.getOrderNum()){
                return (int) (o1.getOrderNum() - o2.getOrderNum());
            }
            return 0;
        };
        return comparator;
    }

}
