package br.com.letscode.santander.dto;
import br.com.letscode.santander.util.CPF;
import br.com.letscode.santander.util.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RequestCliente {
    @NotNull(message = "nao pode ser null vacilao") @NotEmpty @Length(min = 2)
    private String nome;
    @Email
    private String email;
    @CPF
    private String cpf;
    private String senha;
    private Integer agencia;
    private double saldo;
}
