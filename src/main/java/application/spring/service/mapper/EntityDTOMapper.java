package application.spring.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;

public abstract class EntityDTOMapper<D, E> {
    protected Class<D> dClass;
    protected Class<E> eClass;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        dClass = (Class<D>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        eClass = (Class<E>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public abstract D toDTO(E entity, Object... args);

    public abstract E toEntity(D dto, Object... args);

    protected D mapToDTO(ModelMapper mapper, PropertyMap<E, D> propertyMap, E entity) {
        TypeMap<E, D> typeMap = mapper.getTypeMap(eClass, dClass);
        if (typeMap == null) { // if not  already added
            mapper.addMappings(propertyMap);
        }
        return mapper.map(entity, dClass);
    }

    protected E mapToEntity(ModelMapper mapper, PropertyMap<D, E> propertyMap, D dto) {
        TypeMap<D, E> typeMap = mapper.getTypeMap(dClass, eClass);
        if (typeMap == null) { // if not  already added
            mapper.addMappings(propertyMap);
        }
        return mapper.map(dto, eClass);
    }
}