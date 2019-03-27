$(function () {

	//var type=$("#type").val();
    $("#jqGrid").jqGrid({

        url: baseURL + 'sys/sysdict/list',
        datatype: "json",
        colModel: [			
			{ label: '种类名称', name: 'value', index: 'value', width: 80  },
			{ label: '类型', name: 'type', index: 'type', width: 80 },
			{ label: '种类编码', name: 'code', index: 'code', width: 80 },
			{ label: '排序', name: 'orderNum', index: 'order_num', width: 80 },
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }
		],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        },
        ondblClickRow : function (rowid) {
            vm.title="查看字典信息";
            vm.dialogFormVisible= true;
            vm.getInfo(rowid);
        },
    });
});

var vm = new Vue({

	el:'#rrapp',
	data:{
        q:{
            name: null
        },
		showList: true,
		title: null,
		dict: {name:"",type:"",createUser:"",addTime:"",createUserName:""},
        formInline:{status:"",value:""},
        dialogFormVisible:false,
        elementVisible: {
            'hide_element':true
        },
        typeOptions:{},
	},
	methods: {
        cback:function(){
            $(window.parent.document).find(".layui-tab-item").eq(0).addClass("layui-show").siblings().removeClass("layui-show");
            $(window.parent.document).find("#admin-home").addClass("layui-this").siblings().removeClass("layui-this");
        },
		query: function () {
			vm.reload();
		},
		add: function(){
            vm.dict={name:"",type:"",createUser:"",addTime:"",createUserName:"",delFlag:"0"};

            if(vm.dict.type=="free_lawt"){
                vm.elementVisible.hide_element=false;
            }
			vm.dialogFormVisible=true;
			vm.showList = true;
			vm.title = "新增";
            vm.getcreator();
		},
		update: function (event) {

			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = true;
            vm.dialogFormVisible=true;
            vm.title = "修改";
            if(vm.dict.type=="free_lawt"){
                vm.elementVisible.hide_element=false;
            }
            vm.getInfo(id);
		},
        validateForm:function(title) {
            this.$message({
                message: title,
                type: 'error'
            });
        },
		saveOrUpdate: function (event) {
            if(vm.dict.code==null||vm.dict.code==""){
                this.validateForm("请设置字典码");
                return;
            }
            if(vm.dict.value==null||vm.dict.value==""){
                this.validateForm("请设置字典值");
                return;
            }
            if(vm.dict.orderNum==null||vm.dict.orderNum==""){
                this.validateForm("请设置排序值");
                return;
            }
			var url = vm.dict.id == null ? "sys/sysdict/save" : "sys/sysdict/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dict),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){

							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
            vm.dialogFormVisible=false;
		},
		del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            this.$confirm('确定要删除选中的记录？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then( function()  {
                $.ajax({
                type: "POST",
                url: baseURL + "sys/sysdict/delete",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function(r){
                    $("#jqGrid").trigger("reloadGrid");
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                }
            });

        }).catch( function()  {
                this.$message({
                type: 'info',
                message: '已取消删除'
            });
        });
        },
		getInfo: function(id){
			$.get(baseURL + "sys/sysdict/info/"+id, function(r){
                vm.dict = r.dict;
            });
		},
        resetForm:function(formName) {
            vm.$refs[formName].resetFields();
        },
        getcreator:function () {
            $.get(baseURL+"sys/sysdict/getcreator",function (result) {
                vm.dict.addTime=getNowFormatDate();
                vm.dict.createUser=result.createUser;
                vm.dict.createUserName=result.createUserName;
            })
        },
		reload: function (event) {
            vm.dialogFormVisible=false;
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                page:1,
                postData:vm.formInline
            }).trigger("reloadGrid");
		}
	},
    created: function () {

        var url = 'mgt/planexamine/getdicinfo';
        $.ajax({
            type: "POST",
            url: baseURL + url+"/free_court",
            contentType: "application/json",
            success: function(result){
                vm.typeOptions=result.data;
            }
        });
    },
});