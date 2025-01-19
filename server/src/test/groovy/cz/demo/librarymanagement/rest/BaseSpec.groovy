package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.core.CleanUpDb
import cz.demo.librarymanagement.core.WebMockSpec

class BaseSpec extends WebMockSpec implements CleanUpDb{





    def setupSpec() {
    }

    def cleanup() {
        cleanUpDb()
    }

    def cleanupSpec() {
    }


}
