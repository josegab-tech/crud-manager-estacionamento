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
			<h3 class="page-header">${not empty vehicle ? "Editar Veículo" : "Adicionar Veículo"}</h3>

			<form action="${pageContext.request.contextPath}/vehicle/${action}" method="POST">
				<input type="hidden" value="${vehicle.getId()}" name="vehicleId">
				
				<div class="row">
					<div class="form-group col-md-4">
						<label for="plate">Placa</label>
						<input type="text" class="form-control" id="plate" name="plate" 
							   autofocus="autofocus" placeholder="Ex: ABC-1234 ou ABC1D23" maxlength="8"
							   required oninvalid="this.setCustomValidity('Por favor, informe a placa do veículo.')"
							   oninput="setCustomValidity('')"
							   value="${vehicle.getPlate()}">
					</div>

					<div class="form-group col-md-4">
						<label for="model">Modelo</label>
						<input type="text" class="form-control" id="model" name="model" 
							   placeholder="Ex: Honda Civic, Fiat Uno..." 
							   required oninvalid="this.setCustomValidity('Por favor, informe o modelo do veículo.')"
							   oninput="setCustomValidity('')"
							   value="${vehicle.getModel()}">
					</div>

					<div class="form-group col-md-4">
						<label for="manufactureYear">Ano de Fabricação</label>
						<input type="number" class="form-control" id="manufactureYear" name="manufactureYear" 
							   placeholder="Ex: 2022" min="1900" max="2030"
							   required oninvalid="this.setCustomValidity('Por favor, informe o ano de fabricação.')"
							   oninput="setCustomValidity('')"
							   value="${vehicle.getManufactureYear()}">
					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-6">
						<label for="category">Categoria</label>
						<select id="category" class="form-control" name="category" 
							    required oninvalid="this.setCustomValidity('Por favor, selecione a categoria.')"
							    oninput="setCustomValidity('')">
							<option value="" disabled ${empty vehicle ? "selected" : ""}>Selecione uma categoria</option>
							<option value="Carro" ${vehicle.getCategory() eq 'Carro' ? "selected" : ""}>Carro</option>
							<option value="Moto" ${vehicle.getCategory() eq 'Moto' ? "selected" : ""}>Moto</option>
							<option value="Caminhonete" ${vehicle.getCategory() eq 'Caminhonete' ? "selected" : ""}>Caminhonete</option>
							<option value="Utilitário" ${vehicle.getCategory() eq 'Utilitário' ? "selected" : ""}>Utilitário</option>
						</select>
					</div>

					<div class="form-group col-md-6">
						<label for="user">Proprietário</label>
						<select id="user" class="form-control selectpicker" name="user" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o proprietário.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty vehicle ? "" : "selected"}>Selecione um usuário</option>
						  <c:forEach var="user" items="${users}">
						  	<option value="${user.getId()}" ${vehicle.getUser().getId() == user.getId() ? "selected" : ""}>
						  		${user.getName()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>

				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/vehicles" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty vehicle ? "Alterar Veículo" : "Cadastrar Veículo"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>