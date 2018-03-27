package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.cem.service.ProbeExitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 端口-出口对照表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-03-26 19:47:28
 */
@RestController
@RequestMapping("probeexit")
public class ProbeExitController {
	@Autowired
	private ProbeExitService probeExitService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("probeexit:list")
	public R list(String probedata, Integer page, Integer limit) throws Exception {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int total = 0;
		if (page == null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		} else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = probeExitService.queryTotal(map);
		}
		List<ProbeExitEntity> probeExitList = probeExitService.queryList(map);
		
		PageUtils pageUtil = new PageUtils(probeExitList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("probeexit:info")
	public R info(@PathVariable("id") Integer id){
		ProbeExitEntity probeExit = probeExitService.queryObject(id);
		
		return R.ok().put("probeExit", probeExit);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("probeexit:save")
	public R save(@RequestBody ProbeExitEntity probeExit){
		probeExitService.save(probeExit);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("probeexit:update")
	public R update(@RequestBody ProbeExitEntity probeExit){
		probeExitService.update(probeExit);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("probeexit:delete")
	public R delete(@RequestBody Integer[] ids){
		probeExitService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
