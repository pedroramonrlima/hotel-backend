package br.com.pedroramon.backend.service;

import org.springframework.stereotype.Service;

import br.com.pedroramon.backend.model.StatusRoom;
import br.com.pedroramon.backend.repository.IStatusRoomRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada ao status dos quartos.
 * Esta classe estende a {@link GenericService} e utiliza um repositório específico
 * para gerenciar as operações de CRUD para a entidade {@link StatusRoom}.
 *  
 * A classe {@code StatusRoomService} é anotada com {@link Service}, permitindo que o 
 * Spring a reconheça como um componente de serviço e a injete em outros componentes 
 * conforme necessário.
 *  
 * @see GenericService
 * @see StatusRoom
 * @see IStatusRoomRepository
 */
@Service
public class StatusRoomService extends GenericService<StatusRoom> {

    /**
     * Construtor da classe {@code StatusRoomService}.
     * 
     * @param repository O repositório a ser utilizado para gerenciar as operações
     *                   de CRUD relacionadas a {@link StatusRoom}.
     */
    public StatusRoomService(IStatusRoomRepository repository) {
        super(repository);
    }
}
