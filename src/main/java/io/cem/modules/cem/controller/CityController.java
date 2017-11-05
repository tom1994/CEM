package io.cem.modules.cem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.CityEntity;
import io.cem.modules.cem.service.CityService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 行政区域地州市信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:56:26
 */
@RestController
@RequestMapping("/cem/city")
public class CityController {
	@Autowired
	private CityService cityService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("city:list")
	public R list(String citydata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = cityService.queryTotal(map);
		}
		List<CityEntity> cityList = cityService.queryList(map);
		PageUtils pageUtil = new PageUtils(cityList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("city:info")
	public R info(@PathVariable("id") Integer id){
		CityEntity city = cityService.queryObject(id);
		
		return R.ok().put("city", city);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("city:save")
	public R save(@RequestBody CityEntity city){
		cityService.save(city);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("city:update")
	public R update(@RequestBody CityEntity city){
		cityService.update(city);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("city:delete")
	public R delete(@RequestBody Integer[] ids){
		cityService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
