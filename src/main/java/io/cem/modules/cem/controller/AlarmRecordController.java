package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 */
@RestController
@RequestMapping("alarmrecord")
public class AlarmRecordController {
	@Autowired
	private AlarmRecordService alarmRecordService;
	@Autowired
	private RecordPingService recordPingService;
	@Autowired
	private RecordTracertService recordTracertService;
	@Autowired
	private RecordSlaService recordSlaService;
	@Autowired
	private RecordDnsService recordDnsService;
	@Autowired
	private RecordDhcpService recordDhcpService;
	@Autowired
	private RecordPppoeService recordPppoeService;
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
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("alarmrecord:list")
	public R list(String probedata, Integer page, Integer limit) throws Exception{
		Map<String, Object> map = new HashMap<>();
		JSONObject probedata_jsonobject = JSONObject.parseObject(probedata);
		try {
			map.putAll(JSONUtils.jsonToMap(probedata_jsonobject));
		} catch (RuntimeException e) {
			throw new RRException("内部参数错误，请重试！");
		}
//		try {
//			if ((map.get("status") == null) || (map.get("status")=="")) {
//				map.put("status",-1);
//			}
//		} catch (Exception ex) {
//		}

		int total = 0;
		if(page==null) {              /*没有传入page,则取全部值*/
			map.put("offset", null);
			map.put("limit", null);
			page = 0;
			limit = 0;
		}else {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
			total = alarmRecordService.queryTotal(map);
		}
		List<AlarmRecordEntity> probeList = alarmRecordService.queryAlarmRecordList(map);
		PageUtils pageUtil = new PageUtils(probeList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 详细信息
	 */
	@RequestMapping("/detail/{id}")
	@RequiresPermissions("alarmrecord:detail")
	public R detail(@PathVariable("id") Integer id){
//		ProbeEntity probeList = probeService.queryDetail(id);
		AlarmRecordEntity alarmList = alarmRecordService.queryObject(id);
		int service = alarmList.getServiceType();
		int record = alarmList.getRecordId();
		if(service==1||service==2||service==3){
			RecordPingEntity recordPing = recordPingService.queryObject(record);
			alarmList.setPingDelay(recordPing.getDelay());
			alarmList.setPingDelayStd(recordPing.getDelayStd());
			alarmList.setPingDelayVar(recordPing.getDelayVar());
			alarmList.setPingJitter(recordPing.getJitter());
			alarmList.setPingJitterStd(recordPing.getJitterStd());
			alarmList.setPingJitterVar(recordPing.getJitterVar());
			alarmList.setPingLossRate(recordPing.getLossRate());
		}else if(service==4||service==5){
			RecordTracertEntity recordTracert = recordTracertService.queryObject(record);
			alarmList.setTracertDelay(recordTracert.getDelay());
			alarmList.setTracertDelayStd(recordTracert.getDelayStd());
			alarmList.setTracertDelayVar(recordTracert.getDelayVar());
			alarmList.setTracertJitter(recordTracert.getJitter());
			alarmList.setTracertJitterStd(recordTracert.getJitterStd());
			alarmList.setTracertJitterVar(recordTracert.getJitterVar());
			alarmList.setTracertLossRate(recordTracert.getLossRate());
		}else if(service==10||service==11){
			RecordSlaEntity recordSla = recordSlaService.queryObject(record);
			alarmList.setSlaDelay(recordSla.getDelay());
			alarmList.setSlaGDelay(recordSla.getGDelay());
			alarmList.setSlaRDelay(recordSla.getRDelay());
			alarmList.setSlaDelayStd(recordSla.getDelayStd());
			alarmList.setSlaGDelayStd(recordSla.getGDelayStd());
			alarmList.setSlaRDelayStd(recordSla.getRDelayStd());
			alarmList.setSlaDelayVar(recordSla.getDelayVar());
			alarmList.setSlaGDelayVar(recordSla.getGDelayVar());
			alarmList.setSlaRDelayVar(recordSla.getRDelayVar());
			alarmList.setSlaJitter(recordSla.getJitter());
			alarmList.setSlaGJitter(recordSla.getGJitter());
			alarmList.setSlaRJitter(recordSla.getRJitter());
			alarmList.setSlaJitterStd(recordSla.getJitterStd());
			alarmList.setSlaGJitterStd(recordSla.getGJitterStd());
			alarmList.setSlaRJitterStd(recordSla.getRJitterStd());
			alarmList.setSlaJitterVar(recordSla.getJitterVar());
			alarmList.setSlaGJitterVar(recordSla.getGJitterVar());
			alarmList.setSlaRJitterVar(recordSla.getRJitterVar());
			alarmList.setSlaLossRate(recordSla.getLossRate());
			alarmList.setSlaGLossRate(recordSla.getGLossRate());
			alarmList.setSlaRLossRate(recordSla.getRLossRate());
		}else if(service==12){
			RecordDnsEntity recordDns = recordDnsService.queryObject(record);
			alarmList.setDnsDelay(recordDns.getDelay());
			alarmList.setDnsSuccessRate(recordDns.getSuccessRate());
		}else if(service==13){
			RecordDhcpEntity recordDhcp = recordDhcpService.queryObject(record);
			alarmList.setDhcpDelay(recordDhcp.getDelay());
			alarmList.setDhcpSuccessRate(recordDhcp.getSuccessRate());
		}else if(service==14){
			RecordPppoeEntity recordPppoe = recordPppoeService.queryObject(record);
			alarmList.setPppoeDelay(recordPppoe.getDelay());
			alarmList.setPppoeDropRate(recordPppoe.getDropRate());
			alarmList.setPppoeSuccessRate(recordPppoe.getSuccessRate());
		}else if(service==15){
			RecordRadiusEntity recordRadius = recordRadiusService.queryObject(record);
			alarmList.setRadiusDelay(recordRadius.getDelay());
			alarmList.setRadiusSuccessRate(recordRadius.getSuccessRate());
		}else if(service==20){
			RecordWebPageEntity recordWebPage = recordWebPageService.queryObject(record);
			alarmList.setWebpageDnsDelay(recordWebPage.getDnsDelay());
			alarmList.setWebpageConnDelay(recordWebPage.getConnDelay());
			alarmList.setWebpageHeadbyteDelay(recordWebPage.getHeadbyteDelay());
			alarmList.setWebpagePageFileDelay(recordWebPage.getPageFileDelay());
			alarmList.setWebpageRedirectDelay(recordWebPage.getRedirectDelay());
			alarmList.setWebpageAboveFoldDelay(recordWebPage.getAboveFoldDelay());
			alarmList.setWebpagePageElementDelay(recordWebPage.getPageElementDelay());
			alarmList.setWebpageLoadDelay(recordWebPage.getLoadDelay());
			alarmList.setWebpageDownloadRate(recordWebPage.getDownloadRate());
		}else if(service==30){
			RecordWebDownloadEntity recordWebDownload = recordWebDownloadService.queryObject(record);
			alarmList.setWebDownloadDnsDelay(recordWebDownload.getDnsDelay());
			alarmList.setWebDownloadConnDelay(recordWebDownload.getConnDelay());
			alarmList.setWebDownloadHeadbyteDelay(recordWebDownload.getHeadbyteDelay());
			alarmList.setWebDownloadDownloadRate(recordWebDownload.getDownloadRate());
		}else if(service==31||service==32){
			RecordFtpEntity recordFtp = recordFtpService.queryObject(record);
			alarmList.setFtpDnsDelay(recordFtp.getDnsDelay());
			alarmList.setFtpConnDelay(recordFtp.getConnDelay());
			alarmList.setFtpLoginDelay(recordFtp.getLoginDelay());
			alarmList.setFtpHeadbyteDelay(recordFtp.getHeadbyteDelay());
			alarmList.setFtpDownloadRate(recordFtp.getDownloadRate());
			alarmList.setFtpUploadRate(recordFtp.getUploadRate());
		}else if(service==40){
			RecordWebVideoEntity recordWebVideo = recordWebVideoService.queryObject(record);
			alarmList.setWebVideoDnsDelay(recordWebVideo.getDnsDelay());
			alarmList.setWebVideoWsConnDelay(recordWebVideo.getWsConnDelay());
			alarmList.setWebVideoWebPageDelay(recordWebVideo.getWebPageDelay());
			alarmList.setWebVideoSsConnDelay(recordWebVideo.getSsConnDelay());
			alarmList.setWebVideoAddressDelay(recordWebVideo.getAddressDelay());
			alarmList.setWebVideoMsConnDelay(recordWebVideo.getMsConnDelay());
			alarmList.setWebVideoHeadFrameDelay(recordWebVideo.getHeadFrameDelay());
			alarmList.setWebVideoInitBufferDelay(recordWebVideo.getInitBufferDelay());
			alarmList.setWebVideoLoadDelay(recordWebVideo.getLoadDelay());
			alarmList.setWebVideoTotalBufferDelay(recordWebVideo.getTotalBufferDelay());
			alarmList.setWebVideoDownloadRate(recordWebVideo.getDownloadRate());
			alarmList.setWebVideoBufferTime(recordWebVideo.getBufferTime());
		}else if(service==50){
			RecordGameEntity recordGame = recordGameService.queryObject(record);
			alarmList.setGameDnsDelay(recordGame.getDnsDelay());
			alarmList.setGameConnDelay(recordGame.getConnDelay());
			alarmList.setGamePacketDelay(recordGame.getPacketDelay());
			alarmList.setGamePacketJitter(recordGame.getPacketJitter());
			alarmList.setGameLossRate(recordGame.getLossRate());

		}else{}
		System.out.println(alarmList);
		return R.ok().put("alarm", alarmList);
	}



	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("alarmrecord:info")
	public R info(@PathVariable("id") Integer id){
		AlarmRecordEntity alarmRecord = alarmRecordService.queryObject(id);
		
		return R.ok().put("alarmRecord", alarmRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("alarmrecord:save")
	public R save(@RequestBody AlarmRecordEntity alarmRecord){
		alarmRecordService.save(alarmRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("alarmrecord:update")
	public R update(@RequestBody AlarmRecordEntity alarmRecord){
		alarmRecordService.update(alarmRecord);
		
		return R.ok();
	}

	/**
	 * 修改状态
	 */
	@RequestMapping("/operate/{id}")
	@RequiresPermissions("alarmrecord:operate")
	public R update(@PathVariable("id") Integer id){
		alarmRecordService.operate(id);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/change/{ids}")
	@RequiresPermissions("alarmrecord:change")
	public R change(@PathVariable("ids") Integer[] ids){
		System.out.println(ids[0]);
		for(int i=0;i<ids.length;i++){
			alarmRecordService.operate(ids[i]);
		}

		return R.ok();
	}

	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("alarmrecord:delete")
	public R delete(@RequestBody Integer[] ids){
		alarmRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
