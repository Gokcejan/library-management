package cz.demo.librarymanagement.rest

class TestData {

    static final String defaultAuthorBody() {
        return """
            {
                "firstName": "Milan",
                "lastName": "Kundera"
            }
        """
    }

    static final String defaultUserBody() {
        return """
            {
                "firstName": "George",
                "lastName": "Trump",
                "username": "george",
                "password": "password",
                "email": "george@trump.com",
                "phone": "123456789",
                "role": "USER"
            }
        """
    }
}
