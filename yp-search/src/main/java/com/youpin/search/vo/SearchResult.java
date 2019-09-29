package com.youpin.search.vo;

import com.youpin.common.vo.PageResult;
import com.youpin.item.pojo.Brand;
import com.youpin.item.pojo.Category;
import com.youpin.search.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/25 17:18
 */
@Data
@NoArgsConstructor
public class SearchResult extends PageResult<Goods> {
    private List<Category> categories;//分类待选项
    private List<Brand> brands;//品牌待选项
    private List<Map<String,Object>> specs;// 规格参数 key及待选项

//    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Category> categories, List<Brand> brands) {
//        super(total, totalPage, items);
//        this.categories = categories;
//        this.brands = brands;
//    }

    public SearchResult(Long total, Long totalPage, List<Goods> items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
