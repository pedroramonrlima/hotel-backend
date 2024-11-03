package br.com.pedroramon.backend.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import br.com.pedroramon.backend.model.Room;
import br.com.pedroramon.backend.repository.IRoomRepository;
import br.com.pedroramon.backend.service.interfaces.IRoomService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Function;

/**
 * Classe de serviço para gerenciar a lógica de negócio relacionada aos quartos.
 * Extende a {@link GenericService} e implementa {@link IRoomService} para
 * manipulação da entidade {@link Room}, realizando operações de CRUD através do
 * repositório {@link IRoomRepository}.
 * <p>
 * Esta classe é anotada com {@link Service} para ser reconhecida e injetada
 * pelo Spring como um componente de serviço.
 * 
 * @see GenericService
 * @see IRoomService
 * @see IRoomRepository
 * @see Room
 */
@Service
public class RoomService extends GenericService<Room> implements IRoomService {
    
    /**
     * Serviço para manipulação de tipos de quartos.
     */
    private final TypeRoomService typeRoomService;
    
    /**
     * Serviço para manipulação de status dos quartos.
     */
    private final StatusRoomService statusRoomService;
    
    /**
     * Repositório para execução das operações CRUD para a entidade {@link Room}.
     */
    private final IRoomRepository repository;

    /**
     * Construtor para injeção de dependências.
     * 
     * @param repository Repositório de quartos.
     * @param typeRoomService Serviço para manipulação de tipos de quartos.
     * @param statusRoomService Serviço para manipulação de status dos quartos.
     */
    public RoomService(IRoomRepository repository, TypeRoomService typeRoomService,
                       StatusRoomService statusRoomService) {
        super(repository);
        this.typeRoomService = typeRoomService;
        this.statusRoomService = statusRoomService;
        this.repository = repository;
    }

    /**
     * Retorna todos os quartos, associando os tipos e status correspondentes a
     * cada um.
     * 
     * @return Um {@link Flux} contendo todos os quartos com suas dependências associadas.
     */
    @Override
    public Flux<Room> findAll() {
        return this.repository.findAllByOrderByRoomIdAsc()
                .flatMap(room -> Mono.zip(
                        typeRoomService.findById(room.getTypeRoomId()),
                        statusRoomService.findById(room.getStatusRoomId()))
                        .map(tuple -> {
                            room.setTypeRoom(tuple.getT1());
                            room.setStatusRoom(tuple.getT2());
                            return room;
                        }));
    }

    /**
     * Retorna um quarto específico pelo seu ID, incluindo o tipo e status associados.
     * 
     * @param id ID do quarto a ser buscado.
     * @return Um {@link Mono} com o quarto, se encontrado, com as dependências associadas.
     */
    @Override
    public Mono<Room> findById(Long id) {
        return super.findById(id)
                .flatMap(room -> Mono.zip(
                        typeRoomService.findById(room.getTypeRoomId()),
                        statusRoomService.findById(room.getStatusRoomId()))
                        .map(tuple -> {
                            room.setTypeRoom(tuple.getT1());
                            room.setStatusRoom(tuple.getT2());
                            return room;
                        }));
    }

    /**
     * Busca um quarto pelo número do quarto, associando o tipo e status correspondentes.
     * 
     * @param roomNumber Número do quarto.
     * @return Um {@link Mono} com o quarto, se encontrado, com as dependências associadas.
     */
    @Override
    public Mono<Room> findByRoomNumber(Integer roomNumber) {
        return this.repository.findByRoomNumber(roomNumber)
                .flatMap(room -> Mono.zip(
                        typeRoomService.findById(room.getTypeRoomId()),
                        statusRoomService.findById(room.getStatusRoomId()))
                        .map(tuple -> {
                            room.setTypeRoom(tuple.getT1());
                            room.setStatusRoom(tuple.getT2());
                            return room;
                        }));
    }

    /**
     * Salva um novo quarto após validar a taxa diária e verificar a unicidade
     * do número do quarto.
     * 
     * @param room Objeto {@link Room} a ser salvo.
     * @return Um {@link Mono} com o quarto salvo.
     */
    @Override
    public Mono<Room> save(Room room) {
        return checkRoomNumberUniqueness(room.getRoomNumber())
                .then(validateDailyRate(room.getDailyRate()))
                .then(Mono.defer(() -> retrieveDependenciesAndSave(room, super::save))); 
    }

    /**
     * Atualiza um quarto existente após validar a taxa diária e verificar a
     * unicidade do número do quarto.
     * 
     * @param room Objeto {@link Room} com as informações atualizadas.
     * @return Um {@link Mono} com o quarto atualizado.
     */
    @Override
    public Mono<Room> update(Room room) {
        return findExistingRoom(room.getId())
                .flatMap(existingRoom -> {
                    if (!existingRoom.getRoomNumber().equals(room.getRoomNumber())) {
                        return checkRoomNumberUniqueness(room.getRoomNumber()).then(Mono.just(existingRoom));
                    }
                    return Mono.just(existingRoom);
                })
                .then(validateDailyRate(room.getDailyRate()))
                .then(retrieveDependenciesAndSave(room, super::update));
    }

    /**
     * Valida a taxa diária do quarto para garantir que seja igual ou superior
     * a R$60,00.
     * 
     * @param dailyRate Valor da diária.
     * @return Um {@link Mono} vazio se válido, ou um erro se o valor for menor que R$60,00.
     */
    private Mono<Void> validateDailyRate(BigDecimal dailyRate) {
        return dailyRate.compareTo(new BigDecimal("60.00")) < 0
                ? Mono.error(new IllegalArgumentException("O valor mínimo da diária deve ser 60 reais"))
                : Mono.empty();
    }

    /**
     * Verifica se já existe um quarto com o número informado.
     * 
     * @param roomNumber Número do quarto.
     * @return Um {@link Mono} vazio se único, ou um erro se já existir.
     */
    private Mono<Void> checkRoomNumberUniqueness(Integer roomNumber) {
        return this.repository.findByRoomNumber(roomNumber)
            .flatMap(existingRoom -> Mono.defer(() -> Mono.error(new IllegalArgumentException("Já existe um quarto com o número informado!"))))
            .then(); // Convertendo para Mono<Void>
    }

    /**
     * Busca um quarto existente pelo ID, lançando um erro se não encontrado.
     * 
     * @param id ID do quarto.
     * @return Um {@link Mono} com o quarto existente ou erro se não encontrado.
     */
    private Mono<Room> findExistingRoom(Long id) {
        return this.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Quarto não encontrado para atualização!")));
    }

    /**
     * Associa o tipo e status ao quarto e realiza a operação de salvamento ou atualização.
     * 
     * @param room Objeto {@link Room} a ser salvo ou atualizado.
     * @param saveOrUpdate Função de salvamento ou atualização a ser executada.
     * @return Um {@link Mono} com o quarto salvo ou atualizado.
     */
    private Mono<Room> retrieveDependenciesAndSave(Room room, Function<Room, Mono<Room>> saveOrUpdate) {
        return typeRoomService.findById(room.getTypeRoomId())
                .zipWith(statusRoomService.findById(room.getStatusRoomId()), (typeRoom, statusRoom) -> {
                    room.setTypeRoom(typeRoom);
                    room.setStatusRoom(statusRoom);
                    return room;
                })
                .flatMap(saveOrUpdate)
                .onErrorResume(e -> Mono.error(new ResourceNotFoundException("Tipo ou Status do quarto não encontrado para os IDs fornecidos")));
    }

    
}
