<template>
  <!-- WRAPPER -->
		<div id="wrapper" v-cloak>
			<!-- 新增教学组模态框 -->
			<div class="modal" id="up_img" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			   <div class="modal-dialog">
			      <div class="modal-content">
			         <div class="modal-header">
			            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">close</span></button>
			            <h4 class="modal-title">更新头像</h4>
			         </div>
			         <div class="modal-body">
						<span style="color: red;">*</span>
						<span>样例输出</span>
						<input type="file" name="work_file" id="up_avater" />
						<input type="hidden" id="updateImg">
			         </div>
			         <div class="modal-footer">
			            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="upUseravater()">提交</button>
			         </div>
			      </div>
			   </div>
			</div>
			<!-- MAIN -->
			<div class="main">
				<div class="main-content">
					<div class="container-fluid">
						<div class="panel panel-profile">
							<div class="clearfix">
								<div class="profile-left">
									<div class="profile-header">
										<div class="overlay"></div>
										<div class="profile-main">
											<img v-bind:src="user.headimg" class="img-circle avate" alt="img" @click="upavater()">
											<h3 class="name">{{user.name}}</h3>
										</div>
										<div class="profile-stat">
											<div class="row">
												<div class="col-md-4 stat-item">
												{{user.sum}} <span>博客</span>
												</div>
												<div class="col-md-4 stat-item">
													15% <span>通过率</span>
												</div>
												<div class="col-md-4 stat-item">
													2174 <span>排名</span>
												</div>
											</div>
										</div>
									</div>
									<div class="profile-detail">
										<div class="profile-info">
											<h4 class="heading">个人简历</h4>
											<ul class="list-unstyled list-justify">
												<li>电话 <span>{{user.phone}}</span></li>
												<li>邮箱 <span>{{user.email}}</span></li>
												<li>姓名 <span >{{user.realname}}</span></li>
												<li>性别 <span v-if="user.sex == 0">男</span><span v-if="user.sex == 1">女</span></li>
												<li>地址 <span>{{user.address}}</span></li>
											</ul>
										</div>
										<div class="profile-info">
											<h4 class="heading">徽章</h4>
											<ul class="list-inline social-icons">
												<li><a href="www.facebook.com/" class="facebook-bg"><i class="fa fa-facebook"></i></a></li>
												<li><a href="https://www.twitter.com/" class="twitter-bg"><i class="fa fa-twitter"></i></a></li>
												<li><a href="https://www.google.com/" class="google-plus-bg"><i class="fa fa-google-plus"></i></a></li>
												<li><a href="https://github.com/" class="github-bg"><i class="fa fa-github"></i></a></li>
											</ul>
										</div>
										<div class="profile-info">
											<h4 class="heading">签名</h4>
											<p>{{user.profile}}</p>
										</div>
										
									</div>
								</div>
								<div class="profile-right">
									<h4 class="heading">解决问题</h4>
									<div class="awards">
										<div class="row">
											<div class="col-md-4 col-sm-6">
												<div class="award-item">
													<div class="hexagon">
														<span class="lnr lnr-pencil award-icon"></span>
													</div>
													<span>简单 20/500</span>
												</div>
											</div>
											<div class="col-md-4 col-sm-6">
												<div class="award-item">
													<div class="hexagon">
														<span class="lnr lnr-highlight award-icon"></span>
													</div>
													<span>中等 78/320</span>
												</div>
											</div>
											<div class="col-md-4 col-sm-6">
												<div class="award-item">
													<div class="hexagon">
														<span class="lnr lnr-location award-icon"></span>
													</div>
													<span>困难 12/120</span>
												</div>
											</div>
										</div>
									</div>
						
									<div class="tab-content">
										<h4 class="heading">热门文章</h4>
										<div class="panel-body">
											<ul class="list-unstyled activity-timeline">
												<li style="margin-bottom: 20px;" v-for="item in articles">
													<i class="fa fa-plus activity-icon"></i>
													<p>
													<span class="title blog-title" @click="IDJump(item.aid)">{{item.title}}</span>
													<span class="blog-visits"><i class="fa fa-eye"></i>{{item.visits}}</span>
														<span class="blog-likes"><i class="fa fa-thumbs-up"></i>{{item.likes}}</span>
													<span class="timestamp">{{item.addtime}}</span>
													
													</p>
													
												</li>
											</ul>
										</div>
									
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

import $ from 'jquery'
require('bootstrap-fileinput/js/fileinput.js') // 引入图片上传插件  input 的逻辑全部都写在了这个文件内部了  需要注意！！！
require('bootstrap-fileinput/js/locales/zh.js') // 引入图片插件的汉化包
require('bootstrap-fileinput/themes/fa5/theme.js') // 引入主题包
require('bootstrap/dist/js/bootstrap.js') // 引入 bootstrap
import 'bootstrap-fileinput/css/fileinput.min.css'

export default {
    name:"profile",
   	mixins: [mixin],

    data(){
        return{
            user:{},
			articles:'',
			id:'',
			option:{
				language: 'zh',
				uploadUrl: 'http://101.200.87.232:15962/uploadImage',
				dropZoneTitle: '可以将文件拖放到这里,支持文件上传',
				maxFileCount: 1, //最多上传几个文件
				allowedFileExtensions:['png','jpg','jpeg'],//允许上传的文件类型，错误会给出提示
				showUpload: false,
				showRemove: false,
				autoReplace: true,
          }
        }
		
	},

	methods: {
		loadPage(id) {
			var _this = this;
			
            axios({
				method: 'get',
				url: _this.getUSRootURL() + 'user/get-profile',
				params: {
					id:id
					//this.$store.getters.uid
				},
				headers: {
					'Authorization':  _this.$store.getters.token,
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=> {
				console.log(res);

				if (res.data.status >= 0) {
					_this.$data.user = res.data.object;
				} else {
					_this.notify(res.data.msg,'success');
				}

			}).catch(err=> {
				_this.notify(err,'error');
			});
		},
		upavater()
		{
			$('#up_img').modal('show');
		},
		toprofile_set()
		{
			this.$router.push('/profile_set')
		},
		toprivate_set()
		{
			this.$router.push('/privacy_set')
		},
		loadarticles(id)
		{
			var _this = this;
			axios({
				method: 'get',
				url: _this.getUSRootURL() + 'article/get-pop',
				params:{
					id:id
				},
				headers: {
					'Authorization':  _this.$store.getters.token,
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=>{
				if(res.data.status >= 0)
				{
					console.log(res);
					_this.$data.articles = res.data.object;
				}
			}).catch(err=>{
				_this.notify(err,'error');
			})
		},
		upUseravater()
		{
			var _this = this;
			axios({
				method: 'post',
				url: _this.getUSRootURL() + 'user/up-avater',
				data: {
					headimg: $("#updateImg").val()
				},
				headers: {
					'Authorization':  _this.$store.getters.token,
					'Content-Type': 'application/json'
				}
			}).then(res=> {
				console.log(res);

				if (res.data.status >= 0) {
					_this.notify("更新成功",'success');
					window.location.reload();
				} else {
					_this.notify(res.data.msg,'error');
				}

			}).catch(err=> {
				_this.notify(err,'error');
			});
			
		},
		 IDJump(aid)
		{
			this.$router.push({
					name: 'blog_content',
					query: {param: aid}
				})
		},
		initInputImg() {
                $("#up_avater").fileinput(this.option)
                // 实现图片被选中后自动提交
                .on('filebatchselected', function(event, data, previewId, index) {
                    // 选中事件
                    $(event.target).fileinput('upload')
                })
                .on('fileuploaded', function(event,data,previewId,index) {
                    var res = data.response;
                    console.log(data.response);
                    document.getElementById('updateImg').value = res.data;
                });
            },

	},
	created() {
		if(this.$route.query.param) this.$data.id = this.$route.query.param
		else this.$data.id = this.$store.getters.uid

		this.loadPage(this.$data.id)
		this.loadarticles(this.$data.id)
	},
	mounted() {
		this.initInputImg()
	}

}
</script>

<style>
.modal{
    top: 25%;
}
.modal-backdrop{
    position:fixed;
	top:0;
	right:0;
	bottom:0;
	left:0;
	z-index:1040;
	background-color:#000;
	opacity:0.5;
}
.avate{
	width: 80px;
	height: 80px;
	margin: 4px;
}
.profile-header .profile-main {
position: relative;
padding: 20px;
background-image: url("../../assets/img/profile_bg.jpg");
background-repeat: no-repeat;
background-size: cover; 
}

.blog-likes {
	cursor: pointer;
	vertical-align: middle;
	float: right;
	color: #7f7e7e;
	font-size: 1px;

}

.blog-likes:hover {
	color: #00a0f0;
}

.blog-likes>i {
	margin-right: 4px;
}

.blog-visits {
	
	cursor: pointer;
	margin-left: 10px;
	vertical-align: middle;
	float: right;
	color: #7f7e7e;
	font-size: 1px;
	
}

.blog-visits>i {
	margin-right: 4px;
}

.blog-visits:hover {
	color: #00a0f0;
}

</style>