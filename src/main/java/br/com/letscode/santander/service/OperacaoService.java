package br.com.letscode.santander.service;

import br.com.letscode.santander.SantanderApplication;
import br.com.letscode.santander.dto.RequestDeposito;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OperacaoService {

    public void deposita(UUID id, RequestDeposito requestDeposito) throws Exception {
        SantanderApplication.bdClientes.deposita(id, requestDeposito);
    }
}
