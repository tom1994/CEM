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

import io.cem.modules.cem.entity.RecordTracertEntity;
import io.cem.modules.cem.service.RecordTracertService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:47
 */
@RestController
@RequestMapping("recordtracert")
public class RecordTracertController {
	@Autowired
	private RecordTracertService recordTracertService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordtracert:list")
	public R list(String resultdata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject resultdata_jsonobject = JSONObject.parseObject(resultdata);
		try {
			map.putAll(JSONUtils.jsonToMap(resultdata_jsonobject));
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
			total = recordTracertService.queryTotal(map);
		}
		List<RecordTracertEntity> resultList = recordTracertService.queryTracertList(map);
		PageUtils pageUtil = new PageUtils(resultList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordtracert:info")
	public R info(@PathVariable("id") Integer id){
		RecordTracertEntity recordTracert = recordTracertService.queryObject(id);
		
		return R.ok().put("recordTracert", recordTracert);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordtracert:save")
	public R save(@RequestBody RecordTracertEntity recordTracert){
		recordTracertService.save(recordTracert);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordtracert:update")
	public R update(@RequestBody RecordTracertEntity recordTracert){
		recordTracertService.update(recordTracert);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordtracert:delete")
	public R delete(@RequestBody Integer[] ids){
		recordTracertService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
