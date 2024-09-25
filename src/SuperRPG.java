import java.util.Random;
import java.util.Scanner;

public class SuperRPG {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Legend of CIRO ===");
        System.out.println("1. Começar Jogo");
        System.out.println("2. Ver Introdução");
        System.out.println("3. Sair");

        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1 -> iniciarJogo(scanner);
            case 2 -> mostrarIntroducao();
            case 3 -> System.out.println("Jogo encerrado.");
            default -> System.out.println("Opção inválida.");
        }
        scanner.close();
    }

    // Método para mostrar a introdução à história
    private static void mostrarIntroducao() {
        System.out.println("\n=== Introdução ===");
        System.out.println("Em um reino distante, uma sombra se espalhou sobre as terras.");
        System.out.println("Criaturas malignas surgiram, ameaçando a paz que havia reinado por séculos.");
        System.out.println("Um CIRO destemido deve se levantar para enfrentar esses desafios, ");
        System.out.println("derrotar as forças do mal e restaurar a esperança no coração do povo.");
        System.out.println("Prepare-se CIRO, para uma jornada épica de combate e aventura!");
        System.out.println("====================\n");
    }

    // Iniciar o jogo e criar o personagem
    private static void iniciarJogo(Scanner scanner) {
        System.out.println("=== Criação de Personagem ===");

        // Criação do personagem com arma e armadura iniciais
        Arma armaPesada = new Arma("Pesada", 5);
        Armadura armaduraLeve = new Armadura(2);
        Personagem personagem = new Personagem("Herói", 5, 5, 5, 5, 5, armaPesada, armaduraLeve);

        // Três combates consecutivos
        if (combate(personagem, criarAdversario("Goblin", 20, 5, 2, 3, 3), scanner)) {
            premiacaoPrimeira(personagem, scanner);
        } else {
            mostrarDerrota();
            return;
        }

        if (combate(personagem, criarAdversario("Orc", 25, 7, 3, 4, 4), scanner)) {
            premiacaoSegunda(personagem, scanner);
        } else {
            mostrarDerrota();
            return;
        }

        if (combate(personagem, criarAdversario("Dragão", 35, 10, 5, 6, 6), scanner)) {
            mostrarVitoria();
        } else {
            mostrarDerrota();
        }
    }

    // Cria um adversário com atributos predefinidos
    private static Adversario criarAdversario(String nome, int pv, int dano, int defesa, int agilidade, int inteligencia) {
        return new Adversario(nome, pv, dano, defesa, agilidade, inteligencia);
    }

    // Função de combate entre o personagem e o adversário
    private static boolean combate(Personagem personagem, Adversario adversario, Scanner scanner) {
        System.out.println("\n=== Combate contra " + adversario.nome + " ===");

        personagem.exibirStatus();
        adversario.exibirStatus();

        boolean turnoPersonagem = personagem.agilidade >= adversario.agilidade;

        while (personagem.pv > 0 && adversario.pv > 0) {
            if (turnoPersonagem) {
                turnoJogador(personagem, adversario, scanner);
            } else {
                turnoAdversario(personagem, adversario);
            }
            turnoPersonagem = !turnoPersonagem; // alterna turnos
        }

        return personagem.pv > 0; // Se o personagem estiver vivo, ele venceu
    }

    // Ações disponíveis para o jogador durante o turno
    private static void turnoJogador(Personagem personagem, Adversario adversario, Scanner scanner) {
        System.out.println("\nSua vez! Escolha sua ação:");
        System.out.println("1. Atacar");
        System.out.println("2. Defender");
        System.out.println("3. Usar Poção (" + personagem.numPocoes + " restantes)");
        System.out.println("4. Usar Magia");

        int acao = scanner.nextInt();
        switch (acao) {
            case 1 -> {
                // Atacar
                int dano = personagem.calcularDano() - adversario.defesa;
                if (dano < 0) dano = 0;
                adversario.receberDano(dano);
            }
            case 2 -> {
                // Defender
                personagem.defender();
            }
            case 3 -> {
                // Usar Poção
                personagem.usarPocao();
            }
            case 4 -> {
                // Usar Magia
                usarMagiaPersonagem(personagem, adversario, scanner);
            }
            default -> System.out.println("Ação inválida.");
        }
    }

    // Jogador escolhe qual magia usar
    private static void usarMagiaPersonagem(Personagem personagem, Adversario adversario, Scanner scanner) {
        System.out.println("\nEscolha uma magia:");
        System.out.println("1. Magia de Fogo");
        System.out.println("2. Magia de Raio");
        System.out.println("3. Magia de Fortalecimento");
        System.out.println("4. Magia de Cura");

        int escolha = scanner.nextInt();
        switch (escolha) {
            case 1 -> personagem.usarMagiaFogo(adversario);
            case 2 -> personagem.usarMagiaRaio(adversario);
            case 3 -> personagem.usarMagiaFortalecimento();
            case 4 -> personagem.usarMagiaCura();
            default -> System.out.println("Magia inválida.");
        }
    }

    // Ações disponíveis para o adversário durante o turno
    private static void turnoAdversario(Personagem personagem, Adversario adversario) {
        System.out.println("\nTurno do adversário!");
        int acao = adversario.escolherAcao();

        switch (acao) {
            case 0 -> {
                // Atacar
                int dano = adversario.atacar() - personagem.calcularDano();
                if (dano < 0) dano = 0;
                personagem.receberDano(dano);
            }
            case 1 -> {
                // Defender
                adversario.defender();
            }
            case 2 -> {
                // Usar Poção
                adversario.usarPocao();
            }
            case 3 -> {
                // Usar Magia
                usarMagiaAdversario(adversario, personagem);
            }
        }
    }

    // Adversário escolhe uma magia aleatória para usar
    private static void usarMagiaAdversario(Adversario adversario, Personagem personagem) {
        Random random = new Random();
        int escolha = random.nextInt(4);  // Escolhe entre 0 (fogo), 1 (raio), 2 (fortalecimento), 3 (cura)
        
        switch (escolha) {
            case 0 -> adversario.usarMagiaFogo(personagem);
            case 1 -> adversario.usarMagiaRaio(personagem);
            case 2 -> adversario.usarMagiaFortalecimento();
            case 3 -> adversario.usarMagiaCura();
        }
    }

    // Recompensa após a primeira vitória
    private static void premiacaoPrimeira(Personagem personagem, Scanner scanner) {
        System.out.println("\n=== Primeira Premiação ===");
        System.out.println("Você subiu de nível! Ganhe 5 pontos para distribuir nos atributos.");
        personagem.pv += personagem.constituicao;
        distribuirPontos(personagem, 5, scanner);
        escolherNovaArma(personagem, scanner);
    }

    // Recompensa após a segunda vitória
    private static void premiacaoSegunda(Personagem personagem, Scanner scanner) {
        System.out.println("\n=== Segunda Premiação ===");
        System.out.println("Você subiu dois níveis! Ganhe 10 pontos para distribuir nos atributos.");
        personagem.pv += 2 * personagem.constituicao;
        distribuirPontos(personagem, 10, scanner);
        escolherNovaArmadura(personagem, scanner);
    }

    // Método para distribuir pontos de atributo ao subir de nível
    private static void distribuirPontos(Personagem personagem, int pontos, Scanner scanner) {
        while (pontos > 0) {
            System.out.println("\nPontos restantes: " + pontos);
            System.out.println("1. Aumentar Força");
            System.out.println("2. Aumentar Constituição");
            System.out.println("3. Aumentar Agilidade");
            System.out.println("4. Aumentar Destreza");
            System.out.println("5. Aumentar Inteligência");

            int escolha = scanner.nextInt();
            switch (escolha) {
                case 1 -> {
                    personagem.forca++;
                    pontos--;
                }
                case 2 -> {
                    personagem.constituicao++;
                    pontos--;
                }
                case 3 -> {
                    personagem.agilidade++;
                    pontos--;
                }
                case 4 -> {
                    personagem.destreza++;
                    pontos--;
                }
                case 5 -> {
                    personagem.inteligencia++;
                    pontos--;
                }
                default -> System.out.println("Escolha inválida.");
            }
        }
        personagem.exibirStatus();
    }

    // Escolher uma nova arma após a primeira vitória
    private static void escolherNovaArma(Personagem personagem, Scanner scanner) {
        System.out.println("\nEscolha uma nova arma:");
        System.out.println("1. Espada Pesada (dano +10)");
        System.out.println("2. Machado Pesado (dano +12)");
        System.out.println("3. Adaga Leve (dano +8)");

        int escolha = scanner.nextInt();
        switch (escolha) {
            case 1 -> personagem.arma = new Arma("Pesada", 10);
            case 2 -> personagem.arma = new Arma("Pesada", 12);
            case 3 -> personagem.arma = new Arma("Leve", 8);
            default -> System.out.println("Escolha inválida.");
        }
    }

    // Escolher uma nova armadura após a segunda vitória
    private static void escolherNovaArmadura(Personagem personagem, Scanner scanner) {
        System.out.println("\nEscolha uma nova armadura:");
        System.out.println("1. Armadura Leve (defesa +6)");
        System.out.println("2. Armadura Média (defesa +8)");
        System.out.println("3. Armadura Pesada (defesa +10)");

        int escolha = scanner.nextInt();
        switch (escolha) {
            case 1 -> personagem.armadura = new Armadura(6);
            case 2 -> personagem.armadura = new Armadura(8);
            case 3 -> personagem.armadura = new Armadura(10);
            default -> System.out.println("Escolha inválida.");
        }
    }

    // Mostrar tela de vitória
    private static void mostrarVitoria() {
        System.out.println("\n=== Vitória! ===");
        System.out.println("Parabéns, você venceu o jogo!");
    }

    // Mostrar tela de derrota
    private static void mostrarDerrota() {
        System.out.println("\n=== Derrota ===");
        System.out.println("Você foi derrotado! O jogo terminou.");
    }
}
