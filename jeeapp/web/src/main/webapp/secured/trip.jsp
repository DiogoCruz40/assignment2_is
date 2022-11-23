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

</style>
</head>
<body>
<div class="container">
 <form action="secured" method="post">
  <input type="submit" name="logout" value="Logout" />
 </form>
 <form action="trip" method="POST">
   <div class="row">
    <h2 style="text-align:center">Create Trip</h2>
    <span class="error">${messages.departureDate}</span>
    <label for="departureDate">Departure Date:</label>
    <input type="datetime-local" id="departureDate" name="departureDate" required>
    <br><br>

    <span class="error">${messages.departurePoint}</span>
    <label for="departurePoint">Departure Point:</label>
    <input type="text" id="departurePoint" name="departurePoint" required>
    <br><br>

    <span class="error">${messages.destinationPoint}</span>
    <label for="destinationPoint">Destination Point:</label>
    <input type="text" id="destinationPoint" name="destinationPoint" required>
    <br><br>

    <span class="error">${messages.capacity}</span>
    <label for="capacity">Capacity:</label>
    <input type="number" id="capacity" name="capacity" min="0" required>
    <br><br>

    <span class="error">${messages.price}</span>
    <label for="price">Price:</label>
    <input type="number" id="price" name="price" min="0" step=".01" required>
    <br><br>

    <span class="error">${messages.login}</span>
    <input type="submit" value="Save">

   </div>
  </form>
  </hr>
  <a href="/web/secured">Go Back</a>
 </div>
</body>
</html>