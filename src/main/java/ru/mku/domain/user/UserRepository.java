package ru.mku.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Iterable<User> findAll();

    User findOne(Long aLong);

    <S extends User> S save(S entity);

    User findByLogin(String login);
}
