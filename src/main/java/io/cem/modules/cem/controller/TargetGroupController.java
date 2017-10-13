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

import io.cem.modules.cem.entity.TargetGroupEntity;
import io.cem.modules.cem.service.TargetGroupService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
@RestController
@RequestMapping("targetgroup")
public class TargetGroupController {
	@Autowired
	private TargetGroupService targetGroupService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("targetgroup:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TargetGroupEntity> targetGroupList = targetGroupService.queryList(query);
		int total = targetGroupService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(targetGroupList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("targetgroup:info")
	public R info(@PathVariable("id") Integer id){
		TargetGroupEntity targetGroup = targetGroupService.queryObject(id);
		
		return R.ok().put("targetGroup", targetGroup);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("targetgroup:save")
	public R save(@RequestBody TargetGroupEntity targetGroup){
		targetGroupService.save(targetGroup);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("targetgroup:update")
	public R update(@RequestBody TargetGroupEntity targetGroup){
		targetGroupService.update(targetGroup);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("targetgroup:delete")
	public R delete(@RequestBody Integer[] ids){
		targetGroupService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
