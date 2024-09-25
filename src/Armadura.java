// Classe para representar uma Armadura
public class Armadura {
    int constanteDefesa;

    public Armadura(int constanteDefesa) {
        this.constanteDefesa = constanteDefesa;
    }

    public int calcularDefesa(int constituicao) {
        return constanteDefesa + (int) (1.5 * constituicao);
    }
}