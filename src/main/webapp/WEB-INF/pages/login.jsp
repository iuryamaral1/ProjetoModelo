<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">

	@CHARSET "UTF-8";
	
	#login-box {
	    position:fixed;
	    top: 50%;
	    left: 50%;
	    width:30em;
	    height:23em;
	    margin-top: -9em; /*set to a negative number 1/2 of your height*/
	    margin-left: -15em; /*set to a negative number 1/2 of your width*/
	    border: 1px solid #ccc;
	    background-color: #f3f3f3;
	    padding: 3%;
	    border-radius: 15px;
	}
	
	#login-box img {
		display: block;
		margin: 0 auto;
		padding-bottom: 3em;
	}

</style>
</head>
<body>

	<div class="container">
		
		<div>
			<c:if test="${not empty error}">
					<div class="btn btn-lg btn-warning">${error}</div>
			</c:if>
		</div>		
		<div id="login-box">
	
			<form name='loginForm'
				action="<c:url value='/login' />" method='POST'>	
				  <div class="form-group">
				    <label for="email">Username:</label>
				    <input type="text" name="username" class="form-control" id="email">
				  </div>
				  <div class="form-group">
				    <label for="pwd">Senha:</label>
				    <input name="password" type="password" class="form-control" id="pwd">
				  </div>
				  <button type="submit" class="btn btn-primary">Entrar</button>
					
			    <div>
				  <p>Cadastre-se <a href="#" data-toggle="modal" data-target="#cadastroModal">aqui</a></p>
					<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				</div>
	
			</form>
		</div>

	</div>
	
	<div id="cadastroModal" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Cadastre-se</h4>
	      </div>
	      <div class="modal-body">
	        <form name='loginForm' action="<c:url value='/clientSignUp' />" method='POST'>
				  <div class="form-group">
				    <label for="username">Nome de usuário:</label>
				    <input type="text" required="required" name="username" class="form-control" id="username">
				  </div>
				  <div class="form-group">
				    <label for="email">E-mail:</label>
				    <input type="email" required="required" name="email" class="form-control" id="email">
				  </div>
				  <div class="form-group">
				    <label for="pwd">Senha:</label>
				    <input name="password" type="password" required="required" class="form-control" id="pwd">
				  </div>
				  <div class="form-group">
				    <label for="pwd1">Confirmar senha:</label>
				    <input name="confirmPassword" type="password" required="required" class="form-control" id="pwd1">
				  </div>
				  <button type="submit" class="btn btn-primary">Salvar</button>
	
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	
	  </div>
	</div>

</body>
</html>