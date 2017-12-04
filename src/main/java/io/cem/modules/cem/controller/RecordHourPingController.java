package io.cem.modules.cem.controller;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.*;
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
import io.cem.common.utils.excel.ExcelUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
	 * ZTY用于质量排名界面计算分
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourping:list")
	public R list(String scoredata, Integer page, Integer limit){
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(scoredata);
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
			total = recordHourPingService.queryTotal(map);
		}
		List<RecordHourPingEntity> probeList = recordHourPingService.queryList(map);
		PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
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
