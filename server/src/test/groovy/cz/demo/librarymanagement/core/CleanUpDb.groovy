package cz.demo.librarymanagement.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

trait CleanUpDb {

    @Autowired
    JdbcTemplate jdbcTemplate

    def cleanUpDb() {
        jdbcTemplate.execute("DELETE FROM borrow")
        jdbcTemplate.execute("DELETE FROM book")
        jdbcTemplate.execute("DELETE FROM library_user")
        jdbcTemplate.execute("DELETE FROM publisher")
        jdbcTemplate.execute("DELETE FROM author")

    }

}