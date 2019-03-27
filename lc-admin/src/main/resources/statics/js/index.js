layui.config({
  base:'statics/js/'
}).use(['navtab'],function(){
	window.jQuery = window.$ = layui.jquery;
	window.layer = layui.layer;
    var element = layui.element(),
	navtab = layui.navtab({
		elem: '.larry-tab-box'
	});

    //iframe自适应
	$(window).on('resize', function() {
		var $content = $('#larry-tab .layui-tab-content');
        $content.height($(this).height() - 108);
	    $content.find('iframe').each(function(index) {
	    	if(index!=0){
                $(this).height($content.height()-30);
			}else {
                $(this).height($content.height());
			}
	    });
	}).resize();


	$(function(){
	    $('#larry-nav-side').on("click","li",function(){
            if( $(this).hasClass("layui-nav-itemed") ){
                $(this).removeClass("layui-nav-itemed");
            }
	    	if( $(this).attr("dc-select")=="true" ){
                $(this).removeClass("layui-nav-itemed").attr("dc-select","false");
            }else {
                $(this).attr("dc-select","true").addClass("layui-nav-itemed").siblings().removeClass("layui-nav-itemed").attr("dc-select","false");
                $(this).find("dd").removeClass("dc-nav-this");
			}
			if($(this).find('dl').length > 0){

				$(this).on("click",'dd',function(){
					   var $a = $(this).children('a');
					   var href = $a.data('url');
					   var id = $a.data('id');
					   var icon = $a.children('i:first').data('icon');
					   var title = $a.children('span').text();
					   var data = {
							href: href,
							icon: icon,
							title: title,
							id:id
					   };
					   navtab.tabAdd(data);
					   //左侧 侧边栏点亮
					   $(this).addClass("dc-nav-this").siblings().removeClass("dc-nav-this");
					   vm.navTitle=title;//面包屑赋值
					   return false;
			   });
			}else{
				   var $a = $(this).find('a');
				   var href = $a.data('url');
				   var id = $a.data('id');
				   var icon = $a.children('i:first').data('icon');
				   var title = $a.children('span').text();
				   var data = {
						href: href,
						icon: icon,
						title: title,
						id:id
				   }
				   navtab.tabAdd(data);
			}
	    });
	});
});

// larry-side-menu向左折叠
function isShrinkSideFn (isShrink) {
    var isShrink=isShrink|false;//true隐藏、false显示
    if(isShrink) {
        $('#larry-body').animate({
            left: '0'
        });
        $('#larry-footer').animate({
            left: '0'
        });
        $('#larry-side').animate({
            width: '0'
        });
    } else {
        $('#larry-body').animate({
            left: '200px'
        });
        $('#larry-footer').animate({
            left: '200px'
        });
        $('#larry-side').animate({
            width: '200px'
        });
    }
};
//更新tit标识
layui.use(['jquery','layer','element'],function(){
	window.jQuery = window.$ = layui.jquery;
	window.layer = layui.layer;
	var element = layui.element();

	// larry-side-menu向左折叠
	$('.larry-side-menu').click(function () {
        var sideWidth = $('#larry-side').width();
        if(sideWidth === 200) {
            $('#larry-body').animate({
                left: '0'
            });
            $('#larry-footer').animate({
                left: '0'
            });
            $('#larry-side').animate({
                width: '0'
            });
        } else {
            $('#larry-body').animate({
                left: '200px'
            });
            $('#larry-footer').animate({
                left: '200px'
            });
            $('#larry-side').animate({
                width: '200px'
            });
        }
    });
});



//生成菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{}},
	template:[
	          '<li class="layui-nav-item" v-if="item.isShow==1" >',
	          '<a v-if="item.type === 0" href="javascript:;">',
	          '<span class="iconbox"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="layui-icon">&#xe60a;</i></span>',
	          '<span>{{item.name}}</span>',
	          '<em class="layui-nav-more"></em>',
	          '</a>',
	          '<dl v-if="item.type === 0" class="layui-nav-child">',
	          '<dd v-for="item in item.list">',
	          '<a v-if="item.type === 1 &&  item.isShow==1 " href="javascript:;" :data-id="item.menuId" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
	          '</dd>',
	          '</dl>',
	          '<a v-if="item.type === 1 &&  item.isShow==1 " href="javascript:;" :data-id="item.menuId" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
	          '</li>'
	].join('')
});

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#layui_layout',
	data:{
		user:{},
		menuList:{},
		password:'',
		newPassword:'',
        navTitle:"系统首页",
		num:"",
		show:false,
	},
	methods: {
        cback:function(){
            $(window.parent.document).find(".layui-tab-item").eq(0).addClass("layui-show").siblings().removeClass("layui-show");
            $(window.parent.document).find("#admin-home").addClass("layui-this").siblings().removeClass("layui-this");
            vm.navTitle="系统首页";
        },
		getMenuList: function () {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        waitHand:function () {//等待办理
			if (!$("#larry-side .layui-nav li").eq(0).attr("dc-select")){
                $("#larry-side .layui-nav li").eq(0).click();
			}
            $("#larry-side .layui-nav li").eq(0).find("dl a").eq(0).click();
        },
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	}
});
