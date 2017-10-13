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

import io.cem.modules.cem.entity.TaskProbeRelEntity;
import io.cem.modules.cem.service.TaskProbeRelService;
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
@RequestMapping("taskproberel")
public class TaskProbeRelController {
	@Autowired
	private TaskProbeRelService taskProbeRelService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("taskproberel:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TaskProbeRelEntity> taskProbeRelList = taskProbeRelService.queryList(query);
		int total = taskProbeRelService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(taskProbeRelList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("taskproberel:info")
	public R info(@PathVariable("id") Integer id){
		TaskProbeRelEntity taskProbeRel = taskProbeRelService.queryObject(id);
		
		return R.ok().put("taskProbeRel", taskProbeRel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("taskproberel:save")
	public R save(@RequestBody TaskProbeRelEntity taskProbeRel){
		taskProbeRelService.save(taskProbeRel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("taskproberel:update")
	public R update(@RequestBody TaskProbeRelEntity taskProbeRel){
		taskProbeRelService.update(taskProbeRel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("taskproberel:delete")
	public R delete(@RequestBody Integer[] ids){
		taskProbeRelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
