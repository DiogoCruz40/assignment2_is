<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
.btn {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 4px;
  margin: 5px 0;
  opacity: 0.85;
  display: inline-block;
  font-size: 17px;
  line-height: 20px;
  text-decoration: none; /* remove underline from anchors */
}

input:hover,
.btn:hover {
  opacity: 1;
}


table {
 border-collapse: collapse;
 width: 100%;
}

td, th {
 border: 1px solid #dddddd;
 text-align: left;
 padding: 8px;
}

tr:nth-child(even) {
 background-color: #dddddd;
}

</style>
</head>
<body>
 <h1>Trip Details</h1>
 <span><c:out value="${param.departurePoint}"/></span>
 &nbsp;->&nbsp;
 <span><c:out value="${param.destinationPoint}"/></span>
 &nbsp; - &nbsp;
 <span><c:out value="${param.price}"/>&#x20AC;</span>
 <hr>
 <h1>Users</h1>
 <c:choose>
  <c:when test="${tripUsers.size() > 0}">
   <table>
    <tr>
     <th></th>
     <th>Name</th>
     <th>Email</th>
     <th>Place</th>
    </tr>
    <c:forEach var="queryResult" items="${tripUsers}" varStatus="status">
     <tr>
      <td><c:out value = "${status.index + 1}"/></td>
      <td><c:out value = "${queryResult.emailuser}"/></td>
      <td><c:out value = "${queryResult.nomeuser}"/></td>
      <td><c:out value = "${queryResult.place}"/></td>
     </tr>
    </c:forEach>
   </table>
  </c:when>
  <c:otherwise>
   No Users.
   <br />
  </c:otherwise>
 </c:choose>
 <hr>
 <form action="tripdetails" method="post">
  <input type="hidden" name="tripId" value="${param.tripId}" />
  <input type="submit" value="Delete" />
 </form>
 </hr>
 <a href="/web/secured">Go Back</a>
 </body>
</html>