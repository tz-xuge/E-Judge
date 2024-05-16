<template>
  <!-- WRAPPER -->
		<div id="wrapper" v-cloak>
			<!-- MAIN -->
			<div class="main">
				<div class="main-content">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-8">
								<div class="panel">
									
									<div class="top1">
							        	<input type="text" class="search" v-model="title" placeholder="随你 想搜">
								        <div class="pic"><a href="javascript:void(0);" @click="loadPage(1)"><img src="../../assets/img/search.png" alt=""></a></div>
								    </div>

									<h3 class="b-heading" style="padding-left: 20px;">题目列表</h3> 
									

									
									<div class="panel-body no-padding">
										<table class="table table-striped topic-list">
											<thead>
												<tr>
													<th>#</th>
													<th>标题</th>
													<th>通过率</th>
													<th>难度</th>
													<!-- <th>添加时间</th> -->
												</tr>
											</thead>
											<tbody>
												<tr v-for="item in topics">
													<td>{{item.pid}}</td>
													<td class="pointer" @click="IDJump(item.pid)">{{item.title}}</td>
													<td>{{((item.accepttimes/item.submittimes)*100).toFixed(2)}}%</td>
													<td v-if="item.level==0"><span class="label label-success">简单</span></td>
													<td v-if="item.level==1"><span class="label label-warning">中等</span></td>
													<td v-if="item.level==2"><span class="label label-danger">困难</span></td>
													<!-- <td v-html="timeStamp2String(item.addtime)"></td> -->
												</tr>
											</tbody>
										</table>
									</div>
									<div class="panel-footer">
										<div class="row">
											<div class="col-md-12 text-right">
												<nav aria-label="Page navigation">
												  <ul class="pagination">
												    <li>
												      <a href="javascript:void(0);" @click="loadPage(obj.navigateFirstPage)" aria-label="Previous">
												        <span aria-hidden="true">&laquo;</span>
												      </a>
												    </li>
												    <li v-if="obj.prePage>0"> <a href="javascript:void(0);" @click="loadPage(obj.prePage)">{{obj.prePage}}</a></li>
												    <li class="active"><a href="javascript:void(0);">{{obj.pageNum}}</a></li>
												    <li v-if="obj.nextPage>0"><a href="javascript:void(0);" @click="loadPage(obj.nextPage)">{{obj.nextPage}}</a></li>
												    <li>
												      <a href="javascript:void(0);" @click="loadPage(obj.navigateLastPage)" aria-label="Next">
												        <span aria-hidden="true">&raquo;</span>
												      </a>
												    </li>
												  </ul>
												</nav>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="panel panel-scrolling">
									<div class="panel-heading">
										<h3 class="panel-title">热门推荐</h3>
									</div>
									<div class="panel-body">
										<ul class="list-unstyled activity-list" v-for="(item,index) in recommendTopic">
											<li style="padding: 12px 0;">
												<img :src="require('@/assets/img/' + index + '.png')" alt="Avatar" class="img-circle pull-left avatar">
												<span style="cursor: pointer"><p @click="IDJump(item.pid)">{{item.title}}<span class="timestamp">{{level[item.level]}}</span></p></span>
											</li>
										</ul>
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
					<p class="copyright">Copyright &copy; 2022-2023. Company all rights reserved.</p>
				</div>
			</footer>
		</div>
</template>

<script>

import { mixin } from '../../assets/js/util'
import axios from 'axios'
export default {
    name:"topic",
   	mixins: [mixin],
    data() {
        return{
            topics:[],
		    obj:{
			    list:[]
		    },
			recommendTopic:[],
			level:['简单','中等','困难'],
			title:'',
        }
		
	},
	methods: {
		loadPage(num) {
			var _this = this;
			axios({
				method: 'get',
				url: _this.getRSRootURL() + 'topic/search-all-page',
				params: {
					title:_this.title,
					pageNum:num,
					pageSize:10,
					enableflag:1
				},
				headers: {
					'Authorization':  _this.$store.getters.token,
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=> {
				console.log(res);

				if (res.data.status >= 0) {
					_this.$data.obj = res.data.object;
					_this.$data.topics = res.data.object.list;
				} else {
					_this.notify(res.data.msg,'error');
				}

			}).catch(err=> {
				_this.notify(err,'error');
			});
		},
		IDJump(pid)
		{
			this.$router.push({
					name: 'topic_content',
					query: {param: pid}
				})
		},
		rectopic()
		{
			var _this = this;
			axios({
				method: 'get',
				url: _this.getRSRootURL() + 'topic/recommend-topic',
				params: {
					enableflag:1
				},
				headers: {
					'Authorization':  _this.$store.getters.token,
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=> {
				console.log(res);

				if (res.data.status >= 0) {
					_this.$data.recommendTopic = res.data.object;
				} else {
					_this.notify(res.data.msg,'error');
				}

			}).catch(err=> {
				_this.notify(err,'error');
			});
		}

	},
	created() {
		this.loadPage(1);
		this.rectopic();
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
.pic img{
    height: 30px;
    width: 30px;
}
</style>