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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-backstretch/2.0.4/jquery.backstretch.min.js"></script>
    <script src="../../resources/js/scripts.js"></script>

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

    <div class="inner-bg">
        <h1>Deposits:</h1>
        <div class="btn-toolbar">
            <button class="btn btn-primary" data-toggle="modal" data-target="#modalNewDeposite">New Deposit:</button>
        </div>
        <div class="well">
            <table id="user_table" class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Start sum</th>
                    <th>Currency</th>
                    <th>Start date</th>
                    <th>End date</th>
                    <th>Rate</th>
                    <th style="width: 36px;"></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
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

</html>
<script type="text/javascript">
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
</script>