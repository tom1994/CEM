package io.cem.modules.cem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.AlarmRecordEntity;
import io.cem.modules.cem.entity.RecordPingEntity;
import io.cem.modules.cem.service.AlarmRecordService;
import io.cem.modules.cem.service.RecordPingService;
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
	@Autowired
	private RecordPingService recordPingService;
	

	/**
	 * 列表
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
	 * 详细信息
	 */
	@RequestMapping("/detail/{id}")
	@RequiresPermissions("alarmrecord:detail")
	public R detail(@PathVariable("id") Integer id){
//		ProbeEntity probeList = probeService.queryDetail(id);
		AlarmRecordEntity alarmList = alarmRecordService.queryObject(id);
		int service = alarmList.getServiceType();
		int record = alarmList.getRecordId();
		if(service==1||service==2||service==3){
			RecordPingEntity recordPing = recordPingService.queryObject(record);
		}else if(service==4||service==5){

		}else if(service==10||service==11){

		}else if(service==12){

		}else if(service==13){

		}else if(service==14){

		}else if(service==15){

		}else if(service==20){

		}else if(service==30){

		}else if(service==31||service==32){

		}else if(service==40){

		}else if(service==50){

		}else{}
		System.out.println(alarmList);
		return R.ok().put("alarm", alarmList);
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
	 * 修改状态
	 */
	@RequestMapping("/operate/{id}")
	@RequiresPermissions("alarmrecord:operate")
	public R update(@PathVariable("id") Integer id){
		alarmRecordService.operate(id);

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
