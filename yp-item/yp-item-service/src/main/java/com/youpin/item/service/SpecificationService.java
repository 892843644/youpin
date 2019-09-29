package com.youpin.item.service;

import com.youpin.item.pojo.SpecGroup;
import com.youpin.item.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {

    List<SpecGroup> queryGroupByCategoryId(Long cid);

    List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching);

    void saveOrUpdateGroup(SpecGroup specGroup);

    void deleteGroup(Long id);

    void saveOrUpdateParam(SpecParam specParam);

    void deleteParam(Long id);


}
