package br.com.letscode.santander.dto;

import br.com.letscode.santander.controler.Conta;
import br.com.letscode.santander.model.ClienteModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter @AllArgsConstructor
public class ResponseCliente {
    private UUID id;
    private String nome;
    private String email;
    private List<Conta> conta;

    public ResponseCliente(ClienteModel cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.conta = cliente.getConta();
    }

    public static List<ResponseCliente> toResponse(List<ClienteModel> clientes){
        return clientes.stream().map(cliente -> new ResponseCliente(cliente)).collect(Collectors.toList());
    }
}
