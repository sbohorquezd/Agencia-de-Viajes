const toggle = document.getElementById("togglePassword");
const password = document.getElementById("password");

toggle.addEventListener("click", () => {
    if (password.type === "password") {
        password.type = "text";
        toggle.classList.remove("fa-eye-slash");
        toggle.classList.add("fa-eye");
    } else {
        password.type = "password";
        toggle.classList.remove("fa-eye");
        toggle.classList.add("fa-eye-slash");
    }
});

async function iniciarSesion() {

    const correo = document.getElementById("correo").value;
    const contraseña = document.getElementById("password").value;

    const datos = {
        correo: correo,
        password: contraseña
    };

    try {
        const respuesta = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        const resultado = await respuesta.json();
        
        console.log("Respuesta login:", resultado);

        if (resultado.success) {
            sessionStorage.setItem("idUsuario", resultado.idUsuario);
            sessionStorage.setItem("nombre", resultado.nombre);
            console.log("Guardado idUsuario:", sessionStorage.getItem("idUsuario"));
            window.location.href = "index.html";
        } else {
            alert("Correo o contraseña incorrectos");
        }

    } catch (error) {
        console.error(error);
        alert("Error conectando con el servidor");
    }
}