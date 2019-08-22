package com.pinyg.sellergoods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import com.pinyg.sellergoods.entity.PageResult;
import com.pinyg.sellergoods.mapper.TbBrandMapper;
import com.pinyg.sellergoods.pojo.TbBrand;
import com.pinyg.sellergoods.pojo.TbBrandExample;
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


	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		Page<TbBrand> page=   (Page<TbBrand>) tbBrandMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public boolean add(TbBrand tbBrand) {
		return tbBrandMapper.insertSelective(tbBrand)>0;
	}

	@Override
	public TbBrand findOne(long id) {
		return tbBrandMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateById(TbBrand brand) {
		return tbBrandMapper.updateByPrimaryKey(brand)>0;
	}

	@Override
	public void deleteById(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			tbBrandMapper.deleteByPrimaryKey(ids[i]);
		}
	}

	@Override
	public PageResult findPageByBrand(TbBrand brand, int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		TbBrandExample example=new TbBrandExample();
		TbBrandExample.Criteria criteria = example.createCriteria();
		if(brand!=null){
			if(brand.getName()!=null && brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null && brand.getFirstChar().length()>0){
				criteria.andFirstCharEqualTo(brand.getFirstChar());
			}
		}
		Page<TbBrand> page=   (Page<TbBrand>) tbBrandMapper.selectByExample(example);
		 return new PageResult(page.getTotal(), page.getResult());
	}
}
