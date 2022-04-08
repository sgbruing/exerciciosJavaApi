package br.com.letscode.santander.controler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Getter
@Setter

public class Conta {
    private UUID id;
    private int numeroConta;
    private int agencia;
    private Tipo_Conta tipoConta;
    private double saldo;

    public Conta(Tipo_Conta tipoConta) {
        this.id = UUID.randomUUID();
        this.numeroConta = gerarConta();
        this.agencia = 001;
        this.tipoConta = tipoConta;
    }

    public int gerarConta(){

        Random random = new Random();
        int numero = random.nextInt(9999);
        return numero;
    }
}
