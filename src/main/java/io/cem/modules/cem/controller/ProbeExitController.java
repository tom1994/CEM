package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.CollectionToFile;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.ProbeExitEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.ProbeExitService;
import io.cem.modules.cem.service.RecordHourPingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * 端口-出口对照表
 */
@RestController
@RequestMapping("probeexit")
public class ProbeExitController {
	@Autowired
	private ProbeExitService probeExitService;
	@Autowired
	private RecordHourPingService recordHourPingService;

	/**
	 * 出口列表
	 * @param reportdata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions("probeexit:list")
	public R list(String reportdata, Integer page, Integer limit) throws Exception {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(reportdata);
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
	 * 查询出口总数
	 * @param probedata
	 * @return R
	 * @throws Exception
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
	 * 根据id查询信息
	 * @param id
	 * @return R
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("probeexit:info")
	public R info(@PathVariable("id") Integer id){
		ProbeExitEntity probeExit = probeExitService.queryObject(id);
		
		return R.ok().put("probeExit", probeExit);
	}

	/**
	 * 保存
	 * @param probeExit
	 * @return R
	 */
	@RequestMapping("/save")
	@RequiresPermissions("probeexit:save")
	public R save(@RequestBody ProbeExitEntity probeExit){
		if ((probeExitService.queryNameExist(probeExit.getExit()) > 0)||((probeExitService.queryProbeExist(probeExit.getProbeId()) > 0)&&(probeExitService.queryPortExist(probeExit.getPort()) > 0))) {
			return R.error(300, "出口名称或对应端口已存在，请重新输入");
		} else {
			probeExitService.save(probeExit);
			return R.ok();
		}
	}

	/**
	 * 修改
	 * @param probeExit
	 * @return R
	 */
	@RequestMapping("/update/{id}")
	@RequiresPermissions("probeexit:update")
	public R update(@RequestBody ProbeExitEntity probeExit){
		if (probeExitService.queryUpdate(probeExit.getExit(),probeExit.getId()) > 0) {
			return R.error(300, "出口名称已存在，请重新输入");
		} else {
			probeExitService.update(probeExit);
			return R.ok();
		}
	}

	/**
	 * 修改监控状态
	 * @param id
	 * @return R
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
	 * 出口得分
	 * @param probedata
	 * @param page
	 * @param limit
	 * @return R
	 * @throws Exception
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
	 * 下载
	 * @param response
	 * @param probedata
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@RequestMapping("/download/{probedata}")
	@RequiresPermissions("probeexit:download")
	public void download(HttpServletResponse response, @PathVariable String probedata) throws ExecutionException, InterruptedException {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject chartdata_jsonobject = JSONObject.parseObject(probedata);
		System.out.println(chartdata_jsonobject);
		try {
			map.putAll(JSONUtils.jsonToMap(chartdata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		String dateStr = map.get("ava_start").toString();
		String dateStr2 = map.get("ava_terminal").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int dateDifferent = 0;
		try {
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			dateDifferent = recordHourPingService.differentDays(date, date2);
		} catch (ParseException e) {
			e.printStackTrace();
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

		CollectionToFile.collectionToFile(response, scoreList, ScoreEntity.class);
	}

	/**
	 * 删除
	 * @param ids
	 * @return R
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("probeexit:delete")
	public R delete(@RequestBody Integer[] ids){
		probeExitService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
