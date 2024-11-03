package br.com.pedroramon.backend.mapper;

import java.util.function.Function;
/**
 * Interface genérica responsável pelo mapeamento entre entidades e objetos de transferência de dados (DTOs).
 *
 * @param <E> Tipo da entidade.
 * @param <D> Tipo do DTO.
 */
public class EntityDtoMapper<E, D> implements IEntityDtoMapper<E,D> {

    private final Function<E, D> toDtoFunction;
    private final Function<D, E> toEntityFunction;

    /**
     * Construtor da classe {@code GenericMapper}.
     *
     * @param toDtoFunction Função que converte uma entidade em um DTO.
     * @param toEntityFunction Função que converte um DTO em uma entidade.
     */
    public EntityDtoMapper(Function<E, D> toDtoFunction, Function<D, E> toEntityFunction) {
        this.toDtoFunction = toDtoFunction;
        this.toEntityFunction = toEntityFunction;
    }

    /**
     * Converte uma entidade para o DTO usando a função de mapeamento.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente.
     */
    @Override
    public D toDto(E entity) {
        return toDtoFunction.apply(entity);
    }

    /**
     * Converte um DTO para a entidade usando a função de mapeamento.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente.
     */
    @Override
    public E toEntity(D dto) {
        return toEntityFunction.apply(dto);
    }
}