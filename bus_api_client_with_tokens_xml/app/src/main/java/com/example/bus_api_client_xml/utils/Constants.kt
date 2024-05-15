package com.example.bus_api_client_xml.utils

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

    //LOGGING ERROR MESSAGES
    const val LOGIN_ERROR = "There was an error during login"
    const val LOGOFF_ERROR = "There was an error during logoff"
    const val REGISTRATION_ERROR = "There was an error during the registration process"
    const val RETRIEVING_ALL_DRIVERS_ERROR = "There was an error while retrieving the list of drivers"
    const val RETRIEVING_DRIVER_BY_ID_ERROR = "There was an error while retrieving a specific driver"
    const val RETRIEVING_DRIVER_ID_BY_USERNAME_ERROR = "There was an error while retrieving a specific driver ID by its username"
    const val RETRIEVING_STOPS_IN_LINE_ERROR = "There was an error while retrieving all stops in a line"
    const val RETRIEVING_LINES_IN_STOP_ERROR = "There was an error while retrieving all lines in a stop"
    const val RETRIEVING_ALL_LINES_ERROR = "There was an error while retrieving the list of lines"
    const val RETRIEVING_LINE_BY_ID_ERROR = "There was an error while retrieving a specific line"
    const val RETRIEVING_ALL_STOPS_ERROR = "There was an error while retrieving the list of stops"
    const val RETRIEVING_STOP_BY_ID_ERROR = "There was an error while retrieving a specific stop"

    //UI STRINGS
    const val BLANK_SPACE = " "
    const val EMPTY_STRING = ""
    const val EMPTY_LITERAL_STRING = "empty"
    const val FORBIDDEN_STRING = "Forbidden"
    const val UNAUTHORIZED_STRING = "Unauthorized"
    const val CUSTOM_STRING = "CUSTOM"
    const val CONFLICT_STRING = "Conflict"
    const val USER_ID = "userId"
    const val SHARED_PREFERENCES_NAME = "BusApiClientSP"
    const val HELLO_STRING = "hello"

    //REQUEST PATHS
    const val LOGIN_PATH = "login"
    const val REGISTER_PATH = "register"
    const val REFRESH_TOKEN_PATH = "refresh"
    const val LOGOFF_PATH = "logoff"

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

    //APP-BAR TITLES
    const val DRIVERS_LIST_TITLE = "All Drivers"
    const val DRIVER_DETAIL_TITLE = "Driver Profile"
    const val LINE_DETAIL_TITLE = "Bus Line"
    const val STOP_DETAIL_TITLE = "Bus Stop"
}