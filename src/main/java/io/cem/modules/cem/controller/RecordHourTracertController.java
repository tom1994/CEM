package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 */
@RestController
@RequestMapping("recordhourtracert")
public class RecordHourTracertController {
	@Autowired
	private RecordHourPingService recordHourPingService;
	@Autowired
	private RecordHourTracertService recordHourTracertService;
	@Autowired
	private RecordHourSlaService recordHourSlaService;
	@Autowired
	private RecordHourDnsService recordHourDnsService;
	@Autowired
	private RecordHourDhcpService recordHourDhcpService;
	@Autowired
	private RecordHourPppoeService recordHourPppoeService;
	@Autowired
	private RecordHourRadiusService recordHourRadiusService;
	@Autowired
	private RecordHourWebPageService recordHourWebPageService;
	@Autowired
	private RecordHourWebDownloadService recordHourWebDownloadService;
	@Autowired
	private RecordHourFtpService recordHourFtpService;
	@Autowired
	private RecordHourWebVideoService recordHourWebVideoService;
	@Autowired
	private RecordHourGameService recordHourGameService;

	/**
	 * ZTY用于区域排名
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourtracert:list")
	public R list(String probedata, Integer page, Integer limit) throws ExecutionException, InterruptedException {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int service = Integer.parseInt(map.get("service").toString());
		String dateStr = map.get("ava_start").toString();
		String dateStr2 = map.get("ava_terminal").toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int dateDifferent = 0;
		try
		{
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			dateDifferent = recordHourPingService.differentDays(date,date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<ScoreEntity> scoreList = new ArrayList<>();
		//查询天表
		if (dateDifferent>5){
            //查询天表
            scoreList = recordHourRadiusService.calculateAreaDayScore(map);
		}
		else {
            //查询小时表
            scoreList = recordHourRadiusService.calculateAreaHourScore(map);
		}

		if(map.get("target_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setTargetName("");
			}
		}else{}

		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = scoreList.size();
		}
		//List<RecordHourPingEntity> probeList = recordHourPingService.queryList(map);
		PageUtils pageUtil = new PageUtils(scoreList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recordhourtracert:info")
	public R info(@PathVariable("id") Integer id){
		RecordHourTracertEntity recordHourTracert = recordHourTracertService.queryObject(id);

		return R.ok().put("recordHourTracert", recordHourTracert);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recordhourtracert:save")
	public R save(@RequestBody RecordHourTracertEntity recordHourTracert){
		recordHourTracertService.save(recordHourTracert);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recordhourtracert:update")
	public R update(@RequestBody RecordHourTracertEntity recordHourTracert){
		recordHourTracertService.update(recordHourTracert);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recordhourtracert:delete")
	public R delete(@RequestBody Integer[] ids){
		recordHourTracertService.deleteBatch(ids);

		return R.ok();
	}

}
