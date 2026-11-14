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
			<h3 class="page-header">Adicionar Aviso / Mensagem</h3>

			<form action="${pageContext.request.contextPath}/message/${action}" method="POST">
				<input type="hidden" value="${message.getId()}" name="messageId">
				<div class="row">
					<div class="form-group col-md-6">
					<label for="content">Conteúdo</label>
						<input type="text" class="form-control" id="content" name="content" 
							   autofocus="autofocus" placeholder="Conteúdo do Aviso/Mensagem" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o conteúdo do Aviso/Mensagem.')"
							   oninput="setCustomValidity('')"
							   value="${message.getContent()}">
					</div>

					<div class="form-group col-md-6">
						<label for="user">Usuário</label>
						<select id="user" class="form-control selectpicker" name="user" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty message ? "" : "selected"}>Selecione um usuário</option>
						  <c:forEach var="user" items="${users}">
						  	<option value="${user.getId()}"  ${message.getUser().getId() == user.getId() ? "selected" : ""}>
						  		${user.getName()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/messages" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty message ? "Alterar Aviso/Mensagem" : "Criar Aviso/Mensagem"}</button>
					</div>
				</div>
			</form>
		</div>

			<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>	
	</body>
</html>
