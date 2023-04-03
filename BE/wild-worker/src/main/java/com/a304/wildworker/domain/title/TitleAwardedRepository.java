package com.a304.wildworker.domain.title;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleAwardedRepository extends JpaRepository<TitleAwarded, Long> {

    boolean existsByTitleIdAndUserId(Long titleId, Long userId);
}
