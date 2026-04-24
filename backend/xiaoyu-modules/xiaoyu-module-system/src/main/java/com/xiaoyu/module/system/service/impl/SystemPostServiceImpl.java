package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemPost;
import com.xiaoyu.module.system.mapper.SystemPostMapper;
import com.xiaoyu.module.system.service.SystemPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemPostServiceImpl extends ServiceImpl<SystemPostMapper, SystemPost> implements SystemPostService {

    private final SystemPostMapper systemPostMapper;

    @Override
    public List<SystemPost> getPostPage(Integer pageNum, Integer pageSize, String postName, Integer status) {
        QueryWrapper q = QueryWrapper.create()
                .orderBy("post_sort", true);
        if (postName != null && !postName.isEmpty()) {
            q.and("post_name like", "%" + postName + "%");
        }
        if (status != null) {
            q.and("status", status);
        }
        return systemPostMapper.selectListByQuery(q);
    }

    @Override
    public SystemPost getPostById(Long postId) {
        SystemPost post = systemPostMapper.selectOneById(postId);
        XiaoYuThrowUtil.throwIfNull(post, "岗位不存在");
        return post;
    }

    @Override
    public List<SystemPost> getAllPosts() {
        QueryWrapper q = QueryWrapper.create().orderBy("post_sort", true);
        return systemPostMapper.selectListByQuery(q);
    }

    @Override
    @Transactional
    public Long createPost(SystemPost post) {
        XiaoYuThrowUtil.throwIfFalse(checkPostCodeUnique(post.getPostCode(), null),
                "岗位编码已存在");
        XiaoYuThrowUtil.throwIfFalse(checkPostNameUnique(post.getPostName(), null), "岗位名称已存在");

        systemPostMapper.insert(post);
        log.info("新增岗位: {}，ID: {}", post.getPostName(), post.getId());
        return post.getId();
    }

    @Override
    @Transactional
    public void updatePost(SystemPost post) {
        SystemPost existing = systemPostMapper.selectOneById(post.getId());
        XiaoYuThrowUtil.throwIfNull(existing, "岗位不存在");

        XiaoYuThrowUtil.throwIfFalse(checkPostCodeUnique(post.getPostCode(), post.getId()),
                "岗位编码已存在");
        XiaoYuThrowUtil.throwIfFalse(checkPostNameUnique(post.getPostName(), post.getId()), "岗位名称已存在");

        systemPostMapper.update(post);
        log.info("修改岗位: {}，ID: {}", post.getPostName(), post.getId());
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        SystemPost post = systemPostMapper.selectOneById(postId);
        XiaoYuThrowUtil.throwIfNull(post, "岗位不存在");

        systemPostMapper.deleteById(postId);
        log.info("删除岗位: {}，ID: {}", post.getPostName(), postId);
    }

    @Override
    public boolean checkPostCodeUnique(String postCode, Long postId) {
        if (postCode == null || postCode.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create().where("post_code", postCode);
        List<SystemPost> list = systemPostMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (postId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(postId);
    }

    @Override
    public boolean checkPostNameUnique(String postName, Long postId) {
        if (postName == null || postName.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create().where("post_name", postName);
        List<SystemPost> list = systemPostMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (postId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(postId);
    }
}