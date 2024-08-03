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
<%@ include file="menu.jsp" %>
    <section class="container">
        <div class="container d-flex flex-column ">
            <div class="container my-5">
                <h1 class="col-12 d-flex justify-content-center fs-1">Fintech</h1>
            </div>
			<c:if test="${not empty msg }">
				<div class="text-success d-flex justify-content-center">${msg}</div>
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

            
            </div>
    </section>
    <%@ include file="footer.jsp" %>
</body>

</html>