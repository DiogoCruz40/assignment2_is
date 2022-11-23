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

</style>
</head>
<body>
    <h1>Purchase Trip</h1>
    <span><c:out value="${param.departurePoint}"/></span>
    &nbsp;->&nbsp;
    <span><c:out value="${param.destinationPoint}"/></span>
    &nbsp; - &nbsp;
    <span><c:out value="${param.price}"/>&#x20AC;</span>
    </hr>
    <form action="purchase" method="post">
        <label for="place">Place:</label>
        <input type="number" id="place" name="place" min="0" max="70" required>
        <br><br>
        <input type="hidden" name="tripId" value="${param.tripId}" />
        <input type="hidden" name="price" value="${param.price}" />
        <input type="submit" value="Purchase" />
    </form>
    </hr>
    <a href="/web/secured">Go Back</a>
</body>
</html>