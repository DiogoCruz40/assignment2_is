<%@ page import="dtos.UserDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

.search-form {
    display: flex;
    align-items: center;
    width: 25%;
    justify-content: space-between;
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
<%@ page session="true" %>
<jsp:useBean id="now" class="java.util.Date"/>
</head>
<body>
    <h1>Hello <c:out value="${sessionScope.user.getNomeuser()}"/></h1>
    <form action="secured" method="post">
        <input type="submit" name="logout" value="Logout" />
        <c:if test = "${!sessionScope.user.isManager()}">
            <input type="submit" name="wallet" value="Edit Wallet" />
        </c:if>
        <input type="submit" name="editinfo" value="Edit Info" />
        <input type="submit" name="accountremove" value="Remove account" />
    </form>
    <c:choose>
        <c:when test="${sessionScope.user.isManager()}">
            <form action="trip" method="get">
                <input type="submit" value="Create trip" />
            </form>
            <hr>
             <h1>Top 5 users</h1>
             <c:choose>
               <c:when test="${userstop.size() > 0}">
                <table>
                 <tr>
                  <th></th>
                  <th>Name</th>
                  <th>Email</th>
                 </tr>
                 <c:forEach var="queryResult" items="${userstop}" varStatus="status">
                  <tr>
                   <td><c:out value = "${status.index + 1}"/></td>
                   <td><c:out value = "${queryResult.emailuser}"/></td>
                   <td><c:out value = "${queryResult.nomeuser}"/></td>
                  </tr>
                 </c:forEach>
                </table>
               </c:when>
               <c:otherwise>
                No Users.
                <br />
               </c:otherwise>
              </c:choose>
        </c:when>
        <c:otherwise>
            <h1>My Trips</h1>
            <c:choose>
                <c:when test="${userTrips.size() > 0}">
                    <table>
                        <tr>
                            <th></th>
                            <th>Departure Date</th>
                            <th>Departure Point</th>
                            <th>Destination Point</th>
                            <th>Capacity</th>
                            <th>Place</th>
                            <th>Price</th>
                            <th></th>
                        </tr>
                        <c:forEach var="queryResult" items="${userTrips}" varStatus="status">
                            <tr>
                                <td><c:out value = "${status.index + 1}"/></td>
                                <td>
                                    <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${queryResult.departureDate}" />
                                </td>
                                <td><c:out value = "${queryResult.departurePoint.trim()}"/></td>
                                <td><c:out value = "${queryResult.destinationPoint.trim()}"/></td>
                                <td><c:out value = "${queryResult.capacity}"/></td>
                                <td><c:out value = "${queryResult.place}"/></td>
                                <td><fmt:formatNumber value="${queryResult.price}" minFractionDigits="2"/></td>
                                <td>
                                    <c:if test = "${queryResult.departureDate.time > now.time}">
                                        <form action="secured" method="post">
                                            <input type="hidden" name="userTripId" value="${queryResult.userTripId}" />
                                            <input type="submit" name="deleteUserTrip" value="Delete" />
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    No Results.
                    <br />
                </c:otherwise>
            </c:choose>
            <hr>
        </c:otherwise>
    </c:choose>
    <h1>Search Trips</h1>
    <form action="searchtrips" method="post" class="search-form">
        <div>
            <label for="fromDate">From</label>
            <input type="date" id="fromDate" name="fromDate" required>
        </div>
        <div>
            <label for="toDate">To</label>
            <input type="date" id="toDate" name="toDate">
        </div>
        <input type="submit" value="Search" />
    </form>
    <c:if test = "${printSearchResults}">
        <c:choose>
            <c:when test="${tripsList.size() > 0}">
                <table>
                    <tr>
                        <th></th>
                        <th>Departure Date</th>
                        <th>Departure Point</th>
                        <th>Destination Point</th>
                        <th>Capacity</th>
                        <th>Price</th>
                        <th></th>
                    </tr>
                    <c:forEach var="queryResult" items="${tripsList}" varStatus="status">
                        <tr>
                            <td><c:out value = "${status.index + 1}"/></td>
                            <td>
                                <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${queryResult.departureDate}" />
                            </td>
                            <td><c:out value = "${queryResult.departurePoint.trim()}"/></td>
                            <td><c:out value = "${queryResult.destinationPoint.trim()}"/></td>
                            <td><c:out value = "${queryResult.capacity}"/></td>
                            <td><fmt:formatNumber value="${queryResult.price}" minFractionDigits="2"/></td>
                            <td>
                                <c:if test = "${queryResult.departureDate.time > now.time}">
                                    <c:choose>
                                        <c:when test="${sessionScope.user.isManager()}">
                                            <form action="tripdetails" method="get">
                                                <input type="hidden" name="tripId" value="${queryResult.id}" />
                                                <input type="hidden" name="departurePoint" value="${queryResult.departurePoint}" />
                                                <input type="hidden" name="destinationPoint" value="${queryResult.destinationPoint}" />
                                                <input type="hidden" name="capacity" value="${queryResult.capacity}" />
                                                <input type="hidden" name="price" value="${queryResult.price}" />

                                                <input type="submit" value="Details"/>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="purchase" method="get">
                                                <input type="hidden" name="tripId" value="${queryResult.id}" />
                                                <input type="hidden" name="departurePoint" value="${queryResult.departurePoint}" />
                                                <input type="hidden" name="destinationPoint" value="${queryResult.destinationPoint}" />
                                                <input type="hidden" name="capacity" value="${queryResult.capacity}" />
                                                <input type="hidden" name="price" value="${queryResult.price}" />

                                                <input type="submit" value="Purchase" />
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                No Results.
                <br />
            </c:otherwise>
        </c:choose>
    </c:if>
</body>
</html>