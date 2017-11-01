package io.cem.modules.cem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.cem.modules.cem.entity.TaskProbeRelEntity;
import io.cem.modules.cem.service.TaskProbeRelService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author Fern
 * @date 2017-10-12 17:12:45
 */
@RestController
@RequestMapping("/cem/taskproberel")
public class TaskProbeRelController {
	@Autowired
	private TaskProbeRelService taskProbeRelService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("taskproberel:list")
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
       //List<TaskProbeRelEntity> taskProbeRelList = taskProbeRelService.queryList(map);
		List<TaskProbeRelEntity> taskProbeRelList = taskProbeRelService.queryTaskList(map);
		int total = taskProbeRelService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(taskProbeRelList, total, limit, page);
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
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("taskproberel:update")
	public R update(@RequestBody TaskProbeRelEntity taskProbeRel){
		System.out.println("port:"+taskProbeRel.getPort());
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
