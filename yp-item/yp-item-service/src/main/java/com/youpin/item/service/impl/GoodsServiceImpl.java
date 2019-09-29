package com.youpin.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.common.vo.PageResult;
import com.youpin.item.mapper.*;
import com.youpin.item.pojo.*;
import com.youpin.item.request.SaleableRequest;
import com.youpin.item.request.SpuPageRequest;
import com.youpin.item.service.CategoryService;
import com.youpin.item.service.GoodsService;
import com.youpin.item.service.SkuService;
import com.youpin.item.service.StockService;
import com.youpin.item.vo.CategoryVo;
import com.youpin.item.vo.GoodsVo;
import com.youpin.item.vo.SpuVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.execute.Execute;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/11 10:05
 */
@Service
@Transactional
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SkuService skuService;
    @Autowired
    private StockService stockService;

    @Override
    public PageResult<SpuVo> querySpuByPage(SpuPageRequest spuPageRequest) {
        //创建分页对象
        IPage<Spu> page = new Page<>(spuPageRequest.getPage(),spuPageRequest.getRows());

        //创建对象封装操作类
        //拼接sql:SELECT * FROM tb_spu WHERE name like '%%' AND saleable=1 ORDER BY last_update_time DESC limit 0,5
        QueryWrapper<Spu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",spuPageRequest.getKey());
        if(spuPageRequest.getSaleable()!=null){
            queryWrapper.eq("saleable",spuPageRequest.getSaleable());
        }
        queryWrapper.orderByDesc("last_update_time");
        page = spuMapper.selectPage(page, queryWrapper);

        if(CollectionUtils.isEmpty(page.getRecords())){//如果查询出来的数据为空抛出异常
            throw new YpException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //得到分页加条件查询数据封装到 spuVoList
        List<Spu> spuList = page.getRecords();
        //Spu转换SpuVo
        List<SpuVo> spuVoList = spuList.stream().map(e -> convert(e)).collect(Collectors.toList());

        //把数据封装到分页结果对象中
        return new PageResult<SpuVo>(page.getTotal(),page.getPages(),spuVoList);
    }

    /**
     * 保存商品
     * @param spu
     */
    @Override
    public void saveGoods(Spu spu) {

        log.info("spu：【{}】",spu);
        //新增spu
        spu.setSaleable(true);
        spu.setValid(true);
        int count = spuMapper.insert(spu);
        if(count!=1){//保存失败
            throw new YpException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

        //新增spuDetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        //新增sku和stock
        saveSkuAndStock(spu);
    }

    @Override
    public GoodsVo queryGoodsById(Long spuId) {
        GoodsVo goodsVo = new GoodsVo();
        Spu spu = spuMapper.selectById(spuId);
        List<CategoryVo> list = new ArrayList<>();
        List<Long> ids=new ArrayList<>();
        ids.add(spu.getCid1());
        ids.add(spu.getCid2());
        ids.add(spu.getCid3());
        for(Long id:ids){
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(id);
            list.add(categoryVo);
        }
        List<Category> categoryList = categoryMapper.selectBatchIds(ids);
        Map<Long, String> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Category::getName));
        list.forEach(categoryVo -> categoryVo.setName(categoryMap.get(categoryVo.getId())));

        //查询spuDetail
        SpuDetail spuDetail = spuDetailMapper.selectById(spuId);
        if(spuDetail==null){
            throw  new YpException(ExceptionEnum.SPU_DETAIL_NOT_FOUND);
        }

        //查询skus
        QueryWrapper<Sku> qw = new QueryWrapper<>();
        qw.eq("spu_id",spuId);
        List<Sku> skus = skuMapper.selectList(qw);
        if(CollectionUtils.isEmpty(skus)){
            throw new YpException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        //获取id集合
        List<Long> ids2 = skus.stream().map(Sku::getId).collect(Collectors.toList());
        //通过ids批量查询stock
        List<Stock> stocks = stockMapper.selectBatchIds(ids2);
        if(CollectionUtils.isEmpty(stocks)){
            throw new YpException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        //把stock变成map，key是skuid，值是库存数
        Map<Long, Integer> stockMap = stocks.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        //把库存值set进skus集合中
        skus.forEach(sku -> sku.setStock(stockMap.get(sku.getId())));

        //查询商品id和子标题subTitle
        Spu spu1 = spuMapper.queryBrandIdAndSubTitleById(spuId);
        goodsVo.setCategories(list);//分类信息
        goodsVo.setBrandId(spu1.getBrandId());//查询商品id
        goodsVo.setSubTitle(spu1.getSubTitle());
        goodsVo.setSpuDetail(spuDetail);
        goodsVo.setSkus(skus);

        return goodsVo;
    }

    /**
     * 修改商品
     * @param spu
     */
    @Override
    public void updateGoods(Spu spu) {
        if(spu.getId()==null){
            throw new YpException(ExceptionEnum.GOODS_ID_NOT_NULL);
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("spu_id",spu.getId());
        //查询以前的sku
        List<Sku> skus = skuMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(skus)){
            List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
            //删除sku
            skuMapper.delete(queryWrapper);
            //批量删除stock
            stockMapper.deleteBatchIds(ids);
        }

        int count = spuMapper.updateById(spu);
        if(count!=1){
            throw new YpException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
        //修改detail
        count = spuDetailMapper.updateById(spu.getSpuDetail());
        if(count!=1){
            throw new YpException(ExceptionEnum.GOODS_UPDATE_ERROR);
        };
        //新增sku和stock
        saveSkuAndStock(spu);
    }

    @Override
    public void changeSaleable(SaleableRequest saleable) {
        Spu spu = new Spu();
        spu.setId(saleable.getId());
        spu.setSaleable(saleable.getSaleable());
        int count = spuMapper.updateById(spu);
        if(count!=1){
            throw new YpException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteGoods(Long id) {
        if(id==null){
            throw new YpException(ExceptionEnum.GOODS_ID_NOT_NULL);
        }
        //删除spu
        spuMapper.deleteById(id);
        //删除spudetail
        spuDetailMapper.deleteById(id);

        //查询以前的sku
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("spu_id",id);
        List<Sku> skus = skuMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(skus)){
            List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
            //删除sku
            skuMapper.delete(queryWrapper);
            //批量删除stock
            stockMapper.deleteBatchIds(ids);
        }

    }

    @Override
    public SpuDetail queryDetailById(Long spuId) {
        return spuDetailMapper.selectById(spuId);
    }

    @Override
    public List<Sku> querySkuBySpuId(Long id) {
        QueryWrapper<Sku> queryWrapper = new QueryWrapper();
        queryWrapper.eq("spu_id",id);
        return skuMapper.selectList(queryWrapper);
    }

    @Override
    public PageResult<Spu> querySpuByPageApi(SpuPageRequest spuPageRequest) {
        //创建分页对象
        IPage<Spu> page = new Page<>(spuPageRequest.getPage(),spuPageRequest.getRows());

        //创建对象封装操作类
        //拼接sql:SELECT * FROM tb_spu WHERE name like '%%' AND saleable=1 ORDER BY last_update_time DESC limit 0,5
        QueryWrapper<Spu> queryWrapper = new QueryWrapper<>();
        if(spuPageRequest.getKey()!=null){
            queryWrapper.like("name",spuPageRequest.getKey());
        }
        if(spuPageRequest.getSaleable()!=null){
            queryWrapper.eq("saleable",spuPageRequest.getSaleable());
        }
        queryWrapper.orderByDesc("last_update_time");
        page = spuMapper.selectPage(page, queryWrapper);

        if(CollectionUtils.isEmpty(page.getRecords())){//如果查询出来的数据为空抛出异常
            throw new YpException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //得到分页加条件查询数据封装到 spuVoList
        List<Spu> spuList = page.getRecords();

        //把数据封装到分页结果对象中
        return new PageResult<Spu>(page.getTotal(),page.getPages(),spuList);
    }

    /**
     * Spu转换SpuVo方法
     * @param spu
     * @return
     */
    private SpuVo convert(Spu spu){

        //查询品牌名
        Brand brand = brandMapper.selectById(spu.getBrandId());

        //查询一级二级三级分类名
        List<String> categoryNameList = categoryMapper.selectBatchIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                .stream().map(Category::getName).collect(Collectors.toList());

        SpuVo spuVo = new SpuVo();
        //把spu属性拷贝到spuVo
        BeanUtils.copyProperties(spu,spuVo);
        spuVo.setBname(brand.getName());
        spuVo.setCname(StringUtils.join(categoryNameList,"/"));
        return spuVo;
    }

    private void saveSkuAndStock(Spu spu){
        //新增sku
        List<Stock> stocks = new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        int count;
        for (Sku sku: skus) {

            sku.setSpuId(spu.getId());
            count = skuMapper.insert(sku);
            if(count!=1){//保存失败
                throw new YpException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            //新增stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        }
        //批量新增库存
        boolean flag = stockService.saveBatch(stocks);
        if(flag==false){
           //保存失败
            throw new YpException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
    }
}
