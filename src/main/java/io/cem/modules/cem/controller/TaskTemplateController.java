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

import io.cem.modules.cem.entity.TaskTemplateEntity;
import io.cem.modules.cem.service.TaskTemplateService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 任务模板表
 */
@RestController
@RequestMapping("/cem/tasktemplate")
public class TaskTemplateController {
	@Autowired
	private TaskTemplateService taskTemplateService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("tasktemplate:list")
	public R list(String schedulepolicydata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		JSONObject schedulepolicydata_jsonobject = JSONObject.parseObject(schedulepolicydata);
		try {
			map.putAll(JSONUtils.jsonToMap(schedulepolicydata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		List<TaskTemplateEntity> taskTemplateList = taskTemplateService.queryList(map);
		int total = taskTemplateService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(taskTemplateList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tasktemplate:info")
	public R info(@PathVariable("id") Integer id){
		TaskTemplateEntity taskTemplate = taskTemplateService.queryObject(id);
		
		return R.ok().put("taskTemplate", taskTemplate);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("tasktemplate:save")
	public R save(@RequestBody TaskTemplateEntity taskTemplate){
		taskTemplateService.save(taskTemplate);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("tasktemplate:update")
	public R update(@RequestBody TaskTemplateEntity taskTemplate){
		taskTemplateService.update(taskTemplate);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("tasktemplate:delete")
	public R delete(@RequestBody Integer[] ids){
		taskTemplateService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
