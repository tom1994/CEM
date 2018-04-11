package io.cem.modules.cem.controller;

import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.CountyEntity;
import io.cem.modules.cem.service.CountyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 行政区域县区信息表
 */
@RestController
@RequestMapping("/cem/county")
public class CountyController {
	@Autowired
	private CountyService countyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(String countydata, Integer page, Integer limit) throws Exception {
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
			total = countyService.queryTotal(map);
		}
		List<CountyEntity> countyList = countyService.queryList(map);
		PageUtils pageUtil = new PageUtils(countyList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}


	@RequestMapping("/infoByProbe/{id}")
	@RequiresPermissions("county:info")
	public R infoByProbe(@PathVariable("id") Integer id){
		List<CountyEntity> countyList = countyService.queryByProbe(id);
		return R.ok().put("county", countyList);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("county:info")
	public R info(@PathVariable("id") Integer id){
		List<CountyEntity> countyList = countyService.queryCountyList(id);
		System.out.println(countyList);
		return R.ok().put("county", countyList);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("county:save")
	public R save(@RequestBody CountyEntity county){
		countyService.save(county);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("county:update")
	public R update(@RequestBody CountyEntity county){
		countyService.update(county);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("county:delete")
	public R delete(@RequestBody Integer[] ids){
		countyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
