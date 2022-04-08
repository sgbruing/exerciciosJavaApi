package br.com.letscode.santander.controler;

import br.com.letscode.santander.SantanderApplication;
import br.com.letscode.santander.dto.RequestCliente;
import br.com.letscode.santander.dto.RequestDeposito;
import br.com.letscode.santander.dto.ResponseCliente;
import br.com.letscode.santander.exceptions.NotFoundException;
import br.com.letscode.santander.model.BDClientes;
import br.com.letscode.santander.model.ClienteModel;
import br.com.letscode.santander.service.ClienteService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Eu trocando esse Controller por RestController pode retirar todos os ResponseBody
@Controller
//Com esse RequestMapping unifica o path e você trata por método(get ou post...)
@RequestMapping("/clientes")
public class ClienteController {
    ClienteService clienteService = new ClienteService();

    @GetMapping
    @ResponseBody
    //Essa anotação permite o salvamento em cache, evitando que seja feita requisição a cada chamada.
    @Cacheable(value = "listaClientes")
    public List<ResponseCliente> clientes(){
        return ResponseCliente.toResponse(clienteService.buscaTodosClientes());
    }

    @PostMapping
    @ResponseBody
    //Essa anotação realiza a exclusão do cache acima a cada cadastramento, mantendo o cache acima atualizado quando chamado novamente.
    @CacheEvict(value = "listaClientes", allEntries = true)
    public ResponseEntity<ResponseCliente> cadastrarCliente(@RequestBody @Valid RequestCliente requestCliente, UriComponentsBuilder uriComponentsBuilder){
        ClienteModel cliente = clienteService.cadastraCliente(requestCliente);
        URI uri = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseCliente(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCliente> detalhesCliente(@PathVariable UUID id) throws NotFoundException {
        ClienteModel cliente = clienteService.detalhesClientes(id);
        return ResponseEntity.ok(new ResponseCliente(cliente));
    }

    @PutMapping ("/{id}")
    @CacheEvict(value = "listaClientes", allEntries = true)
    public ResponseEntity<ResponseCliente> atualizaCliente(@PathVariable UUID id, @RequestBody RequestCliente requestCliente) throws NotFoundException {
        return ResponseEntity.ok(new ResponseCliente(clienteService.atualizaCliente(id, requestCliente)));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaClientes", allEntries = true)
    public ResponseEntity deleteCliente(@PathVariable UUID id) throws NotFoundException {
        SantanderApplication.bdClientes.deletaCliente(id);
        return ResponseEntity.ok().build();
    }
}
