package com.unisabana.hilos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
public class MoverDineroService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void moverDinero(String origen, String destino, double monto) {
        Cuenta cuentaOrigen = cuentaRepository.findByIdForUpdate(origen);
        Cuenta cuentaDestino = cuentaRepository.findByIdForUpdate(destino);

        if (cuentaOrigen.getMonto() >= monto) {
            cuentaOrigen.setMonto(cuentaOrigen.getMonto() - monto);
            cuentaDestino.setMonto(cuentaDestino.getMonto() + monto);

            cuentaRepository.save(cuentaOrigen);
            cuentaRepository.save(cuentaDestino);

            Transaccion transaccion = new Transaccion();
            transaccion.setOrigen(origen);
            transaccion.setDestino(destino);
            transaccion.setMonto(monto);
            transaccion.setTimestamp(java.time.LocalDateTime.now());

            transaccionRepository.save(transaccion);
        } else {
            throw new RuntimeException("Saldo insuficiente");
        }
    }
}