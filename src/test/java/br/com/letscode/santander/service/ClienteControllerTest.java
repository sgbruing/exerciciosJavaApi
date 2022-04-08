package br.com.letscode.santander.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarClienteComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nome\": \"Teste\",\n" +
                        "    \"email\": \"teste@teste.com\",\n" +
                        "    \"cpf\": \"12345678912\",\n" +
                        "    \"senha\": \"1234\" \n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value("teste@teste.com"))
                .andExpect(jsonPath("$.active").doesNotExist());
    }
}
