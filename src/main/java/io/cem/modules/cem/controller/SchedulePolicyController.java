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

import io.cem.modules.cem.entity.SchedulePolicyEntity;
import io.cem.modules.cem.service.SchedulePolicyService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 21:13:27
 */
@RestController
@RequestMapping("/cem/schedulepolicy")
public class SchedulePolicyController {
	@Autowired
	private SchedulePolicyService schedulePolicyService;
	
	/**
	 * 列表
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
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("schedulepolicy:info")
	public R info(@PathVariable("id") Integer id){
		SchedulePolicyEntity schedulePolicy = schedulePolicyService.queryObject(id);
		
		return R.ok().put("schedulePolicy", schedulePolicy);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("schedulepolicy:save")
	public R save(@RequestBody SchedulePolicyEntity schedulePolicy){
		schedulePolicyService.save(schedulePolicy);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("schedulepolicy:update")
	public R update(@RequestBody SchedulePolicyEntity schedulePolicy){
		schedulePolicyService.update(schedulePolicy);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("schedulepolicy:delete")
	public R delete(@RequestBody Integer[] ids){
		schedulePolicyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
