package br.com.letscode.santander.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//Elimina arquelas anotações do Servlet que são necessárias sem Spring-Boot.
@Controller
public class HealthCheckController {

    @GetMapping("/health-check")
    @ResponseBody
    public String healthCheck(){
        return "Server running";
    }
}
