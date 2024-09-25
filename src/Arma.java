import java.util.Random;


// Classe para representar uma Arma
class Arma {
    String categoria;
    int constanteDano;

    public Arma(String categoria, int constanteDano) {
        this.categoria = categoria;
        this.constanteDano = constanteDano;
    }

    public int calcularDanoPesado(int forca) {
        Random random = new Random();
        return constanteDano + random.nextInt(12) + 1 + (int) (1.5 * forca);
    }

    public int calcularDanoLeve(int destreza) {
        Random random = new Random();
        return constanteDano + random.nextInt(6) + 1 + random.nextInt(6) + 1 + random.nextInt(4) + 1 + destreza;
    }
}
