var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        sysBook: {},
        typeOptions: [],
        userOptions: [],
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.sysBook = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
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
                success: function (result) {
                    if (result.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "mgt/sysbook/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        if (result.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "mgt/sysbook/info/" + id, function (result) {
                vm.sysBook = result.sysBook;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    },
    created: function () {

        var url = 'sys/sysdict/getdicinfo';
        $.ajax({
            type: "POST",
            url: baseURL + url + "/kinds",
            contentType: "application/json",
            success: function (result) {
                vm.typeOptions = result.data;
            }
        });
        $.ajax({
            type: "POST",
            url: baseURL + "/sys/user/listNotAdmin",
            contentType: "application/json",
            success: function (result) {
                vm.userOptions = result.data;
            }
        });
    },
});