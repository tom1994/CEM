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

import io.cem.modules.cem.entity.RecordDhcpEntity;
import io.cem.modules.cem.service.RecordDhcpService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
 */
@RestController
@RequestMapping("recorddhcp")
public class RecordDhcpController {
	@Autowired
	private RecordDhcpService recordDhcpService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recorddhcp:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordDhcpEntity> recordDhcpList = recordDhcpService.queryList(query);
		int total = recordDhcpService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordDhcpList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recorddhcp:info")
	public R info(@PathVariable("id") Integer id){
		RecordDhcpEntity recordDhcp = recordDhcpService.queryObject(id);
		
		return R.ok().put("recordDhcp", recordDhcp);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recorddhcp:save")
	public R save(@RequestBody RecordDhcpEntity recordDhcp){
		recordDhcpService.save(recordDhcp);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recorddhcp:update")
	public R update(@RequestBody RecordDhcpEntity recordDhcp){
		recordDhcpService.update(recordDhcp);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recorddhcp:delete")
	public R delete(@RequestBody Integer[] ids){
		recordDhcpService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
