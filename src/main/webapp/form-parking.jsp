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
			<h3 class="page-header">${not empty parking ? "Editar Registro de Vaga" : "Registrar Vaga"}</h3>

			<form action="${pageContext.request.contextPath}/parking/${action}" method="POST">
				<input type="hidden" value="${parking.getId()}" name="parkingId">
				
				<div class="row">
					<div class="form-group col-md-4">
						<label for="spotNumber">Número da Vaga</label>
						<input type="number" class="form-control" id="spotNumber" name="spotNumber" 
							   autofocus="autofocus" placeholder="Ex: 101" min="1"
							   required oninvalid="this.setCustomValidity('Por favor, informe o número da vaga.')"
							   oninput="setCustomValidity('')"
							   value="${parking.getSpotNumber()}">
					</div>

					<div class="form-group col-md-4">
						<label for="entryTime">Data e Hora de Entrada</label>
						<input type="datetime-local" class="form-control" id="entryTime" name="entryTime" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a data e hora de entrada.')"
							   oninput="setCustomValidity('')"
							   value="${parking.getEntryTime()}">
					</div>

					<div class="form-group col-md-4">
						<label for="hourlyRate">Valor por Hora (R$)</label>
						<input type="number" step="0.01" min="0" class="form-control" id="hourlyRate" name="hourlyRate" 
							   placeholder="Ex: 12.50" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o valor por hora.')"
							   oninput="setCustomValidity('')"
							   value="${parking.getHourlyRate()}">
					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-6">
						<label for="isOccupied">Status da Vaga</label>
						<select id="isOccupied" class="form-control" name="isOccupied" 
							    required oninvalid="this.setCustomValidity('Por favor, selecione o status da vaga.')"
							    oninput="setCustomValidity('')">
							<option value="true" ${empty parking or parking.isOccupied() ? "selected" : ""}>Ocupada</option>
							<option value="false" ${not empty parking and !parking.isOccupied() ? "selected" : ""}>Livre</option>
						</select>
					</div>

					<div class="form-group col-md-6">
						<label for="vehicle">Veículo</label>
						<select id="vehicle" class="form-control selectpicker" name="vehicle" 
							    required oninvalid="this.setCustomValidity('Por favor, selecione o veículo.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty parking ? "" : "selected"}>Selecione um veículo</option>
						  <c:forEach var="vehicle" items="${vehicles}">
						  	<option value="${vehicle.getId()}" ${parking.getVehicle().getId() == vehicle.getId() ? "selected" : ""}>
						  		${vehicle.getPlate()} - ${vehicle.getModel()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>

				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/parkings" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty parking ? "Alterar Registro" : "Salvar Registro"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>