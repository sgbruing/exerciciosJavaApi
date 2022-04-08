package br.com.letscode.santander.service;


import br.com.letscode.santander.controler.Conta;
import br.com.letscode.santander.controler.Tipo_Conta;
import br.com.letscode.santander.dto.RequestCliente;
import br.com.letscode.santander.model.BDClientes;
import br.com.letscode.santander.model.ClienteModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    BDClientes bdClientes;

    @InjectMocks
    ClienteService clienteService;

    @Captor
    private ArgumentCaptor<ClienteModel> clienteArgumentCaptor;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarListaClienteVazia(){
        when(bdClientes.buscaClientes()).thenReturn(new ArrayList<>());

        List<ClienteModel> clientes = clienteService.buscaTodosClientes();
        Assertions.assertTrue(clientes.isEmpty());

        verify(bdClientes, times(1)).buscaClientes();
        verifyNoMoreInteractions(bdClientes);
    }

    @Test
    public void deveRetornarListaClientePreenchida() {
        Conta conta = new Conta(Tipo_Conta.CONTA_CORRENTE);
        List<Conta> contaList = new ArrayList<>();
        contaList.add(conta);

        ClienteModel cliente = new ClienteModel(UUID.randomUUID(), "cinthia", "cinthia@email.com", "1234", 0,contaList);

        when(bdClientes.buscaClientes()).thenReturn(Arrays.asList(cliente));

        List<ClienteModel> clientes = clienteService.buscaTodosClientes();

        assertEquals(Arrays.asList(cliente), clientes);
        verify(bdClientes, times(1)).buscaClientes();
    }

    @Test
    public void testeSpy() {
        RequestCliente requestClienteSpy = spy(new RequestCliente(
                "cinthia",
                "cinthiaqcarsoso@teste.com",
                "44934586719",
                "54353",
                3,
                0
        ));

        when(requestClienteSpy.getNome()).thenReturn("maria");

        assertEquals("cinthia", requestClienteSpy.getNome());
        assertEquals("44934586719", requestClienteSpy.getCpf());
    }

    @Test
    public void cadastraCliente2(){
        RequestCliente requestCliente = new RequestCliente(
                "cinthia",
                "cinthiaqcarsoso@teste.com",
                "44934586719",
                "54353",
                3,
                0
        );

        clienteService.cadastraCliente(requestCliente);

        verify(bdClientes).adiciona(clienteArgumentCaptor.capture());

        assertEquals(requestCliente.getNome(), clienteArgumentCaptor.getValue().getNome());
        assertNotNull(clienteArgumentCaptor.getValue().getId());
    }


    @Test
    public void deveRetornarListaVazia(){
        List<ClienteModel> clientes = clienteService.buscaTodosClientes();
        Assertions.assertFalse(clientes.isEmpty());
    }

    @Test
    public void cadastraCliente(){
        RequestCliente requestCliente = new RequestCliente();
        requestCliente.setNome("Teste");
        requestCliente.setEmail("teste@teste.com.br");
        requestCliente.setCpf("12345678912");
        requestCliente.setSenha("1234");

        ClienteModel clienteModel = clienteService.cadastraCliente(requestCliente);
        assertEquals(clienteModel.getNome(), requestCliente.getNome());
        assertNotNull(clienteModel.getId());
    }

    @Test
    public void atualizaCliente() throws Exception {
        RequestCliente requestCliente = new RequestCliente();
        requestCliente.setNome("Teste");
        requestCliente.setEmail("teste@teste.com.br");
        requestCliente.setCpf("12345678912");
        requestCliente.setSenha("1234");

        ClienteModel clienteModel = clienteService.cadastraCliente(requestCliente);

        RequestCliente requestClienteAtualizado = new RequestCliente();
        requestClienteAtualizado.setNome("Teste2");
        requestClienteAtualizado.setEmail("teste2@teste.com.br");
        requestClienteAtualizado.setCpf("12345678912");
        requestClienteAtualizado.setSenha("1234");
        ClienteModel clienteAtualizado = clienteService.atualizaCliente(clienteModel.getId(), requestClienteAtualizado);

        assertEquals("Teste2", clienteAtualizado.getNome());
    }

    @Test
    public void buscarClienteQueNaoExiste(){
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(Exception.class, () -> clienteService.detalhesClientes(id));
    }

    @Test
    public void buscarClienteQueNaoExiste2(){
        UUID id = UUID.randomUUID();
        try{
            clienteService.detalhesClientes(id);
            fail("Não lançou a exceção.");
        }catch (Exception e){ }
    }
}
