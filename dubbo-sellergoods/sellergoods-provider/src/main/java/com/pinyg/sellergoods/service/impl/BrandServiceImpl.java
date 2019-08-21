package com.pinyg.sellergoods.service.impl;

import com.pinyg.sellergoods.mapper.TbBrandMapper;
import com.pinyg.sellergoods.pojo.TbBrand;
import com.pinyg.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper tbBrandMapper;

	@Override
	public List<TbBrand> findAll() {
		return tbBrandMapper.selectByExample(null);
	}
}
