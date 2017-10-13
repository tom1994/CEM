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

import io.cem.modules.cem.entity.DicTypeEntity;
import io.cem.modules.cem.service.DicTypeService;
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
@RequestMapping("dictype")
public class DicTypeController {
	@Autowired
	private DicTypeService dicTypeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("dictype:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<DicTypeEntity> dicTypeList = dicTypeService.queryList(query);
		int total = dicTypeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(dicTypeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("dictype:info")
	public R info(@PathVariable("id") Integer id){
		DicTypeEntity dicType = dicTypeService.queryObject(id);
		
		return R.ok().put("dicType", dicType);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("dictype:save")
	public R save(@RequestBody DicTypeEntity dicType){
		dicTypeService.save(dicType);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("dictype:update")
	public R update(@RequestBody DicTypeEntity dicType){
		dicTypeService.update(dicType);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("dictype:delete")
	public R delete(@RequestBody Integer[] ids){
		dicTypeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
