<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Usu�rios</title>
<%@ include file="header.jsp" %>
</head>
<body>

<%@ include file="menu.jsp" %>
	<div class="container">
		<h1>Usu�rio</h1>
		<c:if test="${not empty msg }">
			<div class="alert alert-success">${msg}</div>
		</c:if>
		<c:if test="${not empty erro }">
			<div class="alert alert-danger">${erro}</div>
		</c:if>
		<table class="table table-striped">
			<tr>
				<th>Nome</th>
				<th>CPF</th>
				<th>Login</th>
				<th>Email</th>
				<th>N.� Conta</th>
				<th></th>
			</tr>
				<tr>
					<td>${usuario.nome}</td>
					<td>${usuario.cpf}</td>
					<td>${usuario.login}</td>
					<td>${usuario.email}</td>
					<td>${usuario.conta.num}</td>
					<td>
						<c:url value="usuario" var="link">
							<c:param name="acao" value="abrir-form-edicao"/>
							<c:param name="codigo" value="${usuario.codigo}"/>
						</c:url>
						<a href="${link}" class="btn btn-primary btn-xs me-1">Editar</a>
						<button type="button" class="btn btn-danger btn-xs ms-1" 
								data-bs-toggle="modal" 
								data-bs-target="#excluirModal" 
								onclick="codigoExcluir.value = ${usuario.codigo}"
								onclick="codigoExcluir.value = ${usuario.conta.codigo}">
  							Excluir
						</button>
					</td>
				</tr>
		</table>
	</div>
	
	<!-- Modal -->
<div class="modal fade" id="excluirModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Confirma��o</h5>
        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        		Deseja realmente excluir o usu�rio?
      </div>
      <div class="modal-footer">
      	<form action="usuario" method="post">
      		<input type="hidden" name="acao" value="excluir">
      		<input type="hidden" name="codigo" id="codigoExcluir">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
	        <button type="submit" class="btn btn-danger">Excluir</button>
        </form>
      </div>
    </div>
  </div>
</div>
<%@ include file="footer.jsp" %>

</body>
</html>