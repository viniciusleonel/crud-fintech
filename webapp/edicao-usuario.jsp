<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edição de Usuário</title>
<%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container">
	<h1 class="mt-2">Edição de Usuário</h1>
	<form action="usuario" method="post">
		<input type="hidden" value="editar" name="acao">
		<input type="hidden" value="${usuario.codigo}" name="codigo">
		<div class="form-group mt-1 pt-1">
			<label for="id-nome">Nome</label>
			<input type="text" name="nome" id="id-nome" class="form-control" value="${usuario.nome}">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-cpf">CPF</label>
			<input type="text" name="cpf" id="id-cpf" class="form-control" value="${usuario.cpf}">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-login">Login</label>
			<input type="text" name="login" id="id-login" class="form-control" value="${usuario.login}">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-email">Email</label>
			<input type="text" name="email" id="id-email" class="form-control" value="${usuario.email}">
		</div>
		<div class="form-group mt-1 pt-1">
			<label for="id-senha">Senha Criptografada</label>
			<input type="text" name="senha" id="id-senha" class="form-control" value="${usuario.senha}">
		</div>
		<input type="submit" value="Salvar" class="btn btn-primary mt-2">

	</form>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>