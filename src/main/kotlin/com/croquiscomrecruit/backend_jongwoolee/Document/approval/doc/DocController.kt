package com.croquiscomrecruit.backend_jongwoolee.Document.approval.doc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/api/doc")
class DocController @Autowired constructor(
        val docService: DocService
){

    @PostMapping("")
    fun createDoc(
            @RequestBody @Valid param: ReqCreateDoc,
            principal: Principal
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(docService.create(param, principal.name))
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/outbox")
    fun getOutbox(
            @RequestParam pageNum: Int?,
            @RequestParam pageSize: Int?,
            principal: Principal
    ): ResponseEntity<Any> {
        return try {
             ResponseEntity.ok(
                    docService.getOutbox(
                            principal.name,
                            pageNum ?: 0,
                            pageSize ?: 10
                    )
            )
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/inbox")
    fun getInbox(
            @RequestParam pageNum: Int?,
            @RequestParam pageSize: Int?,
            principal: Principal
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(
                    docService.getInbox(
                            principal.name,
                            pageNum ?: 0,
                            pageSize ?: 10
                    )
            )
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/sign")
    fun signDoc(
            @RequestBody param: ReqDocSign,
            principal: Principal
    ): ResponseEntity<Any> {
        return try {
            docService.sign(principal.name, param)
            ResponseEntity.ok(ResDocSign("success"))
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }

    }


    @GetMapping("/archive")
    fun getArchive(
            @RequestParam pageNum: Int?,
            @RequestParam pageSize: Int?,
            principal: Principal
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(
                    docService.getArchive(principal.name,
                    pageNum ?: 0,
                    pageSize ?: 10
                    )
            )
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}