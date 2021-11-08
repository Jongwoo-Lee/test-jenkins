package com.croquiscomrecruit.backend_jongwoolee.Document.approval.doc

import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.ResUser
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.auth.toDto
import com.croquiscomrecruit.backend_jongwoolee.Document.approval.common.Category
import org.springframework.data.domain.Page
import java.sql.Timestamp
import java.util.*

data class ReqCreateDoc(
    val title: String,
    val content: String,
    val category: Int,
    val users: List<String>? // 순서대로 Seq 저장
)

data class ResDoc(
        val id: Int,
        val title: String,
        val content: String,
        val category: Category?,
        val regDate: Date,
        val status: DocStatus,
        val user: ResUser
)
fun Doc.toDto() = ResDoc(
        this.id,
        this.title,
        this.content,
        this.category,
        this.regDate,
        this.status,
        this.user.toDto()
)
fun Array<Object>.toResDoc() = ResDoc(
        this[0] as Int,
        this[2] as String,
        this[1] as String,
        if(this[6] == null) null else Category(this[6] as Int),
        Date((this[4] as Timestamp).time),
        try { DocStatus.valueOf(this[5] as String) }
        catch (e: IllegalArgumentException) { DocStatus.PENDING },
        ResUser(this[3] as String),
)

data class ResOutboxPage(
        val count: Long,
        val page: Int,
        val outbox: List<ResDoc>
)
fun Page<Doc>.toDto() = ResOutboxPage(
        this.totalElements,
        this.totalPages,
        this.content.map { it.toDto() }
)
fun Page<Array<Object>>.toDocPage() = ResOutboxPage(
        this.totalElements,
        this.totalPages,
        this.content.map { it.toResDoc() }
)

data class ResDocApproval(
        val id: Int,
        val doc: ResDoc,
        val status: ApprovalStatus,
        val comment: String?,
)
fun DocApproval.toDto() = ResDocApproval(
        this.id,
        this.doc.toDto(),
        this.status,
        this.comment
)

data class ResInboxPage(
        val count: Long,
        val page: Int,
        val inbox: List<ResDocApproval>
)
fun Page<DocApproval>.toDto() = ResInboxPage(
        this.totalElements,
        this.totalPages,
        this.content.map { it.toDto() }
)

data class ReqDocSign(
        val docId: Int,
        val decision: String,
        val comment: String?
)

data class ResDocSign(
        val result: String
)