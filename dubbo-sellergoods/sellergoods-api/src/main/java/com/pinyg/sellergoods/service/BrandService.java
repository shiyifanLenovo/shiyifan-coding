package com.pinyg.sellergoods.service;

import com.pinyg.sellergoods.entity.PageResult;
import com.pinyg.sellergoods.pojo.TbBrand;

import java.util.List;

public interface BrandService {

	List<TbBrand>  findAll();


	PageResult findPage(int pageNum,int pageSize);
}
