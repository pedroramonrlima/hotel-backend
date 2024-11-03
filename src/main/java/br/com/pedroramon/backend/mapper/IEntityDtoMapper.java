package br.com.pedroramon.backend.mapper;

public interface IEntityDtoMapper<T,D> {
    public D toDto(T entity);
    public T toEntity(D dto);
}
