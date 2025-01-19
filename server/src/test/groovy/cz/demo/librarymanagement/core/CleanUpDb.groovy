package cz.demo.librarymanagement.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

trait CleanUpDb {

    @Autowired
    JdbcTemplate jdbcTemplate

    def cleanUpDb() {
        jdbcTemplate.execute("DELETE FROM author")
        jdbcTemplate.execute("DELETE FROM book")

    }

}