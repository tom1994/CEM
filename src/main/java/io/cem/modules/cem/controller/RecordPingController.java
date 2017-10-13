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

import io.cem.modules.cem.entity.RecordPingEntity;
import io.cem.modules.cem.service.RecordPingService;
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
@RequestMapping("recordping")
public class RecordPingController {
	@Autowired
	private RecordPingService recordPingService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordping:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordPingEntity> recordPingList = recordPingService.queryList(query);
		int total = recordPingService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordPingList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordping:info")
	public R info(@PathVariable("id") Integer id){
		RecordPingEntity recordPing = recordPingService.queryObject(id);
		
		return R.ok().put("recordPing", recordPing);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordping:save")
	public R save(@RequestBody RecordPingEntity recordPing){
		recordPingService.save(recordPing);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordping:update")
	public R update(@RequestBody RecordPingEntity recordPing){
		recordPingService.update(recordPing);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordping:delete")
	public R delete(@RequestBody Integer[] ids){
		recordPingService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
