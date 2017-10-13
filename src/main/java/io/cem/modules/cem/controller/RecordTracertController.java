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
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordTracertEntity> recordTracertList = recordTracertService.queryList(query);
		int total = recordTracertService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordTracertList, total, query.getLimit(), query.getPage());
		
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
