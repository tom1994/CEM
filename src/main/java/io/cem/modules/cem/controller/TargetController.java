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

import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.cem.service.TargetService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
@RestController
@RequestMapping("target")
public class TargetController {
	@Autowired
	private TargetService targetService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("target:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TargetEntity> targetList = targetService.queryList(query);
		int total = targetService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(targetList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("target:info")
	public R info(@PathVariable("id") Integer id){
		TargetEntity target = targetService.queryObject(id);
		
		return R.ok().put("target", target);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("target:save")
	public R save(@RequestBody TargetEntity target){
		targetService.save(target);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("target:update")
	public R update(@RequestBody TargetEntity target){
		targetService.update(target);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("target:delete")
	public R delete(@RequestBody Integer[] ids){
		targetService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
