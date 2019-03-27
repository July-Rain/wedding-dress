$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mgt/sysbook/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '人名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '地址', name: 'userAddress', index: 'user_address', width: 80 }, 			
			{ label: '联系电话', name: 'userPhone', index: 'user_phone', width: 80 }, 			
			{ label: '预约风格', name: 'bookType', index: 'book_type', width: 80 }, 			
			{ label: '预约摄影师', name: 'bookPerson', index: 'book_person', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '预约时间', name: 'bookTime', index: 'book_time', width: 80 ,
                formatter: function (cellvalue, options, rowObject) {
                    var date = new Date(cellvalue);
                    var seperator1 = "-";
                    var seperator2 = ":";
                    var month = date.getMonth() + 1;

                    var strDate = date.getDate();
                    if (month >= 1 && month <= 9) {
                        month = "0" + month;
                    }
                    if (strDate >= 0 && strDate <= 9) {
                        strDate = "0" + strDate;
                    }

                    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
                    return currentdate;
                }
			},
            { label: '创建时间', name: 'createTime', index: "create_time", width: 85,
                formatter: function (cellvalue, options, rowObject) {
                    var date = new Date(cellvalue);
                    var seperator1 = "-";
                    var seperator2 = ":";
                    var month = date.getMonth() + 1;

                    var strDate = date.getDate();
                    if (month >= 1 && month <= 9) {
                        month = "0" + month;
                    }
                    if (strDate >= 0 && strDate <= 9) {
                        strDate = "0" + strDate;
                    }

                    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
                    return currentdate;
                }
            }
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
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysBook: {},
        typeOptions:[],
        userOptions:[],
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysBook = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysBook.id == null ? "mgt/sysbook/save" : "mgt/sysbook/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysBook),
			    success: function(result){
			    	if(result.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(result.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mgt/sysbook/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(result){
						if(result.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(result.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "mgt/sysbook/info/"+id, function(result){
                vm.sysBook = result.sysBook;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	},
    created: function () {

        var url = 'sys/sysdict/getdicinfo';
        $.ajax({
            type: "POST",
            url: baseURL + url+"/kinds",
            contentType: "application/json",
            success: function(result){
                vm.typeOptions=result.data;
            }
        });
        $.ajax({
            type: "POST",
            url: baseURL +"/sys/user/listNotAdmin",
            contentType: "application/json",
            success: function(result){
                vm.userOptions=result.data;
            }
        });
    },
});