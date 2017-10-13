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

import io.cem.modules.cem.entity.RecordDnsEntity;
import io.cem.modules.cem.service.RecordDnsService;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
@RestController
@RequestMapping("recorddns")
public class RecordDnsController {
	@Autowired
	private RecordDnsService recordDnsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recorddns:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordDnsEntity> recordDnsList = recordDnsService.queryList(query);
		int total = recordDnsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordDnsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recorddns:info")
	public R info(@PathVariable("id") Integer id){
		RecordDnsEntity recordDns = recordDnsService.queryObject(id);
		
		return R.ok().put("recordDns", recordDns);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recorddns:save")
	public R save(@RequestBody RecordDnsEntity recordDns){
		recordDnsService.save(recordDns);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recorddns:update")
	public R update(@RequestBody RecordDnsEntity recordDns){
		recordDnsService.update(recordDns);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recorddns:delete")
	public R delete(@RequestBody Integer[] ids){
		recordDnsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
