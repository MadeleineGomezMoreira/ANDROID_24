package com.example.bus_api_client_xml.common

object Constants {


    //ERROR MESSAGES
    const val NO_DRIVER_FOUND_ERROR = "This driver was not found in the database"
    const val NO_DRIVERS_FOUND_ERROR = "No drivers found in the database"
    const val NO_LINE_FOUND_ERROR = "This bus-line was not found in the database"
    const val NO_LINES_FOUND_ERROR = "No bus-lines found in the database"
    const val NO_STOP_FOUND_ERROR = "This bus-stop was not found in the database"
    const val NO_STOPS_FOUND_ERROR = "No bus-stops found in the database"

    //UI ERROR MESSAGES
    const val LOGIN_FIELDS_EMPTY_ERROR = "PLEASE FILL IN ALL FIELDS BEFORE LOGGING IN"
    const val REGISTER_FIELDS_EMPTY_ERROR = "PLEASE FILL IN ALL FIELDS BEFORE TRYING TO REGISTER"
    const val PERMISSION_DENIED_ERROR = "ACCESS DENIED - ONLY ADMINISTRATORS CAN ACCESS THIS FEATURE"
    const val ACTIVATION_REQUIRED_ERROR = "PLEASE ACTIVATE YOUR ACCOUNT - CHECK YOUR EMAIL FOR ACTIVATION LINK"
    const val WRONG_LOGIN_INFO_ERROR = "ACCESS DENIED - WRONG USERNAME OR PASSWORD"
    const val ACCOUNT_NOT_ACTIVATED_ERROR = "ACCOUNT NOT ACTIVATED - PLEASE ACTIVATE YOUR ACCOUNT BEFORE LOGGING IN"
    const val USERNAME_OR_EMAIL_ALREADY_EXISTS_ERROR = "USERNAME OR EMAIL ALREADY EXISTS - PLEASE TRY REGISTERING AGAIN WITH DIFFERENT CREDENTIALS"

    //UI STRINGS
    const val BLANK_SPACE = " "
    const val EMPTY_LITERAL_STRING = "empty"
    const val FORBIDDEN_STRING = "Forbidden"
    const val UNAUTHORIZED_STRING = "Unauthorized"
    const val CUSTOM_STRING = "CUSTOM"
    const val CONFLICT_STRING = "Conflict"

    //URLS
    const val BASE_URL = "http://10.0.2.2:8085/Bus_driving_server_payara-1.0-SNAPSHOT/api/"

    //REQUEST PATHS
    const val LOGIN_PATH = "login"
    const val REGISTER_PATH = "register"

    const val DRIVERS_PATH = "drivers"
    const val LINES_PATH = "lines"
    const val STOPS_PATH = "stops"

    const val DRIVER_BY_ID_PATH = "drivers/{id}"
    const val LINE_BY_ID_PATH = "lines/{id}"
    const val STOP_BY_ID_PATH = "stops/{id}"

    const val LINES_IN_A_STOP_PATH = "lines/stop/{id}"
    const val STOPS_IN_A_LINE_PATH = "stops/line/{id}"

    const val GET_DRIVER_ID_BY_USERNAME_PATH = "drivers/username/{username}"

    //REQUEST PARAMETERS
    const val ID_PARAM = "id"
    const val USERNAME_PARAM = "username"
}