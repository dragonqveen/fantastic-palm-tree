package com.bcopstein.ctrlcorredor_v4_camadas.Interface;

import java.util.List;

import com.bcopstein.ctrlcorredor_v4_camadas.AcessoDados.Corredor;
import com.bcopstein.ctrlcorredor_v4_camadas.AcessoDados.CorredorRepository;
import com.bcopstein.ctrlcorredor_v4_camadas.AcessoDados.Evento;
import com.bcopstein.ctrlcorredor_v4_camadas.AcessoDados.EventoRepository;
import com.bcopstein.ctrlcorredor_v4_camadas.LogicaNegocios.EstatisticasDTO;
import com.bcopstein.ctrlcorredor_v4_camadas.LogicaNegocios.PerformanceDTO;
import com.bcopstein.ctrlcorredor_v4_camadas.LogicaNegocios.ServicoEstatistica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctrlCorridas")
public class CtrlCorridasController {
    private CorredorRepository corredorRep;
    private EventoRepository eventoRep;
    private ServicoEstatistica servicoEstatistica;

    @Autowired
    public CtrlCorridasController(IServicoEstatistica servicoEstatistica,CorredorRepository corredorRep,EventoRepository eventoRep) {
        this.servicoEstatistica = servicoEstatistica;
        this.corredorRep = corredorRep;
        this.eventoRep = eventoRep;
    }

    @GetMapping("/corredor")
    @CrossOrigin(origins = "*")
    public List<Corredor> consultaCorredor() {
        return corredorRep.todos();
    }

    @PostMapping("/corredor")
    @CrossOrigin(origins = "*")
    public boolean cadastraCorredor(@RequestBody final Corredor corredor) {
        corredorRep.removeTodos();
        corredorRep.cadastra(corredor);
        return true;
    }

    @GetMapping("/eventos")
    @CrossOrigin(origins = "*")
    public List<Evento> consultaEventos() {
        return eventoRep.todos();
    }

    @PostMapping("/eventos") // adiciona evento no único corredor
    @CrossOrigin(origins = "*")
    public boolean informaEvento(@RequestBody final Evento evento) {
        eventoRep.cadastra(evento);
        return true;
    }

    @GetMapping("/estatisticas")
    @CrossOrigin(origins = "*")
    public EstatisticasDTO estatisticas(@RequestParam final Integer distancia){
        return servicoEstatistica.calculaEstatisticas(distancia);
    }

    @GetMapping("/aumentoPerformance")
    @CrossOrigin(origins = "*")
    public PerformanceDTO aumentoPerformance(@RequestParam final Integer distancia,
                                            @RequestParam final Integer ano){
        return servicoEstatistica.calculaAumentoPerformance(distancia, ano);
    }
}
