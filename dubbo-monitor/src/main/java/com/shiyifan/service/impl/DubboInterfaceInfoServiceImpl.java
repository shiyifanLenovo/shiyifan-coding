package com.shiyifan.service.impl;

import com.shiyifan.entity.DubboInterfaceInfo;
import com.shiyifan.dao.DubboInterfaceInfoDao;
import com.shiyifan.service.DubboInterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DubboInterfaceInfo)表服务实现类
 * @since 2020-03-27 10:27:07
 */
@Service("dubboInterfaceInfoService")
public class DubboInterfaceInfoServiceImpl implements DubboInterfaceInfoService {
    @Resource
    private DubboInterfaceInfoDao dubboInterfaceInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DubboInterfaceInfo queryById(Integer id) {
        return this.dubboInterfaceInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<DubboInterfaceInfo> queryAllByLimit(int offset, int limit) {
        return this.dubboInterfaceInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dubboInterfaceInfo 实例对象
     * @return 实例对象
     */
    @Override
    public DubboInterfaceInfo insert(DubboInterfaceInfo dubboInterfaceInfo) {
        System.out.println("插入数据=="+dubboInterfaceInfo.toString());
        this.dubboInterfaceInfoDao.insert(dubboInterfaceInfo);

        return dubboInterfaceInfo;
    }

    /**
     * 修改数据
     *
     * @param dubboInterfaceInfo 实例对象
     * @return 实例对象
     */
    @Override
    public DubboInterfaceInfo update(DubboInterfaceInfo dubboInterfaceInfo) {
        this.dubboInterfaceInfoDao.update(dubboInterfaceInfo);
        return this.queryById(dubboInterfaceInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.dubboInterfaceInfoDao.deleteById(id) > 0;
    }
}