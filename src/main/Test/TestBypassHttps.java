
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.ScoreEntity;
import io.cem.modules.cem.service.*;
import org.springframework.beans.factory.annotation.Autowired;


import javax.net.ssl.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class TestBypassHttps {
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

    @org.junit.Test
    public void test() throws ExecutionException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("ava_start","2018-03-28");
        map.put("ava_terminal","2018-03-28");
        map.put("service",0);
        Future<List<RecordHourPingEntity>> pingList_future = recordHourPingService.queryPingList(map);
        Future<List<RecordHourTracertEntity>> tracertList_future = recordHourTracertService.queryTracertList(map);
        List<ScoreEntity> connection;
        while (true) {
            if (pingList_future.isDone() && tracertList_future.isDone()) {
                List<RecordHourPingEntity> pingList = pingList_future.get();
                List<RecordHourTracertEntity> tracertList = tracertList_future.get();
                List<ScoreEntity> pingIcmp = recordHourPingService.calculatePingIcmp(pingList);
                List<ScoreEntity> pingTcp = recordHourPingService.calculatePingTcp(pingList);
                List<ScoreEntity> pingUdp = recordHourPingService.calculatePingUdp(pingList);
                List<ScoreEntity> tracertIcmp = recordHourPingService.calculateTracertIcmp(tracertList);
                List<ScoreEntity> tracertUdp = recordHourPingService.calculateTracertUdp(tracertList);
                connection = recordHourPingService.calculateService1(pingIcmp, pingTcp, pingUdp, tracertIcmp, tracertUdp);
                break;
            }
            Thread.sleep(1000);
        }
        System.out.println(connection);
    }
}
