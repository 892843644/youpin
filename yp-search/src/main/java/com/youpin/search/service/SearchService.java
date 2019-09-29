package com.youpin.search.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableField;
import com.vividsolutions.jts.geom.CoordinateList;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.common.utils.JsonUtils;
import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.*;
import com.youpin.search.client.BrandClient;
import com.youpin.search.client.CategoryClient;
import com.youpin.search.client.GoodsClient;
import com.youpin.search.client.SpecificationClient;
import com.youpin.search.dto.SkuDTO;
import com.youpin.search.pojo.Goods;
import com.youpin.search.repository.GoodsRepository;
import com.youpin.search.request.SearchRequest;
import com.youpin.search.vo.SearchResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/23 10:49
 */
@Service
@Transactional
@Slf4j
public class SearchService {
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    public  PageResult<Goods> search(SearchRequest request) {
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //0.构造分页
        queryBuilder.withPageable(PageRequest.of(request.getPage()-1,request.getSize()));
        //1.构造查询条件
        QueryBuilder basicQuery = QueryBuilders.matchQuery("all", request.getKey());
        queryBuilder.withQuery(basicQuery);
        //2.1聚合品牌
        String brandAggName="brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId").size(20));
        //2.2聚合分类
        String categoryAggName="category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        //3.结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        //4.查询
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

        //5解析结果
        //5.1解析分页结果
        long totalPage = result.getTotalPages();
        long total = result.getTotalElements();
        List<Goods> goodsList = result.getContent();
        //5.2解析聚合结果
        Aggregations aggregations = result.getAggregations();
        List<Brand> brands = parseBrandAgg(aggregations.get(brandAggName));
        List<Category> categories = parseCategoryAgg(aggregations.get(categoryAggName));
        //6.完成规格参数聚合
        List<Map<String,Object>> specs=null;
        if(categories.size()==1&&categories!=null){
            //只有一个分类是，才可以聚合规格参数
            specs=buildSpecificationAgg(categories.get(0).getId(),basicQuery);
        }
        return new SearchResult(total,totalPage,goodsList,categories,brands,specs);
    }

    private List<Map<String, Object>> buildSpecificationAgg(Long cid, QueryBuilder basicQuery) {
        List<Map<String,Object>> specs= new ArrayList<>();
        //查询要聚合的是搜索字段的规格参数
        List<SpecParam> specParams = specificationClient.queryParamByGid(null, cid, true);
        //创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加查询条件
        queryBuilder.withQuery(basicQuery);
        //添加聚合条件
        for (SpecParam specParam:specParams){ //聚合是搜索字段的规格参数
            queryBuilder.addAggregation(AggregationBuilders.terms(specParam.getName()).field("specs."+specParam.getName()+".keyword"));
        }
        //*****查询*****
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);
        //解析结果
        Aggregations aggregations = result.getAggregations();
        for(SpecParam specParam:specParams){
            Map<String,Object> map = new HashMap<>();
            StringTerms terms = aggregations.get(specParam.getName());
            List<String> options = terms.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
            //规格参数名称
            map.put("k",specParam.getName());
            //待选项
            map.put("options",options);
            specs.add(map);
        }
        return specs;
    }

    /**
     * 通过ids查询category集合
     * @param terms
     * @return
     */
    private List<Category> parseCategoryAgg(LongTerms terms) {

        try{
            List<Long> ids = terms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
            log.info("[ids]:{}",ids);
            List<Category> categories = categoryClient.queryCategoryByIds(ids);
            return categories;
        }catch (Exception e){
            log.error("【搜索服务】 查询分类异常:",e);
            return null;
        }
    }

     /**
     * 通过ids查询brand集合
     * @param terms
     * @return
     */
    private List<Brand> parseBrandAgg(LongTerms terms) {
        try {
            List<Long> ids = terms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Brand> brands = brandClient.queryBrandByIds(ids);
            return brands;
        }catch (Exception e){
            log.error("【搜索服务】 查询品牌异常:",e);
            return null;
        }
    }

    /**
     * 同数据库同步数据到es
     * @param spu
     * @return
     */
    public Goods buildGoods(Spu spu){
        //查询分类
        List<Category> categories = categoryClient.queryCategoryByIds
                (Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if(CollectionUtils.isEmpty(categories)){
            throw new YpException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());

        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if(brand==null){
            throw new YpException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        //查询sku
        List<Sku> skus = goodsClient.querySkuBySpuId(spu.getId());
        if(CollectionUtils.isEmpty(skus)){
            throw new YpException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        //得到各sku的价格的集合
        Set<Long> prices = new HashSet<>();
        //对sku进行处理 只要部分字段
        List<SkuDTO> skuDTOS = new ArrayList<>();
        for (Sku sku:skus){
            SkuDTO skuDTO = new SkuDTO();
            BeanUtils.copyProperties(sku,skuDTO);
            skuDTO.setImage(StringUtils.substringBefore(sku.getImages(),","));
            skuDTOS.add(skuDTO);
            prices.add(sku.getPrice());
        }

        //搜素字段拼接
        String all = spu.getName()+ StringUtils.join(names," ")+brand.getName();

        //查询规格参数  规格参数名对应key
        List<SpecParam> params = specificationClient.queryParamByGid(null, spu.getCid3(), true);
        if(CollectionUtils.isEmpty(params)){
            throw new YpException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        //查询商品详情
        SpuDetail spuDetail = goodsClient.queryDetailById(spu.getId());
        //获取通用规格参数 规格参数值对应value
        Map<Long,String> genericSpec = JSONObject.parseObject(spuDetail.getGenericSpec(),new TypeReference<Map<Long, String>>(){});
//        log.info("hhhhhhhh，"+spu.getId()+"-"+JSON.toJSONString(genericSpec));
//        for (Map.Entry entry:genericSpec.entrySet()) {
//
//            String a=genericSpec.get(entry.getKey());
//            log.info(entry.getKey()+"------------"+a);
//        }
        //获取特有规格参数 规格参数值对应value
        Map specialSpec = JSONObject.parseObject(spuDetail.getSpecialSpec(),new TypeReference<Map<Long, List<String>>>(){});
        //规格参数 key是规格参数的名，value规格参数的值
        Map<String,Object> specs = new HashMap<>();
        for(SpecParam specParam:params){
            String key = specParam.getName();
            Object value =null;
            //判断是否是通用规格参数
            if(specParam.getGeneric()){
//                log.info(JSON.toJSONString(genericSpec));
                value=genericSpec.get(specParam.getId());
                //判断是否是数值类型
                if(specParam.getNumeric()){
                    //处理成数值段保存
                    value = chooseSegment(value.toString(), specParam);
                }
            }else {
                value=specialSpec.get(specParam.getId());
            }
            specs.put(key,value);
        }

        Goods goods = new Goods();
        BeanUtils.copyProperties(spu,goods);
        goods.setAll(all);// 所有需要被搜索的信息，包含标题，分类，甚至品牌
        goods.setPrice(prices);// 所有sku价格的集合
        goods.setSkus(JSONObject.toJSONString(skuDTOS));// 所有sku集合的json格式
        goods.setSpecs(specs);// 所有可搜素的规格参数
        return goods;
    }

    /**
     * 返回数值对应的数值段
     * @param value
     * @param p
     * @return
     */
    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
