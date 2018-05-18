package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.SchedulePolicyEntity;
import io.cem.modules.cem.service.SchedulePolicyService;
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
 */
@RestController
@RequestMapping("/cem/schedulepolicy")
public class SchedulePolicyController {
	@Autowired
	private SchedulePolicyService schedulePolicyService;

	/**
	 * 调度策略列表
	 * @param schedulepolicydata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions("schedulepolicy:list")
	public R list(String schedulepolicydata, Integer page, Integer limit) throws Exception {
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
			total = schedulePolicyService.queryTotal(map);
		}
		JSONObject schedulepolicydata_jsonobject = JSONObject.parseObject(schedulepolicydata);
		try {
			map.putAll(JSONUtils.jsonToMap(schedulepolicydata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		List<SchedulePolicyEntity> schedulePolicyList = schedulePolicyService.queryList(map);
		PageUtils pageUtil = new PageUtils(schedulePolicyList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}


	/**
	 * 查询详情
	 * @param id
	 * @return R
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("schedulepolicy:info")
	public R info(@PathVariable("id") Integer id){
		SchedulePolicyEntity schedulePolicy = schedulePolicyService.queryObject(id);
		
		return R.ok().put("schedulePolicy", schedulePolicy);
	}

	/**
	 * 保存
	 * @param schedulePolicy
	 * @return R
	 */
	@SysLog("新建调度策略")
	@RequestMapping("/save")
	@RequiresPermissions("schedulepolicy:save")
	public R save(@RequestBody SchedulePolicyEntity schedulePolicy){
		if (schedulePolicyService.queryExist(schedulePolicy.getSpName()) > 0) {
			return R.error(300, "调度策略名称已存在，请重新输入");
		} else {
			schedulePolicyService.save(schedulePolicy);
			return R.ok();
		}
	}

	/**
	 * 修改
	 * @param schedulePolicy
	 * @return R
	 */
	@SysLog("修改调度策略")
	@RequestMapping("/update")
	@RequiresPermissions("schedulepolicy:update")
	public R update(@RequestBody SchedulePolicyEntity schedulePolicy){
		if (schedulePolicyService.queryExist(schedulePolicy.getSpName()) > 0) {
			return R.error(300, "调度策略名称已存在，请重新输入");
		} else {
			schedulePolicyService.update(schedulePolicy);
			return R.ok();
		}
	}

	/**
	 * 删除
	 * @param ids
	 * @return R
	 */
	@SysLog("删除调度策略")
	@RequestMapping("/delete")
	@RequiresPermissions("schedulepolicy:delete")
	public R delete(@RequestBody Integer[] ids){
		schedulePolicyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
