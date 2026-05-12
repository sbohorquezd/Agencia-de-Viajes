const API = "http://localhost:8080";

function abrirItinerarios() {
    document.getElementById("modalItinerarios").style.display = "flex";
    cargarItinerarios();
}

function cerrarItinerarios() {
    document.getElementById("modalItinerarios").style.display = "none";
}

function cerrarEditar() {
    document.getElementById("modalEditar").style.display = "none";
}

async function cargarItinerarios() {
    const idUsuario = sessionStorage.getItem("idUsuario") || 1;
    const lista = document.getElementById("listaItinerarios");
    lista.innerHTML = "<p>Cargando...</p>";

    try {
        const res = await fetch(`${API}/itinerarios?idUsuario=${idUsuario}`);
        const datos = await res.json();

        if (datos.length === 0) {
            lista.innerHTML = "<p>No tienes itinerarios guardados.</p>";
            return;
        }

        lista.innerHTML = `
            <table class="tabla-itinerarios">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Origen</th>
                        <th>Destino</th>
                        <th>Fecha Ida</th>
                        <th>Fecha Vuelta</th>
                        <th>Duración</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    ${datos.map((it, i) => `
                        <tr>
                            <td>${i + 1}</td>
                            <td>${it.aeropuertoSalida}</td>
                            <td>${it.aeropuertoLlegada}</td>
                            <td>${it.fechaInicio || "-"}</td>
                            <td>${it.fechaFin || "-"}</td>
                            <td>${it.duracionHoras} h</td>
                            <td>
                                <button class="btn-editar" onclick="abrirEditar(${JSON.stringify(it).replace(/"/g, '&quot;')})">✏️ Editar</button>
                                <button class="btn-eliminar" onclick="eliminarItinerario(${it.idItinerario})">🗑️ Eliminar</button>
                            </td>
                        </tr>
                    `).join("")}
                </tbody>
            </table>
        `;

    } catch (error) {
        lista.innerHTML = "<p>Error conectando con el servidor.</p>";
        console.error(error);
    }
}

function abrirEditar(it) {
    document.getElementById("editId").value          = it.idItinerario;
    document.getElementById("editSalida").value      = it.aeropuertoSalida;
    document.getElementById("editLlegada").value     = it.aeropuertoLlegada;
    document.getElementById("editFechaInicio").value = it.fechaInicio || "";
    document.getElementById("editFechaFin").value    = it.fechaFin || "";
    document.getElementById("editDuracion").value    = it.duracionHoras;
    document.getElementById("modalEditar").style.display = "flex";
}

async function guardarEdicion() {
    const datos = {
        idItinerario:      parseInt(document.getElementById("editId").value),
        aeropuertoSalida:  document.getElementById("editSalida").value,
        aeropuertoLlegada: document.getElementById("editLlegada").value,
        fechaInicio:       document.getElementById("editFechaInicio").value,
        fechaFin:          document.getElementById("editFechaFin").value,
        duracionHoras:     parseFloat(document.getElementById("editDuracion").value),
        idUsuario:         parseInt(sessionStorage.getItem("idUsuario") || 1)
    };

    try {
        const res = await fetch(`${API}/itinerarios/${datos.idItinerario}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        const resultado = await res.json();
        if (resultado.success) {
            cerrarEditar();
            cargarItinerarios();
            alert("Itinerario actualizado.");
        }
    } catch (error) {
        console.error(error);
        alert("Error al actualizar.");
    }
}

async function eliminarItinerario(id) {
    if (!confirm("¿Seguro que quieres eliminar este itinerario?")) return;

    try {
        const res = await fetch(`${API}/itinerarios/${id}`, {
            method: "DELETE"
        });

        const resultado = await res.json();
        if (resultado.success) {
            cargarItinerarios();
        }
    } catch (error) {
        console.error(error);
        alert("Error al eliminar.");
    }
}