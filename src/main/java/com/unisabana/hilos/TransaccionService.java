package com.unisabana.hilos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TransaccionService {

    @Autowired
    private MoverDineroService moverDineroService;

    public void ejecutarTransferenciasConcurrentes() {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> moverDineroService.moverDinero("abc", "cbd", 5));
        }
        executorService.shutdown();
    }
}