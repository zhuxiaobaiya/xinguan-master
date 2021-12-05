package com.hl.xinguanservice.converter;

import com.hl.common.vo.business.OutStockVO;
import com.hl.xinguanservice.entity.business.Consumer;
import com.hl.xinguanservice.entity.business.OutStock;
import com.hl.xinguanservice.mapper.ConsumerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/26 22:14
 * @Version 1.0
 * @Description
 */
@Component
public class OutStockConverter {

    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 转voList
     * @param outStocks
     * @return
     */
    public List<OutStockVO> converterToVOList(List<OutStock> outStocks) {
        List<OutStockVO> outStockVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(outStocks)){
            for (OutStock outStock : outStocks) {
                OutStockVO outStockVO = new OutStockVO();
                BeanUtils.copyProperties(outStock,outStockVO);
                Consumer consumer = consumerMapper.selectById(outStock.getConsumerId());
                if(consumer!=null){
                    outStockVO.setName(consumer.getName());
                    outStockVO.setPhone(consumer.getPhone());
                }
                outStockVOS.add(outStockVO);
            }
        }
        return outStockVOS;
    }
}

