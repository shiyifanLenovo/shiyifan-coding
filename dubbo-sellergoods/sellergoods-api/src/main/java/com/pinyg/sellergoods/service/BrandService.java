package com.pinyg.sellergoods.service;

import com.pinyg.sellergoods.entity.PageResult;
import com.pinyg.sellergoods.pojo.TbBrand;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserRequest;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserResponse;

import java.util.List;
import java.util.Map;

public interface BrandService {

	List<TbBrand>  findAll();


	PageResult findPage(int pageNum,int pageSize);

	boolean  add(TbBrand tbBrand);

	TbBrand findOne(long id);

	boolean updateById(TbBrand brand);

	void deleteById(Long[] id);


	PageResult  findPageByBrand(TbBrand brand, int pageNum,int pageSize);

	List<Map> selectOptionList();


	HystrixCollapserResponse findHystrix(HystrixCollapserRequest request);
}
