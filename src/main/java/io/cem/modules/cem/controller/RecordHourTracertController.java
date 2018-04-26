package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.CollectionToFile;
import io.cem.common.utils.JSONUtils;
import io.cem.common.utils.PageUtils;
import io.cem.common.utils.R;
import io.cem.modules.cem.entity.*;
import io.cem.modules.cem.service.*;
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

import static io.cem.modules.cem.entity.ScoreEntity.sortDescStringMethod;
import static io.cem.modules.cem.entity.ScoreEntity.sortStringMethod;

//import static io.cem.modules.cem.entity.ScoreEntity.scoreComparator;


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
	@Autowired
	private ProbeService probeService;
	@Autowired
	private RecordPingService recordPingService;
	@Autowired
	private RecordTracertService recordTracertService;
	@Autowired
	private RecordSlaService recordSlaService;
	@Autowired
	private RecordPppoeService recordPppoeService;
	@Autowired
	private RecordDhcpService recordDhcpService;
	@Autowired
	private RecordDnsService recordDnsService;
	@Autowired
	private RecordRadiusService recordRadiusService;
	@Autowired
	private RecordWebPageService recordWebPageService;
	@Autowired
	private RecordWebDownloadService recordWebDownloadService;
	@Autowired
	private RecordFtpService recordFtpService;
	@Autowired
	private RecordWebVideoService recordWebVideoService;
	@Autowired
	private RecordGameService recordGameService;

	/**
	 * 区域排名方法
	 */
	@RequestMapping("/list")
	@RequiresPermissions("recordhourtracert:list")
	public R list(String probedata, Integer page, Integer limit,String order) throws ExecutionException, InterruptedException {
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
		List<ScoreEntity> scoreList;
		if(dateDifferent==0){
			scoreList = recordHourRadiusService.calculateAreaHourScore(map);
		}else if(dateDifferent==1){
			scoreList = recordHourRadiusService.calculateAreaDayHourScore(map);
		}else{
			scoreList = recordHourRadiusService.calculateAreaDayScore(map);
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

//		Collections.sort(scoreList,scoreComparator);
		if (order.equals("asc")) {
			sortStringMethod(scoreList);
		}else if(order.equals("desc")){
			sortDescStringMethod(scoreList);
		}

		int start = (page-1)*limit;
		int end;
		if((page*limit-1)<scoreList.size()){
			end = page*limit-1;
		}else{end = scoreList.size()-1;}

		List<ScoreEntity> newList = new ArrayList<>();
		for(int i=start;i<=end;i++){
			newList.add(i-start,scoreList.get(i));
		}
		//List<RecordHourPingEntity> probeList = recordHourPingService.queryList(map);
		PageUtils pageUtil = new PageUtils(newList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 门户排名方法
	 */
	@RequestMapping("/targetlist")
	@RequiresPermissions("recordhourtracert:targetlist")
	public R targetlist(String probedata, Integer page, Integer limit,String order) throws ExecutionException, InterruptedException {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
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
		List<ScoreEntity> scoreList;
		if(dateDifferent==0){
			scoreList = recordHourRadiusService.calculateTargetHourScore(map);
		}else if(dateDifferent==1){
			scoreList = recordHourRadiusService.calculateTargetDayHourScore(map);
		}else{
			scoreList = recordHourRadiusService.calculateTargetDayScore(map);
		}

		if(map.get("probe_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setProbeName("");
			}
		}
		if(map.get("county_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setCountyName("");
			}
		}
		if(map.get("city_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setCityName("");
			}
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
			total = scoreList.size();
		}

//		Collections.sort(scoreList,scoreComparator);
		if (order.equals("asc")) {
			sortStringMethod(scoreList);
		}else if(order.equals("desc")){
			sortDescStringMethod(scoreList);
		}

		int start = (page-1)*limit;
		int end;
		if((page*limit-1)<scoreList.size()){
			end = page*limit-1;
		}else{end = scoreList.size()-1;}

		List<ScoreEntity> newList = new ArrayList<>();
		for(int i=start;i<=end;i++){
			newList.add(i-start,scoreList.get(i));
		}
		//List<RecordHourPingEntity> probeList = recordHourPingService.queryList(map);
		PageUtils pageUtil = new PageUtils(newList, total, limit, page);
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
	 * 区域排名详情
	 */
	@RequestMapping("/areadetail/{probedata}")
	@RequiresPermissions("recordhourtracert:areadetail")
	public R areaDetail(@PathVariable String probedata) throws ExecutionException, InterruptedException{
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
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
		List<ScoreEntity> scoreList=new ArrayList<>();

		if(dateDifferent==0){
			scoreList = recordHourRadiusService.calculateHourScore(map);
		}else if(dateDifferent==1){
			scoreList = recordHourRadiusService.calculateDayHourScore(map);
		}else{
			scoreList = recordHourRadiusService.calculateDayScore(map);
		}
//		int countyId = Integer.parseInt(map.get("county_id").toString());
//		List<ProbeEntity> probeList = probeService.queryProbe(countyId);
//		System.out.println(probeList);
//		List<ScoreEntity> scoreList=new ArrayList<>();
//		for(int i=0;i<probeList.size();i++){
//			map.put("probe_id",probeList.get(i).getId());
//			List<ScoreEntity> list = recordHourRadiusService.calculateDayScore(map);
//			if(list.size()!=0) {
//				scoreList.add(list.get(0));
//			}
//		}
		return R.ok().put("scoreList", scoreList);
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

	@RequestMapping("/download/{probedata}")
	@RequiresPermissions("recordhourtracert:download")
	public void downloadProbe(HttpServletResponse response, @PathVariable String probedata) throws ExecutionException, InterruptedException {
		Map<String, Object> map = new HashMap<>();
		JSONObject jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(jsonobject));
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
		List<ScoreEntity> scoreList;
		if (dateDifferent > 5) {
			//查询天表
			scoreList = recordHourRadiusService.calculateDayScore(map); }
		else {
			//查询小时表
			scoreList = recordHourRadiusService.calculateHourScore(map);
		}
		System.out.println(scoreList);
		CollectionToFile.collectionToFile(response, scoreList, ScoreEntity.class);
	}

	@RequestMapping("/doordownload/{probedata}")
	@RequiresPermissions("recordhourtracert:doordownload")
	public void downloadDoor(HttpServletResponse response, @PathVariable String probedata) throws ExecutionException, InterruptedException {
		Map<String, Object> map = new HashMap<>();
		JSONObject jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		List<ScoreEntity> scoreList = recordHourRadiusService.calculateTargetDayScore(map);
		if(map.get("probe_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setProbeName("");
			}
		}
		if(map.get("county_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setCountyName("");
			}
		}
		if(map.get("city_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setCityName("");
			}
		}
		System.out.println(scoreList);
		CollectionToFile.collectionToFile(response, scoreList, ScoreEntity.class);
	}


	@RequestMapping("/areaDownload/{probedata}")
	@RequiresPermissions("recordhourtracert:areaDownload")
	public void areaDownload(HttpServletResponse response, @PathVariable String probedata) throws ExecutionException, InterruptedException {
		Map<String, Object> map = new HashMap<>();
		JSONObject jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(jsonobject));
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
		List<ScoreEntity> scoreList;
		if(dateDifferent==0){
			scoreList = recordHourRadiusService.calculateAreaHourScore(map);
		}else if(dateDifferent==1){
			scoreList = recordHourRadiusService.calculateAreaDayHourScore(map);
		}else{
			scoreList = recordHourRadiusService.calculateAreaDayScore(map);
		}

		if(map.get("target_id")==null){
			for(int i=0;i<scoreList.size();i++){
				scoreList.get(i).setTargetName("");
			}
		}else{}
		System.out.println(scoreList);
		CollectionToFile.collectionToFile(response, scoreList, ScoreEntity.class);
	}


	@RequestMapping("/qualityDownload/{probedata}")
	@RequiresPermissions("recordhourtracert:qualityDownload")
	public void qualityDownload(HttpServletResponse response, @PathVariable String probedata) throws ExecutionException, InterruptedException {
		//查询列表数据
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
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
		EvaluationEntity score;
		if(dateDifferent>5){
			score = recordHourFtpService.calculateDayQualityScore(map);
		}else if(dateDifferent<=5 && dateDifferent>=3){
			score = recordHourFtpService.calculateHourQualityScore(map);
		} else{
			score = recordHourFtpService.calculateDayHourQualityScore(map);
		}

		List<EvaluationEntity> scoreList = new ArrayList<>();
		scoreList.add(score);
		System.out.println(score);
		CollectionToFile.collectionToFile(response, scoreList, EvaluationEntity.class);
	}

	@RequestMapping("/datadownload/{probedata}")
	@RequiresPermissions("recordhourtracert:datadownload")
	public void reportDownload(HttpServletResponse response, @PathVariable String probedata) throws ExecutionException, InterruptedException {
		Map<String, Object> map = new HashMap<>();
		JSONObject jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
		int service = Integer.parseInt(map.get("service_type").toString());

		int queryType = Integer.parseInt(map.get("queryType").toString());
		if (queryType == 1) {
			if (service == 1 || service == 2 || service == 3) {
				List<RecordPingEntity> list = recordPingService.queryPingList(map);
				System.out.println(list);
				CollectionToFile.collectionToFile(response, list, RecordPingEntity.class);
			} else if (service == 4 || service == 5) {
				List<RecordTracertEntity> list = recordTracertService.queryTracertList(map);
				CollectionToFile.collectionToFile(response, list, RecordTracertEntity.class);
			} else if (service == 10 || service == 11) {
				List<RecordSlaEntity> list = recordSlaService.querySlaList(map);
				CollectionToFile.collectionToFile(response, list, RecordSlaEntity.class);
			} else if (service == 12) {
				List<RecordPppoeEntity> list = recordPppoeService.queryPppoeList(map);
				CollectionToFile.collectionToFile(response, list, RecordPppoeEntity.class);
			} else if (service == 13) {
				List<RecordDhcpEntity> list = recordDhcpService.queryDhcpList(map);
				CollectionToFile.collectionToFile(response, list, RecordDhcpEntity.class);
			} else if (service == 14) {
				List<RecordDnsEntity> list = recordDnsService.queryDnsList(map);
				CollectionToFile.collectionToFile(response, list, RecordDnsEntity.class);
			} else if (service == 15) {
				List<RecordRadiusEntity> list = recordRadiusService.queryRadiusList(map);
				CollectionToFile.collectionToFile(response, list, RecordRadiusEntity.class);
			} else if (service == 20) {
				List<RecordWebPageEntity> list = recordWebPageService.queryWebPageList(map);
				CollectionToFile.collectionToFile(response, list, RecordWebPageEntity.class);
			} else if (service == 30) {
				List<RecordWebDownloadEntity> list = recordWebDownloadService.queryWebDownloadList(map);
				CollectionToFile.collectionToFile(response, list, RecordWebDownloadEntity.class);
			} else if (service == 31 || service == 32) {
				List<RecordFtpEntity> list = recordFtpService.queryFtpList(map);
				CollectionToFile.collectionToFile(response, list, RecordFtpEntity.class);
			} else if (service == 40) {
				List<RecordWebVideoEntity> list = recordWebVideoService.queryWebVideoList(map);
				CollectionToFile.collectionToFile(response, list, RecordWebVideoEntity.class);
			} else if (service == 50) {
				List<RecordGameEntity> list = recordGameService.queryGameList(map);
				CollectionToFile.collectionToFile(response, list, RecordGameEntity.class);
			} else {
			}


		} else {
			map.put("interval", map.get("interval"));
			if (service == 1 || service == 2 || service == 3) {
				List<RecordHourPingEntity> list = recordPingService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourPingEntity.class);
			} else if (service == 4 || service == 5) {
				List<RecordHourTracertEntity> list = recordTracertService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourTracertEntity.class);
			} else if (service == 10 || service == 11) {
				List<RecordHourSlaEntity> list = recordSlaService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourSlaEntity.class);
			} else if (service == 12) {
				List<RecordHourPppoeEntity> list = recordPppoeService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourPppoeEntity.class);
			} else if (service == 13) {
				List<RecordHourDhcpEntity> list = recordDhcpService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourDhcpEntity.class);
			} else if (service == 14) {
				List<RecordHourDnsEntity> list = recordDnsService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourDnsEntity.class);
			} else if (service == 15) {
				List<RecordHourRadiusEntity> list = recordRadiusService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourRadiusEntity.class);
			} else if (service == 20) {
				List<RecordHourWebPageEntity> list = recordWebPageService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourWebPageEntity.class);
			} else if (service == 30) {
				List<RecordHourWebDownloadEntity> list = recordWebDownloadService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourWebDownloadEntity.class);
			} else if (service == 31 || service == 32) {
				List<RecordHourFtpEntity> list = recordFtpService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourFtpEntity.class);
			} else if (service == 40) {
				List<RecordHourWebVideoEntity> list = recordWebVideoService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourWebVideoEntity.class);
			} else if (service == 50) {
				List<RecordHourGameEntity> list = recordGameService.queryIntervalList(map);
				CollectionToFile.collectionToFile(response, list, RecordHourGameEntity.class);
			} else {
			}

		}
	}
}
