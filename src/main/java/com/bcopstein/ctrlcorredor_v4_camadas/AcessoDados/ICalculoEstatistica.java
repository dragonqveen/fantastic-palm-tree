package com.bcopstein.ctrlcorredor_v4_camadas.AcessoDados;

import com.bcopstein.ctrlcorredor_v4_camadas.LogicaNegocios.EstatisticasDTO;

public interface ICalculoEstatistica {
    EstatisticasDTO calculaEstatisticas(int distancia);
}
