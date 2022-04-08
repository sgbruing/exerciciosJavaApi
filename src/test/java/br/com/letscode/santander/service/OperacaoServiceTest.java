package br.com.letscode.santander.service;

import br.com.letscode.santander.dto.RequestCliente;
import br.com.letscode.santander.dto.RequestDeposito;
import br.com.letscode.santander.model.ClienteModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OperacaoServiceTest {
    OperacaoService operacaoService = new OperacaoService();
    ClienteService clienteService = new ClienteService();

    @Test
    public void tenteDeposito() throws Exception {
        RequestCliente requestCliente = new RequestCliente();
        requestCliente.setNome("Teste");
        requestCliente.setEmail("teste@teste.com");
        requestCliente.setSenha("123456");
        requestCliente.setCpf("12345678912");

        ClienteModel clienteModel = clienteService.cadastraCliente(requestCliente);

        RequestDeposito requestDeposito = new RequestDeposito();
        requestDeposito.setDeposito(10.0);
        requestDeposito.setConta(clienteModel.getConta().get(0).getId());
        operacaoService.deposita(clienteModel.getId(), requestDeposito);

        Assertions.assertEquals(20.0, clienteModel.getConta().get(0).getSaldo());
    }

    @Test
    public void testeContaInexistente(){
        UUID id = UUID.randomUUID();

        RequestDeposito requestDeposito = new RequestDeposito();
        requestDeposito.setDeposito(10.0);
        requestDeposito.setConta(id);

        Assertions.assertThrows(Exception.class, ()->operacaoService.deposita(id, requestDeposito));
    }
}
