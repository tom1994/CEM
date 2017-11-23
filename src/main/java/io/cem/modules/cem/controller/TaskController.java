package io.cem.modules.cem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
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
 * @author ${author}
 * @email ${email}
 * @date 2017-11-13 11:01:11
 */
@RestController
@RequestMapping("/cem/task")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("task:list")
	public R list(String taskdata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		JSONObject taskdata_jsonobject = JSONObject.parseObject(taskdata);
		try {
			map.putAll(JSONUtils.jsonToMap(taskdata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		List<TaskEntity> taskList = taskService.queryTaskList(map);
		int total = taskService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(taskList, total, limit, page);
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
