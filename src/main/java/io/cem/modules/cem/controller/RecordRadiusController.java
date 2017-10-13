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

import io.cem.modules.cem.entity.RecordRadiusEntity;
import io.cem.modules.cem.service.RecordRadiusService;
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
@RequestMapping("recordradius")
public class RecordRadiusController {
	@Autowired
	private RecordRadiusService recordRadiusService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordradius:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordRadiusEntity> recordRadiusList = recordRadiusService.queryList(query);
		int total = recordRadiusService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordRadiusList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordradius:info")
	public R info(@PathVariable("id") Integer id){
		RecordRadiusEntity recordRadius = recordRadiusService.queryObject(id);
		
		return R.ok().put("recordRadius", recordRadius);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordradius:save")
	public R save(@RequestBody RecordRadiusEntity recordRadius){
		recordRadiusService.save(recordRadius);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordradius:update")
	public R update(@RequestBody RecordRadiusEntity recordRadius){
		recordRadiusService.update(recordRadius);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordradius:delete")
	public R delete(@RequestBody Integer[] ids){
		recordRadiusService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
