package br.com.letscode.santander.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class RequestDeposito {
    private double deposito;
    private UUID conta;
}
