package com.pinyg.sellergoods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyg.sellergoods.entity.PageResult;
import com.pinyg.sellergoods.mapper.TbBrandMapper;
import com.pinyg.sellergoods.pojo.TbBrand;
import com.pinyg.sellergoods.pojo.TbBrandExample;
import com.pinyg.sellergoods.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("brandService")
public class BrandServiceImpl implements BrandService {
	private static final Logger LOGGER=LoggerFactory.getLogger(BrandServiceImpl.class);

	@Autowired
	private TbBrandMapper tbBrandMapper;


	@Override
	public List<TbBrand> findAll() {
		throw  new RuntimeException("Exception to show hystrix enabled.");
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
		LOGGER.info("findOne============================="+id);
		throw  new RuntimeException("Exception to show hystrix enabled.");
		//return tbBrandMapper.selectByPrimaryKey(id);
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

	@Override
	public List<Map> selectOptionList() {
		 return tbBrandMapper.selectOptionList();
	}
}
