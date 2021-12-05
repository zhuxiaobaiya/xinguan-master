package com.hl.xinguanservice.mapper;

import com.hl.common.vo.business.ProductStockVO;
import com.hl.common.vo.business.ProductVO;
import com.hl.xinguanservice.entity.business.ProductStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
public interface ProductStockMapper extends BaseMapper<ProductStock> {

    /**
     * 库存列表
     * @param productVO
     * @return
     */
    List<ProductStockVO> findProductStocks(ProductVO productVO);

    /**
     * 库存信息(饼图使用)
     * @return
     */
    List<ProductStockVO> findAllStocks(ProductVO productVO);
}
