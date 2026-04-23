package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemPost;

import java.util.List;

/**
 * 岗位Service接口
 */
public interface SystemPostService extends IService<SystemPost> {

    /**
     * 获取岗位分页列表
     */
    List<SystemPost> getPostPage(Integer pageNum, Integer pageSize, String postName, Integer status);

    /**
     * 根据ID获取岗位
     */
    SystemPost getPostById(Long postId);

    /**
     * 获取所有岗位列表
     */
    List<SystemPost> getAllPosts();

    /**
     * 新增岗位
     */
    Long createPost(SystemPost post);

    /**
     * 修改岗位
     */
    void updatePost(SystemPost post);

    /**
     * 删除岗位
     */
    void deletePost(Long postId);

    /**
     * 检查岗位编码是否唯一
     */
    boolean checkPostCodeUnique(String postCode, Long postId);

    /**
     * 检查岗位名称是否唯一
     */
    boolean checkPostNameUnique(String postName, Long postId);
}