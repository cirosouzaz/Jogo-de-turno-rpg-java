import java.util.Random;

public class Magia {
    int usosFogo = 3;
    int usosRaio = 3;
    int usosFortalecimento = 3;
    int usosCura = 3;

    // Magia de fogo (ataque)
    public int magiaFogo(int inteligencia) {
        if (usosFogo > 0) {
            usosFogo--;
            Random random = new Random();
            return random.nextInt(8) + 1 + inteligencia;
        } else {
            System.out.println("Sem usos restantes para Magia de Fogo!");
            return 0;
        }
    }

    // Magia de raio (ataque)
    public int magiaRaio(int inteligencia) {
        if (usosRaio > 0) {
            usosRaio--;
            Random random = new Random();
            return random.nextInt(10) + 1 + inteligencia;
        } else {
            System.out.println("Sem usos restantes para Magia de Raio!");
            return 0;
        }
    }

    // Magia de fortalecimento (assistência)
    public int magiaFortalecimento(int forca) {
        if (usosFortalecimento > 0) {
            usosFortalecimento--;
            Random random = new Random();
            return random.nextInt(10) + 1 + forca;
        } else {
            System.out.println("Sem usos restantes para Magia de Fortalecimento!");
            return 0;
        }
    }

    // Magia de cura (assistência)
    public int magiaCura(int constituicao) {
        if (usosCura > 0) {
            usosCura--;
            Random random = new Random();
            return random.nextInt(10) + 1 + constituicao;
        } else {
            System.out.println("Sem usos restantes para Magia de Cura!");
            return 0;
        }
    }
}