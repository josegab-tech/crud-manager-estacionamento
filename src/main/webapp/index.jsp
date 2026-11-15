<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>
					
		<div id="container" class="container-fluid">
			<div class="jumbotron" style="margin-top: 20px;">
				<h2>Bem-vindo ao CRUD Manager</h2>
				<p>Sistema de gerenciamento integrado de Usuários, Avisos, Veículos e Vagas de Estacionamento.</p>
			</div>

			<div class="row">
				<div class="col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading"><strong><span class="glyphicon glyphicon-user"></span>&nbsp;Usuários</strong></div>
						<div class="panel-body">
							<p>Gerencie os proprietários e usuários cadastrados no sistema.</p>
							<a href="${pageContext.request.contextPath}/users" class="btn btn-danger btn-block">Acessar Usuários</a>
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading"><strong><span class="glyphicon glyphicon-pencil"></span>&nbsp;Avisos / Mensagens</strong></div>
						<div class="panel-body">
							<p>Visualize, publique e gerencie os avisos e mensagens do mural.</p>
							<a href="${pageContext.request.contextPath}/messages" class="btn btn-danger btn-block">Acessar Avisos</a>
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading"><strong><span class="glyphicon glyphicon-road"></span>&nbsp;Veículos</strong></div>
						<div class="panel-body">
							<p>Controle de placas, modelos, categorias e vínculos com proprietários.</p>
							<a href="${pageContext.request.contextPath}/vehicles" class="btn btn-danger btn-block">Acessar Veículos</a>
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading"><strong><span class="glyphicon glyphicon-map-marker"></span>&nbsp;Vagas de Estacionamento</strong></div>
						<div class="panel-body">
							<p>Monitore a ocupação, horários de entrada, tarifas e veículos estacionados.</p>
							<a href="${pageContext.request.contextPath}/parkings" class="btn btn-danger btn-block">Acessar Vagas</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>