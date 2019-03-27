layui.define(['element'], function(exports){
   var  element = layui.element(),
        $ = layui.jquery,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		module_name = 'navtab',
	    globalTabIdIndex = 0,
	LarryTab = function(){
          this.config ={
          	  elem: undefined,
			  closed: true
          };
	};
    var ELEM = {};
    /**
     * [参数设置 options]
     */
    LarryTab.prototype.set = function(options){
          var _this = this;
          $.extend(true, _this.config, options);
          return _this;
    };
    /**
     * [init 对象初始化]
     * @return {[type]} [返回对象初始化结果]
     */
    LarryTab.prototype.init  = function(){
         var _this = this;
         var _config = _this.config;
         if(typeof(_config.elem) !== 'string' && typeof(_config.elem) !== 'object') {
		       layer.alert('Tab选项卡错误提示: elem参数未定义或设置出错，具体设置格式请参考文档API.');
	     }
	     var $container;
	     if(typeof(_config.elem) === 'string') {
		     $container = $('' + _config.elem + '');
		     //console.log($container);
	     }
	     if(typeof(_config.elem) === 'object') {
		     $container = _config.elem;
	     }
	     if($container.length === 0) {
		     layer.alert('Tab选项卡错误提示:找不到elem参数配置的容器，请检查.');
	     }
	     var filter = $container.attr('lay-filter');
	     if(filter === undefined || filter === '') {
		      layer.alert('Tab选项卡错误提示:请为elem容器设置一个lay-filter过滤器');
	     }
	     _config.elem = $container;
	     ELEM.titleBox = $container.children('ul.layui-tab-title');
	     ELEM.contentBox = $container.children('div.layui-tab-content');
	     ELEM.tabFilter = filter;
	     return _this;
    };
    /**
     * [exists 在layui-tab中检查对应layui-tab-title是否存在，如果存在则返回索引值，不存在返回-1]
     * @param  {[type]} title [description]
     * @return {[type]}       [description]
     */
    LarryTab.prototype.exists = function(title){
        var _this = ELEM.titleBox === undefined ? this.init() : this,
		    tabIndex = -1;
		ELEM.titleBox.find('li').each(function(i,e){
		    var $em = $(this).children('em');
		    if($em.text() === title) {
			      tabIndex = i;
		    };
	    });
	    return tabIndex;
    };
    /**
     * [tabAdd 增加选项卡，如果已存在则增加this样式]
     * @param  {[type]} data [description]
     * @return {[type]}      [description]
     */
    LarryTab.prototype.tabAdd = function(data){
        var _this = this;
	    var tabIndex = _this.exists(data.title);
        var tabId = _this.exists(data.id);
	    // 若不存在
	    if(tabIndex === -1){
	    	globalTabIdIndex++;
	    	var content = '<iframe src="' + data.href + '" data-id="' + data.id + '" class="larry-iframe"></iframe>';
		    var title = '';
		    // 若icon有定义
		    if(data.icon !== undefined){
                if(data.icon.indexOf('fa-') !== -1) {
				    title += '<i class="' + data.icon + '"></i>';
			    } else {
			    	title += '<i class="layui-icon ">' + data.icon + '</i>';
			    }
		    }
		    title += '<em>' + data.title + '</em>';
		    if(_this.config.closed) {
			    title += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + data.id + '">&#x1006;</i>';
		    }
		    //添加tab
		    element.tabAdd(ELEM.tabFilter, {
			    title: title,
			    content: content
		    });
		    //iframe 自适应
		    ELEM.contentBox.find('iframe[data-id=' + data.id + ']').each(function() {
		    	$(this).height(ELEM.contentBox.height()-30);
		    });
		    if(_this.config.closed) {
			//监听关闭事件
			    ELEM.titleBox.find('li').children('i.layui-tab-close[data-id=' + data.id + ']').on('click', function() {
			    	element.tabDelete(ELEM.tabFilter, $(this).parent('li').index()).init();
                    window.parent.isShrinkSideFn(false);//左侧边栏显示
			    });
		    };
		    //切换到当前打开的选项卡
		    element.tabChange(ELEM.tabFilter, ELEM.titleBox.find('li').length - 1);

        }else {
            element.tabChange(ELEM.tabFilter, tabIndex);
        }
        CustomRightClick(data.id);
        };
    var navtab = new LarryTab();
    exports(module_name, function(options) {
		return navtab.set(options);
	});


var liIndex = 0;
    //绑定右键菜单
    function CustomRightClick(id) {
        //取消右键
        $(document).click(function () {
            $('.rightmenu').hide();
        });
        $('.layui-tab-title').on('contextmenu', 'li:not(:first-child)', function (e) {
            liIndex = $(this).index();
            if ($(this).hasClass("layui-this")) {
                var eLeft = e.clientX;
                var eTop = e.clientY;
                var popupmenu = $(".rightmenu");
                popupmenu.find("li").attr("data-id", id);
                popupmenu.css({left: eLeft, top: eTop}).show();
            }
            return false;
        });
    }

    $(".rightmenu li").click(function () {
        var $navTabList = $(".layui-tab-title li");
        //刷新
        if ($(this).attr("data-type") == "refush") {
            $('.layui-show > iframe')[0].src = $('.layui-show > iframe').attr("src");
        }
        //关闭当前
        if ($(this).attr("data-type") == "closethis") {
            element.tabDelete(ELEM.tabFilter, liIndex).init();
        }
        //关闭其他
        if ($(this).attr("data-type") == "closeothers") {
            for (var i = $navTabList.length-1; i > 0; i--) {
                if (i != liIndex) {
                    element.tabDelete(ELEM.tabFilter, i).init();
                }
            }
        }
        //关闭所有
        if ($(this).attr("data-type") == "closeall") {
            for (var i = $navTabList.length-1; i > 0; i--) {
                element.tabDelete(ELEM.tabFilter, i).init();
            }
        }
        $('.rightmenu').hide();
    });
});