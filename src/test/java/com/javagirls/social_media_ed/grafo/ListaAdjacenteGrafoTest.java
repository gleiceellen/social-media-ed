package com.javagirls.social_media_ed.grafo;

import com.javagirls.social_media_ed.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Classe ListaAdjacenteGrafo - Implementação de Grafo com Lista de Adjacência")
class ListaAdjacenteGrafoTest {

    private ListaAdjacenteGrafo grafo;

    @Mock
    private Usuario usuario1;

    @Mock
    private Usuario usuario2;

    @Mock
    private Usuario usuario3;

    @BeforeEach
    void setUp() {
        grafo = new ListaAdjacenteGrafo();

        // Configuração dos mocks de Usuario
        when(usuario1.getId()).thenReturn(1);
        when(usuario1.getNomeUsuario()).thenReturn("Alice");

        when(usuario2.getId()).thenReturn(2);
        when(usuario2.getNomeUsuario()).thenReturn("Bob");

        when(usuario3.getId()).thenReturn(3);
        when(usuario3.getNomeUsuario()).thenReturn("Carol");
    }

    @Nested
    @DisplayName("Construtor")
    class ConstrutorTest {

        @Test
        @DisplayName("Deve inicializar grafo vazio")
        void deveInicializarGrafoVazio() {
            // O setup já cria um grafo, então verificamos
            assertNotNull(grafo, "O grafo deve ser instanciado corretamente");
        }
    }

    @Nested
    @DisplayName("Método adicionarUsuario()")
    class AdicionarUsuarioTest {

        @Test
        @DisplayName("Deve adicionar um novo usuário ao grafo")
        void deveAdicionarNovoUsuario() {
            // Act
            grafo.adicionarUsuario(usuario1);

            // Assert
            Optional<Usuario> usuarioBuscado = grafo.buscarUsuario(1);
            assertTrue(usuarioBuscado.isPresent(), "O usuário deve estar presente no grafo");
            assertEquals(usuario1, usuarioBuscado.get(), "O usuário buscado deve ser o mesmo adicionado");
        }

        @Test
        @DisplayName("Deve lançar IllegalArgumentException quando usuário é nulo")
        void deveLancarExcecaoParaUsuarioNulo() {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> grafo.adicionarUsuario(null),
                    "Deve lançar IllegalArgumentException para usuário nulo"
            );

            assertEquals("Usuário não pode ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Não deve adicionar usuário duplicado com mesmo ID")
        void naoDeveAdicionarUsuarioDuplicado() {
            // Arrange
            grafo.adicionarUsuario(usuario1);

            // Act
            grafo.adicionarUsuario(usuario1); // Tentativa de adicionar o mesmo usuário

            // Assert
            // Não deve lançar exceção, apenas não adicionar duplicado
            Optional<Usuario> usuarioBuscado = grafo.buscarUsuario(1);
            assertTrue(usuarioBuscado.isPresent(), "O usuário ainda deve estar presente");
            // Verificar que não houve efeito colateral negativo
        }

        @Test
        @DisplayName("Deve adicionar múltiplos usuários diferentes")
        void deveAdicionarMultiplosUsuarios() {
            // Act
            grafo.adicionarUsuario(usuario1);
            grafo.adicionarUsuario(usuario2);
            grafo.adicionarUsuario(usuario3);

            // Assert
            assertAll("Todos os usuários devem estar presentes",
                    () -> assertTrue(grafo.buscarUsuario(1).isPresent(), "Usuário 1 deve existir"),
                    () -> assertTrue(grafo.buscarUsuario(2).isPresent(), "Usuário 2 deve existir"),
                    () -> assertTrue(grafo.buscarUsuario(3).isPresent(), "Usuário 3 deve existir")
            );
        }
    }

    @Nested
    @DisplayName("Método buscarUsuario()")
    class BuscarUsuarioTest {

        @BeforeEach
        void adicionarUsuariosParaBusca() {
            grafo.adicionarUsuario(usuario1);
            grafo.adicionarUsuario(usuario2);
        }

        @Test
        @DisplayName("Deve encontrar usuário existente pelo ID")
        void deveEncontrarUsuarioExistente() {
            // Act
            Optional<Usuario> resultado = grafo.buscarUsuario(1);

            // Assert
            assertTrue(resultado.isPresent(), "Deve encontrar o usuário");
            assertEquals(usuario1, resultado.get(), "Deve retornar o usuário correto");
        }

        @Test
        @DisplayName("Deve retornar Optional vazio para usuário não existente")
        void deveRetornarVazioParaUsuarioInexistente() {
            // Act
            Optional<Usuario> resultado = grafo.buscarUsuario(999);

            // Assert
            assertFalse(resultado.isPresent(), "Não deve encontrar usuário inexistente");
        }

        @Test
        @DisplayName("Deve buscar usuários diferentes corretamente")
        void deveBuscarUsuariosDiferentesCorretamente() {
            // Act
            Optional<Usuario> resultadoUsuario1 = grafo.buscarUsuario(1);
            Optional<Usuario> resultadoUsuario2 = grafo.buscarUsuario(2);

            // Assert
            assertAll("Usuários diferentes devem ser retornados corretamente",
                    () -> assertTrue(resultadoUsuario1.isPresent(), "Usuário 1 deve existir"),
                    () -> assertTrue(resultadoUsuario2.isPresent(), "Usuário 2 deve existir"),
                    () -> assertEquals(usuario1, resultadoUsuario1.get(), "Deve retornar usuário 1 correto"),
                    () -> assertEquals(usuario2, resultadoUsuario2.get(), "Deve retornar usuário 2 correto"),
                    () -> assertNotSame(resultadoUsuario1.get(), resultadoUsuario2.get(),
                            "Usuários devem ser instâncias diferentes")
            );
        }
    }

    @Nested
    @DisplayName("Método adicionarConexao()")
    class AdicionarConexaoTest {

        @BeforeEach
        void adicionarUsuariosParaConexao() {
            grafo.adicionarUsuario(usuario1);
            grafo.adicionarUsuario(usuario2);
            grafo.adicionarUsuario(usuario3);
        }

        @Test
        @DisplayName("Deve adicionar conexão entre dois usuários existentes")
        void deveAdicionarConexaoEntreUsuariosExistentes() {
            // Act - adiciona conexão entre usuários 1 e 2
            grafo.adicionarConexao(1, 2);

            // Assert
            // Como a classe não expõe métodos para verificar conexões,
            // apenas verificamos que não houve exceção
            assertDoesNotThrow(() -> grafo.adicionarConexao(1, 2),
                    "Adicionar conexão entre usuários existentes não deve lançar exceção");
        }

        @Test
        @DisplayName("Não deve adicionar conexão quando primeiro usuário não existe")
        void naoDeveAdicionarConexaoQuandoPrimeiroUsuarioNaoExiste() {
            // Act
            grafo.adicionarConexao(999, 2); // ID 999 não existe

            // Assert
            // Não deve lançar exceção, apenas não adicionar conexão
            assertDoesNotThrow(() -> grafo.adicionarConexao(999, 2));
            // Usuário 2 ainda deve existir
            assertTrue(grafo.buscarUsuario(2).isPresent());
        }

        @Test
        @DisplayName("Não deve adicionar conexão quando segundo usuário não existe")
        void naoDeveAdicionarConexaoQuandoSegundoUsuarioNaoExiste() {
            // Act
            grafo.adicionarConexao(1, 999); // ID 999 não existe

            // Assert
            // Não deve lançar exceção, apenas não adicionar conexão
            assertDoesNotThrow(() -> grafo.adicionarConexao(1, 999));
            // Usuário 1 ainda deve existir
            assertTrue(grafo.buscarUsuario(1).isPresent());
        }

        @Test
        @DisplayName("Não deve adicionar conexão quando ambos usuários não existem")
        void naoDeveAdicionarConexaoQuandoAmbosUsuariosNaoExistem() {
            // Act & Assert
            assertDoesNotThrow(() -> grafo.adicionarConexao(999, 1000),
                    "Não deve lançar exceção mesmo quando ambos IDs não existem");
        }

        @Test
        @DisplayName("Deve permitir adicionar múltiplas conexões a partir de um usuário")
        void devePermitirAdicionarMultiplasConexoes() {
            // Act - adiciona conexões do usuário 1 para usuários 2 e 3
            grafo.adicionarConexao(1, 2);
            grafo.adicionarConexao(1, 3);

            // Assert
            // Ambos os usuários ainda devem estar presentes
            assertTrue(grafo.buscarUsuario(2).isPresent());
            assertTrue(grafo.buscarUsuario(3).isPresent());
        }

        @Test
        @DisplayName("Deve permitir conexão mútua (grafo não-direcionado)")
        void devePermitirConexaoMutua() {
            // Act - adiciona conexão 1-2 e depois 2-1 (não deve criar duplicata)
            grafo.adicionarConexao(1, 2);
            grafo.adicionarConexao(2, 1); // Conexão inversa

            // Assert
            // Não deve lançar exceção
            assertDoesNotThrow(() -> grafo.adicionarConexao(2, 1));
            assertTrue(grafo.buscarUsuario(1).isPresent());
            assertTrue(grafo.buscarUsuario(2).isPresent());
        }
    }

    @Nested
    @DisplayName("Casos de borda e cenários especiais")
    class CasosDeBordaTest {

        @Test
        @DisplayName("Deve lidar com usuário com ID zero")
        void deveLidarComUsuarioComIdZero() {
            // Arrange
            Usuario usuarioZero = mock(Usuario.class);
            when(usuarioZero.getId()).thenReturn(0);
            when(usuarioZero.getNomeUsuario()).thenReturn("UsuarioZero");

            // Act
            grafo.adicionarUsuario(usuarioZero);

            // Assert
            Optional<Usuario> resultado = grafo.buscarUsuario(0);
            assertTrue(resultado.isPresent(), "Deve encontrar usuário com ID zero");
            assertEquals(usuarioZero, resultado.get());
        }

        @Test
        @DisplayName("Deve lidar com usuário com ID negativo")
        void deveLidarComUsuarioComIdNegativo() {
            // Arrange
            Usuario usuarioNegativo = mock(Usuario.class);
            when(usuarioNegativo.getId()).thenReturn(-1);
            when(usuarioNegativo.getNomeUsuario()).thenReturn("UsuarioNegativo");

            // Act
            grafo.adicionarUsuario(usuarioNegativo);

            // Assert
            Optional<Usuario> resultado = grafo.buscarUsuario(-1);
            assertTrue(resultado.isPresent(), "Deve encontrar usuário com ID negativo");
            assertEquals(usuarioNegativo, resultado.get());
        }

        @Test
        @DisplayName("Deve manter isolamento entre diferentes instâncias de grafo")
        void deveManterIsolamentoEntreGrafos() {
            // Arrange
            ListaAdjacenteGrafo outroGrafo = new ListaAdjacenteGrafo();

            // Act
            grafo.adicionarUsuario(usuario1);
            outroGrafo.adicionarUsuario(usuario2);

            // Assert
            assertAll("Grafos devem ser independentes",
                    () -> assertTrue(grafo.buscarUsuario(1).isPresent(),
                            "Usuário 1 deve existir no primeiro grafo"),
                    () -> assertFalse(grafo.buscarUsuario(2).isPresent(),
                            "Usuário 2 não deve existir no primeiro grafo"),
                    () -> assertTrue(outroGrafo.buscarUsuario(2).isPresent(),
                            "Usuário 2 deve existir no segundo grafo"),
                    () -> assertFalse(outroGrafo.buscarUsuario(1).isPresent(),
                            "Usuário 1 não deve existir no segundo grafo")
            );
        }
    }

    @Nested
    @DisplayName("Integração entre métodos")
    class IntegracaoTest {

        @Test
        @DisplayName("Fluxo completo: adicionar usuários, buscar, adicionar conexões")
        void fluxoCompletoAdicionarBuscarConectar() {
            // 1. Adicionar usuários
            grafo.adicionarUsuario(usuario1);
            grafo.adicionarUsuario(usuario2);
            grafo.adicionarUsuario(usuario3);

            // 2. Buscar usuários
            Optional<Usuario> alice = grafo.buscarUsuario(1);
            Optional<Usuario> bob = grafo.buscarUsuario(2);
            Optional<Usuario> carol = grafo.buscarUsuario(3);

            assertTrue(alice.isPresent());
            assertTrue(bob.isPresent());
            assertTrue(carol.isPresent());

            // 3. Adicionar conexões
            grafo.adicionarConexao(1, 2); // Alice conecta com Bob
            grafo.adicionarConexao(1, 3); // Alice conecta com Carol

            // 4. Buscar novamente para verificar que nada foi corrompido
            Optional<Usuario> aliceAposConexao = grafo.buscarUsuario(1);
            Optional<Usuario> bobAposConexao = grafo.buscarUsuario(2);
            Optional<Usuario> carolAposConexao = grafo.buscarUsuario(3);

            assertAll("Usuários devem permanecer acessíveis após conexões",
                    () -> assertTrue(aliceAposConexao.isPresent()),
                    () -> assertTrue(bobAposConexao.isPresent()),
                    () -> assertTrue(carolAposConexao.isPresent()),
                    () -> assertEquals("Alice", aliceAposConexao.get().getNomeUsuario()),
                    () -> assertEquals("Bob", bobAposConexao.get().getNomeUsuario()),
                    () -> assertEquals("Carol", carolAposConexao.get().getNomeUsuario())
            );
        }

        @Test
        @DisplayName("Adicionar conexão e depois buscar usuário não deve afetar resultado")
        void adicionarConexaoNaoDeveAfetarBuscaUsuario() {
            // Arrange
            grafo.adicionarUsuario(usuario1);
            grafo.adicionarUsuario(usuario2);

            // Buscar antes da conexão
            Optional<Usuario> usuario1Antes = grafo.buscarUsuario(1);

            // Act - adicionar conexão
            grafo.adicionarConexao(1, 2);

            // Buscar depois da conexão
            Optional<Usuario> usuario1Depois = grafo.buscarUsuario(1);

            // Assert
            assertAll("Usuário deve permanecer o mesmo antes e depois da conexão",
                    () -> assertTrue(usuario1Antes.isPresent()),
                    () -> assertTrue(usuario1Depois.isPresent()),
                    () -> assertEquals(usuario1Antes.get(), usuario1Depois.get(),
                            "Usuário não deve mudar após adicionar conexão"),
                    () -> assertEquals(usuario1Antes.get().getId(), usuario1Depois.get().getId()),
                    () -> assertEquals(usuario1Antes.get().getNomeUsuario(),
                            usuario1Depois.get().getNomeUsuario())
            );
        }
    }
}