package com.shiyifan.service;

import com.shiyifan.entity.DubboInterfaceInfo;

import java.util.List;

/**
 * (DubboInterfaceInfo)表服务接口
 * @since 2020-03-27 10:27:07
 */
public interface DubboInterfaceInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DubboInterfaceInfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DubboInterfaceInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dubboInterfaceInfo 实例对象
     * @return 实例对象
     */
    DubboInterfaceInfo insert(DubboInterfaceInfo dubboInterfaceInfo);

    /**
     * 修改数据
     *
     * @param dubboInterfaceInfo 实例对象
     * @return 实例对象
     */
    DubboInterfaceInfo update(DubboInterfaceInfo dubboInterfaceInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}