package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.ProbeExitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
	 * 列表
	 */
	@RequestMapping("/total")
	@RequiresPermissions("probeexit:total")
	public R list(String probedata) throws Exception {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int total = probeExitService.queryTotal(map);
		return R.ok().put("total", total);
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
	@RequestMapping("/update/{id}")
	@RequiresPermissions("probeexit:update")
	public R update(@RequestBody ProbeExitEntity probeExit){
		probeExitService.update(probeExit);
		
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/operate/{id}")
	@RequiresPermissions("probeexit:operate")
	public R update(@PathVariable("id") Integer id){
		ProbeExitEntity probeExit = probeExitService.queryObject(id);
		if(probeExit.getStatus()==0){
			probeExitService.operateStatus0(id);
		}else if(probeExit.getStatus()==1){
			probeExitService.operateStatus1(id);
		}else{}

		return R.ok();
	}

	/**
	 * 分数
	 */
	@RequestMapping("/score")
	@RequiresPermissions("probeexit:score")
	public R scoreList(String probedata, Integer page, Integer limit) throws Exception {
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
			total = probeExitService.queryTotal(map);//error!!!!
		}
		List<ProbeExitEntity> probeExitList = probeExitService.queryscoreList(map);
		List<ScoreEntity> scoreList = new ArrayList<>();

		for(int i=0;i<probeExitList.size();i++){
			Map<String, Object> exitMap = new HashMap<>();
			map.put("probe_id",probeExitList.get(i).getProbeId());
			map.put("port",probeExitList.get(i).getPort());
			ScoreEntity score=probeExitService.calculateScore(map);
			if(score.getProbeId()!=null){
				score.setExit(probeExitList.get(i).getExit());
				scoreList.add(score);}
		}

		PageUtils pageUtil = new PageUtils(scoreList, total, limit, page);

		return R.ok().put("page", pageUtil);
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
