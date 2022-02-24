<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<head>
    <title><fmt:message key="jsp.cards"/></title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <script src="../../../script/http_code.jquery.com_jquery-1.11.3.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../script/http_cdn.datatables.net_1.11.3_css_dataTables.bootstrap4.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../../script/http_cdn.datatables.net_1.11.3_js_dataTables.bootstrap4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#request_table').DataTable( {
                "pagingType": "full_numbers"
            } );
        } );
    </script>
</head>
<body class="background-gradient">
<c:set var="activeRequests" value="active" scope="page"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br><br>
<c:if test="${countAdmin <= 0}">
    <p class="requests"><fmt:message key="jsp.dont_have_requests"/></p>
</c:if>
<c:if test="${countAdmin > 0}">

    <div id="container" class="container">
        <div class="table-responsive">
            <table id="request_table" class="table table-striped table-hover table-dark">
                <thead class="thead-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="jsp.idUser"/></th>
                    <th scope="col"><fmt:message key="jsp.Name"/></th>
                    <th scope="col"><fmt:message key="jsp.number"/></th>
                    <th scope="col"><fmt:message key="jsp.money"/></th>
                    <th scope="col"><fmt:message key="jsp.activity"/></th>
                    <th scope="col"><fmt:message key="jsp.unblock"/></th>
                    <th scope="col"><fmt:message key="jsp.reject"/></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="k" value="0"/>
                <c:forEach var="card" items="${cards}">
                    <c:if test="${card.activityId == 1}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                        <td><c:out value="${k}"/></td>
                        <td>${card.userId}</td>
                        <td>${card.name}</td>
                        <td>${card.number}</td>
                        <td>${card.money}</td>
                        <td>
                                <c:if test="${card.activityId == 1}"><fmt:message key="jsp.activity.blocked"/></c:if>
                                <c:if test="${card.activityId == 0}"><fmt:message key="jsp.activity.active"/></c:if>
                        <td class="content center">
                            <form id="block_user" action="controller" method="post">
                                <input type="hidden" name="command" value="acceptRequest"/>
                                <input type="hidden" name="card_id" value="${card.id}"/>
                                <input class="btn btn-success" type="submit" value="<fmt:message key="jsp.accept"/>"/>
                            </form>
                        </td>
                        <td class="content center">
                            <form id="unblock_user" action="controller" method="post">
                                <input type="hidden" name="command" value="rejectRequest"/>
                                <input type="hidden" name="card_id" value="${card.id}"/>
                                <input class="btn btn-danger" type="submit" value="<fmt:message key="jsp.reject"/>"/>
                            </form>
                        </td>
                    </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
</body>
</html>
