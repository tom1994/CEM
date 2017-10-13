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

import io.cem.modules.cem.entity.RecordFtpEntity;
import io.cem.modules.cem.service.RecordFtpService;
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
@RequestMapping("recordftp")
public class RecordFtpController {
	@Autowired
	private RecordFtpService recordFtpService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordftp:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RecordFtpEntity> recordFtpList = recordFtpService.queryList(query);
		int total = recordFtpService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(recordFtpList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordftp:info")
	public R info(@PathVariable("id") Integer id){
		RecordFtpEntity recordFtp = recordFtpService.queryObject(id);
		
		return R.ok().put("recordFtp", recordFtp);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordftp:save")
	public R save(@RequestBody RecordFtpEntity recordFtp){
		recordFtpService.save(recordFtp);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordftp:update")
	public R update(@RequestBody RecordFtpEntity recordFtp){
		recordFtpService.update(recordFtp);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordftp:delete")
	public R delete(@RequestBody Integer[] ids){
		recordFtpService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
