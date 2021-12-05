package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.error.BusinessCodeEnum;
import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.InStockDetailVO;
import com.hl.common.vo.business.InStockItemVO;
import com.hl.common.vo.business.InStockVO;
import com.hl.common.vo.business.SupplierVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.converter.InStockConverter;
import com.hl.xinguanservice.entity.business.*;
import com.hl.xinguanservice.entity.respBean.ActiveUser;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.*;
import com.hl.xinguanservice.service.InStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
@Service
public class InStockServiceImpl extends ServiceImpl<InStockMapper, InStock> implements InStockService {

    @Autowired
    private InStockConverter inStockConverter;

    @Autowired
    private InStockMapper inStockMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InStockInfoMapper inStockInfoMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ProductStockMapper productStockMapper;
    /**
     * 入库单
     * @param pageNum
     * @param pageSize
     * @param inStockVO
     * @return
     */
    @Override
    public PageVO<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO) {
        //创建page对象
        Page<InStock> inStockPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<InStock> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        if(inStockVO.getInNum()!=null&&!"".equals(inStockVO.getInNum())){
            wrapper.like("in_num",inStockVO.getInNum());
        }
        if(inStockVO.getType()!=null){
            wrapper.eq("type",inStockVO.getType());
        }
        if(inStockVO.getStatus()!=null){
            wrapper.eq("status",inStockVO.getStatus());
        }
        if(inStockVO.getStartTime()!=null){
            wrapper.ge("create_time",inStockVO.getStartTime());
        }
        if(inStockVO.getEndTime()!=null){
            wrapper.le("create_time",inStockVO.getEndTime());
        }
        baseMapper.selectPage(inStockPage,wrapper);
        List<InStock> inStocks = inStockPage.getRecords();
        List<InStockVO> inStockVOS=inStockConverter.converterToVOList(inStocks);

        return new PageVO<>(inStockPage.getTotal(),inStockVOS);
    }


    /**
     * 物资入库
     * @param inStockVO
     */
    @Transactional
    @Override
    public void addIntoStock(InStockVO inStockVO) throws BusinessException {
        //随机生成入库单号
        String IN_STOCK_NUM = UUID.randomUUID().toString().substring(0, 32).replace("-","");
        int itemNumber=0;//记录该单的总数
        //获取商品的明细
        List<Object> products = inStockVO.getProducts();
        if(!CollectionUtils.isEmpty(products)) {
            for (Object product : products) {
                LinkedHashMap item = (LinkedHashMap) product;
                //入库数量
                int productNumber = (int) item.get("productNumber");
                //物资编号
                String productId = item.get("productId").toString();
                Product dbProduct = productMapper.selectById(productId);
                if (dbProduct == null) {
                    throw new BusinessException(BusinessCodeEnum.PRODUCT_NOT_FOUND);
                }else if(dbProduct.getStatus()==1) {
                    throw new BusinessException(BusinessCodeEnum.PRODUCT_IS_REMOVE, dbProduct.getName() + "物资已被回收,无法入库");
                } else if(dbProduct.getStatus()==2){
                    throw new BusinessException(BusinessCodeEnum.PRODUCT_WAIT_PASS, dbProduct.getName() + "物资待审核,无法入库");
                }else if(productNumber<=0){
                    throw new BusinessException(BusinessCodeEnum.PRODUCT_IN_STOCK_NUMBER_ERROR,dbProduct.getName()+"入库数量不合法,无法入库");
                } else {
                    itemNumber += productNumber;
                    //插入入库单明细
                    InStockInfo inStockInfo = new InStockInfo();
                    inStockInfo.setCreateTime(new Date());
                    inStockInfo.setModifiedTime(new Date());
                    inStockInfo.setProductNumber(productNumber);
                    inStockInfo.setPNum(dbProduct.getPNum());
                    inStockInfo.setInNum(IN_STOCK_NUM);
                    inStockInfoMapper.insert(inStockInfo);
                }
            }

            InStock inStock = new InStock();
            BeanUtils.copyProperties(inStockVO,inStock);
            inStock.setCreateTime(new Date());
            inStock.setModified(new Date());
            inStock.setProductNumber(itemNumber);
            ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
            inStock.setOperator(activeUser.getUser().getUsername());
            //生成入库单
            inStock.setInNum(IN_STOCK_NUM);
            //设置为待审核
            inStock.setStatus(2);
            inStockMapper.insert(inStock);
        }else {
            throw new BusinessException(BusinessCodeEnum.PRODUCT_IN_STOCK_EMPTY);
        }
    }

    /**
     * 物资入库审核
     * @param id
     */
    @Override
    public void publish(Long id) throws BusinessException {
        InStock inStock = inStockMapper.selectById(id);
        Supplier supplier = supplierMapper.selectById(inStock.getSupplierId());
        if(inStock==null){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单不存在");
        }
        if(inStock.getStatus()!=2){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单状态错误");
        }
        if(supplier==null){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库来源信息错误");
        }
        String inNum = inStock.getInNum();//单号
        QueryWrapper<InStockInfo> inStockInfoQueryWrapper = new QueryWrapper<>();
        inStockInfoQueryWrapper.eq("in_num",inNum);
        List<InStockInfo> infoList = inStockInfoMapper.selectList(inStockInfoQueryWrapper);
        if(!CollectionUtils.isEmpty(infoList)){
            for (InStockInfo inStockInfo : infoList) {
                String pNum = inStockInfo.getPNum();//物资编号
                Integer productNumber = inStockInfo.getProductNumber();//入库物资数
                QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("p_num",pNum);
                List<Product> products = productMapper.selectList(queryWrapper);
                if(products.size()>0){
                    Product product = products.get(0);
                    //入库如果存在，就增加数量，否则插入
                    QueryWrapper<ProductStock> productStockQueryWrapper = new QueryWrapper<>();
                    productStockQueryWrapper.eq("p_num",product.getPNum());
                    List<ProductStock> productStocks = productStockMapper.selectList(productStockQueryWrapper);
                    if(!CollectionUtils.isEmpty(productStocks)){
                        //更新数量
                        ProductStock productStock = productStocks.get(0);
                        productStock.setStock(productStock.getStock()+productNumber);
                        productStockMapper.updateById(productStock);
                    }else {
                        //插入
                        ProductStock productStock = new ProductStock();
                        productStock.setPNum(product.getPNum());
                        productStock.setStock((long) productNumber);
                        productStockMapper.insert(productStock);
                    }
                    //修改入库单状态.
                    inStock.setCreateTime(new Date());
                    inStock.setStatus(0);
                    inStockMapper.updateById(inStock);
                }else {
                    throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"物资编号为:["+pNum+"]的物资不存在");
                }
            }
        }else {
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库的明细不能为空");
        }
    }

    /**
     * 入库单明细
     * @param id
     * @return
     */
    @Override
    public InStockDetailVO detail(Long id, int pageNum, int pageSize) throws BusinessException {
        InStockDetailVO inStockDetailVO = new InStockDetailVO();
        InStock inStock = inStockMapper.selectById(id);
        if(inStock==null){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单不存在");
        }
        BeanUtils.copyProperties(inStock,inStockDetailVO);
        Supplier supplier = supplierMapper.selectById(inStock.getSupplierId());
        if(supplier==null){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"提供物资方不存在,或已被删除");
        }
        SupplierVO supplierVO = new SupplierVO();
        BeanUtils.copyProperties(supplier,supplierVO);
        inStockDetailVO.setSupplierVO(supplierVO);
        String inNum = inStock.getInNum();//入库单号
        //查询该单所有的物资
        QueryWrapper<InStockInfo> inStockInfoQueryWrapper = new QueryWrapper<>();
        Page<InStockInfo> inStockInfoPage = new Page<>();
        inStockInfoQueryWrapper.eq("in_num",inNum);
        List<InStockInfo> inStockInfoList = inStockInfoMapper.selectList(inStockInfoQueryWrapper);
        inStockInfoMapper.selectPage(inStockInfoPage,inStockInfoQueryWrapper);
        inStockDetailVO.setTotal(inStockInfoPage.getTotal());
        if(!CollectionUtils.isEmpty(inStockInfoList)){
            for (InStockInfo inStockInfo : inStockInfoList) {
                String pNum = inStockInfo.getPNum();
                //查出物资
                QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("p_num",pNum);
                List<Product> products = productMapper.selectList(queryWrapper);
                if(!CollectionUtils.isEmpty(products)){
                    Product product = products.get(0);
                    InStockItemVO inStockItemVO = new InStockItemVO();
                    BeanUtils.copyProperties(product,inStockItemVO);
                    inStockItemVO.setCount(inStockInfo.getProductNumber());
                    inStockDetailVO.getItemVOS().add(inStockItemVO);
                }else {
                    throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"编号为:["+pNum+"]的物资找不到,或已被删除");
                }
            }
        }else {
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库编号为:["+inNum+"]的明细找不到,或已被删除");
        }
        return inStockDetailVO;
    }

    @Override
    public void delete(Long id) throws BusinessException {
        InStock in = new InStock();
        in.setId(id);
        InStock inStock = inStockMapper.selectById(in);
        //只有处于回收站,或者待审核的情况下可删除
        if(inStock==null){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单不存在");
        }else if(inStock.getStatus()!=1&&inStock.getStatus()!=2){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单状态错误,无法删除");
        }else {
            inStockMapper.deleteById(id);
        }
        String inNum = inStock.getInNum();//单号
        QueryWrapper<InStockInfo> inStockInfoQueryWrapper = new QueryWrapper<>();
        inStockInfoQueryWrapper.eq("in_num",inNum);
        inStockInfoMapper.delete(inStockInfoQueryWrapper);
    }

    /**
     * 从回收站恢复数据
     * @param id
     */
    @Override
    public void back(Long id) throws BusinessException {
        InStock t = new InStock();
        t.setId(id);
        InStock inStock = inStockMapper.selectById(t);
        if(inStock.getStatus()!=1){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单状态不正确");
        }else {
            t.setStatus(0);
            inStockMapper.updateById(t);
        }
    }

    /**
     * 移入回收站
     * @param id
     */
    @Override
    public void remove(Long id) throws BusinessException {
        InStock inStock = inStockMapper.selectById(id);
        if(inStock==null){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单不存在");
        }
        Integer status = inStock.getStatus();
        //只有status=0,正常的情况下,才可移入回收站
        if(status!=0){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"入库单状态不正确");
        }else {
            InStock in = new InStock();
            in.setStatus(1);
            in.setId(id);
            inStockMapper.updateById(in);
        }
    }
}
