package com.grafos.controller;

import com.grafos.dto.Coordenada;
import com.grafos.dto.RotaRequest;
import com.grafos.service.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rota")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @PostMapping("/calcular")
    public ResponseEntity<List<Coordenada>> calcularRota(@RequestBody RotaRequest rota) {
        List<Coordenada> caminho = rotaService.calcular(rota);
        return ResponseEntity.ok(caminho);
    }
}
