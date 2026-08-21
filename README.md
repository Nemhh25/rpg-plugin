# RPGCore

Um plugin de RPG desenvolvido para **Minecraft Java Edition utilizando Paper API**, criado com foco em aprendizado de desenvolvimento de plugins, orientação a objetos, arquitetura de software, testes automatizados e construção de sistemas RPG.

O projeto implementa um sistema de personagens com atributos, progressão de nível, combate PVE/PVP, vida, mana, habilidades e equipamentos.

---

## 🎮 Funcionalidades

### 👤 Sistema de personagens

Cada jogador possui um personagem RPG associado ao seu UUID.

O personagem possui:

- Nome
- Nível
- Experiência
- Pontos de atributo
- Força
- Defesa
- Inteligência
- Vida
- Mana
- Equipamentos
- Habilidades

Os dados do personagem são persistidos em arquivos YAML, permitindo que o progresso seja mantido mesmo após o jogador sair do servidor.

---

## 📊 Sistema de atributos

O RPGCore possui três atributos principais:

| Atributo | Função |
|---|---|
| Strength | Aumenta o dano causado |
| Defense | Reduz o dano recebido |
| Intelligence | Aumenta a mana máxima |

Todos os personagens começam com:

```text
Strength: 5
Defense: 5
Intelligence: 5
```

Ao subir de nível, o personagem recebe **3 pontos de atributo** para distribuir.

Os atributos podem ser modificados através do sistema de atributos do plugin.

---

## ❤️ Sistema de vida

O personagem possui uma vida própria do sistema RPG.

A vida máxima atualmente é baseada nos atributos do personagem.

O sistema suporta:

- Receber dano
- Recuperar vida
- Regeneração automática
- Verificação de personagem vivo
- Morte
- Respawn
- Sincronização com a barra de vida do Minecraft

A regeneração acontece automaticamente enquanto o personagem estiver vivo e abaixo da vida máxima.

---

## 🔮 Sistema de mana

O personagem possui mana utilizada pelas habilidades.

A mana máxima é influenciada pelo atributo Intelligence.

A fórmula utilizada atualmente é:

```text
Mana máxima = 100 + ((Intelligence - 5) × 10)
```

Exemplo:

```text
Intelligence = 5
Mana máxima = 100
```

```text
Intelligence = 10
Mana máxima = 150
```

O sistema possui:

- Consumo de mana
- Recuperação de mana
- Limite de mana máxima
- Regeneração automática
- Integração com habilidades

---

## ⚔️ Sistema de combate

O combate RPG funciona tanto em **PVE quanto PVP**.

### Dano causado

A Força influencia o dano causado pelo personagem.

A lógica utiliza a diferença entre a Força atual e o valor base `5`.

```text
Força = 5
→ multiplicador de dano = 1.0
```

Cada ponto acima do valor base aumenta o multiplicador de dano.

### Dano recebido

A Defesa influencia o dano recebido.

Quanto maior a Defesa, menor o dano final recebido pelo personagem.

O sistema impede que o cálculo resulte em dano negativo.

---

## ☠️ Morte e respawn

Quando a vida RPG chega a zero:

1. O personagem é considerado morto.
2. O dano é aplicado à lógica de vida RPG.
3. O Minecraft recebe a indicação de morte.
4. O jogador visualiza a tela de morte.
5. Após o respawn, a vida RPG é restaurada.
6. A vida do Minecraft é restaurada.

Não existe atualmente perda de experiência, itens ou níveis ao morrer.

---

## 📈 Sistema de progressão

O personagem recebe experiência através do sistema de progressão.

A cada:

```text
100 XP
```

o personagem sobe um nível.

Ao subir de nível:

```text
+1 Level
+3 Attribute Points
```

Caso uma quantidade de XP permita vários níveis, o sistema continua processando os níveis até que a experiência restante seja menor que 100.

Exemplo:

```text
XP recebida: 250

Level +2
Attribute Points +6
XP restante: 50
```

---

## 🧟 XP por mobs

O sistema de experiência está integrado à morte de entidades.

Atualmente:

| Mob | XP |
|---|---:|
| Zombie | 10 |
| Skeleton | 12 |
| Spider | 10 |
| Creeper | 15 |
| Enderman | 25 |

Apenas entidades configuradas no sistema concedem XP.

Quando um jogador derrota uma entidade válida, ele recebe uma mensagem informando a experiência obtida.

Caso a morte resulte em level up, o jogador também recebe uma mensagem informando a progressão.

---

## ⚔️ Sistema de equipamentos

O RPGCore possui um sistema próprio de equipamentos.

Os equipamentos possuem informações como:

- Nome
- Tipo
- Atributo afetado
- Bônus de atributo

Os itens são identificados através de **Persistent Data Container (PDC)**, permitindo que o plugin diferencie seus equipamentos dos itens comuns do Minecraft.

Exemplo:

```text
Espada de Ferro
Tipo: Weapon
Bônus: +3 Strength
```

O sistema permite:

- Equipar equipamentos
- Desequipar equipamentos
- Ver equipamentos através de um menu
- Aplicar bônus de atributos
- Impedir equipamentos duplicados
- Identificar equipamentos RPG através de dados persistentes

---

## ✨ Sistema de habilidades

O projeto possui um sistema de habilidades integrado ao sistema de mana.

As habilidades são processadas através de um `SkillService` e utilizam o sistema de mana para controlar seus custos.

O sistema também possui comandos e listeners responsáveis pela execução das habilidades.

---

## 💾 Persistência de dados

Os personagens são salvos em arquivos YAML individuais.

Os dados persistidos incluem informações como:

```yaml
name: Nemhh15
level: 101
experience: 0
attribute-points: 298
mana: 100.0
attributes:
  strength: 7
  defense: 5
  intelligence: 5
```

O UUID do jogador é utilizado para localizar seu personagem.

O fluxo é:

```text
Jogador entra
     ↓
PlayerDataService
     ↓
Carrega YAML
     ↓
PlayerManager
     ↓
RPGCharacter
```

Ao sair:

```text
Jogador sai
     ↓
PlayerManager
     ↓
PlayerDataService
     ↓
Salva YAML
```

---

# 🛠️ Comandos

| Comando | Descrição |
|---|---|
| `/rpg` | Mostra informações do personagem |
| `/addxp <jogador> <quantidade>` | Adiciona experiência para testes |
| `/attribute <strength\|defense\|intelligence>` | Aumenta um atributo |
| `/attributes` | Abre o menu de atributos |
| `/skill <fireball>` | Utiliza uma habilidade |
| `/resetcharacter` | Reseta o personagem |
| `/giveequipment` | Recebe um equipamento de teste |
| `/equipment` | Abre o menu de equipamentos |

O comando de reset possui confirmação para evitar que o personagem seja apagado acidentalmente.

---

# 🧪 Testes

O projeto utiliza **JUnit** para testar partes importantes da lógica do RPG.

Entre os testes existentes estão:

```text
RPGCharacterManaTest
AttributeServiceTest
CombatServiceTest
ManaServiceTest
SkillServiceTest
```

Os testes ajudam a verificar individualmente sistemas como:

- Combate
- Atributos
- Mana
- Habilidades
- Comportamento do personagem

A utilização de testes automatizados permite alterar a implementação com maior segurança e detectar regressões durante o desenvolvimento.

---

# 🏗️ Arquitetura

O projeto é dividido em pacotes de acordo com a responsabilidade de cada classe.

```text
com.nelson.rpg
│
├── command
│   ├── RpgCommand
│   ├── AddXpCommand
│   ├── AttributeCommand
│   ├── AttributeMenuCommand
│   ├── SkillCommand
│   ├── ResetCharacterCommand
│   └── GiveEquipmentCommand
│
├── factory
│   └── EquipmentItemFactory
│
├── listener
│   ├── CombatListener
│   ├── EquipmentListener
│   ├── PlayerDataListener
│   ├── PlayerJoinListener
│   ├── PlayerRespawnListener
│   ├── AttributeMenuListener
│   ├── SkillListener
│   └── ExperienceListener
│
├── manager
│   └── PlayerManager
│
├── model
│   ├── RPGCharacter
│   ├── Attributes
│   ├── Equipment
│   ├── EquipmentType
│   ├── AttributeType
│   ├── SkillType
│   └── ProgressionResult
│
├── service
│   ├── AttributeService
│   ├── CombatService
│   ├── HealthService
│   ├── ManaService
│   ├── PlayerDataService
│   ├── ProgressionService
│   └── SkillService
│
└── task
    ├── ManaRegenerationTask
    └── HealthRegenerationTask
```

A separação entre `model`, `service`, `listener`, `command`, `manager` e `task` permite manter as responsabilidades organizadas.

---

# 🔄 Fluxo simplificado

O funcionamento geral do plugin pode ser representado da seguinte forma:

```text
                    ┌─────────────────┐
                    │     Jogador     │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │ PlayerManager   │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │ RPGCharacter    │
                    └────────┬────────┘
                             │
          ┌──────────────────┼──────────────────┐
          ▼                  ▼                  ▼
     Atributos            Combate           Progressão
          │                  │                  │
          ▼                  ▼                  ▼
     Equipamentos         Vida/Mana            XP
          │                  │                  │
          └──────────────────┼──────────────────┘
                             ▼
                    PlayerDataService
                             │
                             ▼
                         YAML
```

---

# 🚀 Como executar

## Requisitos

- Java
- Minecraft Java Edition
- Servidor Paper compatível com a versão utilizada pelo projeto
- Gradle

## Compilação

Na raiz do projeto:

```bash
./gradlew build
```

No Windows:

```bash
gradlew.bat build
```

O arquivo `.jar` gerado pelo Gradle pode ser colocado na pasta:

```text
plugins/
```

do servidor Paper.

Depois, reinicie o servidor.

---

# 🧰 Tecnologias utilizadas

- **Java**
- **Paper API**
- **Gradle**
- **JUnit**
- **YAML**
- **Persistent Data Container (PDC)**
- **Git / GitHub**

---

# 📚 Conhecimentos demonstrados

Este projeto foi desenvolvido como uma forma prática de estudar desenvolvimento de plugins para Minecraft e conceitos de desenvolvimento de software.

Entre os principais conhecimentos aplicados estão:

### Java

- Programação Orientada a Objetos
- Classes e objetos
- Encapsulamento
- Enum
- Interfaces
- Collections
- `HashMap`
- `UUID`
- Tratamento de exceções
- Switch expressions
- Separação de responsabilidades

### Desenvolvimento de plugins

- Paper API
- Eventos do Bukkit/Paper
- Commands
- Listeners
- Tasks
- Scheduler
- Inventários customizados
- Item Meta
- Persistent Data Container
- Integração com entidades e jogadores

### Arquitetura

- Separação entre Model, Service, Listener e Command
- Managers
- Factories
- Serviços especializados
- Persistência de dados
- Injeção de dependências através de construtores

### Testes

- JUnit
- Testes unitários
- Validação de regras de negócio
- Desenvolvimento orientado a testes em partes do projeto

---

# 🎯 Objetivo do projeto

O objetivo principal do RPGCore é servir como um projeto prático de estudo e portfólio para desenvolvimento de plugins Minecraft utilizando Java.

Em vez de concentrar toda a lógica em uma única classe, o projeto busca aplicar uma arquitetura organizada e separar as diferentes responsabilidades do sistema.

O projeto também serve como laboratório para estudar conceitos como:

- Arquitetura de software
- Orientação a objetos
- Desenvolvimento orientado a eventos
- Persistência de dados
- Testes automatizados
- Design de sistemas RPG

---

# 📌 Status

**Concluído ✅**

O RPGCore foi finalizado como projeto de estudo e portfólio.

O projeto possui um sistema RPG funcional com:

- Criação e persistência de personagens
- Atributos e pontos de atributo
- Sistema de vida e regeneração
- Sistema de mana e regeneração
- Combate PVE e PVP
- Morte e respawn
- Equipamentos e bônus de atributos
- Habilidades
- XP e progressão de níveis
- Persistência em YAML
- Testes automatizados

O objetivo do projeto foi aplicar, de forma prática, conhecimentos de Java, Programação Orientada a Objetos, desenvolvimento de plugins para Minecraft, arquitetura de software, persistência de dados e testes.

O desenvolvimento de novas funcionalidades foi encerrado após a conclusão do escopo definido para o projeto.
---

## 👨‍💻 Autor

**Nelson**

Projeto desenvolvido como parte dos estudos de desenvolvimento Java e desenvolvimento de plugins para Minecraft.