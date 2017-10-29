package io.cem.modules.cem.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.CitiesEntity;
import io.cem.modules.cem.service.CitiesService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 行政区域地州市信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-10-27 16:11:02
 */
@RestController
@RequestMapping("cities")
public class CitiesController {
	@Autowired
	private CitiesService citiesService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("cities:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CitiesEntity> citiesList = citiesService.queryList(query);
		int total = citiesService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(citiesList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("cities:info")
	public R info(@PathVariable("id") Integer id){
		CitiesEntity cities = citiesService.queryObject(id);
		
		return R.ok().put("cities", cities);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("cities:save")
	public R save(@RequestBody CitiesEntity cities){
		citiesService.save(cities);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("cities:update")
	public R update(@RequestBody CitiesEntity cities){
		citiesService.update(cities);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("cities:delete")
	public R delete(@RequestBody Integer[] ids){
		citiesService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
