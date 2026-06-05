const API = 'http://localhost:8080/api/propuestas';
const META_FIRMAS = 25000;

const mensaje = document.getElementById('mensaje');
const lista = document.getElementById('listaPropuestas');
const listaCongreso = document.getElementById('listaCongreso');
const rolActual = document.getElementById('rolActual');
const firmaSesion = document.getElementById('firmaSesion');

let rolSesion = '';
let propuestasCache = [];

function mostrarMensaje(texto) {
    mensaje.textContent = texto;
    mensaje.classList.add('visible');
    setTimeout(() => {
        mensaje.textContent = '';
        mensaje.classList.remove('visible');
    }, 4500);
}

function textoSeguro(valor) {
    if (valor === null || valor === undefined) return '';
    return String(valor)
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}


function obtenerFirmaSesion() {
    const valor = (firmaSesion.value || '').trim();
    if (valor.length < 6) {
        firmaSesion.focus();
        throw new Error('Primero ingresa una firma digital de sesión válida.');
    }
    return valor;
}

function firmaSesionRegistrada() {
    return (firmaSesion.value || '').trim().length >= 6;
}

function activarTabs() {
    document.querySelectorAll('.tab').forEach(boton => {
        boton.addEventListener('click', () => activarTab(boton.dataset.tab));
    });
}

function activarRoles() {
    document.querySelectorAll('.rol').forEach(boton => {
        boton.addEventListener('click', () => {
            try {
                obtenerFirmaSesion();
                rolSesion = boton.dataset.rol;
                document.querySelectorAll('.rol').forEach(r => r.classList.remove('activo'));
                boton.classList.add('activo');
                actualizarVistaPorRol();
                mostrarMensaje('Firma digital de sesión validada. Rol habilitado correctamente.');
            } catch (error) {
                mostrarMensaje(error.message);
            }
        });
    });

    firmaSesion.addEventListener('input', () => {
        if (!firmaSesionRegistrada()) {
            rolSesion = '';
            actualizarVistaPorRol();
        }
    });
}

function actualizarVistaPorRol() {
    const nombres = {
        representante: 'Representante de colectivo',
        ciudadano: 'Usuario ciudadano',
        congreso: 'Usuario del Congreso'
    };

    if (!rolSesion || !firmaSesionRegistrada()) {
        rolActual.textContent = 'Sesión pendiente: ingresa una firma digital y elige un rol';
        document.querySelectorAll('.tab').forEach(tab => tab.style.display = 'none');
        document.querySelectorAll('.tab-content').forEach(panel => panel.classList.remove('activo'));
        return;
    }

    rolActual.textContent = `Sesión activa: ${nombres[rolSesion]} con firma digital registrada`;

    document.querySelectorAll('.tab').forEach(tab => {
        const rolesPermitidos = tab.dataset.roles.split(',');
        tab.style.display = rolesPermitidos.includes(rolSesion) ? 'flex' : 'none';
    });

    const primerTabVisible = Array.from(document.querySelectorAll('.tab')).find(tab => tab.style.display !== 'none');
    if (primerTabVisible) activarTab(primerTabVisible.dataset.tab);
}

function activarTab(idTab) {
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('activo'));
    document.querySelectorAll('.tab-content').forEach(panel => panel.classList.remove('activo'));
    const boton = document.querySelector(`.tab[data-tab="${idTab}"]`);
    const panel = document.getElementById(idTab);
    if (boton) boton.classList.add('activo');
    if (panel) panel.classList.add('activo');
}

async function enviarJson(url, metodo, datos) {
    const respuesta = await fetch(url, {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
    });

    const contenido = await respuesta.json();
    if (!respuesta.ok) {
        throw new Error(contenido.error || 'No se pudo completar la operación');
    }
    return contenido;
}

async function cargarPropuestas() {
    try {
        const respuesta = await fetch(API);
        propuestasCache = await respuesta.json();
        renderizarPropuestas(propuestasCache);
        renderizarCongreso(propuestasCache);
    } catch (error) {
        mostrarMensaje(error.message);
    }
}

function renderizarPropuestas(propuestas) {
    lista.innerHTML = '';

    if (!propuestas || propuestas.length === 0) {
        lista.innerHTML = '<p class="vacio">No hay propuestas registradas.</p>';
        return;
    }

    propuestas.forEach(p => lista.appendChild(crearCardPropuesta(p, false)));
}

function renderizarCongreso(propuestas) {
    listaCongreso.innerHTML = '';
    const congeladas = (propuestas || []).filter(p => p.estado === 'ENVIADA' || p.hashCriptografico || p.expedienteCongreso);

    if (congeladas.length === 0) {
        listaCongreso.innerHTML = '<p class="vacio">Todavía no hay expedientes congelados. Se mostrarán aquí automáticamente cuando una propuesta alcance 25 000 firmas válidas.</p>';
        return;
    }

    congeladas.forEach(p => listaCongreso.appendChild(crearCardPropuesta(p, true)));
}

function crearCardPropuesta(p, modoCongreso) {
    const firmas = contarFirmas(p);
    const porcentaje = Math.min((firmas / META_FIRMAS) * 100, 100);
    const card = document.createElement('div');
    card.className = `card ${p.estado === 'ENVIADA' ? 'card-enviada' : ''}`;

    card.innerHTML = `
        <div class="card-top">
            <div>
                <h3>${textoSeguro(p.titulo)}</h3>
                <p class="small"><b>ID:</b> ${textoSeguro(p.id)}</p>
            </div>
            <span class="estado">${textoSeguro(p.estado)}</span>
        </div>
        <div class="datos-card">
            <p><b>Colectivo:</b> ${textoSeguro(p.colectivo)}</p>
            <p><b>Categoría:</b> ${textoSeguro(p.categoria || 'Sin categoría')}</p>
        </div>
        <p>${textoSeguro(p.resumen)}</p>
        <p class="small"><b>Firmas válidas:</b> ${firmas.toLocaleString('es-PE')} / ${META_FIRMAS.toLocaleString('es-PE')}</p>
        <div class="progreso"><div style="width:${porcentaje}%"></div></div>
        <p class="small"><b>Fecha límite:</b> ${formatearFecha(p.fechaLimite)}</p>
        <div class="mini-grid">
            <span>Comentarios: <b>${(p.comentarios || []).length}</b></span>
            <span>Modificaciones: <b>${(p.modificaciones || []).length}</b></span>
            <span>Recursos: <b>${(p.recursos || []).length}</b></span>
        </div>
        ${renderizarComentarios(p.comentarios)}
        ${renderizarModificaciones(p.modificaciones)}
        ${renderizarRecursos(p.recursos)}
        <div class="congreso-box">
            <p class="small"><b>Congelamiento:</b> ${p.fechaCongelamiento ? formatearFecha(p.fechaCongelamiento) : 'Pendiente automático'}</p>
            <p class="small hash-texto"><b>Hash:</b> ${textoSeguro(p.hashCriptografico || 'Pendiente')}</p>
            <p class="small"><b>Expediente:</b> ${textoSeguro(p.expedienteCongreso || 'Pendiente')}</p>
        </div>
        ${renderizarFirmaDigital(p.firmaDigital)}
        ${modoCongreso ? '<p class="nota-congreso">Vista congresal: expediente listo para revisión y firma digital.</p>' : ''}
    `;
    return card;
}

function renderizarComentarios(comentarios) {
    if (!comentarios || comentarios.length === 0) return '<p class="small">Sin comentarios registrados.</p>';
    let html = '<div class="bloque-lista"><h4>Comentarios</h4>';
    comentarios.forEach(c => {
        html += `<div class="item-lista"><b>${textoSeguro(c.autor)}</b><p>${textoSeguro(c.mensaje)}</p></div>`;
    });
    html += '</div>';
    return html;
}

function renderizarModificaciones(modificaciones) {
    if (!modificaciones || modificaciones.length === 0) return '<p class="small">Sin modificaciones propuestas.</p>';
    let html = '<div class="bloque-lista"><h4>Modificaciones propuestas</h4>';
    modificaciones.forEach(m => {
        html += `<div class="item-lista"><b>${textoSeguro(m.autor)}</b><p>${textoSeguro(m.descripcion)}</p><p class="small"><b>Texto propuesto:</b> ${textoSeguro(m.textoPropuesto)}</p></div>`;
    });
    html += '</div>';
    return html;
}

function renderizarRecursos(recursos) {
    if (!recursos || recursos.length === 0) return '<p class="small">Sin recursos registrados.</p>';
    let html = '<div class="recursos-lista">';
    recursos.forEach(r => {
        html += `<span class="recurso-chip" title="${textoSeguro(r.descripcionTipo || '')}">${textoSeguro(r.tipo)}: ${textoSeguro(r.nombre)}</span>`;
    });
    html += '</div>';
    return html;
}

function renderizarFirmaDigital(firmaDigital) {
    if (!firmaDigital) return '<p class="small">Firma digital: pendiente</p>';
    return `
        <div class="firma-digital">
            <p><b>Firma digital:</b> ${textoSeguro(firmaDigital.estadoValidacion)}</p>
            <p class="small"><b>Firmante:</b> ${textoSeguro(firmaDigital.firmante)} - ${textoSeguro(firmaDigital.cargo)}</p>
            <p class="small"><b>Certificado:</b> ${textoSeguro(firmaDigital.certificadoReferencia)}</p>
            <p class="small"><b>Algoritmo:</b> ${textoSeguro(firmaDigital.algoritmo)}</p>
            <p class="small hash-texto"><b>Valor:</b> ${textoSeguro(firmaDigital.valorFirma)}</p>
        </div>
    `;
}

function contarFirmas(propuesta) {
    return (propuesta.firmas || []).filter(f => f.valida).length;
}

function formatearFecha(fecha) {
    if (!fecha) return 'Sin fecha';
    return new Date(fecha).toLocaleString('es-PE');
}

document.getElementById('formPropuesta').addEventListener('submit', async e => {
    e.preventDefault();
    obtenerFirmaSesion();
    const datos = {
        titulo: titulo.value,
        colectivo: colectivo.value,
        categoria: categoria.value,
        resumen: resumen.value,
        textoNormativo: textoNormativo.value
    };
    try {
        await enviarJson(API, 'POST', datos);
        e.target.reset();
        mostrarMensaje('Propuesta creada correctamente');
        cargarPropuestas();
    } catch (error) {
        mostrarMensaje(error.message);
    }
});

document.getElementById('formFirma').addEventListener('submit', async e => {
    e.preventDefault();
    obtenerFirmaSesion();
    const datos = { dni: dni.value, nombres: nombres.value, correo: correo.value };
    try {
        const propuesta = await enviarJson(`${API}/${firmaId.value}/firmas`, 'POST', datos);
        e.target.reset();
        if (propuesta.estado === 'ENVIADA') {
            mostrarMensaje('Firma registrada. La propuesta alcanzó la meta y fue congelada automáticamente.');
        } else {
            mostrarMensaje('Firma registrada');
        }
        cargarPropuestas();
    } catch (error) {
        mostrarMensaje(error.message);
    }
});

document.getElementById('formComentario').addEventListener('submit', async e => {
    e.preventDefault();
    obtenerFirmaSesion();
    const datos = { autor: autorComentario.value, mensaje: `[${tipoComentario.value}] ${mensajeComentario.value}` };
    try {
        await enviarJson(`${API}/${comentarioId.value}/comentarios`, 'POST', datos);
        e.target.reset();
        mostrarMensaje('Comentario registrado');
        cargarPropuestas();
    } catch (error) {
        mostrarMensaje(error.message);
    }
});

document.getElementById('formRecurso').addEventListener('submit', async e => {
    e.preventDefault();
    obtenerFirmaSesion();
    const datos = { nombre: nombreRecurso.value, tipo: tipoRecurso.value, url: urlRecurso.value };
    try {
        await enviarJson(`${API}/${recursoId.value}/recursos`, 'POST', datos);
        e.target.reset();
        mostrarMensaje('Recurso registrado');
        cargarPropuestas();
    } catch (error) {
        mostrarMensaje(error.message);
    }
});

document.getElementById('formModificacion').addEventListener('submit', async e => {
    e.preventDefault();
    obtenerFirmaSesion();
    const datos = {
        autor: autorModificacion.value,
        descripcion: descripcionModificacion.value,
        textoPropuesto: textoModificacion.value
    };
    try {
        await enviarJson(`${API}/${modificacionId.value}/modificaciones`, 'POST', datos);
        e.target.reset();
        mostrarMensaje('Modificación registrada');
        cargarPropuestas();
    } catch (error) {
        mostrarMensaje(error.message);
    }
});

document.getElementById('formFirmaDigital').addEventListener('submit', async e => {
    e.preventDefault();
    obtenerFirmaSesion();
    const datos = {
        firmante: firmanteDigital.value,
        cargo: cargoDigital.value,
        certificadoReferencia: certificadoReferencia.value
    };
    try {
        await enviarJson(`${API}/${firmaDigitalId.value}/firma-digital`, 'POST', datos);
        e.target.reset();
        mostrarMensaje('Firma digital registrada como carcasa simulada');
        cargarPropuestas();
    } catch (error) {
        mostrarMensaje(error.message);
    }
});

document.getElementById('btnActualizar').addEventListener('click', cargarPropuestas);
document.getElementById('btnActualizarCongreso').addEventListener('click', cargarPropuestas);
activarTabs();
activarRoles();
actualizarVistaPorRol();
cargarPropuestas();
