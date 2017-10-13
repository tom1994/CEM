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

import io.cem.modules.cem.entity.TaskEntity;
import io.cem.modules.cem.service.TaskService;
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
@RequestMapping("task")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("task:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TaskEntity> taskList = taskService.queryList(query);
		int total = taskService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(taskList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("task:info")
	public R info(@PathVariable("id") Integer id){
		TaskEntity task = taskService.queryObject(id);
		
		return R.ok().put("task", task);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("task:save")
	public R save(@RequestBody TaskEntity task){
		taskService.save(task);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("task:update")
	public R update(@RequestBody TaskEntity task){
		taskService.update(task);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("task:delete")
	public R delete(@RequestBody Integer[] ids){
		taskService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
