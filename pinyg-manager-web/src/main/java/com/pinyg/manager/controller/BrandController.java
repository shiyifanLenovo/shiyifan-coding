package com.pinyg.manager.controller;

import com.pinyg.sellergoods.entity.PageResult;
import com.pinyg.sellergoods.pojo.TbBrand;
import com.pinyg.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
public class BrandController {

	@Autowired(required = true)
	private BrandService brandService;

	@RequestMapping(value = "/findAll")
	public List<TbBrand> findAll(){
		return brandService.findAll();
	}

	@RequestMapping("/findPage")
	public PageResult findPage(int page,int rows){
		return brandService.findPage(page, rows);
	}
}
