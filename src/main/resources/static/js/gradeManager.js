var $path_base = "/";//this will be used in gritter alerts containing images
var grid_data;
var grid_selector = "#grid-table";
var flag=0;
function query(){
    var info = $("[name='info']").val();
    if(info==""){
        alert("考试Id为空");
    }else {
        $.ajax({
            type: "get",
            url: "/gradeManager/all",             //向springboot请求数据的url
            data: {"examinationId": info}, //发送登陆ID及Token
            // async:false,
            success: function (result) {
                if (result.status == 200) {
                  //  console.log(JSON.stringify(result));
                    grid_data = result.data;
                    createform();
                }
                else if (result.status == 10002) {
                    alert(result.message);
                    location = "/";
                } else {
                    alert(result.message);
                }
            }
        })
    }
}

function createform() {
    console.log(flag);
    var pager_selector = "#grid-pager";
    if(flag==0) {
        flag++;
        jQuery(grid_selector).jqGrid({
            //direction: "rtl",
            // url:$path_base+"test",
            data: grid_data,
            datatype: "local",
            mtype: "post",
            height: 450,
            colNames: ['考试编号', '姓名', '手机号',
                '区域', '分数', '考试时间'],
            colModel: [
                {name: 'examineeId', index: 'examineeId', width: 80, editable: false, key: true},
                {name: 'name', index: 'name',width: 100,sortable: true,editable: true},
                {name: 'phone',index: 'phone', width: 100, editable: true},
                {name: 'areaName', index: 'areaName', width: 100, sortable: true,editable: true},
                {name: 'grade', index: 'grade', width: 100, sortable: true, editable: true},
                {name:'examinationTime',index:'examinationTime', width:150, editable: false,
                    formatter:"date",formatoptions: {language:'zh-CN',srcformat:'u',newformat:'Y-m-d H:i:s'},
                    unformat: pickDate}],

            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: pager_selector,
            altRows: true,
            //toppager: true,

            multiselect: false,
            //multikey: "ctrlKey",
            multiboxonly: true,

            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },

            editurl: $path_base,//nothing is saved
            caption: "考试成绩  ",
            autowidth: true
        });
    }
    else{
        $(grid_selector).jqGrid('clearGridData');  //清空表格
        $(grid_selector).jqGrid('setGridParam',{  // 重新加载数据
            datatype:'local',
            data : grid_data,   //  newdata 是符合格式要求的需要重新加载的数据
        }).trigger("reloadGrid");
    }


    //switch element when editing inline
    function aceSwitch( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=checkbox]')
                .wrap('<label class="inline" />')
                .addClass('ace ace-switch ace-switch-5')
                .after('<span class="lbl"></span>');
        }, 0);
    }
    //enable datepicker
    function pickDate( cellvalue, options, cell ) {
        setTimeout(function(){
            $(cell) .find('input[type=text]')
                .datepicker({format:'yyyy-mm-dd' , autoclose:true});
        }, 0);
    }


    //navButtons
    jQuery(grid_selector).jqGrid('navGrid',pager_selector,
        { 	//navbar options
            edit: false,
            editicon : 'icon-pencil blue',
            add: false,
            addicon : 'icon-plus-sign purple',
            del: false,
            delicon : 'icon-trash red',
            search: true,
            searchicon : 'icon-search orange',
            refresh: true,
            refreshicon : 'icon-refresh green',
            view: false,
            viewicon : 'icon-zoom-in grey',
        },
        {
            //edit record form
            //closeAfterEdit: true,
            recreateForm: true,
            beforeShowForm : function(e) {
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                style_edit_form(form);
            }
        },
        {
            //new record form
            closeAfterAdd: true,
            recreateForm: true,
            viewPagerButtons: false,
            beforeShowForm : function(e) {
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                style_edit_form(form);
            },
            afterComplete:function (data,postdata) {
                alert(data.responseText);
                // location.reload(true);
            }
        },
        {
            //delete record form
            recreateForm: true,
            beforeShowForm : function(e) {
                var form = $(e[0]);
                if(form.data('styled')) return false;

                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                style_delete_form(form);
                form.data('styled', true);
            },
            onClick : function(e) {
                alert(1);
            }
        },
        {
            //search form
            recreateForm: true,
            afterShowSearch: function(e){
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                style_search_form(form);
            },
            afterRedraw: function(){
                style_search_filters($(this));
            }
            ,
            multipleSearch: true,
            /**
             multipleGroup:true,
             showQuery: true
             */
        },
        {
            //view record form
            recreateForm: true,
            beforeShowForm: function(e){
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
            }
        }
    )
}

function getAraeInfo() {
    var info = $("[name='info']").val();
    if(info==""){
        alert("考试Id为空");
    }
    else {
        localStorage.setItem('examinationId',info);
        console.log(info);
        window.open('/gradeAreaManager','gradeArea',
            'height=800,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no')
    }
}
