import java.util.Random;

public class Adversario {
    String nome;
    int pv;
    int dano;
    int defesa;
    int agilidade;
    int inteligencia; // Novo atributo
    int numPocoes = 3;
    boolean defendendo = false;
    Magia magia = new Magia(); // Instância de magias

    public Adversario(String nome, int pv, int dano, int defesa, int agilidade, int inteligencia) {
        this.nome = nome;
        this.pv = pv;
        this.dano = dano;
        this.defesa = defesa;
        this.agilidade = agilidade;
        this.inteligencia = inteligencia;
    }

    public void exibirStatus() {
        System.out.println("Adversário: " + nome);
        System.out.println("P.V.: " + pv);
        System.out.println("Dano: " + dano);
        System.out.println("Defesa: " + defesa);
        System.out.println("Agilidade: " + agilidade);
        System.out.println("Inteligência: " + inteligencia); // Exibindo inteligência
    }

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

    public int atacar() {
        return dano;
    }

    public int escolherAcao() {
        Random random = new Random();
        return random.nextInt(3); // Retorna 0 (Atacar), 1 (Defender) ou 2 (Usar Poção)
    }

    public void defender() {
        System.out.println(nome + " está defendendo!");
        this.defendendo = true;
    }

    public void usarPocao() {
        if (numPocoes > 0) {
            Random random = new Random();
            int cura = random.nextInt(6) + 1 + random.nextInt(6) + 1 + random.nextInt(6) + 1;
            pv += cura;
            System.out.println(nome + " usou uma poção e curou " + cura + " PV. PV atual: " + pv);
            numPocoes--;
        } else {
            System.out.println(nome + " não tem mais poções!");
        }
    }

    public void usarMagiaFogo(Personagem personagem) {
        int danoMagia = magia.magiaFogo(inteligencia);
        if (danoMagia > 0) {
            personagem.receberDano(danoMagia);
            System.out.println(nome + " lançou uma Magia de Fogo causando " + danoMagia + " de dano.");
        }
    }

    public void usarMagiaRaio(Personagem personagem) {
        int danoMagia = magia.magiaRaio(inteligencia);
        if (danoMagia > 0) {
            personagem.receberDano(danoMagia);
            System.out.println(nome + " lançou uma Magia de Raio causando " + danoMagia + " de dano.");
        }
    }

    public void usarMagiaFortalecimento() {
        int bonusForca = magia.magiaFortalecimento(dano);
        if (bonusForca > 0) {
            this.dano += bonusForca;
            System.out.println(nome + " lançou Magia de Fortalecimento! Dano aumentado em " + bonusForca + " por 1 turno.");
        }
    }

    public void usarMagiaCura() {
        int cura = magia.magiaCura(defesa);
        if (cura > 0) {
            this.pv += cura;
            System.out.println(nome + " lançou Magia de Cura e recuperou " + cura + " PV.");
        }
    }
}
