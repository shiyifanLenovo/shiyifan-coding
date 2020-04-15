package com.shiyifan.controller;

import com.shiyifan.entity.DubboInterfaceInfo;
import com.shiyifan.service.DubboInterfaceInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DubboInterfaceInfo)表控制层
 * @since 2020-03-27 10:32:50
 */
@RestController
@RequestMapping("/dubboInterfaceInfo")
public class DubboInterfaceInfoController {
    /**
     * 服务对象
     */
    @Resource
    private DubboInterfaceInfoService dubboInterfaceInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public DubboInterfaceInfo selectOne(Integer id) {
        return this.dubboInterfaceInfoService.queryById(id);
    }

}