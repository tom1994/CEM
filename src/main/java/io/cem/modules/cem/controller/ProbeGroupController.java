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

import io.cem.modules.cem.entity.ProbeGroupEntity;
import io.cem.modules.cem.service.ProbeGroupService;
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
@RequestMapping("probegroup")
public class ProbeGroupController {
	@Autowired
	private ProbeGroupService probeGroupService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("probegroup:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProbeGroupEntity> probeGroupList = probeGroupService.queryList(query);
		int total = probeGroupService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(probeGroupList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("probegroup:info")
	public R info(@PathVariable("id") Integer id){
		ProbeGroupEntity probeGroup = probeGroupService.queryObject(id);
		
		return R.ok().put("probeGroup", probeGroup);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("probegroup:save")
	public R save(@RequestBody ProbeGroupEntity probeGroup){
		probeGroupService.save(probeGroup);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("probegroup:update")
	public R update(@RequestBody ProbeGroupEntity probeGroup){
		probeGroupService.update(probeGroup);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("probegroup:delete")
	public R delete(@RequestBody Integer[] ids){
		probeGroupService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
