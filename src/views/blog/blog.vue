<template>
  <div id="wrapper" v-cloak>
			<div class="main">
				<div class="main-content">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-18">
								<div class="panel" >
									
									<div class="top1">
							        	<input type="text" class="search" v-model="title" placeholder="随你 想搜">
								        <div class="pic"><a href="javascript:void(0);" @click="loadPage(1)"><img src="../../assets/img/search.png" alt=""></a></div>
								    </div>

									<h3 class="b-heading" style="padding-left: 20px; font-weight: bold;" >E-Judge</h3> 
									
									<div class="panel-body">

										<ul class="list-unstyled todo-list blog-list" ref="blog_list">
											<li v-for="item in articles">
												<p>
													<span class="title blog-title" @click="IDJumpblog(item.aid)">{{subStr(item.title,64,"...")}}</span>
													<span class="short-description">{{subStr(removeHtml(item.content),164,"...")}}</span>
													<span class="date blog-data">
														<img alt="headimg" class="blog-headimg" :src="item.user.headimg">
														<span class="blog-name">{{item.user.name}}</span>
														
														<span class="blog-visits"><i class="fa fa-eye"></i>{{item.visits}}</span>
														<span class="blog-likes" ><i class="fa fa-thumbs-up"></i>{{item.likes}}</span>
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
			</div>+

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
  name:"Login",
   	mixins: [mixin],
  data(){
    return{
      articles:[],
	  users:'',
	  nextpage:'',
		  obj:{
			list:[]
		},
		title:'',
		headimg:'',
    }
		
	},
	methods: {
		loadPage(num) {
			var _this = this;
			axios({
				method: 'get',
				url: _this.getUSRootURL() + 'article/search-articles',
				params: {
					title:_this.title,
					pageNum:num,
					pageSize:5,
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
					_this.$data.nextpage = res.data.object.nextPage;
					if(_this.title != '')
					{
						_this.$data.articles = res.data.object.list;
					}else{
						_this.$data.articles = (_this.$data.articles).concat(res.data.object.list);
					}

				} else {
					_this.notify(res.data.msg,'warning');
				}

			}).catch(err=> {
				_this.notify(err,'error');
			});
		},
		loadblogger()
		{
			var _this = this;
			axios({
				method: 'get',
				url: _this.getUSRootURL() + 'user/get-blogger',
				headers: {
					'Authorization':  _this.$store.getters.token,
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=>{
				if(res.data.status >= 0)
				{
					console.log(res);
					_this.$data.users = res.data.object;
				}
			}).catch(err=>{
				_this.notify(err,'error');
			})
		},
		
		jump()
		{
			this.$router.push('/blog_add');
		},
		IDJump(aid)
		{
			this.$router.push({
					name: 'blog_content',
					query: {param: aid}
				})
		},
		scrollBottom() {
			let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
			let clientHeight = document.documentElement.clientHeight;
			let scrollHeight = document.documentElement.scrollHeight;
			if (scrollTop + clientHeight >= scrollHeight-10) {
				console.log("触底了");
				var index = this.$data.nextpage;
				if(index > 0) this.loadPage(index);

			}
   		},
		IDJumpprofile(uid)
		{
			this.$router.push({
					name: 'profile',
					query: {param: uid}
				})
		},
		IDJumpblog(aid)
		{
			this.$router.push({
					name: 'blog_content',
					query: {param: aid}
				})
		}
		},

		created() {
			this.$data.headimg = this.$store.getters.headimg;
			this.loadPage(1);
			this.loadblogger();
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
.avate{
	width: 40px;
	height: 40px;
	margin: 5px 4px 5px 4px;
}
.btn{
	width: 350px;
	height: 40px;
	color: #afadad;
	-webkit-border-radius: 20px;
	-moz-border-radius: 20px;
	border-radius: 20px;
}
.end{
	text-align:center;
	font-style:italic;
	color: #afadad;
}
.blog-headimg{
	margin: 4px;
}



</style>