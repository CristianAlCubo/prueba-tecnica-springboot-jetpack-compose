package com.ccubillos.prueba.dto

import org.springframework.http.HttpStatus

class ApiResponseDTO<T>  (
    var status : HttpStatus,
    var message : String ? = null,
    var data : T ? = null,
    var error : String ? = null
) {}