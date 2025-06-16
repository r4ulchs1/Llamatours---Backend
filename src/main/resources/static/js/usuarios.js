let usuarios = JSON.parse(localStorage.getItem("usuarios")) || [];

const registroForm = document.getElementById("registroForm");
const loginForm = document.getElementById("loginForm");

const btnIngresar = document.getElementById("btnIngresar");

document.addEventListener("DOMContentLoaded", () => {
    const usuario = usuarioConectado();
    if (usuario) {
        renderizarUsuarioLogueado(usuario);
    }
});

registroForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const nombre = document.getElementById("usrName").value.trim();
    const gmail = document.getElementById("usrGmail").value.trim();
    const celular = document.getElementById("usrPhone").value.trim();
    const contrasenia = document.getElementById("usrContrasenia").value.trim();
    const usuarioExiste = usuarios.some(user => user.gmail === gmail);
    if (usuarioExiste) {
        alert("Este correo ya se encuentra registrado");
        return;
    }
    usuarios.push({ nombre, gmail, celular, contrasenia });
    localStorage.setItem("usuarios", JSON.stringify(usuarios));
    alert("Usuario Registrado Exitosamente");
});

loginForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const gmail = document.getElementById("loginGmail").value.trim();
    const contrasenia = document.getElementById("loginContrasenia").value.trim();
    let usuario = usuarios.find(user => user.gmail === gmail && user.contrasenia === contrasenia);

    if (usuario) {
        alert("Bienvenido " + usuario.nombre);
        localStorage.setItem("loggedInUser", JSON.stringify(usuario));
        renderizarUsuarioLogueado(usuario);
    } else {
        alert("Correo o contraseña incorrectos");
    }
});

function renderizarUsuarioLogueado(usuario) {
    const container = document.querySelector('.container');
    btnIngresar.style.display = "none";

    const cerrarsesion = document.createElement('li');
    cerrarsesion.innerHTML = `
        <a class="nav-link dropdown-toggle gap-0" role="button" data-bs-toggle="dropdown" aria-expanded="false" href="#">
            <div class="d-inline-flex p-0 align-items-center">
                <img src="img/loginAndRegister/avatar-usuario.png" style="width: 50px; height: 50px;">
                <div class="d-inline-flex flex-column p-2">
                    <p class="mb-0">${usuario.nombre}</p>
                    <p class="mb-0">${usuario.gmail}</p>
                </div>
            </div>
        </a>
        <ul class="dropdown-menu bg-dark p-0 w-100">                            
            <button id="btnIngresar" type="button" class="btn btn-warning w-100" onclick="cerrarSesion()">Cerrar Sesión</button>
        </ul>
    `;
    document.getElementById('menuu').replaceChild(cerrarsesion, document.getElementById('change'));
}

function cerrarSesion() {
    localStorage.removeItem("loggedInUser");
    alert("Sesión Cerrada");
    btnIngresar.style.display = "block";
    location.reload();
}

function usuarioConectado() {
    return JSON.parse(localStorage.getItem("loggedInUser")) || null;
}
