package br.com.pedroramon.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroramon.backend.exception.InvalidDataException;
import br.com.pedroramon.backend.exception.ResourceNotFoundException;
import br.com.pedroramon.backend.model.IEntity;
import br.com.pedroramon.backend.repository.IRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Classe de serviço genérica para operações de persistência de dados.
 *
 * Esta classe implementa a interface {@link IService} e fornece métodos para
 * realizar operações CRUD (Create, Read, Update, Delete) em entidades do tipo
 * {@code T}, que deve implementar a interface {@link IEntity}. As operações
 * são realizadas através de um repositório, que é injetado pelo Spring Framework.
 *
 * @param <T> O tipo da entidade que será gerenciada por este serviço, que deve
 *            implementar a interface {@link IEntity}.
 */
@Service
public class GenericService<T extends IEntity> implements IService<T> {

    private final IRepository<T> repository;

    /**
     * Construtor da classe {@code GenericService}.
     *
     * Este construtor inicializa uma instância da classe {@code GenericService} com um
     * repositório que será utilizado para realizar operações de persistência de dados.
     * A injeção de dependência é feita pelo Spring Framework, que fornece uma
     * implementação da interface {@link IRepository} no momento da criação da instância.
     *
     * @param repository O repositório que será utilizado para acessar e manipular
     *                   os dados do tipo {@code T}. Este parâmetro deve ser uma
     *                   implementação da interface {@link IRepository}. A injeção
     *                   deste parâmetro é realizada automaticamente pelo Spring
     *                   através da anotação {@code @Autowired}.
     */
    @Autowired
    public GenericService(IRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Recupera todas as entidades do tipo {@code T} do repositório.
     *
     * @return Um {@link Flux} contendo todas as entidades encontradas.
     */
    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }

    /**
     * Recupera uma entidade do tipo {@code T} pelo seu ID.
     *
     * Este método tenta encontrar a entidade correspondente ao ID fornecido. Se a entidade
     * não for encontrada, uma exceção {@link ResourceNotFoundException} será lançada, indicando
     * que não há um objeto associado ao ID informado.
     *
     * @param id O ID da entidade a ser recuperada.
     * @return Um {@link Mono} contendo a entidade encontrada, ou um erro se não for encontrada.
     * @throws ResourceNotFoundException Se a entidade não for encontrada.
     */
    @Override
    public Mono<T> findById(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Object not found with id: " + id)));
    }

    /**
     * Salva uma nova entidade do tipo {@code T} no repositório.
     *
     * Este método realiza a operação de salvamento da entidade fornecida. 
     * Caso ocorra um erro durante o processo de salvamento, uma exceção 
     * {@link InvalidDataException} será lançada, contendo informações sobre
     * o erro que ocorreu. 
     *
     * @param object A entidade a ser salva, que deve ser uma instância da
     *               classe {@code T} que implementa a interface {@link IEntity}.
     * @return Um {@link Mono} contendo a entidade salva. Se o salvamento for
     *         bem-sucedido, a entidade retornada será a mesma que foi passada
     *         como parâmetro, possivelmente com um ID gerado pelo repositório.
     * @throws InvalidDataException Se ocorrer um erro durante o processo de salvamento.
     */
    @Override
    public Mono<T> save(T object) {
        return repository.save(object)
            .onErrorMap(ex -> new InvalidDataException("Error saving object: " + ex.getMessage()));
    }

    /**
     * Remove uma entidade do tipo {@code T} pelo seu ID.
     *
     * Este método realiza a operação de remoção da entidade correspondente ao ID fornecido.
     *
     * @param id O ID da entidade a ser removida.
     * @return Um {@link Mono} que indica quando a operação de remoção é concluída.
     */
    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    /**
     * Atualiza uma entidade existente do tipo {@code T}.
     *
     * Este método busca a entidade pelo ID e, se encontrada, atualiza seus dados.
     * Caso a entidade não seja encontrada, uma exceção {@link ResourceNotFoundException}
     * é lançada.
     *
     * @param object A entidade com os dados atualizados.
     * @return Um {@link Mono} contendo a entidade atualizada ou um erro se a entidade
     *         não for encontrada.
     * @throws ResourceNotFoundException Se a entidade não for encontrada.
     */
    @Override
    public Mono<T> update(T object) {
        return repository.findById(object.getId())
            .flatMap(existingObject -> {
                object.setCreatedAt(existingObject.getCreatedAt());
                return repository.save(object);
            })
            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Object not found with id: " + object.getId())));
    }
}
