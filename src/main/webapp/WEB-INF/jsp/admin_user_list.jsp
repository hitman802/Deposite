<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- import jquery-->
    <script src="http://code.jquery.com/jquery-latest.js"></script>

    <!--import toastr -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/css/toastr.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/js/toastr.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    -->
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!-- bootstrap -->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <title>Admin - user roles</title>

    <body>
        <h1>Users:</h1>

        <div class="btn-toolbar">
            <button class="btn btn-primary">New User</button>
        </div>
        <div class="well">
            <table class="table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Password</th>
                    <th>Email</th>
                    <th>Roles</th>
                    <th style="width: 36px;"></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr id="row_${user.id}">
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.password}</td>
                            <td>${user.email}</td>
                            <td>
                                <c:forEach items="${usersroles[user.name]}" var="role" varStatus="status">
                                    <c:out value="${role}"/><c:if test="${!status.last}">,</c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <a href="#" onclick=
                                        'changeUserRowForEdit(${user.id},"${user.name}","${user.password}","${user.email}","${usersroles[user.name]}","${roles}")'><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="#myModal" role="button" data-toggle="modal"><i class="glyphicon glyphicon-remove" data-book-id="my_id_value"></i></a>
                             </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">4</a></li>
                <li class="page-item"><a class="page-link" href="#">5</a></li>
                <li class="page-item"><a class="page-link" href="#">Next</a></li>
            </ul>
        </nav>
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h4 class="modal-title">Delete user</h4>
                    </div>
                    <div class="modal-body">
                        <p class="error-text">Are you sure you want to delete the user? </p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                        <button class="btn btn-danger" data-dismiss="modal" onclick="deleteUser(bookId)">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
<script type="text/javascript">
    $('#myModal').on('show.bs.modal', function(e) {

        //get data-id attribute of the clicked element
        var bookId = $(e.relatedTarget).data('book-id');

        alert("fff"+bookId)

        //populate the textbox
        $(e.currentTarget).find('input[name="bookId"]').val(bookId);
    });
    function changeUserRowForEdit(id,name,password,email,userroles,allroles) {
        var userRolesArray = userroles.replace(" ","").replace("[","").replace("]","").split(",")
          , allRolesArray = allroles.replace(" ","").replace("[","").replace("]","").split(",")
          , userRolesFormatted = userRolesArray.join(", ")
          , allRolesFormatted = allRolesArray.join(", ")
          , rolesCheckBox=""
          ;
            for( var inx in allRolesArray ) {
                var currentRole = allRolesArray[inx];
                var isChecked = userRolesArray.indexOf(currentRole)!=-1;
                rolesCheckBox += '<div class="checkbox">' +
                '<label><input id="role_'+id+'_'+currentRole+'" type="checkbox" value=""'+ (isChecked?' checked':'') +'>'+currentRole+'</label>' +
                '</div>'
            }
        ;
        var element = document.getElementById("row_"+id);
        element.outerHTML =
            '<tr id='+"row_"+id+'>'+
                '<td><div class="form-group">'+id+'</div></td>' +
                '<td><div class="form-group"><input id="name_'+id+'" type="text" class="form-control" value='+name+'></div></td>' +
                '<td><div class="form-group">'+password+'</div></td>' +
                '<td><div class="form-group"><input id="email_'+id+'" type="text" class="form-control" value='+email+'></div></td>'+
                '<td>'+rolesCheckBox+'</td>'+
                '<td>'+
                    '<a id="btn_edit_update_'+id+'" href="#" onclick=\'changeUserRowFromEditWithUpdate('+id+',"'+name+'","'+password+'","'+email+'","'+userRolesFormatted+'","'+allRolesFormatted+'")\'><i class="glyphicon glyphicon-ok"></i></a>'+
                    '<a href="#" onclick=\'changeUserRowFromEditWithCancel('+id+',"'+name+'","'+password+'","'+email+'","'+userRolesFormatted+'","'+allRolesFormatted+'")\'><i class="glyphicon glyphicon-remove"></i></a>'+
                '<td>'+
            '</tr>'
    }
    function changeUserRowFromEditWithUpdate(id,name,password,email,userroles,allroles) {
        var element = document.getElementById("row_"+id)
          , allrolesArray = allroles.split(", ")
          , curUserRolesArray = userroles.split(", ")
          , input_name = document.getElementById("name_"+id).value
          , input_email = document.getElementById("email_"+id).value
          , input_roles = []
          ;

        for( var inx in allrolesArray ) {
            var role_element = document.getElementById("role_"+id+"_"+allrolesArray[inx]);
            if( !role_element || !role_element.checked ) {
                continue;
            }
            input_roles.push(allrolesArray[inx])
        }

        var btn_update = document.getElementById('btn_edit_update_'+id);
        $('#btn_edit_update_'+id).before(('<div class="loader">'));
        var curName, curEmail, curRoles;
        $.ajax(
            { type: "GET"
            , url: '/admin/user/update'
            , data:
                { id : id
                , name : input_name
                , password: password
                , email: input_email
                , roles: input_roles
                }
            , success:
              function() {
                  curName = input_name;
                  curEmail = input_email;
                  curRoles = input_roles.join(", ");
                  toastr.options=
                  { closeButton: true
                      , showMethod: 'fadeIn'
                      , timeOut: 1000
                      , extendedTimeOut: 1000
                      , closeMethod: 'fadeOut'
                      , closeDuration: 1000
                      , positionClass: 'toast-bottom-right'
                  }
                  toastr.success('User '+ curName +' successfully changed');
              }
            , error:
              function(xhr) {
                  curName = name;
                  curEmail = email;
                  curRoles = curUserRolesArray.join(", ");
                  toastr.options=
                  { closeButton: true
                      , showMethod: 'fadeIn'
                      , timeOut: 2000
                      , extendedTimeOut: 2000
                      , closeMethod: 'fadeOut'
                      , closeDuration: 2000
                      , positionClass: 'toast-bottom-right'
                  }
                  toastr.error(JSON.parse(xhr.responseText).message);
              }
            , complete:
              function() {
                  element.outerHTML =
                      '<tr id='+"row_"+id+'>'+
                      '<td><div class="form-group">'+id+'</div></td>' +
                      '<td><div class="form-group">'+curName+'</div></td>' +
                      '<td><div class="form-group">'+password+'</div></td>' +
                      '<td><div class="form-group">'+curEmail+'</div></td>' +
                      '<td><div class="form-group">'+curRoles+'</div></td>' +
                      '<td>'+
                      '<a href="#" onclick=\'changeUserRowForEdit('+id+',"'+curName+'","'+password+'","'+curEmail+'","'+curRoles+'","'+allroles+'")\'><i class="glyphicon glyphicon-pencil"></i></a>'+
                      '<a href="#myModal" role="button" data-toggle="modal"><i class="glyphicon glyphicon-remove"></i></a>'+
                      '</td>'+
                      '</tr>'
              }
            }
        )
    }
    function changeUserRowFromEditWithCancel(id,name,password,email,userroles,allroles) {
        var element = document.getElementById("row_"+id);
        element.outerHTML =
            '<tr id='+"row_"+id+'>'+
                '<td><div class="form-group">'+id+'</div></td>' +
                '<td><div class="form-group">'+name+'</div></td>' +
                '<td><div class="form-group">'+password+'</div></td>' +
                '<td><div class="form-group">'+email+'</div></td>' +
                '<td><div class="form-group">'+userroles+'</div></td>' +
                '<td>'+
                    '<a href="#" onclick=\'changeUserRowForEdit('+id+',"'+name+'","'+password+'","'+email+'","'+userroles+'","'+allroles+'")\'><i class="glyphicon glyphicon-pencil"></i></a>'+
                    '<a href="#myModal" role="button" data-toggle="modal"><i class="glyphicon glyphicon-remove"></i></a>'+
                 '</td>'+
            '</tr>'
    }
    function deleteUser(id) {
        alert('delete called id=' + id)
    }
</script>