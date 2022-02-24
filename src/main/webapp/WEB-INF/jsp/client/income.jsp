<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<head>
    <title><fmt:message key="jsp.header.income"/></title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <script src="../../../script/http_code.jquery.com_jquery-1.11.3.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../script/http_cdn.datatables.net_1.11.3_css_dataTables.bootstrap4.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf8" src="../../../script/http_cdn.datatables.net_1.11.3_js_dataTables.bootstrap4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#table').DataTable( {
                "pagingType": "full_numbers"
            } );
        } );
    </script>
</head>
<body class="background-gradient">
<c:set var="activeIncome" value="active" scope="page" />
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<br>
<div id="main-container" class="container-fluid p5percent">
    <c:if test="${sorting == 'date'}">
        <c:set var="selectedDate" value="selected" scope="page"/>
    </c:if>
    <c:if test="${sorting == 'number'}">
        <c:set var="selectedNumber" value="selected" scope="page"/>
    </c:if>
    <c:if test="${order == 'ascending'}">
        <c:set var="selectedAscending" value="selected" scope="page"/>
    </c:if>
    <c:if test="${order == 'descending'}">
        <c:set var="selectedDescending" value="selected" scope="page"/>
    </c:if>
    <form id="sorting_payments" action="controller" method="post">
        <input type="hidden" name="command" value="showIncome"/>
        <label>
            <select name="sorting" class="form-control bg-transparent shadow-lg text-white">
                <option class="bg-dark" ${selectedDate} value="date"><fmt:message key="jsp.sorting.date"/></option>
                <option class="bg-dark" ${selectedNumber} value="number"><fmt:message key="jsp.number"/></option>
            </select>
        </label>
        <label>
            <select name="order" class="form-control bg-transparent shadow-lg text-white">
                <option class="bg-dark" ${selectedAscending} value="ascending"><fmt:message key="jsp.sorting.ascending"/></option>
                <option class="bg-dark" ${selectedDescending} value="descending"><fmt:message key="jsp.sorting.descending"/></option>
            </select>
        </label>
        <input type="hidden" name="filter" value="all">
        <input class="btn btn-info shadow-lg" type="submit" value="<fmt:message key="jsp.sort"/>">
    </form>
    <div class="table-responsive">
        <table id="table" class="table table-striped table-hover table-dark">
            <thead class="thead-light">
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="jsp.card.number"/></th>
                <th scope="col"><fmt:message key="jsp.sender"/></th>
                <th scope="col"><fmt:message key="jsp.number.payments"/></th>
                <th scope="col"><fmt:message key="jsp.date"/></th>
                <th scope="col"><fmt:message key="jsp.money"/> UAH</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="k" value="0"/>
            <c:forEach var="payment" items="${payments}">
                <c:set var="k" value="${k+1}"/>
                <tr>
                    <th scope="row"><c:out value="${k}"/></th>
                    <td>${payment.cardDestinationNumber}</td>
                    <td><c:if test="${payment.cardId != null && payment.cardId != 0}">
                        ${payment.cardNumber}</c:if>
                        <c:if test="${payment.cardId == null || payment.cardId == 0}"><fmt:message
                                key="jsp.refill"/></c:if>
                    </td>
                    <td>${payment.id}</td>
                    <td>${payment.date}</td>
                    <td>${payment.money}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
