const form = document.querySelector('#formulario');

let usuario = {};

async function fazerLogin() {
    let options = {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify({
            login: document.querySelector('#user').value,
            senha: document.querySelector('#senha').value
        })

    };
    const resposta = await fetch('http://localhost:8090/Senhor-financas/rest/usuario/logar', options);
    const usuarioLogado = await resposta.json();
    if(usuarioLogado.idusuario != 0){
        sessionStorage.setItem('usuarioVO', JSON.stringify(usuarioLogado));
        alert("Login realizado com sucesso.");
        window.location.href = 'modules/receita.html';
    } else {
        alert("Login ou Senha incorreto.")
        document.querySelector('#senha').value = "";
    }
}

form.addEventListener('submit', (evento) => {
    evento.preventDefault();
    cadastrarUsuario();
})

async function cadastrarUsuario() {
    let options = {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify({
            idUsuario: 0,
            nome: document.querySelector('#nome').value,
            cpf: document.querySelector('#cpf').value,
            email: document.querySelector('#email').value,
            dataNascimento: document.querySelector('#dtnascimento').value,
            login: document.querySelector('#login').value,
            senha: document.querySelector('#senha').value,   
        })
    };
    const resposta = await fetch('http://localhost:8090/Senhor-financas/rest/usuario/cadastrar', options);
    const usuarioCadastradoJson = await resposta.json();
    if(usuarioCadastradoJson.idUsuario !== 0) {
        alert("Usuário Cadastrado com Sucesso");
        window.location.href = "../index.html";
    } else {
        alert("Houve um problema no cadastro do usuário.")
    }
    form.reset();
}