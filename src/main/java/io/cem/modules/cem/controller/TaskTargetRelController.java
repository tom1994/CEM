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

import io.cem.modules.cem.entity.TaskTargetRelEntity;
import io.cem.modules.cem.service.TaskTargetRelService;
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
@RequestMapping("tasktargetrel")
public class TaskTargetRelController {
	@Autowired
	private TaskTargetRelService taskTargetRelService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("tasktargetrel:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TaskTargetRelEntity> taskTargetRelList = taskTargetRelService.queryList(query);
		int total = taskTargetRelService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(taskTargetRelList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tasktargetrel:info")
	public R info(@PathVariable("id") Integer id){
		TaskTargetRelEntity taskTargetRel = taskTargetRelService.queryObject(id);
		
		return R.ok().put("taskTargetRel", taskTargetRel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("tasktargetrel:save")
	public R save(@RequestBody TaskTargetRelEntity taskTargetRel){
		taskTargetRelService.save(taskTargetRel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("tasktargetrel:update")
	public R update(@RequestBody TaskTargetRelEntity taskTargetRel){
		taskTargetRelService.update(taskTargetRel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("tasktargetrel:delete")
	public R delete(@RequestBody Integer[] ids){
		taskTargetRelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
