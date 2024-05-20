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
                <div class="top1">
                  <input type="text" class="search" v-model="title" placeholder="随你 想搜">
                  <div class="pic"><a href="javascript:void(0);" @click="loadPage(1)"><img
                    src="../assets/img/search.png" alt=""></a></div>
                </div>

                <h3 class="b-heading" style="padding-left: 20px; font-weight: bold;">E-Judge</h3>
                <div class="collapse" id="collapseExample">
                  <div class="panel panel-primary ">
                    <div class="panel-heading ">
                      <h3 class="panel-title">高级查询</h3>
                    </div>
                    <div class="panel-body btn1">

                      <div style="display:inline-block;">
                        <!-- 年级 -->
                        <div style="float:left;padding:4px;">
                          <span>年级：</span>
                        </div>

                        <div style="float:left;">
                          <select id="enroll" class="form-control" style="width:200px;" v-model="SearchForm.enroll"
                                  @change="loadenroll(1)" οnfοcus="selectFocus(this)">
                            <option value="0">-请选择-</option>
                            <option v-for="enroll in enrolls" :key="enroll" :value="enroll">{{ enroll }}</option>
                          </select>
                        </div>
                        <!-- 学院 -->
                        <div style="float:left;padding:4px;">
                          <span>学院：</span>
                        </div>
                        <div style="float:left;">
                          <select id="institute" class="form-control" style="width:200px;" v-model="SearchForm.instcode"
                                  @change="loadinstitute(1)" οnfοcus="selectFocus(this)">
                            <option value="0">-请选择-</option>
                            <option v-for="institute in institutes" :key="institute.xymc" :value="institute.xydm">
                              {{ institute.xymc }}
                            </option>
                          </select>
                        </div>
                        <!-- 专业 -->
                        <div style="float:left;padding:4px;">
                          <span>专业：</span>
                        </div>
                        <div style="float:left;">
                          <select id="major" class="form-control" style="width:200px;" v-model="SearchForm.major"
                                  @change="loadmajor(1)" οnfοcus="selectFocus(this)">
                            <option value="0">-请选择-</option>
                            <option v-for="major in majors" :key="major.zymc" :value="major.zydm">{{
                                major.zymc
                              }}
                            </option>
                          </select>
                        </div>
                        <!--班级-->
                        <div style="float:left;padding:4px;">
                          <span>班级：</span>
                        </div>
                        <div style="float:left;">
                          <select id="class" class="form-control" style="width:200px;" v-model="SearchForm.class"
                                  @change="loadclass(1)" οnfοcus="selectFocus(this)">
                            <option value="0">-请选择-</option>
                            <option v-for="cless in classes" :key="cless.bjmc" :value="cless.bjmc">{{
                                cless.bjmc
                              }}
                            </option>
                          </select>
                        </div>
                        <!-- 姓名 -->
                        <div style="float:left;padding:4px;">
                          <span>姓名：</span>
                        </div>
                        <div style="float:left;">
                          <input id="name" class="form-control" style="width:200px;" placeholder="请输入姓名"/>
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
                  <!-- 学生数据信息 -->
                  <ul class="list-unstyled todo-list blog-list" ref="blog_list">
                    <li v-for="item in articles">
                      <p>
                        <span class="title blog-title"
                              @click="IDJumpblog(item.aid)">{{ subStr(item.title, 64, "...") }}</span>
                        <span class="short-description">{{ subStr(removeHtml(item.content), 164, "...") }}</span>
                        <span class="date blog-data">
														<img alt="headimg" class="blog-headimg" :src="item.user.headimg">
														<span class="blog-name">{{ item.user.name }}</span>

														<span class="blog-visits"><i class="fa fa-eye"></i>{{ item.visits }}</span>
														<span class="blog-likes"><i class="fa fa-thumbs-up"></i>{{ item.likes }}</span>
													</span>
                      </p>
                    </li>
                  </ul>
                  <p class="end" v-if="obj.nextPage==0">==THE END==</p>
                </div>
              </div>

            </div>


          </div>
        </div>
      </div>
    </div>
    +

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

export default {
  name: "Login",
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
      institutes: [],
      Userinfo:[],
      enrolls: [],
      majors: [],
      classes: [],
      students: [],
      nextpage: '',
      obj: {
        list: []
      },

    }

  },
  methods: {
    loadPage(num){
      var _this = this;
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-info',
        params: {
          NJ:_this.SearchForm.enroll,
          XYDM:_this.SearchForm.instcode,
          ZYDM:_this.SearchForm.major,
          BJMC:_this.SearchForm.class,
          pageNum:num,
          pageSize:5,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res=> {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.Userinfo = res.data.object.list;

        } else {
          _this.notify(res.data.msg,'warning');
        }

      }).catch(err=> {
        _this.notify(err,'error');
      });

    },

    scrollselect(){
      window.selectFocus = function(that) {
        $(that).attr("size", 5);
      };
      window.selectClick = function(that) {
        $(that).parent().removeAttr("size");
        $(that).parent().blur();
        $(that).parent().children("[selected='selected']").removeAttr("selected");
        $(that).attr("selected", "");
      };
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
          _this.loadmajor(1);
          _this.loadclass(1);
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

    loadmajor(num) {
      var _this = this;
      console.log(_this.SearchForm.instcode);
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-major',
        params: {
          XYDM: _this.SearchForm.instcode,
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
          _this.$data.majors = res.data.object.list;
          _this.loadclass(1);
        } else {
          _this.notify(res.data.msg, 'warning');
        }

      }).catch(err => {
        _this.notify(err, 'error');
      });

    },
    loadclass(num){
      var _this = this;
      console.log(_this.SearchForm.major);
      axios({
        method: 'get',
        url: _this.getURL() + 'user/search-class',
        params: {
          XYDM:_this.SearchForm.instcode,
          ZYDM:_this.SearchForm.major,
          pageNum:num,
          pageSize:5,
        },
        headers: {
          'Authorization': _this.$store.getters.token,
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(res=> {
        console.log(res);

        if (res.data.status >= 0) {
          _this.$data.obj = res.data.object;
          _this.$data.nextpage = res.data.object.nextPage;
          _this.$data.classes = res.data.object.list;

        } else {
          _this.notify(res.data.msg,'warning');
        }

      }).catch(err=> {
        _this.notify(err,'error');
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
  },

  created() {

    this.loadinstitute(1);
    this.loadenroll(1);
    this.loadmajor(1);
    this.loadclass(1);
    this.scrollselect();
    window.addEventListener('scroll', this.scrollBottom);

  },
  destroyed() {
    window.removeEventListener('scroll', this.scrollBottom) //页面离开后销毁监听事件
  },
  mounted() {
  }

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

.end {
  text-align: center;
  font-style: italic;
  color: #afadad;
}

.blog-headimg {
  margin: 4px;
}

</style>
