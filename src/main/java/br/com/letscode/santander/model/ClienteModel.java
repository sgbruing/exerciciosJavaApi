package br.com.letscode.santander.model;

import br.com.letscode.santander.controler.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ClienteModel {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private double saldo;
    private List<Conta> conta;
}
