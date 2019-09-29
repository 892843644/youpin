package com.youpin.item.api;

import com.youpin.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecificationApi {

    /**
     * 查询规格参数名
     * @param gid
     * @param cid
     * @param searching
     * @return
     */
    @GetMapping("/spec/params")
    List<SpecParam> queryParamByGid(@RequestParam(value = "gid",required = false)Long gid,
                                    @RequestParam(value = "cid",required = false)Long cid,
                                    @RequestParam(value = "searching",required = false)Boolean searching);
}
