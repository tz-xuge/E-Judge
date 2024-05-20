
import 'element-plus/dist/index.css'
import { ElMessage } from 'element-plus'

export const mixin = {

	methods: {

	//消息通知
	notify (title, type) {
		ElMessage({
            showClose: true,
            message: title,
			type: type
          });
		},

	//us 获取域名+端口号的根URL
	getURL(path) {
		var url =
		//'http://localhost:9527/hebeuoj/';
		'http://localhost:8020/';
		if(path!=null){
			return url+path;
		}
		//var url = 'http://localhost:8020/us';
		//var url = window.location.protocol+'//'+window.location.host;
		return url;
	},

		//rs 获取域名+端口号的根URL
		getRSRootURL(path) {
			var url =
			//'http://localhost:9527/hebeuoj/';
			'http://localhost:8021/';
			if(path!=null){
				return url+path;
			}
			//var url = 'http://localhost:8021/us';
			//var url = window.location.protocol+'//'+window.location.host;
			return url;
	},


	updatePrintnr(num, t) {
		if (num == t) {
			$('#send').html("发送");
			eventBtn = true;
		} else {
			printnr = t - num;
			$('#send').html(printnr);
			eventBtn = false;
		}
	},

	showtime(t) {
		for (var i = 1; i <= t; i++) {
			window.setTimeout("updatePrintnr(" + i + "," + t + ")", i * 1000);
		}
	},

	/// <summary>
	/// 截取字符串
	/// </summary>
	/// <param name="str">截取内容</param>
	/// <param name="len">截取长度,中文*2</param>
	/// <param name="flow">截取剩余显示的符号</param>
	subStr(str, len, flow) {
		if (!str||str==null) return '';
		str = str.toString();
		var newStr = "",
			strLength = str.replace(/[^\x00-\xff]/g, "**").length,
			flow = typeof(flow) == 'undefined' ? '...' : flow;

		if (strLength <= len + (strLength % 2 == 0 ? 2 : 1)) return str;

		for (var i = 0, newLength = 0, singleChar; i < strLength; i++) {
			singleChar = str.charAt(i).toString();
			if (singleChar.match(/[^\x00-\xff]/g) != null) newLength += 2;
			else newLength++;

			if (newLength > len) break;
			newStr += singleChar;
		}

		if (strLength > len) newStr = $.trim(newStr) + flow;
		return newStr;
	},

	//去除html标签
	removeHtml(ss) {
		if (!ss||ss==null) return '';
		var dd=ss.replace(/<\/?.+?>/g,"");
		var dds=dd.replace(/ /g,"");//dds为得到后的内容
		return dds;
	},
	load(path) {
		let xhr = new XMLHttpRequest(),
		okStatus = document.location.protocol === "file:" ? 0 : 200;
		xhr.open('GET', path, false);
		xhr.overrideMimeType("text/html;charset=utf-8");//默认为utf-8
		xhr.send(null);
		return xhr.status === okStatus ? xhr.responseText : null;
	},
	//获取文件内容
	getFileContent(filePath) {
		let text = this.load(filePath);
		return text;
	},

	//直接跳转
	jump(path) {
		location=path;
	},

	/**
	 *  睡眠函数
	 *  @param numberMillis -- 要睡眠的毫秒数
	 */
 	sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    	}
	},

	power(date){
		var _this = this;
		var timestamp1 = (new Date(date)).getTime();
		var timestamp2 = (new Date()).getTime();
		if(timestamp1 > timestamp2){
			//_this.notify("竞赛还未开始，禁止答题",'warning');
			return false;
		}
		return true;
	},
	datefater(date){
		var timestamp1 = this.date2TimeStamp(date);
		var timestamp2 = (new Date()).getTime();
		if(timestamp1 < timestamp2){
			this.notify("竞赛已经结束，禁止答题",'warning');
			return false;
		}
		return true;
	},
	//判断是否为数值
	isRealNum(val) {
		// isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除，
		if (val === "" || val == null) {
			return false;
		}
		if (!isNaN(val)) {
			//对于空数组和只有一个数值成员的数组或全是数字组成的字符串，isNaN返回false，例如：'123'、[]、[2]、['123'],isNaN返回false,   //所以如果不需要val包含这些特殊情况，则这个判断改写为if(!isNaN(val) && typeof val === 'number' )
			return true;
		} else {
			return false;
		}
	},

	//空值效验
	validityForStringNull(val) {
		if (val == null || val === '') {
			return true;
		} else {
			return false;
		}
	},

	//等值效验,转小写
	validityForStringEqual(val1, val2) {
		console.log(val1.toLowerCase() + " " + val2.toLowerCase())
		if (val1.toLowerCase() === val2.toLowerCase()) {
			return true;
		} else {
			return false;
		}
	},

	//检查是否为正确的日期时间
	checkDateTime(str){
		if(this.validityForStringNull(str)) return true;// 为空返回true
		var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
		var r = str.match(reg);
		if(r==null)return false;
		r[2]=r[2]-1;
		var d= new Date(r[1], r[2],r[3], r[4],r[5], r[6]);
		if(d.getFullYear()!=r[1])return false;
		if(d.getMonth()!=r[2])return false;
		if(d.getDate()!=r[3])return false;
		if(d.getHours()!=r[4])return false;
		if(d.getMinutes()!=r[5])return false;
		if(d.getSeconds()!=r[6])return false;
		return true;
	},

	//日期格式转时间戳
	date2TimeStamp(time) {
		if(/^[0-9]*$/.test(time)) return time; //检查是否为时间戳
		//var data = time.substr(0, 19);
		var newDate = new Date(new Date(time.replace(/T/g, ' ').replace(/-/g, '/')).getTime() + 8 * 60 * 60 * 1000);
		return newDate.getTime();
	},

	//时间戳转换日期格式
	timeStamp2String(time) {
		if(this.checkDateTime(time)) return time; //检查是否为正确的日期时间
		var datetime = new Date();
		datetime.setTime(time);
		if(!/^[0-9]*$/.test(time)){ //是否为时间戳
			var data = time.substr(0, 19);
			var newDate = new Date(new Date(data.replace(/T/g, ' ').replace(/-/g, '/')).getTime() + 8 * 60 * 60 * 1000);
			datetime.setTime(newDate.getTime());
		}
		var year = datetime.getFullYear();
		var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
		var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
		var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
		var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
		var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	},

	//时间差，单位min
	dateDiff(date1,date2){
		var time = this.date2TimeStamp(date2) -this.date2TimeStamp(date1);
		return time/1000/60;
	},

	//获取url的参数
	getUrlQuery(url) {
		var dz_url = url.split('#')[0]; //获取#/之前的字符串
		var cs = dz_url.split('?')[1]; //获取?之后的参数字符串
		var cs_arr = cs.split('&'); //参数字符串分割为数组
		var cs = {};
		for (var i = 0; i < cs_arr.length; i++) { //遍历数组，拿到json对象
			cs[cs_arr[i].split('=')[0]] = cs_arr[i].split('=')[1]
		}
		return cs;
	},



}
}
