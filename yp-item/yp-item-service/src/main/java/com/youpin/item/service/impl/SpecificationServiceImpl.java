package com.youpin.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youpin.common.enums.ExceptionEnum;
import com.youpin.common.exception.YpException;
import com.youpin.item.mapper.SpecGroupMapper;
import com.youpin.item.mapper.SpecParamMapper;
import com.youpin.item.pojo.SpecGroup;
import com.youpin.item.pojo.SpecParam;
import com.youpin.item.service.SpecificationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;


/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/9/10 9:13
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> queryGroupByCategoryId(Long cid) {
        QueryWrapper<SpecGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", cid);
        List<SpecGroup> list = specGroupMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            throw new YpException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }

    /**
     * 查询规格参数名
     * @param gid 规格组id
     * @param cid 分类id
     * @param searching 是否是搜索字段
     * @return
     */
    @Override
    public List<SpecParam> queryParamList(Long gid ,Long cid, Boolean searching) {
        QueryWrapper<SpecParam> queryWrapper = new QueryWrapper<>();
        if(gid!=null){
            queryWrapper.eq("group_id", gid);
        }
        if(cid!=null){
            queryWrapper.eq("cid",cid);
        }
        if(searching!=null){
            queryWrapper.eq("searching",searching);
        }
        List<SpecParam> list = specParamMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){//为真抛出异常
            throw new YpException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }

    @Override
    public void saveOrUpdateGroup(SpecGroup specGroup) {
        int count = 0;
        if(specGroup.getId()!=null){//不是空为修改操作
             count = specGroupMapper.updateById(specGroup);
             if(count==0){
                 throw new YpException(ExceptionEnum.SPEC_GROUP_UPDATE_ERROR);
             }
        }else {
            //保存操作
            count = specGroupMapper.insert(specGroup);
            if(count==0){
                throw new YpException(ExceptionEnum.SPEC_GROUP_SAVE_ERROR);
            }
        }

    }

    @Override
    public void deleteGroup(Long id) {
        int count = specGroupMapper.deleteById(id);
        if(count==0){
            throw new YpException(ExceptionEnum.SPEC_GROUP_DELETE_ERROR);
        }
    }

    @Override
    public void saveOrUpdateParam(SpecParam specParam) {
        int count = 0;
        if(specParam.getId()!=null){//不是空为修改操作
            count = specParamMapper.updateById(specParam);
            if(count==0){
                throw new YpException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
            }
        }else {
            //保存操作
            count = specParamMapper.insert(specParam);
            if(count==0){
                throw new YpException(ExceptionEnum.SPEC_PARAM_SAVE_ERROR);
            }
        }
    }

    @Override
    public void deleteParam(Long id) {
        int count = specParamMapper.deleteById(id);
        if(count==0){
            throw new YpException(ExceptionEnum.SPEC_PARAM_DELETE_ERROR);
        }
    }


}
