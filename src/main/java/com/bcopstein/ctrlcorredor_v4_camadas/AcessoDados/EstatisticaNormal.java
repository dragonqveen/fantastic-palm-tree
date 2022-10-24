package com.bcopstein.ctrlcorredor_v4_camadas.AcessoDados;

import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.ctrlcorredor_v4_camadas.LogicaNegocios.EstatisticasDTO;

public class EstatisticaNormal implements ICalculoEstatistica{
    private EventoRepository eventoRep;

    public EstatisticasDTO calculaEstatisticas(int distancia){
        // Seleciona os eventos da distancia informada
        List<Evento> eventos = 
            eventoRep.todos()
                .stream()
                .filter(e->e.getDistancia() == distancia)
                .collect(Collectors.toList());
        // Obtém um stream com os valores ordenados
        List<Double> valores = eventos
            .stream()
            .map(e-> e.getHoras()*60*60 + e.getMinutos()*60.0 + e.getSegundos())
            .sorted()
            .collect(Collectors.toList());
        // Calcula a média
        double media = valores
            .stream()
            .mapToDouble(v->v)
            .average()
            .orElse(Double.NaN);
        // Seleciona os eventos da distancia informada
        // Calcula mediana
        Double mediana = Double.NaN;
        if (valores.size() > 0){
            mediana =
                ((valores.size() % 2 == 0) ?
                (valores.get(valores.size()/2 - 1))+(valores.get(valores.size()/2))/2.0 :
                (valores.get(valores.size()/2)));
        }
        // Calcula o desvio padrao
        double varianca;
        double desvioPadrao = Double.NaN;
        if (mediana != Double.NaN){
            varianca = valores
                .stream()
                .mapToDouble(v -> v - media)
                .map(v -> v*v)
                .average().getAsDouble();
            desvioPadrao = Math.sqrt(varianca);
        }
        return new EstatisticasDTO(media, mediana, desvioPadrao);
    }
}
