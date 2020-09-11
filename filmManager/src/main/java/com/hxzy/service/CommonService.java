package com.hxzy.service;

import java.util.List;

/**
 * @author nick
 * @description
 * @date create in 2020/9/10
 */
public interface CommonService<T,ID> {
    /**
     * 插入数据
     * @param t
     * @return
     */
    int save(T t);

    /**
     * 修改
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 根据主键删除数据
     * @param id
     * @return
     */
    int remove(ID id);

    /**
     * 全查
     * @return
     */
    List<T> queryAll();

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T findById(ID id);
}
