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

import io.cem.modules.cem.entity.RecordSlaEntity;
import io.cem.modules.cem.service.RecordSlaService;
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
@RequestMapping("recordsla")
public class RecordSlaController {
	@Autowired
	private RecordSlaService recordSlaService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordsla:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordSlaEntity> recordSlaList = recordSlaService.queryList(query);
		int total = recordSlaService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordSlaList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordsla:info")
	public R info(@PathVariable("id") Integer id){
		RecordSlaEntity recordSla = recordSlaService.queryObject(id);
		
		return R.ok().put("recordSla", recordSla);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordsla:save")
	public R save(@RequestBody RecordSlaEntity recordSla){
		recordSlaService.save(recordSla);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordsla:update")
	public R update(@RequestBody RecordSlaEntity recordSla){
		recordSlaService.update(recordSla);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordsla:delete")
	public R delete(@RequestBody Integer[] ids){
		recordSlaService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
