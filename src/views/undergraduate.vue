<template>
  <div id="wrapper" v-cloak>
    <div class="main">
      <div class="main-content">
        <div class="container-fluid">
          <div class="row">

            <div class="col-md-18">

              <div class="panel">
                <div class="top2">
                  <a class="btn1" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
                     aria-controls="collapseExample">
                    高级检索>>
                  </a>
                </div>


                <button type="button" class="button" @click="AIanalysis(1)">
                  <span>Submit→</span>
                </button>
                <div class="top1">
                  <input type="text" class="search" v-model="SearchForm.name" placeholder="想搜一下">
                  <div class="pic"><a href="javascript:void(0);" @click="loadPage(1)"><img
                    src="../assets/img/search.png" alt=""></a></div>
                </div>


                <h3 class="b-heading" style="padding-left: 20px; font-weight: bold;">E-Judge</h3>
                <!-- <Loading /> -->
                <div class="collapse" id="collapseExample">
                  <div class="panel panel-primary ">
                    <div class="panel-heading ">
                      <h3 class="panel-title">高级检索</h3>
                    </div>
                    <div class="panel-body btn1">

                      <div style="display:inline-block;">
                        <!-- 年级 -->
                        <div style="float:left;padding:6px;">
                          <span>年级：</span>
                        </div>
                        <div style="float:left;">
                          <select id="enroll" class="form-control" style="width:200px;" v-model="SearchForm.enroll"
                                  @change="loadenroll(1)">
                            <option value="0">-请选择-</option>
                            <option v-for="enroll in enrolls" :key="enroll" :value="enroll">{{ enroll }}</option>
                          </select>
                        </div>
                        <!-- 学院 -->
                        <div style="float:left;padding:6px;">
                          <span>学院：</span>
                        </div>
                        <div style="float:left;">
                          <select id="institute" class="form-control" style="width:200px;" v-model="SearchForm.instcode"
                                  @change="loadmajor(1)" @οnfοcus="selectFocus(this)">
                            <option value="0">-请选择-</option>
                            <option v-for="institute in institutes" :key="institute.xymc" :value="institute.xydm">
                              {{ institute.xymc }}
                            </option>
                          </select>
                        </div>
                        <!-- 专业 -->
                        <div style="float:left;padding:6px;">
                          <span>专业：</span>
                        </div>
                        <div style="float:left;">
                          <select id="major" class="form-control " style="width:200px;" v-model="SearchForm.major"
                                  @change="loadclass(1)" @focus="showDropdown" @blur="hideDropdown">
                            <option value="0">-请选择-</option>
                            <option v-for="major in majors" :key="major.zymc" :value="major.zydm">{{
                                major.zymc
                              }}
                            </option>
                          </select>
                        </div>
                        <!-- 班级 -->
                        <div style="float:left;padding:6px;">
                          <span>班级：</span>
                        </div>
                        <div style="float:left;">
                          <select id="class" class="form-control" style="width:200px;" v-model="SearchForm.class">
                            <option value="0">-请选择-</option>
                            <option v-for="cless in classes" :key="cless.bjdm" :value="cless.bjmc">{{
                                cless.bjmc
                              }}
                            </option>
                          </select>
                        </div>
                        <!-- 姓名 -->
                        <div style="float:left;padding:6px;">
                          <span>姓名：</span>
                        </div>
                        <div style="float:left;">
                          <input id="name" v-model="SearchForm.name" class="form-control" style="width:200px;"
                                 placeholder="请输入姓名"/>
                        </div>

                        <div style="float:left;margin-left:20px;">
                          <div class="pic"><a href="javascript:void(0);" @click="loadPage(1)"><img
                            src="../assets/img/search.png" alt=""></a></div>
                        </div>
                      </div>
                    </div>
                  </div>

                </div>
                <div class="panel-body">
                  <table class="table table-hover table-success">
                    <thead class="thead-default">
                    <tr>
                      <th>学号</th>
                      <th>年级</th>
                      <th>姓名</th>
                      <th>性别</th>
                      <th>籍贯</th>
                      <th>民族</th>
                      <th>班级名称</th>
                      <th>专业名称</th>
                      <th>学院名称</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="clickable-row" v-for="userinfo in Userinfos" :key="userinfo.xh"
                        @click="handleRowClick(userinfo)">
                      <td>{{ userinfo.xh }}</td>
                      <td>{{ userinfo.nj }}</td>
                      <td>{{ userinfo.xm }}</td>
                      <td>{{ userinfo.xb }}</td>
                      <td>{{ userinfo.jg }}</td>
                      <td>{{ userinfo.mz }}</td>
                      <td>{{ userinfo.bjmc }}</td>
                      <td>{{ userinfo.zymc }}</td>
                      <td>{{ userinfo.xymc }}</td>

                    </tr>
                    </tbody>
                  </table>
                  <p class="end" v-if="obj.nextPage==0">
                    <Loading text="我是有底线的"/>

                  </p>
                  <p class="end" v-if="Userinfos == undefined || Userinfos == null || Userinfos.length <=0  ">
                    <Loading text="智能分析中"/>
                  </p>

                  <detail-card :visible="isDetailCardVisible" @close="cardvisible()">
                    <div class="col-md-12 b-heading">
                      <div class="col-md-5 lbox font_font">
                        <StampBadge style="top:50px; float:right;" size="large"
                                    :color="(isNormal === '1') ? 'error' : 'success'"
                                    :content="(isNormal === '1') ? '异常' : '正常'"
                                    :rotate="45">
                        </StampBadge>

                        <p><i class="fa fa-phone"></i>&nbsp;&nbsp;学号：{{ detailContent.xh }}</p>
                        <p><i class="fa fa-user"></i>&nbsp;&nbsp;姓名：{{ detailContent.xm }}</p>
                        <p><i class="fa fa-transgender"></i>&nbsp;&nbsp;性别：{{ detailContent.xb }}</p>
                        <p><i class="fa fa-ils"></i>&nbsp;&nbsp;民族：{{ detailContent.mz }}</p>
                        <p><i class="fa fa-signal"></i>&nbsp;&nbsp;年级：{{ detailContent.nj }}</p>
                        <p><i class="fa fa-header"></i>&nbsp;&nbsp;班级：{{ detailContent.bjmc }}</p>
                        <p><i class="fa fa-calendar-o"></i>&nbsp;&nbsp;专业：{{ detailContent.zymc }}</p>
                        <p><i class="fa fa-university"></i>&nbsp;&nbsp;学院：{{ detailContent.xymc }}</p>
                        <p><i class="fa fa-location-arrow"></i>&nbsp;&nbsp;籍贯：{{ detailContent.jg }}</p>
                      </div>

                      <div class="col-md-8" id="rader" style="float:right; width:450px;height:400px;"></div>
                    </div>

                    <el-date-picker
                      v-model="timerange"
                      type="daterange"
                      :clearable="false"
                      value-format="YYYY-MM-DD"
                      @input="changeDate"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      :disabled-date="disabledDate">
                    </el-date-picker>

                    <div class="col-md-10" id="chart" style="padding:20px; width:800px;height:450px;"></div>
                  </detail-card>

                </div>
              </div>

            </div>


          </div>
        </div>
      </div>
    </div>

    <!-- END MAIN -->
    <footer>
      <div class="container-fluid">
        <p class="copyright">Copyright &copy; 2023-2024. Company all rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script>

import {mixin} from '../assets/js/util'
import axios from 'axios'
import StampBadge from '../components/StampBadge'
import DetailCard from '../components/DetailCard';
import Loading from "../components/Loading";
import * as echarts from 'echarts';

import {onMounted, toRefs, ref, reactive} from 'vue'

export default {
  components: {DetailCard, Loading, StampBadge},
  mixins: [mixin],
  data() {
    return {
      SearchForm: {
        instcode: "",
        enroll: "",
        major: "",
        class: "",
        name: "",
      },
      disabledDate: time => {
        return time.getTime() > Date.now()
      },
      isNormal: "0",
      timerange: [],
      selectedInstitute: '0', // 默认选中第一个选项
      isDetailCardVisible: false,
      detailContent: '',
      Userinfos: [],
      institutes: [],
      enrolls: [],
      majors: [],
      classes: [],
      students: [],
      nextpage: '',
      weeksum: [],
      obj: {
        list: []
      },
    }
  },
  mounted() {
  },
  watch: {
    'timerange': function (newValue, oldValue) {
      this.getChangeChart()
    }
  },
  methods: {
    handleRowClick(userinfo) {
      this.isDetailCardVisible = true;
      console.log('Row clicked:', userinfo);
      this.detailContent = userinfo;

      this.$nextTick(() => {
        this.getChangeChart()
        this.getChangeRader()
      });
    },
    cardvisible(){
      this.$data.isDetailCardVisible = false
      this.$data.timerange = []
    },
    loadPage(num) {
      var _this = this;
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-info',
        params: {
          NJ: _this.SearchForm.enroll,
          XYDM: _this.SearchForm.instcode,
          ZYDM: _this.SearchForm.major,
          BJMC: _this.SearchForm.class,
          XM: _this.SearchForm.name,
          pageNum: num,
          pageSize: 20,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;

          if (res.data.object.isFirstPage && (_this.SearchForm.name != "" || _this.SearchForm.enroll != "" || _this.SearchForm.class != "" || _this.SearchForm.instcode != "" || _this.SearchForm.major != ""))
            _this.$data.Userinfos = res.data.object.list;
          else
            _this.$data.Userinfos = (_this.$data.Userinfos).concat(res.data.object.list);

        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });
    },
    changeDate() {
      this.$forceUpdate()
    },
    loadinstitute(num) {
      var _this = this;
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-institute',
        params: {
          pageNum: num,
          pageSize: 5,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.institutes = res.data.object.list;
        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });

    },
    loadenroll(num) {
      var _this = this;
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-enroll',
        params: {

          pageNum: num,
          pageSize: 5,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.enrolls = res.data.object.list;

        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });

    },
    AIanalysis(num) {
      var _this = this;
      _this.$data.Userinfos = [];
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-abnormal',
        params: {
          TIME: new Date(),
          pageNum: num,
          pageSize: 20,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.Userinfos = res.data.object.list;

        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });
    },
    loadmajor(num) {
      var _this = this;
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-major',
        params: {
          XYDM: _this.SearchForm.instcode,
          pageNum: num,
          pageSize: 10,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.majors = res.data.object.list;

        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });

    },
    loadclass(num) {
      var _this = this;
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-class',
        params: {
          XYDM: _this.SearchForm.instcode,
          ZYDM: _this.SearchForm.major,
          pageNum: num,
          pageSize: 5,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.classes = res.data.object.list;

        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });

    },

    scrollBottom() {
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      let clientHeight = document.documentElement.clientHeight;
      let scrollHeight = document.documentElement.scrollHeight;
      if (scrollTop + clientHeight >= scrollHeight - 10) {
        console.log("触底了");
        var index = this.$data.nextpage;
        if (index > 0) this.loadPage(index);

      }
    },
    getChangeChart() {
      var myChart = echarts.init(document.getElementById('chart'));
      var option;
      myChart.showLoading();
      var _this = this;
      axios({
        method: 'get',
        url: this.getURL() + 'user/search-week',
        params: {
          XH: this.detailContent.xh,
          BEGIN: this.$data.timerange[0],
          END: this.$data.timerange[1],
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res)
        myChart.hideLoading();
        _this.$data.isNormal = res.data.object.poor[0]
        option = {
          title: {
            text: '消费数据'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {},
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              dataView: {readOnly: false},
              magicType: {type: ['line', 'bar']},
              restore: {},
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            // boundaryGap: false,
            data: res.data.object.time,
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value}'
            }
          },
          series: [
            {
              name: '早',
              type: 'line',
              smooth: true,  //true 为平滑曲线，false为直线
              data: res.data.object.zao,
              markPoint: {
                data: [
                  {type: 'max', name: 'Max'},
                  {type: 'min', name: 'Min'}
                ]
              },
              markLine: {
                data: [{type: 'average', name: 'Avg'}]
              }
            },
            {
              name: '中',
              type: 'line',
              smooth: true,  //true 为平滑曲线，false为直线
              data: res.data.object.zhong,
              markPoint: {
                data: [
                  {type: 'max', name: 'Max'},
                  {type: 'min', name: 'Min'}
                ]
              },
              markLine: {
                data: [{type: 'average', name: 'Avg'}]
              }
            },
            {
              name: '晚',
              type: 'line',
              smooth: true,  //true 为平滑曲线，false为直线
              data: res.data.object.wan,
              markPoint: {
                data: [{name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}]
              },
              markLine: {
                data: [
                  {type: 'average', name: 'Avg'},
                  [
                    {
                      symbol: 'none',
                      x: '90%',
                      yAxis: 'max'
                    },
                    {
                      symbol: 'circle',
                      label: {
                        position: 'start',
                        formatter: 'Max'
                      },
                      type: 'max',
                      name: '最高点'
                    }
                  ]
                ]
              }
            }
          ]
        };

        option && myChart.setOption(option);
      })
    },
    getChangeRader() {
      var myRader = echarts.init(document.getElementById('rader'));
      var option;
      myRader.showLoading();
      var _this = this;
      axios({
        method: 'get',
        url: this.getURL() + 'user/search-week-all',
        params: {
          XH: this.detailContent.xh,
          TIME: new Date()
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        console.log(res)
        myRader.hideLoading();
        // 指定图表的配置项和数据
        option = {
          backgroundColor: 'rgba(204,204,204,0.7 )',
          // 背景色，默认无背景    rgba(51,255,255,0.7)
          title: {
            // text: '各教育阶段男女人数统计',
            link: 'https://www.xxx.com',
            target: 'blank',
            top: '5%',
            left: '3%',
            textStyle: {
              color: '#fff',
              fontSize: 20,
            }
          },

          legend: { // 图例组件
            show: true,
            icon: 'rect',
            // 图例项的 icon。ECharts 提供的标记类型包括 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow'也可以通过 'image://url' 设置为图片，其中 url 为图片的链接，或者 dataURI。可以通过 'path://' 将图标设置为任意的矢量路径。
            top: '40%',
            // 图例距离顶部边距
            left: '15%',
            // 图例距离左侧边距
            itemWidth: 10,
            // 图例标记的图形宽度。[ default: 25 ]
            itemHeight: 10,
            // 图例标记的图形高度。[ default: 14 ]
            itemGap: 30,
            // 图例每项之间的间隔。[ default: 10 ]横向布局时为水平间隔，纵向布局时为纵向间隔。
            orient: 'vertical',
            // 图例列表的布局朝向,'horizontal'为横向,''为纵向.
            textStyle: { // 图例的公用文本样式。
              fontSize: 15,
              color: '#fff'
            },
            data: [{ // 图例的数据数组。数组项通常为一个字符串，每一项代表一个系列的 name（如果是饼图，也可以是饼图单个数据的 name）。图例组件会自动根据对应系列的图形标记（symbol）来绘制自己的颜色和标记，特殊字符串 ''（空字符串）或者 '\n'（换行字符串）用于图例的换行。
              name: '个人',
              // 图例项的名称，应等于某系列的name值（如果是饼图，也可以是饼图单个数据的 name）。
              icon: 'rect',
              // 图例项的 icon。
              textStyle: { // 图例项的文本样式。
                color: 'rgba(51,0,255,1)',
                fontWeight: 'bold' // 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
              }
            },
              {
                name: '平均值',
                icon: 'rect',
                textStyle: {
                  color: 'rgba(255,0,0,1)',
                  fontWeight: 'bold' // 文字字体的粗细，可选'normal'，'bold'，'bolder'，'lighter'
                }
              }],
          },

          radar: [{ // 雷达图坐标系组件，只适用于雷达图。
            center: ['50%', '50%'],
            // 圆中心坐标，数组的第一项是横坐标，第二项是纵坐标。[ default: ['50%', '50%'] ]
            radius: 160,
            // 圆的半径，数组的第一项是内半径，第二项是外半径。
            startAngle: 90,
            // 坐标系起始角度，也就是第一个指示器轴的角度。[ default: 90 ]
            name: { // (圆外的标签)雷达图每个指示器名称的配置项。
              formatter: '{value}',
              textStyle: {
                fontSize: 15,
                color: '#000'
              }
            },
            nameGap: 15,
            // 指示器名称和指示器轴的距离。[ default: 15 ]
            splitNumber: 4,
            // (这里是圆的环数)指示器轴的分割段数。[ default: 5 ]
            shape: 'circle',
            // 雷达图绘制类型，支持 'polygon'(多边形) 和 'circle'(圆)。[ default: 'polygon' ]
            axisLine: { // (圆内的几条直线)坐标轴轴线相关设置
              lineStyle: {
                color: '#fff',
                // 坐标轴线线的颜色。
                width: 1,
                // 坐标轴线线宽。
                type: 'solid',
                // 坐标轴线线的类型。
              }
            },
            splitLine: { // (这里是指所有圆环)坐标轴在 grid 区域中的分隔线。
              lineStyle: {
                color: '#fff',
                // 分隔线颜色
                width: 2,
                // 分隔线线宽
              }
            },
            splitArea: { // 坐标轴在 grid 区域中的分隔区域，默认不显示。
              show: true,
              areaStyle: { // 分隔区域的样式设置。
                color: ['rgba(250,250,250,0.3)', 'rgba(200,200,200,0.3)'],
                // 分隔区域颜色。分隔区域会按数组中颜色的顺序依次循环设置颜色。默认是一个深浅的间隔色。
              }
            },
            indicator: [
              { 	// 雷达图的指示器，用来指定雷达图中的多个变量（维度）,跟data中 value 对应
                name: res.data.object.time[0],
                // 指示器名称
                max: 50,
                // 指示器的最大值，可选，建议设置
                //color: '#fff' // 标签特定的颜色。
              },
              {
                name: res.data.object.time[1],
                max: 50
              },
              {
                name: res.data.object.time[2],
                max: 50
              },
              {
                name: res.data.object.time[3],
                max: 50
              },
              {
                name: res.data.object.time[4],
                max: 50
              },
              {
                name: res.data.object.time[5],
                max: 50
              },
              {
                name: res.data.object.time[6],
                max: 50
              }]
          }],
          series: [{
            name: '雷达图',
            // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
            type: 'radar',
            // 系列类型: 雷达图
            itemStyle: { // 折线拐点标志的样式。
              normal: { // 普通状态时的样式
                lineStyle: {
                  width: 1
                },
                opacity: 0.2
              },
              emphasis: { // 高亮时的样式
                lineStyle: {
                  width: 5
                },
                opacity: 1
              }
            },
            data: [{ // 雷达图的数据是多变量（维度）的
              name: '平均值',
              // 数据项名称
              value: res.data.object.aver,
              // 其中的value项数组是具体的数据，每个值跟 radar.indicator 一一对应。
              symbol: 'circle',
              // 单个数据标记的图形。
              symbolSize: 5,
              // 单个数据标记的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
              label: { // 单个拐点文本的样式设置
                normal: {
                  show: true,
                  // 单个拐点文本的样式设置。[ default: false ]
                  position: 'top',
                  // 标签的位置。[ default: top ]
                  distance: 5,
                  // 距离图形元素的距离。当 position 为字符描述值（如 'top'、'insideRight'）时候有效。[ default: 5 ]
                  color: 'rgba(255,0,0,1)',
                  // 文字的颜色。如果设置为 'auto'，则为视觉映射得到的颜色，如系列色。[ default: "#fff" ]
                  fontSize: 14,
                  // 文字的字体大小
                  formatter: function (params) {
                    return params.value;
                  }
                }
              },
              itemStyle: { // 单个拐点标志的样式设置。
                normal: {
                  borderColor: 'rgba(255,0,0,1)',
                  // 拐点的描边颜色。[ default: '#000' ]
                  borderWidth: 3,
                  // 拐点的描边宽度，默认不描边。[ default: 0 ]
                }
              },
              lineStyle: { // 单项线条样式。
                normal: {
                  opacity: 0.5 // 图形透明度
                }
              },
              areaStyle: { // 单项区域填充样式
                normal: {
                  color: 'rgba(255,0,0,0.6)' // 填充的颜色。[ default: "#000" ]
                }
              }
            },
              {
                name: '个人',
                value: res.data.object.sum1,
                symbol: 'circle',
                symbolSize: 5,
                label: {
                  normal: {
                    show: true,
                    position: 'top',
                    distance: 5,
                    color: 'rgba(51,0,255,1)',
                    fontSize: 14,
                    formatter: function (params) {
                      return params.value;
                    }
                  }
                },
                itemStyle: {
                  normal: {
                    borderColor: 'rgba(51,0,255,1)',
                    borderWidth: 3,
                  }
                },
                lineStyle: {
                  normal: {
                    opacity: 0.5
                  }
                },
                areaStyle: {
                  normal: {
                    color: 'rgba(51,0,255,0.5)'
                  }
                }
              }]
          },
          ]
        };
        option && myRader.setOption(option);
      })
    }

  },
  created() {
    this.loadinstitute(1);
    this.loadenroll(1);
    this.loadmajor(1);
    this.loadclass(1);
    this.loadPage(1);
    window.addEventListener('scroll', this.scrollBottom);
  },

  destroyed() {
    window.removeEventListener('scroll', this.scrollBottom) //页面离开后销毁监听事件
  },

}
</script>

<style scoped>

.top1 {
  float: right;
  width: 400px;
  height: 40px;
  border: 1px solid #ccc;
  background-color: #f2f2f5;
  margin: 10px 30px 0;
  border-radius: 10px;
}

.top2 {
  float: right;
  margin: 19px 20px 0;
}

.search {
  height: 40px;
  width: 300px;
  padding: 0 8px;
  outline: none;
  border: none;
  background-color: #f2f2f5;
  border-radius: 10px;
}

.pic {
  align-items: center;
  height: 30px;
  width: 30px;
  float: right;
  padding-top: 5px;
}

.pic img {
  height: 30px;
  width: 30px;
}

.avate {
  width: 40px;
  height: 40px;
  margin: 5px 4px 5px 4px;
}

.input {
  margin: 10px;
  background-color: rgba(253, 248, 248, 0.438);
  max-width: 190px;
  height: 40px;
  padding: 10px;
  /* text-align: center; */
  border: 2px solid white;
  border-radius: 5px;
  /* box-shadow: 3px 3px 2px rgb(249, 255, 85); */
}

/* .input:focus {
  color: rgb(0, 255, 255);
  background-color: #212121;
  outline-color: rgb(0, 255, 255);
  box-shadow: -3px -3px 15px rgb(0, 255, 255);
  transition: .1s;
  transition-property: box-shadow;
} */
.daterange {
  float: left;
  padding: 20px;
}

.detail-box {
  float: left;
  padding: 6px;
  border: 1px solid #ccc; /* 添加边框 */
  border-radius: 4px; /* 可选：添加圆角 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 可选：添加阴影 */
  background-color: #f9f9f9; /* 可选：添加背景颜色 */
}

.btn {
  width: 350px;
  height: 40px;
  color: #afadad;
  -webkit-border-radius: 20px;
  -moz-border-radius: 20px;
  border-radius: 20px;
}

.btn1 {
  -webkit-border-radius: 20px;
  -moz-border-radius: 20px;
  border-radius: 20px;
}

.button {
  float: right;
  display: inline-block;
  border-radius: 4px;
  background-color: #4fa1ee;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 16px;
  padding: 10px;
  width: 120px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 8px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb'; /* CSS Entities. 如果用的是 HTML Entities, 请改成 &#8594;*/
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 25px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}

.end {
  text-align: center;
  font-style: italic;
  color: #afadad;
}

.blog-headimg {
  margin: 4px;
}

.clickable-row {
  cursor: pointer;
}

.font_font {
  padding: 30px 10px 0;
  font-size: larger;

}


</style>
