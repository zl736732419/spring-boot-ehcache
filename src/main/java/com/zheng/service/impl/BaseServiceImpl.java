package com.zheng.service.impl;

import com.google.common.collect.Lists;
import com.zheng.service.BaseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhenglian on 2016/9/24.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected abstract CrudRepository<T, Serializable> getRepository();

    public static final String CACHE_KEY = "'demoInfo'";
    public static final String CACHE_NAME = "demo";

    @Override
    public void test() {
        //TODO test ehcache enable
    }

    @Cacheable(value = CACHE_NAME, key = "'demoInfo_'+#id")
    @Override
    public T get(Serializable id) {
        System.out.println("从数据库中获取" + id + "对应的用户......");
        return getRepository().findOne(id);
    }

    @CachePut(value = CACHE_NAME, key = "'demoInfo_'+#t.getId()")
    @Override
    public void update(T t) {
        getRepository().save(t);
    }

    @CacheEvict(value = CACHE_NAME, key = CACHE_KEY)
    @Override
    public void save(T t) {
        getRepository().save(t);
    }

    @CacheEvict(value = CACHE_NAME, key = "'demoInfo_'+#id")
    @Override
    public void delete(Serializable id) {
        getRepository().delete(id);
    }

    @Override
    public List<T> findAll() {
        return Lists.newArrayList(getRepository().findAll());
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    @Override
    public void clearCache() {
        System.out.println("清空缓存");
    }
}
