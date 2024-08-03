<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Receitas</title>
<%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container">
	<h1 class="mt-2">Cadastrar Receita</h1>
	<c:if test="${not empty msg }">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${not empty erro }">
		<div class="alert alert-danger">${erro}</div>
	</c:if>
	<form action="receita" method="post">
		<input type="hidden" value="cadastrar" name="acao">
		<div class="form-group mt-1 pt-1">
			<label for="id-valor">Valor</label>
			<input type="text" name="valor" id="id-valor" class="form-control">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-data">Data</label>
			<input type="text" name="data" id="id-data" class="form-control">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-categoria">Categoria</label>
			<input type="text" name="categoria" id="id-categoria" class="form-control">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-descricao">Descrição</label>
			<input type="text" name="descricao" id="id-descricao" class="form-control" >
		</div>
		<input type="submit" value="Salvar" class="btn btn-primary mt-2">

	</form>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>