package com.hl.xinguanservice.converter;

import com.hl.common.vo.business.ConsumerVO;
import com.hl.xinguanservice.entity.business.Consumer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/28 14:06
 * @Version 1.0
 * @Description
 */
public class ConsumerConverter {
    /**
     * 转voList
     * @param consumers
     * @return
     */
    public static List<ConsumerVO> converterToVOList(List<Consumer> consumers) {
        List<ConsumerVO> consumerVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(consumers)){
            for (Consumer consumer : consumers) {
                ConsumerVO consumerVO = converterToConsumerVO(consumer);
                consumerVOS.add(consumerVO);
            }
        }
        return consumerVOS;
    }


    /***
     * 转VO
     * @param consumers
     * @return
     */
    public static ConsumerVO converterToConsumerVO(Consumer consumers) {
        ConsumerVO consumerVO = new ConsumerVO();
        BeanUtils.copyProperties(consumers,consumerVO);
        return consumerVO;
    }
}
