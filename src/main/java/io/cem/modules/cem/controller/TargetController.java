package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.cem.service.TargetService;
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
 */
@RestController
@RequestMapping("target")
public class TargetController {
	@Autowired
	private TargetService targetService;

	/**
	 * 目标地址列表
	 * @param targetdata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions("target:list")
	public R list(String targetdata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject targetdata_jsonobject = JSONObject.parseObject(targetdata);
		try {
			map.putAll(JSONUtils.jsonToMap(targetdata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = targetService.queryTotal(map);
		}
		List<TargetEntity> targetList = targetService.queryTgByTList(map);
		PageUtils pageUtil = new PageUtils(targetList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}


	/**
	 * 目标地址详情
	 * @param id
	 * @return R
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("target:info")
	public R info(@PathVariable("id") Integer id){
		TargetEntity target = targetService.queryObject(id);
		
		return R.ok().put("target", target);
	}

	/**
	 * 按业务类型显示目标地址(原用于按子业务类型查询，后取消了目标的子业务类型概念）
	 * @param serviceId
	 * @return R
	 */
	@RequestMapping("/infobat/{id}")
	@RequiresPermissions("target:infobat")
	public R infobat(@PathVariable("id") Integer serviceId){
		List<TargetEntity> target = targetService.infoBatch(serviceId);
		return R.ok().put("target", target);
	}


	/**
	 * 按照业务类型筛选地址
	 * @param spId
	 * @return R
	 */
	@RequestMapping("/infoList/{spid}")
	@RequiresPermissions("target:info")
	public R infoList(@PathVariable("spid") Integer spId){
		List<TargetEntity> target = targetService.queryTargetList(spId);
		return R.ok().put("target", target);
	}

	/**
	 * 保存
	 * @param target
	 * @return R
	 */
	@SysLog("新建测试目标")
	@RequestMapping("/save")
	@RequiresPermissions("target:save")
	public R save(@RequestBody TargetEntity target){
		if (targetService.queryExist(target.getTargetName()) > 0) {
			return R.error(300, "测试目标名称已存在，请重新输入");
		} else {
			targetService.save(target);
			return R.ok();
		}
	}

	/**
	 * 修改
	 * @param target
	 * @return R
	 */
	@SysLog("修改测试目标")
	@RequestMapping("/update")
	@RequiresPermissions("target:update")
	public R update(@RequestBody TargetEntity target){
		if (targetService.queryUpdate(target.getTargetName(),target.getId()) > 0) {
			return R.error(300, "测试目标名称已存在，请重新输入");
		} else {
			targetService.update(target);
			return R.ok();
		}
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@SysLog("删除测试目标")
	@RequestMapping("/delete")
	@RequiresPermissions("target:delete")
	public R delete(@RequestBody Integer[] ids){
		targetService.deleteBatch(ids);
		
		return R.ok();
	}



}
