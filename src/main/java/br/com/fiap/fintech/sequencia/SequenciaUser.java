package br.com.fiap.fintech.sequencia;

import java.util.List;
import java.util.Random;

public class SequenciaUser {

	// Método que manipula uma lista conforme a lógica descrita
    public static List<Integer> novoCodigoUser(List<Integer> lista) {
        int numero = 1;

        while (lista.contains(numero)) {
            // Se o número já existe na lista, passa para o próximo número
            numero++;
        }

        // Adiciona o próximo número à lista
        lista.add(numero);
        
        return lista;
    }
}