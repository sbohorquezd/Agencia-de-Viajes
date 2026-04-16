let listaAeropuertos = [];

document.addEventListener("DOMContentLoaded", () => {

  fetch("https://api-colombia.com/api/v1/airport")
    .then(res => res.json())
    .then(data => {

      listaAeropuertos = data.filter(a => a.latitude && a.longitude);

      const select = document.getElementById("aeropuerto");
      listaAeropuertos.forEach(aeropuerto => {
        const option = document.createElement("option");
        option.value = aeropuerto.id;
        option.textContent = aeropuerto.name;
        select.appendChild(option);
      });
      mostrarMapa(listaAeropuertos[0]);

    })
    .catch(error => console.error(error));

  // evento dentro del DOM
  document.getElementById("aeropuerto").addEventListener("change", (e) => {
    const id = e.target.value;

    const aeropuerto = listaAeropuertos.find(a => a.id == id);

    if (!aeropuerto) return;

    mostrarMapa(aeropuerto);
  });

});

function mostrarMapa(aeropuerto) {
  var data = [{
    type: "scattermap",
    mode: "markers",
    lat: [aeropuerto.latitude],
    lon: [aeropuerto.longitude],
    marker: {
      size: 12,
      color: "red"
    },
    text: [aeropuerto.name]
  }];

  var layout = {
      dragmodo:"zoom",
      map:{
      			style: "open-street-map", 
			center: { lat: 4.7110, lon: -74.0721 },
			zoom: 12
      },
		margin: { r: 0, t: 0, b: 0, l: 0 },
    title: aeropuerto.name
  };

  Plotly.newPlot("mapa", data, layout);
}