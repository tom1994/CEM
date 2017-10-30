package io.cem.modules.cem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cem.modules.cem.entity.AreasEntity;
import io.cem.modules.cem.service.AreasService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 行政区域县区信息表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-10-27 16:11:01
 */
@RestController
@RequestMapping("/cem/areas")
public class AreasController {
	@Autowired
	private AreasService areasService;
	
	/**
	 * 列表
	 */
//	@RequestMapping("/list")
//	@RequiresPermissions("areas:list")
//	public R list(@RequestParam Map<String, Object> params){
//		//查询列表数据
//        Query query = new Query(params);
//
//		List<AreasEntity> areasList = areasService.queryList(query);
//		int total = areasService.queryTotal(query);
//
//		PageUtils pageUtil = new PageUtils(areasList, total, query.getLimit(), query.getPage());
//
//		return R.ok().put("page", pageUtil);
//	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("areas:info")
	public R info(@PathVariable("id") Integer id){
		List<AreasEntity> areasList = areasService.queryAreaList(id);
		return R.ok().put("areas", areasList);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("areas:save")
	public R save(@RequestBody AreasEntity areas){
		areasService.save(areas);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("areas:update")
	public R update(@RequestBody AreasEntity areas){
		areasService.update(areas);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("areas:delete")
	public R delete(@RequestBody Integer[] ids){
		areasService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
