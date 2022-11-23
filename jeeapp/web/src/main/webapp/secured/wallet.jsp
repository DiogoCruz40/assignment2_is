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
  display: block;
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
<h1>Hello <c:out value="${sessionScope.user.getEmailuser()}"/></h1>
<form action="wallet" method="post">
      <div style="padding:15px;">
       <label for"wallet">O seu saldo e: ${valorwallet}€</label><br>
       <label for="wallet"><b>Inserir valor:</b></label>
       <input type="number" step="any" min="0" placeholder="Enter value" name="wallet">
       <input type="submit" name="editwallet" value="Save Wallet" />
     </div>

    <div style="padding:15px;">
    <input type="submit" name="back" value="Back" />
    </div>
</form>
</body>
</html>