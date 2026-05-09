let ciudades = [];
let aeropuertos = [];
let clickSalida = true;

document.addEventListener("DOMContentLoaded", () => {

  fetch("https://api-colombia.com/api/v1/City")
    .then(res => res.json())
    .then(data => {

      ciudades = data;

      return fetch("https://api-colombia.com/api/v1/airport");

    })
    .then(res => res.json())
    .then(data => {

      aeropuertos = data.filter(a => a.latitude && a.longitude);

      const selectSalida = document.getElementById("aeropuertos");
      const selectLlegada = document.getElementById("aeropuertol");

      aeropuertos.forEach(aeropuerto => {


        const ciudad = ciudades.find(c => c.id == aeropuerto.cityId);

        const nombreCiudad = ciudad
          ? ciudad.name
          : "Sin ciudad";

        const texto = `${aeropuerto.name} - ${nombreCiudad}`;

        // OPTION SALIDA
        const option1 = document.createElement("option");
        option1.value = aeropuerto.id;
        option1.textContent = texto;
        selectSalida.appendChild(option1);

        // OPTION LLEGADA
        const option2 = document.createElement("option");
        option2.value = aeropuerto.id;
        option2.textContent = texto;
        selectLlegada.appendChild(option2);

      });

      selectSalida.selectedIndex = -1;
      selectLlegada.selectedIndex = -1;

      // Mostrar mapa inicial
      mostrarMapa();

    })
    .catch(error => console.error(error));

  // EVENTO SALIDA
  document
    .getElementById("aeropuertos")
    .addEventListener("change", actualizarMapa);

  // EVENTO LLEGADA
  document
    .getElementById("aeropuertol")
    .addEventListener("change", actualizarMapa);

});


function actualizarMapa() {

  const idSalida =
    document.getElementById("aeropuertos").value;

  const idLlegada =
    document.getElementById("aeropuertol").value;

  const aeropuertoSalida =
    aeropuertos.find(a => a.id == idSalida);

  const aeropuertoLlegada =
    aeropuertos.find(a => a.id == idLlegada);

  if (!aeropuertoSalida || !aeropuertoLlegada) return;

  mostrarMapa(aeropuertoSalida, aeropuertoLlegada);
}

function crearRutaCurva(lat1, lon1, lat2, lon2) {

  let lats = [];
  let lons = [];

  const pasos = 50;

  for (let i = 0; i <= pasos; i++) {

    let t = i / pasos;

    let lat = lat1 + (lat2 - lat1) * t;
    let lon = lon1 + (lon2 - lon1) * t;

    // Curvatura
    lat += Math.sin(Math.PI * t) * 2;

    lats.push(lat);
    lons.push(lon);
  }

  return {
    lats,
    lons
  };
}
function limpiarMapa() {

  document.getElementById("aeropuertos").selectedIndex = -1;

  document.getElementById("aeropuertol").selectedIndex = -1;
  clickSalida = true;
  mostrarMapa();
}

function mostrarMapa() {

  const idSalida =
    document.getElementById("aeropuertos").value;

  const idLlegada =
    document.getElementById("aeropuertol").value;

  const salida =
    aeropuertos.find(a => a.id == idSalida);

  const llegada =
    aeropuertos.find(a => a.id == idLlegada);

  // TODOS LOS AEROPUERTOS
  let data = [{

    type: "scattermap",

    mode: "markers",

    lat: aeropuertos.map(a => a.longitude),

    lon: aeropuertos.map(a => a.latitude),

    text: aeropuertos.map(a => a.name),

    customdata: aeropuertos.map(a => a.id),

    marker: {
      size: 10,
      color: "#ff3b30",
      symbol:"marker"
    },

    name: "Aeropuertos"
  }];

  // SI HAY SALIDA Y LLEGADA
  if (salida && llegada) {

    const ruta = crearRutaCurva(

      parseFloat(salida.longitude),
      parseFloat(salida.latitude),

      parseFloat(llegada.longitude),
      parseFloat(llegada.latitude)
    );

    // RUTA
    data.push({

      type: "scattermap",

      mode: "lines",

      lat: ruta.lats,
      lon: ruta.lons,

      line: {
        width: 4,
        color: "blue"
      },

      name: "Ruta"
    });

    // AVION
    data.push({

      type: "scattermap",

      mode: "markers",

      lat: [
        (
          parseFloat(salida.longitude) +
          parseFloat(llegada.longitude)
        ) / 2
      ],

      lon: [
        (
          parseFloat(salida.latitude) +
          parseFloat(llegada.latitude)
        ) / 2
      ],

      marker: {
        size: 18,
        color: "black",
        symbol: "airfield"
      },

      name: "Avión"
    });
  }

  const mapaActual = document.getElementById("mapa");

  let zoomActual = 4;
  let centroActual = {
    lat: 4.5709,
    lon: -74.2973
  };

  // GUARDAR POSICIÓN ACTUAL
  if (mapaActual.layout && mapaActual.layout.map) {

    zoomActual = mapaActual.layout.map.zoom;

    centroActual = mapaActual.layout.map.center;
  }

  var layout = {

    dragmode: "zoom",

    map: {

      style: "open-street-map",

      center: centroActual,

      zoom: zoomActual
    },

    margin: {
      r: 0,
      t: 40,
      b: 0,
      l: 0
    },

    title: "Mapa de vuelos"
  };


  Plotly.react("mapa", data, layout);

  // CLICK EN MAPA
  const mapa = document.getElementById("mapa");
  mapa.removeAllListeners("plotly_click");

  mapa.on("plotly_click", function (evento) {

    const punto = evento.points[0];

    // SOLO aceptar clicks en aeropuertos
    if (punto.data.name !== "Aeropuertos") return;

    const id = punto.customdata;

    if (!id) return;

    // PRIMER CLICK -> SALIDA
    if (clickSalida) {

      document.getElementById("aeropuertos").value = id;

      clickSalida = false;
    }

    // SEGUNDO CLICK -> LLEGADA
    else {

      document.getElementById("aeropuertol").value = id;

      clickSalida = true;
    }

    mostrarMapa();
  });
}