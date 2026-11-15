<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="modal.html"%>
		<%@include file="nav-menu.jsp"%>
					
		<div id="container" class="container-fluid">
			<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  ${message}
			</div>
		
	 	 	<div id="top" class="row">
	 			<div class="col-md-3">
			        <h3>Estacionamento</h3>
			    </div>
			 
			    <div class="col-md-6">
			        <div class="input-group h2">
			            <input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar Vagas / Veículos">
			            <span class="input-group-btn">
			                <button class="btn btn-danger" type="submit">
			                    <span class="glyphicon glyphicon-search"></span>
			                </button>
			            </span>
			        </div>
			    </div>
			 
			    <div class="col-md-3">
			        <a href="${pageContext.request.contextPath}/parking/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbspRegistrar Vaga</a>
			    </div>
	     	</div>
	 
	     	<hr />
	     	
	     	<div id="list" class="row">
	     		<div class="table-responsive col-md-12">
			        <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
			            <thead>
			                <tr>
			                    <th>Vaga</th>
			                    <th>Entrada</th>
			                    <th>Valor/Hora</th>
			                    <th>Status</th>
			                    <th>Veículo</th>
			                    <th>Editar</th>
			                    <th>Excluir</th>
			                 </tr>
			            </thead>
			            <tbody>
			            	<c:forEach var="parking" items="${parkings}">
								<tr>
				                    <td>${parking.getSpotNumber()}</td>
				                    <td>${parking.getEntryTime()}</td>
				                    <td>R$ ${parking.getHourlyRate()}</td>
				                    <td>
				                    	<span class="label ${parking.isOccupied() ? 'label-danger' : 'label-success'}">
				                    		${parking.isOccupied() ? 'Ocupada' : 'Livre'}
				                    	</span>
				                    </td>
				                    <td>${parking.getVehicle().getPlate()} - ${parking.getVehicle().getModel()}</td>				                    
				                    <td class="actions">
				                        <a class="btn btn-info btn-xs" 
				                           href="${pageContext.request.contextPath}/parking/update?parkingId=${parking.getId()}" >
				                           <span class="glyphicon glyphicon-edit"></span>
				                        </a>
				                    </td>
				                    <td class="actions">
				                        <a class="btn btn-danger btn-xs modal-remove" parking-id="${parking.getId()}" 
				                           parking-spot="${parking.getSpotNumber()}" data-toggle="modal" 
				                           data-target="#delete-modal" href="#"><span 
				                           class="glyphicon glyphicon-trash"></span></a>
				                    </td>
				                </tr>
							</c:forEach>
			            </tbody>
			         </table>
			     </div>
	     	</div>
	 
	     	<div id="bottom" class="row">
	     		<div class="col-md-12">
			        <ul class="pagination">
			            <li class="${currentPage == 1 ? 'disabled' : ''}">
			            	<a href="${currentPage == 1 ? '#' : pageContext.request.contextPath.concat('/parkings?page=').concat(currentPage - 1)}">&lt; Anterior</a>
			            </li>
			            
			            <c:forEach begin="1" end="${totalPages}" var="i">
			            	<li class="${currentPage == i ? 'active' : ''}">
			            		<a href="${pageContext.request.contextPath}/parkings?page=${i}">${i}</a>
			            	</li>
			            </c:forEach>
			            
			            <li class="${currentPage >= totalPages ? 'disabled' : ''}">
			            	<a href="${currentPage >= totalPages ? '#' : pageContext.request.contextPath.concat('/parkings?page=').concat(currentPage + 1)}">Próximo &gt;</a>
			            </li>
			        </ul>
			    </div>
	     	</div>
		</div>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
		    setTimeout(function() {
		        $("#alert").slideUp(500);
		    }, 3000);

		    $(".modal-remove").click(function () {
		        var parkingSpot = $(this).attr('parking-spot');
		        var parkingId = $(this).attr('parking-id');
		        $(".modal-body #hiddenValue").text("o registro da vaga '" + parkingSpot + "'");
		        $("#id").val(parkingId);
		        $("#form").attr("action", "${pageContext.request.contextPath}/parking/delete");
		    });
		});
		</script>
	</body>
</html>