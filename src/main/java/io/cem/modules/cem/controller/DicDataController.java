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

import io.cem.modules.cem.entity.DicDataEntity;
import io.cem.modules.cem.service.DicDataService;
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
@RequestMapping("dicdata")
public class DicDataController {
	@Autowired
	private DicDataService dicDataService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("dicdata:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<DicDataEntity> dicDataList = dicDataService.queryList(query);
		int total = dicDataService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(dicDataList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("dicdata:info")
	public R info(@PathVariable("id") Integer id){
		DicDataEntity dicData = dicDataService.queryObject(id);
		
		return R.ok().put("dicData", dicData);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("dicdata:save")
	public R save(@RequestBody DicDataEntity dicData){
		dicDataService.save(dicData);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("dicdata:update")
	public R update(@RequestBody DicDataEntity dicData){
		dicDataService.update(dicData);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("dicdata:delete")
	public R delete(@RequestBody Integer[] ids){
		dicDataService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
