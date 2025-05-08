package hus.daa.app.repository;

import hus.daa.app.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Words, Integer> {
    // Tìm kiếm từ trong cơ sở dữ liệu
    Words findByWord(String word);

    List<Words> findAll();
}
