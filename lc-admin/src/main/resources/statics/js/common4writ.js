<!--pageoffice.js与jqGrid.js冲突，不能同时存在-->
var jqGridPath = contextPath + "/statics/plugins/jqgrid/jquery.jqGrid.min.js";
var jqueryPath = contextPath + "/statics/libs/jquery2.min.js";

var pageofficePath = contextPath + "/pageoffice.js";
var pageofficeJqueryPath = contextPath + "/jquery.min.js";

function openPageofficeWindow(writId,fileType,openModeType,recordDataJson,obj,fileId){

    //禁用当前按钮
    $(obj).addClass('disabled');

    //先移除pageoffice相关插件
    removejscssfile("jquery.min.js","js");//pageoffice jquery
    removejscssfile("pageoffice.js","js");//pageoffice.js
    removejscssfile("pobstyle.css","css");//pageoffice css

    if (!writId){
        writId = getSelectdRow4writPage();
        if(writId == null){
            return ;
        }
    }

    // console.log(recordDataJson);
    var recordDataId;
    //新增 recordData
    if ("word" == fileType || "WORD" == fileType) {
        $.ajax({
            url: contextPath + '/mgt/writ/ird',
            type:'POST', //GET
            async:false,    //或false,是否异步
            data:{"recordDataJson":JSON.stringify(recordDataJson)},
            timeout:5000,    //超时时间
            dataType:'json', //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend:function(xhr){
                // console.log(xhr)
                // console.log('发送前')
            },
            success:function(data,textStatus,jqXHR){
                if (textStatus == "success"){
                    recordDataId = data.rdid;
                }
                // console.log(data)
                // console.log(textStatus)
                // console.log(jqXHR)
            },
            error:function(xhr,textStatus){
                // console.log('错误')
                // console.log(xhr)
                // console.log(textStatus)
            },
            complete:function(){
                // console.log('结束')
            }
        });
    }

    var json_params = {
        'wid':writId,
        'ft':fileType,
        'omt':openModeType,
        'rdid':recordDataId,
        'fileId':fileId,
        };

    //获取选中行的信息后 才能移除 jquery2/jqGrid插件
    removejscssfile("jquery2.min.js","js");//jquery2
    removejscssfile("jquery.jqGrid.min.js","js");//jqGrid

    //此时 在加载 pageoffice相关插件 即可调起pageoffice应用程序
    loadjscssfile(pageofficePath,"js","po_js_main");//pageoffice
    loadjscssfile(pageofficeJqueryPath,"js");//pageoffice jquery

    // console.log(urlencode(JSON.stringify(json_params)));

    //1.使用pageoffice.js方法调起pageoffice应用窗口的方式，将请求后[跳转的页面]嵌入在pageoffice应用窗口中
    setTimeout(function(){
        POBrowser.openWindowModeless(
            contextPath + '/mgt/writ/openWordWindow?params=' + urlencode(urlencode(JSON.stringify(json_params))),
            'width=1200px;height=876px;'
        );
    },300);

    //注意加载顺序
    setTimeout(loadjscssfile(jqGridPath,"js"),900);
    setTimeout(loadjscssfile(jqueryPath,"js"),900);

    //解除按钮禁用状态
    setTimeout(function(){
        $(obj).removeClass("disabled");
    },2500);

    //-------------------------------------------------------------------------------------
    //2.ajax请求后端跳转到html页面 加载 方式
    // 先弹出新窗口，在执行 middle4pageoffice 请求后跳转到的页面里执行ajax获取获取后端pageoffice输出的word->html字节数组,并展示pageoffice html控件
    // window.open ("${request.contextPath}/mgt/writ/middle4pageoffice?writId=1", "newwindow",
    //     "height=800px, width=1200px, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
}

function urlencode (str) {
    str = (str + '').toString();
    return encodeURIComponent(str).replace(/!/g, '%21').replace(/'/g, '%27').replace(/\(/g, '%28').
    replace(/\)/g, '%29').replace(/\*/g, '%2A').replace(/%20/g, '+');
}

//通过全局定义的writGrid对象来获取当前表格选中的行 对应的数据id;
function getSelectdRow4writPage(){
    var rowKey = writGrid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    var selectedIDs = writGrid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    // console.log("rowKey:"+rowKey);
    console.log("selectedIDs[0]:"+selectedIDs[0]);
    return selectedIDs[0];
}

//动态加载一个js/css文件;最后一次调用此方法,则意味着 当前css/js文件 插入到指定类型标签的 最前面
function loadjscssfile(filename, filetype, id) {
    var tagrray = document.getElementsByTagName('script');
    if (filetype == "js") {
        var fileref = document.createElement('script')
        fileref.setAttribute("type", "text/javascript")
        fileref.setAttribute("src", filename)
        if (id)
            fileref.setAttribute("id", id)
    } else if (filetype == "css") {
        tagrray = document.getElementsByTagName('link');
        var fileref = document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", filename)
    }

    if (typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].insertBefore(fileref,tagrray[1]);
        // document.getElementsByTagName("head")[0].appendChild(fileref);
    }
}

//移动已经加载过的js/css
function removejscssfile(filename, filetype) {
    var targetelement = (filetype == "js") ? "script" : (filetype == "css") ? "link" : "none"
    var targetattr = (filetype == "js") ? "src" : (filetype == "css") ? "href" : "none"
    var allsuspects = document.getElementsByTagName(targetelement)
    for (var i = allsuspects.length; i >= 0; i--) {
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr) != null && allsuspects[i].getAttribute(targetattr).indexOf(filename) != -1)
            allsuspects[i].parentNode.removeChild(allsuspects[i])
    }
}