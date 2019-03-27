$(function () {
//  自适应echarts宽度
    $(window).resize(function() {
        cityChart.resize();
        provinceCharts.resize();
        judgeCharts.resize();
    });
    //城市横排柱形图
    var cityChart= echarts.init(document.getElementById('city-echarts'));
    var provinceCharts=echarts.init(document.getElementById('province-echarts'));
    var judgeCharts=echarts.init(document.getElementById('judge-echarts'));
    var cityoption = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '5%',
            right: '4%',
            top: '5%',
            height:"90%",
            containLabel: true
        },
        dataZoom:{
            show: true,
            yAxisIndex: 0,
            filterMode: 'empty',
            width: 15,
            height: '80%',
            showDataShadow: false,
            left: '95%'
        },
        xAxis: {
            type: 'value',
            show:false,
            boundaryGap: [0, 0.01],
            splitLine:{show: false},//去除网格线
        },
        yAxis: {
            type: 'category',
            axisLine: {show: false},//去除网格线
            axisTick: {show: false},//去除刻度线
            data: ['下关区','秦淮区','白下区','下关区','秦淮区','白下区','建邺区','玄武区','鼓楼区','玄武区','鼓楼区']
        },
        series: [
            {
                name: '2011年',
                barWidth : 10,//柱图宽度
                barMaxWidth:30,//最大宽度
                type: 'bar',
                //顶部数字展示pzr
                itemStyle: {
                    //柱形图圆角，鼠标移上去效果，如果只是一个数字则说明四个参数全部设置为那么多
                    emphasis: {
                        barBorderRadius: 30
                    },
                    normal: {
                        //柱形图圆角，初始化效果
                        barBorderRadius:[5, 5, 5, 5],
                        color:'#e8db93',
                        label: {
                            show: true,//是否展示
                            position: 'right',//对齐方式
                            textStyle: {
                                color:'#4a8ad9',
                                fontSize : '12',
                                fontFamily : '微软雅黑',
                            }
                        }
                    }
                },
                data: [12, 23, 10, 15, 20, 14, 13, 9, 8, 5, 3]
            }
        ]
    };
    //市区柱形图
    var provinceOption = {
        grid:{
            bottom:"10%",//底部距离
            height:"85%",//整体高度
            width:"85%"//整体宽度
        },
        color: ['#e8db93','#e8b68a','#eda57d','#67b5d8','#67b5d8','#6bc5df','#96d3e5','#99d9c9','#7ec2b1','#63b5a0','#3eb7c2','#6ad0d9','#a2e4ea'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            axisTick: {show: false},//去除刻度线
            // x轴的颜色和宽度
            axisLine:{
                lineStyle:{
                    color:'#4281cd',
                    width:1,   //这里是坐标轴的宽度,可以去掉
                }
            },
            data: ['南京', '无锡', '徐州', '常州', '苏州', '南通', '连云港', '淮安', '盐城', '扬州', '镇江', '泰州', '宿迁']
        },
        yAxis: {
            type: 'value',
            axisTick: {show: false},//去除刻度线
            // 控制网格线是否显示
            splitLine: {
                show: true,
                //改变轴线颜色
                lineStyle: {
                    // 使用深浅的间隔色
                    color: ['rgba(66,129,205,0.3)']
                }
            },
            // y轴的颜色和宽度
            axisLine:{
                lineStyle:{
                    color:'#4281cd',
                    width:1,   //这里是坐标轴的宽度,可以去掉
                }
            },
            boundaryGap: [0.2, 0.2]
        },
        series: [{
            // barWidth : 30,//柱图宽度
            barMaxWidth:30,//最大宽度
            //配置样式
            itemStyle: {
                //柱形图圆角，鼠标移上去效果，如果只是一个数字则说明四个参数全部设置为那么多
                emphasis: {
                    barBorderRadius: 30
                },
                normal:{
                    //柱形图圆角，初始化效果
                    barBorderRadius:[10, 10, 0, 0],
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                    color: function (params){
                        var colorList =  ['#e8db93','#e8b68a','#eda57d','#67b5d8','#67b5d8','#6bc5df','#96d3e5','#99d9c9','#7ec2b1','#63b5a0','#3eb7c2','#6ad0d9','#a2e4ea'];
                        return colorList[params.dataIndex];
                    }
                }
            },
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ],
                itemStyle :{
                    color:'#e86f6f',
                }
            },
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data: [120, 200, 150, 80, 70, 110, 130, 200, 150, 80, 70, 110, 130],
            type: 'bar'
        }]
    };
    var judgeOption = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x : '10%',
            y : '20%',
            orient: 'vertical',
            data:['检查计划','举报投诉','交办报请移送','事故调查','双随机','其他']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: false, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore : {show: false},
                saveAsImage : {show: false}
            }
        },
        calculable : true,
        series : [
            {
                name:'检查来源分析研判',
                type:'pie',
                radius : [0, 140],
                center : ['65%', '50%'],
                roseType : 'radius',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                lableLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                color: ['#d76662','#e8b68a','#7ec2b1','#e8db93','#99d9c9','#3eb7c2'],
                data:[
                    {value:10, name:'检查计划'},
                    {value:30, name:'举报投诉'},
                    {value:15, name:'交办报请移送'},
                    {value:25, name:'事故调查'},
                    {value:20, name: '双随机'},
                    {value:35, name:'其他'}
                ]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    cityChart.setOption(cityoption);
    provinceCharts.setOption(provinceOption);
    judgeCharts.setOption(judgeOption);
    //点击柱状图功能，其中param.name参数为横坐标的值
    provinceCharts.on('click', function (params) {
        console.log(params)
    });
});
var vm = new Vue({
    el:"#app",
    data:{
        tableData: [{
            date: '1',
            name: '立案审批表审核',
            address: '12',
            time:'2018-04-13',
            sender:'管理员',
            company:'无锡市'
        }, {
            date: '2',
            name: '检查方案审核',
            address: '21',
            time:'2018-04-12',
            sender:'管理员',
            company:'无锡市'
        }, {
            date: '3',
            name: '检查方案审批',
            address: '32',
            time:'2018-04-12',
            sender:'管理员',
            company:'无锡市'
        }, {
            date: '4',
            name: '检查方案审核',
            address: '12',
            time:'2018-04-12',
            sender:'管理员',
            company:'无锡市'
        },{
            date: '5',
            name: '检查方案审批',
            address: '21',
            time:'2018-04-11',
            sender:'管理员',
            company:'无锡市'
        }, {
            date: '6',
            name: '检查方案审批',
            address: '32',
            time:'2018-04-11',
            sender:'管理员',
            company:'无锡市'
        }, {
            date: '7',
            name: '检查方案审批',
            address: '12',
            time:'2018-04-10',
            sender:'管理员',
            company:'无锡市'
        }],
        tableData2: [{
            type: '移送案件',
            sum0: "91",
            sum1: "38",
            sum2: "38",
            sum3: "38",
            sum4: "64",
            sum5: "17",
            sum6: "41",
            sum7: "15",
            sum8: "17",
            sum9: "11",
            sum10: "90",
            sum11: "39",
            sum12: "93",
            sum13: "64"
        },],
        tableData22: [{
            type: '销案案件',
            sum0: "45",
            sum1: "98",
            sum2: "25",
            sum3: "35",
            sum4: "92",
            sum5: "23",
            sum6: "24",
            sum7: "33",
            sum8: "77",
            sum9: "82",
            sum10: "59",
            sum11: "15",
            sum12: "56",
            sum13: "95"
        }],
        formInline: {
            user:"",
            region:"",
            number:"",
            date1:"",
            date2:"",
            department:"",
            personnel:"",
            from:"",
            condition:""
        }
//            defaultProps: {
//                data: 'data',
//                name: 'name'
//            }
    },
    methods:{
        search:function (){//搜索
            console.log("搜索")
        },
        handleClick:function (index, row) {//处置
            console.log(index, row);
        }
    }
});