let receita = sessionStorage.getItem('receitaVO');
receita = JSON.parse(receita);

async function atualizarReceita() {
    let options = {
        method: "PUT",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({
            idReceita: receita.idReceita,
            descricao: document.querySelector('#descricao').value,
            valor: document.querySelector('#valor').value,
            dataReceita: document.querySelector('#dtreceita').value
        })
    };
    const resultado = await fetch('http://localhost:8090/Senhor-financas/rest/receita/atualizar', options);
    if (resultado.ok == true) {
        alert("Atualização realizada com sucesso.");
        sessionStorage.removeItem('receitaVO');
        window.location.href = './receita.html';
    } else {
        alert("Ocorreu um problema ao atualizar a receita.");
    }
}

async function preencherCampos() {
    document.querySelector('#descricao').value = receita.descricao;
    document.querySelector('#valor').value = receita.valor;
    document.querySelector('#dtreceita').value = formatarData(receita.dataReceita);
}
preencherCampos();

function formatarData(data) {
    let dataFormatada = new Date(data);
    dia = dataFormatada.getDate().toString().padStart(2, '0');
    mes = (dataFormatada.getMonth() + 1).toString().padStart(2, '0');
    ano = dataFormatada.getFullYear();
    return ano + "-" + mes + "-" + dia;
}