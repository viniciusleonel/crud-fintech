<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark ">
  <a class="navbar-brand ms-5" href="#">Fintech</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="dropdown me-2">
  	<button class="btn btn-primary bg-btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    Cadastrar
	</button>
		<ul class="dropdown-menu">
		    <li><a class="dropdown-item" href="cadastro-usuario.jsp">Cadastrar Usuário</a></li>
		    <li><a class="dropdown-item" href="#">Another action</a></li>
		    <li><a class="dropdown-item" href="#">Something else here</a></li>
	    </ul>
  </div>
  
  <div class="dropdown ms-2">
  	<button class="btn btn-primary bg-btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    Listar
	</button>
		<ul class="dropdown-menu">
		    <li><a class="dropdown-item" href="usuario?acao=listar">Listar Usuários</a></li>
		    <li><a class="dropdown-item" href="#">Another action</a></li>
		    <li><a class="dropdown-item" href="#">Something else here</a></li>
	    </ul>
  </div>


  <div class="collapse navbar-collapse container-fluid d-flex justify-content-end" id="navbarSupportedContent">
    <c:if test="${empty user }">
	    
	    <form class="form-inline my-2 my-sm-1 d-flex " action="login" method="post">
    	  <input class="form-control my-2 my-sm-1 me-2" type="text" name="email" placeholder="E-mail">
	      <input class="form-control my-2 my-sm-1 me-2" type="password" name="senha" placeholder="Senha">
	      <button class="btn btn-primary bg-btn-dark my-2 my-2 my-sm-1" type="submit">Entrar</button>
	    </form>
    </c:if>
    <c:if test="${not empty user }">
    		<span class="navbar-text">
	    		${user }
	    		<a href="login" class="btn btn-outline-primary my-2 my-sm-0">Sair</a>
	  	</span>	
    </c:if>	
  </div>
</nav>



