package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.annotation.SysLog;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.TargetGroupEntity;
import io.cem.modules.cem.service.TargetGroupService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("targetgroup")
public class TargetGroupController {
	@Autowired
	private TargetGroupService targetGroupService;

	/**
	 * 测试目标组列表
	 * @param tgdata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions("targetgroup:list")
	public R list(String tgdata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		JSONObject tgdata_jsonobject = JSONObject.parseObject(tgdata);
		try {
			map.putAll(JSONUtils.jsonToMap(tgdata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		List<TargetGroupEntity> tgList = targetGroupService.queryByTgNameList(map);
		int total = targetGroupService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(tgList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 显示所有地址
	 * @param groupdata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/searchlist")
	@RequiresPermissions("targetgroup:searchlist")
	public R searchlist(String groupdata, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<>();
		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = targetGroupService.queryTotal(map);
		}
		List<TargetGroupEntity> probeGroupList = targetGroupService.queryList(map);
		PageUtils pageUtil = new PageUtils(probeGroupList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("targetgroup:info")
	public R info(@PathVariable("id") Integer id){
		TargetGroupEntity targetGroup = targetGroupService.queryObject(id);
		
		return R.ok().put("targetGroup", targetGroup);
	}

	/**
	 * 根据业务类型查询
	 * @param spId
	 * @return R
	 */
	@RequestMapping("/infoList/{spid}")
	@RequiresPermissions("targetgroup:info")
	public R infoList(@PathVariable("spid") Integer spId){
		List<TargetGroupEntity> target = targetGroupService.queryTGList(spId);
		return R.ok().put("targetGroup", target);
	}

	/**
	 * 保存
	 * @param targetGroup
	 * @return
	 */
	@SysLog("新建目标组")
	@RequestMapping("/save")
	@RequiresPermissions("targetgroup:save")
	public R save(@RequestBody TargetGroupEntity targetGroup){
		if (targetGroupService.queryExist(targetGroup.getTgName()) > 0) {
			return R.error(300, "测试目标组名称已存在，请重新输入");
		} else {
			targetGroupService.save(targetGroup);
			return R.ok();
		}
	}

	/**
	 * 修改
	 * @param targetGroup
	 * @return R
	 */
	@SysLog("修改目标组")
	@RequestMapping("/update")
	@RequiresPermissions("targetgroup:update")
	public R update(@RequestBody TargetGroupEntity targetGroup){
		if (targetGroupService.queryUpdate(targetGroup.getTgName(),targetGroup.getId()) > 0) {
			return R.error(300, "测试目标组名称已存在，请重新输入");
		} else {
			targetGroupService.update(targetGroup);
			return R.ok();
		}
	}

	/**
	 * 删除
	 * @param ids
	 * @return R
	 */
	@SysLog("删除目标组")
	@RequestMapping("/delete")
	@RequiresPermissions("targetgroup:delete")
	public R delete(@RequestBody Integer[] ids){
		targetGroupService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
