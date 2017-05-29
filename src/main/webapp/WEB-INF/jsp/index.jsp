<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="option" uri="http://www.springframework.org/tags/form" %><%--
  Created by IntelliJ IDEA.
  User: SHonchar
  Date: 5/23/2017
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your deposits</title>

    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="../../resources/fonts/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../resources/css/landing/form-elements.css">
    <link rel="stylesheet" href="../../resources/css/landing/style.css">
    <link rel="stylesheet" href="http://rawgit.com/vitalets/x-editable/master/dist/bootstrap3-editable/css/bootstrap-editable.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Javascript -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/2.18.1/moment.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-backstretch/2.0.4/jquery.backstretch.min.js"></script>
    <script src="../../resources/js/scripts.js"></script>

    <!--import toastr -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/css/toastr.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/js/toastr.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <!-- combobox selector -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    <!-- (Optional) Latest compiled and minified JavaScript translation files -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/i18n/defaults-en_US.min.js"></script>
    <!-- combobox selector -->

    <!--[if lt IE 10]>
    <script src="../../resources/js/placeholder.js"></script>
    <![endif]-->

    <!-- table -->
    <script src="../../resources/js/table/ga.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.css">
    <link rel="stylesheet" href="http://rawgit.com/vitalets/x-editable/master/dist/bootstrap3-editable/css/bootstrap-editable.css">

    <!-- Favicon and touch icons -->
    <link rel="shortcut icon" href="../../resources/img/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../../resources/img/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../../resources/img/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../../resources/img/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../../resources/img/apple-touch-icon-57-precomposed.png">

</head>

<body>
<!-- Top content -->
<div class="top-content">

    <div class="container">
        <h1>Deposits:</h1>
        <div id="toolbar">
            <button id="remove" class="btn btn-danger" disabled>
                <i class="glyphicon glyphicon-remove"></i> Delete
            </button>
            <button class="btn btn-primary" data-toggle="modal" data-target="#modalNewDeposite">New</button>
        </div>
        <table id="table"
               data-toolbar="#toolbar"
               data-search="true"
               data-show-refresh="true"
               data-show-toggle="true"
               data-show-columns="true"
               data-show-export="false"
               data-detail-view="true"
               data-detail-formatter="detailFormatter"
               data-minimum-count-columns="2"
               data-show-pagination-switch="false"
               data-pagination="true"
               data-id-field="id"
               data-page-list="[10, 25, 50, 100, ALL]"
               data-show-footer="false"
               data-side-pagination="server"
               data-url="/deposit/get"
               data-response-handler="responseHandler">
        </table>
    </div>

    <div id="modalNewDeposite" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">To create new deposit please fill in next fields:</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group"><input id="create_new_deposite_name" type="text" class="form-control" value="name"></div>
                    <div class="form-group">
                        <div class="row">
                            <div class='col-sm-6'>
                                <div class="form-group">
                                    <div class='input-group date' id='picker_date_start'>
                                        <input type='text' class="form-control" id="create_new_deposite_date_start"/>
                                        <div class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class='col-sm-6'>
                                <div class="form-group">
                                    <div class='input-group date' id='picker_date_finish'>
                                        <input type='text' class="form-control" id="create_new_deposite_date_finish"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input id="create_new_deposite_start_sum" type="text" class="form-control" value="start_sum">
                    </div>
                    <div class="combobox">
                        <div class="form-group">
                            <select class="selectpicker" data-live-search="true" id="create_new_deposite_currency">
                                <c:forEach items="${currencies}" var="currency">
                                    <option>${currency.name}</option>
                                </c:forEach>
                            </select>
                            <!-- <input id="create_new_deposite_currency" type="text" class="form-control" value="currency"> -->
                        </div>
                    </div>
                    <div class="form-group">
                        <input id="create_new_deposite_rate" type="text" class="form-control" value="rate">
                        <input id="create_new_deposite_tax_on_percent" type="text" class="form-control" value="tax_on_percent">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-danger" data-dismiss="modal">Cancel</button>
                    <button class="btn btn-success" data-dismiss="modal" onclick='createNewDeposit($("input#create_new_deposite_name").val(),$("input#create_new_deposite_date_start").val(), $("input#create_new_deposite_date_finish").val(),$("input#create_new_deposite_start_sum").val(),$("#create_new_deposite_currency option:selected").text(),$("input#create_new_deposite_rate").val(),$("input#create_new_deposite_tax_on_percent").val())'>Create</button>
                </div>
            </div>
        </div>
    </div>
</div>




</body>

<script type="text/javascript">
    var toastrOptions_success =
        { closeButton: true
        , showMethod: 'fadeIn'
        , timeOut: 1000
        , extendedTimeOut: 1000
        , closeMethod: 'fadeOut'
        , closeDuration: 1000
        , positionClass: 'toast-bottom-right'
        }
      , toastrOptions_failed =
        { closeButton: true
        , showMethod: 'fadeIn'
        , timeOut: 2000
        , extendedTimeOut: 2000
        , closeMethod: 'fadeOut'
        , closeDuration: 2000
        , positionClass: 'toast-bottom-right'
        }
      , currenciesForField = []
        ;
    function createNewDeposit(name, date_start, date_finish, start_sum, currency, rate, tax_on_percent) {
        $.ajax(
            { type: "GET"
                , url: '/deposit/new'
                , data:
            {  name : name
            , date_start: date_start
            , date_finish: date_finish
            , sum: start_sum
            , rate: rate
            , currency: currency
            , tax_on_percent: tax_on_percent
            }
                , success:
                    function() {
                        toastr.options = toastrOptions_success;
                        toastr.success('Deposit succefuly created');
                        $('#table').bootstrapTable('refresh');
                    }
                , error:
                    function(xhr) {
                        toastr.options = toastrOptions_failed;
                        toastr.error(JSON.parse(xhr.responseText).message);
                    }
                , complete:
                    function() {
                    }
            }
        )
    }
    $(function(){
        $('#picker_date_start').datetimepicker({
            format: 'DD/MM/YY',
        });
    });
    $(function(){
        $('#picker_date_finish').datetimepicker({
            format: 'DD/MM/YY'
        });
    });

    var $table = $('#table'),
        $remove = $('#remove'),
        selections = [];
    function initTable() {
        $table.bootstrapTable({
            height: getHeight(),
            columns:
              [ [ { field: 'state'
                  , checkbox: true
                  , rowspan: 2
                  , align: 'center'
                  , valign: 'middle'
                  }
                , { title: 'Deposit id'
                  , field: 'id'
                  , rowspan: 2
                  , align: 'center'
                  , valign: 'middle'
                  , sortable: false
                  , footerFormatter: totalTextFormatter
                  , visible: false
                  }
                , { title: 'Deposit name'
                  , field: 'name'
                  , rowspan: 2
                  , align: 'center'
                  , valign: 'middle'
                  , sortable: true
                  , footerFormatter: totalTextFormatter
                  , editable:
                    { type: 'text'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    }
                  }
                , { title: 'Deposit detail'
                  , colspan: 7
                  , align: 'center'
                  }
                ]
              , [ { field: 'sum'
                  , title: 'Start sum'
                  , sortable: true
                  , editable:
                    { type: 'text'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    }
                  , footerFormatter: totalNameFormatter
                  , align: 'center'
                  }
                , { field: 'currency.name'
                  , title: 'Currency'
                  , sortable: true
                  , align: 'center'
                  , editable:
                    { type: 'select'
                    , source: currenciesForField
                    , title: 'Currency'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    , validate:
                      function (value) {
                          value = $.trim(value);
                          if (!value) {
                              return 'This field is required';
                          }
                          return '';
                        }
                    }
                  , footerFormatter: totalPriceFormatter
                  }
                , { field: 'startDate'
                  , title: 'Start date'
                  , sortable: true
                  , footerFormatter: totalNameFormatter
                  , align: 'center'
                  , editable:
                    { type: 'text'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    }
                  }
                , { field: 'endDate'
                  , title: 'End date'
                  , sortable: true
                  , footerFormatter: totalNameFormatter
                  , align: 'center'
                  , editable:
                    { type: 'text'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    }
                  }
                , { field: 'depositeRate'
                  , title: 'Rate, %'
                  , sortable: true
                  , footerFormatter: totalNameFormatter
                  , align: 'center'
                  , editable:
                    { type: 'text'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    }
                  }
                , { field: 'taxOnPercents'
                  , title: 'Tax on percents, %'
                  , sortable: true
                  , footerFormatter: totalNameFormatter
                  , align: 'center'
                  , editable:
                    { type: 'text'
                    , url: 'deposit/update'
                    , ajaxOptions:
                      { method: 'get'
                      }
                    }
                  }
                , { field: 'operate'
                  , title: 'Item Operate'
                  , align: 'center'
                  , events: operateEvents
                  , formatter: operateFormatter
                  }
                ]
              ]
        });
        // sometimes footer render error.
        setTimeout(function () {
            $table.bootstrapTable('resetView');
        }, 200);
        $table.on('check.bs.table uncheck.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
            // save your data, here just save the current page
            selections = getIdSelections();
            // push or splice the selections if you want to save all data selections
        });
        $table.on('expand-row.bs.table', function (e, index, row, $detail) {
        });
        $table.on('all.bs.table', function (e, name, args) {
            console.log(name, args);
        });
        $remove.click(function () {
            var ids = getIdSelections();
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids
            });
            $remove.prop('disabled', true);
        });
        $(window).resize(function () {
            $table.bootstrapTable('resetView', {
                height: getHeight()
            });
        });
    }
    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
    function responseHandler(res) {
        $.each(res.rows, function (i, row) {
            row.state = $.inArray(row.id, selections) !== -1;
        });
        return res;
    }
    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }
    function operateFormatter(value, row, index) {
        return [
            '<a class="like" href="javascript:void(0)" title="Like">',
            '<i class="glyphicon glyphicon-heart"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like action, row: ' + JSON.stringify(row));
        },
        'click .remove': function (e, value, row, index) {
            $table.bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
            });
        }
    };
    function totalTextFormatter(data) {
        return 'Total';
    }
    function totalNameFormatter(data) {
        return data.length;
    }
    function totalPriceFormatter(data) {
        var total = 0;
        $.each(data, function (i, row) {
            total += +(row.price.substring(1));
        });
        return '$' + total;
    }
    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
    $(function () {
        var scripts = [
                location.search.substring(1) || 'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.js',
                'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/extensions/export/bootstrap-table-export.js',
                'http://rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js',
                'https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/extensions/editable/bootstrap-table-editable.js',
                'http://rawgit.com/vitalets/x-editable/master/dist/bootstrap3-editable/js/bootstrap-editable.js'
            ],
            eachSeries = function (arr, iterator, callback) {
                callback = callback || function () {};
                if (!arr.length) {
                    return callback();
                }
                var completed = 0;
                var iterate = function () {
                    iterator(arr[completed], function (err) {
                        if (err) {
                            callback(err);
                            callback = function () {};
                        }
                        else {
                            completed += 1;
                            if (completed >= arr.length) {
                                callback(null);
                            }
                            else {
                                iterate();
                            }
                        }
                    });
                };
                iterate();
            };
        eachSeries(scripts, getScript, initTable);
    });
    function getScript(url, callback) {
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.src = url;
        var done = false;
        // Attach handlers for all browsers
        script.onload = script.onreadystatechange = function() {
            if (!done && (!this.readyState ||
                this.readyState == 'loaded' || this.readyState == 'complete')) {
                done = true;
                if (callback)
                    callback();
                // Handle memory leak in IE
                script.onload = script.onreadystatechange = null;
            }
        };
        head.appendChild(script);
        // We handle everything using the script element injection
        return undefined;
    }

    $(function addCurrencies() {
        <c:forEach items="${currencies}" var="currency">
            currenciesForField.push({value:'${currency.name}',text:'${currency.name}'});
        </c:forEach>
    })
</script>
</html>