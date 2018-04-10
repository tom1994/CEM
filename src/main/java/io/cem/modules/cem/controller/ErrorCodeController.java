package io.cem.modules.cem.controller;

import io.cem.common.utils.PageUtils;
import io.cem.common.utils.Query;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ErrorCodeEntity;
import io.cem.modules.cem.service.ErrorCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 *
 */
@RestController
@RequestMapping("errorcode")
public class ErrorCodeController {
	@Autowired
	private ErrorCodeService errorCodeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("errorcode:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErrorCodeEntity> errorCodeList = errorCodeService.queryList(query);
		int total = errorCodeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(errorCodeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{code}")
	@RequiresPermissions("errorcode:info")
	public R info(@PathVariable("code") Integer code){
		ErrorCodeEntity errorCode = errorCodeService.queryObject(code);
		
		return R.ok().put("errorCode", errorCode);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("errorcode:save")
	public R save(@RequestBody ErrorCodeEntity errorCode){
		errorCodeService.save(errorCode);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("errorcode:update")
	public R update(@RequestBody ErrorCodeEntity errorCode){
		errorCodeService.update(errorCode);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("errorcode:delete")
	public R delete(@RequestBody Integer[] codes){
		errorCodeService.deleteBatch(codes);
		
		return R.ok();
	}
	
}
