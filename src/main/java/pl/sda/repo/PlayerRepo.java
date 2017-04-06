package pl.sda.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Player;

import java.awt.print.Book;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Repository
public interface PlayerRepo extends JpaRepository<Player, Integer> {

}