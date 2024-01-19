const form = document.querySelector('#formulario');

let despesa = {};

let despesas = sessionStorage.getItem('despesaVO')
despesas = JSON.parse(despesas);
let usuario = sessionStorage.getItem('usuarioVO');
usuario = JSON.parse(usuario);


async function buscarDespesa() {
    let options = {
        method: "GET",
        headers: {"Content-type": "application/json"}
    };
    const listaDespesas = await fetch('http://localhost:8090/Senhor-financas/rest/despesa/listar/' + usuario.idUsuario, options);
    const listaDespesasJson = await listaDespesas.json();
    if (listaDespesasJson.length != 0) {
        preencherTabela(listaDespesasJson)
    } else {
        alert("Houve um erro na busca das despesas!");
    }
}

function preencherTabela(dados) {
    let tbody = document.getElementById('table-body');
    let acmulador = 0;
    tbody.innerText = '';

    for(let i = 0; i < dados.length; i++) {
        document.getElementById('table-foot').hidden = false;

        let tr = tbody.insertRow();
        let td_id = tr.insertCell();
        let td_descricao = tr.insertCell();
        let td_dataVencimento = tr.insertCell();
        let td_dataPagamento = tr.insertCell();
        let td_valor = tr.insertCell();
        let td_acoes= tr.insertCell();

        acmulador += dados[i].valor;

        td_id.innerText = dados[i].idDespesa;
        td_descricao.innerText = dados[i].descricao;
        td_dataVencimento.innerText = formatarData(dados[i].dataVencimento);
        td_dataPagamento.innerText = formatarData(dados[i].dataPagamento);
        td_valor.innerText = dados[i].valor;
        
        let editar = document.createElement('button');
        editar.textContent = 'Editar';
        editar.style.height = '30px';
        editar.style.width = '90px';
        editar.style.border = '2px solid';
        editar.style.borderRadius = '7px';
        editar.setAttribute('onclick', 'editarDespesa('+JSON.stringify(dados[i]) +')');
        td_acoes.appendChild(editar);

        let excluir = document.createElement('button');
        excluir.textContent = 'Excluir';
        excluir.style.height = '30px';
        excluir.style.width = '90px';
        excluir.style.border = '2px solid';
        excluir.style.borderRadius = '7px';
        excluir.setAttribute('onclick', 'excluirDespesa('+JSON.stringify(dados[i])+')');
        td_acoes.appendChild(excluir);

    }
    document.querySelector('#total').innerText = acmulador.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'});
}

function limparTabela(){
    let tbody = document.getElementById('table-body');
    document.getElementById('table-foot').hidden = true;
    tbody.innerHTML = '';
  }

function formatarData(data){
    let dataFormatada = new Date(data),
    dia  = dataFormatada.getDate().toString().padStart(2,'0'),
    mes  = (dataFormatada.getMonth()+1).toString().padStart(2,'0'),
    ano  = dataFormatada.getFullYear();
    return dia+"/"+mes+"/"+ano;
}

async function excluirDespesa(dados) {
    let options = {
        method: "DELETE",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify({
            idDespesa: dados.idDespesa,
            descricao: dados.descricao,
            valor: dados.valor,
            dataVencimento: dados.dataVencimento,
            dataPagamento: dados.dataPagamento
        })
    };
    const resultado = await fetch('http://localhost:8090/Senhor-financas/rest/despesa/excluir', options);
    if (resultado.ok) {
        alert("Despesa excluida com sucesso!");
    } else {
        alert("Houve um problema na exclusão da despesa!");
    }
}

async function cadastrarDespesa() {
    let usuario = sessionStorage.getItem('usuarioVO');
    usuario = JSON.parse(usuario);
    let options = {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify ({
            idDespesa: 0,
            idUsuario: usuario.idUsuario,
            descricao: document.querySelector('#descricao').value,
            valor: document.querySelector('#valor').value,
            dataVencimento: document.querySelector("#dtvencimento").value,
            dataPagamento: document.querySelector('#dtpagamento').value
        })
    };
    const resultado = await fetch('http://localhost:8090/Senhor-financas/rest/despesa/cadastrar', options);
    despesa = await resultado.json();
    if (despesa.idDespesa != 0) {
        alert("Cadastro realizado com sucesso!")
        window.location.href = './depesas.html';
    } else {
        alert("Houve um problema ao cadastrar uma nova despesa!")
    }
    form.reset();
}

function editarDespesa(dados) {
    sessionStorage.setItem('despesaVO', JSON.stringify(dados));
    window.location.href = ('./editarDespesa.html');
}

