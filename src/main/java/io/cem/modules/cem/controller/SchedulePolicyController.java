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
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:46
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
	/*public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SchedulePolicyEntity> schedulePolicyList = schedulePolicyService.queryList(query);
		int total = schedulePolicyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(schedulePolicyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}*/
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
		List<SchedulePolicyEntity> schedulePolicyList = schedulePolicyService.queryList(map);
		int total = schedulePolicyService.queryTotal(map);
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
	 * 新增
	 */
	@RequestMapping("/add")
	@RequiresPermissions("schedulepolicy:add")
	public R add(@RequestBody SchedulePolicyEntity schedulePolicy){
		schedulePolicyService.add(schedulePolicy);

		return R.ok();
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
