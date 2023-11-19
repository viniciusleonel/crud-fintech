<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fintech</title>
    <%@ include file="header.jsp" %>

    <!-- Favicon  -->
    <link rel="apple-touch-icon" sizes="180x180" href="resources/img/favicon_io/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="resources/img/favicon_io/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="resources/img/favicon_io/favicon-16x16.png">

    <!-- Fonte  -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zen+Antique&display=swap" rel="stylesheet">

    <!-- BS  -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">

	<!-- Minha CSS -->
	<link rel="stylesheet" href="resources/css/style.css">
</head>

<body class="bg-img ">
    <section class="container">
        <div class="container d-flex flex-column ">
            <div class="container my-5">
                <h1 class="col-12 d-flex justify-content-center fs-1">Fintech</h1>
            </div>
			<c:if test="${not empty msg }">
				<div class="d-flex justify-content-center">${msg}</div>
			</c:if>
			<c:if test="${empty user }">
			    <span class="navbar-text text-danger ms-3 d-flex justify-content-center" style="margin-right:10px" >
			        ${erro }
			  	</span>	   
	   		</c:if>
            <div class="container my-3 position-absolute top-50 start-50 translate-middle">
                <h3 class="justify-content-start col-6 fs-3 px-4 pb-5">Tecnologia e finanças finalmente JUNTOS</h3>
            </div>
            <div
                class="d-grid gap-2 col-12 justify-content-center my-5 position-absolute bottom-0 start-50 translate-middle-x">

                <button type="button" class="btn btn-primary bg-btn-dark" data-bs-toggle="modal"
                    data-bs-target="#logar">
                    Já tenho uma
                    conta
                </button>

                <button type="button" class="btn btn-primary bg-btn-blue" data-bs-toggle="modal"
                    data-bs-target="#abrirconta">
                    Abrir minha
                    conta
                </button>
            </div>

            <!-- Modal logar-->
            <div class="modal fade " id="logar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog ">
                    <div class="modal-content bg-img container">
                        <div class="modal-header">
                            <h1 class="modal-title fs-2" id="exampleModalLabel">Fintech</h1>
							 
                            <button type="button" class="btn-close form-area" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                        </div>
                        <div class="modal-body ">
							
                            <form action="login" method="post">
								<!--<input type="hidden" value="listar" name="acao">  -->
                                
							   
                                <div class="mb-3">
                                    <h3 class="my-5 fs-3">Bem-vindo de volta!</h3>
                                    
										
                                    <label for="id-email" class="form-label fs-4">Email</label>
                                    <input type="text" class="form-control form-area" name="email" id="id-email"
                                        placeholder="teste@fiap.com" required>
                                </div>

                                <div class="mb-3">
                                    <label for="id-senha" class="form-label fs-4">Senha</label>
                                    <input type="password" class="form-control form-area" name="senha" id="id-senha"
                                        placeholder="123456" required>
                                </div>
								<c:if test="${empty user }">
								    <span class="navbar-text text-danger d-flex" style="margin-right:10px" >
								        ${erro }
								  	</span>
								</c:if>
								
                                <div class="container d-flex justify-content-center my-5">
                                    <button type="submit" class="btn btn-primary bg-btn-blue col-6 btn-logar">
                                        Entrar
                                    </button>
                                </div>
                            </form>
                            
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Abrir Conta  -->
            <div class="modal fade " id="abrirconta" tabindex="-1" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog ">
                    <div class="modal-content bg-img container">
                        <div class="modal-header">
                            <h1 class="modal-title fs-2" id="exampleModalLabel">Fintech</h1>

                            <button type="button" class="btn-close form-area" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                        </div>

                        <div class="modal-body ">

                            <form action="usuario" method="post">
								<input type="hidden" value="cadastrar" name="acao">
								<c:if test="${not empty erro }">
									<span class="navbar-text text-danger d-flex" style="margin-right:10px" >
								        ${erro }
								  	</span>
								</c:if>
                                <div class="my-3">
                                    <label for="id-nome" class="form-label fs-4">Nome</label>
                                    <input type="text" class="form-control form-area" name="nome" id="id-nome"
                                        placeholder="Fiap" required>
                                </div>

                                <div class="mb-3">
                                    <label for="id-cpf" class="form-label fs-4">CPF</label>
                                    <input type="text" class="form-control form-area" name="cpf" id="id-cpf"
                                        placeholder="12345678910" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="id-login" class="form-label fs-4">Login</label>
                                    <input type="text" class="form-control form-area" name="login" id="id-login"
                                        placeholder="teste.fiap" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="id-email" class="form-label fs-4">Email</label>
                                    <input type="text" class="form-control form-area" name="email" id="id-email"
                                        placeholder="teste@fiap.com" required>
                                </div>

                                <div class="mb-3">
                                    <label for="id-conta" class="form-label fs-4">Senha</label>
                                    <input type="password" class="form-control form-area" name="senha" id="id-conta"
                                        placeholder="123456" minlength="4" maxlength="8" required>
                                </div>

                                <div class="container d-flex justify-content-center my-5">
                                    <button type="submit" class="btn btn-primary bg-btn-blue col-6 btn-logar">
                                        Criar Conta
                                    </button>
                                </div>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
            </div>
    </section>
    <%@ include file="footer.jsp" %>
</body>

</html>