<jsp:directive.page contentType="text/html; charset=UTF-8" />
<nav id="menu" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		    <span class="sr-only">Toggle navigation</span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		   </button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}">
		    	<span><img height="30px" width="30px" alt="PM" src="${pageContext.request.contextPath}/images/logo.png"><strong>&nbspCRUD Manager</strong></strong></span>
		    </a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home" /><strong>&nbspInício</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/users"><span class="glyphicon glyphicon-user" /><strong>&nbspUsuários</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/messages"><span class="glyphicon glyphicon-pencil" /><strong>&nbspAvisos/Mensagens</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/vehicles"><span class="glyphicon glyphicon-road" /><strong>&nbspVeículos</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/parkings"><span class="glyphicon glyphicon-map-marker" /><strong>&nbspVagas</strong></a></li>
				<li class="dropdown">
			        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
			        	<strong>${not empty usuarioLogado ? usuarioLogado.getName() : 'Perfil'}</strong>
			        	<span class="caret"></span>
			        </a>
			        <ul class="dropdown-menu">
			          <li><a href="${pageContext.request.contextPath}/user/update?userId=${usuarioLogado.getId()}"><span class="glyphicon glyphicon-cog"></span>&nbsp;Dados cadastrais</a></li>
			          <li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Sair</a></li>
			        </ul>
			    </li>
			</ul>
		</div>
	</div>
</nav>
<br /><br /><br />