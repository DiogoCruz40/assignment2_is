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
<form action="editinfo" method="post">
      <div style="padding:15px;">

       <label for="Nome"><b>Novo nome:</b></label>
       <input type="text" placeholder="Enter new Name" name="nome" id="nome">
       <input type="submit" name="editname" value="Save Name" />
     </div>
      <span class="error">${messages.nome}</span>
     <div style="padding:15px;">

      <span class="error">${messages.password_repeat}</span>
        <label for="psw-repeat"><b>Old Password:</b></label>
        <input type="password" placeholder="Old Password" name="psw-repeat" id="psw-repeat">
           <br>

        <label for="psw"><b>New Password:</b></label>
        <input type="password" placeholder="New Password" name="psw" id="psw" >
        <input  type="submit" name="editpass" value="Save Pass" />

    </div>
    <span class="error">${messages.password}</span>
    <div style="padding:15px;">
    <input type="submit" name="back" value="Back" />
    </div>
</form>
</body>
</html>