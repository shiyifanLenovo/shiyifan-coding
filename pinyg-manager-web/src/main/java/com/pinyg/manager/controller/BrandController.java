package com.pinyg.manager.controller;

import com.alibaba.fastjson.JSON;
import com.pinyg.sellergoods.entity.PageResult;
import com.pinyg.sellergoods.entity.Result;
import com.pinyg.sellergoods.pojo.TbBrand;
import com.pinyg.sellergoods.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/brand")
public class BrandController {


	private static final Logger LOGGER=LoggerFactory.getLogger(BrandController.class);


	@Autowired(required = true)
	private BrandService brandService;


	@RequestMapping(value = "/findAll")
	public List<TbBrand> findAll(String name){
		return brandService.findAll();
	}

	public String reliable(long id) {
		LOGGER.error("发生了异常===============================");
		return "hystrix fallback value-------";
	}

	@RequestMapping("/findPage")
	public PageResult findPage(int page,int rows){
		return brandService.findPage(page, rows);
	}

	@RequestMapping(value = "/add",method =RequestMethod.POST)
	public Result add(@RequestBody TbBrand tbBrand){
		try {
			if(brandService.add(tbBrand)){
				return 	new Result(true,"新增成功");
			}
		}catch (Exception e){
			LOGGER.error("新增品牌",e);
		}
		return 	new Result(false,"新增失败");
	}

	@RequestMapping("/findOne")
	public TbBrand findOne(long id){
		TbBrand one = brandService.findOne(id);
		LOGGER.info("findOne response ==================="+one);
		return one;
	}

	@RequestMapping("/update")
 	public Result update(@RequestBody TbBrand tbBrand){
		try {
			if(brandService.updateById(tbBrand)){
				return 	new Result(true,"更新成功");
			}
		}catch (Exception e){
			LOGGER.error("更新品牌",e);
		}
		return 	new Result(false,"更新失败");
	}

	@RequestMapping("/delete")
	public Result delete(Long[] ids){
		try {
			brandService.deleteById(ids);
			return 	new Result(true,"删除成功");
		}catch (Exception e){
			LOGGER.error("删除品牌",e);
		}
		return 	new Result(false,"删除失败");
	}

	//查询
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand brand, int page, int rows){
		return brandService.findPageByBrand(brand, page, rows);
	}


	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList(){


		return brandService.selectOptionList();
	}

}
