package io.cem.modules.cem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cem.common.utils.PropertiesUtils;
import io.cem.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/cem/allweight")
public class AllWeightController {
    @RequestMapping("/get")
    public R getWeight() throws IOException {
        String[] weight = new String[23];
        try {
            weight[0] = PropertiesUtils.getValue("connectionweight");
            weight[1] = PropertiesUtils.getValue("qualityweight");
            weight[2] = PropertiesUtils.getValue("downloadweight");
            weight[3] = PropertiesUtils.getValue("browseweight");
            weight[4] = PropertiesUtils.getValue("videoweight");
            weight[5] = PropertiesUtils.getValue("gameweight");
            weight[6] = PropertiesUtils.getValue("ping_icmp");
            weight[7] = PropertiesUtils.getValue("ping_tcp");
            weight[8] = PropertiesUtils.getValue("ping_udp");
            weight[9] = PropertiesUtils.getValue("tr_tcp");
            weight[10] = PropertiesUtils.getValue("tr_icmp");
            weight[11] = PropertiesUtils.getValue("sla_tcp");
            weight[12] = PropertiesUtils.getValue("sla_udp");
            weight[13] = PropertiesUtils.getValue("dns");
            weight[14] = PropertiesUtils.getValue("dhcp");
            weight[15] = PropertiesUtils.getValue("adsl");
            weight[16] = PropertiesUtils.getValue("radius");
            weight[17] = PropertiesUtils.getValue("ftp_upload");
            weight[18] = PropertiesUtils.getValue("ftp_download");
            weight[19] = PropertiesUtils.getValue("web_download");
            weight[20] = PropertiesUtils.getValue("webpage");
            weight[21] = PropertiesUtils.getValue("video");
            weight[22] = PropertiesUtils.getValue("game");
        } catch (IOException e) { }
        return R.ok().put("weight",weight);
    }

    @RequestMapping("/getpingICMP")
    public R getpingICMP() throws IOException {
        String[] pingICMP = new String[49];
        try {
            pingICMP[0] = PropertiesUtils.getValue("pingI21");
            pingICMP[1] = PropertiesUtils.getValue("pingI22");
            pingICMP[2] = PropertiesUtils.getValue("pingI23");
            pingICMP[3] = PropertiesUtils.getValue("pingI24");
            pingICMP[4] = PropertiesUtils.getValue("pingI25");
            pingICMP[5] = PropertiesUtils.getValue("pingI26");
            pingICMP[6] = PropertiesUtils.getValue("pingI27");
            pingICMP[7] = PropertiesUtils.getValue("pingI31");
            pingICMP[8] = PropertiesUtils.getValue("pingI32");
            pingICMP[9] = PropertiesUtils.getValue("pingI33");
            pingICMP[10] = PropertiesUtils.getValue("pingI34");
            pingICMP[11] = PropertiesUtils.getValue("pingI35");
            pingICMP[12] = PropertiesUtils.getValue("pingI36");
            pingICMP[13] = PropertiesUtils.getValue("pingI37");
            pingICMP[14] = PropertiesUtils.getValue("pingI41");
            pingICMP[15] = PropertiesUtils.getValue("pingI42");
            pingICMP[16] = PropertiesUtils.getValue("pingI43");
            pingICMP[17] = PropertiesUtils.getValue("pingI44");
            pingICMP[18] = PropertiesUtils.getValue("pingI45");
            pingICMP[19] = PropertiesUtils.getValue("pingI46");
            pingICMP[20] = PropertiesUtils.getValue("pingI47");
            pingICMP[21] = PropertiesUtils.getValue("pingI51");
            pingICMP[22] = PropertiesUtils.getValue("pingI52");
            pingICMP[23] = PropertiesUtils.getValue("pingI53");
            pingICMP[24] = PropertiesUtils.getValue("pingI54");
            pingICMP[25] = PropertiesUtils.getValue("pingI55");
            pingICMP[26] = PropertiesUtils.getValue("pingI56");
            pingICMP[27] = PropertiesUtils.getValue("pingI57");
            pingICMP[28] = PropertiesUtils.getValue("pingI61");
            pingICMP[29] = PropertiesUtils.getValue("pingI62");
            pingICMP[30] = PropertiesUtils.getValue("pingI63");
            pingICMP[31] = PropertiesUtils.getValue("pingI64");
            pingICMP[32] = PropertiesUtils.getValue("pingI65");
            pingICMP[33] = PropertiesUtils.getValue("pingI66");
            pingICMP[34] = PropertiesUtils.getValue("pingI67");
            pingICMP[35] = PropertiesUtils.getValue("pingI71");
            pingICMP[36] = PropertiesUtils.getValue("pingI72");
            pingICMP[37] = PropertiesUtils.getValue("pingI73");
            pingICMP[38] = PropertiesUtils.getValue("pingI74");
            pingICMP[39] = PropertiesUtils.getValue("pingI75");
            pingICMP[40] = PropertiesUtils.getValue("pingI76");
            pingICMP[41] = PropertiesUtils.getValue("pingI77");
            pingICMP[42] = PropertiesUtils.getValue("pingI81");
            pingICMP[43] = PropertiesUtils.getValue("pingI82");
            pingICMP[44] = PropertiesUtils.getValue("pingI83");
            pingICMP[45] = PropertiesUtils.getValue("pingI84");
            pingICMP[46] = PropertiesUtils.getValue("pingI85");
            pingICMP[47] = PropertiesUtils.getValue("pingI86");
            pingICMP[48] = PropertiesUtils.getValue("pingI87");
        } catch (IOException e) { }
        System.out.println(pingICMP);
        return R.ok().put("pingICMP",pingICMP);
    }

    @RequestMapping("/getpingTCP")
    public R getpingTCP() throws IOException {
        String[] pingTCP = new String[245];
        try {
            pingTCP[0] = PropertiesUtils.getValue("pingT21");
            pingTCP[1] = PropertiesUtils.getValue("pingT22");
            pingTCP[2] = PropertiesUtils.getValue("pingT23");
            pingTCP[3] = PropertiesUtils.getValue("pingT24");
            pingTCP[4] = PropertiesUtils.getValue("pingT25");
            pingTCP[5] = PropertiesUtils.getValue("pingT26");
            pingTCP[6] = PropertiesUtils.getValue("pingT27");
            pingTCP[7] = PropertiesUtils.getValue("pingT31");
            pingTCP[8] = PropertiesUtils.getValue("pingT32");
            pingTCP[9] = PropertiesUtils.getValue("pingT33");
            pingTCP[10] = PropertiesUtils.getValue("pingT34");
            pingTCP[11] = PropertiesUtils.getValue("pingT35");
            pingTCP[12] = PropertiesUtils.getValue("pingT36");
            pingTCP[13] = PropertiesUtils.getValue("pingT37");
            pingTCP[14] = PropertiesUtils.getValue("pingT41");
            pingTCP[15] = PropertiesUtils.getValue("pingT42");
            pingTCP[16] = PropertiesUtils.getValue("pingT43");
            pingTCP[17] = PropertiesUtils.getValue("pingT44");
            pingTCP[18] = PropertiesUtils.getValue("pingT45");
            pingTCP[19] = PropertiesUtils.getValue("pingT46");
            pingTCP[20] = PropertiesUtils.getValue("pingT47");
            pingTCP[21] = PropertiesUtils.getValue("pingT51");
            pingTCP[22] = PropertiesUtils.getValue("pingT52");
            pingTCP[23] = PropertiesUtils.getValue("pingT53");
            pingTCP[24] = PropertiesUtils.getValue("pingT54");
            pingTCP[25] = PropertiesUtils.getValue("pingT55");
            pingTCP[26] = PropertiesUtils.getValue("pingT56");
            pingTCP[27] = PropertiesUtils.getValue("pingT57");
            pingTCP[28] = PropertiesUtils.getValue("pingT61");
            pingTCP[29] = PropertiesUtils.getValue("pingT62");
            pingTCP[30] = PropertiesUtils.getValue("pingT63");
            pingTCP[31] = PropertiesUtils.getValue("pingT64");
            pingTCP[32] = PropertiesUtils.getValue("pingT65");
            pingTCP[33] = PropertiesUtils.getValue("pingT66");
            pingTCP[34] = PropertiesUtils.getValue("pingT67");
            pingTCP[35] = PropertiesUtils.getValue("pingT71");
            pingTCP[36] = PropertiesUtils.getValue("pingT72");
            pingTCP[37] = PropertiesUtils.getValue("pingT73");
            pingTCP[38] = PropertiesUtils.getValue("pingT74");
            pingTCP[39] = PropertiesUtils.getValue("pingT75");
            pingTCP[40] = PropertiesUtils.getValue("pingT76");
            pingTCP[41] = PropertiesUtils.getValue("pingT77");
            pingTCP[42] = PropertiesUtils.getValue("pingT81");
            pingTCP[43] = PropertiesUtils.getValue("pingT82");
            pingTCP[44] = PropertiesUtils.getValue("pingT83");
            pingTCP[45] = PropertiesUtils.getValue("pingT84");
            pingTCP[46] = PropertiesUtils.getValue("pingT85");
            pingTCP[47] = PropertiesUtils.getValue("pingT86");
            pingTCP[48] = PropertiesUtils.getValue("pingT87");
        } catch (IOException e) { }
        return R.ok().put("pingTCP",pingTCP);
    }

    @RequestMapping("/getpingUDP")
    public R getpingUDP() throws IOException {
        String[] pingUDP = new String[49];
        try {
            pingUDP[0] = PropertiesUtils.getValue("pingU21");
            pingUDP[1] = PropertiesUtils.getValue("pingU22");
            pingUDP[2] = PropertiesUtils.getValue("pingU23");
            pingUDP[3] = PropertiesUtils.getValue("pingU24");
            pingUDP[4] = PropertiesUtils.getValue("pingU25");
            pingUDP[5] = PropertiesUtils.getValue("pingU26");
            pingUDP[6] = PropertiesUtils.getValue("pingU27");
            pingUDP[7] = PropertiesUtils.getValue("pingU31");
            pingUDP[8] = PropertiesUtils.getValue("pingU32");
            pingUDP[9] = PropertiesUtils.getValue("pingU33");
            pingUDP[10] = PropertiesUtils.getValue("pingU34");
            pingUDP[11] = PropertiesUtils.getValue("pingU35");
            pingUDP[12] = PropertiesUtils.getValue("pingU36");
            pingUDP[13] = PropertiesUtils.getValue("pingU37");
            pingUDP[14] = PropertiesUtils.getValue("pingU41");
            pingUDP[15] = PropertiesUtils.getValue("pingU42");
            pingUDP[16] = PropertiesUtils.getValue("pingU43");
            pingUDP[17] = PropertiesUtils.getValue("pingU44");
            pingUDP[18] = PropertiesUtils.getValue("pingU45");
            pingUDP[19] = PropertiesUtils.getValue("pingU46");
            pingUDP[20] = PropertiesUtils.getValue("pingU47");
            pingUDP[21] = PropertiesUtils.getValue("pingU51");
            pingUDP[22] = PropertiesUtils.getValue("pingU52");
            pingUDP[23] = PropertiesUtils.getValue("pingU53");
            pingUDP[24] = PropertiesUtils.getValue("pingU54");
            pingUDP[25] = PropertiesUtils.getValue("pingU55");
            pingUDP[26] = PropertiesUtils.getValue("pingU56");
            pingUDP[27] = PropertiesUtils.getValue("pingU57");
            pingUDP[28] = PropertiesUtils.getValue("pingU61");
            pingUDP[29] = PropertiesUtils.getValue("pingU62");
            pingUDP[30] = PropertiesUtils.getValue("pingU63");
            pingUDP[31] = PropertiesUtils.getValue("pingU64");
            pingUDP[32] = PropertiesUtils.getValue("pingU65");
            pingUDP[33] = PropertiesUtils.getValue("pingU66");
            pingUDP[33] = PropertiesUtils.getValue("pingU67");
            pingUDP[35] = PropertiesUtils.getValue("pingU71");
            pingUDP[36] = PropertiesUtils.getValue("pingU72");
            pingUDP[37] = PropertiesUtils.getValue("pingU73");
            pingUDP[38] = PropertiesUtils.getValue("pingU74");
            pingUDP[39] = PropertiesUtils.getValue("pingU75");
            pingUDP[40] = PropertiesUtils.getValue("pingU76");
            pingUDP[41] = PropertiesUtils.getValue("pingU77");
            pingUDP[42] = PropertiesUtils.getValue("pingU81");
            pingUDP[43] = PropertiesUtils.getValue("pingU82");
            pingUDP[44] = PropertiesUtils.getValue("pingU83");
            pingUDP[45] = PropertiesUtils.getValue("pingU84");
            pingUDP[46] = PropertiesUtils.getValue("pingU85");
            pingUDP[47] = PropertiesUtils.getValue("pingU86");
            pingUDP[48] = PropertiesUtils.getValue("pingU87");
        } catch (IOException e) { }
        return R.ok().put("pingUDP",pingUDP);
    }

    @RequestMapping("/gettrICMP")
    public R gettrICMP() throws IOException {
        String[] trICMP = new String[49];
        try {
            trICMP[0] = PropertiesUtils.getValue("trI11");
            trICMP[1] = PropertiesUtils.getValue("trI12");
            trICMP[2] = PropertiesUtils.getValue("trI13");
            trICMP[3] = PropertiesUtils.getValue("trI14");
            trICMP[4] = PropertiesUtils.getValue("trI15");
            trICMP[5] = PropertiesUtils.getValue("trI16");
            trICMP[6] = PropertiesUtils.getValue("trI17");
            trICMP[7] = PropertiesUtils.getValue("trI21");
            trICMP[8] = PropertiesUtils.getValue("trI22");
            trICMP[9] = PropertiesUtils.getValue("trI23");
            trICMP[10] = PropertiesUtils.getValue("trI24");
            trICMP[11] = PropertiesUtils.getValue("trI25");
            trICMP[12] = PropertiesUtils.getValue("trI26");
            trICMP[13] = PropertiesUtils.getValue("trI27");
            trICMP[14] = PropertiesUtils.getValue("trI31");
            trICMP[15] = PropertiesUtils.getValue("trI32");
            trICMP[16] = PropertiesUtils.getValue("trI33");
            trICMP[17] = PropertiesUtils.getValue("trI34");
            trICMP[18] = PropertiesUtils.getValue("trI35");
            trICMP[19] = PropertiesUtils.getValue("trI36");
            trICMP[20] = PropertiesUtils.getValue("trI37");
            trICMP[21] = PropertiesUtils.getValue("trI41");
            trICMP[22] = PropertiesUtils.getValue("trI42");
            trICMP[23] = PropertiesUtils.getValue("trI43");
            trICMP[24] = PropertiesUtils.getValue("trI44");
            trICMP[25] = PropertiesUtils.getValue("trI45");
            trICMP[26] = PropertiesUtils.getValue("trI46");
            trICMP[27] = PropertiesUtils.getValue("trI47");
            trICMP[28] = PropertiesUtils.getValue("trI51");
            trICMP[29] = PropertiesUtils.getValue("trI52");
            trICMP[30] = PropertiesUtils.getValue("trI53");
            trICMP[31] = PropertiesUtils.getValue("trI54");
            trICMP[32] = PropertiesUtils.getValue("trI55");
            trICMP[33] = PropertiesUtils.getValue("trI56");
            trICMP[34] = PropertiesUtils.getValue("trI57");
            trICMP[35] = PropertiesUtils.getValue("trI61");
            trICMP[36] = PropertiesUtils.getValue("trI62");
            trICMP[37] = PropertiesUtils.getValue("trI63");
            trICMP[38] = PropertiesUtils.getValue("trI64");
            trICMP[39] = PropertiesUtils.getValue("trI65");
            trICMP[40] = PropertiesUtils.getValue("trI66");
            trICMP[41] = PropertiesUtils.getValue("trI67");
            trICMP[42] = PropertiesUtils.getValue("trI71");
            trICMP[43] = PropertiesUtils.getValue("trI72");
            trICMP[44] = PropertiesUtils.getValue("trI73");
            trICMP[45] = PropertiesUtils.getValue("trI74");
            trICMP[46] = PropertiesUtils.getValue("trI75");
            trICMP[47] = PropertiesUtils.getValue("trI76");
            trICMP[48] = PropertiesUtils.getValue("trI77");
        } catch (IOException e) { }
        return R.ok().put("trICMP",trICMP);
    }

    @RequestMapping("/gettrTCP")
    public R gettrTCP() throws IOException {
        String[] trTCP = new String[49];
        try {
            trTCP[0] = PropertiesUtils.getValue("trT11");
            trTCP[1] = PropertiesUtils.getValue("trT12");
            trTCP[2] = PropertiesUtils.getValue("trT13");
            trTCP[3] = PropertiesUtils.getValue("trT14");
            trTCP[4] = PropertiesUtils.getValue("trT15");
            trTCP[5] = PropertiesUtils.getValue("trT16");
            trTCP[6] = PropertiesUtils.getValue("trT17");
            trTCP[7] = PropertiesUtils.getValue("trT21");
            trTCP[8] = PropertiesUtils.getValue("trT22");
            trTCP[9] = PropertiesUtils.getValue("trT23");
            trTCP[10] = PropertiesUtils.getValue("trT24");
            trTCP[11] = PropertiesUtils.getValue("trT25");
            trTCP[12] = PropertiesUtils.getValue("trT26");
            trTCP[13] = PropertiesUtils.getValue("trT27");
            trTCP[14] = PropertiesUtils.getValue("trT31");
            trTCP[15] = PropertiesUtils.getValue("trT32");
            trTCP[16] = PropertiesUtils.getValue("trT33");
            trTCP[17] = PropertiesUtils.getValue("trT34");
            trTCP[18] = PropertiesUtils.getValue("trT35");
            trTCP[19] = PropertiesUtils.getValue("trT36");
            trTCP[20] = PropertiesUtils.getValue("trT37");
            trTCP[21] = PropertiesUtils.getValue("trT41");
            trTCP[22] = PropertiesUtils.getValue("trT42");
            trTCP[23] = PropertiesUtils.getValue("trT43");
            trTCP[24] = PropertiesUtils.getValue("trT44");
            trTCP[25] = PropertiesUtils.getValue("trT45");
            trTCP[26] = PropertiesUtils.getValue("trT46");
            trTCP[27] = PropertiesUtils.getValue("trT47");
            trTCP[28] = PropertiesUtils.getValue("trT51");
            trTCP[29] = PropertiesUtils.getValue("trT52");
            trTCP[30] = PropertiesUtils.getValue("trT53");
            trTCP[31] = PropertiesUtils.getValue("trT54");
            trTCP[32] = PropertiesUtils.getValue("trT55");
            trTCP[33] = PropertiesUtils.getValue("trT56");
            trTCP[34] = PropertiesUtils.getValue("trT57");
            trTCP[35] = PropertiesUtils.getValue("trT61");
            trTCP[36] = PropertiesUtils.getValue("trT62");
            trTCP[37] = PropertiesUtils.getValue("trT63");
            trTCP[38] = PropertiesUtils.getValue("trT64");
            trTCP[39] = PropertiesUtils.getValue("trT65");
            trTCP[40] = PropertiesUtils.getValue("trT66");
            trTCP[41] = PropertiesUtils.getValue("trT67");
            trTCP[42] = PropertiesUtils.getValue("trT71");
            trTCP[43] = PropertiesUtils.getValue("trT72");
            trTCP[44] = PropertiesUtils.getValue("trT73");
            trTCP[45] = PropertiesUtils.getValue("trT74");
            trTCP[46] = PropertiesUtils.getValue("trT75");
            trTCP[47] = PropertiesUtils.getValue("trT76");
            trTCP[48] = PropertiesUtils.getValue("trT77");
        } catch (IOException e) { }
        return R.ok().put("trTCP",trTCP);
    }

    @RequestMapping("/getslaTCP")
    public R getslaTCP() throws IOException {
        String[] slaTCP = new String[133];
        try {
            slaTCP[0] = PropertiesUtils.getValue("slaT11");
            slaTCP[1] = PropertiesUtils.getValue("slaT12");
            slaTCP[2] = PropertiesUtils.getValue("slaT13");
            slaTCP[3] = PropertiesUtils.getValue("slaT14");
            slaTCP[4] = PropertiesUtils.getValue("slaT15");
            slaTCP[5] = PropertiesUtils.getValue("slaT16");
            slaTCP[6] = PropertiesUtils.getValue("slaT17");
            slaTCP[7] = PropertiesUtils.getValue("slaT21");
            slaTCP[8] = PropertiesUtils.getValue("slaT22");
            slaTCP[9] = PropertiesUtils.getValue("slaT23");
            slaTCP[10] = PropertiesUtils.getValue("slaT24");
            slaTCP[11] = PropertiesUtils.getValue("slaT25");
            slaTCP[12] = PropertiesUtils.getValue("slaT26");
            slaTCP[13] = PropertiesUtils.getValue("slaT27");
            slaTCP[14] = PropertiesUtils.getValue("slaT31");
            slaTCP[15] = PropertiesUtils.getValue("slaT32");
            slaTCP[16] = PropertiesUtils.getValue("slaT33");
            slaTCP[17] = PropertiesUtils.getValue("slaT34");
            slaTCP[18] = PropertiesUtils.getValue("slaT35");
            slaTCP[19] = PropertiesUtils.getValue("slaT36");
            slaTCP[20] = PropertiesUtils.getValue("slaT37");
            slaTCP[21] = PropertiesUtils.getValue("slaT41");
            slaTCP[22] = PropertiesUtils.getValue("slaT42");
            slaTCP[23] = PropertiesUtils.getValue("slaT43");
            slaTCP[24] = PropertiesUtils.getValue("slaT44");
            slaTCP[25] = PropertiesUtils.getValue("slaT45");
            slaTCP[26] = PropertiesUtils.getValue("slaT46");
            slaTCP[27] = PropertiesUtils.getValue("slaT47");
            slaTCP[28] = PropertiesUtils.getValue("slaT51");
            slaTCP[29] = PropertiesUtils.getValue("slaT52");
            slaTCP[30] = PropertiesUtils.getValue("slaT53");
            slaTCP[31] = PropertiesUtils.getValue("slaT54");
            slaTCP[32] = PropertiesUtils.getValue("slaT55");
            slaTCP[33] = PropertiesUtils.getValue("slaT56");
            slaTCP[34] = PropertiesUtils.getValue("slaT57");
            slaTCP[35] = PropertiesUtils.getValue("slaT61");
            slaTCP[36] = PropertiesUtils.getValue("slaT62");
            slaTCP[37] = PropertiesUtils.getValue("slaT63");
            slaTCP[38] = PropertiesUtils.getValue("slaT64");
            slaTCP[39] = PropertiesUtils.getValue("slaT65");
            slaTCP[40] = PropertiesUtils.getValue("slaT66");
            slaTCP[41] = PropertiesUtils.getValue("slaT67");
            slaTCP[42] = PropertiesUtils.getValue("slaT71");
            slaTCP[43] = PropertiesUtils.getValue("slaT72");
            slaTCP[44] = PropertiesUtils.getValue("slaT73");
            slaTCP[45] = PropertiesUtils.getValue("slaT74");
            slaTCP[46] = PropertiesUtils.getValue("slaT75");
            slaTCP[47] = PropertiesUtils.getValue("slaT76");
            slaTCP[48] = PropertiesUtils.getValue("slaT77");
            slaTCP[49] = PropertiesUtils.getValue("slaT81");
            slaTCP[50] = PropertiesUtils.getValue("slaT82");
            slaTCP[51] = PropertiesUtils.getValue("slaT83");
            slaTCP[52] = PropertiesUtils.getValue("slaT84");
            slaTCP[53] = PropertiesUtils.getValue("slaT85");
            slaTCP[54] = PropertiesUtils.getValue("slaT86");
            slaTCP[55] = PropertiesUtils.getValue("slaT87");
            slaTCP[56] = PropertiesUtils.getValue("slaT91");
            slaTCP[57] = PropertiesUtils.getValue("slaT92");
            slaTCP[58] = PropertiesUtils.getValue("slaT93");
            slaTCP[59] = PropertiesUtils.getValue("slaT94");
            slaTCP[60] = PropertiesUtils.getValue("slaT95");
            slaTCP[61] = PropertiesUtils.getValue("slaT96");
            slaTCP[62] = PropertiesUtils.getValue("slaT97");
            slaTCP[63] = PropertiesUtils.getValue("slaT101");
            slaTCP[64] = PropertiesUtils.getValue("slaT102");
            slaTCP[65] = PropertiesUtils.getValue("slaT103");
            slaTCP[66] = PropertiesUtils.getValue("slaT104");
            slaTCP[67] = PropertiesUtils.getValue("slaT105");
            slaTCP[68] = PropertiesUtils.getValue("slaT106");
            slaTCP[69] = PropertiesUtils.getValue("slaT107");
            slaTCP[70] = PropertiesUtils.getValue("slaT111");
            slaTCP[71] = PropertiesUtils.getValue("slaT112");
            slaTCP[72] = PropertiesUtils.getValue("slaT113");
            slaTCP[73] = PropertiesUtils.getValue("slaT114");
            slaTCP[74] = PropertiesUtils.getValue("slaT115");
            slaTCP[75] = PropertiesUtils.getValue("slaT116");
            slaTCP[76] = PropertiesUtils.getValue("slaT117");
            slaTCP[77] = PropertiesUtils.getValue("slaT121");
            slaTCP[78] = PropertiesUtils.getValue("slaT122");
            slaTCP[79] = PropertiesUtils.getValue("slaT123");
            slaTCP[80] = PropertiesUtils.getValue("slaT124");
            slaTCP[81] = PropertiesUtils.getValue("slaT125");
            slaTCP[82] = PropertiesUtils.getValue("slaT126");
            slaTCP[83] = PropertiesUtils.getValue("slaT127");
            slaTCP[84] = PropertiesUtils.getValue("slaT131");
            slaTCP[85] = PropertiesUtils.getValue("slaT132");
            slaTCP[86] = PropertiesUtils.getValue("slaT133");
            slaTCP[87] = PropertiesUtils.getValue("slaT134");
            slaTCP[88] = PropertiesUtils.getValue("slaT135");
            slaTCP[89] = PropertiesUtils.getValue("slaT136");
            slaTCP[90] = PropertiesUtils.getValue("slaT137");
            slaTCP[91] = PropertiesUtils.getValue("slaT141");
            slaTCP[92] = PropertiesUtils.getValue("slaT142");
            slaTCP[93] = PropertiesUtils.getValue("slaT143");
            slaTCP[94] = PropertiesUtils.getValue("slaT144");
            slaTCP[95] = PropertiesUtils.getValue("slaT145");
            slaTCP[96] = PropertiesUtils.getValue("slaT146");
            slaTCP[97] = PropertiesUtils.getValue("slaT147");
            slaTCP[98] = PropertiesUtils.getValue("slaT151");
            slaTCP[99] = PropertiesUtils.getValue("slaT152");
            slaTCP[100] = PropertiesUtils.getValue("slaT153");
            slaTCP[101] = PropertiesUtils.getValue("slaT154");
            slaTCP[102] = PropertiesUtils.getValue("slaT155");
            slaTCP[103] = PropertiesUtils.getValue("slaT156");
            slaTCP[104] = PropertiesUtils.getValue("slaT157");
            slaTCP[105] = PropertiesUtils.getValue("slaT161");
            slaTCP[106] = PropertiesUtils.getValue("slaT162");
            slaTCP[107] = PropertiesUtils.getValue("slaT163");
            slaTCP[108] = PropertiesUtils.getValue("slaT164");
            slaTCP[109] = PropertiesUtils.getValue("slaT165");
            slaTCP[110] = PropertiesUtils.getValue("slaT166");
            slaTCP[111] = PropertiesUtils.getValue("slaT167");
            slaTCP[112] = PropertiesUtils.getValue("slaT171");
            slaTCP[113] = PropertiesUtils.getValue("slaT172");
            slaTCP[114] = PropertiesUtils.getValue("slaT173");
            slaTCP[115] = PropertiesUtils.getValue("slaT174");
            slaTCP[116] = PropertiesUtils.getValue("slaT175");
            slaTCP[117] = PropertiesUtils.getValue("slaT176");
            slaTCP[118] = PropertiesUtils.getValue("slaT177");
            slaTCP[119] = PropertiesUtils.getValue("slaT181");
            slaTCP[120] = PropertiesUtils.getValue("slaT182");
            slaTCP[121] = PropertiesUtils.getValue("slaT183");
            slaTCP[122] = PropertiesUtils.getValue("slaT184");
            slaTCP[123] = PropertiesUtils.getValue("slaT185");
            slaTCP[124] = PropertiesUtils.getValue("slaT186");
            slaTCP[125] = PropertiesUtils.getValue("slaT187");
            slaTCP[126] = PropertiesUtils.getValue("slaT191");
            slaTCP[127] = PropertiesUtils.getValue("slaT192");
            slaTCP[128] = PropertiesUtils.getValue("slaT193");
            slaTCP[129] = PropertiesUtils.getValue("slaT194");
            slaTCP[130] = PropertiesUtils.getValue("slaT195");
            slaTCP[131] = PropertiesUtils.getValue("slaT196");
            slaTCP[132] = PropertiesUtils.getValue("slaT197");
        } catch (IOException e) { }
        return R.ok().put("slaTCP",slaTCP);
    }

    @RequestMapping("/getslaUDP")
    public R getslaUDP() throws IOException {
        String[] slaUDP = new String[133];
        try {
            slaUDP[0] = PropertiesUtils.getValue("slaU11");
            slaUDP[1] = PropertiesUtils.getValue("slaU12");
            slaUDP[2] = PropertiesUtils.getValue("slaU13");
            slaUDP[3] = PropertiesUtils.getValue("slaU14");
            slaUDP[4] = PropertiesUtils.getValue("slaU15");
            slaUDP[5] = PropertiesUtils.getValue("slaU16");
            slaUDP[6] = PropertiesUtils.getValue("slaU17");
            slaUDP[7] = PropertiesUtils.getValue("slaU21");
            slaUDP[8] = PropertiesUtils.getValue("slaU22");
            slaUDP[9] = PropertiesUtils.getValue("slaU23");
            slaUDP[10] = PropertiesUtils.getValue("slaU24");
            slaUDP[11] = PropertiesUtils.getValue("slaU25");
            slaUDP[12] = PropertiesUtils.getValue("slaU26");
            slaUDP[13] = PropertiesUtils.getValue("slaU27");
            slaUDP[14] = PropertiesUtils.getValue("slaU31");
            slaUDP[15] = PropertiesUtils.getValue("slaU32");
            slaUDP[16] = PropertiesUtils.getValue("slaU33");
            slaUDP[17] = PropertiesUtils.getValue("slaU34");
            slaUDP[18] = PropertiesUtils.getValue("slaU35");
            slaUDP[19] = PropertiesUtils.getValue("slaU36");
            slaUDP[20] = PropertiesUtils.getValue("slaU37");
            slaUDP[21] = PropertiesUtils.getValue("slaU41");
            slaUDP[22] = PropertiesUtils.getValue("slaU42");
            slaUDP[23] = PropertiesUtils.getValue("slaU43");
            slaUDP[24] = PropertiesUtils.getValue("slaU44");
            slaUDP[25] = PropertiesUtils.getValue("slaU45");
            slaUDP[26] = PropertiesUtils.getValue("slaU46");
            slaUDP[27] = PropertiesUtils.getValue("slaU47");
            slaUDP[28] = PropertiesUtils.getValue("slaU51");
            slaUDP[29] = PropertiesUtils.getValue("slaU52");
            slaUDP[30] = PropertiesUtils.getValue("slaU53");
            slaUDP[31] = PropertiesUtils.getValue("slaU54");
            slaUDP[32] = PropertiesUtils.getValue("slaU55");
            slaUDP[33] = PropertiesUtils.getValue("slaU56");
            slaUDP[34] = PropertiesUtils.getValue("slaU57");
            slaUDP[35] = PropertiesUtils.getValue("slaU61");
            slaUDP[36] = PropertiesUtils.getValue("slaU62");
            slaUDP[37] = PropertiesUtils.getValue("slaU63");
            slaUDP[38] = PropertiesUtils.getValue("slaU64");
            slaUDP[39] = PropertiesUtils.getValue("slaU65");
            slaUDP[40] = PropertiesUtils.getValue("slaU66");
            slaUDP[41] = PropertiesUtils.getValue("slaU67");
            slaUDP[42] = PropertiesUtils.getValue("slaU71");
            slaUDP[43] = PropertiesUtils.getValue("slaU72");
            slaUDP[44] = PropertiesUtils.getValue("slaU73");
            slaUDP[45] = PropertiesUtils.getValue("slaU74");
            slaUDP[46] = PropertiesUtils.getValue("slaU75");
            slaUDP[47] = PropertiesUtils.getValue("slaU76");
            slaUDP[48] = PropertiesUtils.getValue("slaU77");
            slaUDP[49] = PropertiesUtils.getValue("slaU81");
            slaUDP[50] = PropertiesUtils.getValue("slaU82");
            slaUDP[51] = PropertiesUtils.getValue("slaU83");
            slaUDP[52] = PropertiesUtils.getValue("slaU84");
            slaUDP[53] = PropertiesUtils.getValue("slaU85");
            slaUDP[54] = PropertiesUtils.getValue("slaU86");
            slaUDP[55] = PropertiesUtils.getValue("slaU87");
            slaUDP[56] = PropertiesUtils.getValue("slaU91");
            slaUDP[57] = PropertiesUtils.getValue("slaU92");
            slaUDP[58] = PropertiesUtils.getValue("slaU93");
            slaUDP[59] = PropertiesUtils.getValue("slaU94");
            slaUDP[60] = PropertiesUtils.getValue("slaU95");
            slaUDP[61] = PropertiesUtils.getValue("slaU96");
            slaUDP[62] = PropertiesUtils.getValue("slaU97");
            slaUDP[63] = PropertiesUtils.getValue("slaU101");
            slaUDP[64] = PropertiesUtils.getValue("slaU102");
            slaUDP[65] = PropertiesUtils.getValue("slaU103");
            slaUDP[66] = PropertiesUtils.getValue("slaU104");
            slaUDP[67] = PropertiesUtils.getValue("slaU105");
            slaUDP[68] = PropertiesUtils.getValue("slaU106");
            slaUDP[69] = PropertiesUtils.getValue("slaU107");
            slaUDP[70] = PropertiesUtils.getValue("slaU111");
            slaUDP[71] = PropertiesUtils.getValue("slaU112");
            slaUDP[72] = PropertiesUtils.getValue("slaU113");
            slaUDP[73] = PropertiesUtils.getValue("slaU114");
            slaUDP[74] = PropertiesUtils.getValue("slaU115");
            slaUDP[75] = PropertiesUtils.getValue("slaU116");
            slaUDP[76] = PropertiesUtils.getValue("slaU117");
            slaUDP[77] = PropertiesUtils.getValue("slaU121");
            slaUDP[78] = PropertiesUtils.getValue("slaU122");
            slaUDP[79] = PropertiesUtils.getValue("slaU123");
            slaUDP[80] = PropertiesUtils.getValue("slaU124");
            slaUDP[81] = PropertiesUtils.getValue("slaU125");
            slaUDP[82] = PropertiesUtils.getValue("slaU126");
            slaUDP[83] = PropertiesUtils.getValue("slaU127");
            slaUDP[84] = PropertiesUtils.getValue("slaU131");
            slaUDP[85] = PropertiesUtils.getValue("slaU132");
            slaUDP[86] = PropertiesUtils.getValue("slaU133");
            slaUDP[87] = PropertiesUtils.getValue("slaU134");
            slaUDP[88] = PropertiesUtils.getValue("slaU135");
            slaUDP[89] = PropertiesUtils.getValue("slaU136");
            slaUDP[90] = PropertiesUtils.getValue("slaU137");
            slaUDP[91] = PropertiesUtils.getValue("slaU141");
            slaUDP[92] = PropertiesUtils.getValue("slaU142");
            slaUDP[93] = PropertiesUtils.getValue("slaU143");
            slaUDP[94] = PropertiesUtils.getValue("slaU144");
            slaUDP[95] = PropertiesUtils.getValue("slaU145");
            slaUDP[96] = PropertiesUtils.getValue("slaU146");
            slaUDP[97] = PropertiesUtils.getValue("slaU147");
            slaUDP[98] = PropertiesUtils.getValue("slaU151");
            slaUDP[99] = PropertiesUtils.getValue("slaU152");
            slaUDP[100] = PropertiesUtils.getValue("slaU153");
            slaUDP[101] = PropertiesUtils.getValue("slaU154");
            slaUDP[102] = PropertiesUtils.getValue("slaU155");
            slaUDP[103] = PropertiesUtils.getValue("slaU156");
            slaUDP[104] = PropertiesUtils.getValue("slaU157");
            slaUDP[105] = PropertiesUtils.getValue("slaU161");
            slaUDP[106] = PropertiesUtils.getValue("slaU162");
            slaUDP[107] = PropertiesUtils.getValue("slaU163");
            slaUDP[108] = PropertiesUtils.getValue("slaU164");
            slaUDP[109] = PropertiesUtils.getValue("slaU165");
            slaUDP[110] = PropertiesUtils.getValue("slaU166");
            slaUDP[111] = PropertiesUtils.getValue("slaU167");
            slaUDP[112] = PropertiesUtils.getValue("slaU171");
            slaUDP[113] = PropertiesUtils.getValue("slaU172");
            slaUDP[114] = PropertiesUtils.getValue("slaU173");
            slaUDP[115] = PropertiesUtils.getValue("slaU174");
            slaUDP[116] = PropertiesUtils.getValue("slaU175");
            slaUDP[117] = PropertiesUtils.getValue("slaU176");
            slaUDP[118] = PropertiesUtils.getValue("slaU177");
            slaUDP[119] = PropertiesUtils.getValue("slaU181");
            slaUDP[120] = PropertiesUtils.getValue("slaU182");
            slaUDP[121] = PropertiesUtils.getValue("slaU183");
            slaUDP[122] = PropertiesUtils.getValue("slaU184");
            slaUDP[123] = PropertiesUtils.getValue("slaU185");
            slaUDP[124] = PropertiesUtils.getValue("slaU186");
            slaUDP[125] = PropertiesUtils.getValue("slaU187");
            slaUDP[126] = PropertiesUtils.getValue("slaU191");
            slaUDP[127] = PropertiesUtils.getValue("slaU192");
            slaUDP[128] = PropertiesUtils.getValue("slaU193");
            slaUDP[129] = PropertiesUtils.getValue("slaU194");
            slaUDP[130] = PropertiesUtils.getValue("slaU195");
            slaUDP[131] = PropertiesUtils.getValue("slaU196");
            slaUDP[132] = PropertiesUtils.getValue("slaU197");
        } catch (IOException e) { }
        return R.ok().put("slaUDP",slaUDP);
    }

    @RequestMapping("/getdns")
    public R getdns() throws IOException {
        String[] dns = new String[14];
        try {
            dns[0] = PropertiesUtils.getValue("dns11");
            dns[1] = PropertiesUtils.getValue("dns12");
            dns[2] = PropertiesUtils.getValue("dns13");
            dns[3] = PropertiesUtils.getValue("dns14");
            dns[4] = PropertiesUtils.getValue("dns15");
            dns[5] = PropertiesUtils.getValue("dns16");
            dns[6] = PropertiesUtils.getValue("dns17");
            dns[7] = PropertiesUtils.getValue("dns21");
            dns[8] = PropertiesUtils.getValue("dns22");
            dns[9] = PropertiesUtils.getValue("dns23");
            dns[10] = PropertiesUtils.getValue("dns24");
            dns[11] = PropertiesUtils.getValue("dns25");
            dns[12] = PropertiesUtils.getValue("dns26");
            dns[13] = PropertiesUtils.getValue("dns27");
        } catch (IOException e) {

        }
        return R.ok().put("dns",dns);
    }

    @RequestMapping("/getdhcp")
    public R getdhcp() throws IOException {
        String[] dhcp = new String[14];
        try {
            dhcp[0] = PropertiesUtils.getValue("dhcp11");
            dhcp[1] = PropertiesUtils.getValue("dhcp12");
            dhcp[2] = PropertiesUtils.getValue("dhcp13");
            dhcp[3] = PropertiesUtils.getValue("dhcp14");
            dhcp[4] = PropertiesUtils.getValue("dhcp15");
            dhcp[5] = PropertiesUtils.getValue("dhcp16");
            dhcp[6] = PropertiesUtils.getValue("dhcp17");
            dhcp[7] = PropertiesUtils.getValue("dhcp21");
            dhcp[8] = PropertiesUtils.getValue("dhcp22");
            dhcp[9] = PropertiesUtils.getValue("dhcp23");
            dhcp[10] = PropertiesUtils.getValue("dhcp24");
            dhcp[11] = PropertiesUtils.getValue("dhcp25");
            dhcp[12] = PropertiesUtils.getValue("dhcp26");
            dhcp[13] = PropertiesUtils.getValue("dhcp27");
        } catch (IOException e) {

        }
        return R.ok().put("dhcp",dhcp);
    }

    @RequestMapping("/getadsl")
    public R getadsl() throws IOException {
        String[] adsl = new String[21];
        try {
            adsl[0] = PropertiesUtils.getValue("adsl11");
            adsl[1] = PropertiesUtils.getValue("adsl12");
            adsl[2] = PropertiesUtils.getValue("adsl13");
            adsl[3] = PropertiesUtils.getValue("adsl14");
            adsl[4] = PropertiesUtils.getValue("adsl15");
            adsl[5] = PropertiesUtils.getValue("adsl16");
            adsl[6] = PropertiesUtils.getValue("adsl17");
            adsl[7] = PropertiesUtils.getValue("adsl21");
            adsl[8] = PropertiesUtils.getValue("adsl22");
            adsl[9] = PropertiesUtils.getValue("adsl23");
            adsl[10] = PropertiesUtils.getValue("adsl24");
            adsl[11] = PropertiesUtils.getValue("adsl25");
            adsl[12] = PropertiesUtils.getValue("adsl26");
            adsl[13] = PropertiesUtils.getValue("adsl27");
            adsl[14] = PropertiesUtils.getValue("adsl31");
            adsl[15] = PropertiesUtils.getValue("adsl32");
            adsl[16] = PropertiesUtils.getValue("adsl33");
            adsl[17] = PropertiesUtils.getValue("adsl34");
            adsl[18] = PropertiesUtils.getValue("adsl35");
            adsl[19] = PropertiesUtils.getValue("adsl36");
            adsl[20] = PropertiesUtils.getValue("adsl37");
        } catch (IOException e) {

        }
        return R.ok().put("adsl",adsl);
    }

    @RequestMapping("/getradius")
    public R getradius() throws IOException {
        String[] radius = new String[14];
        try {
            radius[0] = PropertiesUtils.getValue("radius11");
            radius[1] = PropertiesUtils.getValue("radius12");
            radius[2] = PropertiesUtils.getValue("radius13");
            radius[3] = PropertiesUtils.getValue("radius14");
            radius[4] = PropertiesUtils.getValue("radius15");
            radius[5] = PropertiesUtils.getValue("radius16");
            radius[6] = PropertiesUtils.getValue("radius17");
            radius[7] = PropertiesUtils.getValue("radius21");
            radius[8] = PropertiesUtils.getValue("radius22");
            radius[9] = PropertiesUtils.getValue("radius23");
            radius[10] = PropertiesUtils.getValue("radius24");
            radius[11] = PropertiesUtils.getValue("radius25");
            radius[12] = PropertiesUtils.getValue("radius26");
            radius[13] = PropertiesUtils.getValue("radius27");
        } catch (IOException e) {

        }
        return R.ok().put("radius",radius);
    }

    @RequestMapping("/getftpUpload")
    public R getftpUpload() throws IOException {
        String[] ftpUpload = new String[35];
        try {
            ftpUpload[0] = PropertiesUtils.getValue("ftpU11");
            ftpUpload[1] = PropertiesUtils.getValue("ftpU12");
            ftpUpload[2] = PropertiesUtils.getValue("ftpU13");
            ftpUpload[3] = PropertiesUtils.getValue("ftpU14");
            ftpUpload[4] = PropertiesUtils.getValue("ftpU15");
            ftpUpload[5] = PropertiesUtils.getValue("ftpU16");
            ftpUpload[6] = PropertiesUtils.getValue("ftpU17");
            ftpUpload[7] = PropertiesUtils.getValue("ftpU21");
            ftpUpload[8] = PropertiesUtils.getValue("ftpU22");
            ftpUpload[9] = PropertiesUtils.getValue("ftpU23");
            ftpUpload[10] = PropertiesUtils.getValue("ftpU24");
            ftpUpload[11] = PropertiesUtils.getValue("ftpU25");
            ftpUpload[12] = PropertiesUtils.getValue("ftpU26");
            ftpUpload[13] = PropertiesUtils.getValue("ftpU27");
            ftpUpload[14] = PropertiesUtils.getValue("ftpU31");
            ftpUpload[15] = PropertiesUtils.getValue("ftpU32");
            ftpUpload[16] = PropertiesUtils.getValue("ftpU33");
            ftpUpload[17] = PropertiesUtils.getValue("ftpU34");
            ftpUpload[18] = PropertiesUtils.getValue("ftpU35");
            ftpUpload[19] = PropertiesUtils.getValue("ftpU36");
            ftpUpload[20] = PropertiesUtils.getValue("ftpU37");
            ftpUpload[21] = PropertiesUtils.getValue("ftpU41");
            ftpUpload[22] = PropertiesUtils.getValue("ftpU42");
            ftpUpload[23] = PropertiesUtils.getValue("ftpU43");
            ftpUpload[24] = PropertiesUtils.getValue("ftpU44");
            ftpUpload[25] = PropertiesUtils.getValue("ftpU45");
            ftpUpload[26] = PropertiesUtils.getValue("ftpU46");
            ftpUpload[27] = PropertiesUtils.getValue("ftpU47");
            ftpUpload[28] = PropertiesUtils.getValue("ftpU51");
            ftpUpload[29] = PropertiesUtils.getValue("ftpU52");
            ftpUpload[30] = PropertiesUtils.getValue("ftpU53");
            ftpUpload[31] = PropertiesUtils.getValue("ftpU54");
            ftpUpload[32] = PropertiesUtils.getValue("ftpU55");
            ftpUpload[33] = PropertiesUtils.getValue("ftpU56");
            ftpUpload[34] = PropertiesUtils.getValue("ftpU57");
        } catch (IOException e) {

        }
        return R.ok().put("ftpUpload",ftpUpload);
    }

    @RequestMapping("/getftpDownload")
    public R getftpDownload() throws IOException {
        String[] ftpDownload = new String[35];
        try {
            ftpDownload[0] = PropertiesUtils.getValue("ftpD11");
            ftpDownload[1] = PropertiesUtils.getValue("ftpD12");
            ftpDownload[2] = PropertiesUtils.getValue("ftpD13");
            ftpDownload[3] = PropertiesUtils.getValue("ftpD14");
            ftpDownload[4] = PropertiesUtils.getValue("ftpD15");
            ftpDownload[5] = PropertiesUtils.getValue("ftpD16");
            ftpDownload[6] = PropertiesUtils.getValue("ftpD17");
            ftpDownload[7] = PropertiesUtils.getValue("ftpD21");
            ftpDownload[8] = PropertiesUtils.getValue("ftpD22");
            ftpDownload[9] = PropertiesUtils.getValue("ftpD23");
            ftpDownload[10] = PropertiesUtils.getValue("ftpD24");
            ftpDownload[11] = PropertiesUtils.getValue("ftpD25");
            ftpDownload[12] = PropertiesUtils.getValue("ftpD26");
            ftpDownload[13] = PropertiesUtils.getValue("ftpD27");
            ftpDownload[14] = PropertiesUtils.getValue("ftpD31");
            ftpDownload[15] = PropertiesUtils.getValue("ftpD32");
            ftpDownload[16] = PropertiesUtils.getValue("ftpD33");
            ftpDownload[17] = PropertiesUtils.getValue("ftpD34");
            ftpDownload[18] = PropertiesUtils.getValue("ftpD35");
            ftpDownload[19] = PropertiesUtils.getValue("ftpD36");
            ftpDownload[20] = PropertiesUtils.getValue("ftpD37");
            ftpDownload[21] = PropertiesUtils.getValue("ftpD41");
            ftpDownload[22] = PropertiesUtils.getValue("ftpD42");
            ftpDownload[23] = PropertiesUtils.getValue("ftpD43");
            ftpDownload[24] = PropertiesUtils.getValue("ftpD44");
            ftpDownload[25] = PropertiesUtils.getValue("ftpD45");
            ftpDownload[26] = PropertiesUtils.getValue("ftpD46");
            ftpDownload[27] = PropertiesUtils.getValue("ftpD47");
            ftpDownload[28] = PropertiesUtils.getValue("ftpD51");
            ftpDownload[29] = PropertiesUtils.getValue("ftpD52");
            ftpDownload[30] = PropertiesUtils.getValue("ftpD53");
            ftpDownload[31] = PropertiesUtils.getValue("ftpD54");
            ftpDownload[32] = PropertiesUtils.getValue("ftpD55");
            ftpDownload[33] = PropertiesUtils.getValue("ftpD56");
            ftpDownload[34] = PropertiesUtils.getValue("ftpD57");
        } catch (IOException e) {

        }
        return R.ok().put("ftpDownload",ftpDownload);
    }

    @RequestMapping("/getwebDownload")
    public R getwebDownload() throws IOException {
        String[] webDownload = new String[28];
        try {
            webDownload[0] = PropertiesUtils.getValue("webD11");
            webDownload[1] = PropertiesUtils.getValue("webD12");
            webDownload[2] = PropertiesUtils.getValue("webD13");
            webDownload[3] = PropertiesUtils.getValue("webD14");
            webDownload[4] = PropertiesUtils.getValue("webD15");
            webDownload[5] = PropertiesUtils.getValue("webD16");
            webDownload[6] = PropertiesUtils.getValue("webD17");
            webDownload[7] = PropertiesUtils.getValue("webD21");
            webDownload[8] = PropertiesUtils.getValue("webD22");
            webDownload[9] = PropertiesUtils.getValue("webD23");
            webDownload[10] = PropertiesUtils.getValue("webD24");
            webDownload[11] = PropertiesUtils.getValue("webD25");
            webDownload[12] = PropertiesUtils.getValue("webD26");
            webDownload[13] = PropertiesUtils.getValue("webD27");
            webDownload[14] = PropertiesUtils.getValue("webD31");
            webDownload[15] = PropertiesUtils.getValue("webD32");
            webDownload[16] = PropertiesUtils.getValue("webD33");
            webDownload[17] = PropertiesUtils.getValue("webD34");
            webDownload[18] = PropertiesUtils.getValue("webD35");
            webDownload[19] = PropertiesUtils.getValue("webD36");
            webDownload[20] = PropertiesUtils.getValue("webD37");
            webDownload[21] = PropertiesUtils.getValue("webD41");
            webDownload[22] = PropertiesUtils.getValue("webD42");
            webDownload[23] = PropertiesUtils.getValue("webD43");
            webDownload[24] = PropertiesUtils.getValue("webD44");
            webDownload[25] = PropertiesUtils.getValue("webD45");
            webDownload[26] = PropertiesUtils.getValue("webD46");
            webDownload[27] = PropertiesUtils.getValue("webD47");
        } catch (IOException e) {

        }
        return R.ok().put("webDownload",webDownload);
    }

    @RequestMapping("/getwebpage")
    public R getwebpage() throws IOException {
        String[] webpage = new String[56];
        try {
            webpage[0] = PropertiesUtils.getValue("webP11");
            webpage[1] = PropertiesUtils.getValue("webP12");
            webpage[2] = PropertiesUtils.getValue("webP13");
            webpage[3] = PropertiesUtils.getValue("webP14");
            webpage[4] = PropertiesUtils.getValue("webP15");
            webpage[5] = PropertiesUtils.getValue("webP16");
            webpage[6] = PropertiesUtils.getValue("webP17");
            webpage[7] = PropertiesUtils.getValue("webP21");
            webpage[8] = PropertiesUtils.getValue("webP22");
            webpage[9] = PropertiesUtils.getValue("webP23");
            webpage[10] = PropertiesUtils.getValue("webP24");
            webpage[11] = PropertiesUtils.getValue("webP25");
            webpage[12] = PropertiesUtils.getValue("webP26");
            webpage[13] = PropertiesUtils.getValue("webP27");
            webpage[14] = PropertiesUtils.getValue("webP31");
            webpage[15] = PropertiesUtils.getValue("webP32");
            webpage[16] = PropertiesUtils.getValue("webP33");
            webpage[17] = PropertiesUtils.getValue("webP34");
            webpage[18] = PropertiesUtils.getValue("webP35");
            webpage[19] = PropertiesUtils.getValue("webP36");
            webpage[20] = PropertiesUtils.getValue("webP37");
            webpage[21] = PropertiesUtils.getValue("webP41");
            webpage[22] = PropertiesUtils.getValue("webP42");
            webpage[23] = PropertiesUtils.getValue("webP43");
            webpage[24] = PropertiesUtils.getValue("webP44");
            webpage[25] = PropertiesUtils.getValue("webP45");
            webpage[26] = PropertiesUtils.getValue("webP46");
            webpage[27] = PropertiesUtils.getValue("webP47");
            webpage[28] = PropertiesUtils.getValue("webP51");
            webpage[29] = PropertiesUtils.getValue("webP52");
            webpage[30] = PropertiesUtils.getValue("webP53");
            webpage[31] = PropertiesUtils.getValue("webP54");
            webpage[32] = PropertiesUtils.getValue("webP55");
            webpage[33] = PropertiesUtils.getValue("webP56");
            webpage[34] = PropertiesUtils.getValue("webP57");
            webpage[35] = PropertiesUtils.getValue("webP61");
            webpage[36] = PropertiesUtils.getValue("webP62");
            webpage[37] = PropertiesUtils.getValue("webP63");
            webpage[38] = PropertiesUtils.getValue("webP64");
            webpage[39] = PropertiesUtils.getValue("webP65");
            webpage[40] = PropertiesUtils.getValue("webP66");
            webpage[41] = PropertiesUtils.getValue("webP67");
            webpage[42] = PropertiesUtils.getValue("webP71");
            webpage[43] = PropertiesUtils.getValue("webP72");
            webpage[44] = PropertiesUtils.getValue("webP73");
            webpage[45] = PropertiesUtils.getValue("webP74");
            webpage[46] = PropertiesUtils.getValue("webP75");
            webpage[47] = PropertiesUtils.getValue("webP76");
            webpage[48] = PropertiesUtils.getValue("webP77");
            webpage[49] = PropertiesUtils.getValue("webP81");
            webpage[50] = PropertiesUtils.getValue("webP82");
            webpage[51] = PropertiesUtils.getValue("webP83");
            webpage[52] = PropertiesUtils.getValue("webP84");
            webpage[53] = PropertiesUtils.getValue("webP85");
            webpage[54] = PropertiesUtils.getValue("webP86");
            webpage[55] = PropertiesUtils.getValue("webP87");
        } catch (IOException e) {

        }
        return R.ok().put("webpage",webpage);
    }

    @RequestMapping("/getvideo")
    public R getvideo() throws IOException {
        String[] video = new String[77];
        try {
            video[0] = PropertiesUtils.getValue("video11");
            video[1] = PropertiesUtils.getValue("video12");
            video[2] = PropertiesUtils.getValue("video13");
            video[3] = PropertiesUtils.getValue("video14");
            video[4] = PropertiesUtils.getValue("video15");
            video[5] = PropertiesUtils.getValue("video16");
            video[6] = PropertiesUtils.getValue("video17");
            video[7] = PropertiesUtils.getValue("video21");
            video[8] = PropertiesUtils.getValue("video22");
            video[9] = PropertiesUtils.getValue("video23");
            video[10] = PropertiesUtils.getValue("video24");
            video[11] = PropertiesUtils.getValue("video25");
            video[12] = PropertiesUtils.getValue("video26");
            video[13] = PropertiesUtils.getValue("video27");
            video[14] = PropertiesUtils.getValue("video31");
            video[15] = PropertiesUtils.getValue("video32");
            video[16] = PropertiesUtils.getValue("video33");
            video[17] = PropertiesUtils.getValue("video34");
            video[18] = PropertiesUtils.getValue("video35");
            video[19] = PropertiesUtils.getValue("video36");
            video[20] = PropertiesUtils.getValue("video37");
            video[21] = PropertiesUtils.getValue("video41");
            video[22] = PropertiesUtils.getValue("video42");
            video[23] = PropertiesUtils.getValue("video43");
            video[24] = PropertiesUtils.getValue("video44");
            video[25] = PropertiesUtils.getValue("video45");
            video[26] = PropertiesUtils.getValue("video46");
            video[27] = PropertiesUtils.getValue("video47");
            video[28] = PropertiesUtils.getValue("video51");
            video[29] = PropertiesUtils.getValue("video52");
            video[30] = PropertiesUtils.getValue("video53");
            video[31] = PropertiesUtils.getValue("video54");
            video[32] = PropertiesUtils.getValue("video55");
            video[33] = PropertiesUtils.getValue("video56");
            video[34] = PropertiesUtils.getValue("video57");
            video[35] = PropertiesUtils.getValue("video61");
            video[36] = PropertiesUtils.getValue("video62");
            video[37] = PropertiesUtils.getValue("video63");
            video[38] = PropertiesUtils.getValue("video64");
            video[39] = PropertiesUtils.getValue("video65");
            video[40] = PropertiesUtils.getValue("video66");
            video[41] = PropertiesUtils.getValue("video67");
            video[42] = PropertiesUtils.getValue("video71");
            video[43] = PropertiesUtils.getValue("video72");
            video[44] = PropertiesUtils.getValue("video73");
            video[45] = PropertiesUtils.getValue("video74");
            video[46] = PropertiesUtils.getValue("video75");
            video[47] = PropertiesUtils.getValue("video76");
            video[48] = PropertiesUtils.getValue("video77");
            video[49] = PropertiesUtils.getValue("video81");
            video[50] = PropertiesUtils.getValue("video82");
            video[51] = PropertiesUtils.getValue("video83");
            video[52] = PropertiesUtils.getValue("video84");
            video[53] = PropertiesUtils.getValue("video85");
            video[54] = PropertiesUtils.getValue("video86");
            video[55] = PropertiesUtils.getValue("video87");
            video[56] = PropertiesUtils.getValue("video91");
            video[57] = PropertiesUtils.getValue("video92");
            video[58] = PropertiesUtils.getValue("video93");
            video[59] = PropertiesUtils.getValue("video94");
            video[60] = PropertiesUtils.getValue("video95");
            video[61] = PropertiesUtils.getValue("video96");
            video[62] = PropertiesUtils.getValue("video97");
            video[63] = PropertiesUtils.getValue("video101");
            video[64] = PropertiesUtils.getValue("video102");
            video[65] = PropertiesUtils.getValue("video103");
            video[66] = PropertiesUtils.getValue("video104");
            video[67] = PropertiesUtils.getValue("video105");
            video[68] = PropertiesUtils.getValue("video106");
            video[69] = PropertiesUtils.getValue("video107");
            video[70] = PropertiesUtils.getValue("video111");
            video[71] = PropertiesUtils.getValue("video112");
            video[72] = PropertiesUtils.getValue("video113");
            video[73] = PropertiesUtils.getValue("video114");
            video[74] = PropertiesUtils.getValue("video115");
            video[75] = PropertiesUtils.getValue("video116");
            video[76] = PropertiesUtils.getValue("video117");
        } catch (IOException e) {

        }
        return R.ok().put("video",video);
    }

    @RequestMapping("/getgame")
    public R getgame() throws IOException {
        String[] game = new String[35];
        try {
            game[0] = PropertiesUtils.getValue("game11");
            game[1] = PropertiesUtils.getValue("game12");
            game[2] = PropertiesUtils.getValue("game13");
            game[3] = PropertiesUtils.getValue("game14");
            game[4] = PropertiesUtils.getValue("game15");
            game[5] = PropertiesUtils.getValue("game16");
            game[6] = PropertiesUtils.getValue("game17");
            game[7] = PropertiesUtils.getValue("game21");
            game[8] = PropertiesUtils.getValue("game22");
            game[9] = PropertiesUtils.getValue("game23");
            game[10] = PropertiesUtils.getValue("game24");
            game[11] = PropertiesUtils.getValue("game25");
            game[12] = PropertiesUtils.getValue("game26");
            game[13] = PropertiesUtils.getValue("game27");
            game[14] = PropertiesUtils.getValue("game31");
            game[15] = PropertiesUtils.getValue("game32");
            game[16] = PropertiesUtils.getValue("game33");
            game[17] = PropertiesUtils.getValue("game34");
            game[18] = PropertiesUtils.getValue("game35");
            game[19] = PropertiesUtils.getValue("game36");
            game[20] = PropertiesUtils.getValue("game37");
            game[21] = PropertiesUtils.getValue("game41");
            game[22] = PropertiesUtils.getValue("game42");
            game[23] = PropertiesUtils.getValue("game43");
            game[24] = PropertiesUtils.getValue("game44");
            game[25] = PropertiesUtils.getValue("game45");
            game[26] = PropertiesUtils.getValue("game46");
            game[27] = PropertiesUtils.getValue("game47");
            game[28] = PropertiesUtils.getValue("game51");
            game[29] = PropertiesUtils.getValue("game52");
            game[30] = PropertiesUtils.getValue("game53");
            game[31] = PropertiesUtils.getValue("game54");
            game[32] = PropertiesUtils.getValue("game55");
            game[33] = PropertiesUtils.getValue("game56");
            game[34] = PropertiesUtils.getValue("game57");
        } catch (IOException e) {

        }
        return R.ok().put("game",game);
    }

    @RequestMapping("/reset")
    public R resetWeight() {
        String[] weightdefault = new String[23];
        try {
            weightdefault[0] = PropertiesUtils.getValue("connection_default");
            weightdefault[1] = PropertiesUtils.getValue("quality_default");
            weightdefault[2] = PropertiesUtils.getValue("browse_default");
            weightdefault[3] = PropertiesUtils.getValue("download_default");
            weightdefault[4] = PropertiesUtils.getValue("video_default");
            weightdefault[5] = PropertiesUtils.getValue("game_default");
            weightdefault[6] = PropertiesUtils.getValue("ping_icmp_def");
            weightdefault[7] = PropertiesUtils.getValue("ping_tcp_def");
            weightdefault[8] = PropertiesUtils.getValue("ping_udp_def");
            weightdefault[9] = PropertiesUtils.getValue("tr_tcp_def");
            weightdefault[10] = PropertiesUtils.getValue("tr_icmp_def");
            weightdefault[11] = PropertiesUtils.getValue("sla_tcp_def");
            weightdefault[12] = PropertiesUtils.getValue("sla_udp_def");
            weightdefault[13] = PropertiesUtils.getValue("dns_def");
            weightdefault[14] = PropertiesUtils.getValue("dhcp_def");
            weightdefault[15] = PropertiesUtils.getValue("adsl_def");
            weightdefault[16] = PropertiesUtils.getValue("radius_def");
            weightdefault[17] = PropertiesUtils.getValue("ftp_upload_def");
            weightdefault[18] = PropertiesUtils.getValue("ftp_download_def");
            weightdefault[19] = PropertiesUtils.getValue("web_download_def");
            weightdefault[20] = PropertiesUtils.getValue("webpage_def");
            weightdefault[21] = PropertiesUtils.getValue("video_def");
            weightdefault[22] = PropertiesUtils.getValue("game_def");
        } catch (IOException e) {

        }
        return R.ok().put("weightdefault",weightdefault);
    }


    @RequestMapping("/set")
    public R setWeight(String weight_new) {
        System.out.println(weight_new);
        JSONObject weight_new_jsonobject = JSON.parseObject(weight_new);
        System.out.println(weight_new_jsonobject.get("connectionweight"));
        try {
            PropertiesUtils.setValue("connectionweight",weight_new_jsonobject.get("connectionweight").toString());
            PropertiesUtils.setValue("qualityweight",weight_new_jsonobject.get("qualityweight").toString());
            PropertiesUtils.setValue("browseweight",weight_new_jsonobject.get("browseweight").toString());
            PropertiesUtils.setValue("downloadweight",weight_new_jsonobject.get("downloadweight").toString());
            PropertiesUtils.setValue("videoweight",weight_new_jsonobject.get("videoweight").toString());
            PropertiesUtils.setValue("gameweight",weight_new_jsonobject.get("gameweight").toString());
            PropertiesUtils.setValue("ping_icmp",weight_new_jsonobject.get("ping_icmp").toString());
            PropertiesUtils.setValue("ping_udp",weight_new_jsonobject.get("ping_udp").toString());
            PropertiesUtils.setValue("tr_tcp",weight_new_jsonobject.get("tr_tcp").toString());
            PropertiesUtils.setValue("tr_icmp",weight_new_jsonobject.get("tr_icmp").toString());
            PropertiesUtils.setValue("sla_tcp",weight_new_jsonobject.get("sla_tcp").toString());
            PropertiesUtils.setValue("sla_udp",weight_new_jsonobject.get("sla_udp").toString());
            PropertiesUtils.setValue("dns",weight_new_jsonobject.get("dns").toString());
            PropertiesUtils.setValue("dhcp",weight_new_jsonobject.get("dhcp").toString());
            PropertiesUtils.setValue("adsl",weight_new_jsonobject.get("adsl").toString());
            PropertiesUtils.setValue("radius",weight_new_jsonobject.get("radius").toString());
            PropertiesUtils.setValue("ftp_upload",weight_new_jsonobject.get("ftp_upload").toString());
            PropertiesUtils.setValue("ftp_download",weight_new_jsonobject.get("ftp_download").toString());
            PropertiesUtils.setValue("web_download",weight_new_jsonobject.get("web_download").toString());
            PropertiesUtils.setValue("webpage",weight_new_jsonobject.get("webpage").toString());
            PropertiesUtils.setValue("video",weight_new_jsonobject.get("video").toString());
            PropertiesUtils.setValue("game",weight_new_jsonobject.get("game").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setpingICMP")
    public R setpingICMP(String pi_new) {
        JSONObject pi_new_jsonobject = JSON.parseObject(pi_new);
        try {
            PropertiesUtils.setValue("pingI21",pi_new_jsonobject.get("pingI21").toString());
            PropertiesUtils.setValue("pingI22",pi_new_jsonobject.get("pingI22").toString());
            PropertiesUtils.setValue("pingI23",pi_new_jsonobject.get("pingI23").toString());
            PropertiesUtils.setValue("pingI24",pi_new_jsonobject.get("pingI24").toString());
            PropertiesUtils.setValue("pingI25",pi_new_jsonobject.get("pingI25").toString());
            PropertiesUtils.setValue("pingI26",pi_new_jsonobject.get("pingI26").toString());
            PropertiesUtils.setValue("pingI27",pi_new_jsonobject.get("pingI27").toString());
            PropertiesUtils.setValue("pingI31",pi_new_jsonobject.get("pingI31").toString());
            PropertiesUtils.setValue("pingI32",pi_new_jsonobject.get("pingI32").toString());
            PropertiesUtils.setValue("pingI33",pi_new_jsonobject.get("pingI33").toString());
            PropertiesUtils.setValue("pingI34",pi_new_jsonobject.get("pingI34").toString());
            PropertiesUtils.setValue("pingI35",pi_new_jsonobject.get("pingI35").toString());
            PropertiesUtils.setValue("pingI36",pi_new_jsonobject.get("pingI36").toString());
            PropertiesUtils.setValue("pingI37",pi_new_jsonobject.get("pingI37").toString());
            PropertiesUtils.setValue("pingI41",pi_new_jsonobject.get("pingI41").toString());
            PropertiesUtils.setValue("pingI42",pi_new_jsonobject.get("pingI42").toString());
            PropertiesUtils.setValue("pingI43",pi_new_jsonobject.get("pingI43").toString());
            PropertiesUtils.setValue("pingI44",pi_new_jsonobject.get("pingI44").toString());
            PropertiesUtils.setValue("pingI45",pi_new_jsonobject.get("pingI45").toString());
            PropertiesUtils.setValue("pingI46",pi_new_jsonobject.get("pingI46").toString());
            PropertiesUtils.setValue("pingI47",pi_new_jsonobject.get("pingI47").toString());
            PropertiesUtils.setValue("pingI51",pi_new_jsonobject.get("pingI51").toString());
            PropertiesUtils.setValue("pingI52",pi_new_jsonobject.get("pingI52").toString());
            PropertiesUtils.setValue("pingI53",pi_new_jsonobject.get("pingI53").toString());
            PropertiesUtils.setValue("pingI54",pi_new_jsonobject.get("pingI54").toString());
            PropertiesUtils.setValue("pingI55",pi_new_jsonobject.get("pingI55").toString());
            PropertiesUtils.setValue("pingI56",pi_new_jsonobject.get("pingI56").toString());
            PropertiesUtils.setValue("pingI57",pi_new_jsonobject.get("pingI57").toString());
            PropertiesUtils.setValue("pingI61",pi_new_jsonobject.get("pingI61").toString());
            PropertiesUtils.setValue("pingI62",pi_new_jsonobject.get("pingI62").toString());
            PropertiesUtils.setValue("pingI63",pi_new_jsonobject.get("pingI63").toString());
            PropertiesUtils.setValue("pingI64",pi_new_jsonobject.get("pingI64").toString());
            PropertiesUtils.setValue("pingI65",pi_new_jsonobject.get("pingI65").toString());
            PropertiesUtils.setValue("pingI66",pi_new_jsonobject.get("pingI66").toString());
            PropertiesUtils.setValue("pingI67",pi_new_jsonobject.get("pingI67").toString());
            PropertiesUtils.setValue("pingI71",pi_new_jsonobject.get("pingI71").toString());
            PropertiesUtils.setValue("pingI72",pi_new_jsonobject.get("pingI72").toString());
            PropertiesUtils.setValue("pingI73",pi_new_jsonobject.get("pingI73").toString());
            PropertiesUtils.setValue("pingI74",pi_new_jsonobject.get("pingI74").toString());
            PropertiesUtils.setValue("pingI75",pi_new_jsonobject.get("pingI75").toString());
            PropertiesUtils.setValue("pingI76",pi_new_jsonobject.get("pingI76").toString());
            PropertiesUtils.setValue("pingI77",pi_new_jsonobject.get("pingI77").toString());
            PropertiesUtils.setValue("pingI81",pi_new_jsonobject.get("pingI81").toString());
            PropertiesUtils.setValue("pingI82",pi_new_jsonobject.get("pingI82").toString());
            PropertiesUtils.setValue("pingI83",pi_new_jsonobject.get("pingI83").toString());
            PropertiesUtils.setValue("pingI84",pi_new_jsonobject.get("pingI84").toString());
            PropertiesUtils.setValue("pingI85",pi_new_jsonobject.get("pingI85").toString());
            PropertiesUtils.setValue("pingI86",pi_new_jsonobject.get("pingI86").toString());
            PropertiesUtils.setValue("pingI87",pi_new_jsonobject.get("pingI87").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setpingTCP")
    public R setpingTCP(String pt_new) {
        JSONObject pt_new_jsonobject = JSON.parseObject(pt_new);
        try {
            PropertiesUtils.setValue("pingT21",pt_new_jsonobject.get("pingT21").toString());
            PropertiesUtils.setValue("pingT22",pt_new_jsonobject.get("pingT22").toString());
            PropertiesUtils.setValue("pingT23",pt_new_jsonobject.get("pingT23").toString());
            PropertiesUtils.setValue("pingT24",pt_new_jsonobject.get("pingT24").toString());
            PropertiesUtils.setValue("pingT25",pt_new_jsonobject.get("pingT25").toString());
            PropertiesUtils.setValue("pingT26",pt_new_jsonobject.get("pingT26").toString());
            PropertiesUtils.setValue("pingT27",pt_new_jsonobject.get("pingT27").toString());
            PropertiesUtils.setValue("pingT31",pt_new_jsonobject.get("pingT31").toString());
            PropertiesUtils.setValue("pingT32",pt_new_jsonobject.get("pingT32").toString());
            PropertiesUtils.setValue("pingT33",pt_new_jsonobject.get("pingT33").toString());
            PropertiesUtils.setValue("pingT34",pt_new_jsonobject.get("pingT34").toString());
            PropertiesUtils.setValue("pingT35",pt_new_jsonobject.get("pingT35").toString());
            PropertiesUtils.setValue("pingT36",pt_new_jsonobject.get("pingT36").toString());
            PropertiesUtils.setValue("pingT37",pt_new_jsonobject.get("pingT37").toString());
            PropertiesUtils.setValue("pingT41",pt_new_jsonobject.get("pingT41").toString());
            PropertiesUtils.setValue("pingT42",pt_new_jsonobject.get("pingT42").toString());
            PropertiesUtils.setValue("pingT43",pt_new_jsonobject.get("pingT43").toString());
            PropertiesUtils.setValue("pingT44",pt_new_jsonobject.get("pingT44").toString());
            PropertiesUtils.setValue("pingT45",pt_new_jsonobject.get("pingT45").toString());
            PropertiesUtils.setValue("pingT46",pt_new_jsonobject.get("pingT46").toString());
            PropertiesUtils.setValue("pingT47",pt_new_jsonobject.get("pingT47").toString());
            PropertiesUtils.setValue("pingT51",pt_new_jsonobject.get("pingT51").toString());
            PropertiesUtils.setValue("pingT52",pt_new_jsonobject.get("pingT52").toString());
            PropertiesUtils.setValue("pingT53",pt_new_jsonobject.get("pingT53").toString());
            PropertiesUtils.setValue("pingT54",pt_new_jsonobject.get("pingT54").toString());
            PropertiesUtils.setValue("pingT55",pt_new_jsonobject.get("pingT55").toString());
            PropertiesUtils.setValue("pingT56",pt_new_jsonobject.get("pingT56").toString());
            PropertiesUtils.setValue("pingT57",pt_new_jsonobject.get("pingT57").toString());
            PropertiesUtils.setValue("pingT61",pt_new_jsonobject.get("pingT61").toString());
            PropertiesUtils.setValue("pingT62",pt_new_jsonobject.get("pingT62").toString());
            PropertiesUtils.setValue("pingT63",pt_new_jsonobject.get("pingT63").toString());
            PropertiesUtils.setValue("pingT64",pt_new_jsonobject.get("pingT64").toString());
            PropertiesUtils.setValue("pingT65",pt_new_jsonobject.get("pingT65").toString());
            PropertiesUtils.setValue("pingT66",pt_new_jsonobject.get("pingT66").toString());
            PropertiesUtils.setValue("pingT67",pt_new_jsonobject.get("pingT67").toString());
            PropertiesUtils.setValue("pingT71",pt_new_jsonobject.get("pingT71").toString());
            PropertiesUtils.setValue("pingT72",pt_new_jsonobject.get("pingT72").toString());
            PropertiesUtils.setValue("pingT73",pt_new_jsonobject.get("pingT73").toString());
            PropertiesUtils.setValue("pingT74",pt_new_jsonobject.get("pingT74").toString());
            PropertiesUtils.setValue("pingT75",pt_new_jsonobject.get("pingT75").toString());
            PropertiesUtils.setValue("pingT76",pt_new_jsonobject.get("pingT76").toString());
            PropertiesUtils.setValue("pingT77",pt_new_jsonobject.get("pingT77").toString());
            PropertiesUtils.setValue("pingT81",pt_new_jsonobject.get("pingT81").toString());
            PropertiesUtils.setValue("pingT82",pt_new_jsonobject.get("pingT82").toString());
            PropertiesUtils.setValue("pingT83",pt_new_jsonobject.get("pingT83").toString());
            PropertiesUtils.setValue("pingT84",pt_new_jsonobject.get("pingT84").toString());
            PropertiesUtils.setValue("pingT85",pt_new_jsonobject.get("pingT85").toString());
            PropertiesUtils.setValue("pingT86",pt_new_jsonobject.get("pingT86").toString());
            PropertiesUtils.setValue("pingT87",pt_new_jsonobject.get("pingT87").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setpingUDP")
    public R setpingUDP(String pu_new) {
        JSONObject pu_new_jsonobject = JSON.parseObject(pu_new);
        try {
            PropertiesUtils.setValue("pingU21",pu_new_jsonobject.get("pingU21").toString());
            PropertiesUtils.setValue("pingU22",pu_new_jsonobject.get("pingU22").toString());
            PropertiesUtils.setValue("pingU23",pu_new_jsonobject.get("pingU23").toString());
            PropertiesUtils.setValue("pingU24",pu_new_jsonobject.get("pingU24").toString());
            PropertiesUtils.setValue("pingU25",pu_new_jsonobject.get("pingU25").toString());
            PropertiesUtils.setValue("pingU26",pu_new_jsonobject.get("pingU26").toString());
            PropertiesUtils.setValue("pingU27",pu_new_jsonobject.get("pingU27").toString());
            PropertiesUtils.setValue("pingU31",pu_new_jsonobject.get("pingU31").toString());
            PropertiesUtils.setValue("pingU32",pu_new_jsonobject.get("pingU32").toString());
            PropertiesUtils.setValue("pingU33",pu_new_jsonobject.get("pingU33").toString());
            PropertiesUtils.setValue("pingU34",pu_new_jsonobject.get("pingU34").toString());
            PropertiesUtils.setValue("pingU35",pu_new_jsonobject.get("pingU35").toString());
            PropertiesUtils.setValue("pingU36",pu_new_jsonobject.get("pingU36").toString());
            PropertiesUtils.setValue("pingU37",pu_new_jsonobject.get("pingU37").toString());
            PropertiesUtils.setValue("pingU41",pu_new_jsonobject.get("pingU41").toString());
            PropertiesUtils.setValue("pingU42",pu_new_jsonobject.get("pingU42").toString());
            PropertiesUtils.setValue("pingU43",pu_new_jsonobject.get("pingU43").toString());
            PropertiesUtils.setValue("pingU44",pu_new_jsonobject.get("pingU44").toString());
            PropertiesUtils.setValue("pingU45",pu_new_jsonobject.get("pingU45").toString());
            PropertiesUtils.setValue("pingU46",pu_new_jsonobject.get("pingU46").toString());
            PropertiesUtils.setValue("pingU47",pu_new_jsonobject.get("pingU47").toString());
            PropertiesUtils.setValue("pingU51",pu_new_jsonobject.get("pingU51").toString());
            PropertiesUtils.setValue("pingU52",pu_new_jsonobject.get("pingU52").toString());
            PropertiesUtils.setValue("pingU53",pu_new_jsonobject.get("pingU53").toString());
            PropertiesUtils.setValue("pingU54",pu_new_jsonobject.get("pingU54").toString());
            PropertiesUtils.setValue("pingU55",pu_new_jsonobject.get("pingU55").toString());
            PropertiesUtils.setValue("pingU56",pu_new_jsonobject.get("pingU56").toString());
            PropertiesUtils.setValue("pingU57",pu_new_jsonobject.get("pingU57").toString());
            PropertiesUtils.setValue("pingU61",pu_new_jsonobject.get("pingU61").toString());
            PropertiesUtils.setValue("pingU62",pu_new_jsonobject.get("pingU62").toString());
            PropertiesUtils.setValue("pingU63",pu_new_jsonobject.get("pingU63").toString());
            PropertiesUtils.setValue("pingU64",pu_new_jsonobject.get("pingU64").toString());
            PropertiesUtils.setValue("pingU65",pu_new_jsonobject.get("pingU65").toString());
            PropertiesUtils.setValue("pingU66",pu_new_jsonobject.get("pingU66").toString());
            PropertiesUtils.setValue("pingU67",pu_new_jsonobject.get("pingU67").toString());
            PropertiesUtils.setValue("pingU71",pu_new_jsonobject.get("pingU71").toString());
            PropertiesUtils.setValue("pingU72",pu_new_jsonobject.get("pingU72").toString());
            PropertiesUtils.setValue("pingU73",pu_new_jsonobject.get("pingU73").toString());
            PropertiesUtils.setValue("pingU74",pu_new_jsonobject.get("pingU74").toString());
            PropertiesUtils.setValue("pingU75",pu_new_jsonobject.get("pingU75").toString());
            PropertiesUtils.setValue("pingU76",pu_new_jsonobject.get("pingU76").toString());
            PropertiesUtils.setValue("pingU77",pu_new_jsonobject.get("pingU77").toString());
            PropertiesUtils.setValue("pingU81",pu_new_jsonobject.get("pingU81").toString());
            PropertiesUtils.setValue("pingU82",pu_new_jsonobject.get("pingU82").toString());
            PropertiesUtils.setValue("pingU83",pu_new_jsonobject.get("pingU83").toString());
            PropertiesUtils.setValue("pingU84",pu_new_jsonobject.get("pingU84").toString());
            PropertiesUtils.setValue("pingU85",pu_new_jsonobject.get("pingU85").toString());
            PropertiesUtils.setValue("pingU86",pu_new_jsonobject.get("pingU86").toString());
            PropertiesUtils.setValue("pingU87",pu_new_jsonobject.get("pingU87").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/settrICMP")
    public R settrICMP(String tri_new) {
        JSONObject tri_new_jsonobject = JSON.parseObject(tri_new);
        try {
            PropertiesUtils.setValue("trI11",tri_new_jsonobject.get("trI11").toString());
            PropertiesUtils.setValue("trI12",tri_new_jsonobject.get("trI12").toString());
            PropertiesUtils.setValue("trI13",tri_new_jsonobject.get("trI13").toString());
            PropertiesUtils.setValue("trI14",tri_new_jsonobject.get("trI14").toString());
            PropertiesUtils.setValue("trI15",tri_new_jsonobject.get("trI15").toString());
            PropertiesUtils.setValue("trI16",tri_new_jsonobject.get("trI16").toString());
            PropertiesUtils.setValue("trI17",tri_new_jsonobject.get("trI17").toString());
            PropertiesUtils.setValue("trI21",tri_new_jsonobject.get("trI21").toString());
            PropertiesUtils.setValue("trI22",tri_new_jsonobject.get("trI22").toString());
            PropertiesUtils.setValue("trI23",tri_new_jsonobject.get("trI23").toString());
            PropertiesUtils.setValue("trI24",tri_new_jsonobject.get("trI24").toString());
            PropertiesUtils.setValue("trI25",tri_new_jsonobject.get("trI25").toString());
            PropertiesUtils.setValue("trI26",tri_new_jsonobject.get("trI26").toString());
            PropertiesUtils.setValue("trI27",tri_new_jsonobject.get("trI27").toString());
            PropertiesUtils.setValue("trI31",tri_new_jsonobject.get("trI31").toString());
            PropertiesUtils.setValue("trI32",tri_new_jsonobject.get("trI32").toString());
            PropertiesUtils.setValue("trI33",tri_new_jsonobject.get("trI33").toString());
            PropertiesUtils.setValue("trI34",tri_new_jsonobject.get("trI34").toString());
            PropertiesUtils.setValue("trI35",tri_new_jsonobject.get("trI35").toString());
            PropertiesUtils.setValue("trI36",tri_new_jsonobject.get("trI36").toString());
            PropertiesUtils.setValue("trI37",tri_new_jsonobject.get("trI37").toString());
            PropertiesUtils.setValue("trI41",tri_new_jsonobject.get("trI41").toString());
            PropertiesUtils.setValue("trI42",tri_new_jsonobject.get("trI42").toString());
            PropertiesUtils.setValue("trI43",tri_new_jsonobject.get("trI43").toString());
            PropertiesUtils.setValue("trI44",tri_new_jsonobject.get("trI44").toString());
            PropertiesUtils.setValue("trI45",tri_new_jsonobject.get("trI45").toString());
            PropertiesUtils.setValue("trI46",tri_new_jsonobject.get("trI46").toString());
            PropertiesUtils.setValue("trI47",tri_new_jsonobject.get("trI47").toString());
            PropertiesUtils.setValue("trI51",tri_new_jsonobject.get("trI51").toString());
            PropertiesUtils.setValue("trI52",tri_new_jsonobject.get("trI52").toString());
            PropertiesUtils.setValue("trI53",tri_new_jsonobject.get("trI53").toString());
            PropertiesUtils.setValue("trI54",tri_new_jsonobject.get("trI54").toString());
            PropertiesUtils.setValue("trI55",tri_new_jsonobject.get("trI55").toString());
            PropertiesUtils.setValue("trI56",tri_new_jsonobject.get("trI56").toString());
            PropertiesUtils.setValue("trI57",tri_new_jsonobject.get("trI57").toString());
            PropertiesUtils.setValue("trI61",tri_new_jsonobject.get("trI61").toString());
            PropertiesUtils.setValue("trI62",tri_new_jsonobject.get("trI62").toString());
            PropertiesUtils.setValue("trI63",tri_new_jsonobject.get("trI63").toString());
            PropertiesUtils.setValue("trI64",tri_new_jsonobject.get("trI64").toString());
            PropertiesUtils.setValue("trI65",tri_new_jsonobject.get("trI65").toString());
            PropertiesUtils.setValue("trI66",tri_new_jsonobject.get("trI66").toString());
            PropertiesUtils.setValue("trI67",tri_new_jsonobject.get("trI67").toString());
            PropertiesUtils.setValue("trI71",tri_new_jsonobject.get("trI71").toString());
            PropertiesUtils.setValue("trI72",tri_new_jsonobject.get("trI72").toString());
            PropertiesUtils.setValue("trI73",tri_new_jsonobject.get("trI73").toString());
            PropertiesUtils.setValue("trI74",tri_new_jsonobject.get("trI74").toString());
            PropertiesUtils.setValue("trI75",tri_new_jsonobject.get("trI75").toString());
            PropertiesUtils.setValue("trI76",tri_new_jsonobject.get("trI76").toString());
            PropertiesUtils.setValue("trI77",tri_new_jsonobject.get("trI77").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/settrTCP")
    public R settrTCP(String trt_new) {
        JSONObject trt_new_jsonobject = JSON.parseObject(trt_new);
        try {
            PropertiesUtils.setValue("trT21",trt_new_jsonobject.get("trT11").toString());
            PropertiesUtils.setValue("trT22",trt_new_jsonobject.get("trT12").toString());
            PropertiesUtils.setValue("trT23",trt_new_jsonobject.get("trT13").toString());
            PropertiesUtils.setValue("trT24",trt_new_jsonobject.get("trT14").toString());
            PropertiesUtils.setValue("trT25",trt_new_jsonobject.get("trT15").toString());
            PropertiesUtils.setValue("trT26",trt_new_jsonobject.get("trT16").toString());
            PropertiesUtils.setValue("trT27",trt_new_jsonobject.get("trT17").toString());
            PropertiesUtils.setValue("trT31",trt_new_jsonobject.get("trT21").toString());
            PropertiesUtils.setValue("trT32",trt_new_jsonobject.get("trT22").toString());
            PropertiesUtils.setValue("trT33",trt_new_jsonobject.get("trT23").toString());
            PropertiesUtils.setValue("trT34",trt_new_jsonobject.get("trT24").toString());
            PropertiesUtils.setValue("trT35",trt_new_jsonobject.get("trT25").toString());
            PropertiesUtils.setValue("trT36",trt_new_jsonobject.get("trT26").toString());
            PropertiesUtils.setValue("trT37",trt_new_jsonobject.get("trT27").toString());
            PropertiesUtils.setValue("trT41",trt_new_jsonobject.get("trT31").toString());
            PropertiesUtils.setValue("trT42",trt_new_jsonobject.get("trT32").toString());
            PropertiesUtils.setValue("trT43",trt_new_jsonobject.get("trT33").toString());
            PropertiesUtils.setValue("trT44",trt_new_jsonobject.get("trT34").toString());
            PropertiesUtils.setValue("trT45",trt_new_jsonobject.get("trT35").toString());
            PropertiesUtils.setValue("trT46",trt_new_jsonobject.get("trT36").toString());
            PropertiesUtils.setValue("trT47",trt_new_jsonobject.get("trT37").toString());
            PropertiesUtils.setValue("trT51",trt_new_jsonobject.get("trT41").toString());
            PropertiesUtils.setValue("trT52",trt_new_jsonobject.get("trT42").toString());
            PropertiesUtils.setValue("trT53",trt_new_jsonobject.get("trT43").toString());
            PropertiesUtils.setValue("trT54",trt_new_jsonobject.get("trT44").toString());
            PropertiesUtils.setValue("trT55",trt_new_jsonobject.get("trT45").toString());
            PropertiesUtils.setValue("trT56",trt_new_jsonobject.get("trT46").toString());
            PropertiesUtils.setValue("trT57",trt_new_jsonobject.get("trT47").toString());
            PropertiesUtils.setValue("trT61",trt_new_jsonobject.get("trT51").toString());
            PropertiesUtils.setValue("trT62",trt_new_jsonobject.get("trT52").toString());
            PropertiesUtils.setValue("trT63",trt_new_jsonobject.get("trT53").toString());
            PropertiesUtils.setValue("trT64",trt_new_jsonobject.get("trT54").toString());
            PropertiesUtils.setValue("trT65",trt_new_jsonobject.get("trT55").toString());
            PropertiesUtils.setValue("trT66",trt_new_jsonobject.get("trT56").toString());
            PropertiesUtils.setValue("trT67",trt_new_jsonobject.get("trT57").toString());
            PropertiesUtils.setValue("trT71",trt_new_jsonobject.get("trT61").toString());
            PropertiesUtils.setValue("trT72",trt_new_jsonobject.get("trT62").toString());
            PropertiesUtils.setValue("trT73",trt_new_jsonobject.get("trT63").toString());
            PropertiesUtils.setValue("trT74",trt_new_jsonobject.get("trT64").toString());
            PropertiesUtils.setValue("trT75",trt_new_jsonobject.get("trT65").toString());
            PropertiesUtils.setValue("trT76",trt_new_jsonobject.get("trT66").toString());
            PropertiesUtils.setValue("trT77",trt_new_jsonobject.get("trT67").toString());
            PropertiesUtils.setValue("trT81",trt_new_jsonobject.get("trT71").toString());
            PropertiesUtils.setValue("trT82",trt_new_jsonobject.get("trT72").toString());
            PropertiesUtils.setValue("trT83",trt_new_jsonobject.get("trT73").toString());
            PropertiesUtils.setValue("trT84",trt_new_jsonobject.get("trT74").toString());
            PropertiesUtils.setValue("trT85",trt_new_jsonobject.get("trT75").toString());
            PropertiesUtils.setValue("trT86",trt_new_jsonobject.get("trT76").toString());
            PropertiesUtils.setValue("trT87",trt_new_jsonobject.get("trT77").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setslaTCP")
    public R setslaTCP(String st_new) {
        JSONObject st_new_jsonobject = JSON.parseObject(st_new);
        try {
            PropertiesUtils.setValue("slaT11",st_new_jsonobject.get("slaT11").toString());
            PropertiesUtils.setValue("slaT12",st_new_jsonobject.get("slaT12").toString());
            PropertiesUtils.setValue("slaT13",st_new_jsonobject.get("slaT13").toString());
            PropertiesUtils.setValue("slaT14",st_new_jsonobject.get("slaT14").toString());
            PropertiesUtils.setValue("slaT15",st_new_jsonobject.get("slaT15").toString());
            PropertiesUtils.setValue("slaT16",st_new_jsonobject.get("slaT16").toString());
            PropertiesUtils.setValue("slaT17",st_new_jsonobject.get("slaT17").toString());
            PropertiesUtils.setValue("slaT21",st_new_jsonobject.get("slaT21").toString());
            PropertiesUtils.setValue("slaT22",st_new_jsonobject.get("slaT22").toString());
            PropertiesUtils.setValue("slaT23",st_new_jsonobject.get("slaT23").toString());
            PropertiesUtils.setValue("slaT24",st_new_jsonobject.get("slaT24").toString());
            PropertiesUtils.setValue("slaT25",st_new_jsonobject.get("slaT25").toString());
            PropertiesUtils.setValue("slaT26",st_new_jsonobject.get("slaT26").toString());
            PropertiesUtils.setValue("slaT27",st_new_jsonobject.get("slaT27").toString());
            PropertiesUtils.setValue("slaT31",st_new_jsonobject.get("slaT31").toString());
            PropertiesUtils.setValue("slaT32",st_new_jsonobject.get("slaT32").toString());
            PropertiesUtils.setValue("slaT33",st_new_jsonobject.get("slaT33").toString());
            PropertiesUtils.setValue("slaT34",st_new_jsonobject.get("slaT34").toString());
            PropertiesUtils.setValue("slaT35",st_new_jsonobject.get("slaT35").toString());
            PropertiesUtils.setValue("slaT36",st_new_jsonobject.get("slaT36").toString());
            PropertiesUtils.setValue("slaT37",st_new_jsonobject.get("slaT37").toString());
            PropertiesUtils.setValue("slaT41",st_new_jsonobject.get("slaT41").toString());
            PropertiesUtils.setValue("slaT42",st_new_jsonobject.get("slaT42").toString());
            PropertiesUtils.setValue("slaT43",st_new_jsonobject.get("slaT43").toString());
            PropertiesUtils.setValue("slaT44",st_new_jsonobject.get("slaT44").toString());
            PropertiesUtils.setValue("slaT45",st_new_jsonobject.get("slaT45").toString());
            PropertiesUtils.setValue("slaT46",st_new_jsonobject.get("slaT46").toString());
            PropertiesUtils.setValue("slaT47",st_new_jsonobject.get("slaT47").toString());
            PropertiesUtils.setValue("slaT51",st_new_jsonobject.get("slaT51").toString());
            PropertiesUtils.setValue("slaT52",st_new_jsonobject.get("slaT52").toString());
            PropertiesUtils.setValue("slaT53",st_new_jsonobject.get("slaT53").toString());
            PropertiesUtils.setValue("slaT54",st_new_jsonobject.get("slaT54").toString());
            PropertiesUtils.setValue("slaT55",st_new_jsonobject.get("slaT55").toString());
            PropertiesUtils.setValue("slaT56",st_new_jsonobject.get("slaT56").toString());
            PropertiesUtils.setValue("slaT57",st_new_jsonobject.get("slaT57").toString());
            PropertiesUtils.setValue("slaT61",st_new_jsonobject.get("slaT61").toString());
            PropertiesUtils.setValue("slaT62",st_new_jsonobject.get("slaT62").toString());
            PropertiesUtils.setValue("slaT63",st_new_jsonobject.get("slaT63").toString());
            PropertiesUtils.setValue("slaT64",st_new_jsonobject.get("slaT64").toString());
            PropertiesUtils.setValue("slaT65",st_new_jsonobject.get("slaT65").toString());
            PropertiesUtils.setValue("slaT66",st_new_jsonobject.get("slaT66").toString());
            PropertiesUtils.setValue("slaT67",st_new_jsonobject.get("slaT67").toString());
            PropertiesUtils.setValue("slaT71",st_new_jsonobject.get("slaT71").toString());
            PropertiesUtils.setValue("slaT72",st_new_jsonobject.get("slaT72").toString());
            PropertiesUtils.setValue("slaT73",st_new_jsonobject.get("slaT73").toString());
            PropertiesUtils.setValue("slaT74",st_new_jsonobject.get("slaT74").toString());
            PropertiesUtils.setValue("slaT75",st_new_jsonobject.get("slaT75").toString());
            PropertiesUtils.setValue("slaT76",st_new_jsonobject.get("slaT76").toString());
            PropertiesUtils.setValue("slaT77",st_new_jsonobject.get("slaT77").toString());
            PropertiesUtils.setValue("slaT81",st_new_jsonobject.get("slaT81").toString());
            PropertiesUtils.setValue("slaT82",st_new_jsonobject.get("slaT82").toString());
            PropertiesUtils.setValue("slaT83",st_new_jsonobject.get("slaT83").toString());
            PropertiesUtils.setValue("slaT84",st_new_jsonobject.get("slaT84").toString());
            PropertiesUtils.setValue("slaT85",st_new_jsonobject.get("slaT85").toString());
            PropertiesUtils.setValue("slaT86",st_new_jsonobject.get("slaT86").toString());
            PropertiesUtils.setValue("slaT87",st_new_jsonobject.get("slaT87").toString());
            PropertiesUtils.setValue("slaT91",st_new_jsonobject.get("slaT91").toString());
            PropertiesUtils.setValue("slaT92",st_new_jsonobject.get("slaT92").toString());
            PropertiesUtils.setValue("slaT93",st_new_jsonobject.get("slaT93").toString());
            PropertiesUtils.setValue("slaT94",st_new_jsonobject.get("slaT94").toString());
            PropertiesUtils.setValue("slaT95",st_new_jsonobject.get("slaT95").toString());
            PropertiesUtils.setValue("slaT96",st_new_jsonobject.get("slaT96").toString());
            PropertiesUtils.setValue("slaT97",st_new_jsonobject.get("slaT97").toString());
            PropertiesUtils.setValue("slaT101",st_new_jsonobject.get("slaT101").toString());
            PropertiesUtils.setValue("slaT102",st_new_jsonobject.get("slaT102").toString());
            PropertiesUtils.setValue("slaT103",st_new_jsonobject.get("slaT103").toString());
            PropertiesUtils.setValue("slaT104",st_new_jsonobject.get("slaT104").toString());
            PropertiesUtils.setValue("slaT105",st_new_jsonobject.get("slaT105").toString());
            PropertiesUtils.setValue("slaT106",st_new_jsonobject.get("slaT106").toString());
            PropertiesUtils.setValue("slaT107",st_new_jsonobject.get("slaT107").toString());
            PropertiesUtils.setValue("slaT111",st_new_jsonobject.get("slaT111").toString());
            PropertiesUtils.setValue("slaT112",st_new_jsonobject.get("slaT112").toString());
            PropertiesUtils.setValue("slaT113",st_new_jsonobject.get("slaT113").toString());
            PropertiesUtils.setValue("slaT114",st_new_jsonobject.get("slaT114").toString());
            PropertiesUtils.setValue("slaT115",st_new_jsonobject.get("slaT115").toString());
            PropertiesUtils.setValue("slaT116",st_new_jsonobject.get("slaT116").toString());
            PropertiesUtils.setValue("slaT117",st_new_jsonobject.get("slaT117").toString());
            PropertiesUtils.setValue("slaT121",st_new_jsonobject.get("slaT121").toString());
            PropertiesUtils.setValue("slaT122",st_new_jsonobject.get("slaT122").toString());
            PropertiesUtils.setValue("slaT123",st_new_jsonobject.get("slaT123").toString());
            PropertiesUtils.setValue("slaT124",st_new_jsonobject.get("slaT124").toString());
            PropertiesUtils.setValue("slaT125",st_new_jsonobject.get("slaT125").toString());
            PropertiesUtils.setValue("slaT126",st_new_jsonobject.get("slaT126").toString());
            PropertiesUtils.setValue("slaT127",st_new_jsonobject.get("slaT127").toString());
            PropertiesUtils.setValue("slaT131",st_new_jsonobject.get("slaT131").toString());
            PropertiesUtils.setValue("slaT132",st_new_jsonobject.get("slaT132").toString());
            PropertiesUtils.setValue("slaT133",st_new_jsonobject.get("slaT133").toString());
            PropertiesUtils.setValue("slaT134",st_new_jsonobject.get("slaT134").toString());
            PropertiesUtils.setValue("slaT135",st_new_jsonobject.get("slaT135").toString());
            PropertiesUtils.setValue("slaT136",st_new_jsonobject.get("slaT136").toString());
            PropertiesUtils.setValue("slaT137",st_new_jsonobject.get("slaT137").toString());
            PropertiesUtils.setValue("slaT141",st_new_jsonobject.get("slaT141").toString());
            PropertiesUtils.setValue("slaT142",st_new_jsonobject.get("slaT142").toString());
            PropertiesUtils.setValue("slaT143",st_new_jsonobject.get("slaT143").toString());
            PropertiesUtils.setValue("slaT144",st_new_jsonobject.get("slaT144").toString());
            PropertiesUtils.setValue("slaT145",st_new_jsonobject.get("slaT145").toString());
            PropertiesUtils.setValue("slaT146",st_new_jsonobject.get("slaT146").toString());
            PropertiesUtils.setValue("slaT147",st_new_jsonobject.get("slaT147").toString());
            PropertiesUtils.setValue("slaT151",st_new_jsonobject.get("slaT151").toString());
            PropertiesUtils.setValue("slaT152",st_new_jsonobject.get("slaT152").toString());
            PropertiesUtils.setValue("slaT153",st_new_jsonobject.get("slaT153").toString());
            PropertiesUtils.setValue("slaT154",st_new_jsonobject.get("slaT154").toString());
            PropertiesUtils.setValue("slaT155",st_new_jsonobject.get("slaT155").toString());
            PropertiesUtils.setValue("slaT156",st_new_jsonobject.get("slaT156").toString());
            PropertiesUtils.setValue("slaT157",st_new_jsonobject.get("slaT157").toString());
            PropertiesUtils.setValue("slaT161",st_new_jsonobject.get("slaT161").toString());
            PropertiesUtils.setValue("slaT162",st_new_jsonobject.get("slaT162").toString());
            PropertiesUtils.setValue("slaT163",st_new_jsonobject.get("slaT163").toString());
            PropertiesUtils.setValue("slaT164",st_new_jsonobject.get("slaT164").toString());
            PropertiesUtils.setValue("slaT165",st_new_jsonobject.get("slaT165").toString());
            PropertiesUtils.setValue("slaT166",st_new_jsonobject.get("slaT166").toString());
            PropertiesUtils.setValue("slaT167",st_new_jsonobject.get("slaT167").toString());
            PropertiesUtils.setValue("slaT171",st_new_jsonobject.get("slaT171").toString());
            PropertiesUtils.setValue("slaT172",st_new_jsonobject.get("slaT172").toString());
            PropertiesUtils.setValue("slaT173",st_new_jsonobject.get("slaT173").toString());
            PropertiesUtils.setValue("slaT174",st_new_jsonobject.get("slaT174").toString());
            PropertiesUtils.setValue("slaT175",st_new_jsonobject.get("slaT175").toString());
            PropertiesUtils.setValue("slaT176",st_new_jsonobject.get("slaT176").toString());
            PropertiesUtils.setValue("slaT177",st_new_jsonobject.get("slaT177").toString());
            PropertiesUtils.setValue("slaT181",st_new_jsonobject.get("slaT181").toString());
            PropertiesUtils.setValue("slaT182",st_new_jsonobject.get("slaT182").toString());
            PropertiesUtils.setValue("slaT183",st_new_jsonobject.get("slaT183").toString());
            PropertiesUtils.setValue("slaT184",st_new_jsonobject.get("slaT184").toString());
            PropertiesUtils.setValue("slaT185",st_new_jsonobject.get("slaT185").toString());
            PropertiesUtils.setValue("slaT186",st_new_jsonobject.get("slaT186").toString());
            PropertiesUtils.setValue("slaT187",st_new_jsonobject.get("slaT187").toString());
            PropertiesUtils.setValue("slaT191",st_new_jsonobject.get("slaT191").toString());
            PropertiesUtils.setValue("slaT192",st_new_jsonobject.get("slaT192").toString());
            PropertiesUtils.setValue("slaT193",st_new_jsonobject.get("slaT193").toString());
            PropertiesUtils.setValue("slaT194",st_new_jsonobject.get("slaT194").toString());
            PropertiesUtils.setValue("slaT195",st_new_jsonobject.get("slaT195").toString());
            PropertiesUtils.setValue("slaT196",st_new_jsonobject.get("slaT196").toString());
            PropertiesUtils.setValue("slaT197",st_new_jsonobject.get("slaT197").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setslaUDP")
    public R setslaUDP(String su_new) {
        JSONObject su_new_jsonobject = JSON.parseObject(su_new);
        try {
            PropertiesUtils.setValue("slaU11",su_new_jsonobject.get("slaT11").toString());
            PropertiesUtils.setValue("slaU12",su_new_jsonobject.get("slaT12").toString());
            PropertiesUtils.setValue("slaU13",su_new_jsonobject.get("slaT13").toString());
            PropertiesUtils.setValue("slaU14",su_new_jsonobject.get("slaT14").toString());
            PropertiesUtils.setValue("slaU15",su_new_jsonobject.get("slaT15").toString());
            PropertiesUtils.setValue("slaU16",su_new_jsonobject.get("slaT16").toString());
            PropertiesUtils.setValue("slaU17",su_new_jsonobject.get("slaT17").toString());
            PropertiesUtils.setValue("slaU21",su_new_jsonobject.get("slaT21").toString());
            PropertiesUtils.setValue("slaU22",su_new_jsonobject.get("slaT22").toString());
            PropertiesUtils.setValue("slaU23",su_new_jsonobject.get("slaT23").toString());
            PropertiesUtils.setValue("slaU24",su_new_jsonobject.get("slaT24").toString());
            PropertiesUtils.setValue("slaU25",su_new_jsonobject.get("slaT25").toString());
            PropertiesUtils.setValue("slaU26",su_new_jsonobject.get("slaT26").toString());
            PropertiesUtils.setValue("slaU27",su_new_jsonobject.get("slaT27").toString());
            PropertiesUtils.setValue("slaU31",su_new_jsonobject.get("slaT31").toString());
            PropertiesUtils.setValue("slaU32",su_new_jsonobject.get("slaT32").toString());
            PropertiesUtils.setValue("slaU33",su_new_jsonobject.get("slaT33").toString());
            PropertiesUtils.setValue("slaU34",su_new_jsonobject.get("slaT34").toString());
            PropertiesUtils.setValue("slaU35",su_new_jsonobject.get("slaT35").toString());
            PropertiesUtils.setValue("slaU36",su_new_jsonobject.get("slaT36").toString());
            PropertiesUtils.setValue("slaU37",su_new_jsonobject.get("slaT37").toString());
            PropertiesUtils.setValue("slaU41",su_new_jsonobject.get("slaT41").toString());
            PropertiesUtils.setValue("slaU42",su_new_jsonobject.get("slaT42").toString());
            PropertiesUtils.setValue("slaU43",su_new_jsonobject.get("slaT43").toString());
            PropertiesUtils.setValue("slaU44",su_new_jsonobject.get("slaT44").toString());
            PropertiesUtils.setValue("slaU45",su_new_jsonobject.get("slaT45").toString());
            PropertiesUtils.setValue("slaU46",su_new_jsonobject.get("slaT46").toString());
            PropertiesUtils.setValue("slaU47",su_new_jsonobject.get("slaT47").toString());
            PropertiesUtils.setValue("slaU51",su_new_jsonobject.get("slaT51").toString());
            PropertiesUtils.setValue("slaU52",su_new_jsonobject.get("slaT52").toString());
            PropertiesUtils.setValue("slaU53",su_new_jsonobject.get("slaT53").toString());
            PropertiesUtils.setValue("slaU54",su_new_jsonobject.get("slaT54").toString());
            PropertiesUtils.setValue("slaU55",su_new_jsonobject.get("slaT55").toString());
            PropertiesUtils.setValue("slaU56",su_new_jsonobject.get("slaT56").toString());
            PropertiesUtils.setValue("slaU57",su_new_jsonobject.get("slaT57").toString());
            PropertiesUtils.setValue("slaU61",su_new_jsonobject.get("slaT61").toString());
            PropertiesUtils.setValue("slaU62",su_new_jsonobject.get("slaT62").toString());
            PropertiesUtils.setValue("slaU63",su_new_jsonobject.get("slaT63").toString());
            PropertiesUtils.setValue("slaU64",su_new_jsonobject.get("slaT64").toString());
            PropertiesUtils.setValue("slaU65",su_new_jsonobject.get("slaT65").toString());
            PropertiesUtils.setValue("slaU66",su_new_jsonobject.get("slaT66").toString());
            PropertiesUtils.setValue("slaU67",su_new_jsonobject.get("slaT67").toString());
            PropertiesUtils.setValue("slaU71",su_new_jsonobject.get("slaT71").toString());
            PropertiesUtils.setValue("slaU72",su_new_jsonobject.get("slaT72").toString());
            PropertiesUtils.setValue("slaU73",su_new_jsonobject.get("slaT73").toString());
            PropertiesUtils.setValue("slaU74",su_new_jsonobject.get("slaT74").toString());
            PropertiesUtils.setValue("slaU75",su_new_jsonobject.get("slaT75").toString());
            PropertiesUtils.setValue("slaU76",su_new_jsonobject.get("slaT76").toString());
            PropertiesUtils.setValue("slaU77",su_new_jsonobject.get("slaT77").toString());
            PropertiesUtils.setValue("slaU81",su_new_jsonobject.get("slaT81").toString());
            PropertiesUtils.setValue("slaU82",su_new_jsonobject.get("slaT82").toString());
            PropertiesUtils.setValue("slaU83",su_new_jsonobject.get("slaT83").toString());
            PropertiesUtils.setValue("slaU84",su_new_jsonobject.get("slaT84").toString());
            PropertiesUtils.setValue("slaU85",su_new_jsonobject.get("slaT85").toString());
            PropertiesUtils.setValue("slaU86",su_new_jsonobject.get("slaT86").toString());
            PropertiesUtils.setValue("slaU87",su_new_jsonobject.get("slaT87").toString());
            PropertiesUtils.setValue("slaU91",su_new_jsonobject.get("slaT91").toString());
            PropertiesUtils.setValue("slaU92",su_new_jsonobject.get("slaT92").toString());
            PropertiesUtils.setValue("slaU93",su_new_jsonobject.get("slaT93").toString());
            PropertiesUtils.setValue("slaU94",su_new_jsonobject.get("slaT94").toString());
            PropertiesUtils.setValue("slaU95",su_new_jsonobject.get("slaT95").toString());
            PropertiesUtils.setValue("slaU96",su_new_jsonobject.get("slaT96").toString());
            PropertiesUtils.setValue("slaU97",su_new_jsonobject.get("slaT97").toString());
            PropertiesUtils.setValue("slaU101",su_new_jsonobject.get("slaU101").toString());
            PropertiesUtils.setValue("slaU102",su_new_jsonobject.get("slaU102").toString());
            PropertiesUtils.setValue("slaU103",su_new_jsonobject.get("slaU103").toString());
            PropertiesUtils.setValue("slaU104",su_new_jsonobject.get("slaU104").toString());
            PropertiesUtils.setValue("slaU105",su_new_jsonobject.get("slaU105").toString());
            PropertiesUtils.setValue("slaU106",su_new_jsonobject.get("slaU106").toString());
            PropertiesUtils.setValue("slaU107",su_new_jsonobject.get("slaU107").toString());
            PropertiesUtils.setValue("slaU111",su_new_jsonobject.get("slaU111").toString());
            PropertiesUtils.setValue("slaU112",su_new_jsonobject.get("slaU112").toString());
            PropertiesUtils.setValue("slaU113",su_new_jsonobject.get("slaU113").toString());
            PropertiesUtils.setValue("slaU114",su_new_jsonobject.get("slaU114").toString());
            PropertiesUtils.setValue("slaU115",su_new_jsonobject.get("slaU115").toString());
            PropertiesUtils.setValue("slaU116",su_new_jsonobject.get("slaU116").toString());
            PropertiesUtils.setValue("slaU117",su_new_jsonobject.get("slaU117").toString());
            PropertiesUtils.setValue("slaU121",su_new_jsonobject.get("slaU121").toString());
            PropertiesUtils.setValue("slaU122",su_new_jsonobject.get("slaU122").toString());
            PropertiesUtils.setValue("slaU123",su_new_jsonobject.get("slaU123").toString());
            PropertiesUtils.setValue("slaU124",su_new_jsonobject.get("slaU124").toString());
            PropertiesUtils.setValue("slaU125",su_new_jsonobject.get("slaU125").toString());
            PropertiesUtils.setValue("slaU126",su_new_jsonobject.get("slaU126").toString());
            PropertiesUtils.setValue("slaU127",su_new_jsonobject.get("slaU127").toString());
            PropertiesUtils.setValue("slaU131",su_new_jsonobject.get("slaU131").toString());
            PropertiesUtils.setValue("slaU132",su_new_jsonobject.get("slaU132").toString());
            PropertiesUtils.setValue("slaU133",su_new_jsonobject.get("slaU133").toString());
            PropertiesUtils.setValue("slaU134",su_new_jsonobject.get("slaU134").toString());
            PropertiesUtils.setValue("slaU135",su_new_jsonobject.get("slaU135").toString());
            PropertiesUtils.setValue("slaU136",su_new_jsonobject.get("slaU136").toString());
            PropertiesUtils.setValue("slaU137",su_new_jsonobject.get("slaU137").toString());
            PropertiesUtils.setValue("slaU141",su_new_jsonobject.get("slaU141").toString());
            PropertiesUtils.setValue("slaU142",su_new_jsonobject.get("slaU142").toString());
            PropertiesUtils.setValue("slaU143",su_new_jsonobject.get("slaU143").toString());
            PropertiesUtils.setValue("slaU144",su_new_jsonobject.get("slaU144").toString());
            PropertiesUtils.setValue("slaU145",su_new_jsonobject.get("slaU145").toString());
            PropertiesUtils.setValue("slaU146",su_new_jsonobject.get("slaU146").toString());
            PropertiesUtils.setValue("slaU147",su_new_jsonobject.get("slaU147").toString());
            PropertiesUtils.setValue("slaU151",su_new_jsonobject.get("slaU151").toString());
            PropertiesUtils.setValue("slaU152",su_new_jsonobject.get("slaU152").toString());
            PropertiesUtils.setValue("slaU153",su_new_jsonobject.get("slaU153").toString());
            PropertiesUtils.setValue("slaU154",su_new_jsonobject.get("slaU154").toString());
            PropertiesUtils.setValue("slaU155",su_new_jsonobject.get("slaU155").toString());
            PropertiesUtils.setValue("slaU156",su_new_jsonobject.get("slaU156").toString());
            PropertiesUtils.setValue("slaU157",su_new_jsonobject.get("slaU157").toString());
            PropertiesUtils.setValue("slaU161",su_new_jsonobject.get("slaU161").toString());
            PropertiesUtils.setValue("slaU162",su_new_jsonobject.get("slaU162").toString());
            PropertiesUtils.setValue("slaU163",su_new_jsonobject.get("slaU163").toString());
            PropertiesUtils.setValue("slaU164",su_new_jsonobject.get("slaU164").toString());
            PropertiesUtils.setValue("slaU165",su_new_jsonobject.get("slaU165").toString());
            PropertiesUtils.setValue("slaU166",su_new_jsonobject.get("slaU166").toString());
            PropertiesUtils.setValue("slaU167",su_new_jsonobject.get("slaU167").toString());
            PropertiesUtils.setValue("slaU171",su_new_jsonobject.get("slaU171").toString());
            PropertiesUtils.setValue("slaU172",su_new_jsonobject.get("slaU172").toString());
            PropertiesUtils.setValue("slaU173",su_new_jsonobject.get("slaU173").toString());
            PropertiesUtils.setValue("slaU174",su_new_jsonobject.get("slaU174").toString());
            PropertiesUtils.setValue("slaU175",su_new_jsonobject.get("slaU175").toString());
            PropertiesUtils.setValue("slaU176",su_new_jsonobject.get("slaU176").toString());
            PropertiesUtils.setValue("slaU177",su_new_jsonobject.get("slaU177").toString());
            PropertiesUtils.setValue("slaU181",su_new_jsonobject.get("slaU181").toString());
            PropertiesUtils.setValue("slaU182",su_new_jsonobject.get("slaU182").toString());
            PropertiesUtils.setValue("slaU183",su_new_jsonobject.get("slaU183").toString());
            PropertiesUtils.setValue("slaU184",su_new_jsonobject.get("slaU184").toString());
            PropertiesUtils.setValue("slaU185",su_new_jsonobject.get("slaU185").toString());
            PropertiesUtils.setValue("slaU186",su_new_jsonobject.get("slaU186").toString());
            PropertiesUtils.setValue("slaU187",su_new_jsonobject.get("slaU187").toString());
            PropertiesUtils.setValue("slaU191",su_new_jsonobject.get("slaU191").toString());
            PropertiesUtils.setValue("slaU192",su_new_jsonobject.get("slaU192").toString());
            PropertiesUtils.setValue("slaU193",su_new_jsonobject.get("slaU193").toString());
            PropertiesUtils.setValue("slaU194",su_new_jsonobject.get("slaU194").toString());
            PropertiesUtils.setValue("slaU195",su_new_jsonobject.get("slaU195").toString());
            PropertiesUtils.setValue("slaU196",su_new_jsonobject.get("slaU196").toString());
            PropertiesUtils.setValue("slaU197",su_new_jsonobject.get("slaU197").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setdns")
    public R setdns(String dns_new) {
        JSONObject dns_new_jsonobject = JSON.parseObject(dns_new);
        try {
            PropertiesUtils.setValue("dns11", dns_new_jsonobject.get("dns11").toString());
            PropertiesUtils.setValue("dns12", dns_new_jsonobject.get("dns12").toString());
            PropertiesUtils.setValue("dns13", dns_new_jsonobject.get("dns13").toString());
            PropertiesUtils.setValue("dns14", dns_new_jsonobject.get("dns14").toString());
            PropertiesUtils.setValue("dns15", dns_new_jsonobject.get("dns15").toString());
            PropertiesUtils.setValue("dns16", dns_new_jsonobject.get("dns16").toString());
            PropertiesUtils.setValue("dns17", dns_new_jsonobject.get("dns17").toString());
            PropertiesUtils.setValue("dns21", dns_new_jsonobject.get("dns21").toString());
            PropertiesUtils.setValue("dns22", dns_new_jsonobject.get("dns22").toString());
            PropertiesUtils.setValue("dns23", dns_new_jsonobject.get("dns23").toString());
            PropertiesUtils.setValue("dns24", dns_new_jsonobject.get("dns24").toString());
            PropertiesUtils.setValue("dns25", dns_new_jsonobject.get("dns25").toString());
            PropertiesUtils.setValue("dns26", dns_new_jsonobject.get("dns26").toString());
            PropertiesUtils.setValue("dns27", dns_new_jsonobject.get("dns27").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setdhcp")
    public R setdhcp(String dhcp_new) {
        JSONObject dhcp_new_jsonobject = JSON.parseObject(dhcp_new);
        try {
            PropertiesUtils.setValue("dhcp11", dhcp_new_jsonobject.get("dhcp11").toString());
            PropertiesUtils.setValue("dhcp12", dhcp_new_jsonobject.get("dhcp12").toString());
            PropertiesUtils.setValue("dhcp13", dhcp_new_jsonobject.get("dhcp13").toString());
            PropertiesUtils.setValue("dhcp14", dhcp_new_jsonobject.get("dhcp14").toString());
            PropertiesUtils.setValue("dhcp15", dhcp_new_jsonobject.get("dhcp15").toString());
            PropertiesUtils.setValue("dhcp16", dhcp_new_jsonobject.get("dhcp16").toString());
            PropertiesUtils.setValue("dhcp17", dhcp_new_jsonobject.get("dhcp17").toString());
            PropertiesUtils.setValue("dhcp21", dhcp_new_jsonobject.get("dhcp21").toString());
            PropertiesUtils.setValue("dhcp22", dhcp_new_jsonobject.get("dhcp22").toString());
            PropertiesUtils.setValue("dhcp23", dhcp_new_jsonobject.get("dhcp23").toString());
            PropertiesUtils.setValue("dhcp24", dhcp_new_jsonobject.get("dhcp24").toString());
            PropertiesUtils.setValue("dhcp25", dhcp_new_jsonobject.get("dhcp25").toString());
            PropertiesUtils.setValue("dhcp26", dhcp_new_jsonobject.get("dhcp26").toString());
            PropertiesUtils.setValue("dhcp27", dhcp_new_jsonobject.get("dhcp27").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setadsl")
    public R setadsl(String adsl_new) {
        JSONObject adsl_new_jsonobject = JSON.parseObject(adsl_new);
        try {
            PropertiesUtils.setValue("adsl11", adsl_new_jsonobject.get("adsl11").toString());
            PropertiesUtils.setValue("adsl12", adsl_new_jsonobject.get("adsl12").toString());
            PropertiesUtils.setValue("adsl13", adsl_new_jsonobject.get("adsl13").toString());
            PropertiesUtils.setValue("adsl14", adsl_new_jsonobject.get("adsl14").toString());
            PropertiesUtils.setValue("adsl15", adsl_new_jsonobject.get("adsl15").toString());
            PropertiesUtils.setValue("adsl16", adsl_new_jsonobject.get("adsl16").toString());
            PropertiesUtils.setValue("adsl17", adsl_new_jsonobject.get("adsl17").toString());
            PropertiesUtils.setValue("adsl21", adsl_new_jsonobject.get("adsl21").toString());
            PropertiesUtils.setValue("adsl22", adsl_new_jsonobject.get("adsl22").toString());
            PropertiesUtils.setValue("adsl23", adsl_new_jsonobject.get("adsl23").toString());
            PropertiesUtils.setValue("adsl24", adsl_new_jsonobject.get("adsl24").toString());
            PropertiesUtils.setValue("adsl25", adsl_new_jsonobject.get("adsl25").toString());
            PropertiesUtils.setValue("adsl26", adsl_new_jsonobject.get("adsl26").toString());
            PropertiesUtils.setValue("adsl27", adsl_new_jsonobject.get("adsl27").toString());
            PropertiesUtils.setValue("adsl31", adsl_new_jsonobject.get("adsl31").toString());
            PropertiesUtils.setValue("adsl32", adsl_new_jsonobject.get("adsl32").toString());
            PropertiesUtils.setValue("adsl33", adsl_new_jsonobject.get("adsl33").toString());
            PropertiesUtils.setValue("adsl34", adsl_new_jsonobject.get("adsl34").toString());
            PropertiesUtils.setValue("adsl35", adsl_new_jsonobject.get("adsl35").toString());
            PropertiesUtils.setValue("adsl36", adsl_new_jsonobject.get("adsl36").toString());
            PropertiesUtils.setValue("adsl37", adsl_new_jsonobject.get("adsl37").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setradius")
    public R setradius(String radius_new) {
        JSONObject radius_new_jsonobject = JSON.parseObject(radius_new);
        try {
            PropertiesUtils.setValue("radius11", radius_new_jsonobject.get("radius11").toString());
            PropertiesUtils.setValue("radius12", radius_new_jsonobject.get("radius12").toString());
            PropertiesUtils.setValue("radius13", radius_new_jsonobject.get("radius13").toString());
            PropertiesUtils.setValue("radius14", radius_new_jsonobject.get("radius14").toString());
            PropertiesUtils.setValue("radius15", radius_new_jsonobject.get("radius15").toString());
            PropertiesUtils.setValue("radius16", radius_new_jsonobject.get("radius16").toString());
            PropertiesUtils.setValue("radius17", radius_new_jsonobject.get("radius17").toString());
            PropertiesUtils.setValue("radius21", radius_new_jsonobject.get("radius21").toString());
            PropertiesUtils.setValue("radius22", radius_new_jsonobject.get("radius22").toString());
            PropertiesUtils.setValue("radius23", radius_new_jsonobject.get("radius23").toString());
            PropertiesUtils.setValue("radius24", radius_new_jsonobject.get("radius24").toString());
            PropertiesUtils.setValue("radius25", radius_new_jsonobject.get("radius25").toString());
            PropertiesUtils.setValue("radius26", radius_new_jsonobject.get("radius26").toString());
            PropertiesUtils.setValue("radius27", radius_new_jsonobject.get("radius27").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setftpUpload")
    public R setftpUpload(String fu_new) {
        JSONObject fu_new_jsonobject = JSON.parseObject(fu_new);
        try {
            PropertiesUtils.setValue("ftpU11", fu_new_jsonobject.get("ftpU11").toString());
            PropertiesUtils.setValue("ftpU12", fu_new_jsonobject.get("ftpU12").toString());
            PropertiesUtils.setValue("ftpU13", fu_new_jsonobject.get("ftpU13").toString());
            PropertiesUtils.setValue("ftpU14", fu_new_jsonobject.get("ftpU14").toString());
            PropertiesUtils.setValue("ftpU15", fu_new_jsonobject.get("ftpU15").toString());
            PropertiesUtils.setValue("ftpU16", fu_new_jsonobject.get("ftpU16").toString());
            PropertiesUtils.setValue("ftpU17", fu_new_jsonobject.get("ftpU17").toString());
            PropertiesUtils.setValue("ftpU21", fu_new_jsonobject.get("ftpU21").toString());
            PropertiesUtils.setValue("ftpU22", fu_new_jsonobject.get("ftpU22").toString());
            PropertiesUtils.setValue("ftpU23", fu_new_jsonobject.get("ftpU23").toString());
            PropertiesUtils.setValue("ftpU24", fu_new_jsonobject.get("ftpU24").toString());
            PropertiesUtils.setValue("ftpU25", fu_new_jsonobject.get("ftpU25").toString());
            PropertiesUtils.setValue("ftpU26", fu_new_jsonobject.get("ftpU26").toString());
            PropertiesUtils.setValue("ftpU27", fu_new_jsonobject.get("ftpU27").toString());
            PropertiesUtils.setValue("ftpU31", fu_new_jsonobject.get("ftpU31").toString());
            PropertiesUtils.setValue("ftpU32", fu_new_jsonobject.get("ftpU32").toString());
            PropertiesUtils.setValue("ftpU33", fu_new_jsonobject.get("ftpU33").toString());
            PropertiesUtils.setValue("ftpU34", fu_new_jsonobject.get("ftpU34").toString());
            PropertiesUtils.setValue("ftpU35", fu_new_jsonobject.get("ftpU35").toString());
            PropertiesUtils.setValue("ftpU36", fu_new_jsonobject.get("ftpU36").toString());
            PropertiesUtils.setValue("ftpU37", fu_new_jsonobject.get("ftpU37").toString());
            PropertiesUtils.setValue("ftpU41", fu_new_jsonobject.get("ftpU41").toString());
            PropertiesUtils.setValue("ftpU42", fu_new_jsonobject.get("ftpU42").toString());
            PropertiesUtils.setValue("ftpU43", fu_new_jsonobject.get("ftpU43").toString());
            PropertiesUtils.setValue("ftpU44", fu_new_jsonobject.get("ftpU44").toString());
            PropertiesUtils.setValue("ftpU45", fu_new_jsonobject.get("ftpU45").toString());
            PropertiesUtils.setValue("ftpU46", fu_new_jsonobject.get("ftpU46").toString());
            PropertiesUtils.setValue("ftpU47", fu_new_jsonobject.get("ftpU47").toString());
            PropertiesUtils.setValue("ftpU51", fu_new_jsonobject.get("ftpU51").toString());
            PropertiesUtils.setValue("ftpU52", fu_new_jsonobject.get("ftpU52").toString());
            PropertiesUtils.setValue("ftpU53", fu_new_jsonobject.get("ftpU53").toString());
            PropertiesUtils.setValue("ftpU54", fu_new_jsonobject.get("ftpU54").toString());
            PropertiesUtils.setValue("ftpU55", fu_new_jsonobject.get("ftpU55").toString());
            PropertiesUtils.setValue("ftpU56", fu_new_jsonobject.get("ftpU56").toString());
            PropertiesUtils.setValue("ftpU57", fu_new_jsonobject.get("ftpU57").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setftpDownload")
    public R setftpDownload(String fd_new) {
        JSONObject fd_new_jsonobject = JSON.parseObject(fd_new);
        try {
            PropertiesUtils.setValue("ftpD11", fd_new_jsonobject.get("ftpD11").toString());
            PropertiesUtils.setValue("ftpD12", fd_new_jsonobject.get("ftpD12").toString());
            PropertiesUtils.setValue("ftpD13", fd_new_jsonobject.get("ftpD13").toString());
            PropertiesUtils.setValue("ftpD14", fd_new_jsonobject.get("ftpD14").toString());
            PropertiesUtils.setValue("ftpD15", fd_new_jsonobject.get("ftpD15").toString());
            PropertiesUtils.setValue("ftpD16", fd_new_jsonobject.get("ftpD16").toString());
            PropertiesUtils.setValue("ftpD17", fd_new_jsonobject.get("ftpD17").toString());
            PropertiesUtils.setValue("ftpD21", fd_new_jsonobject.get("ftpD21").toString());
            PropertiesUtils.setValue("ftpD22", fd_new_jsonobject.get("ftpD22").toString());
            PropertiesUtils.setValue("ftpD23", fd_new_jsonobject.get("ftpD23").toString());
            PropertiesUtils.setValue("ftpD24", fd_new_jsonobject.get("ftpD24").toString());
            PropertiesUtils.setValue("ftpD25", fd_new_jsonobject.get("ftpD25").toString());
            PropertiesUtils.setValue("ftpD26", fd_new_jsonobject.get("ftpD26").toString());
            PropertiesUtils.setValue("ftpD27", fd_new_jsonobject.get("ftpD27").toString());
            PropertiesUtils.setValue("ftpD31", fd_new_jsonobject.get("ftpD31").toString());
            PropertiesUtils.setValue("ftpD32", fd_new_jsonobject.get("ftpD32").toString());
            PropertiesUtils.setValue("ftpD33", fd_new_jsonobject.get("ftpD33").toString());
            PropertiesUtils.setValue("ftpD34", fd_new_jsonobject.get("ftpD34").toString());
            PropertiesUtils.setValue("ftpD35", fd_new_jsonobject.get("ftpD35").toString());
            PropertiesUtils.setValue("ftpD36", fd_new_jsonobject.get("ftpD36").toString());
            PropertiesUtils.setValue("ftpD37", fd_new_jsonobject.get("ftpD37").toString());
            PropertiesUtils.setValue("ftpD41", fd_new_jsonobject.get("ftpD41").toString());
            PropertiesUtils.setValue("ftpD42", fd_new_jsonobject.get("ftpD42").toString());
            PropertiesUtils.setValue("ftpD43", fd_new_jsonobject.get("ftpD43").toString());
            PropertiesUtils.setValue("ftpD44", fd_new_jsonobject.get("ftpD44").toString());
            PropertiesUtils.setValue("ftpD45", fd_new_jsonobject.get("ftpD45").toString());
            PropertiesUtils.setValue("ftpD46", fd_new_jsonobject.get("ftpD46").toString());
            PropertiesUtils.setValue("ftpD47", fd_new_jsonobject.get("ftpD47").toString());
            PropertiesUtils.setValue("ftpD51", fd_new_jsonobject.get("ftpD51").toString());
            PropertiesUtils.setValue("ftpD52", fd_new_jsonobject.get("ftpD52").toString());
            PropertiesUtils.setValue("ftpD53", fd_new_jsonobject.get("ftpD53").toString());
            PropertiesUtils.setValue("ftpD54", fd_new_jsonobject.get("ftpD54").toString());
            PropertiesUtils.setValue("ftpD55", fd_new_jsonobject.get("ftpD55").toString());
            PropertiesUtils.setValue("ftpD56", fd_new_jsonobject.get("ftpD56").toString());
            PropertiesUtils.setValue("ftpD57", fd_new_jsonobject.get("ftpD57").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }


    @RequestMapping("/setwebDownload")
    public R setwebDownload(String wd_new) {
        JSONObject wd_new_jsonobject = JSON.parseObject(wd_new);
        try {
            PropertiesUtils.setValue("webD11", wd_new_jsonobject.get("webD11").toString());
            PropertiesUtils.setValue("webD12", wd_new_jsonobject.get("webD12").toString());
            PropertiesUtils.setValue("webD13", wd_new_jsonobject.get("webD13").toString());
            PropertiesUtils.setValue("webD14", wd_new_jsonobject.get("webD14").toString());
            PropertiesUtils.setValue("webD15", wd_new_jsonobject.get("webD15").toString());
            PropertiesUtils.setValue("webD16", wd_new_jsonobject.get("webD16").toString());
            PropertiesUtils.setValue("webD17", wd_new_jsonobject.get("webD17").toString());
            PropertiesUtils.setValue("webD21", wd_new_jsonobject.get("webD21").toString());
            PropertiesUtils.setValue("webD22", wd_new_jsonobject.get("webD22").toString());
            PropertiesUtils.setValue("webD23", wd_new_jsonobject.get("webD23").toString());
            PropertiesUtils.setValue("webD24", wd_new_jsonobject.get("webD24").toString());
            PropertiesUtils.setValue("webD25", wd_new_jsonobject.get("webD25").toString());
            PropertiesUtils.setValue("webD26", wd_new_jsonobject.get("webD26").toString());
            PropertiesUtils.setValue("webD27", wd_new_jsonobject.get("webD27").toString());
            PropertiesUtils.setValue("webD31", wd_new_jsonobject.get("webD31").toString());
            PropertiesUtils.setValue("webD32", wd_new_jsonobject.get("webD32").toString());
            PropertiesUtils.setValue("webD33", wd_new_jsonobject.get("webD33").toString());
            PropertiesUtils.setValue("webD34", wd_new_jsonobject.get("webD34").toString());
            PropertiesUtils.setValue("webD35", wd_new_jsonobject.get("webD35").toString());
            PropertiesUtils.setValue("webD36", wd_new_jsonobject.get("webD36").toString());
            PropertiesUtils.setValue("webD37", wd_new_jsonobject.get("webD37").toString());
            PropertiesUtils.setValue("webD41", wd_new_jsonobject.get("webD41").toString());
            PropertiesUtils.setValue("webD42", wd_new_jsonobject.get("webD42").toString());
            PropertiesUtils.setValue("webD43", wd_new_jsonobject.get("webD43").toString());
            PropertiesUtils.setValue("webD44", wd_new_jsonobject.get("webD44").toString());
            PropertiesUtils.setValue("webD45", wd_new_jsonobject.get("webD45").toString());
            PropertiesUtils.setValue("webD46", wd_new_jsonobject.get("webD46").toString());
            PropertiesUtils.setValue("webD47", wd_new_jsonobject.get("webD47").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }


    @RequestMapping("/setwebpage")
    public R setwebpage(String webpage_new) {
        JSONObject webpage_new_jsonobject = JSON.parseObject(webpage_new);
        try {
            PropertiesUtils.setValue("webP11", webpage_new_jsonobject.get("webP11").toString());
            PropertiesUtils.setValue("webP12", webpage_new_jsonobject.get("webP12").toString());
            PropertiesUtils.setValue("webP13", webpage_new_jsonobject.get("webP13").toString());
            PropertiesUtils.setValue("webP14", webpage_new_jsonobject.get("webP14").toString());
            PropertiesUtils.setValue("webP15", webpage_new_jsonobject.get("webP15").toString());
            PropertiesUtils.setValue("webP16", webpage_new_jsonobject.get("webP16").toString());
            PropertiesUtils.setValue("webP17", webpage_new_jsonobject.get("webP17").toString());
            PropertiesUtils.setValue("webP21", webpage_new_jsonobject.get("webP21").toString());
            PropertiesUtils.setValue("webP22", webpage_new_jsonobject.get("webP22").toString());
            PropertiesUtils.setValue("webP23", webpage_new_jsonobject.get("webP23").toString());
            PropertiesUtils.setValue("webP24", webpage_new_jsonobject.get("webP24").toString());
            PropertiesUtils.setValue("webP25", webpage_new_jsonobject.get("webP25").toString());
            PropertiesUtils.setValue("webP26", webpage_new_jsonobject.get("webP26").toString());
            PropertiesUtils.setValue("webP27", webpage_new_jsonobject.get("webP27").toString());
            PropertiesUtils.setValue("webP31", webpage_new_jsonobject.get("webP31").toString());
            PropertiesUtils.setValue("webP32", webpage_new_jsonobject.get("webP32").toString());
            PropertiesUtils.setValue("webP33", webpage_new_jsonobject.get("webP33").toString());
            PropertiesUtils.setValue("webP34", webpage_new_jsonobject.get("webP34").toString());
            PropertiesUtils.setValue("webP35", webpage_new_jsonobject.get("webP35").toString());
            PropertiesUtils.setValue("webP36", webpage_new_jsonobject.get("webP36").toString());
            PropertiesUtils.setValue("webP37", webpage_new_jsonobject.get("webP37").toString());
            PropertiesUtils.setValue("webP41", webpage_new_jsonobject.get("webP41").toString());
            PropertiesUtils.setValue("webP42", webpage_new_jsonobject.get("webP42").toString());
            PropertiesUtils.setValue("webP43", webpage_new_jsonobject.get("webP43").toString());
            PropertiesUtils.setValue("webP44", webpage_new_jsonobject.get("webP44").toString());
            PropertiesUtils.setValue("webP45", webpage_new_jsonobject.get("webP45").toString());
            PropertiesUtils.setValue("webP46", webpage_new_jsonobject.get("webP46").toString());
            PropertiesUtils.setValue("webP47", webpage_new_jsonobject.get("webP47").toString());
            PropertiesUtils.setValue("webP51", webpage_new_jsonobject.get("webP51").toString());
            PropertiesUtils.setValue("webP52", webpage_new_jsonobject.get("webP52").toString());
            PropertiesUtils.setValue("webP53", webpage_new_jsonobject.get("webP53").toString());
            PropertiesUtils.setValue("webP54", webpage_new_jsonobject.get("webP54").toString());
            PropertiesUtils.setValue("webP55", webpage_new_jsonobject.get("webP55").toString());
            PropertiesUtils.setValue("webP56", webpage_new_jsonobject.get("webP56").toString());
            PropertiesUtils.setValue("webP57", webpage_new_jsonobject.get("webP57").toString());
            PropertiesUtils.setValue("webP61", webpage_new_jsonobject.get("webP61").toString());
            PropertiesUtils.setValue("webP62", webpage_new_jsonobject.get("webP62").toString());
            PropertiesUtils.setValue("webP63", webpage_new_jsonobject.get("webP63").toString());
            PropertiesUtils.setValue("webP64", webpage_new_jsonobject.get("webP64").toString());
            PropertiesUtils.setValue("webP65", webpage_new_jsonobject.get("webP65").toString());
            PropertiesUtils.setValue("webP66", webpage_new_jsonobject.get("webP66").toString());
            PropertiesUtils.setValue("webP67", webpage_new_jsonobject.get("webP67").toString());
            PropertiesUtils.setValue("webP71", webpage_new_jsonobject.get("webP71").toString());
            PropertiesUtils.setValue("webP72", webpage_new_jsonobject.get("webP72").toString());
            PropertiesUtils.setValue("webP73", webpage_new_jsonobject.get("webP73").toString());
            PropertiesUtils.setValue("webP74", webpage_new_jsonobject.get("webP74").toString());
            PropertiesUtils.setValue("webP75", webpage_new_jsonobject.get("webP75").toString());
            PropertiesUtils.setValue("webP76", webpage_new_jsonobject.get("webP76").toString());
            PropertiesUtils.setValue("webP77", webpage_new_jsonobject.get("webP77").toString());
            PropertiesUtils.setValue("webP81", webpage_new_jsonobject.get("webP81").toString());
            PropertiesUtils.setValue("webP82", webpage_new_jsonobject.get("webP82").toString());
            PropertiesUtils.setValue("webP83", webpage_new_jsonobject.get("webP83").toString());
            PropertiesUtils.setValue("webP84", webpage_new_jsonobject.get("webP84").toString());
            PropertiesUtils.setValue("webP85", webpage_new_jsonobject.get("webP85").toString());
            PropertiesUtils.setValue("webP86", webpage_new_jsonobject.get("webP86").toString());
            PropertiesUtils.setValue("webP87", webpage_new_jsonobject.get("webP87").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setvideo")
    public R setvideo(String video_new) {
        JSONObject video_new_jsonobject = JSON.parseObject(video_new);
        try {
            PropertiesUtils.setValue("video11", video_new_jsonobject.get("video11").toString());
            PropertiesUtils.setValue("video12", video_new_jsonobject.get("video12").toString());
            PropertiesUtils.setValue("video13", video_new_jsonobject.get("video13").toString());
            PropertiesUtils.setValue("video14", video_new_jsonobject.get("video14").toString());
            PropertiesUtils.setValue("video15", video_new_jsonobject.get("video15").toString());
            PropertiesUtils.setValue("video16", video_new_jsonobject.get("video16").toString());
            PropertiesUtils.setValue("video17", video_new_jsonobject.get("video17").toString());
            PropertiesUtils.setValue("video21", video_new_jsonobject.get("video21").toString());
            PropertiesUtils.setValue("video22", video_new_jsonobject.get("video22").toString());
            PropertiesUtils.setValue("video23", video_new_jsonobject.get("video23").toString());
            PropertiesUtils.setValue("video24", video_new_jsonobject.get("video24").toString());
            PropertiesUtils.setValue("video25", video_new_jsonobject.get("video25").toString());
            PropertiesUtils.setValue("video26", video_new_jsonobject.get("video26").toString());
            PropertiesUtils.setValue("video27", video_new_jsonobject.get("video27").toString());
            PropertiesUtils.setValue("video31", video_new_jsonobject.get("video31").toString());
            PropertiesUtils.setValue("video32", video_new_jsonobject.get("video32").toString());
            PropertiesUtils.setValue("video33", video_new_jsonobject.get("video33").toString());
            PropertiesUtils.setValue("video34", video_new_jsonobject.get("video34").toString());
            PropertiesUtils.setValue("video35", video_new_jsonobject.get("video35").toString());
            PropertiesUtils.setValue("video36", video_new_jsonobject.get("video36").toString());
            PropertiesUtils.setValue("video37", video_new_jsonobject.get("video37").toString());
            PropertiesUtils.setValue("video41", video_new_jsonobject.get("video41").toString());
            PropertiesUtils.setValue("video42", video_new_jsonobject.get("video42").toString());
            PropertiesUtils.setValue("video43", video_new_jsonobject.get("video43").toString());
            PropertiesUtils.setValue("video44", video_new_jsonobject.get("video44").toString());
            PropertiesUtils.setValue("video45", video_new_jsonobject.get("video45").toString());
            PropertiesUtils.setValue("video46", video_new_jsonobject.get("video46").toString());
            PropertiesUtils.setValue("video47", video_new_jsonobject.get("video47").toString());
            PropertiesUtils.setValue("video51", video_new_jsonobject.get("video51").toString());
            PropertiesUtils.setValue("video52", video_new_jsonobject.get("video52").toString());
            PropertiesUtils.setValue("video53", video_new_jsonobject.get("video53").toString());
            PropertiesUtils.setValue("video54", video_new_jsonobject.get("video54").toString());
            PropertiesUtils.setValue("video55", video_new_jsonobject.get("video55").toString());
            PropertiesUtils.setValue("video56", video_new_jsonobject.get("video56").toString());
            PropertiesUtils.setValue("video57", video_new_jsonobject.get("video57").toString());
            PropertiesUtils.setValue("video61", video_new_jsonobject.get("video61").toString());
            PropertiesUtils.setValue("video62", video_new_jsonobject.get("video62").toString());
            PropertiesUtils.setValue("video63", video_new_jsonobject.get("video63").toString());
            PropertiesUtils.setValue("video64", video_new_jsonobject.get("video64").toString());
            PropertiesUtils.setValue("video65", video_new_jsonobject.get("video65").toString());
            PropertiesUtils.setValue("video66", video_new_jsonobject.get("video66").toString());
            PropertiesUtils.setValue("video67", video_new_jsonobject.get("video67").toString());
            PropertiesUtils.setValue("video71", video_new_jsonobject.get("video71").toString());
            PropertiesUtils.setValue("video72", video_new_jsonobject.get("video72").toString());
            PropertiesUtils.setValue("video73", video_new_jsonobject.get("video73").toString());
            PropertiesUtils.setValue("video74", video_new_jsonobject.get("video74").toString());
            PropertiesUtils.setValue("video75", video_new_jsonobject.get("video75").toString());
            PropertiesUtils.setValue("video76", video_new_jsonobject.get("video76").toString());
            PropertiesUtils.setValue("video77", video_new_jsonobject.get("video77").toString());
            PropertiesUtils.setValue("video81", video_new_jsonobject.get("video81").toString());
            PropertiesUtils.setValue("video82", video_new_jsonobject.get("video82").toString());
            PropertiesUtils.setValue("video83", video_new_jsonobject.get("video83").toString());
            PropertiesUtils.setValue("video84", video_new_jsonobject.get("video84").toString());
            PropertiesUtils.setValue("video85", video_new_jsonobject.get("video85").toString());
            PropertiesUtils.setValue("video86", video_new_jsonobject.get("video86").toString());
            PropertiesUtils.setValue("video87", video_new_jsonobject.get("video87").toString());
            PropertiesUtils.setValue("video91", video_new_jsonobject.get("video91").toString());
            PropertiesUtils.setValue("video92", video_new_jsonobject.get("video92").toString());
            PropertiesUtils.setValue("video93", video_new_jsonobject.get("video93").toString());
            PropertiesUtils.setValue("video94", video_new_jsonobject.get("video94").toString());
            PropertiesUtils.setValue("video95", video_new_jsonobject.get("video95").toString());
            PropertiesUtils.setValue("video96", video_new_jsonobject.get("video96").toString());
            PropertiesUtils.setValue("video97", video_new_jsonobject.get("video97").toString());
            PropertiesUtils.setValue("video101", video_new_jsonobject.get("video101").toString());
            PropertiesUtils.setValue("video102", video_new_jsonobject.get("video102").toString());
            PropertiesUtils.setValue("video103", video_new_jsonobject.get("video103").toString());
            PropertiesUtils.setValue("video104", video_new_jsonobject.get("video104").toString());
            PropertiesUtils.setValue("video105", video_new_jsonobject.get("video105").toString());
            PropertiesUtils.setValue("video106", video_new_jsonobject.get("video106").toString());
            PropertiesUtils.setValue("video107", video_new_jsonobject.get("video107").toString());
            PropertiesUtils.setValue("video111", video_new_jsonobject.get("video111").toString());
            PropertiesUtils.setValue("video112", video_new_jsonobject.get("video112").toString());
            PropertiesUtils.setValue("video113", video_new_jsonobject.get("video113").toString());
            PropertiesUtils.setValue("video114", video_new_jsonobject.get("video114").toString());
            PropertiesUtils.setValue("video115", video_new_jsonobject.get("video115").toString());
            PropertiesUtils.setValue("video116", video_new_jsonobject.get("video116").toString());
            PropertiesUtils.setValue("video117", video_new_jsonobject.get("video117").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }

    @RequestMapping("/setgame")
    public R setgame(String game_new) {
        JSONObject game_new_jsonobject = JSON.parseObject(game_new);
        try {
            PropertiesUtils.setValue("game11", game_new_jsonobject.get("game11").toString());
            PropertiesUtils.setValue("game12", game_new_jsonobject.get("game12").toString());
            PropertiesUtils.setValue("game13", game_new_jsonobject.get("game13").toString());
            PropertiesUtils.setValue("game14", game_new_jsonobject.get("game14").toString());
            PropertiesUtils.setValue("game15", game_new_jsonobject.get("game15").toString());
            PropertiesUtils.setValue("game16", game_new_jsonobject.get("game16").toString());
            PropertiesUtils.setValue("game17", game_new_jsonobject.get("game17").toString());
            PropertiesUtils.setValue("game21", game_new_jsonobject.get("game21").toString());
            PropertiesUtils.setValue("game22", game_new_jsonobject.get("game22").toString());
            PropertiesUtils.setValue("game23", game_new_jsonobject.get("game23").toString());
            PropertiesUtils.setValue("game24", game_new_jsonobject.get("game24").toString());
            PropertiesUtils.setValue("game25", game_new_jsonobject.get("game25").toString());
            PropertiesUtils.setValue("game26", game_new_jsonobject.get("game26").toString());
            PropertiesUtils.setValue("game27", game_new_jsonobject.get("game27").toString());
            PropertiesUtils.setValue("game31", game_new_jsonobject.get("game31").toString());
            PropertiesUtils.setValue("game32", game_new_jsonobject.get("game32").toString());
            PropertiesUtils.setValue("game33", game_new_jsonobject.get("game33").toString());
            PropertiesUtils.setValue("game34", game_new_jsonobject.get("game34").toString());
            PropertiesUtils.setValue("game35", game_new_jsonobject.get("game35").toString());
            PropertiesUtils.setValue("game36", game_new_jsonobject.get("game36").toString());
            PropertiesUtils.setValue("game37", game_new_jsonobject.get("game37").toString());
            PropertiesUtils.setValue("game41", game_new_jsonobject.get("game41").toString());
            PropertiesUtils.setValue("game42", game_new_jsonobject.get("game42").toString());
            PropertiesUtils.setValue("game43", game_new_jsonobject.get("game43").toString());
            PropertiesUtils.setValue("game44", game_new_jsonobject.get("game44").toString());
            PropertiesUtils.setValue("game45", game_new_jsonobject.get("game45").toString());
            PropertiesUtils.setValue("game46", game_new_jsonobject.get("game46").toString());
            PropertiesUtils.setValue("game47", game_new_jsonobject.get("game47").toString());
            PropertiesUtils.setValue("game51", game_new_jsonobject.get("game51").toString());
            PropertiesUtils.setValue("game52", game_new_jsonobject.get("game52").toString());
            PropertiesUtils.setValue("game53", game_new_jsonobject.get("game53").toString());
            PropertiesUtils.setValue("game54", game_new_jsonobject.get("game54").toString());
            PropertiesUtils.setValue("game55", game_new_jsonobject.get("game55").toString());
            PropertiesUtils.setValue("game56", game_new_jsonobject.get("game56").toString());
            PropertiesUtils.setValue("game57", game_new_jsonobject.get("game57").toString());
        } catch (IOException e) {

        }
        return R.ok();
    }
}