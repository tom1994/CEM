package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
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
@RequestMapping("alarmrecord")
public class AlarmRecordController {
	@Autowired
	private AlarmRecordService alarmRecordService;

	/**
	 * 告警信息列表
	 * @param probedata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions("alarmrecord:list")
	public R list(String probedata, Integer page, Integer limit) throws Exception{
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}

		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = alarmRecordService.queryTotal(map);
		}
		List<AlarmRecordEntity> probeList = alarmRecordService.queryAlarmRecordList(map);
		PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
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
	 * 确认修改状态
	 * @param id
	 * @return R
	 */
	@RequestMapping("/operate/{id}")
	@RequiresPermissions("alarmrecord:operate")
	public R update(@PathVariable("id") Integer id){
		alarmRecordService.operate(id);
		return R.ok();
	}

	/**
	 * 批量确认
	 * @param ids
	 * @return R
	 */
	@RequestMapping("/change/{ids}")
	@RequiresPermissions("alarmrecord:change")
	public R change(@PathVariable("ids") Integer[] ids){
		System.out.println(ids[0]);
		for(int i=0;i<ids.length;i++){
			alarmRecordService.operate(ids[i]);
		}

		return R.ok();
	}


	/**
	 * 删除
	 * @param ids
	 * @return R
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("alarmrecord:delete")
	public R delete(@RequestBody Integer[] ids){
		alarmRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
