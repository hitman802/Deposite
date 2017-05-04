<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

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

    <title>Admin - edit user</title>

    <body>
        <h1>Edit user:</h1>

        <form:form method="POST" modelAttribute="userForm" action="admin/user/update">
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
                        <tr>
                            <td>
                                <div class="form-group">
                                    <form:input type="text" class="form-control" path="id" disabled="true"/>
                                </div>
                            </td>
                            <td>
                                <div class="form-group">
                                    <form:input type="text" class="form-control" path="name"/>
                                </div>
                            </td>
                            <td>
                                <div class="form-group">
                                    <form:input type="text" class="form-control" path="password" disabled="true"/>
                                </div>
                            </td>
                            <td>
                                <div class="form-group">
                                    <form:input type="text" class="form-control" path="email"/>
                                </div>
                            </td>
                            <td>
                                <div class="form-group">
                                    <c:forEach items="${allRoles}" var="role">
                                        <c:set var="roleFromDB" value="${fn:toUpperCase(fn:substring(role.name,5,6))}${fn:toLowerCase(fn:substring(role.name,6,fn:length(role.name)))}"
                                        />
                                        <div class="checkbox">
                                            <label><input type="checkbox" value="${roleFromDB}" >${roleFromDB}</label>
                                        </div>
                                    </c:forEach>

                                    <!-- <form:input type="text" class="form-control" path="roles"/> -->
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </form:form>

    </body>
</html>
<function>
    <name>contains</name>
    <function-signature>boolean contains(java.util.List,java.lang.Object)
    </function-signature>
</function>