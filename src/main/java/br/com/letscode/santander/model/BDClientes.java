package br.com.letscode.santander.model;

import br.com.letscode.santander.controler.Conta;
import br.com.letscode.santander.dto.RequestCliente;
import br.com.letscode.santander.dto.RequestDeposito;
import br.com.letscode.santander.exceptions.NotFoundException;

import java.util.*;

public class BDClientes {
    private static List<ClienteModel> clientes = new ArrayList<>();

    public void adiciona(ClienteModel cliente){
        clientes.add(cliente);
    }

    public List<ClienteModel> buscaClientes(){
        return clientes;
    }

    public Optional<ClienteModel> detalhesCliente(UUID id) {
        return BDClientes.clientes.stream().filter(clientes -> Objects.equals(clientes.getId(),id)).findAny();
    }

    public Optional<ClienteModel> atualizaCliente(UUID id, RequestCliente requestCliente) {
            BDClientes.clientes.stream().filter(clientes -> Objects.equals(clientes.getId(),id)).forEach(clientes -> {
            clientes.setNome(requestCliente.getNome());
            clientes.setEmail(requestCliente.getEmail());
            clientes.setSenha(requestCliente.getSenha());
        });
            return detalhesCliente(id);
    }

    public void deletaCliente(UUID id) throws NotFoundException {
        Optional<ClienteModel> clienteModel = detalhesCliente(id);
        if(clienteModel.isPresent()){
            BDClientes.clientes.remove(clienteModel.get());
        }else{
            throw new NotFoundException("Cliente não encontrado.");
        }

    }

    public void deposita(UUID id, RequestDeposito requestDeposito) throws Exception{
        BDClientes.clientes.stream().filter(clientes -> Objects.equals(clientes.getId(),id))
                .forEach(clientes -> {
                    Optional<Conta> resultConta = clientes.getConta().stream().filter(conta->Objects.equals(conta.getId(),requestDeposito.getConta())).findAny();
                    if(resultConta.isPresent()){
                        double valor = resultConta.get().getSaldo() + requestDeposito.getDeposito();
                        resultConta.get().setSaldo(valor);
                    }else {
                        try{
                            throw new Exception("Conta não encontrada.");
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}
