package br.com.pedroramon.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import br.com.pedroramon.backend.model.TypeRoom;
import br.com.pedroramon.backend.repository.ITypeRoomRepository;
import br.com.pedroramon.backend.service.GenericService;

/**
 * Configuração de serviços da aplicação.
 *
 * A classe {@code TypeRoomServiceConfig} é uma classe de configuração do Spring que
 * define beans necessários para a aplicação. Esta classe é responsável por
 * configurar a injeção de dependência para o serviço {@code GenericService} que
 * opera com a entidade {@code TypeRoom}.
 *
 * A anotação {@code @EnableR2dbcAuditing} ativa a auditoria para as operações
 * de banco de dados usando R2DBC.
 */
@Configuration
@EnableR2dbcAuditing
public class TypeRoomServiceConfig {

    /**
     * Cria um bean do serviço {@code GenericService} para a entidade {@code TypeRoom}.
     *
     * Este método recebe um repositório {@code ITypeRoomRepository} como parâmetro
     * e retorna uma nova instância de {@code GenericService} configurada para a entidade
     * {@code TypeRoom}. O Spring gerencia a criação e injeção deste bean em outros
     * componentes da aplicação.
     *
     * @param typeRoomRepository O repositório que será utilizado pelo serviço
     *                           para acessar e manipular dados da entidade
     *                           {@code TypeRoom}.
     * @return Uma instância do serviço {@code GenericService<TypeRoom>}.
     */
    @Bean
    public GenericService<TypeRoom> typeRoomService(ITypeRoomRepository typeRoomRepository) {
        return new GenericService<>(typeRoomRepository);
    }
}
