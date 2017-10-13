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

import io.cem.modules.cem.entity.ProbeEntity;
import io.cem.modules.cem.service.ProbeService;
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
@RequestMapping("probe")
public class ProbeController {
	@Autowired
	private ProbeService probeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("probe:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProbeEntity> probeList = probeService.queryList(query);
		int total = probeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(probeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("probe:info")
	public R info(@PathVariable("id") Integer id){
		ProbeEntity probe = probeService.queryObject(id);
		
		return R.ok().put("probe", probe);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("probe:save")
	public R save(@RequestBody ProbeEntity probe){
		probeService.save(probe);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("probe:update")
	public R update(@RequestBody ProbeEntity probe){
		probeService.update(probe);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("probe:delete")
	public R delete(@RequestBody Integer[] ids){
		probeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
