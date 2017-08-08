var $path_base = "/";//this will be used in gritter alerts containing images
var grid_data;
var grid_selector = "#grid-table";
var flag=0;
function query(){
    var info = $("[name='info']").val();
    $("#showquery").removeClass("displayno");
    if(info==""){
        alert("考试Id为空");
    }else {
        $.ajax({
            type: "get",
            url: "/errorQuestion",             //向springboot请求数据的url
            data: {"examinationId": info}, //发送登陆ID及Token
            // async:false,
            success: function (result) {
                if (result.status == 200) {
                    console.log(JSON.stringify(result));
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
            colNames: ['考试号', '试题号', '分值',
                '正确率'],
            colModel: [
                {name: 'examinationId', index: 'examinationId', width: 80, editable: false},
                {name: 'questionId', index: 'questionId',width: 100,sortable: true,editable: false, key: true},
                {name: 'score',index: 'score', width: 100, editable: false},
                {name: 'accuracy', index: 'accuracy', width: 100, sortable: true,editable: false,formatter:'number',formatoptions: {decimalPlaces:2}},
                ],
            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: pager_selector,
            altRows: true,
            //toppager: true,

            multiselect: false,
            //multikey: "ctrlKey",
            multiboxonly: false,

            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },

            editurl: $path_base,//nothing is saved
            caption: "正确率统计",
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

function info() {
    selr = $('#grid-table').jqGrid('getGridParam','selrow');
    if(selr==null){
        alert("请选择一场考试");
    }
    else {
        console.log(selr);
        $.ajax({
            type: "get",
            url: "/questionsManager/aQuestion",             //向springboot请求数据的url
            data: {"questionId": selr}, //发送登陆ID及Token
            // async:false,
            success: function (result) {
                if (result.status == 200) {
                    console.log(JSON.stringify(result));
                    $("#text").html(result.data.questionText);
                    $("#type").html(result.data.questionType);
                    $("#classification").html(result.data.questionClassification);
                    $("#CA").html(result.data.questionChooseA);
                    $("#CB").html(result.data.questionChooseB);
                    $("#CC").html(result.data.questionChooseC);
                    $("#CD").html(result.data.questionChooseD);
                    $("#answer").html(result.data.questionAnswer);
                    $("#mymodal").modal("toggle");
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

