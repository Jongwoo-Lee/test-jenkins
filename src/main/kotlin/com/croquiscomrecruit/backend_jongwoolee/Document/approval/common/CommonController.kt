package com.croquiscomrecruit.backend_jongwoolee.Document.approval.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CommonController @Autowired constructor(
        val commonService: CommonService
) {
    @GetMapping(value=["/api/category/all"])
    fun getCategories() = ResponseEntity.ok(commonService.getAllCategories())

}