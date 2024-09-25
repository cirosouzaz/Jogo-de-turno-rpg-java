import java.util.Random;

// Adicionando a inteligência ao personagem e adversário
public class Personagem {
    String nome;
    int pv;
    int maxPv;
    int forca;
    int constituicao;
    int agilidade;
    int destreza;
    int inteligencia; // Novo atributo
    int numPocoes = 3;
    boolean defendendo = false;
    Arma arma;
    Armadura armadura;
    Magia magias = new Magia(); // Instância de magias

    public Personagem(String nome, int forca, int constituicao, int agilidade, int destreza, int inteligencia, Arma arma, Armadura armadura) {
        this.nome = nome;
        this.forca = forca;
        this.constituicao = constituicao;
        this.agilidade = agilidade;
        this.destreza = destreza;
        this.inteligencia = inteligencia;
        this.arma = arma;
        this.armadura = armadura;
        this.pv = calcularPV();
        this.maxPv = this.pv;
    }

    private int calcularPV() {
        Random random = new Random();
        return random.nextInt(6) + 1 + random.nextInt(6) + 1 + random.nextInt(6) + 1 + constituicao;
    }

    public void exibirStatus() {
        System.out.println("Nome: " + nome);
        System.out.println("P.V.: " + pv + "/" + maxPv);
        System.out.println("Força: " + forca);
        System.out.println("Constituição: " + constituicao);
        System.out.println("Agilidade: " + agilidade);
        System.out.println("Destreza: " + destreza);
        System.out.println("Inteligência: " + inteligencia); // Exibindo inteligência
        System.out.println("Arma: " + arma.categoria);
        System.out.println("Armadura: Defesa = " + armadura.calcularDefesa(constituicao));
    }

    // Cálculo de dano da arma
    public int calcularDano() {
        if (arma.categoria.equals("Pesada")) {
            return arma.calcularDanoPesado(forca);
        } else {
            return arma.calcularDanoLeve(destreza);
        }
    }

    // Receber dano
    public void receberDano(int dano) {
        if (defendendo) {
            dano /= 2;
            defendendo = false; // Defesa só dura um turno
        }
        this.pv -= dano;
        if (this.pv < 0) {
            this.pv = 0;
        }
        System.out.println(nome + " recebeu " + dano + " de dano. PV restante: " + pv);
    }

    // Defender
    public void defender() {
        System.out.println(nome + " está defendendo!");
        this.defendendo = true;
    }

    // Usar poção
    public void usarPocao() {
        if (numPocoes > 0) {
            Random random = new Random();
            int cura = random.nextInt(6) + 1 + random.nextInt(6) + 1 + random.nextInt(6) + 1;
            pv += cura;
            if (pv > maxPv) pv = maxPv;
            System.out.println(nome + " usou uma poção e curou " + cura + " PV. PV atual: " + pv);
            numPocoes--;
        } else {
            System.out.println("Você não tem mais poções!");
        }
    }

    // Magias
    public void usarMagiaFogo(Adversario adversario) {
        int danoMagia = magias.magiaFogo(inteligencia);
        if (danoMagia > 0) {
            adversario.receberDano(danoMagia);
            System.out.println(nome + " lançou uma Magia de Fogo causando " + danoMagia + " de dano.");
        }
    }

    public void usarMagiaRaio(Adversario adversario) {
        int danoMagia = magias.magiaRaio(inteligencia);
        if (danoMagia > 0) {
            adversario.receberDano(danoMagia);
            System.out.println(nome + " lançou uma Magia de Raio causando " + danoMagia + " de dano.");
        }
    }

    public void usarMagiaFortalecimento() {
        int bonusForca = magias.magiaFortalecimento(forca);
        if (bonusForca > 0) {
            this.forca += bonusForca;
            System.out.println(nome + " lançou Magia de Fortalecimento! Força aumentada em " + bonusForca + " por 1 turno.");
        }
    }

    public void usarMagiaCura() {
        int cura = magias.magiaCura(constituicao);
        if (cura > 0) {
            this.pv += cura;
            if (this.pv > this.maxPv) this.pv = this.maxPv;
            System.out.println(nome + " lançou Magia de Cura e recuperou " + cura + " PV.");
        }
    }
}
