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

import io.cem.modules.cem.entity.AlarmRecordEntity;
import io.cem.modules.cem.service.AlarmRecordService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
@RestController
@RequestMapping("alarmrecord")
public class AlarmRecordController {
	@Autowired
	private AlarmRecordService alarmRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("alarmrecord:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<AlarmRecordEntity> alarmRecordList = alarmRecordService.queryList(query);
		int total = alarmRecordService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(alarmRecordList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("alarmrecord:info")
	public R info(@PathVariable("id") Integer id){
		AlarmRecordEntity alarmRecord = alarmRecordService.queryObject(id);
		
		return R.ok().put("alarmRecord", alarmRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("alarmrecord:save")
	public R save(@RequestBody AlarmRecordEntity alarmRecord){
		alarmRecordService.save(alarmRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("alarmrecord:update")
	public R update(@RequestBody AlarmRecordEntity alarmRecord){
		alarmRecordService.update(alarmRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("alarmrecord:delete")
	public R delete(@RequestBody Integer[] ids){
		alarmRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
