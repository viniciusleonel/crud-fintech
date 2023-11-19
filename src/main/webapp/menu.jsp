<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="pt-br">
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
		    <li><a class="dropdown-item" href="cadastro-receita.jsp">Cadastrar Receita</a></li>
		    <li><a class="dropdown-item" href="cadastro-despesa.jsp">Cadastrar Despesa</a></li>
		    <li><a class="dropdown-item" href="cadastro-investimento.jsp">Cadastrar Investimento</a></li>
	    </ul>
  </div>
  
  <div class="dropdown ms-2">
  	<button class="btn btn-primary bg-btn-dark dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
	    Listar
	</button>
		<ul class="dropdown-menu">
		    <li><a class="dropdown-item" href="receita?acao=listar">Listar Receitas</a></li>
		    <li><a class="dropdown-item" href="despesa?acao=listar">Listar Despesas</a></li>
		    <li><a class="dropdown-item" href="investimento?acao=listar">Listar Investimentos</a></li>
	    </ul>
  </div>


  <div class="collapse navbar-collapse container-fluid d-flex justify-content-end" id="navbarSupportedContent">
    
    <c:if test="${not empty user }">
   		<a href="minha-conta.jsp" class="text-decoration-none me-2 " >
   			${idConta }
   			${conta }
    		${user }
    		${id}
    		${nome }
    		<a href="login" class="btn btn-outline-primary my-2 my-sm-0 text-white">Sair</a>
	  	</a>	
    </c:if>	
  </div>
</nav>



