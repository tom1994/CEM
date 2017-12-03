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

import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.service.RecordHourPingService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
@RestController
@RequestMapping("recordhourping")
public class RecordHourPingController {
	@Autowired
	private RecordHourPingService recordHourPingService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourping:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordHourPingEntity> recordHourPingList = recordHourPingService.queryList(query);
		int total = recordHourPingService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordHourPingList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordhourping:info")
	public R info(@PathVariable("id") Integer id){
		RecordHourPingEntity recordHourPing = recordHourPingService.queryObject(id);
		
		return R.ok().put("recordHourPing", recordHourPing);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordhourping:save")
	public R save(@RequestBody RecordHourPingEntity recordHourPing){
		recordHourPingService.save(recordHourPing);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordhourping:update")
	public R update(@RequestBody RecordHourPingEntity recordHourPing){
		recordHourPingService.update(recordHourPing);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordhourping:delete")
	public R delete(@RequestBody Integer[] ids){
		recordHourPingService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
