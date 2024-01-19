let despesa = sessionStorage.getItem('despesaVO');
despesa = JSON.parse(despesa);

async function atualizarDespesa() {
    let options = {
        method: "PUT",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({
            idDespesa: despesa.idDespesa,
            descricao: document.querySelector('#descricao').value,
            valor: document.querySelector('#valor').value,
            dataVencimento: document.querySelector('#dtvencimento').value,
            dataPagamento: document.querySelector('#dtpagamento').value
        })
    };
    const resultado = await fetch('http://localhost:8090/Senhor-financas/rest/despesa/atualizar', options);
    if (resultado.ok == true) {
        alert("Atualização realizada com sucesso.");
        sessionStorage.removeItem('despesaVO');
        window.location.href = './depesas.html';
    } else {
        alert("Ocorreu um problema ao atualizar a despesa.");
    }
}

async function preencherCampos() {
    document.querySelector('#descricao').value = despesa.descricao;
    document.querySelector('#valor').value = despesa.valor;
    document.querySelector('#dtvencimento').value = formatarData(despesa.dataVencimento);
    document.querySelector('#dtpagamento').value = formatarData(despesa.dataPagamento);
}
preencherCampos();

function formatarData(data) {
    let dataFormatada = new Date(data);
    dia = dataFormatada.getDate().toString().padStart(2, '0');
    mes = (dataFormatada.getMonth() + 1).toString().padStart(2, '0');
    ano = dataFormatada.getFullYear();
    return ano + "-" + mes + "-" + dia;
}