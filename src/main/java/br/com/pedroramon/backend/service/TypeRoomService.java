package br.com.pedroramon.backend.service;

import org.springframework.stereotype.Service;

import br.com.pedroramon.backend.model.TypeRoom;
import br.com.pedroramon.backend.repository.ITypeRoomRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada ao tipo dos quartos.
 * Esta classe estende a {@link GenericService} e utiliza um repositório específico
 * para gerenciar as operações de CRUD para a entidade {@link TypeRoom}.
 *  
 * A classe {@code TypeRoomService} é anotada com {@link Service}, permitindo que o 
 * Spring a reconheça como um componente de serviço e a injete em outros componentes 
 * conforme necessário.
 *  
 * @see GenericService
 * @see TypeRoom
 * @see ITypeRoomRepository
 */
@Service
public class TypeRoomService extends GenericService<TypeRoom> {

    public TypeRoomService(ITypeRoomRepository repository) {
        super(repository);
    }
    
}
