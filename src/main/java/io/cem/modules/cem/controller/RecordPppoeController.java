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

import io.cem.modules.cem.entity.RecordPppoeEntity;
import io.cem.modules.cem.service.RecordPppoeService;
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
@RequestMapping("recordpppoe")
public class RecordPppoeController {
	@Autowired
	private RecordPppoeService recordPppoeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordpppoe:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordPppoeEntity> recordPppoeList = recordPppoeService.queryList(query);
		int total = recordPppoeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordPppoeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordpppoe:info")
	public R info(@PathVariable("id") Integer id){
		RecordPppoeEntity recordPppoe = recordPppoeService.queryObject(id);
		
		return R.ok().put("recordPppoe", recordPppoe);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordpppoe:save")
	public R save(@RequestBody RecordPppoeEntity recordPppoe){
		recordPppoeService.save(recordPppoe);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordpppoe:update")
	public R update(@RequestBody RecordPppoeEntity recordPppoe){
		recordPppoeService.update(recordPppoe);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordpppoe:delete")
	public R delete(@RequestBody Integer[] ids){
		recordPppoeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
