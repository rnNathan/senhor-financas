const form = document.querySelector('#formulario');
const limpar = document.querySelector('#limpar');

let receita = {};

let receitas = sessionStorage.getItem('receitaVO');
receitas = JSON.parse(receitas);
let usuario = sessionStorage.getItem('usuarioVO');
usuario = JSON.parse(usuario);

async function buscarReceita() {
    let options = {
        method: "GET",
        headers: { "Content-type": "application/json" }
    };
    const listaReceita = await fetch('http://localhost:8090/Senhor-financas/rest/receita/listar/' + usuario.idUsuario, options);
    const listaReceitaJson = await listaReceita.json();
    if (listaReceitaJson.length != 0) {
        preencherTabela(listaReceitaJson);
    } else {
        alert("Houve um problema ao listar as receitas.")
    }
}

function preencherTabela(dados) {
    document.getElementById('table-foot').hidden = false;
    let tbody = document.getElementById('table-body');
    tbody.innerText = '';
    let acumulador = 0;

    for (let i = 0; i < dados.length; i++) {

        document.getElementById('table-foot').hidden = false;

        let tr = tbody.insertRow();
        let td_id = tr.insertCell();
        let td_descricao = tr.insertCell();
        let td_data = tr.insertCell();
        let td_valor = tr.insertCell();
        let td_acoes = tr.insertCell();

        acumulador += dados[i].valor;

        td_id.innerText = dados[i].idReceita;
        td_descricao.innerText = dados[i].descricao;
        td_data.innerText = formatarData(dados[i].dataReceita);
        td_valor.innerText = dados[i].valor;

        let editar = document.createElement('button');
        editar.textContent = 'Editar';
        editar.style.height = '30px';
        editar.style.width = '90px';
        editar.style.border = '2px solid';
        editar.style.borderRadius = '7px';
        editar.setAttribute('onclick', 'editarReceita(' + JSON.stringify(dados[i]) + ')');
        td_acoes.appendChild(editar);

        let excluir = document.createElement('button');
        excluir.textContent = 'Excluir';
        excluir.style.height = '30px';
        excluir.style.width = '90px';
        excluir.style.border = '2px solid';
        excluir.style.borderRadius = '7px';
        excluir.setAttribute('onclick', 'excluirReceita(' + JSON.stringify(dados[i]) + ')');
        td_acoes.appendChild(excluir);

    }
    document.querySelector('#total').innerText = acumulador.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });
}

function limparTabela() {
    let tbody = document.getElementById('table-body');
    document.getElementById('table-foot').hidden = true;
    tbody.innerHTML = '';
}

function formatarData(data) {
    let dataFormatada = new Date(data);
    dia = dataFormatada.getDate().toString().padStart(2, '0');
    mes = (dataFormatada.getMonth() + 1).toString().padStart(2, '0');
    ano = dataFormatada.getFullYear();
    return dia + "/" + mes + "/" + ano;
}

async function excluirReceita(dados) {
    let options = {
        method: "DELETE",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({
            idReceita: dados.idReceita,
            descricao: dados.descricao,
            valor: dados.valor,
            dataReceita: dados.dataReceita
        })
    };
    const resultado = await fetch('http://localhost:8090/Senhor-financas/rest/receita/excluir/', options)
    if (resultado.ok == true) {
        alert("Exclusão realizada com sucesso.");
        receita = {};
        limparTabela();
    } else {
        alert("Erro ao excluir uma receita.");
    }
}

async function cadastrarReceita() {
    let usuario = sessionStorage.getItem('usuarioVO');
    usuario = JSON.parse(usuario);
    let options = {
        method: "POST",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({
            idUsuario: usuario.idUsuario,
            descricao: document.querySelector('#descricao').value,
            valor: document.querySelector('#valor').value,
            dataReceita: document.querySelector('#dtreceita').value
        })
    };
    const resultado = await fetch('http://localhost:8090/Senhor-financas/rest/receita/cadastrar', options);
    receita = await resultado.json();
    if (receita.idReceita != 0) {
        alert("Receita cadastrada com sucesso.");
        window.location.href = ('./receita.html');
    } else {
        alert("Erro ao cadastrar uma receita.");
    }
    form.reset();
}

function editarReceita(dados) {
    sessionStorage.setItem('receitaVO', JSON.stringify(dados));
    window.location.href = ('./editarReceita.html');
}


