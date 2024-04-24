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
    const val LOGIN_FIELDS_EMPTY_ERROR = "Please fill in all fields (username + password) before logging in"
    const val REGISTER_FIELDS_EMPTY_ERROR = "Please fill in all fields (username + password + phone + email + first name + last name) before logging in"

    //UI STRINGS
    const val BLANK_SPACE = " "

    //URLS
    const val BASE_URL = "http://10.0.2.2:8085/Bus_driving_server_payara-1.0-SNAPSHOT/api"

    //REQUEST PATHS
    const val LOGIN_PATH = "/login"
    const val REGISTER_PATH = "/register"

    const val DRIVERS_PATH = "/drivers"
    const val LINES_PATH = "/lines"
    const val STOPS_PATH = "/stops"

    const val DRIVER_BY_ID_PATH = "/drivers/{id}"
    const val LINE_BY_ID_PATH = "/lines/{id}"
    const val STOP_BY_ID_PATH = "/stops/{id}"

    const val LINES_IN_A_STOP_PATH = "/lines/stop/{id}"
    const val STOPS_IN_A_LINE_PATH = "/stops/line/{id}"

    //REQUEST PARAMETERS
    const val ID_PARAM = "id"
}