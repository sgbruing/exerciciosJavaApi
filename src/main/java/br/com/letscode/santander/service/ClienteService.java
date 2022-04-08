package br.com.letscode.santander.service;

import br.com.letscode.santander.SantanderApplication;
import br.com.letscode.santander.controler.Conta;
import br.com.letscode.santander.controler.Tipo_Conta;
import br.com.letscode.santander.dto.RequestCliente;
import br.com.letscode.santander.exceptions.NotFoundException;
import br.com.letscode.santander.model.BDClientes;
import br.com.letscode.santander.model.ClienteModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
//Anotação utilizada para escrever log nos códigos.
@Service

//O service serve para adicionarmos o tratamento/lógica da operação, deixando o controller apenas recebendo os requests
//e devolvendo a response.
public class ClienteService {

    BDClientes bdClientes = new BDClientes();

    public ClienteModel cadastraCliente(RequestCliente requestCliente) {
        List<Conta> contaList = new ArrayList<>();
        Conta conta = new Conta(Tipo_Conta.CONTA_CORRENTE);
        contaList.add(conta);
        ClienteModel cliente = new ClienteModel(UUID.randomUUID(), requestCliente.getNome(), requestCliente.getEmail(), requestCliente.getSenha(), requestCliente.getSaldo(), contaList);
        bdClientes.adiciona(cliente);
        return cliente;
    }

    public List<ClienteModel> buscaTodosClientes(){
        log.info("Buscando todos os clientes");
        return bdClientes.buscaClientes();
    }

    public ClienteModel detalhesClientes(UUID id) throws NotFoundException {
        Optional<ClienteModel> resultCliente = bdClientes.detalhesCliente(id);
        if(resultCliente.isPresent()){
            return resultCliente.get();
        }else{
            log.error("Usuario nao foi encontrado"+ id);
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    public ClienteModel atualizaCliente(UUID id, RequestCliente requestCliente) throws NotFoundException {
        Optional<ClienteModel> resultCliente = bdClientes.atualizaCliente(id, requestCliente);
        if(resultCliente.isPresent()){
            return resultCliente.get();
        }else{
            log.error("Usuario nao foi encontrado"+ id);
            throw new NotFoundException("Usuário não encontrado.");
        }
    }
}
