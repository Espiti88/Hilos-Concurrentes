package com.unisabana.hilos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private MoverDineroService moverDineroService;


    @PostMapping("/mover-dinero")
    public String moverDinero() {
        transaccionService.ejecutarTransferenciasConcurrentes();

        return "Transferencias ejecutadas.";
    }

    @PostMapping("/transferir")
    public String transferir(@RequestParam String origen, @RequestParam String destino, @RequestParam double monto) {
        try {
            moverDineroService.moverDinero(origen, destino, monto);
            return "Transacción ejecutada con éxito.";
        } catch (Exception e) {
            return "Error al ejecutar la transacción: " + e.getMessage();
        }
    }

}
