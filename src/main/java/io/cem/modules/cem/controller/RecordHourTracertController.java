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

import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.service.RecordHourTracertService;
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
@RequestMapping("recordhourtracert")
public class RecordHourTracertController {
	@Autowired
	private RecordHourTracertService recordHourTracertService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourtracert:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordHourTracertEntity> recordHourTracertList = recordHourTracertService.queryList(query);
		int total = recordHourTracertService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordHourTracertList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordhourtracert:info")
	public R info(@PathVariable("id") Integer id){
		RecordHourTracertEntity recordHourTracert = recordHourTracertService.queryObject(id);
		
		return R.ok().put("recordHourTracert", recordHourTracert);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordhourtracert:save")
	public R save(@RequestBody RecordHourTracertEntity recordHourTracert){
		recordHourTracertService.save(recordHourTracert);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordhourtracert:update")
	public R update(@RequestBody RecordHourTracertEntity recordHourTracert){
		recordHourTracertService.update(recordHourTracert);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordhourtracert:delete")
	public R delete(@RequestBody Integer[] ids){
		recordHourTracertService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
