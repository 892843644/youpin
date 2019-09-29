package com.youpin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author ：cjy
 * @description ：异常枚举
 * @CreateTime ：Created in 2019/9/6 20:36
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum {

    BRAND_NOT_FOUND(404,"品牌不存在"),
    CATEGORY_NOT_FOUND(404,"商品分类没查到"),
    GOODS_NOT_FOUND(404,"商品没查到"),
    SPEC_GROUP_NOT_FOUND(404,"商品规格组没查到"),
    SPEC_PARAM_NOT_FOUND(404,"商品参数名没查到"),
    SPU_DETAIL_NOT_FOUND(404,"商品细节没查到"),
    GOODS_SKU_NOT_FOUND(404,"商品SKU没查到"),
    BRAND_SAVE_ERROR(500,"品牌保存失败"),
    BRAND_UPDATE_ERROR(500,"品牌更新失败"),
    BRAND_DELETE_ERROR(500,"品牌删除失败"),
    SPEC_GROUP_SAVE_ERROR(500,"商品规格组保存失败"),
    SPEC_GROUP_UPDATE_ERROR(500,"商品规格组更新失败"),
    SPEC_GROUP_DELETE_ERROR(500,"商品规格组删除失败"),
    SPEC_PARAM_UPDATE_ERROR(500,"商品参数更新失败"),
    SPEC_PARAM_SAVE_ERROR(500,"商品参数保存失败"),
    SPEC_PARAM_DELETE_ERROR(500,"商品参数删除失败"),
    GOODS_SAVE_ERROR(500,"新增商品失败"),
    GOODS_UPDATE_ERROR(500,"商品更新失败"),
    GOODS_ID_NOT_NULL(400,"商品id不能为空")
    ;

    private int code;
    private String msg;

}
