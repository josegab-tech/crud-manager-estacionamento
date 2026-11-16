<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
		<style>
			body {
				background-color: #f5f5f5;
			}
			.login-container {
				max-width: 400px;
				margin: 100px auto;
				padding: 30px;
				background: #ffffff;
				border-radius: 6px;
				box-shadow: 0 2px 10px rgba(0,0,0,0.1);
			}
			<style>
			body {
				background-color: #f5f5f5;
			}
			.login-container {
				max-width: 400px;
				margin: 100px auto;
				padding: 30px;
				background: #ffffff;
				border-radius: 6px;
				box-shadow: 0 2px 10px rgba(0,0,0,0.1);
			}
			.login-container .alert {
				position: relative !important;
				top: auto !important;
				left: auto !important;
				right: auto !important;
				width: 100% !important;
				margin-bottom: 20px;
			}
		</style>
		</style>
	</head>
	<body>
		<div class="container">
			<div class="login-container">
				<div class="text-center" style="margin-bottom: 25px;">
					<img height="45px" width="45px" alt="Logo" src="${pageContext.request.contextPath}/images/logo.png">
					<h3><strong>CRUD Manager</strong></h3>
					<p class="text-muted">Acesse sua conta para continuar</p>
				</div>

				<c:if test="${not empty message}">
					<div class="alert alert-danger alert-dismissable">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						${message}
					</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/login" method="POST">
					<div class="form-group">
						<label for="mail">E-mail</label>
						<input type="email" class="form-control" id="mail" name="mail"
							   placeholder="Digite seu e-mail" autofocus required
							   oninvalid="this.setCustomValidity('Por favor, informe seu e-mail.')"
							   oninput="setCustomValidity('')">
					</div>

					<div class="form-group">
						<label for="password">Senha</label>
						<input type="password" class="form-control" id="password" name="password"
							   placeholder="Digite sua senha" required
							   oninvalid="this.setCustomValidity('Por favor, informe sua senha.')"
							   oninput="setCustomValidity('')">
					</div>

					<button type="submit" class="btn btn-danger btn-block" style="margin-top: 20px;">
						<span class="glyphicon glyphicon-log-in"></span>&nbsp;Entrar
					</button>
				</form>
			</div>
		</div>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>