var layers = new Map();
var layerNames = new Map();
    // 地图
  $(function () {
      require.config({
          paths: {
              echarts: './statics/js/echarts-2.2.7/build/dist',
              'echarts/chart/map':'/statics/js/echarts-2.2.7/build/dist/chart/map'
          }
      });
      require(
          [
              'echarts',
              'echarts/chart/map'
          ],
          function(ec) {
              // 基于准备好的dom，初始化echarts图表
              var myChart = ec.init(document.getElementById('container'));
              var ecConfig = require('echarts/config');
              var zrEvent = require('zrender/tool/event');
              var curIndx = 0;
              var mapType = [
                  'china',
                  // 23个省
                  '广东', '青海', '四川', '海南', '陕西',
                  '甘肃', '云南', '湖南', '湖北', '黑龙江',
                  '贵州', '山东', '江西', '河南', '河北',
                  '山西', '安徽', '福建', '浙江', '江苏',
                  '吉林', '辽宁', '台湾',
                  // 5个自治区
                  '新疆', '广西', '宁夏', '内蒙古', '西藏',
                  // 4个直辖市
                  '北京', '天津', '上海', '重庆',
                  // 2个特别行政区
                  '香港', '澳门',
              ];
              var cityMap = {
                  "北京市": "110100",
                  "天津市": "120100",
                  "上海市": "310100",
                  "重庆市": "500100",

                  "崇明县": "310200", //
                  "湖北省直辖县市": "429000", //
                  "铜仁市": "522200", //
                  "毕节市": "522400", //

                  "石家庄市": "130100",
                  "唐山市": "130200",
                  "秦皇岛市": "130300",
                  "邯郸市": "130400",
                  "邢台市": "130500",
                  "保定市": "130600",
                  "张家口市": "130700",
                  "承德市": "130800",
                  "沧州市": "130900",
                  "廊坊市": "13100",
                  "衡水市": "131100",
                  "太原市": "140100",
                  "大同市": "140200",
                  "阳泉市": "140300",
                  "长治市": "140400",
                  "晋城市": "140500",
                  "朔州市": "140600",
                  "晋中市": "140700",
                  "运城市": "140800",
                  "忻州市": "140900",
                  "临汾市": "14100",
                  "吕梁市": "141100",
                  "呼和浩特市": "150100",
                  "包头市": "150200",
                  "乌海市": "150300",
                  "赤峰市": "150400",
                  "通辽市": "150500",
                  "鄂尔多斯市": "150600",
                  "呼伦贝尔市": "150700",
                  "巴彦淖尔市": "150800",
                  "乌兰察布市": "150900",
                  "兴安盟": "152200",
                  "锡林郭勒盟": "152500",
                  "阿拉善盟": "152900",
                  "沈阳市": "210100",
                  "大连市": "210200",
                  "鞍山市": "210300",
                  "抚顺市": "210400",
                  "本溪市": "210500",
                  "丹东市": "210600",
                  "锦州市": "210700",
                  "营口市": "210800",
                  "阜新市": "210900",
                  "辽阳市": "21100",
                  "盘锦市": "211100",
                  "铁岭市": "211200",
                  "朝阳市": "211300",
                  "葫芦岛市": "211400",
                  "长春市": "220100",
                  "吉林市": "220200",
                  "四平市": "220300",
                  "辽源市": "220400",
                  "通化市": "220500",
                  "白山市": "220600",
                  "松原市": "220700",
                  "白城市": "220800",
                  "延边朝鲜族自治州": "222400",
                  "哈尔滨市": "230100",
                  "齐齐哈尔市": "230200",
                  "鸡西市": "230300",
                  "鹤岗市": "230400",
                  "双鸭山市": "230500",
                  "大庆市": "230600",
                  "伊春市": "230700",
                  "佳木斯市": "230800",
                  "七台河市": "230900",
                  "牡丹江市": "23100",
                  "黑河市": "231100",
                  "绥化市": "231200",
                  "大兴安岭地区": "232700",
                  "南京市": "320100",
                  "无锡市": "320200",
                  "徐州市": "320300",
                  "常州市": "320400",
                  "苏州市": "320500",
                  "南通市": "320600",
                  "连云港市": "320700",
                  "淮安市": "320800",
                  "盐城市": "320900",
                  "扬州市": "32100",
                  "镇江市": "321100",
                  "泰州市": "321200",
                  "宿迁市": "321300",
                  "杭州市": "330100",
                  "宁波市": "330200",
                  "温州市": "330300",
                  "嘉兴市": "330400",
                  "湖州市": "330500",
                  "绍兴市": "330600",
                  "金华市": "330700",
                  "衢州市": "330800",
                  "舟山市": "330900",
                  "台州市": "33100",
                  "丽水市": "331100",
                  "合肥市": "340100",
                  "芜湖市": "340200",
                  "蚌埠市": "340300",
                  "淮南市": "340400",
                  "马鞍山市": "340500",
                  "淮北市": "340600",
                  "铜陵市": "340700",
                  "安庆市": "340800",
                  "黄山市": "34100",
                  "滁州市": "341100",
                  "阜阳市": "341200",
                  "宿州市": "341300",
                  "六安市": "341500",
                  "亳州市": "341600",
                  "池州市": "341700",
                  "宣城市": "341800",
                  "福州市": "350100",
                  "厦门市": "350200",
                  "莆田市": "350300",
                  "三明市": "350400",
                  "泉州市": "350500",
                  "漳州市": "350600",
                  "南平市": "350700",
                  "龙岩市": "350800",
                  "宁德市": "350900",
                  "南昌市": "360100",
                  "景德镇市": "360200",
                  "萍乡市": "360300",
                  "九江市": "360400",
                  "新余市": "360500",
                  "鹰潭市": "360600",
                  "赣州市": "360700",
                  "吉安市": "360800",
                  "宜春市": "360900",
                  "抚州市": "36100",
                  "上饶市": "361100",
                  "济南市": "370100",
                  "青岛市": "370200",
                  "淄博市": "370300",
                  "枣庄市": "370400",
                  "东营市": "370500",
                  "烟台市": "370600",
                  "潍坊市": "370700",
                  "济宁市": "370800",
                  "泰安市": "370900",
                  "威海市": "37100",
                  "日照市": "371100",
                  "莱芜市": "371200",
                  "临沂市": "371300",
                  "德州市": "371400",
                  "聊城市": "371500",
                  "滨州市": "371600",
                  "菏泽市": "371700",
                  "郑州市": "410100",
                  "开封市": "410200",
                  "洛阳市": "410300",
                  "平顶山市": "410400",
                  "安阳市": "410500",
                  "鹤壁市": "410600",
                  "新乡市": "410700",
                  "焦作市": "410800",
                  "濮阳市": "410900",
                  "许昌市": "41100",
                  "漯河市": "411100",
                  "三门峡市": "411200",
                  "南阳市": "411300",
                  "商丘市": "411400",
                  "信阳市": "411500",
                  "周口市": "411600",
                  "驻马店市": "411700",
                  "省直辖县级行政区划": "469000",
                  "武汉市": "420100",
                  "黄石市": "420200",
                  "十堰市": "420300",
                  "宜昌市": "420500",
                  "襄阳市": "420600",
                  "鄂州市": "420700",
                  "荆门市": "420800",
                  "孝感市": "420900",
                  "荆州市": "42100",
                  "黄冈市": "421100",
                  "咸宁市": "421200",
                  "随州市": "421300",
                  "恩施土家族苗族自治州": "422800",
                  "长沙市": "430100",
                  "株洲市": "430200",
                  "湘潭市": "430300",
                  "衡阳市": "430400",
                  "邵阳市": "430500",
                  "岳阳市": "430600",
                  "常德市": "430700",
                  "张家界市": "430800",
                  "益阳市": "430900",
                  "郴州市": "43100",
                  "永州市": "431100",
                  "怀化市": "431200",
                  "娄底市": "431300",
                  "湘西土家族苗族自治州": "433100",
                  "广州市": "440100",
                  "韶关市": "440200",
                  "深圳市": "440300",
                  "珠海市": "440400",
                  "汕头市": "440500",
                  "佛山市": "440600",
                  "江门市": "440700",
                  "湛江市": "440800",
                  "茂名市": "440900",
                  "肇庆市": "441200",
                  "惠州市": "441300",
                  "梅州市": "441400",
                  "汕尾市": "441500",
                  "河源市": "441600",
                  "阳江市": "441700",
                  "清远市": "441800",
                  "东莞市": "441900",
                  "中山市": "442000",
                  "潮州市": "445100",
                  "揭阳市": "445200",
                  "云浮市": "445300",
                  "南宁市": "450100",
                  "柳州市": "450200",
                  "桂林市": "450300",
                  "梧州市": "450400",
                  "北海市": "450500",
                  "防城港市": "450600",
                  "钦州市": "450700",
                  "贵港市": "450800",
                  "玉林市": "450900",
                  "百色市": "45100",
                  "贺州市": "451100",
                  "河池市": "451200",
                  "来宾市": "451300",
                  "崇左市": "451400",
                  "海口市": "460100",
                  "三亚市": "460200",
                  "三沙市": "460300",
                  "成都市": "510100",
                  "自贡市": "510300",
                  "攀枝花市": "510400",
                  "泸州市": "510500",
                  "德阳市": "510600",
                  "绵阳市": "510700",
                  "广元市": "510800",
                  "遂宁市": "510900",
                  "内江市": "51100",
                  "乐山市": "511100",
                  "南充市": "511300",
                  "眉山市": "511400",
                  "宜宾市": "511500",
                  "广安市": "511600",
                  "达州市": "511700",
                  "雅安市": "511800",
                  "巴中市": "511900",
                  "资阳市": "512000",
                  "阿坝藏族羌族自治州": "513200",
                  "甘孜藏族自治州": "513300",
                  "凉山彝族自治州": "513400",
                  "贵阳市": "520100",
                  "六盘水市": "520200",
                  "遵义市": "520300",
                  "安顺市": "520400",
                  "黔西南布依族苗族自治州": "522300",
                  "黔东南苗族侗族自治州": "522600",
                  "黔南布依族苗族自治州": "522700",
                  "昆明市": "530100",
                  "曲靖市": "530300",
                  "玉溪市": "530400",
                  "保山市": "530500",
                  "昭通市": "530600",
                  "丽江市": "530700",
                  "普洱市": "530800",
                  "临沧市": "530900",
                  "楚雄彝族自治州": "532300",
                  "红河哈尼族彝族自治州": "532500",
                  "文山壮族苗族自治州": "532600",
                  "西双版纳傣族自治州": "532800",
                  "大理白族自治州": "532900",
                  "德宏傣族景颇族自治州": "533100",
                  "怒江傈僳族自治州": "533300",
                  "迪庆藏族自治州": "533400",
                  "拉萨市": "540100",
                  "昌都地区": "542100",
                  "山南地区": "542200",
                  "日喀则地区": "542300",
                  "那曲地区": "542400",
                  "阿里地区": "542500",
                  "林芝地区": "542600",
                  "西安市": "610100",
                  "铜川市": "610200",
                  "宝鸡市": "610300",
                  "咸阳市": "610400",
                  "渭南市": "610500",
                  "延安市": "610600",
                  "汉中市": "610700",
                  "榆林市": "610800",
                  "安康市": "610900",
                  "商洛市": "61100",
                  "兰州市": "620100",
                  "嘉峪关市": "620200",
                  "金昌市": "620300",
                  "白银市": "620400",
                  "天水市": "620500",
                  "武威市": "620600",
                  "张掖市": "620700",
                  "平凉市": "620800",
                  "酒泉市": "620900",
                  "庆阳市": "62100",
                  "定西市": "621100",
                  "陇南市": "621200",
                  "临夏回族自治州": "622900",
                  "甘南藏族自治州": "623000",
                  "西宁市": "630100",
                  "海东地区": "632100",
                  "海北藏族自治州": "632200",
                  "黄南藏族自治州": "632300",
                  "海南藏族自治州": "632500",
                  "果洛藏族自治州": "632600",
                  "玉树藏族自治州": "632700",
                  "海西蒙古族藏族自治州": "632800",
                  "银川市": "640100",
                  "石嘴山市": "640200",
                  "吴忠市": "640300",
                  "固原市": "640400",
                  "中卫市": "640500",
                  "乌鲁木齐市": "650100",
                  "克拉玛依市": "650200",
                  "吐鲁番地区": "652100",
                  "哈密地区": "652200",
                  "昌吉回族自治州": "652300",
                  "博尔塔拉蒙古自治州": "652700",
                  "巴音郭楞蒙古自治州": "652800",
                  "阿克苏地区": "652900",
                  "克孜勒苏柯尔克孜自治州": "653000",
                  "喀什地区": "653100",
                  "和田地区": "653200",
                  "伊犁哈萨克自治州": "654000",
                  "塔城地区": "654200",
                  "阿勒泰地区": "654300",
                  "自治区直辖县级行政区划": "659000",
                  "台湾省": "71000",
                  "香港特别行政区": "810100",
                  "澳门特别行政区": "820000"
              };
              var mapGeoData = require('echarts/util/mapData/params');
              for(var city in cityMap) {
                  mapType.push(city);
                  // 自定义扩展图表类型
                  mapGeoData.params[city] = {
                      getGeoJson: (function(c) {
                          var geoJsonName = cityMap[c];
                          return function(callback) {
                              $.getJSON('geoJson/china-main-city/' + geoJsonName + '.json', callback);
                          }
                      })(city)
                  }
              }
              myChart.on(ecConfig.EVENT.MAP_SELECTED, function(param) {
                  var len = mapType.length;
                  var mt = param.target;
                  var f = false;
                  for(var i = 0; i < len; i++) {
                      if(mt == mapType[i]) {
                          f = true;
                          mt = mapType[i];
                      }
                  }
                  if(!f) {
                      mt = 'china';
                      option.title.text = "网内各地区平均感知统计";
                  }
                  else if(mt.indexOf("市")>-1||mt.indexOf("州")>-1||mt.indexOf("区")>-1){
                      mt = 'china';
                      option.title.text = "网内各地区平均感知统计";
                  }
                  else{
                      option.title.text = mt+"平均感知统计";
                  }
                  option.tooltip.trigger = 'item';
                  option.series[0].mapType = mt;
                  myChart.setOption(option, true);
              });
              option = {
                  title: {
                      text: '网内各地区平均感知统计',
                      x: 'center'
                  },
                  tooltip: {
                      trigger: 'item',
                      padding:0,
                      position:function(p){
                          var id = document.getElementById('container');
                          if ($(id).width() - p[0]- $(id).find("div .echarts-tooltip").width()-20 <0) {
                              p[0] = p[0] - $(id).find("div .echarts-tooltip").width() -40;
                          }
                          return [p[0], p[1]];
                      },
                      formatter: "{a} <br/>{b} : {c} "
                  },
                  color:['#77BBEF'],
                  legend: {
                      orient: 'vertical',//水平布局
                      x: 'left',
                      data: ['QoE'],

                  },

                  dataRange: {
                      min: 60,
                      max: 100,
                      x: 'left',
                      y: 'bottom',
                      text:['高','低'],           // 文本，默认为数值文本
                      calculable : true
                  },
                  toolbox: {
                      show: true,
                      orient : 'vertical',
                      x: 'right',
                      y: 'bottom',
                      feature : {
                          dataView : {show: true, readOnly: false},
                          restore : {show: true},
                          saveAsImage : {show: true}
                      }
                  },
                  series: [{
                      name: 'QoE',
                      type: 'map',
                      mapType: 'china',
                      selectedMode: 'single',
                      roam: false,
                      itemStyle:{
                          normal:{label:{show:true}},
                          emphasis:{label:{show:true}}
                      },
                      data://[{name:'北京',value:250}]//显示正常;[{name:'北京市',value:250}]//不能正常显示 数据库 里的值就是“北京市”
                      (function(){
                          var arr=[];
                          $.ajax({
                              type: "post",
                              async: false, //同步执行
                              url: "../../cem/collect/cityrankingview",
                              data : {serviceType:'2'},
                              dataType: "json", //返回数据形式为json
                              success: function (result) {
                                  arr = result.scoreCollects;
                                  var i = 0;
                              }
                          })

                          return arr;

                      })()
                      // data: [
                      //     { name: '湖南', value: Math.round(Math.random() * 100) },
                      //     { name: '湖北', value: Math.round(Math.random() * 100) },
                      //     { name: '广东', value: Math.round(Math.random() * 100) },
                      //     { name: '青海', value: Math.round(Math.random() * 100) },
                      //     { name: '四川', value: Math.round(Math.random() * 100) },
                      //     { name: '海南', value: Math.round(Math.random() * 100) },
                      //     { name: '陕西', value: Math.round(Math.random() * 100) },
                      //     { name: '甘肃', value: Math.round(Math.random() * 100) },
                      //     { name: '云南', value: Math.round(Math.random() * 100) },
                      //     { name: '黑龙江', value: Math.round(Math.random() * 100) },
                      //     { name: '贵州', value: Math.round(Math.random() * 100) },
                      //     { name: '山东', value: Math.round(Math.random() * 100) },
                      //     { name: '江西', value: Math.round(Math.random() * 100) },
                      //     { name: '河南', value: Math.round(Math.random() * 100) },
                      //     { name: '河北', value: Math.round(Math.random() * 100) },
                      //     { name: '山西', value: Math.round(Math.random() * 100) },
                      //     { name: '安徽', value: Math.round(Math.random() * 100) },
                      //     { name: '福建', value: Math.round(Math.random() * 100) },
                      //     { name: '浙江', value: Math.round(Math.random() * 100) },
                      //     { name: '江苏', value: Math.round(Math.random() * 100) },
                      //     { name: '吉林', value: Math.round(Math.random() * 100) },
                      //     { name: '辽宁', value: Math.round(Math.random() * 100) },
                      //     { name: '台湾', value: Math.round(Math.random() * 100) },
                      //     { name: '新疆', value: Math.round(Math.random() * 100) },
                      //     { name: '广西', value: Math.round(Math.random() * 100) },
                      //     { name: '宁夏', value: Math.round(Math.random() * 100) },
                      //     { name: '内蒙古', value: Math.round(Math.random() * 100) },
                      //     { name: '西藏', value: Math.round(Math.random() * 100) },
                      //     { name: '北京', value: Math.round(Math.random() * 100) },
                      //     { name: '天津', value: Math.round(Math.random() * 100) },
                      //     { name: '重庆', value: Math.round(Math.random() * 100) },
                      //     { name: '上海', value: Math.round(Math.random() * 100) },
                      //     { name: '广州', value: Math.round(Math.random() * 100) },
                      //     { name: '重庆市', value: Math.round(Math.random() * 100) },
                      //     { name: '北京市', value: Math.round(Math.random() * 100) },
                      //     { name: '天津市', value: Math.round(Math.random() * 100) },
                      //     { name: '上海市', value: Math.round(Math.random() * 100) },
                      //     { name: '香港', value: Math.round(Math.random() * 100) },
                      //     { name: '澳门', value: Math.round(Math.random() * 100) },
                      //     { name: '巴音郭楞蒙古自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '和田地区', value: Math.round(Math.random() * 100) },
                      //     { name: '哈密地区', value: Math.round(Math.random() * 100) },
                      //     { name: '阿克苏地区', value: Math.round(Math.random() * 100) },
                      //     { name: '阿勒泰地区', value: Math.round(Math.random() * 100) },
                      //     { name: '喀什地区', value: Math.round(Math.random() * 100) },
                      //     { name: '塔城地区', value: Math.round(Math.random() * 100) },
                      //     { name: '昌吉回族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '克孜勒苏柯尔克孜自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '吐鲁番地区', value: Math.round(Math.random() * 100) },
                      //     { name: '伊犁哈萨克自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '博尔塔拉蒙古自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '乌鲁木齐市', value: Math.round(Math.random() * 100) },
                      //     { name: '克拉玛依市', value: Math.round(Math.random() * 100) },
                      //     { name: '阿拉尔市', value: Math.round(Math.random() * 100) },
                      //     { name: '图木舒克市', value: Math.round(Math.random() * 100) },
                      //     { name: '五家渠市', value: Math.round(Math.random() * 100) },
                      //     { name: '石河子市', value: Math.round(Math.random() * 100) },
                      //     { name: '那曲地区', value: Math.round(Math.random() * 100) },
                      //     { name: '阿里地区', value: Math.round(Math.random() * 100) },
                      //     { name: '日喀则地区', value: Math.round(Math.random() * 100) },
                      //     { name: '林芝地区', value: Math.round(Math.random() * 100) },
                      //     { name: '昌都地区', value: Math.round(Math.random() * 100) },
                      //     { name: '山南地区', value: Math.round(Math.random() * 100) },
                      //     { name: '拉萨市', value: Math.round(Math.random() * 100) },
                      //     { name: '呼伦贝尔市', value: Math.round(Math.random() * 100) },
                      //     { name: '阿拉善盟', value: Math.round(Math.random() * 100) },
                      //     { name: '锡林郭勒盟', value: Math.round(Math.random() * 100) },
                      //     { name: '鄂尔多斯市', value: Math.round(Math.random() * 100) },
                      //     { name: '赤峰市', value: Math.round(Math.random() * 100) },
                      //     { name: '巴彦淖尔市', value: Math.round(Math.random() * 100) },
                      //     { name: '通辽市', value: Math.round(Math.random() * 100) },
                      //     { name: '乌兰察布市', value: Math.round(Math.random() * 100) },
                      //     { name: '兴安盟', value: Math.round(Math.random() * 100) },
                      //     { name: '包头市', value: Math.round(Math.random() * 100) },
                      //     { name: '呼和浩特市', value: Math.round(Math.random() * 100) },
                      //     { name: '乌海市', value: Math.round(Math.random() * 100) },
                      //     { name: '海西蒙古族藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '玉树藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '果洛藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '海南藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '海北藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '黄南藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '海东地区', value: Math.round(Math.random() * 100) },
                      //     { name: '西宁市', value: Math.round(Math.random() * 100) },
                      //     { name: '甘孜藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '阿坝藏族羌族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '凉山彝族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '绵阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '达州市', value: Math.round(Math.random() * 100) },
                      //     { name: '广元市', value: Math.round(Math.random() * 100) },
                      //     { name: '雅安市', value: Math.round(Math.random() * 100) },
                      //     { name: '宜宾市', value: Math.round(Math.random() * 100) },
                      //     { name: '乐山市', value: Math.round(Math.random() * 100) },
                      //     { name: '南充市', value: Math.round(Math.random() * 100) },
                      //     { name: '巴中市', value: Math.round(Math.random() * 100) },
                      //     { name: '泸州市', value: Math.round(Math.random() * 100) },
                      //     { name: '成都市', value: Math.round(Math.random() * 100) },
                      //     { name: '资阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '攀枝花市', value: Math.round(Math.random() * 100) },
                      //     { name: '眉山市', value: Math.round(Math.random() * 100) },
                      //     { name: '广安市', value: Math.round(Math.random() * 100) },
                      //     { name: '德阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '内江市', value: Math.round(Math.random() * 100) },
                      //     { name: '遂宁市', value: Math.round(Math.random() * 100) },
                      //     { name: '自贡市', value: Math.round(Math.random() * 100) },
                      //     { name: '黑河市', value: Math.round(Math.random() * 100) },
                      //     { name: '大兴安岭地区', value: Math.round(Math.random() * 100) },
                      //     { name: '哈尔滨市', value: Math.round(Math.random() * 100) },
                      //     { name: '齐齐哈尔市', value: Math.round(Math.random() * 100) },
                      //     { name: '牡丹江市', value: Math.round(Math.random() * 100) },
                      //     { name: '绥化市', value: Math.round(Math.random() * 100) },
                      //     { name: '伊春市', value: Math.round(Math.random() * 100) },
                      //     { name: '佳木斯市', value: Math.round(Math.random() * 100) },
                      //     { name: '鸡西市', value: Math.round(Math.random() * 100) },
                      //     { name: '双鸭山市', value: Math.round(Math.random() * 100) },
                      //     { name: '大庆市', value: Math.round(Math.random() * 100) },
                      //     { name: '鹤岗市', value: Math.round(Math.random() * 100) },
                      //     { name: '七台河市', value: Math.round(Math.random() * 100) },
                      //     { name: '酒泉市', value: Math.round(Math.random() * 100) },
                      //     { name: '张掖市', value: Math.round(Math.random() * 100) },
                      //     { name: '甘南藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '武威市', value: Math.round(Math.random() * 100) },
                      //     { name: '陇南市', value: Math.round(Math.random() * 100) },
                      //     { name: '庆阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '白银市', value: Math.round(Math.random() * 100) },
                      //     { name: '定西市', value: Math.round(Math.random() * 100) },
                      //     { name: '天水市', value: Math.round(Math.random() * 100) },
                      //     { name: '兰州市', value: Math.round(Math.random() * 100) },
                      //     { name: '平凉市', value: Math.round(Math.random() * 100) },
                      //     { name: '临夏回族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '金昌市', value: Math.round(Math.random() * 100) },
                      //     { name: '嘉峪关市', value: Math.round(Math.random() * 100) },
                      //     { name: '普洱市', value: Math.round(Math.random() * 100) },
                      //     { name: '红河哈尼族彝族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '文山壮族苗族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '曲靖市', value: Math.round(Math.random() * 100) },
                      //     { name: '楚雄彝族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '大理白族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '临沧市', value: Math.round(Math.random() * 100) },
                      //     { name: '迪庆藏族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '昭通市', value: Math.round(Math.random() * 100) },
                      //     { name: '昆明市', value: Math.round(Math.random() * 100) },
                      //     { name: '丽江市', value: Math.round(Math.random() * 100) },
                      //     { name: '西双版纳傣族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '保山市', value: Math.round(Math.random() * 100) },
                      //     { name: '玉溪市', value: Math.round(Math.random() * 100) },
                      //     { name: '怒江傈僳族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '德宏傣族景颇族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '百色市', value: Math.round(Math.random() * 100) },
                      //     { name: '河池市', value: Math.round(Math.random() * 100) },
                      //     { name: '桂林市', value: Math.round(Math.random() * 100) },
                      //     { name: '南宁市', value: Math.round(Math.random() * 100) },
                      //     { name: '柳州市', value: Math.round(Math.random() * 100) },
                      //     { name: '崇左市', value: Math.round(Math.random() * 100) },
                      //     { name: '来宾市', value: Math.round(Math.random() * 100) },
                      //     { name: '玉林市', value: Math.round(Math.random() * 100) },
                      //     { name: '梧州市', value: Math.round(Math.random() * 100) },
                      //     { name: '贺州市', value: Math.round(Math.random() * 100) },
                      //     { name: '钦州市', value: Math.round(Math.random() * 100) },
                      //     { name: '贵港市', value: Math.round(Math.random() * 100) },
                      //     { name: '防城港市', value: Math.round(Math.random() * 100) },
                      //     { name: '北海市', value: Math.round(Math.random() * 100) },
                      //     { name: '怀化市', value: Math.round(Math.random() * 100) },
                      //     { name: '永州市', value: Math.round(Math.random() * 100) },
                      //     { name: '邵阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '郴州市', value: Math.round(Math.random() * 100) },
                      //     { name: '常德市', value: Math.round(Math.random() * 100) },
                      //     { name: '湘西土家族苗族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '衡阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '岳阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '益阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '长沙市', value: Math.round(Math.random() * 100) },
                      //     { name: '株洲市', value: Math.round(Math.random() * 100) },
                      //     { name: '张家界市', value: Math.round(Math.random() * 100) },
                      //     { name: '娄底市', value: Math.round(Math.random() * 100) },
                      //     { name: '湘潭市', value: Math.round(Math.random() * 100) },
                      //     { name: '榆林市', value: Math.round(Math.random() * 100) },
                      //     { name: '延安市', value: Math.round(Math.random() * 100) },
                      //     { name: '汉中市', value: Math.round(Math.random() * 100) },
                      //     { name: '安康市', value: Math.round(Math.random() * 100) },
                      //     { name: '商洛市', value: Math.round(Math.random() * 100) },
                      //     { name: '宝鸡市', value: Math.round(Math.random() * 100) },
                      //     { name: '渭南市', value: Math.round(Math.random() * 100) },
                      //     { name: '咸阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '西安市', value: Math.round(Math.random() * 100) },
                      //     { name: '铜川市', value: Math.round(Math.random() * 100) },
                      //     { name: '清远市', value: Math.round(Math.random() * 100) },
                      //     { name: '韶关市', value: Math.round(Math.random() * 100) },
                      //     { name: '湛江市', value: Math.round(Math.random() * 100) },
                      //     { name: '梅州市', value: Math.round(Math.random() * 100) },
                      //     { name: '河源市', value: Math.round(Math.random() * 100) },
                      //     { name: '肇庆市', value: Math.round(Math.random() * 100) },
                      //     { name: '惠州市', value: Math.round(Math.random() * 100) },
                      //     { name: '茂名市', value: Math.round(Math.random() * 100) },
                      //     { name: '江门市', value: Math.round(Math.random() * 100) },
                      //     { name: '阳江市', value: Math.round(Math.random() * 100) },
                      //     { name: '云浮市', value: Math.round(Math.random() * 100) },
                      //     { name: '广州市', value: Math.round(Math.random() * 100) },
                      //     { name: '汕尾市', value: Math.round(Math.random() * 100) },
                      //     { name: '揭阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '珠海市', value: Math.round(Math.random() * 100) },
                      //     { name: '佛山市', value: Math.round(Math.random() * 100) },
                      //     { name: '潮州市', value: Math.round(Math.random() * 100) },
                      //     { name: '汕头市', value: Math.round(Math.random() * 100) },
                      //     { name: '深圳市', value: Math.round(Math.random() * 100) },
                      //     { name: '东莞市', value: Math.round(Math.random() * 100) },
                      //     { name: '中山市', value: Math.round(Math.random() * 100) },
                      //     { name: '延边朝鲜族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '吉林市', value: Math.round(Math.random() * 100) },
                      //     { name: '白城市', value: Math.round(Math.random() * 100) },
                      //     { name: '松原市', value: Math.round(Math.random() * 100) },
                      //     { name: '长春市', value: Math.round(Math.random() * 100) },
                      //     { name: '白山市', value: Math.round(Math.random() * 100) },
                      //     { name: '通化市', value: Math.round(Math.random() * 100) },
                      //     { name: '四平市', value: Math.round(Math.random() * 100) },
                      //     { name: '辽源市', value: Math.round(Math.random() * 100) },
                      //     { name: '承德市', value: Math.round(Math.random() * 100) },
                      //     { name: '张家口市', value: Math.round(Math.random() * 100) },
                      //     { name: '保定市', value: Math.round(Math.random() * 100) },
                      //     { name: '唐山市', value: Math.round(Math.random() * 100) },
                      //     { name: '沧州市', value: Math.round(Math.random() * 100) },
                      //     { name: '石家庄市', value: Math.round(Math.random() * 100) },
                      //     { name: '邢台市', value: Math.round(Math.random() * 100) },
                      //     { name: '邯郸市', value: Math.round(Math.random() * 100) },
                      //     { name: '秦皇岛市', value: Math.round(Math.random() * 100) },
                      //     { name: '衡水市', value: Math.round(Math.random() * 100) },
                      //     { name: '廊坊市', value: Math.round(Math.random() * 100) },
                      //     { name: '恩施土家族苗族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '十堰市', value: Math.round(Math.random() * 100) },
                      //     { name: '宜昌市', value: Math.round(Math.random() * 100) },
                      //     { name: '襄樊市', value: Math.round(Math.random() * 100) },
                      //     { name: '黄冈市', value: Math.round(Math.random() * 100) },
                      //     { name: '荆州市', value: Math.round(Math.random() * 100) },
                      //     { name: '荆门市', value: Math.round(Math.random() * 100) },
                      //     { name: '咸宁市', value: Math.round(Math.random() * 100) },
                      //     { name: '随州市', value: Math.round(Math.random() * 100) },
                      //     { name: '孝感市', value: Math.round(Math.random() * 100) },
                      //     { name: '武汉市', value: Math.round(Math.random() * 100) },
                      //     { name: '黄石市', value: Math.round(Math.random() * 100) },
                      //     { name: '神农架林区', value: Math.round(Math.random() * 100) },
                      //     { name: '天门市', value: Math.round(Math.random() * 100) },
                      //     { name: '仙桃市', value: Math.round(Math.random() * 100) },
                      //     { name: '潜江市', value: Math.round(Math.random() * 100) },
                      //     { name: '鄂州市', value: Math.round(Math.random() * 100) },
                      //     { name: '遵义市', value: Math.round(Math.random() * 100) },
                      //     { name: '黔东南苗族侗族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '毕节地区', value: Math.round(Math.random() * 100) },
                      //     { name: '黔南布依族苗族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '铜仁地区', value: Math.round(Math.random() * 100) },
                      //     { name: '黔西南布依族苗族自治州', value: Math.round(Math.random() * 100) },
                      //     { name: '六盘水市', value: Math.round(Math.random() * 100) },
                      //     { name: '安顺市', value: Math.round(Math.random() * 100) },
                      //     { name: '贵阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '烟台市', value: Math.round(Math.random() * 100) },
                      //     { name: '临沂市', value: Math.round(Math.random() * 100) },
                      //     { name: '潍坊市', value: Math.round(Math.random() * 100) },
                      //     { name: '青岛市', value: Math.round(Math.random() * 100) },
                      //     { name: '菏泽市', value: Math.round(Math.random() * 100) },
                      //     { name: '济宁市', value: Math.round(Math.random() * 100) },
                      //     { name: '德州市', value: Math.round(Math.random() * 100) },
                      //     { name: '滨州市', value: Math.round(Math.random() * 100) },
                      //     { name: '聊城市', value: Math.round(Math.random() * 100) },
                      //     { name: '东营市', value: Math.round(Math.random() * 100) },
                      //     { name: '济南市', value: Math.round(Math.random() * 100) },
                      //     { name: '泰安市', value: Math.round(Math.random() * 100) },
                      //     { name: '威海市', value: Math.round(Math.random() * 100) },
                      //     { name: '日照市', value: Math.round(Math.random() * 100) },
                      //     { name: '淄博市', value: Math.round(Math.random() * 100) },
                      //     { name: '枣庄市', value: Math.round(Math.random() * 100) },
                      //     { name: '莱芜市', value: Math.round(Math.random() * 100) },
                      //     { name: '赣州市', value: Math.round(Math.random() * 100) },
                      //     { name: '吉安市', value: Math.round(Math.random() * 100) },
                      //     { name: '上饶市', value: Math.round(Math.random() * 100) },
                      //     { name: '九江市', value: Math.round(Math.random() * 100) },
                      //     { name: '抚州市', value: Math.round(Math.random() * 100) },
                      //     { name: '宜春市', value: Math.round(Math.random() * 100) },
                      //     { name: '南昌市', value: Math.round(Math.random() * 100) },
                      //     { name: '景德镇市', value: Math.round(Math.random() * 100) },
                      //     { name: '萍乡市', value: Math.round(Math.random() * 100) },
                      //     { name: '鹰潭市', value: Math.round(Math.random() * 100) },
                      //     { name: '新余市', value: Math.round(Math.random() * 100) },
                      //     { name: '南阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '信阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '洛阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '驻马店市', value: Math.round(Math.random() * 100) },
                      //     { name: '周口市', value: Math.round(Math.random() * 100) },
                      //     { name: '商丘市', value: Math.round(Math.random() * 100) },
                      //     { name: '三门峡市', value: Math.round(Math.random() * 100) },
                      //     { name: '新乡市', value: Math.round(Math.random() * 100) },
                      //     { name: '平顶山市', value: Math.round(Math.random() * 100) },
                      //     { name: '郑州市', value: Math.round(Math.random() * 100) },
                      //     { name: '安阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '开封市', value: Math.round(Math.random() * 100) },
                      //     { name: '焦作市', value: Math.round(Math.random() * 100) },
                      //     { name: '许昌市', value: Math.round(Math.random() * 100) },
                      //     { name: '濮阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '漯河市', value: Math.round(Math.random() * 100) },
                      //     { name: '鹤壁市', value: Math.round(Math.random() * 100) },
                      //     { name: '大连市', value: Math.round(Math.random() * 100) },
                      //     { name: '朝阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '丹东市', value: Math.round(Math.random() * 100) },
                      //     { name: '铁岭市', value: Math.round(Math.random() * 100) },
                      //     { name: '沈阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '抚顺市', value: Math.round(Math.random() * 100) },
                      //     { name: '葫芦岛市', value: Math.round(Math.random() * 100) },
                      //     { name: '阜新市', value: Math.round(Math.random() * 100) },
                      //     { name: '锦州市', value: Math.round(Math.random() * 100) },
                      //     { name: '鞍山市', value: Math.round(Math.random() * 100) },
                      //     { name: '本溪市', value: Math.round(Math.random() * 100) },
                      //     { name: '营口市', value: Math.round(Math.random() * 100) },
                      //     { name: '辽阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '盘锦市', value: Math.round(Math.random() * 100) },
                      //     { name: '忻州市', value: Math.round(Math.random() * 100) },
                      //     { name: '吕梁市', value: Math.round(Math.random() * 100) },
                      //     { name: '临汾市', value: Math.round(Math.random() * 100) },
                      //     { name: '晋中市', value: Math.round(Math.random() * 100) },
                      //     { name: '运城市', value: Math.round(Math.random() * 100) },
                      //     { name: '大同市', value: Math.round(Math.random() * 100) },
                      //     { name: '长治市', value: Math.round(Math.random() * 100) },
                      //     { name: '朔州市', value: Math.round(Math.random() * 100) },
                      //     { name: '晋城市', value: Math.round(Math.random() * 100) },
                      //     { name: '太原市', value: Math.round(Math.random() * 100) },
                      //     { name: '阳泉市', value: Math.round(Math.random() * 100) },
                      //     { name: '六安市', value: Math.round(Math.random() * 100) },
                      //     { name: '安庆市', value: Math.round(Math.random() * 100) },
                      //     { name: '滁州市', value: Math.round(Math.random() * 100) },
                      //     { name: '宣城市', value: Math.round(Math.random() * 100) },
                      //     { name: '阜阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '宿州市', value: Math.round(Math.random() * 100) },
                      //     { name: '黄山市', value: Math.round(Math.random() * 100) },
                      //     { name: '巢湖市', value: Math.round(Math.random() * 100) },
                      //     { name: '亳州市', value: Math.round(Math.random() * 100) },
                      //     { name: '池州市', value: Math.round(Math.random() * 100) },
                      //     { name: '合肥市', value: Math.round(Math.random() * 100) },
                      //     { name: '蚌埠市', value: Math.round(Math.random() * 100) },
                      //     { name: '芜湖市', value: Math.round(Math.random() * 100) },
                      //     { name: '淮北市', value: Math.round(Math.random() * 100) },
                      //     { name: '淮南市', value: Math.round(Math.random() * 100) },
                      //     { name: '马鞍山市', value: Math.round(Math.random() * 100) },
                      //     { name: '铜陵市', value: Math.round(Math.random() * 100) },
                      //     { name: '南平市', value: Math.round(Math.random() * 100) },
                      //     { name: '三明市', value: Math.round(Math.random() * 100) },
                      //     { name: '龙岩市', value: Math.round(Math.random() * 100) },
                      //     { name: '宁德市', value: Math.round(Math.random() * 100) },
                      //     { name: '福州市', value: Math.round(Math.random() * 100) },
                      //     { name: '漳州市', value: Math.round(Math.random() * 100) },
                      //     { name: '泉州市', value: Math.round(Math.random() * 100) },
                      //     { name: '莆田市', value: Math.round(Math.random() * 100) },
                      //     { name: '厦门市', value: Math.round(Math.random() * 100) },
                      //     { name: '丽水市', value: Math.round(Math.random() * 100) },
                      //     { name: '杭州市', value: Math.round(Math.random() * 100) },
                      //     { name: '温州市', value: Math.round(Math.random() * 100) },
                      //     { name: '宁波市', value: Math.round(Math.random() * 100) },
                      //     { name: '舟山市', value: Math.round(Math.random() * 100) },
                      //     { name: '台州市', value: Math.round(Math.random() * 100) },
                      //     { name: '金华市', value: Math.round(Math.random() * 100) },
                      //     { name: '衢州市', value: Math.round(Math.random() * 100) },
                      //     { name: '绍兴市', value: Math.round(Math.random() * 100) },
                      //     { name: '嘉兴市', value: Math.round(Math.random() * 100) },
                      //     { name: '湖州市', value: Math.round(Math.random() * 100) },
                      //     { name: '盐城市', value: Math.round(Math.random() * 100) },
                      //     { name: '徐州市', value: Math.round(Math.random() * 100) },
                      //     { name: '南通市', value: Math.round(Math.random() * 100) },
                      //     { name: '淮安市', value: Math.round(Math.random() * 100) },
                      //     { name: '苏州市', value: Math.round(Math.random() * 100) },
                      //     { name: '宿迁市', value: Math.round(Math.random() * 100) },
                      //     { name: '连云港市', value: Math.round(Math.random() * 100) },
                      //     { name: '扬州市', value: Math.round(Math.random() * 100) },
                      //     { name: '南京市', value: Math.round(Math.random() * 100) },
                      //     { name: '泰州市', value: Math.round(Math.random() * 100) },
                      //     { name: '无锡市', value: Math.round(Math.random() * 100) },
                      //     { name: '常州市', value: Math.round(Math.random() * 100) },
                      //     { name: '镇江市', value: Math.round(Math.random() * 100) },
                      //     { name: '吴忠市', value: Math.round(Math.random() * 100) },
                      //     { name: '中卫市', value: Math.round(Math.random() * 100) },
                      //     { name: '固原市', value: Math.round(Math.random() * 100) },
                      //     { name: '银川市', value: Math.round(Math.random() * 100) },
                      //     { name: '石嘴山市', value: Math.round(Math.random() * 100) },
                      //     { name: '儋州市', value: Math.round(Math.random() * 100) },
                      //     { name: '文昌市', value: Math.round(Math.random() * 100) },
                      //     { name: '乐东黎族自治县', value: Math.round(Math.random() * 100) },
                      //     { name: '三亚市', value: Math.round(Math.random() * 100) },
                      //     { name: '琼中黎族苗族自治县', value: Math.round(Math.random() * 100) },
                      //     { name: '东方市', value: Math.round(Math.random() * 100) },
                      //     { name: '海口市', value: Math.round(Math.random() * 100) },
                      //     { name: '万宁市', value: Math.round(Math.random() * 100) },
                      //     { name: '澄迈县', value: Math.round(Math.random() * 100) },
                      //     { name: '白沙黎族自治县', value: Math.round(Math.random() * 100) },
                      //     { name: '琼海市', value: Math.round(Math.random() * 100) },
                      //     { name: '昌江黎族自治县', value: Math.round(Math.random() * 100) },
                      //     { name: '临高县', value: Math.round(Math.random() * 100) },
                      //     { name: '陵水黎族自治县', value: Math.round(Math.random() * 100) },
                      //     { name: '屯昌县', value: Math.round(Math.random() * 100) },
                      //     { name: '定安县', value: Math.round(Math.random() * 100) },
                      //     { name: '保亭黎族苗族自治县', value: Math.round(Math.random() * 100) },
                      //     { name: '五指山市', value: Math.round(Math.random() * 100) },
                      //     { name: '长沙县', value: Math.round(Math.random() * 100) },
                      //     { name: '宁乡县', value: Math.round(Math.random() * 100) },
                      //     { name: '浏阳市', value: Math.round(Math.random() * 100) },
                      //     { name: '长沙县', value: Math.round(Math.random() * 100) },
                      //     { name: '开福区', value: Math.round(Math.random() * 100) },
                      //     { name: '芙蓉区', value: Math.round(Math.random() * 100) },
                      //     { name: '雨花区', value: Math.round(Math.random() * 100) },
                      //     { name: '望城区', value: Math.round(Math.random() * 100) },
                      //     { name: '天心区', value: Math.round(Math.random() * 100) },
                      //     { name: '岳麓区', value: Math.round(Math.random() * 100) },
                      // ]
                  }]
              };

              // 为echarts对象加载数据
              myChart.setOption(option);
          }
      );
  })
// 混合图
    $(function () {
        var a='im a!';
        var mc = 5;
        $('#container2').highcharts({
            chart: {
                backgroundColor:'rgba(0,0,0,0)'
            },
            plotOptions: {
                series: {
                    groupPadding:0.3,
                    stickyTracking: false,
                }
            },
            credits:{
                enabled: false // 禁用版权信息
            },
            title: {
                text: '各应用网络QoE分析'
            },
            exporting: { enabled: false },
            xAxis: {
                categories:(function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/queryrange",
                        data : {serviceType:'3'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {

                            mc = result.mouths.length;
                            for(var i=0;i<result.mouths.length;i++){
                                arr[i] = result.mouths[i].mouthDay;
                            }
                        }
                    })

                    return arr;

                })(),//['2017-08','2017-09','2017-10','2017-11','2017-12'],
                labels:{y:18},
            },
            yAxis: [{
                max:100,
                min:0,
                title: {
                    text: "感知评分(QoE)"
                }
            }],
            labels: {
                items: [{
                    style: {
                        left: '50px',
                        top: '18px',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                    }
                }]
            },
            series: [{
                type: 'column',
                name: '网页浏览',
                //data:[40,30,70,80,90],
                data: (function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/qoeview",
                        data : {serviceType:'3'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {
                            for(var i=0;i<result.scoreCollects.length;i++){

                                arr[i] = result.scoreCollects[i].score;

                            }
                        }
                    })

                    return arr;

                })()
            }, {
                type: 'column',
                name: '在线视频',
                //data:[40,30,70,80,90],
                data: (function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/qoeview",
                        data : {serviceType:'5'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {
                            if (result) {
                                for(var i=0;i<result.scoreCollects.length;i++){
                                    arr[i] = result.scoreCollects[i].score;

                                }
                            }
                        }
                    })
                    return arr;
                })()
            }, {
                type: 'column',
                name: '网络连通性',
                //data:[41,51,61,81,91]
                data: (function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/qoeview",
                        data : {serviceType:'1'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {
                            if (result) {
                                for(var i=0;i<result.scoreCollects.length;i++){
                                    arr[i] = result.scoreCollects[i].score;

                                }
                            }
                        }
                    })
                    return arr;
                })()
            }, {
                type: 'column',
                name: '文件下载',
                //data:[40,60,30,80,90]
                data: (function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/qoeview",
                        data : {serviceType:'4'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {
                            if (result) {
                                for(var i=0;i<result.scoreCollects.length;i++){
                                    arr[i] = result.scoreCollects[i].score;

                                }
                            }
                        }
                    })
                    return arr;
                })()
            }, {
                type: 'column',
                name: '网络游戏',
                //data:[40,60,30,80,90]
                data: (function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/qoeview",
                        data : {serviceType:'6'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {
                                for(var i=0;i<result.scoreCollects.length;i++){
                                    arr[i] = result.scoreCollects[i].score;

                                }
                        }
                    })
                    return arr;
                })()
            } , {
                type: 'column',
                name: '网络层质量',
                //data:[40,60,30,80,90]
                data: (function(){
                    var arr=[];
                    $.ajax({
                        type: "post",
                        async: false, //同步执行
                        url: "../../cem/collect/qoeview",
                        data : {serviceType:'2'},
                        dataType: "json", //返回数据形式为json
                        success: function (result) {
                            if (result) {
                                for(var i=0;i<result.scoreCollects.length;i++){
                                    arr[i] = result.scoreCollects[i].score;

                                }
                            }
                        }
                    })
                    return arr;
                })()
            }]
        });
    });
 //雷达图
    $(function () {
        require.config({
            paths: {
                echarts: '/statics/js/echarts-2.2.7/build/dist',
                'echarts/chart/radar':'/statics/js/echarts-2.2.7/build/dist/chart/radar'
            }
        });

        // 使用
        require(
            [
                'echarts',
                'echarts/chart/radar'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('container3'));
                var option = {
                    title: {
                        text: '各应用感知',
                        subtext: '时间范围:最近30天'
                    },
                    color: ['#5FD1AE', '#FF7256'],
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'right',
                        y: 'bottom',
                        data: ['闲时', '忙时']
                    },

                    polar: [
                        {
                            indicator: [
                                {text: '网络连通性', min: 0, max: 100},
                                {text: '网络层质量', min: 0, max: 100},
                                {text: '网页浏览', min: 0, max: 100},
                                {text: '文件下载', min: 0, max: 100},
                                {text: '在线视频', min: 0, max: 100},
                                {text: '网络游戏', min: 0, max: 100},

                            ]
                        }
                    ],
                    calculable: true,
                    series: [
                        {
                            name: '各应用感知',
                            type: 'radar',
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data: [
                                {
                                    value: (function () {
                                        var arr = [];
                                        $.ajax({
                                            type: "post",
                                            async: false, //同步执行
                                            url: "../../cem/collect/dayscoresview",
                                            data: {interval: '1'},
                                            dataType: "json", //返回数据形式为json
                                            success: function (result) {
                                                if (result) {
                                                    for(var i=0;i<result.scoreCollects.length;i++){
                                                        arr[i] = result.scoreCollects[i].score;
                                                    }
                                                }
                                            }
                                        })
                                        return arr;
                                    })(),
                                    name: '闲时'
                                },
                                {
                                    value: (function () {
                                        var arr = [];
                                        $.ajax({
                                            type: "post",
                                            async: false, //同步执行
                                            url: "../../cem/collect/dayscoresview",
                                            data: {interval: '2'},
                                            dataType: "json", //返回数据形式为json
                                            success: function (result) {
                                                if (result) {
                                                    for(var i=0;i<result.scoreCollects.length;i++){
                                                        arr[i] = result.scoreCollects[i].score;
                                                    }
                                                }
                                            }
                                        })
                                        return arr;
                                    })(),
                                    name: '忙时'
                                }

                                // {
                                //     value : [97, 42, 88, 94, 90, 86],
                                //     name : '闲时'
                                // },
                                // {
                                //     value : [97, 88, 74, 95, 77, 92],
                                //     name : '忙时'
                                // }
                            ]


                        }
                    ]
                };
                myChart.setOption(option);
            }
        );
    })
//top10排名 柱状图
    $(function () {
        //平均qoe值
        var day = new Date();
        var Year = day.getFullYear();
        var Month = day.getMonth()+1;
        var CurrentDate = "";
        CurrentDate += Year;
        if (Month >= 10) {
            CurrentDate += '-' + Month;
        }
        else {
            CurrentDate += '-' + "0" + Month;
        }
        indexColumn();
        function indexColumn() {

            $('#container4').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'rgba(0,0,0,0)'
                },
                title: {
                    text: 'TOP10网络连通性排行'
                },

                xAxis: {
                    labels: {
                        rotation: 0//调节倾斜角度偏移
                    },
                    categories:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '1'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr.push(result.scoreCollects[i].targetName);
                                    }
                            }
                        })
                        return arr;
                    })(),
                    crosshair: true
                },
                yAxis: {
                    max: 100,
                    min: 0,
                    title: {
                        text: ' '
                    }
                },
                exporting: { enabled: false },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    stickyTracking: false,
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'QoE',
                    data: (function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '1'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for (var i = 0; i < result.scoreCollects.length; i++) {
                                        arr[i] = result.scoreCollects[i].score;
                                    }
                                }
                            }
                        })
                        return arr;
                    })()
                    //data:[65,88,79,84,96,75,86,66,99,76]
                }]

            });
        }
        //发送请求函数
        function netColumn() {
            $('#container4').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'rgba(0,0,0,0)'
                },
                title: {
                    text: 'TOP10网络层质量排行'
                },
                exporting: { enabled: false },
                xAxis: {
                    labels: {
                        rotation: 0//调节倾斜角度偏移
                    },
                    categories:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '2'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr.push(result.scoreCollects[i].targetName);
                                    }
                                }
                            }
                        })
                        return arr;
                    })(),
                    crosshair: true
                },
                yAxis: {
                    max: 100,
                    min: 0,
                    title: {
                        text: ' '
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    stickyTracking: false,
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'QoE',
                    data: (function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '2'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr[i] = result.scoreCollects[i].score;
                                    }
                                }
                            }
                        })
                        return arr;
                    })()
                    //data:[65,88,79,84,96,75,86,66,99,76]
                }]

            });
        }

        function httpColumn() {
            $('#container4').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'rgba(0,0,0,0)'
                },
                title: {
                    text: 'TOP10网页浏览排行'
                },
                exporting: { enabled: false },
                xAxis: {
                    labels: {
                        rotation: 0//调节倾斜角度偏移
                    },
                    categories:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '3'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr.push(result.scoreCollects[i].targetName);
                                    }
                                }
                            }
                        })
                        return arr;
                    })(),
                    crosshair: true
                },
                yAxis: {
                    max: 100,
                    min: 0,
                    title: {
                        text: ' '
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    stickyTracking: false,
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'QoE',
                    data:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '3'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr[i] = result.scoreCollects[i].score;
                                    }
                                }
                            }
                        })
                        return arr;
                    })()
                }]

            });
        }
        function down_line(){
            $('#container4').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'rgba(0,0,0,0)'
                },
                title: {
                    text: 'TOP10文件下载排行'
                },
                exporting: { enabled: false },
                xAxis: {
                    labels: {
                        rotation: 0//调节倾斜角度偏移
                    },
                    categories:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '4'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {

                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr.push(result.scoreCollects[i].targetName);
                                    }
                                }
                            }
                        })
                        return arr;
                    })(),
                    crosshair: true
                },
                yAxis: {
                    max: 100,
                    min: 0,
                    title: {
                        text: ' '
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    stickyTracking: false,
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'QoE',

                    data:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '4'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr[i] = result.scoreCollects[i].score;
                                    }
                                }
                            }
                        })
                        return arr;
                    })()
                }]

            });
        }




        function PING_Pie() {
            $('#container4').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'rgba(0,0,0,0)'
                },
                title: {
                    text: 'TOP10在线视频排行'
                },
                exporting: { enabled: false },
                xAxis: {
                    labels: {
                        rotation: 0//调节倾斜角度偏移
                    },
                    categories:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '5'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr.push(result.scoreCollects[i].targetName);
                                    }
                                }
                            }
                        })
                        return arr;
                    })(),
                    crosshair: true
                },
                yAxis: {
                    max: 100,
                    min: 0,
                    title: {
                        text: ' '
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    stickyTracking: false,
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'QoE',
                    data: (function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '5'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr[i] = result.scoreCollects[i].score;
                                    }
                                }
                            }
                        })
                        return arr;
                    })()
                    //data:[65,88,79,84,96,75,86,66,99,76]
                }]

            });
        }
        function Game_line() {
            $('#container4').highcharts({
                chart: {
                    type: 'column',
                    backgroundColor: 'rgba(0,0,0,0)'
                },
                title: {
                    text: 'TOP10网络游戏排行'
                },
                exporting: { enabled: false },
                xAxis: {
                    labels: {
                        rotation: 0//调节倾斜角度偏移
                    },
                    categories:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '6'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {

                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr.push(result.scoreCollects[i].targetName);
                                    }
                                }
                            }
                        })
                        return arr;
                    })(),
                    crosshair: true
                },
                yAxis: {
                    max: 100,
                    min: 0,
                    title: {
                        text: ' '
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    stickyTracking: false,
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'QoE',

                    data:(function () {
                        var arr = [];
                        $.ajax({
                            type: "post",
                            async: false, //同步执行
                            url: "../../cem/collect/targetview",
                            data: {serviceType: '6'},
                            dataType: "json", //返回数据形式为json
                            success: function (result) {
                                if (result) {
                                    for(var i=0;i<result.scoreCollects.length;i++){
                                        arr[i] = result.scoreCollects[i].score;
                                    }
                                }
                            }
                        })
                        return arr;
                    })()
                }]

            });
        }

        $('#TopId').change(function () {
                var Top_name = $('#TopId').find('option:selected').attr('lev');
                if(Top_name=="ping_top"){
                    indexColumn();
                }
                if(Top_name=='sla_top'){
                    netColumn();
                }if(Top_name=='http_top'){
                    httpColumn()
                }if(Top_name=='youku_top'){
                    PING_Pie();
                }if(Top_name=='xunlei_top'){
                    down_line();
                }
                if(Top_name=='game_top'){
                    Game_line();
                }

        });
    });
//   层级 折线图
    $(function () {
        //平均qoe值
        var day = new Date();
        var Year = day.getFullYear();
        var Month = day.getMonth()+1;
        var CurrentDate = "";
        CurrentDate += Year;
        if (Month >= 10) {
            CurrentDate += '-' + Month;
        }
        else {
            CurrentDate += '-' + "0" + Month;
        }

        function Index_qoe() {
           $('#container5').highcharts({
               chart: {
                   type: 'spline',
                   backgroundColor: 'rgba(0,0,0,0)'
               },
               title: {
                   text: '网络分层评测'
               },
               credits:{
                   enabled: false // 禁用版权信息
               },
               xAxis: {
                   type: 'datetime',
                   dateTimeLabelFormats: {
                       day: '%Y-%m-%d',
                       week: '%Y-%m-%d',
                       month: '%Y-%m-%d',
                       year: '%Y-%m-%d'
                   },
                   title: {
                       text: 'Date'
                   },
                   categories:(function(){
                       var arr=[];
                       $.ajax({
                           type: "post",
                           async: false, //同步执行
                           url: "../../cem/index/querydayrange",
                           data : {serviceType:'0'},
                           dataType: "json", //返回数据形式为json
                           success: function (result) {

                               for(var i=0;i<result.days.length;i++){
                                   arr[i] = result.days[i].day;
                               }
                           }
                       })

                       return arr;

                   })()
                   //crosshair: true,
               },
               yAxis: {
                   max: 100,
                   min: 0,
                   title: {
                       text: '结果(分)'
                   }
               },
               tooltip: {
                   /*headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                   pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                   '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
                   footerFormat: '</table>',
                   shared: true,
                   shadow: false,
                   useHTML: true,*/
                   crosshairs: true,
                   headerFormat: '<b>{series.name}</b><br>',
                   pointFormat: '分数:{point.y:.2f}分'
               },
               plotOptions: {
                   spline: {
                       marker: {
                           enabled: false
                       }
                   }
               },
               series:(function () {
                   var arr = [];
                   $.ajax({
                       type: "post",
                       async: false, //同步执行
                       url: "../../cem/index/layerqoeview",
                       data: {accessLayer: '2000',serviceType:'5'},
                       dataType: "json", //返回数据形式为json
                       success: function (result) {
                           arr = result.scoreCollects;
                       }
                   })
                   return arr;
               })()

           });
       }

       // layerShow();
        //Index_qoe();


        // function second() {
        //
        //     $('#container5').highcharts({
        //
        //         chart: {
        //             type: 'spline',
        //             backgroundColor: 'rgba(0,0,0,0)'
        //         },
        //         title: {
        //             text: '网络分层评测'
        //         },
        //
        //         xAxis: {
        //             type: 'datetime',
        //             dateTimeLabelFormats: {
        //                 day: '%Y-%m-%d',
        //                 week: '%Y-%m-%d',
        //                 month: '%Y-%m-%d',
        //                 year: '%Y-%m-%d'
        //             },
        //             title: {
        //                 text: 'Date'
        //             },
        //             categories: (function(){
        //                 var arr=[];
        //                 $.ajax({
        //                     type: "post",
        //                     async: false, //同步执行
        //                     url: "../../cem/index/querydayrange",
        //                     data : {serviceType:'1'},
        //                     dataType: "json", //返回数据形式为json
        //                     success: function (result) {
        //
        //                         for(var i=0;i<result.days.length;i++){
        //                             arr[i] = result.days[i].day;
        //                         }
        //                     }
        //                 })
        //
        //                 return arr;
        //
        //             })()
        //             //crosshair: true
        //         },
        //         yAxis: {
        //             max: 100,
        //             min: 0,
        //             title: {
        //                 text: ' '
        //             }
        //         },
        //         tooltip: {
        //             /*headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        //            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        //            '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
        //            footerFormat: '</table>',
        //            shared: true,
        //            shadow: false,
        //            useHTML: true,*/
        //             crosshairs: true,
        //             headerFormat: '<b>{series.name}</b><br>',
        //             pointFormat: '分数:{point.y:.2f}分'
        //         },
        //         plotOptions: {
        //             spline: {
        //                 marker: {
        //                     enabled: false
        //                 }
        //
        //             }
        //         },
        //         series: (function () {
        //             var arr = [];
        //             $.ajax({
        //                 type: "post",
        //                 async: false, //同步执行
        //                 url: "../../cem/index/layerqoeview",
        //                 data: {accessLayer: '2000',serviceType:'1'},
        //                 dataType: "json", //返回数据形式为json
        //                 success: function (result) {
        //                     arr = result.scoreCollects;
        //                 }
        //             })
        //             return arr;
        //         })()
        //     });
        //     document.getElementById("loading").style.display = "none";
        // }
        //
        // function showHttpLine() {
        //
        //     $('#container5').highcharts({
        //
        //         chart: {
        //             type: 'spline',
        //             backgroundColor: 'rgba(0,0,0,0)'
        //         },
        //         title: {
        //             text: '网络分层评测'
        //         },
        //         credits:{
        //             enabled: false // 禁用版权信息
        //         },
        //         xAxis: {
        //             type: 'datetime',
        //             dateTimeLabelFormats: {
        //                 day: '%Y-%m-%d',
        //                 week: '%Y-%m-%d',
        //                 month: '%Y-%m-%d',
        //                 year: '%Y-%m-%d'
        //             },
        //             title: {
        //                 text: 'Date'
        //             },
        //             categories: (function(){
        //                 var arr=[];
        //                 $.ajax({
        //                     type: "post",
        //                     async: false, //同步执行
        //                     url: "../../cem/index/querydayrange",
        //                     data : {serviceType:'2'},
        //                     dataType: "json", //返回数据形式为json
        //                     success: function (result) {
        //
        //                         for(var i=0;i<result.days.length;i++){
        //                             arr[i] = result.days[i].day;
        //                         }
        //                     }
        //                 })
        //
        //                 return arr;
        //
        //             })()
        //             //crosshair: true
        //         },
        //         yAxis: {
        //             max: 100,
        //             min: 0,
        //             title: {
        //                 text: '结果(分) '
        //             }
        //         },
        //         tooltip: {
        //             /*headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        //             pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        //             '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
        //             footerFormat: '</table>',
        //             shared: true,
        //             useHTML: true*/
        //             crosshairs: true,
        //             headerFormat: '<b>{series.name}</b><br>',
        //             pointFormat: '分数:{point.y:.2f}分'
        //         },
        //         plotOptions: {
        //             spline: {
        //                 marker: {
        //                     enabled: false
        //                 }
        //             }
        //
        //         },
        //         series: (function () {
        //             var arr = [];
        //             $.ajax({
        //                 type: "post",
        //                 async: false, //同步执行
        //                 url: "../../cem/index/layerqoeview",
        //                 data: {accessLayer: '2000',serviceType:'2'},
        //                 dataType: "json", //返回数据形式为json
        //                 success: function (result) {
        //                     arr = result.scoreCollects;
        //                 }
        //             })
        //             return arr;
        //         })()
        //     });
        //     document.getElementById("loading").style.display = "none";
        // }
        //
        // function showVedioLine() {
        //
        //     $('#container5').highcharts({
        //
        //         chart: {
        //             type: 'spline',
        //             backgroundColor: 'rgba(0,0,0,0)'
        //         },
        //         title: {
        //             text: '网络分层评测'
        //         },
        //         credits:{
        //             enabled: false // 禁用版权信息
        //         },
        //         xAxis: {
        //             type: 'datetime',
        //             dateTimeLabelFormats: {
        //                 day: '%Y-%m-%d',
        //                 week: '%Y-%m-%d',
        //                 month: '%Y-%m-%d',
        //                 year: '%Y-%m-%d'
        //             },
        //             title: {
        //                 text: 'Date'
        //             },
        //             categories: (function(){
        //                 var arr=[];
        //                 $.ajax({
        //                     type: "post",
        //                     async: false, //同步执行
        //                     url: "../../cem/index/querydayrange",
        //                     data : {serviceType:'4'},
        //                     dataType: "json", //返回数据形式为json
        //                     success: function (result) {
        //
        //                         for(var i=0;i<result.days.length;i++){
        //                             arr[i] = result.days[i].day;
        //                         }
        //                     }
        //                 })
        //
        //                 return arr;
        //
        //             })(),
        //             crosshair: true
        //         },
        //         yAxis: {
        //             max: 100,
        //             min: 0,
        //             title: {
        //                 text: ' '
        //             }
        //         },
        //         tooltip: {
        //             /*headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        //             pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        //             '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
        //             footerFormat: '</table>',
        //             shared: true,
        //             useHTML: true*/
        //             crosshairs: true,
        //             headerFormat: '<b>{series.name}</b><br>',
        //             pointFormat: '分数:{point.y:.2f}分'
        //         },
        //         plotOptions: {
        //             spline: {
        //                 marker: {
        //                     enabled: false
        //                 }
        //             }
        //         },
        //         series: (function () {
        //             var arr = [];
        //             $.ajax({
        //                 type: "post",
        //                 async: false, //同步执行
        //                 url: "../../cem/index/layerqoeview",
        //                 data: {accessLayer: '2000',serviceType:'4'},
        //                 dataType: "json", //返回数据形式为json
        //                 success: function (result) {
        //                     arr = result.scoreCollects;
        //                 }
        //             })
        //             return arr;
        //         })()
        //     });
        //     document.getElementById("loading").style.display = "none";
        // }
        //
        // function showDownLine() {
        //
        //     $('#container5').highcharts({
        //
        //         chart: {
        //             type: 'spline',
        //             backgroundColor: 'rgba(0,0,0,0)'
        //         },
        //         title: {
        //             text: '网络分层评测'
        //         },
        //         credits:{
        //             enabled: false // 禁用版权信息
        //         },
        //         xAxis: {
        //             type: 'datetime',
        //             dateTimeLabelFormats: {
        //                 day: '%Y-%m-%d',
        //                 week: '%Y-%m-%d',
        //                 month: '%Y-%m-%d',
        //                 year: '%Y-%m-%d'
        //             },
        //             title: {
        //                 text: 'Date'
        //             },
        //             categories: (function(){
        //                 var arr=[];
        //                 $.ajax({
        //                     type: "post",
        //                     async: false, //同步执行
        //                     url: "../../cem/index/querydayrange",
        //                     data : {serviceType:'3'},
        //                     dataType: "json", //返回数据形式为json
        //                     success: function (result) {
        //
        //                         for(var i=0;i<result.days.length;i++){
        //                             arr[i] = result.days[i].day;
        //                         }
        //                     }
        //                 })
        //
        //                 return arr;
        //
        //             })(),
        //             crosshair: true
        //         },
        //         yAxis: {
        //             max: 100,
        //             min: 0,
        //             title: {
        //                 text: ' '
        //             }
        //         },
        //         tooltip: {
        //             /*headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        //             pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        //             '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
        //             footerFormat: '</table>',
        //             shared: true,
        //             useHTML: true*/
        //             crosshairs: true,
        //             headerFormat: '<b>{series.name}</b><br>',
        //             pointFormat: '分数:{point.y:.2f}分'
        //         },
        //         plotOptions: {
        //             spline: {
        //                 marker: {
        //                     enabled: false
        //                 }
        //             }
        //         },
        //         series: (function () {
        //             var arr = [];
        //             $.ajax({
        //                 type: "post",
        //                 async: false, //同步执行
        //                 url: "../../cem/index/layerqoeview",
        //                 data: {accessLayer: '2000',serviceType:'3'},
        //                 dataType: "json", //返回数据形式为json
        //                 success: function (result) {
        //                     arr = result.scoreCollects;
        //                 }
        //             })
        //             return arr;
        //         })()
        //     });
        //     document.getElementById("loading").style.display = "none";
        // }

        //
        // function showGameLine() {
        //
        //     $('#container5').highcharts({
        //
        //         chart: {
        //             type: 'spline',
        //             backgroundColor: 'rgba(0,0,0,0)'
        //         },
        //         title: {
        //             text: '网络游戏评测'
        //         },
        //
        //         xAxis: {
        //             type: 'datetime',
        //             dateTimeLabelFormats: {
        //                 day: '%Y-%m-%d',
        //                 week: '%Y-%m-%d',
        //                 month: '%Y-%m-%d',
        //                 year: '%Y-%m-%d'
        //             },
        //             title: {
        //                 text: 'Date'
        //             },
        //             categories: (function(){
        //                 var arr=[];
        //                 $.ajax({
        //                     type: "post",
        //                     async: false, //同步执行
        //                     url: "../../cem/index/querydayrange",
        //                     data : {serviceType:'5'},
        //                     dataType: "json", //返回数据形式为json
        //                     success: function (result) {
        //
        //                         for(var i=0;i<result.days.length;i++){
        //                             arr[i] = result.days[i].day;
        //                         }
        //                     }
        //                 })
        //
        //                 return arr;
        //
        //             })(),
        //             crosshair: true
        //         },
        //         yAxis: {
        //             max: 100,
        //             min: 0,
        //             title: {
        //                 text: ' '
        //             }
        //         },
        //         tooltip: {
        //             /*headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        //             pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        //             '<td style="padding:0"><b>{point.y:.1f} Q</b></td></tr>',
        //             footerFormat: '</table>',
        //             shared: true,
        //             useHTML: true*/
        //             crosshairs: true,
        //             headerFormat: '<b>{series.name}</b><br>',
        //             pointFormat: '分数:{point.y:.2f}分'
        //         },
        //         plotOptions: {
        //             spline: {
        //                 marker: {
        //                     enabled: false
        //                 }
        //             }
        //         },
        //         series: (function () {
        //             var arr = [];
        //             $.ajax({
        //                 type: "post",
        //                 async: false, //同步执行
        //                 url: "../../cem/index/layerqoeview",
        //                 data: {accessLayer: '2000',serviceType:'5'},
        //                 dataType: "json", //返回数据形式为json
        //                 success: function (result) {
        //                     arr = result.scoreCollects;
        //                 }
        //             })
        //             return arr;
        //         })()
        //     });
        //     document.getElementById("loading").style.display = "none";
        // }


        $('#lev').change(function () {
            var Top_name = $('#lev').find('option:selected').attr('lev');
            if(Top_name=="ping_line"){
                connection_layer_show(1);
            }

            if(Top_name=="sla_line"){
                connection_layer_show(2);
            }
            if(Top_name=="http_line"){
                connection_layer_show(3);
            }
            if(Top_name=="down_line"){
                connection_layer_show(4);
            }
            if(Top_name=="video_line"){
                connection_layer_show(5);
            }
            if(Top_name=="game_line"){
                connection_layer_show(6);
            }
        });
    });



    var options = {
        chart: {
            type: 'spline',
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
                day: '%Y-%m-%d',
                week: '%Y-%m-%d',
                month: '%Y-%m-%d',
                year: '%Y-%m-%d'
            },
        },
        yAxis: {
            title: {
                text: '结果(分)'
            },
            min: 0,
            max: 100
        },

        tooltip: {
            crosshairs: true,
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '日期:{point.x:%Y-%m-%d %H:00:00} 分数:{point.y:.2f}分',
        },
        plotOptions: {
            spline: {
                marker: {
                    enabled: false
                }
            },
            series: {
                stickyTracking: false,
            }
        },
        series: []
    };

    function connection_layer_show(stype) {
        new Vue({
            el: '#container5',

            mounted:function () {
                $.ajax({
                    type: "POST",
                    url: "../../cem/collect/layerqoeview",
                    cache: false,  //禁用缓存
                    data: {serviceType:stype},  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        score_list=result.scoreCollects;
                        for (let i = 0; i < options.series.length; i++) {
                            options.series[i].data = [];
                        }
                        for (let i = 0; i < options.series.length; i++) {
                            for (let j = 0; j < score_list.length; j++) {
                                if (parseInt(layers.get(options.series[i].name)) == parseInt(score_list[j].accessLayer)) {
                                    let date_token = score_list[j].scoreDate.split("-");
                                    let year = parseInt(date_token[0]);
                                    let month = parseInt(date_token[1]) - 1;
                                    let day = parseInt(date_token[2]);
                                    //let hour = parseInt(score_list[j].recordTime);
                                    if (isNaN(year) || isNaN(month) || isNaN(day)) {
                                        continue;
                                    }
                                    options.series[i].data[j] = [Date.UTC(year, month, day), score_list[j].score];
                                    options.series[i].data.sort(compare("0"));
                                }

                            }

                        }
                        var chart = new Highcharts.Chart('container5', options);

                    }
                })

                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/layer/searchlist",
                    cache: false,  //禁用缓存
                    dataType: "json",
                    success: function (result) {
                        for (let i = 0; i < result.page.list.length; i++) {
                            // layers.set(result.page.list[i].layerTag, result.page.list[i].layerName);
                            layers.set(result.page.list[i].layerName, result.page.list[i].layerTag);
                            layerNames.set(result.page.list[i].layerTag, result.page.list[i].layerName);
                            let newlayer = {};
                            newlayer.name = result.page.list[i].layerName;
                            newlayer.data = [];
                            options.series[i] = newlayer;
                        }
                    }
                });
            }
        });
    }

    connection_layer_show(1);

    function compare(property) {
        return function (obj1, obj2) {
            let value1 = obj1[property];
            let value2 = obj2[property];
            return value1 - value2;     // 升序
        }
    }