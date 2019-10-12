package xyz.rtxux.game.shisanshui.config

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.model.dto.RankEntry
import xyz.rtxux.game.shisanshui.model.dto.ResponseObject
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ResponseAdvice : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean = true

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): Any? {
        if (body is ResponseObject) return body
        if (body is Iterable<*>) {
            if (body.firstOrNull() is RankEntry) {
                return body
            }
        }
        return ResponseObject(0, body)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleControllerException(request: HttpServletRequest, ex: Throwable): ResponseEntity<ResponseObject> {
        if (ex is AppException) return ResponseEntity.badRequest().body(ResponseObject(ex.status, ex.toString()))
        //else throw ex
        return ResponseEntity.badRequest().body(ResponseObject(5000, ex.toString()))
    }
}